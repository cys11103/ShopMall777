package com.green.biz.utils;

public class Criteria {
	private int pageNum; //현재 페이지 번호
	private int numPerPage; //페이지 당 출력 항목 수
	
	public Criteria() {
		this(1, 10); //기본값 : 1페이지, 항목수 = 10개
	}
	
	public Criteria(int pageNum, int numPerPage) {
		this.pageNum = pageNum;
		this.numPerPage = numPerPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		if(pageNum <= 0) {
		this.pageNum = 1;
		}else {
			this.pageNum = pageNum;
		}
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		if(numPerPage <= 0 || numPerPage > 50)
			this.numPerPage = 10;
		else
			this.numPerPage = numPerPage;
	}
	
	/*
	 * 각 페이지에서 시작하는 항목 번호를 반환한다.
	 */
	public int getPageStart() {
		return(this.pageNum-1) * this.numPerPage+1;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", numPerPage=" + numPerPage + "]";
	}
	
}
