package com.green.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.biz.product.ProductService;
import com.green.biz.product.dto.ProductVO;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/product_detail", method=RequestMethod.GET)
	//public String productDetailAction(@RequestParam(value="pseq", defaultValue="0", required=true) String pseq,
	//									Model model) {
	public String productDetailAction(ProductVO vo, Model model) {
		
		// 첫번째 방법 RequestParam을 이용한 방식
		//ProductVO pVO = new ProductVO();
		//pVO.setPseq(Integer.parseInt(pseq));
		
		//ProductVO vo = productService.getProduct(pVO);
		
		// 두번째 방법 ProductVO 커맨드 객체 이용하는 방법
		//ProductVO product = productService.getProduct(vo);
		ProductVO product = productService.getProduct(vo);
		
		model.addAttribute("productVO", product);
		
		return "product/productDetail";
	}
	
	@RequestMapping(value="/category", method=RequestMethod.GET)
	public String productKindAction(ProductVO vo, Model model) {
	//public String productKindAction(@RequestParam(value="kind") String kind, Model model) {
		/*
		ProductVO pVo = new ProductVO();
	
		pVo.setKind(kind);
		*/
	
		List<ProductVO> listProdByKind = productService.getProductListByKind(vo);
		
		model.addAttribute("productKindList", listProdByKind);
		
		return "product/productKind";
	}
	
	
}






