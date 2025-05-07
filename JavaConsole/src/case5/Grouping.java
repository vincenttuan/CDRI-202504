package case5;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Grouping {

	public static void main(String[] args) {
		List<String> fruits = List.of("apple", "apple", "banana", "apple", "orange", "banana", "papaya");
		// 請問每一種水果各有幾個 ?
		System.out.println(
				fruits.stream().collect(groupingBy(identity(), counting()))
		);
		
		Map<String, Long> result = fruits.stream().collect(groupingBy(identity(), counting()));
		System.out.println(result);
	}

}
