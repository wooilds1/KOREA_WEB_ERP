package dao;

import exception.FindException;
import exception.ModifyException;
import vo.AnnualLeave;

public interface AnnualLeaveDAO {
	
	AnnualLeave selectById(String emp_id) throws FindException;
	
	
	void update(AnnualLeave a, int use_day) throws ModifyException;
}
