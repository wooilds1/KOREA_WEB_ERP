package service;

import exception.FindException;
import vo.Employee;

public interface LogInService {
	
	Employee logIn(String id, String pwd) throws FindException;
}
