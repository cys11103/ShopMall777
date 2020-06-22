package com.green.view.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.biz.member.MemberVO;
import com.green.biz.qna.QnaService;
import com.green.biz.qna.QnaVO;

@Controller //컨트롤러로 지정
public class QnaController {
	
	@Autowired //서비스 호출
	private QnaService qnaService;
	
	@RequestMapping(value="qna_list") //post, get 구분 없을 때  method 생략해줘도됨
	public String qnaList(HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser"); //사용자 확인
		
		if(loginUser == null) {
			return "member/login"; //로그인 페이지 호출
		}else {
			List<QnaVO> qnaList = qnaService.listQna(loginUser.getId());
			
			model.addAttribute("qnaList", qnaList);
			
			return "qna/qnaList";
		}
	}
	
	@RequestMapping(value="qna_view")
	public String qnaView(@RequestParam(value="qseq") int qseq, Model model, HttpSession session) {
		
		//로그인하는 부분
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser"); //사용자 확인
		
		if(loginUser == null) {
			return "member/login"; //로그인 페이지 호출
		}else {
			QnaVO qnaVO = qnaService.getQna(qseq); //화면에서 받은 qseq를 얻음, qnaVO 값으로 넘겨주는 것(qnaView에서 ${qnaVO.~})
			
			model.addAttribute("qnaVO", qnaVO);
			
			return "qna/qnaView";
		}
		
	}
	
	@RequestMapping(value="qna_write_form")
	public String qnaWriteView(HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser"); //사용자 확인
		
		if(loginUser == null) {
			return "member/login"; //로그인 페이지 호출
		}else {
			return "qna/qnaWrite";
		}
	}
	
	@RequestMapping(value="qna_write")
	public String qnaWrite(QnaVO qnaVO, HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser"); //사용자 확인
		
		if(loginUser == null) {
			return "member/login"; //로그인 페이지 호출
		}else {
			qnaVO.setId(loginUser.getId());
			
			qnaService.insertQna(qnaVO);
			
			return "redirect:qna_list"; //
		}
	}
}
