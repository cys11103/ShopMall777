package com.green.biz.order;

import java.util.List;

public interface OrderService {

	public int selectMaxOseq();
	
	public int insertOrder(OrderVO vo);
	
	public void insertOrderDetail(OrderVO vo);
	
	public List<OrderVO> listOrderById(OrderVO vo);
	
	public List<Integer> selectSeqOrdering(String id);
	
	public List<OrderVO> listOrder(String name);
	
	public void updateOrderResult(int pseq);
}
