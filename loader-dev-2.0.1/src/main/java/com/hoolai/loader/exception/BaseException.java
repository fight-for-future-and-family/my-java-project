package com.hoolai.loader.exception;

/**
 * 基本异常类
 * @author ruijie
 * @date 2013-11-21
 * @version V1.0
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 42629348300604028L;

	public BaseException(){
		super();
	}
	
	public BaseException(String str){
		super(str);
	}
	
	public BaseException(String message, Throwable cause){
		super(message, cause);
	}
	
	public BaseException(Throwable cause){
		super(cause);
	}
}
