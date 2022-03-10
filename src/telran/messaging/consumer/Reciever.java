package telran.messaging.consumer;

import telran.messaging.produser.MessageBox;

public class Reciever extends Thread {

	MessageBox messageBox;

	public Reciever(MessageBox messageBox) {
		this.messageBox = messageBox;
		//TODO DONE

	}
	@Override
	public void run() {
	boolean	flRun = true;
		while(flRun) {
			try {
				String message = messageBox.getText();
				System.out.printf("%s - %s\n", getName(), message);
			} catch (InterruptedException e) {
				flRun = false;
			}
		}
	}

}