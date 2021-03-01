package service;

import java.util.Date;
import java.util.List;

import dao.SalaryDAO;
import dao.SalaryDAOOracle;
import exception.FindException;
import vo.Salary;

public interface SalaryService {
	
	/**
	 * 
	 * @param start_date
	 * @param end_date
	 * @param currentPage
	 * @param cnt_per_page
	 * @param id
	 * @return
	 * @throws FindException
	 */
	public List <Salary>findByTerm(Date start_date, 
									Date end_date, 
									int currentPage, 
									int cnt_per_page, 
									String id)throws FindException;
	
	List<Salary> findByTerm(Date start, Date end, String id)throws FindException;
	
	int findCntByTerm(Date start, Date end, String id) throws FindException;
	
}
