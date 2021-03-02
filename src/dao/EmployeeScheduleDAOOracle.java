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
import vo.Employee;
import vo.EmployeeSchedule;

public class EmployeeScheduleDAOOracle implements EmployeeScheduleDAO {
	
	private int selectNo(Connection con) throws FindException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String selectNoSQL = "SELECT emp_schedule_seq.currval FROM dual";
		
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
	public int insert(EmployeeSchedule es) throws Exception{ // Exception 고쳐야함
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			throw new AddException("일정 추가 실패 : 이유=" + e.getMessage());
			
		}
		
		String insertSQL = "insert INTO employee_schedule(\r\n" + 
				"           emp_content,\r\n" + 
				"           emp_id,\r\n" + 
				"           emp_title,\r\n" + 
				"           emp_task_start,\r\n" + 
				"           emp_task_end,\r\n" + 
				"           emp_schedule_no,\r\n" + 
				"           emp_task_status)\r\n" + 
				"VALUES ( ?,\r\n" + 
				"?,\r\n" + 
				"?,\r\n" + 
				"?,\r\n" + 
				"?,\r\n" + 
				"emp_schedule_seq.nextval,\r\n" + 
				"?)";
		try {
			pstmt = con.prepareStatement(insertSQL);
			pstmt.setString(1, es.getEmp_content());
			pstmt.setString(2, es.getEmp_vo().getEmp_id());
			pstmt.setString(3, es.getEmp_title());
			pstmt.setTimestamp(4, new java.sql.Timestamp((es.getEmp_task_start()).getTime())); // timestamp 아니면 시분초 정확하게 표시 안됨..
			pstmt.setTimestamp(5, new java.sql.Timestamp((es.getEmp_task_end()).getTime()));
			pstmt.setInt(6, es.getEmp_task_status());
			pstmt.executeUpdate();
			return selectNo(con);
		} catch (SQLException e) {
			throw new AddException(e.getMessage());
		} catch (FindException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("알 수 없는 오류");
		} finally {
			MyConnection.close(con, pstmt);
		}
	}

	@Override
	public EmployeeSchedule selectByNo(int no) throws FindException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			throw new FindException("일정 검색 실패 : 이유=" + e.getMessage());
		}
		
		String selectByNoSQL = "SELECT EMP_SCHEDULE_NO, EMP_TITLE, EMP_CONTENT,\r\n" + 
				"EMP_ID, EMP_TASK_STATUS, EMP_TASK_START ,\r\n" + 
				"EMP_TASK_END\r\n" + 
				"FROM EMPLOYEE_SCHEDULE\r\n" + 
				"WHERE EMP_SCHEDULE_NO = ?";
		
		try {
			pstmt = con.prepareStatement(selectByNoSQL);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int sno = rs.getInt("emp_schedule_no");
				String title = rs.getString("emp_title");
				String content = rs.getString("emp_content");
				String id = rs.getString("emp_id");
				int status = rs.getInt("emp_task_status");
				Date start = rs.getTimestamp("emp_task_start");
				Date end = rs.getTimestamp("emp_task_end");
				Employee e = new Employee();
				e.setEmp_id(id);
				EmployeeSchedule es = new EmployeeSchedule(sno, title, 
						status, start, end, content, e);
				return es;
			} else {
				throw new FindException("일정 검색 실패!");			
			}
		} catch (SQLException e) {
			throw new FindException(e.getMessage());	
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	
//	@Override
//	public List<EmployeeSchedule> selectAllById(String emp_id) throws FindException{
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			con = MyConnection.getConnection();
//		} catch (Exception e) {
//			throw new FindException("일정 검색 실패 : 이유=" + e.getMessage());
//		}
//		
//		String selectByIdSQL = "SELECT emp_schedule_no, emp_task_status, e.emp_name, emp_title, emp_task_start, emp_task_end, emp_content\r\n" + 
//				"FROM employee_schedule es JOIN employee e ON (es.emp_id = e.emp_id)\r\n" + 
//				"WHERE e.emp_id = ?";
//		
//		try {
//			pstmt = con.prepareStatement(selectByIdSQL);
//			pstmt.setString(1, emp_id);
//			rs = pstmt.executeQuery();
//			List<EmployeeSchedule> list = new ArrayList<>();
//			while(rs.next()) {
//				int emp_schedule_no = rs.getInt("emp_schedule_no");
//				String emp_name = rs.getString("emp_name");
//				String emp_title = rs.getString("emp_title");
//				Date start = rs.getTimestamp("emp_task_start");
//				Date end = rs.getTimestamp("emp_task_end");
//				String emp_content = rs.getString("emp_content");
//				int status = rs.getInt("emp_task_status");
//				Employee e = new Employee();
//				e.setEmp_id(emp_id);
//				e.setName(emp_name);
//				EmployeeSchedule es = new EmployeeSchedule(emp_schedule_no, emp_title, status, start, end, emp_content, e);
//				list.add(es);
//			}
//			if(list.size()==0) {
//				throw new FindException("일정이 하나도 없습니다");
//			}
//			return list;
//		} catch (SQLException e) {
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//	}
	
	@Override
	public List<EmployeeSchedule> selectAllByIdAndStatus(String emp_id, int status) throws FindException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			throw new FindException("일정 검색 실패 : 이유=" + e.getMessage());
		}
		
		String selectByIdSQL = "SELECT emp_schedule_no, e.emp_name, emp_title, emp_task_start, emp_task_end, emp_content\r\n" + 
				"FROM employee_schedule es JOIN employee e ON (es.emp_id = e.emp_id)\r\n" + 
				"WHERE e.emp_id = ? AND es.emp_task_status = ?";
		
		try {
			pstmt = con.prepareStatement(selectByIdSQL);
			pstmt.setString(1, emp_id);
			pstmt.setInt(2, status);
			rs = pstmt.executeQuery();
			List<EmployeeSchedule> list = new ArrayList<>();
			while(rs.next()) {
				int emp_schedule_no = rs.getInt("emp_schedule_no");
				String emp_name = rs.getString("emp_name");
				String emp_title = rs.getString("emp_title");
				Date start = rs.getTimestamp("emp_task_start");
				Date end = rs.getTimestamp("emp_task_end");
				String emp_content = rs.getString("emp_content");
				Employee e = new Employee();
				e.setEmp_id(emp_id);
				e.setName(emp_name);
				EmployeeSchedule es = new EmployeeSchedule(emp_schedule_no, emp_title, status, start, end, emp_content, e);
				list.add(es);
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
	public void update(EmployeeSchedule es) throws ModifyException{
		Connection con = null;
		Statement stmt = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			throw new ModifyException("일정 수정 실패 : 이유=" + e.getMessage());
		}
		
		EmployeeSchedule oldes = null;
		try {
			 oldes = selectByNo(es.getEmp_schedule_no());
		} catch (FindException e1) {
			throw new ModifyException(e1.getMessage());
		}
		
		
		String updateSQL = "UPDATE EMPLOYEE_SCHEDULE SET ";
		String updateSQLSet = "";
		String updateSQL1 = "WHERE EMP_SCHEDULE_NO='" + es.getEmp_schedule_no() + "'";
		
		try {
			stmt = con.createStatement();
			boolean flag = false;
			if(oldes.getEmp_content() == null) {
				oldes.setEmp_content("");
			}
								
			if(!es.getEmp_content().equals(oldes.getEmp_content())) {
				updateSQLSet = "EMP_CONTENT='" + es.getEmp_content() +"' ";
				flag = true;
			}
			if(es.getEmp_title() != null && !es.getEmp_title().equals("") && !es.getEmp_title().equals(oldes.getEmp_title())) {
				if(flag) {
					updateSQLSet += ",";
				}
				updateSQLSet += "EMP_TITLE='" + es.getEmp_title() + "' ";
				flag = true;
			}
			if(es.getEmp_task_start() != null && es.getEmp_task_start().getTime() != oldes.getEmp_task_start().getTime()) {
				if(flag) {
					updateSQLSet += ",";
				}
				updateSQLSet += "EMP_TASK_START=TO_DATE('" 
								+ dateFormat.format(new java.sql.Timestamp((es.getEmp_task_start()).getTime()))
								+ "','YYYY-MM-DD HH24:mi:ss') ";
				flag = true;
			}
			if(es.getEmp_task_end() != null && es.getEmp_task_end().getTime() != oldes.getEmp_task_end().getTime()) {
				if(flag) {
					updateSQLSet += ",";
				}
				updateSQLSet += "EMP_TASK_END=TO_DATE('" 
								+ dateFormat.format(new java.sql.Timestamp((es.getEmp_task_end()).getTime()))
								+ "','YYYY-MM-DD HH24:mi:ss') ";
				flag = true;
			}
			if(es.getEmp_task_status() != 0 && es.getEmp_task_status() != oldes.getEmp_task_status()) {
				if(flag) {
					updateSQLSet += ",";
				}
				updateSQLSet += "EMP_TASK_STATUS='" + es.getEmp_task_status() + "' ";
				flag = true;
			}
			
			if(flag) {
				System.out.println(updateSQL + updateSQLSet + updateSQL1);
				stmt.executeUpdate(updateSQL + updateSQLSet + updateSQL1);
			} 
//			else {
//				throw new ModifyException("일정 수정 실패!");
//			}
		} catch (SQLException e) {
			throw new ModifyException(e.getMessage());
		} finally {
			MyConnection.close(con, stmt);
		}
	}

	@Override
	public void delete(int no) throws RemoveException{
		EmployeeSchedule es;
		try {
			es = selectByNo(no);
		} catch (FindException e1) {
			throw new RemoveException(e1.getMessage());
		}
		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			throw new RemoveException("일정 삭제 실패 : 이유=" + e.getMessage());
		}
		
		String deleteSQL = "DELETE\r\n" + 
				"FROM EMPLOYEE_SCHEDULE\r\n" + 
				"WHERE EMP_SCHEDULE_NO = ?";
		
		try {
			pstmt = con.prepareStatement(deleteSQL);
			pstmt.setInt(1, no);
			int rowcnt = pstmt.executeUpdate();
			if(rowcnt != 1) {
				throw new RemoveException("삭제실패 : 해당 일정 없음");
			}
		} catch (SQLException e) {
			throw new RemoveException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt);
		}
	}
}
