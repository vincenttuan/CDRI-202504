package com.example.demo.proxy;

public class CalcProxy implements Calc {
	
	private Calc calc;
	
	public CalcProxy(Calc calc) {
		this.calc = calc;
	}
	
	@Override
	public Integer add(Integer x, Integer y) {
		// 前置通知: 驗證 x, y 不可以是 null
		if(x == null || y == null) {
			System.out.println("x, y 請放入數字");
			return null;
		}
		// 調用業務邏輯
		Integer result = calc.add(x, y);
		
		return result;
	}

	@Override
	public Integer div(Integer x, Integer y) {
		// TODO Auto-generated method stub
		return null;
	}

}
