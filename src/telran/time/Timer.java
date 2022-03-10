package telran.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.text.DateFormatter;

public class Timer extends Thread {
	private static final String DEFAULT_TIME_PATTERN = "hh:mm ss a";
	private static final long DEFAULT_TIME_OUT = 1000;
	String pattern = DEFAULT_TIME_PATTERN;
	long timeOut = DEFAULT_TIME_OUT;
	
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern); 
	
	public Timer() {
		setDaemon(true);
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
		dtf = DateTimeFormatter.ofPattern(pattern);
	}

@Override
public void run() {
	while(true) {
		System.out.println(LocalTime.now().format(dtf));
		try {
			sleep(timeOut);
		} catch (InterruptedException e) {
			break;

		}
	}
}
}
