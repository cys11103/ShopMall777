package com.green.biz.order.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.order.OrderVO;

@Repository
public class OrderDAO {
	
	@Autowired
	SqlSessionTemplate mybatis;
	
	public int selectMaxOseq() {
		return mybatis.selectOne("OrderDAO.selectMaxOseq");
	}
	
	//장바구니의 상품을 주문 테이블에 넣는다
	public void insertOrder(OrderVO vo) {
		System.out.println("mybatis로 insertOrder()기능 처리");
		
		mybatis.insert("OrderDAO.insertOrder", vo);
	}
	
	public void insertOrderDetail(OrderVO vo) {
		System.out.println("mybatis로 insertOrderDetail()기능 처리");
		
		mybatis.insert("OrderDAO.insertOrderDetail", vo);
	}
	
	//사용자별 주문내역을 조회한다
	public List<OrderVO> listOrderById(OrderVO vo){
		System.out.println("mybatis로 listOrderById()기능 처리");
		
		return mybatis.selectList("OrderDAO.listOrderById", vo);
	}
	
	//사용자 별 주문번호를 조회한다
	public List<Integer> selectSeqOrdering(String id){
		System.out.println("mybatis로 selectSeqOrdering()기능 처리");
		
		return mybatis.selectList("OrderDAO.selectSeqOrdering", id);
	}
	
	//주문 전체 조회
	//입력 매개변수 : value => 사용자명
	public List<OrderVO> listOrder(String value){
		System.out.println("mybatis로 listOrder()기능 처리");
		
		return mybatis.selectList("OrderDAO.listOrder", value);
	}
	
	//주문 상태 갱신
	public void updateOrderResult(int pseq) {
		System.out.println("mybatis로 updateOrderResult()기능 처리");
		
		mybatis.update("OrderDAO.updateOrderResult", pseq);
	}
}
