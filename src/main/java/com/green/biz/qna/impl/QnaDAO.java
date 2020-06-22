package com.green.biz.qna.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.qna.QnaVO;

@Repository
public class QnaDAO {

	@Autowired
	private SqlSessionTemplate mybatis; //데이터를 가져옴
	
	public List<QnaVO> listQna(String id){ //id를 조건으로 하기 때문
		
		return mybatis.selectList("QnaDAO.listQna", id); //""는 매핑, id는 파라미터
	}
	
	public QnaVO getQna(int seq) {
		
		return mybatis.selectOne("QnaDAO.getQna", seq);
	}
	
	public void insertQna(QnaVO qnaVO) {
		
		mybatis.insert("QnaDAO.insertQna", qnaVO);
	}
	
	public List<QnaVO> listAllQna(){
		
		return mybatis.selectList("QnaDAO.listAllQna");
	}
	
	public void updateQna(QnaVO vo) {
		
		mybatis.update("QnaDAO.updateQna", vo);
	}
}
