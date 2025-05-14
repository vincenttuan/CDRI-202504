package com.example.demo.controller;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.BMI;
import com.example.demo.model.Book;
import com.example.demo.response.ApiResponse;

@RestController // 免去撰寫 @ResponseBody, 但若要透過 jsp 渲染則不適用
@RequestMapping("/api") // 以下路徑統一都有 URL 前綴 "/api"
public class ApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	/**
	 * 1. 首頁
	 * 路徑: /home
	 * 路徑: /
	 * 網址: http://localhost:8080/api/home
	 * 網址: http://localhost:8080/api/
	 * */
	@GetMapping(value = {"/home", "/"})
	public String home() {
		return "我是首頁";
	}
	
	/**
	 * 2. ?帶參數
	 * 路徑: /greet?name=John&age=18
	 * 路徑: /greet?name=Mary
	 * 網址: http://localhost:8080/api/greet?name=John&age=18
	 * 結果: Hi John, 18 (成年)
	 * 網址: http://localhost:8080/api/greet?name=Mary
	 * 結果: Hi Mary, 0 (未成年)
	 * 限制: name 參數一定要加, age 為可選參數(有初始值 0)
	 * */
	@GetMapping("/greet")
	public String greet(@RequestParam(value = "name", required = true) String username,
						@RequestParam(value = "age", required = false, defaultValue = "0") Integer userage) {
		
		logger.info("執行路徑: /greet 參數: name=" + username + ", age=" + userage);
		String result = String.format("Hi %s %d (%s)", 
				username, userage, userage >= 18 ? "成年" : "未成年");
		return result;
	}
	
	// 3. 上述 2 的精簡寫法
	// 方法參數名與請求參數名相同
	@GetMapping("/greet2")
	public String greet2(@RequestParam String name,
						 @RequestParam(defaultValue = "0") Integer age) {

		String result = String.format("Hi %s %d (%s)", 
								name, age, age >= 18 ? "成年" : "未成年");
		return result;
	}
	
	/**
	 * 4. Lab 練習 I
	 * 路徑: /bmi?h=170&w=60
	 * 網址: http://localhost:8080/api/bmi?h=170&w=60
	 * 執行結果: 
	 * {
	 *   "status": 200,
	 *   "message": "BMI 計算成功",
	 *   "data": {
	 *     "height": 170.0,
	 *     "weight": 60.0,
	 *     "bmi": 20.76
	 *   }
	 * }
	 * */
	@GetMapping(value = "/bmi", produces = "application/json;charset=utf-8")
	public ResponseEntity<ApiResponse<BMI>> calcBmi(@RequestParam(required = false) Double h, 
						            @RequestParam(required = false) Double w) {
		if(h == null || w == null) {
			return ResponseEntity.badRequest().body(ApiResponse.error("請提供身高(h)或體重(w)"));
		}
		double bmi = w / Math.pow(h/100, 2);
		return ResponseEntity.ok(ApiResponse.success("BMI 計算成功", new BMI(h, w, bmi)));
	}
	
	/**
	 * 5. 同名多筆資料
	 * 路徑: /age?age=17&age=21&age=20
	 * 網址: http://localhost:8080/api/age?age=17&age=21&age=20
	 * 請計算出平均年齡
	 * */
	@GetMapping(value = "/age", produces = "application/json;charset=utf-8")
	public ResponseEntity<ApiResponse<Object>> getAverage(@RequestParam(name = "age", required = false) List<String> ages) {
		if(ages == null || ages.size() == 0) {
			return ResponseEntity.badRequest().body(ApiResponse.error("請輸入年齡(age)"));
		}
		double avg = ages.stream().mapToInt(Integer::parseInt).average().orElseGet(() -> 0);
		Object map = Map.of("年齡", ages, "平均年齡", String.format("%.1f", avg));
		return ResponseEntity.ok(ApiResponse.success("計算成功", map));
	}
	
	/*
	 * 6. Lab 練習: 得到多筆 score 資料
	 * 路徑: "/exam?score=80&score=100&score=50&score=70&score=30"
	 * 網址: http://localhost:8080/api/exam?score=80&score=100&score=50&score=70&score=30
	 * 請自行設計一個方法，此方法可以
	 * 印出: 最高分=?、最低分=?、平均=?、總分=?、及格分數列出=?、不及格分數列出=?
	 */
	@GetMapping(value = "/exam", produces = "application/json;charset=utf-8")
	public ResponseEntity<ApiResponse<Object>> getExamInfo(@RequestParam(name = "score", required = false) List<Integer> scores) {
		// 統計資料
		IntSummaryStatistics stat = scores.stream().mapToInt(Integer::intValue).summaryStatistics();
		// 利用 Collectors.partitioningBy 分組
		// key=true 及格分數 | key=false 不及格分數
		Map<Boolean, List<Integer>> resultMap = scores.stream()
				.collect(Collectors.partitioningBy(score -> score >= 60));
		Object data = Map.of(
				"最高分", stat.getMax(),
				"最低分", stat.getMin(),
				"平均", stat.getAverage(),
				"總分", stat.getSum(),
				"及格", resultMap.get(true),
				"不及格", resultMap.get(false));
		return ResponseEntity.ok(ApiResponse.success("計算成功", data));
				
	}
	
	/**
	 * 7. 多筆參數轉 Map
	 * name 書名(String), price 價格(Double), amount 數量(Integer), pub 出刊/停刊(Boolean)
	 * 路徑: /book?name=Math&price=12.5&amount=10&pub=true
	 * 路徑: /book?name=English&price=10.5&amount=20&pub=false
	 * 網址: http://localhost:8080/api/book?name=Math&price=12.5&amount=10&pub=true
	 * 網址: http://localhost:8080/api/book?name=English&price=10.5&amount=20&pub=false
	 * 讓參數自動轉成 key/value 的 Map 集合
	 * */
	@GetMapping(value = "/book", produces = "application/json;charset=utf-8")
	public ResponseEntity<ApiResponse<Object>> getBookInfo(@RequestParam Map<String, Object> bookMap) {
		System.out.println(bookMap);
		return ResponseEntity.ok(ApiResponse.success("回應成功", bookMap));
	}
	
	/** 8. 多筆參數轉指定 model 物件
	 * 路徑: 承上
	 * 網址: 承上
	 * */
	@GetMapping(value = "/book2", produces = "application/json;charset=utf-8")
	public ResponseEntity<ApiResponse<Book>> getBookInfo2(Book book) {
		book.setId(1); // 設定 id
		System.out.println(book);
		return ResponseEntity.ok(ApiResponse.success("回應成功2", book));
	}
	
	/**
	 * 9. 路徑參數
	 * 早期設計風格:
	 * 路徑: /book?id=1 得到 id = 1 的書
	 * 路徑: /book?id=3 得到 id = 3 的書
	 * 
	 * 現代設計風格(Rest):
	 * GET    /books   查詢所有書籍
	 * GET    /book/1  查詢單筆書籍
	 * POST   /book    新增書籍
	 * PUT    /book/1  修改單筆書籍
	 * DELETE /book/1  刪除單筆書籍
	 * 
	 * 路徑: /book/1 得到 id = 1 的書
	 * 路徑: /book/3 得到 id = 3 的書
	 * 網址: http://localhost:8080/api/book/1
	 * 網址: http://localhost:8080/api/book/3
	 * 
	 * @PathVariable(name = "id") Integer id
	 * 等價於
	 * @PathVariable(value = "id") Integer id
	 * 也等價於
	 * @PathVariable("id") Integer id
	 * ps: 就只是符合不同開發者的需要 !
	 * 
	 * */
	@GetMapping(value = "/book/{id}", produces = "application/json;charset=utf-8")
	//public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable(name = "id") Integer id) {
	public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Integer id) {
		// 書庫
		List<Book> books = List.of(
				new Book(1, "機器貓小叮噹", 12.5, 20, false),
				new Book(2, "老夫子", 10.5, 30, false),
				new Book(3, "好小子", 8.5, 40, true),
				new Book(4, "尼羅河的女兒", 14.5, 50, true)
		);
		// 根據 id 搜尋該筆書籍
		Optional<Book> optBook = books.stream()
									  .filter(book -> book.getId().equals(id)).findFirst();
		// 判斷是否有找到
		if(optBook.isEmpty()) {
			return ResponseEntity.badRequest().body(ApiResponse.error("查無此書"));
		}
		Book book = optBook.get(); // 取得書籍
		return ResponseEntity.ok(ApiResponse.success("查詢成功", book));
	}
	
	/**
	 * 請利用"路徑參數"設計出可以只顯示出刊或停刊的設計風格與方法
	 * 路徑: /book/pub/true
	 * 路徑: /book/pub/false
	 * 網址: http://localhost:8080/api/book/pub/true
	 * 網址: http://localhost:8080/api/book/pub/false
	 * */
	@GetMapping("/book/pub/{isPub}")
	public ResponseEntity<ApiResponse<List<Book>>> queryBook(@PathVariable Boolean isPub) {
		// 書庫
		List<Book> books = List.of(
				new Book(1, "機器貓小叮噹", 12.5, 20, false),
				new Book(2, "老夫子", 10.5, 30, false),
				new Book(3, "好小子", 8.5, 40, true),
				new Book(4, "尼羅河的女兒", 14.5, 50, true)
		);
		// 過濾出刊/停刊
		List<Book> queryBooks = books.stream()
									 .filter(book -> book.getPub().equals(isPub))
									 .toList();
		if(queryBooks.size() == 0) {
			return ResponseEntity.badRequest().body(ApiResponse.error("查無此書"));
		}
		return ResponseEntity.ok(ApiResponse.success("查詢成功:" + (isPub?"出刊":"停刊"), queryBooks));
	}
	
	
}
