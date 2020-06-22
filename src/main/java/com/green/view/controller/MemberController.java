package com.green.view.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.green.biz.member.AddressVO;
import com.green.biz.member.MemberService;
import com.green.biz.member.MemberVO;

@Controller
@SessionAttributes("loginUser")
public class MemberController {
	
	@Autowired 
	MemberService memberService;
	
	/*
	 * �α��� ȭ�� ǥ��
	 */
	@RequestMapping(value="/login_form", method=RequestMethod.GET)
	public String loginView() {
		
		return "member/login";
	}
	
	/*
	 * ����� �α��� ����
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(MemberVO vo, Model model) {
		
		// �����ͺ��̽����� ����� ���� Ȯ��
		MemberVO loginUser = memberService.loginMember(vo);
		if (loginUser != null) {	// ����ڰ� �����ϸ�
			model.addAttribute("loginUser",loginUser);
			
			return("redirect:/index");
		} else {
			return("member/login_fail");
		}
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(SessionStatus status) {
		/*
		HttpSession session = request.getSession();
		
		session.setAttribute("loginUser", null);
		session.invalidate();
		*/
		
		status.setComplete();
		
		return "redirect:/login_form";
	}

	/*
	 * ������� ȭ�� ���
	 */
	@RequestMapping(value="/contract", method=RequestMethod.GET)
	public String contractView() {
		
		return "member/contract";
	}
	
	/*
	 * ȸ�� ���� ȭ�� ���
	 */
	@RequestMapping(value="/join_form", method=RequestMethod.GET)
	public String joinView() {
		
		return "member/join";
	}
	
	@RequestMapping(value="/join_form", method=RequestMethod.POST)
	public String joinForm() {
		
		return "member/join";
	}
	
	/*
	 * ȸ������ ��û ó��
	 */
	@RequestMapping(value="/join",method=RequestMethod.POST)
	public String joinAction(@RequestParam(value="addr1") String addr1, 
							 @RequestParam(value="addr2") String addr2,
							 MemberVO mVo) {
		
		mVo.setAddress(addr1+addr2);
		memberService.insertMember(mVo);
		
		return "member/login";	// �α��� ���
		
	}
	
	/*
	 * ������� ���̵� ���� ��ȸ�ϴ� ȭ�� ���
	 */
	@RequestMapping(value="/id_check_form", method=RequestMethod.GET)
	public String idCheckView(@RequestParam(value="id", defaultValue="", required=false) String id,
							Model model) {
		
		model.addAttribute("id", id);
		
		return "member/idcheck";
	}
	
	/*
	 * ȭ���� id�� �޾Ƽ�, ���̵� �����ϴ��� �����ͺ��̽����� ��ȸ
	 * ����ڰ� ������ message = 1
	 * ����ڰ� ������ message = -1
	 * JSPȭ�� ȣ��:idcheck.jsp
	 */
	@RequestMapping(value="/id_check_form", method=RequestMethod.POST)
	public String idCheckAction(@RequestParam(value="id", defaultValue="", required=false)
								String id,
								Model model) {
		
		MemberVO vo = new MemberVO();
		vo.setId(id);
		
		MemberVO user = memberService.getMember(vo);
		
		if (user != null) { // ����ڰ� �̹� ����
			model.addAttribute("message", 1);
		} else {	// ����ڰ� ���� ���� ����. ���̵�� ��밡��
			model.addAttribute("message", -1);
		}
		
		model.addAttribute("id", id);
		
		return "member/idcheck";
	}
	
	@RequestMapping(value="/id_check_confirmed", method=RequestMethod.GET)
	public String idCheckConfirmed(MemberVO mVo, Model model) {
		
		model.addAttribute("reid", mVo.getId());
		
		return "member/join";
	}
	
	/*
	 * �����ȣ ��ȸâ�� ǥ��
	 */
	@RequestMapping(value="find_zip_num", method=RequestMethod.GET)
	public String findZipNumView() {
		
		return "member/findZipNum";
	}
	
	@RequestMapping(value="find_zip_num", method=RequestMethod.POST)
	public String findZipNumAction(AddressVO aVo, Model model) {
		System.out.println("���̸�="+aVo.getDong());
		
		List<AddressVO> addrList = memberService.selectAddressByDong(aVo);
		
		model.addAttribute("addressList", addrList);
		
		return "member/findZipNum";
	}
	
	@RequestMapping(value="/find_id_form", method=RequestMethod.GET)
	public String findIdView() {
		
		return "member/findIdAndPassword";
	}
	
	/*
	 * ����� �̸��� �̸��Ϸ� ���̵� ã�� �׼�
	 */
	@RequestMapping(value="/find_id", method=RequestMethod.GET)
	public String findIdAction(MemberVO vo, Model model) {
		
		MemberVO member= memberService.getMemberByNameAndEmail(vo);
		
		if (member != null) {	// ���̵� �����ϴ� ���
			model.addAttribute("message","1");
			model.addAttribute("id",member.getId());
		} else {
			model.addAttribute("message","-1");
		}
		
		return "member/findResult";
	}
	
	@RequestMapping(value="/find_password", method=RequestMethod.GET)
	public String findpasswordAction(MemberVO vo, Model model) {
		
		MemberVO member= memberService.findPassword(vo);
		
		if (member != null) {	// ���̵� �����ϴ� ���
			model.addAttribute("message","1");
			model.addAttribute("pwd",member.getId());
		} else {
			model.addAttribute("message","-1");
		}
		
		return "member/findResult";
	}
	
}










