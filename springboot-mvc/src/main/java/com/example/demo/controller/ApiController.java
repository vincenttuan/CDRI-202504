package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.BMI;
import com.example.demo.response.ApiResponse;

@RestController // 免去撰寫 @ResponseBody, 但若要透過 jsp 渲染則不適用
@RequestMapping("/api") // 以下路徑統一都有 URL 前綴 "/api"
public class ApiController {
	
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
	public ResponseEntity<ApiResponse<Object>> getAverage(@RequestParam("age") List<String> ages) {
		double avg = ages.stream().mapToInt(Integer::parseInt).average().orElseGet(() -> 0);
		Object map = Map.of("平均年齡", String.format("%.1f", avg));
		return ResponseEntity.ok(ApiResponse.success("計算成功", map));
	}
	
	
}
