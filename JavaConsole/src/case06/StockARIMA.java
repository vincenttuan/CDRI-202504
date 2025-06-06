package case06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import com.github.signaflo.timeseries.TimeSeries;
import com.github.signaflo.timeseries.model.arima.Arima;
import com.github.signaflo.timeseries.model.arima.ArimaOrder;

public class StockARIMA {

	public static void main(String[] args) throws IOException {
		// 利用 ARIMA 來預測股價
		// 將 2330.txt 所有股價放到 double[] 中
		List<String> list = Files.readAllLines(Path.of("src/case06/2330.txt"));
		System.out.println(list);
		double[] prices = list.stream()
				.map(line -> line.split(",")[1])
				.mapToDouble(Double::parseDouble)
				.toArray();
		System.out.println(Arrays.toString(prices));
		// 利用 ARIMA
		// 將股價價格數據轉換為時間序列對象
		TimeSeries series = TimeSeries.from(prices);
		
		// 定義模型參數
		// ArimaOrder.order(p, d, q)中的p、d和q分別是ARIMA模型的自迴歸階數、差分階數和移動平均階數
		ArimaOrder order = ArimaOrder.order(1, 1, 1); // 參數可以過 LLM 來取得
		
		// 擬合 ARIMA 模型
		Arima model = Arima.model(series, order);
		
		// 預測下一個值
		double predicted = model.forecast(1).pointEstimates().at(0);
		
		System.out.printf("預測今日收盤價: %.1f %n", predicted);
		
	}

}
