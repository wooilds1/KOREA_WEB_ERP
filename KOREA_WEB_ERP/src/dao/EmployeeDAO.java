package dao;

import exception.FindException;
import exception.ModifyException;
import vo.Employee;

public interface EmployeeDAO {
	
	/**
	 * 저장소에 아이디에 해당하는 사원을 반환한다
	 * @param id 사원번호
	 * @return 사원
	 * @throws FindException 해당 사원이 없으면 예외발생한다 **/
	Employee selectById(String id) throws FindException;
	
	/**
	 * 사원의 정보를 수정한다
	 * @param e 변경될 내용이 담겨있는 사원객체
	 * @return 변경된 고객객체
	 * @throws 수정실패시 예외발생한다
	 */
	Employee update(Employee e) throws ModifyException;
}
