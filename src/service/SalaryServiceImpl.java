package service;

import java.util.Date;
import java.util.List;

import dao.SalaryDAO;
import dao.SalaryDAOOracle;
import exception.FindException;
import vo.Salary;

public class SalaryServiceImpl implements SalaryService {
	SalaryDAO dao = new SalaryDAOOracle();
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
	public List <Salary>findByTerm(Date start_date, 
									Date end_date, 
									int currentPage, 
									int cnt_per_page, 
									String id)throws FindException{
		List<Salary> page = dao.selectByTerm(start_date, end_date, currentPage, cnt_per_page, id);
		return page;
		
	}

	@Override
	public List<Salary> findByTerm(Date start, Date end, String id) throws FindException {
		List<Salary> term = dao.selectByTerm(start, end, id);
		return term;
	}

	@Override
	public int findCntByTerm(Date start, Date end, String id) throws FindException {
		return dao.selectCntByTerm(start, end, id);

	}

	
}
