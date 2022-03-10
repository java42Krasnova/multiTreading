package telran.multithreading.introduction;

public class PribterAppl {

	public static void main(String[] args) throws InterruptedException {
Printer printer1 = new Printer('*',100);
Printer printer2 = new Printer('#',100);
//printer1.run();
//printer2.run();
printer1.start();
printer2.start();
//Thread.sleep(1000);
printer1.join();
printer2.join();
System.out.println("All threads are over");
	}

}
