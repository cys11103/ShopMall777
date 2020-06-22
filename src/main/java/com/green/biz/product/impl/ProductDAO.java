package com.green.biz.product.impl;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.product.ProductCommentVO;
import com.green.biz.product.SalesQuantity;
import com.green.biz.product.dto.ProductVO;
import com.green.biz.utils.Criteria;

@Repository
public class ProductDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	public ProductVO getProduct(ProductVO vo) {
		System.out.println("==> Mybatis로 getProduct() 기능 처리");
		
		return mybatis.selectOne("ProductDAO.getProduct", vo);
	}
	
	public List<ProductVO> getNewProductList() {
		System.out.println("==> Mybatis로 getNewProductList() 기능 처리");
		
		return mybatis.selectList("ProductDAO.getNewProductList");
	}
	
	public List<ProductVO> getBestProductList() {
		System.out.println("==> Mybatis로 getBestProductList() 기능 처리");
		
		return mybatis.selectList("ProductDAO.getBestProductList");
	}
	
	/*
	 * 카테고리별 조회
	 */
	public List<ProductVO> getProductListByKind(ProductVO vo) {
		System.out.println("==> Mybatis로 getProductListByKind() 기능 처리");
		
		return mybatis.selectList("ProductDAO.getProductListByKind", vo);
	}
	
	/*
	 * 총 상품 개수 조회
	 */
	public int countProductList(String name) {
		System.out.println("Mybatis로 countProductList() 기능 처리");
		
		return mybatis.selectOne("ProductDAO.countProductList", name);
	}
	
	/*
	 * 페이지별 상품 목록 조회
	 */
	public List<ProductVO> getListWithPaging(Criteria criteria, String key){
		System.out.println("Mybatis로 getListWithPaging() 기능 처리");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("criteria", criteria);
		map.put("key", key);
		
		
		return mybatis.selectList("ProductDAO.listWithPaging", map); //product-mapping에서 <select id="listWithPaging"로 되어 있음!! 동일하게 해야함!
	}
	
	
	/*
	 * 상품 목록
	 */
	public List<ProductVO> listProduct(String name){
		System.out.println("Mybatis로 listProduct() 기능 처리");
		
		return mybatis.selectList("ProductDAO.listProduct", name);
	}
	
	/*
	 * 상품 등록
	 */
	public void insertProduct(ProductVO vo) {
		System.out.println("Mybatis로 insertProduct() 기능 처리");
		
		mybatis.insert("ProductDAO.insertProduct", vo);
	}
	
	/*
	 * 상품정보 수정
	 */
	public void updateProduct(ProductVO vo) {
		System.out.println("Mybatis로 updateProduct() 기능 처리");
		
		mybatis.update("ProductDAO.updateProduct", vo);
	}
	
	/*
	 * 상품평 관련 메소드
	 */
	public List<ProductCommentVO> getCommentList(int pseq){
		System.out.println("Mybatis로 getCommentList() 기능처리");
		
		return mybatis.selectList("CommentDAO.getCommentList", pseq);
	}
	
	public void saveComment(ProductCommentVO commentVO) {
		System.out.println("Mybatis로 saveComment() 기능처리");
		
		mybatis.insert("CommentDAO.saveComment", commentVO);
	}
	
	public void updateComment(ProductCommentVO commentVO) {
		System.out.println("Mybatis로 updateComment()기능처리");
		
		mybatis.update("CommentDAO.updateComment", commentVO);
	}
	
	public void deleteComment(int commentSeq) {
		System.out.println("Mybatis로 deleteComment()기능처리");
		
		mybatis.delete("CommentDAO.deleteComment", commentSeq);
	}
	
	/*
	 * 상품별 판매 실적
	 */
	public List<SalesQuantity> getProductSales(){
		System.out.println("Mybatis로 getProductSales() 기능 처리");
		
		return mybatis.selectList("ProductDAO.getProductSales");
	}
}







