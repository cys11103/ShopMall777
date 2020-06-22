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
	
	//��ٱ����� ��ǰ�� �ֹ� ���̺� �ִ´�
	public void insertOrder(OrderVO vo) {
		System.out.println("mybatis�� insertOrder()��� ó��");
		
		mybatis.insert("OrderDAO.insertOrder", vo);
	}
	
	public void insertOrderDetail(OrderVO vo) {
		System.out.println("mybatis�� insertOrderDetail()��� ó��");
		
		mybatis.insert("OrderDAO.insertOrderDetail", vo);
	}
	
	//����ں� �ֹ������� ��ȸ�Ѵ�
	public List<OrderVO> listOrderById(OrderVO vo){
		System.out.println("mybatis�� listOrderById()��� ó��");
		
		return mybatis.selectList("OrderDAO.listOrderById", vo);
	}
	
	//����� �� �ֹ���ȣ�� ��ȸ�Ѵ�
	public List<Integer> selectSeqOrdering(String id){
		System.out.println("mybatis�� selectSeqOrdering()��� ó��");
		
		return mybatis.selectList("OrderDAO.selectSeqOrdering", id);
	}
	
	//�ֹ� ��ü ��ȸ
	//�Է� �Ű����� : value => ����ڸ�
	public List<OrderVO> listOrder(String value){
		System.out.println("mybatis�� listOrder()��� ó��");
		
		return mybatis.selectList("OrderDAO.listOrder", value);
	}
	
	//�ֹ� ���� ����
	public void updateOrderResult(int pseq) {
		System.out.println("mybatis�� updateOrderResult()��� ó��");
		
		mybatis.update("OrderDAO.updateOrderResult", pseq);
	}
}
