package telran.kolhoz;



public class Truck extends Thread {
private static long elevator1;
private static long elevator2;
private static final Object mutex = new Object();
public static long getElevator1() {
	return elevator1;
}
public static long getElevator2() {
	return elevator2;
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
private static synchronized void loadElevator1(int load) {
	elevator1 += load;
	
}
private static void loadElevator2(int load) {
	synchronized (mutex) {
		elevator2 += load;
	}
	
}

}