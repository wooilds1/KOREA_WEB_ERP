package service;

import java.util.List;

import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import exception.RemoveException;
import vo.DepartmentSchedule;

public interface DepartmentScheduleService {
	
	int add(DepartmentSchedule ds) throws Exception;
	
	DepartmentSchedule findByNo(int no);
	
	List<DepartmentSchedule> findById(String dept_id) throws FindException;
	
	void modify(DepartmentSchedule ds) throws ModifyException;
	
	void remove(int no) throws RemoveException;
}
