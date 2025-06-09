package com.example.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

// 動態代理
// 可以代理任何物件
public class DynProxy {
	// 被代理的物件
	private Object object;
	
	public DynProxy(Object object) {
		this.object = object;
	}
	
	// 取得代理物件
	public Object getProxy() {
		Object proxyObj = null;
		
		// ClassLoader loader => 類別載入器
        // Class<?>[] interfaces => 被代理物件所實作的介面
        // InvocationHandler handler => 處理代理的實現
		
		proxyObj = Proxy.newProxyInstance(null, null, null);
		return proxyObj;
	}
}
