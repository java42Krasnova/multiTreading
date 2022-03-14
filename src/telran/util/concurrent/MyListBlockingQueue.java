package telran.util.concurrent;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class MyListBlockingQueue<E> implements BlockingQueue<E> {
	// TODO fields of the class
	// V.R. Queu<E> looks more naturally
	private LinkedList<E> listQueue = new LinkedList<>();;
	int queueLimit = Integer.MAX_VALUE;
	private Lock monitor = new ReentrantLock();;
	private Condition producerWaiting = monitor.newCondition();
	private Condition cunsumerWaiting = monitor.newCondition();

	public MyListBlockingQueue(int limit) {
		// TODO done
		queueLimit = limit;
	}

	@Override
	public E remove() {
		// TODO done
		monitor.lock();
		if (listQueue.isEmpty()) {
			throw new NoSuchElementException();
		}
		try {
			// V.R. removeFirst() throws NoSuchElementException - if this list is empty
			return listQueue.removeFirst();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E poll() {
		// TODO done
		monitor.lock();
		try {
			return listQueue.isEmpty() ? null : listQueue.removeFirst();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E element() {
		// TODO done
		monitor.lock();
		try {
			if (listQueue.isEmpty()) {
				throw new NoSuchElementException();
			}
			// V.R. getFirst() throws NoSuchElementException - if this list is empty
			return listQueue.getFirst();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E peek() {
		// TODO done
		monitor.lock();
		try {
			return listQueue.isEmpty() ? null : listQueue.getFirst();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public int size() {
		// TODO done
		monitor.lock();
		try {
			// V.R. Is it possible to do the same without lock?
			return listQueue.size();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		// TODO done
		monitor.lock();
		try {
			return listQueue.isEmpty();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public Iterator<E> iterator() {

		return null;
	}

	@Override
	public Object[] toArray() {

		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {

		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {

		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {

		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {

		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {

		return false;
	}

	@Override
	public void clear() {
		// TODO done
		monitor.lock();
		try {
			listQueue.clear();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean add(E e) {
		// TODO done
		monitor.lock();
		try {
			if (listQueue.size() == queueLimit) {
				throw new IllegalStateException();
			}
			return listQueue.add(e);
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		// TODO done
		monitor.lock();
		try {
			return listQueue.size() == queueLimit ? false : listQueue.add(e);
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public void put(E e) throws InterruptedException {
		// TODO done
		monitor.lock();
		try {
			while (listQueue.size() == queueLimit) {
				try {
					producerWaiting.await();
				} catch (InterruptedException m) {
					// V.R. May be break is suitable here?
					// It is impossible to leave the cycle without break
				}
			}
			listQueue.add(e);
			cunsumerWaiting.signal();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		// TODO done
		monitor.lock();
		try {
			while (listQueue.size() == queueLimit) {
				try {
					// V.R. It doesn't work in case of time is elapsed
					// await() returns false in case of timeout
					producerWaiting.await(timeout, unit);
				} catch (InterruptedException m) {
					return false;
				}
			}
			listQueue.add(e);
			cunsumerWaiting.signal();
			return true;

		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		// TODO done
		E res;
		monitor.lock();
		try {
			while (listQueue.isEmpty()) {
				cunsumerWaiting.await();
			}
			res = listQueue.removeFirst();
			producerWaiting.signal();
			return res;
		} finally {
			monitor.unlock();
		}

	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		// TODO done
		E res;
		monitor.lock();
		try {
			while (listQueue.isEmpty()) {
				try {
					// V.R. It doesn't work in case of time is elapsed
					cunsumerWaiting.await(timeout, unit);
				} catch (InterruptedException m) {
					return null;
				}
			}
			res = listQueue.removeFirst();
			producerWaiting.signal();
			return res;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public int remainingCapacity() {
		// TODO done
		monitor.lock();
		// V.R. Is it possible to do the same without lock?
		try {
			return queueLimit - listQueue.size();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		// TODO done
		monitor.lock();
		try {
			return listQueue.remove(o);
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean contains(Object o) {
		// TODO done
		monitor.lock();
		try {
			return listQueue.contains(o);
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		// V.R. The implementation isn't requested
		//TODO done
		Lock collLock = new ReentrantLock();
		monitor.lock();
		// V.R. This lock does nothing
		collLock.lock();
		try {
			int res = listQueue.size();
			c.addAll(listQueue);
			listQueue.clear();
			return res;
		} finally {
			monitor.unlock();
			collLock.unlock();
		}
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		// V.R. The implementation isn't requested
		//TODO done
		Lock collLock = new ReentrantLock();
		monitor.lock();
		// V.R. This lock does nothing
		collLock.lock();
		try {
			if (listQueue.size() <= maxElements) {
				int res = listQueue.size();
				c.addAll(listQueue);
				listQueue.clear();
				return res;
			}
			IntStream.range(0, maxElements).mapToObj(i -> c.add(listQueue.removeLast()));
			return maxElements;
		} finally {
			monitor.unlock();
			collLock.unlock();
		}

	}

}