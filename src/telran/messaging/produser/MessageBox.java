package telran.messaging.produser;

public class MessageBox {
private String text;
public synchronized void setText(String text) {
	while(this.text != null) {
		try {
			this.wait();
		} catch (InterruptedException e) {
			
		}
	}
	this.text = text;
	this.notify();
}
public synchronized String getText() throws InterruptedException {
	while (text == null) {
		this.wait();
	}
	String res = text;
	text = null;
	//DONE
	this.notifyAll();
	return res;
}
public synchronized String pullText() {
	return text;
}
}