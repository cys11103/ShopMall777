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
	public int workerCheck(String id, String pwd) {//ȭ�鿡�� id�� pwd�� �Է¹���
		int result = -1;
	
		//���̺��� ��ȸ�� pwd�� ����
		String pass = workerDao.workerCheck(id);
		if(pass != null) { // ����� ���̵� �������� ��ȸ�ؼ� ���� pwd�ϰ� �� ==> ����� ���̵� �ֱ� ��
			if(pass.equals(pwd)) { //1) �Է��� ��ȣ�� ���̺��� ��ȣ�� ��ġ
				result = 1;  //�������� �α���
			}else {
				result = 0; //2) ��ȣ ����ġ
			}
		}else {
			result = -1; // ���̵� �������� ����
		}
		return result;
		
	}

	/*
	 * ���̵� �������� ������ ���� ��ȸ
	 */
	@Override
	public WorkerVO getEmployee(String id) {
		
		return workerDao.getEmployee(id);
	}

}
