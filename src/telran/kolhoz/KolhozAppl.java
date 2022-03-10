package telran.kolhoz;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

import telran.kolhoz.atomic.Truck;

//import telran.kolhoz.synch.Truck;

public class KolhozAppl {

	private static final int N_LOADS = 100000;
	private static final long N_TRUCKS = 1000;

	public static void main(String[] args) {
		List<Truck> trucks = Stream.generate(() -> new Truck(1, N_LOADS))
				.limit(N_TRUCKS).toList();
		Instant start = Instant.now();
		trucks.forEach(Truck::start);
		trucks.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				
			}
		});
		System.out.printf("elevateor1=%d; elevator2=%d "
				+ "runnin time %d\n", Truck.getElevator1(), Truck.getElevator2(),
				ChronoUnit.MILLIS.between(start, Instant.now()));

	}

}