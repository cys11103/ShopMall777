package com.green.view.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.green.biz.member.MemberVO;
import com.green.biz.product.ProductCommentVO;
import com.green.biz.product.ProductService;

//결과 데이터를 전송하는 콘트롤러
@RestController
public class CommentController {

	@Autowired
	ProductService productService;
	
	@RequestMapping(value="save_comment", method=RequestMethod.POST)
	@ResponseBody
	public String saveComment(ProductCommentVO commentVO, HttpSession session) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		if(loginUser == null) {
			
			return ("fail");
		}else {
			String userId = loginUser.getId();
			
			commentVO.setWriter(userId);
			
			productService.saveComment(commentVO);
			
			return "success";
		}
	}
	
	@RequestMapping(value="/comment_list", method=RequestMethod.GET)
	public List<ProductCommentVO> getCommentList(ProductCommentVO commentVo){
		
		int pseq = commentVo.getPseq();
		System.out.println("상품번호="+pseq);
		
		List<ProductCommentVO> commentList = productService.getCommentList(pseq);
		
		return commentList;
	}
}
