package service;

import java.sql.Timestamp;

import dao.CommuteDAO;
import dao.CommuteDAOOracle;
import dao.EmployeeDAOOracle;
import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import vo.Commute;
import vo.Employee;

public class MainPageServiceImpl implements MainPageService {

	private EmployeeDAOOracle dao = new EmployeeDAOOracle();
	private CommuteDAO cdao = new CommuteDAOOracle();
	
	@Override
	public void addCommute(Commute c) throws AddException {
		cdao.insert(c);
	}

	@Override
	public String findName(String id) throws FindException{
		Employee e = dao.selectById(id);
		return e.getName();
		
	}

	@Override
	public Timestamp findStartTime(String id) throws FindException{
		Commute c = cdao.selectById(id);
		return c.getStart_time();
	}

	@Override
	public Timestamp findEndTime(String id) throws FindException{
		Commute c = cdao.selectById(id);
		return c.getEnd_time();
	}


	@Override
	public void modifyCommute(Commute c) throws ModifyException{
		cdao.update(c);
	}

}
