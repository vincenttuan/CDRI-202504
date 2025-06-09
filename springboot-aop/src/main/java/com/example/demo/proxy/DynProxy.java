package com.example.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

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
		ClassLoader loader = object.getClass().getClassLoader();
		
        // Class<?>[] interfaces => 被代理物件所實作的介面
		Class<?>[] interfaces = object.getClass().getInterfaces();
		
        // InvocationHandler handler => 處理代理的實現
		InvocationHandler handler = (Object proxy, Method method, Object[] args) -> {
			Object result = null;
			// 前置通知:
			MyAspect.before(method, args);
			// 執行業務方法
			try {
				result = method.invoke(object, args); // object: 被代理的物件, args: 方法參數
			} catch (Exception e) {
				// 例外通知:
				MyAspect.throwing(method, args, e);
			} finally {
				// 後置通知:
				MyAspect.end(method, args);
			}
			
			return result;
		};
		proxyObj = Proxy.newProxyInstance(loader, interfaces, handler);
		return proxyObj;
	}
}
