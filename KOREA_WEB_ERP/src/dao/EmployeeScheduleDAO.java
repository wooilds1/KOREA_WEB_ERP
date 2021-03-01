package dao;

import java.util.List;

import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import exception.RemoveException;
import vo.EmployeeSchedule;

public interface EmployeeScheduleDAO {
	
	/**
	 * 개인일정을 등록한다
	 * @param es 개인일정객체
	 */
	int insert(EmployeeSchedule es) throws Exception;
	
	/**
	 * 개인일정번호에 해당하는 개인일정 반환한다
	 * @param no 개인일정번호
	 * @return 개인일정객체
	 */
	EmployeeSchedule selectByNo(int no) throws FindException;
	
	
	List<EmployeeSchedule> selectAllByIdAndStatus(String emp_id, int status) throws FindException;
	// 모든 리스트 출력하는거(메인에서 쓸거) 추가해야함
	
	/**
	 * 개인일정을 수정한다
	 * @param es 변경될 내용이 담겨있는 개인일정객체
	 */
	void update(EmployeeSchedule es) throws ModifyException;
	
	/**
	 * 개인일정을 삭제한다
	 * @param no 개인일정번호
	 */
	void delete(int no) throws RemoveException;
}
