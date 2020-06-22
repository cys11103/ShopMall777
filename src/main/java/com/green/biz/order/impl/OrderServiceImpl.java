package com.green.biz.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.order.CartService;
import com.green.biz.order.CartVO;
import com.green.biz.order.OrderService;
import com.green.biz.order.OrderVO;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired  //OrderDAO ȣ��
	private OrderDAO orderDao; 
	
	@Autowired
	private CartService cartService;
	
	@Override
	public int selectMaxOseq() {
		
		return orderDao.selectMaxOseq();
	}

	/*
	 * ���ϰ� : oseq - �ֹ��Ϸù�ȣ
	 */
	@Override
	public int insertOrder(OrderVO vo) {
		
		int oseq = selectMaxOseq(); // �ֹ���ȣ�� �Ҵ�޴´�.(order ������ �Ҵ����)
		
		vo.setOseq(oseq);
		
		//�ֹ� ���̺� �ֹ���ȣ�� ���̵� �����Ѵ�.
		orderDao.insertOrder(vo);
		
		//��ٱ��Ͽ��� ����� �����´�.
		List<CartVO> cartList = cartService.listCart(vo.getId());
		
		//��ٱ��Ͽ� �ִ� �ֹ��� ������ order_detail ���̺� �����Ѵ�.
		//�ʿ��׸�: oseq, pseq, quantity
		for(CartVO cartVO : cartList) {
			System.out.println("��ٱ��� ����:"+cartVO);
			
			OrderVO order = new OrderVO();
			order.setOseq(oseq); //�ֹ���ȣ ����
			order.setPseq(cartVO.getPseq());
			order.setQuantity(cartVO.getQuantity());
			
			insertOrderDetail(order);
			
			//cart ���̺� �ֹ�ó���� �Ϸ�� �����Ѵ�.
			cartService.updateCart(cartVO.getCseq());
		}
		
		return oseq;
	}

	@Override
	public void insertOrderDetail(OrderVO vo) {
		
		orderDao.insertOrderDetail(vo);
	}

	@Override
	public List<OrderVO> listOrderById(OrderVO vo) {
		
		return orderDao.listOrderById(vo);
	}

	@Override
	public List<Integer> selectSeqOrdering(String id) {
		
		return orderDao.selectSeqOrdering(id);
	}
	
	@Override
	public List<OrderVO> listOrder(String name){
		
		return orderDao.listOrder(name);
	}
	
	@Override
	public void updateOrderResult(int pseq) {
		
		orderDao.updateOrderResult(pseq);
	}
	

}
