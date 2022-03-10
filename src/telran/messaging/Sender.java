package telran.messaging;

import telran.messaging.produser.MessageBox;

public class Sender extends Thread {
	MessageBox messageBox;
	int nMessages;
	public Sender(MessageBox messageBox, int nMessages) {
		this.messageBox = messageBox;
		this.nMessages = nMessages;
	} 
	@Override
	public void run() {
		for(int i = 1; i <= nMessages; i++) {
			messageBox.setText("message" + i);
//			try {
//				sleep(10);
//			} catch (InterruptedException e) {
//				
//			}
		}
	}
}