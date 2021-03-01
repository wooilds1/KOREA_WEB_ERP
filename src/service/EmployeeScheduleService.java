package service;

import java.util.List;

import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import exception.RemoveException;
import vo.EmployeeSchedule;

public interface EmployeeScheduleService {
	/**
	 * 사원일정을 등록한다
	 * @param es 사원일정객체
	 */
	int add(EmployeeSchedule es) throws Exception;
	
	/**
	 * 사원일정번호에 해당하는 사원일정을 찾는다
	 * @param no 사원일정번호
	 * @return 사원일정객체
	 */
	EmployeeSchedule findByNo(int no) throws FindException;
	
	List<EmployeeSchedule> findAllByIdAndStatus(String emp_id, int status) throws FindException;
	
	/**
	 * 사원일정을 수정한다
	 * @param es 변경될 사원일정객체
	 */
	void modify(EmployeeSchedule es) throws ModifyException;
	
	/**
	 * 접속한 사원의 사원번호와 일치하는 사원일정을 삭제한다
	 * @param es 개인일정객체(사원 id와 비교하기 위해)
	 * @param id 접속한 사원의 사원번호
	 */
//	void remove(EmployeeSchedule es, String id); ?????
	// int no -> EmployeeSchedule es 로 인자값 변경
	
	void remove(int no) throws RemoveException;
}
