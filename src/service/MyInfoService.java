package service;

import exception.FindException;
import exception.ModifyException;
import vo.Employee;

public interface MyInfoService {
	
	/** 아이디로 사원정보 조회
	 * @param id
	 * @return  Employee
	 * @throws FindException
	 */
	Employee findById(String id) throws FindException; 
	
	/**  사원정보 수정
	 * @param e
	 * @throws ModifyException
	 */
	void modify(Employee e) throws ModifyException;
}
