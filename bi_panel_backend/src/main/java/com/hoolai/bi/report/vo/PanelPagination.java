package com.hoolai.bi.report.vo;

import com.hoolai.dao.pagination.Pagination;

public class PanelPagination extends Pagination{
	/**
	 * 当前页码
	 */
	private final int pageNo;
	
	private final int pageSize;
	
	private final int totalRecord;
	
	private final int totalPage;
	
	private final int beforeDisplayNum;
	
	private final int endDisplayNum;
	
	private final int nextPageNo;
	
	private final int prePageNo;
	
	private final boolean isPre;
	
	private final boolean isNext;
	
	private final boolean isEnd;

	public PanelPagination(int pageNo, int pageSize,int totalRecord,int beforeDisplayNum,int endDisplayNum) {
		super();
		this.pageNo = pageNo;
		this.pageSize=pageSize;
		this.totalRecord=totalRecord;
		if(this.totalRecord==0){
			this.totalPage=0;
		}else{
			this.totalPage = (this.totalRecord %  this.pageSize == 0) ? (this.totalRecord /  this.pageSize): (this.totalRecord /  this.pageSize + 1);
		}
		this.beforeDisplayNum=beforeDisplayNum;
		this.endDisplayNum=endDisplayNum;
		
		if(this.pageNo>1){
			this.prePageNo=this.pageNo-1;
			this.isPre=true;
		}else{
			this.prePageNo=1;
			this.isPre=false;
		}
		if(this.pageNo<this.totalPage){
			this.nextPageNo=this.pageNo+1;
			this.isNext=true;
			this.isEnd=false;
		}else{
			this.nextPageNo=this.totalPage;
			this.isNext=false;
			this.isEnd=true;
		}
	}
	
	public PanelPagination(int pageNo, int pageSize,int totalRecord){
		this(pageNo, pageSize,totalRecord, 3, 5);
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getBeforeDisplayNum() {
		return beforeDisplayNum;
	}

	public int getEndDisplayNum() {
		return endDisplayNum;
	}

	public int getNextPageNo() {
		return nextPageNo;
	}

	public int getPrePageNo() {
		return prePageNo;
	}

	public boolean isPre() {
		return isPre;
	}

	public boolean isNext() {
		return isNext;
	}

	public boolean isEnd() {
		return isEnd;
	}
	
}
