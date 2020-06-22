package com.green.biz.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.biz.admin.WorkerService;
import com.green.biz.admin.WorkerVO;

@Service("adminService")
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	private WorkerDAO workerDao;
	
	@Override
	public int workerCheck(String id, String pwd) {//화면에서 id랑 pwd를 입력받음
		int result = -1;
	
		//테이블에서 조회된 pwd를 저장
		String pass = workerDao.workerCheck(id);
		if(pass != null) { // 사용자 아이디를 조건으로 조회해서 나온 pwd하고 비교 ==> 사용자 아이디가 있긴 함
			if(pass.equals(pwd)) { //1) 입력한 암호와 테이블의 암호가 일치
				result = 1;  //정상적인 로그인
			}else {
				result = 0; //2) 암호 불일치
			}
		}else {
			result = -1; // 아이디가 존재하지 않음
		}
		return result;
		
	}

	/*
	 * 아이디를 조건으로 관리자 정보 조회
	 */
	@Override
	public WorkerVO getEmployee(String id) {
		
		return workerDao.getEmployee(id);
	}

}
