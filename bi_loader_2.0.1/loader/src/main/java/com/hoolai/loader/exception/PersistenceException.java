package com.hoolai.loader.exception;

/**
 * 持久层异常
 * @author ruijie
 * @date 2013-11-21
 * @version V1.0
 */
public class PersistenceException extends BaseException {

	private static final long serialVersionUID = -3129307479183417068L;

	public PersistenceException(){
		super();
	}
	
	public PersistenceException(String str){
		super(str);
	}
	
	public PersistenceException(String message, Throwable cause){
		super(message, cause);
	}
	
	public PersistenceException(Throwable cause){
		super(cause);
	}
}
