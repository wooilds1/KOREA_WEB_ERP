package service;

import java.util.List;

import dao.EmployeeScheduleDAO;
import dao.EmployeeScheduleDAOOracle;
import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import exception.RemoveException;
import vo.EmployeeSchedule;

public class EmployeeScheduleServiceImpl implements EmployeeScheduleService {
	private EmployeeScheduleDAO dao = new EmployeeScheduleDAOOracle();
	
	
	@Override
	public int add(EmployeeSchedule es) throws Exception{
		return dao.insert(es);
	}

	@Override
	public EmployeeSchedule findByNo(int no) throws FindException {
		return dao.selectByNo(no);
	}

	@Override
	public List<EmployeeSchedule> findAllByIdAndStatus(String emp_id, int status) throws FindException {
		return dao.selectAllByIdAndStatus(emp_id, status);
	}

	@Override
	public void modify(EmployeeSchedule es) throws ModifyException{
		dao.update(es);

	}

	@Override
	public void remove(int no) throws RemoveException{
		dao.delete(no);
	}

}
