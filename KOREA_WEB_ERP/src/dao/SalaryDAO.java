package dao;

import java.util.Date;
import java.util.List;

import exception.FindException;

import vo.Salary;

public interface SalaryDAO {
	
	/** 검색된 사원번호로 시작지급일자부터 종료지급일자까지 건수조회
	 * @param start 시작row
	 * @param end   종료row
	 * @param id 사원번호
	 * @return 건수반환
	 * @throws FindException 조회된 데이터가 없을경우 반환
	 */
	int selectCntByTerm(Date start, Date end, String id) throws FindException;
	
	/**
	 * 
	 * @param start_date 급여검색시작월
	 * @param end_date 급여검색끝월
	 * @param currentPage 검색할 현재페이지
	 * @param cnt_per_page 검색할 페이지에 보여줄 급여목록수
	 * @param id 사원아이디
	 * @return
	 * @throws FindException
	 */
	public List <Salary>selectByTerm(Date start_date, 
									Date end_date, 
									int currentPage, 
									int cnt_per_page, 
									String id)throws FindException;
	
	
	
	
	/** 검색된 사원번호로 시작지급일자부터 종료지급일자까지 조회
	 * @param start_date 시작지급일자
	 * @param end_date  종료지급일자
	 * @param id 사원번호
	 * @return   List형식으로 Salary 데이터 변환 
	 * @throws FindException 조회된 데이터가 없을경우 반환
	 */
	List <Salary>selectByTerm(Date start, Date end, String id)throws FindException;
}