package com.green.biz.member.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.member.AddressVO;
import com.green.biz.member.MemberService;
import com.green.biz.member.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDao;
	
	@Override
	public MemberVO getMember(MemberVO vo) {
		// TODO Auto-generated method stub
		return memberDao.getMember(vo.getId());
	}
	
	@Override
	public MemberVO loginMember(MemberVO vo) {
		// TODO Auto-generated method stub
		return memberDao.loginMember(vo);
	}

	@Override
	public void insertMember(MemberVO vo) {
		
		memberDao.insertMember(vo);
	}

	@Override
	public List<AddressVO> selectAddressByDong(AddressVO vo) {
		
		return memberDao.selectAddressByDong(vo);
	}
	
	@Override
	public MemberVO getMemberByNameAndEmail(MemberVO vo) {
		
		return memberDao.getMemberByNameAndEmail(vo);
	}
	
	@Override
	public MemberVO findPassword(MemberVO vo) {
		
		return memberDao.findPassword(vo);
	}
	
	@Override
	public List<MemberVO> listMember(String name){
		
		return memberDao.listMember(name);
	}
}



