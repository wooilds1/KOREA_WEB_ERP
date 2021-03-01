package service;

import java.util.List;

import dao.DepartmentScheduleDAO;
import dao.DepartmentScheduleDAOOracle;
import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import exception.RemoveException;
import vo.DepartmentSchedule;

public class DepartmentScheduleServiceImpl implements DepartmentScheduleService {
	private DepartmentScheduleDAO dao = new DepartmentScheduleDAOOracle();
	
	
	@Override
	public int add(DepartmentSchedule ds) throws Exception{
		return dao.insert(ds);
	}

	@Override
	public DepartmentSchedule findByNo(int no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DepartmentSchedule> findById(String dept_id) throws FindException {
		return dao.selectById(dept_id);
	}

	@Override
	public void modify(DepartmentSchedule ds) throws ModifyException{
		dao.update(ds);

	}

	@Override
	public void remove(int no) throws RemoveException{
		dao.delete(no);
	}

}
