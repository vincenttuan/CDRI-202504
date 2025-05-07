package case5;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Grouping2 {

	public static void main(String[] args) {
		List<Map> students = List.of(
				Map.of("gender", "男", "score", 100),
				Map.of("gender", "男", "score", 50),
				Map.of("gender", "女", "score", 40),
				Map.of("gender", "女", "score", 80),
				Map.of("gender", "男", "score", 90)
		);
		// 利用 gender 來分組
		
		// 利用 score 來分組
				
	}

}
