package service;

import dao.EmployeeDAO;
import dao.EmployeeDAOOracle;
import exception.FindException;
import vo.Employee;

public class LogInServiceImpl implements LogInService{
	public EmployeeDAO dao = new EmployeeDAOOracle();
	
	public Employee logIn(String id, String pwd) throws FindException {
		try {
			Employee emp = dao.selectById(id); //���̵�ã��
			if(emp.getPassword().equals(pwd)) {
				return emp;
			}else {
				throw new FindException("�α��� ����");
			}
		}catch (FindException e) {
			// TODO: handle exception
			throw new FindException("�α��� ����");
		}
	  }
}
	

