package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import exception.FindException;
import exception.ModifyException;
import sql.MyConnection;
import vo.AnnualLeave;
import vo.Department;
import vo.Employee;

public class EmployeeDAOOracle implements EmployeeDAO {

		@Override

		   public Employee selectById(String id) throws FindException {
		      
		      Connection con = null;
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      try {
		         con = MyConnection.getConnection();
		      }catch(Exception e) {
		         e.printStackTrace();
		         throw new FindException(e.getMessage());
		      }
		      
		      String selectByIdSQL = "SELECT\r\n" + 
			            "e.email,\r\n" + 
			            "e.birth_date,\r\n" + 
			            "e.emp_name,\r\n" + 
			            "e.h_phone,\r\n" + 
			            "e.address,\r\n" + 
			            "e.emp_id,\r\n" + 
			            "e.password,\r\n" + 
			            "e.position,\r\n" + 
			            "al.used_day,\r\n" + 
			            "al.max_day,\r\n" +
			            "d.dept_id,\r\n" +
			            "d.dept_name,\r\n" + 
			            "e.hire_date,\r\n" + 
			            "e.office_tel,\r\n" + 
			            "e.emp_status\r\n" + 
			            "FROM\r\n" + 
			            "employee e\r\n" + 
			            "JOIN department d ON ( e.dept_id = d.dept_id )\r\n" + 
			            "JOIN annual_leave al ON ( al.emp_id = e.emp_id )\r\n" + 
			            "WHERE\r\n" + 
			            "e.emp_id = ?\r\n" + 
			            "AND al.apply_year = TO_CHAR(SYSDATE,'YYYY')";
		         
		      try {
		         pstmt = con.prepareStatement(selectByIdSQL);
		         pstmt.setString(1, id);
		         rs = pstmt.executeQuery();
		         
		         if(rs.next()) {
		            String name = rs.getString("emp_name");
		            String email = rs.getString("email");
		            
		            Date birth_date = rs.getDate("birth_date");
		            String h_phone = rs.getString("h_phone");
		            String address = rs.getString("address");
		            String position = rs.getString("position");
		            String password = rs.getString("password");
		            int status = rs.getInt("emp_status");
		            int used_day = rs.getInt("used_day");
		            int max_day = rs.getInt("max_day");
		            String dept_id = rs.getString("dept_id"); // 추가함
		            String dept_name = rs.getString("dept_name");
		            Date hire_date = rs.getDate("hire_date");
		            String office_tel = rs.getString("office_tel");
		            AnnualLeave a = new AnnualLeave();
		            Department d = new Department();
		            a.used_day = used_day;
		            a.max_day = max_day;
		            d.setDept_id(dept_id); // 추가함
		            d.setDept_name(dept_name);
		            return new Employee(id,position,hire_date,
		                  office_tel,password,name,email,
		                  birth_date,h_phone,status,address,
		                  a,d);
		         }else {
		            throw new FindException("아이디에 해당 사원이없습니다.");
		         }
		      }catch(SQLException e1) {
		         throw new FindException(e1.getMessage());
		      }finally {
		         MyConnection.close(con, pstmt, rs);
		      }
		   }
	
	@Override
	public Employee update(Employee e) throws ModifyException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = MyConnection.getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new ModifyException(e1.getMessage());//예외를떠넘깁시다
		}
		Statement stmt = null;
		String modifySQL = "UPDATE employee SET ";//경우에따라다르게 처리하도록
		String modifySQLSET = "";
		String modifySQL1 = "WHERE emp_id='"+e.getEmp_id()+"'";
		
		try {
			stmt = con.createStatement();
			boolean flag = false;
			if(e.getPassword() != null) {
				modifySQLSET = "password='"+e.getPassword()+"' ";
				flag = true;
			}
			if(e.getEmail() != null) {
				if(flag) {
					modifySQLSET +=",";
				}
				modifySQLSET += "email='"+e.getEmail()+"' ";
				flag = true;
			}
			if(e.getH_phone() != null) {
				if(flag) {
					modifySQLSET+=",";
				}
				modifySQLSET += "h_phone='"+e.getH_phone()+"' ";
				flag = true;
			}
			if(e.getAddress() != null) {
				if(flag) {
					modifySQLSET+=",";
				}
				modifySQLSET += "address='"+e.getAddress()+"' ";
				flag = true;
			}
			if(flag) {
				System.out.println(modifySQL+modifySQLSET+modifySQL1);
				stmt.execute(modifySQL+modifySQLSET+modifySQL1);	
				try {
					return selectById(e.getEmp_id());
				} catch (FindException e1) {
					e1.printStackTrace();
					throw new ModifyException(e1.getMessage());
				}
			}else {
				throw new ModifyException("변경할 내용이 없습니다.");	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ModifyException(e1.getMessage());
		}finally {
			MyConnection.close(con, stmt);
		}
	}
}
