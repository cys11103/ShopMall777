package com.green.biz.qna.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.qna.QnaService;
import com.green.biz.qna.QnaVO;

@Service("qnaService")
public class QnaServiceImpl implements QnaService {

	@Autowired
	private QnaDAO qnaDao;
	
	@Override
	public List<QnaVO> listQna(String id) {
		
		return qnaDao.listQna(id);
	}

	@Override
	public QnaVO getQna(int seq) {
		
		return qnaDao.getQna(seq);
	}

	@Override
	public void insertQna(QnaVO qnaVO) {
		
		qnaDao.insertQna(qnaVO);
	}

	@Override
	public List<QnaVO> listAllQna(){
		
		return qnaDao.listAllQna();
	}
	
	@Override
	public void updateQna(QnaVO vo) {
		
		qnaDao.updateQna(vo);
	}
}
