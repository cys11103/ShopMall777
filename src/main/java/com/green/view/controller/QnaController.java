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

@Controller //��Ʈ�ѷ��� ����
public class QnaController {
	
	@Autowired //���� ȣ��
	private QnaService qnaService;
	
	@RequestMapping(value="qna_list") //post, get ���� ���� ��  method �������൵��
	public String qnaList(HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser"); //����� Ȯ��
		
		if(loginUser == null) {
			return "member/login"; //�α��� ������ ȣ��
		}else {
			List<QnaVO> qnaList = qnaService.listQna(loginUser.getId());
			
			model.addAttribute("qnaList", qnaList);
			
			return "qna/qnaList";
		}
	}
	
	@RequestMapping(value="qna_view")
	public String qnaView(@RequestParam(value="qseq") int qseq, Model model, HttpSession session) {
		
		//�α����ϴ� �κ�
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser"); //����� Ȯ��
		
		if(loginUser == null) {
			return "member/login"; //�α��� ������ ȣ��
		}else {
			QnaVO qnaVO = qnaService.getQna(qseq); //ȭ�鿡�� ���� qseq�� ����, qnaVO ������ �Ѱ��ִ� ��(qnaView���� ${qnaVO.~})
			
			model.addAttribute("qnaVO", qnaVO);
			
			return "qna/qnaView";
		}
		
	}
	
	@RequestMapping(value="qna_write_form")
	public String qnaWriteView(HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser"); //����� Ȯ��
		
		if(loginUser == null) {
			return "member/login"; //�α��� ������ ȣ��
		}else {
			return "qna/qnaWrite";
		}
	}
	
	@RequestMapping(value="qna_write")
	public String qnaWrite(QnaVO qnaVO, HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser"); //����� Ȯ��
		
		if(loginUser == null) {
			return "member/login"; //�α��� ������ ȣ��
		}else {
			qnaVO.setId(loginUser.getId());
			
			qnaService.insertQna(qnaVO);
			
			return "redirect:qna_list"; //
		}
	}
}
