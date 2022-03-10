package telran.kolhoz.atomic;

import java.util.concurrent.atomic.AtomicLong;

public class Truck extends Thread {
private static AtomicLong elevator1 = new AtomicLong(0);
private static AtomicLong elevator2 = new AtomicLong(0);
private static final Object mutex = new Object();
public static long getElevator1() {
	return elevator1.get();
}
public static long getElevator2() {
	return elevator2.get();
}
int load;
int nLoads;
public Truck(int load, int nLoads) {
	this.load = load;
	this.nLoads = nLoads;
}
@Override
	public void run() {
		for (int i = 0; i < nLoads; i++) {
			loadElevator1(load);
			loadElevator2(load);
		}
	}
private  void loadElevator1(int load) {
	elevator1.addAndGet(load);
	
}
private  static void loadElevator2(int load) {
		elevator2.addAndGet(load);
	
}

}