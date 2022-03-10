package telran.deadlock;

public class Worker extends Thread{
private static Object mutex1 = new Object();
private static Object mutex2 = new Object();
public void f1() {
	synchronized (mutex1) {
		try {
			sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (mutex2) {
			
		}
	}
	
}
public void f2() {
	synchronized (mutex2) {
		synchronized (mutex1) {
			
		}
	}
}
@Override
	public void run() {
	for(int i = 0; i<10; i++)	{
	f1();
		f2();
	}}
}
