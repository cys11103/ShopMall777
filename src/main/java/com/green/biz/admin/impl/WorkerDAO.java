package com.green.biz.admin.impl;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.biz.admin.WorkerVO;

@Repository
public class WorkerDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	public String workerCheck(String id) {
		return mybatis.selectOne("WorkerDAO.workerCheck", id);
	}
	
	public WorkerVO getEmployee(String id) {
		return (WorkerVO)mybatis.selectOne("WorkerDAO.getEmployee", id);
	}
	
}
