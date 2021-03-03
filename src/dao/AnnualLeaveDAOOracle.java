package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.FindException;
import exception.ModifyException;
import sql.MyConnection;
import vo.AnnualLeave;

public class AnnualLeaveDAOOracle implements AnnualLeaveDAO {

	
	@Override
	public AnnualLeave selectById(String emp_id) throws FindException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		}
		
		String selectByIdSQL = "SELECT APPLY_YEAR, MAX_DAY, USED_DAY\r\n" + 
				"FROM ANNUAL_LEAVE\r\n" + 
				"WHERE EMP_ID = ? AND APPLY_YEAR = TO_CHAR(SYSDATE, 'YYYY')";
         
      try {
         pstmt = con.prepareStatement(selectByIdSQL);
         pstmt.setString(1, emp_id);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {     
            String apply_year = rs.getString("apply_year");
            int max_day = rs.getInt("max_day");
            int used_day = rs.getInt("used_day");
            
            AnnualLeave a = new AnnualLeave(emp_id, apply_year, max_day, used_day);
            return a;
         }else {
            throw new FindException("아이디에 해당 연차가 없습니다.");
         }
      }catch(SQLException e1) {
         throw new FindException(e1.getMessage());
      }finally {
         MyConnection.close(con, pstmt, rs);
      }
	}
	
	
	
	
	@Override
	public void update(AnnualLeave a, int use_day) throws ModifyException{
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = MyConnection.getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new ModifyException(e1.getMessage());//예외를떠넘깁시다
		}
		Statement stmt = null;
		String modifySQL = "UPDATE annual_leave SET ";//경우에따라다르게 처리하도록
		String modifySQLSET = "";
		String modifySQL1 = "WHERE emp_id='"+a.getEmp_id()+"' AND APPLY_YEAR=TO_CHAR(SYSDATE, 'YYYY')";
		
		try {
			stmt = con.createStatement();
			modifySQLSET = "used_day='" + (a.getUsed_day() - use_day) + "'";
			System.out.println(modifySQL+modifySQLSET+modifySQL1);
			stmt.execute(modifySQL+modifySQLSET+modifySQL1);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ModifyException(e1.getMessage());
		}finally {
			MyConnection.close(con, stmt);
		}
	}
}
