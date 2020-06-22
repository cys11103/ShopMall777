package com.green.biz.admin;

public interface WorkerService {

	int workerCheck(String id, String pwd);

	WorkerVO getEmployee(String id);

}