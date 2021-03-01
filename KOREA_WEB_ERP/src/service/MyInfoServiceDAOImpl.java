package service;

import java.util.Date;

import dao.EmployeeDAOOracle;
import dao.SalaryDAOOracle;
import exception.FindException;
import exception.ModifyException;
import vo.AnnualLeave;
import vo.Department;
import vo.Employee;

public class MyInfoServiceDAOImpl implements MyInfoService {
	private EmployeeDAOOracle dao;
	
	public MyInfoServiceDAOImpl() {
		dao = new EmployeeDAOOracle();
	}
	@Override
	public Employee findById(String id)  throws FindException{
			return dao.selectById(id);	
	}

	@Override
	public void modify(Employee e) throws ModifyException {
			dao.update(e);
	}

}
