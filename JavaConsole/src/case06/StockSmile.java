package case06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.formula.Formula;
import smile.data.vector.DoubleVector;
import smile.regression.RandomForest;

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
		
		// 建立一個 DataFrame (分別將 prices 與 volumes 放入)
		DataFrame data = DataFrame.of(DoubleVector.of("Price", prices));
		data = data.merge(DoubleVector.of("Volume", volumes));
		
		// 要預測的變量
		Formula formula = Formula.lhs("Price");
		
		// 使用隨機森林建立回歸模型
		RandomForest model = RandomForest.fit(formula, data);
		
		// 獲取數據集中的最後一條數據（最後一天的價格和成交量），以預測下一個值（明日股價
		Tuple lastRow = data.stream().skip(data.nrows() - 1).findFirst().orElse(null);
		
		// 取得預測價格
		double predicted = model.predict(lastRow);
		
		System.out.printf("預測今日收盤價: %.1f %n", predicted);
		
	}

}
