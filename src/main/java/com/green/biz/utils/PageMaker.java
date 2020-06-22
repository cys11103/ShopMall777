package com.green.biz.utils;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/*
 * 화면에 페이지 정보를 표시하기 위한 클래스
 */
public class PageMaker {
	private Criteria cri; // page번호, 페이지당 항목 수
	
	private int totalCount; // 전체 게시글의 수
	private int startPage;  // 시작 페이지 번호
	private int endPage;    // 끝 페이지 번호
	private boolean prev;   // 이전 페이지 유무 저장
	private boolean next;   // 다음 페이지 유무 저장
	
	private int displayPageNum = 10; // 화면 하단에 보이는 페이지의 개수
	private int realEndPage;    // 전체 항목수에 따른 실제 끝 페이지 저장
	
	//최초로 페이지 정보 설정하는 메소드
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		fieldInit();
	}
	
	public void fieldInit() {
		// 끝 페이지 계산
		endPage = (int)(Math.ceil(cri.getPageNum() / (double)displayPageNum) * displayPageNum); // ceil : 올림
		startPage = (endPage - displayPageNum) + 1;
		realEndPage = (int)(Math.ceil(totalCount / (double)cri.getNumPerPage()));
		
		// 위에서 계산한 실제 끝 페이지(realEndPage)에 따라서 endPage를 수정
		if(endPage > realEndPage) {
			endPage = realEndPage;
		}
		
		prev = startPage == 1 ? false : true; //1페이지 일 경우 '이전' 버튼이 표시 안됨
		next = endPage * cri.getNumPerPage() > totalCount ? false : true;
	}

	/*
	 * 입력 매개변수:
	 * 		page - 표시할 페이지 번호
	 * 출력 :
	 * 		?pageNum=3&numPerPage=10
	 */
	public String makeQuery(int page) {
		UriComponents uriComp = UriComponentsBuilder.newInstance().queryParam("pageNum", page).queryParam("numPerPage", cri.getNumPerPage()).build();
		
		return uriComp.toUriString();
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public int getRealEndPage() {
		return realEndPage;
	}

	public void setRealEndPage(int realEndPage) {
		this.realEndPage = realEndPage;
	}

	public int getTotalCount() {
		return totalCount;
	}
	
	@Override
	public String toString() {
		return "PageMaker [cri=" + cri + ", totalCount=" + totalCount + ", startPage=" + startPage + ", endPage="
				+ endPage + ", prev=" + prev + ", next=" + next + ", displayPageNum=" + displayPageNum
				+ ", realEndPage=" + realEndPage + "]";
	}
}
