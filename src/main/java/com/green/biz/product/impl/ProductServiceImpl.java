package com.green.biz.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.product.ProductCommentVO;
import com.green.biz.product.ProductService;
import com.green.biz.product.SalesQuantity;
import com.green.biz.product.dto.ProductVO;
import com.green.biz.utils.Criteria;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDAO pDao;
	
	@Override
	public ProductVO getProduct(ProductVO vo) {
		
		return pDao.getProduct(vo);
	}

	@Override
	public List<ProductVO> getNewProductList() {
		
		return pDao.getNewProductList();
	}

	@Override
	public List<ProductVO> getBestProductList() {
		
		return pDao.getBestProductList();
	}

	@Override
	public List<ProductVO> getProductListByKind(ProductVO vo) {
		
		return pDao.getProductListByKind(vo);
	}
	
	@Override
	public int countProductList(String name) {
		
		return pDao.countProductList(name);
	}
	
	@Override
	public List<ProductVO> getListWithPaging(Criteria criteria, String key){
		
		return pDao.getListWithPaging(criteria, key);
	}
	
	@Override
	public List<ProductVO> listProduct(String name){
		
		return pDao.listProduct(name);
	}
	
	@Override
	public void insertProduct(ProductVO vo) {
		
		pDao.insertProduct(vo);
	}
	@Override
	public void updateProduct(ProductVO vo) {
		
		pDao.updateProduct(vo);
	}
	
	@Override
	public List<ProductCommentVO> getCommentList(int pseq){
		
		return pDao.getCommentList(pseq);
	}
	
	@Override
	public void saveComment(ProductCommentVO commentVO) {
		
		pDao.saveComment(commentVO);
	}

	@Override
	public void updateComment(ProductCommentVO commentVO) {
		
		pDao.updateComment(commentVO);
	}
	
	@Override
	public void deleteComment(int commentSeq) {
		
		pDao.deleteComment(commentSeq);
	}
	
	@Override
	public List<SalesQuantity> getProductSales(){
		
		return pDao.getProductSales();
	}
}
