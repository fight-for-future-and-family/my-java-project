package com.hoolai.loader.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;

/**
 * 方法计时代理类
 * @author ruijie
 * @date 2013-10-11
 * @version V1.0
 * 
 * 没有看明白
 */
public class SchedulerTimeLogProxy implements InvocationHandler {

	private static Logger log = Logger.getLogger(SchedulerTimeLogProxy.class);
	
	private Object target;

	/**
	 * 绑定委托对象并返回一个代理类
	 * @param target
	 * @return
	 */
	public Object bind(Object target) {
		this.target = target;
		// 取得代理对象
		return Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		String name = getName(target);
		long start = System.currentTimeMillis();
		
		log.info(name + " start");
		
		// 执行方法
		result = method.invoke(target, args);
		
		long time = (System.currentTimeMillis() - start) / 1000;
		log.info("The running time of this " + name + " is : " + time + " seconds");
		return result;
	}
	
	private String getName(Object obj){
		return obj.getClass().getSimpleName().replaceFirst("Scheduler", "").toLowerCase();
	}
	
}
