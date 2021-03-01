package service;

import dao.EmployeeDAO;
import dao.EmployeeDAOOracle;
import exception.FindException;
import vo.Employee;

public class MyInfoServiceImpl implements MyInfoService {
	private EmployeeDAO dao = new EmployeeDAOOracle();
	
	
	@Override
	public Employee findById(String id) throws FindException {
		return dao.selectById(id);
	}

	@Override
	public void modify(Employee e) {
		// TODO Auto-generated method stub

	}

}
