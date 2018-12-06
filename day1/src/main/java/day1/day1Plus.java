package day1;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import common.ReadInput;

/*
Not working ...
 */
public class day1Plus {
	
	public static void main(String[] args) throws IOException {
		ReadInput read = new ReadInput();
//		List<String> inputList = ReadInput.readInput(input);
		List<String> inputList = read.readFile("input.txt");
		System.out.println("input size: " + inputList.size());

		day1Plus day = new day1Plus();
		int sum = day.findFrequency(inputList);

		System.out.println("answer:" + sum);
		//part1: 484
		//part2: 367	
	}
	
	public int findFrequency(List<String> list) {
		IntSupplier input = new IntSupplier() {
			private int index = -1; //special case
			private List<String> array=list;

			public int getAsInt() {
				return Integer.valueOf(array.get(++index%array.size()));
			}
		};

		IntStream.generate(input).limit(15).forEach(System.out::println);
		
		//.forEach(System.out::println)
//	    list.stream().reduce(0, (a,b) ->  a + b );
		//ap( i -> collect(Collectors.summingInt(i -> i));
		return 0;
	}
	
	public class MapCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {
		@Override
		public Supplier<Map<Boolean, List<Integer>>> supplier() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Characteristics> characteristics() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
}
