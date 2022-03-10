package telran.list;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class ListThreadAppl {

	private static final int N_THTREADS = 10000;
	private static final int N_NUMBERS = 1000;
	private static final int UPDATE_PROB = 50;
	private static final int N_RUNS = 10000;
	public static void main(String[] args) {
		List<Integer> list = createList();
		List<ListThread> threads = IntStream.range(0, N_THTREADS)
				.mapToObj(i -> new ListThread(list, UPDATE_PROB, N_RUNS))
				.collect(Collectors.toCollection(ArrayList::new));
		threads.forEach(ListThread::start);
				

	}
	static List<Integer> createList() {
		return IntStream.range(0, N_NUMBERS).boxed().collect(Collectors.toCollection(ArrayList::new));
	}

}