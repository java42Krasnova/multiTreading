package telran.printer;

public class Printer extends Thread {
String string;
int length; 
volatile boolean running = true;
public void setRunning(boolean running) {
	this.running = running;
}


public Printer (String string) {
	
	this.string = string;
	length = string.length();
	setName(string);
}
@Override
public void run() {
	int index = 0;
	while(running) {
		System.out.printf("%s: %s\n", getName(), string.charAt(index));
		try {
			sleep(3000);
		} catch (InterruptedException e) {
			index++;
			if(index == length) {
				index = 0;
			}
		}
	}
}
}
