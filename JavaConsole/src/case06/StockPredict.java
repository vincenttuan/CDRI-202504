package case06;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class StockPredict {

	public static void main(String[] args) throws Exception {
		// 利用簡單線性回歸來預測股價
		// 將 2330.txt 所有股價放到 double[] 中
		List<String> list = Files.readAllLines(Path.of("src/case06/2330.txt"));
		System.out.println(list);
		double[] prices = list.stream()
				.map(line -> line.split(",")[1])
				.mapToDouble(Double::parseDouble)
				.toArray();
		System.out.println(Arrays.toString(prices));
		// 利用 SimpleRegression
		SimpleRegression regression = new SimpleRegression();
		for(int i=0;i<prices.length;i++) {
			regression.addData(i+1, prices[i]);
		}
		// 預測今日
		double predicted = regression.predict(prices.length + 1);
		System.out.printf("預測今日收盤價: %.1f %n", predicted);
	}

}
