package dao;

import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import vo.Commute;

public interface CommuteDAO {
	/**
	 * 저장소에 출퇴근 정보를 저장한다. 단, 출근을 할때 저장된다
	 * @param c 출퇴근객체
	 * @throws AddException 
	 */
	void insert(Commute c) throws AddException;
	
	
	/**
	 * 출근시간과 퇴근시간을 반환한다
	 * @param id
	 * @return 출근시간과 퇴근시간을 반환
	 * @throws id가 없을시 FindException 에러 발생
	 */
	Commute selectById(String id) throws FindException;

	
	/**
	 * 저장소의 출퇴근 정보를 수정한다. 단, 퇴근할 때 수정된다
	 * @param c 출퇴근객체
	 * @throws ModifyException 
	 */
	void update(Commute c) throws ModifyException;
}
