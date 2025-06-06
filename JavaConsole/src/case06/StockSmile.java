package case06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class StockSmile {

	public static void main(String[] args) throws IOException {
		// 利用 Smile 來預測股價
		// 將 2330.txt 所有股價與成交量放到 double[] 中
		List<String> list = Files.readAllLines(Path.of("src/case06/2330.txt"));
		System.out.println(list);
		double[] prices = list.stream()
				.map(line -> line.split(",")[1])
				.mapToDouble(Double::parseDouble)
				.toArray();
		System.out.println(Arrays.toString(prices));
		double[] volumes = list.stream()
				.map(line -> line.split(",")[2])
				.mapToDouble(Double::parseDouble)
				.toArray();
		System.out.println(Arrays.toString(volumes));
	}

}
