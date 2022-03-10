package telran.time;

public class TImerTestAppl {

	public static void main(String[] args) throws InterruptedException {
Timer timer = new Timer();
//timer.setDaemon(true);
timer.setPattern("HH:mm:ss");
timer.start();
//application running immitation
Thread.sleep(5000);
timer.interrupt();
Thread.sleep(5000);
//interrupt
//Thread.currentThread().interrupt();
//Thread.sleep(100000000000000L);
	}

}
