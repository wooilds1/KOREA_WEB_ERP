package service;

import java.util.List;

import dao.AnnualLeaveDAO;
import dao.AnnualLeaveDAOOracle;
import dao.EmployeeScheduleDAO;
import dao.EmployeeScheduleDAOOracle;
import exception.FindException;
import exception.ModifyException;
import exception.RemoveException;
import vo.AnnualLeave;
import vo.EmployeeSchedule;

public class EmployeeScheduleServiceImpl implements EmployeeScheduleService {
	private EmployeeScheduleDAO dao = new EmployeeScheduleDAOOracle();
	private AnnualLeaveDAO adao = new AnnualLeaveDAOOracle();
	
	
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
	public AnnualLeave findAnnualLeaveById(String emp_id) throws FindException{
		return adao.selectById(emp_id);
	}
	
	
	
	@Override
	public void modify(EmployeeSchedule es) throws ModifyException{
		dao.update(es);

	}
	
	
	
	@Override
	public void modifyAnnualLeave(AnnualLeave a, int use_day) throws ModifyException{
		adao.update(a, use_day);
	}

	@Override
	public void remove(int no) throws RemoveException{
		dao.delete(no);
	}

}
