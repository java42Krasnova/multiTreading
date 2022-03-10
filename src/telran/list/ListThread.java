package telran.list;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ListThread extends Thread {

private static final int N_READS = 1000;
private final List<Integer> list;
static private ReadWriteLock lock = new ReentrantReadWriteLock();
static private Lock readLock = lock.readLock();
static private Lock writeLock = lock.writeLock();
private int probUpdate;
private int nRuns;
public ListThread(List<Integer> list, int probUpdate, int nRuns) {
	this.list = list;
	this.probUpdate = probUpdate;
	this.nRuns = nRuns;
}
private void update() {
	
		try {
			writeLock.lock();
			int index = list.size() - 1;
			list.remove(index);
			list.add(100);
		} finally {
			writeLock.unlock();
		}
	
}
private int getRandomNumber(int min, int max) {
	
	return ThreadLocalRandom.current().nextInt(min, max);
}
private void read() {

		
			try {
				readLock.lock();
				for (int i = 0; i < N_READS; i++) {
					list.get(list.size() - 1);
				} 
			} finally {
				readLock.unlock();
			}
		
}
@Override
	public void run() {
		for (int i = 0; i < nRuns; i++) {
			if (getRandomNumber(1, 100) < probUpdate) {
				update();
			} else {
				read();
			} 
		}
	}

}