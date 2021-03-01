package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import sql.MyConnection;
import vo.Commute;
import vo.Employee;

public class CommuteDAOOracle implements CommuteDAO  {

	@Override
	//출근시간을 저장한다
	public void insert(Commute c) throws AddException {
		Connection con = null;
			
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddException("출근시간 저장실패");
		}
		PreparedStatement pstmt = null;
		String insertSQL ="INSERT INTO COMMUTE(emp_id, start_time)\r\n" + "VALUES(?,?)";
		try {
			pstmt = con.prepareStatement(insertSQL);
			//pstmt.setString(1, c.emp_vo.emp_id);
			pstmt.setString(1, c.getEmp_vo().getEmp_id());
			//pstmt.setTimestamp(2, c.start_time);
			pstmt.setTimestamp(2, c.getStart_time());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			if(e.getErrorCode() == 1) {
				throw new AddException("이미 존재합니다");
			}
			e.printStackTrace();
		}finally {
			MyConnection.close(con, pstmt);
		}
	}
	
	//사원번호에 해당하는 출퇴근시간을 반환한다
	public Commute selectById(String id) throws FindException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = MyConnection.getConnection();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		String selectStart_EndTimeSQL = "SELECT *\r\n" + 
				"FROM commute\r\n" + 
				"WHERE emp_id = ? AND TO_CHAR(start_time, 'yy/mm/dd') = TO_CHAR(SYSTIMESTAMP, 'yy/mm/dd')";
		
		try {
			pstmt = con.prepareStatement(selectStart_EndTimeSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			Timestamp start_time=null;
			Timestamp end_time=null;
			Employee emp_vo=null;
			String emp_id=null;
			if(rs.next()) {
				start_time = rs.getTimestamp("start_time");
				end_time = rs.getTimestamp("end_time");
				emp_vo = new Employee();
				emp_id = rs.getString("emp_id");
				emp_vo.getEmp_id();
				//emp_vo.emp_id = emp_id;
				
//				System.out.println("사원번호:"+emp_id);
//				System.out.println("출근시간:"+start_time);
//				System.out.println("퇴근시간:"+end_time);
				
				
			} //else {
			 //	throw new FindException("출퇴근시간이 없습니다");
			 //}
			return new Commute(start_time, end_time, emp_vo);

		} catch (SQLException e) {
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
		
	}

//	@Override
	//퇴근시간을 수정한다
	public void update(Commute c) throws ModifyException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new ModifyException(e1.getMessage());
		}
		
		String updateCommuteSQL ="UPDATE COMMUTE\r\n" + 
				"SET end_time = systimestamp\r\n" + 
				"WHERE emp_id = ?  AND end_time IS NULL";
		
	
			try {
				pstmt = con.prepareStatement(updateCommuteSQL);//sql문 실행
//				pstmt.setString(1, c.emp_vo.emp_id);
				pstmt.setString(1, c.getEmp_vo().getEmp_id());
				
				pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ModifyException(e.getMessage());
			}finally {
				MyConnection.close(con, pstmt);
			}
				
				
				
	}
}
