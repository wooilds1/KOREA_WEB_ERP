package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import exception.RemoveException;
import sql.MyConnection;
import vo.Department;
import vo.DepartmentSchedule;
import vo.Employee;

public class DepartmentScheduleDAOOracle implements DepartmentScheduleDAO {
	
	
	private int selectNo(Connection con) throws FindException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String selectNoSQL = "SELECT dept_schedule_seq.currval FROM dual";
		
		try {
			pstmt = con.prepareStatement(selectNoSQL);
			rs = pstmt.executeQuery();
			int no = 0;
			if(rs.next()) {
				no = rs.getInt("currval");
			}
			return no;
		} catch (SQLException e) {
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(null, pstmt, rs);
		}
		
	}
	
	
	
	@Override
	public int insert(DepartmentSchedule ds) throws Exception{	//객체생성 
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			throw new AddException("일정 추가 실패 (이유: " + e.getMessage()+")"); //AddException 에러 이유?
		}
	String insertScheduleSQL = 
			"insert into department_schedule("
			+ "dept_content, "
			+ "dept_id, "
			+ "dept_title, "
			+ "dept_task_start, "
			+ "dept_task_end, "
			+ "dept_schedule_no, "
			+ "emp_id) "
			+ "VALUES (?,?,?,?,?,dept_schedule_seq.nextval,?)";
	
		try {
			pstmt = con.prepareStatement(insertScheduleSQL);
			pstmt.setString(1, ds.getDept_content());
			pstmt.setString(2, ds.getDept_vo().getDept_id());
			pstmt.setString(3, ds.getDept_title());
			pstmt.setTimestamp(4, new java.sql.Timestamp((ds.getDept_task_start()).getTime())); 
			pstmt.setTimestamp(5, new java.sql.Timestamp((ds.getDept_task_end()).getTime()));  
			pstmt.setString(6, ds.getEmp_vo().getEmp_id()); //department의 has a 관계인 emp_vo를 이렇게 쓰는게 맞는지? 
			pstmt.executeUpdate(); //SQL실행문
			return selectNo(con);
		} catch (SQLException e) {
			throw new AddException(e.getMessage());
		} catch (FindException e){
			throw e;
		} catch (Exception e){
			throw new Exception("알 수 없는 오류");
		} finally {
			MyConnection.close(con, pstmt);
		}
	}

	
	@Override
	public DepartmentSchedule selectByNo(int no) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			throw new FindException("일정 검색 실패 (이유: "+e.getMessage()+")");
		}
		
		String selectByNoSQL = "select "
				+ "DEPT_SCHEDULE_NO, "
				+ "DEPT_TITLE, "
				+ "DEPT_CONTENT,"
				+ "DEPT_TASK_START, "
				+ "DEPT_TASK_END, "
				+ "EMP_ID, "
				+ "DEPT_ID "
				+ "from "
				+ "department_schedule "
				+ "where dept_schedule_no=?"; //단순 조회이기 때문에 pk(dept_schedule_no) 로하고 emp_id는 제외 
		
		try {
			pstmt = con.prepareStatement(selectByNoSQL);
			pstmt.setInt(1, no); //기존에는 setString인데 setInt를 써도 되는지? 
			rs = pstmt.executeQuery();
			if(rs.next()) { 
				int dept_schedule_no = rs.getInt("dept_schedule_no");
				String dept_schedule_title = rs.getString("dept_title");
				String dept_schedule_content = rs.getString("dept_content");
				Date dept_task_start = rs.getTimestamp("dept_task_start"); //getDate는 sql로 변환, 부서일정에도 시분초 포함  
				Date dept_task_end = rs.getTimestamp("dept_task_end");
				String emp_id = rs.getString("emp_id");
				String dept_id = rs.getString("dept_id");
				Employee e = new Employee();
				Department d = new Department();
				e.setEmp_id(emp_id); 
				d.setDept_id(dept_id);
				DepartmentSchedule ds = new DepartmentSchedule( //생성자로 넣거나, 
						dept_schedule_no, 
						dept_schedule_title, 
						dept_task_start, 
						dept_task_end,
						dept_schedule_content, 
						e,
						d);
				return ds;
			} else {                 //else는 department_schedule_no에 해당하는 일정을 찾지 못한경우 
				throw new FindException("조건에 해당하는 일정이 없습니다.");
			}
		} catch (SQLException se) {
			throw new FindException(se.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	
	@Override
	public List<DepartmentSchedule> selectById(String dept_id) throws FindException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			throw new FindException("일정 검색 실패 (이유: "+e.getMessage()+")");
		}
		
		String selectByIdSQL = "SELECT DEPT_SCHEDULE_NO, DEPT_TITLE, DEPT_CONTENT, DEPT_TASK_START, DEPT_TASK_END, e.EMP_ID, e.EMP_NAME, d.DEPT_NAME\r\n" + 
				"FROM DEPARTMENT_SCHEDULE ds JOIN EMPLOYEE e ON (ds.EMP_ID = e.EMP_ID)\r\n" + 
				"                            JOIN DEPARTMENT d ON (ds.DEPT_ID = d.DEPT_ID)\r\n" + 
				"WHERE ds.DEPT_ID = ?";
		
		try {
			pstmt = con.prepareStatement(selectByIdSQL);
			pstmt.setString(1, dept_id);
			rs = pstmt.executeQuery();
			List<DepartmentSchedule> list = new ArrayList<>();
			while(rs.next()) {
				int dept_schedule_no = rs.getInt("dept_schedule_no");
				String dept_title = rs.getString("dept_title");
				String dept_content = rs.getString("dept_content");
				Date dept_task_start = rs.getTimestamp("dept_task_start");
				Date dept_task_end = rs.getTimestamp("dept_task_end");
				String emp_id = rs.getString("emp_id");
				String emp_name = rs.getString("emp_name");
				String dept_name = rs.getString("dept_name");
				Department d = new Department(dept_id, dept_name);
				Employee e = new Employee();
				e.setEmp_id(emp_id);
				e.setName(emp_name);
				DepartmentSchedule ds = new DepartmentSchedule(dept_schedule_no, dept_title, dept_task_start, dept_task_end, dept_content, e, d);
				list.add(ds);
			}
			if(list.size()==0) {
				throw new FindException("일정이 하나도 없습니다");
			}
			return list;
		} catch (SQLException e) {
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	
	
	@Override
	public void delete(int no) throws RemoveException {
		DepartmentSchedule ds;
		try {
			ds = selectByNo(no);
		} catch (FindException e) {
			throw new RemoveException(e.getMessage()); //오류발생 
		}
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			throw new RemoveException("일정 삭제 실패 (이유: "+e.getMessage()+")");
		}
		
		String deleteSQL = "delete from department_schedule where dept_schedule_no=?";
		
		try {
			pstmt = con.prepareStatement(deleteSQL);
			pstmt.setInt(1, no);
			int rowcnt = pstmt.executeUpdate();
			if(rowcnt != 1) {
				throw new RemoveException("해당 팀 일정이 없습니다");
			}
		} catch(SQLException e) {
			throw new RemoveException(e.getMessage());
		} finally {
			MyConnection.close(con,pstmt);
		}
	}


	
	@Override
	public void update(DepartmentSchedule ds) throws ModifyException { //title은 null값일 경우 업데이트 안 됨, content는 상관없음 
		Connection con = null;
		PreparedStatement pstmt = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			throw new ModifyException("일정 삭제 실패 (이유: "+e.getMessage()+")");
		}
		
		Statement stmt = null;
		String updateSQL = "update department_schedule set ";
		String updateSQLSet = "";
		String updateSQL1 = "where dept_schedule_no='"+ds.getDept_schedule_no()+"'";
		
		try {
			stmt = con.createStatement();
			boolean flag = false;
			//dept_title 업데이트
			if(ds.getDept_title() != null && !ds.getDept_title().equals("")) {
				updateSQLSet = "dept_title='"+ds.getDept_title()+"' ";
				flag = true;
			}
			//dept_content 업데이트 
			if(ds.getDept_content() != null && !ds.getDept_content().equals("")) {
				if(flag) {
					updateSQLSet += ",";
				}
				updateSQLSet += "dept_content='"+ds.getDept_content()+"' ";
				flag = true;
			}
			//dept_task_start 업데이트
			if(ds.getDept_task_start() != null && !ds.getDept_task_start().equals("")) {
				if(flag) {
					updateSQLSet += ",";
				}
				updateSQLSet += "dept_task_start=TO_DATE('"+dateFormat.format(new java.sql.Timestamp((ds.getDept_task_start()).getTime()))+"', 'yyyy-MM-dd hh24:mi:ss') ";
				flag = true; //TO_DATE로 쓸떄는 뒤에 날짜, 시간형식을 지정해줘야 한다.
			}
			//dept_task_end 업데이트
			if(ds.getDept_task_end() != null && !ds.getDept_task_end().equals("")) {
				if(flag) {
					updateSQLSet += ",";
				}
				updateSQLSet += "dept_task_end=TO_DATE('"+dateFormat.format(new java.sql.Timestamp((ds.getDept_task_end()).getTime()))+"', 'yyyy-MM-dd hh24:mi:ss') ";
				flag = true;
			}
			
			if(flag) {
				System.out.println(updateSQL + updateSQLSet + updateSQL1);
				stmt.executeUpdate(updateSQL + updateSQLSet + updateSQL1);
			} else {
				throw new ModifyException("수정할 부서일정이 없습니다.");
			}
		} catch (SQLException e) {
			throw new ModifyException(e.getMessage());
		} finally {
			MyConnection.close(con, stmt);
		}
	}
}


















