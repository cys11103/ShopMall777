package com.green.biz.product;

import java.util.*;
import com.green.biz.product.dto.ProductVO;
import com.green.biz.utils.Criteria;

public interface ProductService {

	// ��ǰ��ȣ�� ��ǰ ��ȸ
	public ProductVO getProduct(ProductVO vo);
	
	// �Ż�ǰ ��ȸ
	public List<ProductVO> getNewProductList();
	
	// ����Ʈ ��ǰ ��ȸ
	public List<ProductVO> getBestProductList();
	
	// ��ǰ ������ ��ȸ
	public List<ProductVO> getProductListByKind(ProductVO vo);
	
	// ��ǰ ���� ��ȸ
	public int countProductList(String name);
	
	// ��ǰ��ü ���
	public List<ProductVO> listProduct(String name);
	
	//�������� ��� ��ȸ
	public List<ProductVO> getListWithPaging(Criteria criteria, String key);
	
	// ��ǰ ���
	public void insertProduct(ProductVO vo);
	
	// ��ǰ ���� ����
	public void updateProduct(ProductVO vo);
	
	public List<ProductCommentVO> getCommentList(int pseq);
	
	public void saveComment(ProductCommentVO commentVO);
	
	public void updateComment(ProductCommentVO commentVO);
	
	public void deleteComment(int commentSeq);
	
	public List<SalesQuantity> getProductSales();
}




