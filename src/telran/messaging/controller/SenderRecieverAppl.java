package telran.messaging.controller;

import java.util.List;
import java.util.stream.Stream;

import telran.messaging.Sender;
import telran.messaging.consumer.Reciever;
import telran.messaging.produser.MessageBox;

public class SenderRecieverAppl {

	private static final int N_MESSAGES = 20;
	private static final int N_RECIEVERS = 10;

	public static void main(String[] args) throws InterruptedException {
		final MessageBox messageBox = new MessageBox();
		Sender sender = new Sender(messageBox, N_MESSAGES);
		List<Reciever> recievers = Stream.generate(() -> new Reciever(messageBox))
				.limit(N_RECIEVERS).toList();
		recievers.forEach(Reciever::start);
		sender.start();
		Thread.sleep(100);
		recievers.forEach(r -> r.interrupt());
	}

}