package com.green.view.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.biz.member.MemberVO;
import com.green.biz.order.CartService;
import com.green.biz.order.CartVO;
import com.green.biz.order.OrderService;
import com.green.biz.order.OrderVO;

@Controller
public class MyPageController {

	@Autowired
	CartService cartService; //불러오기
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping(value="/cart_insert")
	public String cartInsert(CartVO vo, Model model, HttpServletRequest request) { //loginUser정보가 session에 들어있음
		
		//세션에 저장된 사용자 정보를 얻어온다.
		MemberVO loginUser = (MemberVO)request.getSession().getAttribute("loginUser");
		
		if(loginUser == null) { //로그인이 안되어 있으면 로그인 화면 표시
			return "member/login"; //jsp 호출
		}else {
			vo.setId(loginUser.getId());
			
			cartService.insertCart(vo);
			
			return "redirect:cart_list"; //사용자 화면에서 Servlet으로 요청하는 문자열
		}
	}
	
	@RequestMapping(value="cart_list")
	public String cartList(Model model, HttpSession session) {
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		}else {
			List<CartVO> cartList = cartService.listCart(loginUser.getId());
			
			int totalPrice = 0;
			
			for(CartVO cartVO : cartList) {
				totalPrice += cartVO.getQuantity() * cartVO.getPrice2();
			}
			
			model.addAttribute("cartList", cartList); //"cartList"는 c:에서 item의 이름을 말한다
			model.addAttribute("totalPrice", totalPrice);
			
			return "mypage/cartList";
		}
	}
	
	@RequestMapping(value="cart_delete")
	public String cartDelete(@RequestParam(value="cseq") int[] cseq, Model model) {
		
		for(int i=0; i<cseq.length; i++) {
			cartService.deleteCart(cseq[i]);
		}
		
		return "redirect:cart_list";
	}
	
	@RequestMapping(value="/order_insert")
	public String orderInsert(OrderVO vo, Model model, HttpSession session) {

		//세션에 저장된 사용자 정보를 얻어온다.
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) { //로그인이 안되어 있으면 로그인 화면 표시
			return "member/login"; //jsp 호출
		}else {
			vo.setId(loginUser.getId());
			
			//주문 테이블에 주문내역을 저장한다.
			int oseq = orderService.insertOrder(vo);
			vo.setResult("1");
			model.addAttribute("oseq", oseq);
		}
		
		return "redirect:order_list";
	}
	
	@RequestMapping(value="/order_list")
	public String orderList(@RequestParam(value="oseq") int oseq, Model model, OrderVO order, HttpSession session) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) { //로그인이 안되어 있으면 로그인 화면 표시
			return "member/login"; //jsp 호출
		}else {
			order.setId(loginUser.getId());
			order.setOseq(oseq);
			order.setResult("1");
			
			List<OrderVO> orderList = orderService.listOrderById(order);
			
			int totalPrice = 0;
			
			for(OrderVO vo : orderList) {
				totalPrice += vo.getPrice2();
			}
			
			model.addAttribute("orderList", orderList);
			model.addAttribute("totalPrice", totalPrice); //"totalPrice"에 totalPrice를 넣어준다.
			
			return "mypage/orderList";
		}
	}
	
	@RequestMapping(value="/mypage")
	public String myPageView(OrderVO order, Model model, HttpSession session) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) { //로그인이 안되어 있으면 로그인 화면 표시
			return "member/login"; //jsp 호출
		}else {
			
			//orderDAO에서 selectSeqOrdering()을 호출하여 진행 중인 주문의 상세주문 번호 목록을 조회하여 배열에 저장
			List<Integer> oseqList = orderService.selectSeqOrdering(loginUser.getId());
			
			//사용자의 전체 주문내역 요약
			List<OrderVO> orderList = new ArrayList<OrderVO>();
			
			for(int oseq : oseqList) {
				order.setId(loginUser.getId());
				order.setOseq(oseq);
				order.setResult("1");
				
				//주문번호별 주문내역 조회
				List<OrderVO> listByseq = orderService.listOrderById(order);
				
				//각 주문별 내용 축약
				OrderVO vo = new OrderVO();
				vo.setIndate(listByseq.get(0).getIndate());
				vo.setOseq(listByseq.get(0).getOseq());
				if(listByseq.size() > 1) {
					vo.setPname(listByseq.get(0).getPname() + " 외" + (listByseq.size()-1)+"건");
				}else {
					vo.setPname(listByseq.get(0).getPname());
				}
				int totalPrice = 0;
				for(int i=0; i<listByseq.size(); i++) {
					totalPrice += listByseq.get(i).getPrice2();
				}
				vo.setPrice2(totalPrice);
				
				orderList.add(vo);
				
				model.addAttribute("title", "진행중인 주문내역");
				model.addAttribute("orderList", orderList);
			}
		}
		return "mypage/mypage";
	}
	
	@RequestMapping(value="order_detail")
	public String orderDetailView(OrderVO vo, Model model, HttpSession session) {
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) { //로그인이 안되어 있으면 로그인 화면 표시
			return "member/login"; //jsp 호출
		}else {
			vo.setId(loginUser.getId());
			vo.setResult("1");
			
			List<OrderVO> orderList = orderService.listOrderById(vo);
			
			//주문자 정보를 조립
			OrderVO orderDetail = new OrderVO();
			orderDetail.setIndate(orderList.get(0).getIndate());
			orderDetail.setOseq(orderList.get(0).getOseq());
			orderDetail.setMname(orderList.get(0).getMname());
			orderDetail.setResult(orderList.get(0).getResult());
			
			int totalPrice = 0;
			for(int i=0; i<orderList.size(); i++) {
				totalPrice += orderList.get(i).getPrice2();
			}
			model.addAttribute("orderDetail", orderDetail);
			model.addAttribute("totalPrice", totalPrice);
			model.addAttribute("orderList", orderList);
			model.addAttribute("title", "주문 상세 정보");
			
			return "mypage/orderDetail";
		}
	}
	
	@RequestMapping(value="order_all")
	public String orderAll(OrderVO order, Model model, HttpSession session) {
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) { //로그인이 안되어 있으면 로그인 화면 표시
			return "member/login"; //jsp 호출
		}else {
			
			List<Integer> oseqList = orderService.selectSeqOrdering(loginUser.getId());
			
			for(int oseq : oseqList) {
				OrderVO vo = new OrderVO();
				vo.setId(loginUser.getId());
				vo.setOseq(oseq);
				vo.setResult("1");
				
				List<OrderVO> orderListing = orderService.listOrderById(vo);
				
				//각 주문별 내용 요약
				OrderVO item = new OrderVO();
				item.setPname(item.getPname()+" 외"+orderListing.size()+"건");
				
				int totalPrice = 0;
				
				for(OrderVO orderVo: orderListing) {
					totalPrice += orderVo.getPrice2() * orderVo.getQuantity();
				}
				item.setPrice2(totalPrice);
				
				orderListing.add(vo);
				
				model.addAttribute("title", "총 주문 내역");
				model.addAttribute("orderListing", orderListing);
			}
		}
		return "mypage/";
	}
}









