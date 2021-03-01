package dao;

import java.util.List;

import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import exception.RemoveException;
import vo.DepartmentSchedule;

public interface DepartmentScheduleDAO {
	
	/**
	 * 부서일정을 등록한다
	 * @param ds 부서일정객체
	 */
	int insert(DepartmentSchedule ds) throws Exception;
	
	/**
	 * 부서일정번호에 해당하는 부서일정 반환한다
	 * @param no 부서일정번호
	 * @return 부서일정객체
	 */
	DepartmentSchedule selectByNo(int no) throws FindException;
	
	// 모든 리스트 출력하는거(메인에서 쓸거) 추가해야함
	
	List<DepartmentSchedule> selectById(String dept_id) throws FindException;
	
	/**
	 * 부서일정을 수정한다
	 * @param ds 변경될 내용이 담겨있는 부서일정객체
	 */
	void update(DepartmentSchedule ds) throws ModifyException;
	
	/**
	 * 부서일정을 삭제한다
	 * @param no 부서일정번호
	 */
	void delete(int no) throws RemoveException;
}
