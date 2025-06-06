package case06;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.google.gson.Gson;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

//實用工具類，包含獲取股票資料和繪製股價折線圖的方法
public class Utils {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	public static void disableSSLVerification() {
	    try {
	        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
	        } };

	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	// 從台灣證交所API獲取股票資訊的JSON字符串
	private static String getJsonString(String stockNo, String today) throws IOException {
		disableSSLVerification();
		if(today == null) today = dateFormat.format(new Date());
		// 股票資訊API的URL
		String path = "https://www.twse.com.tw/rwd/zh/afterTrading/STOCK_DAY?date=" + today + "&stockNo=" + stockNo + "&response=json";
		try (Scanner scanner = new Scanner(new URL(path).openStream()).useDelimiter("\\A")) {
			String result = scanner.hasNext() ? scanner.next() : "";
			return result;
		}
	}

	// 獲取指定股票的時間序列和收盤價格
	public static double[][] getTimeAndClosingPrice(String stockNo, String today) throws IOException {
		// 調用getJsonString獲取JSON數據
		String jsonString = getJsonString(stockNo, today);

		// 使用Gson解析JSON數據
		Gson gson = new Gson();
		TradingData tradingData = gson.fromJson(jsonString, TradingData.class);

		// 創建一個二維數組來儲存時間和價格
		double[][] prices = new double[tradingData.data.size()][2];

		for (int i = 0; i < tradingData.data.size(); i++) {
			List<String> dailyData = tradingData.data.get(i);
			prices[i][0] = i + 1; // 時間流水號
			prices[i][1] = Double.parseDouble(dailyData.get(6).replace(",", "")); // 收盤價在索引6的位置
		}

		return prices;
	}

	// 單獨獲取指定股票的收盤價格
	public static double[] getClosingPrice(String stockNo, String today) throws IOException {
		// 調用getJsonString獲取JSON數據
		String jsonString = getJsonString(stockNo, today);
		Gson gson = new Gson();
		TradingData tradingData = gson.fromJson(jsonString, TradingData.class);

		// 從tradingData中提取收盤價格，並轉換為double數組
		return tradingData.data.stream().mapToDouble(dailyData -> Double.parseDouble(dailyData.get(6).replace(",", ""))) // 收盤價是在索引6的位置
				.toArray();
	}

	// 獲取指定股票的成交量
	public static double[] getVolume(String stockNo, String today) throws IOException {
		// 調用getJsonString獲取JSON數據
		String jsonString = getJsonString(stockNo, today);
		Gson gson = new Gson();
		TradingData tradingData = gson.fromJson(jsonString, TradingData.class);

		// 從tradingData中提取成交量，並轉換為double數組
		return tradingData.data.stream().mapToDouble(dailyData -> Double.parseDouble(dailyData.get(8).replace(",", ""))) // 成交量是在索引8的位置
				.toArray();
	}

	
	// 內部類，用於解析從API獲取的股票交易數據
	private class TradingData {
		String stat;
		String date;
		String title;
		List<String> fields;
		List<List<String>> data;
		List<String> notes;
		int total;
	}
}

