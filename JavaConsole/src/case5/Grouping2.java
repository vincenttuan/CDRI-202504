package case5;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Grouping2 {

	public static void main(String[] args) {
		List<Map> students = List.of(
				Map.of("gender", "男", "grade", "A"),
				Map.of("gender", "男", "grade", "B"),
				Map.of("gender", "女", "grade", "C"),
				Map.of("gender", "女", "grade", "B"),
				Map.of("gender", "男", "grade", "A")
		);
		System.out.println(students);
		
		// 利用 gender 來分組
		
		// 利用 grade 來分組
				
	}

}
