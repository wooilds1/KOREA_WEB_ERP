package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import exception.FindException;
import sql.MyConnection;
import vo.Salary;

public class SalaryDAOOracle implements SalaryDAO{

//	public int selectPageNum(Date start_date, Date end_date, String id) throws FindException{
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		int num=0;
//		try {
//			con= MyConnection.getConnection();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		String selectSQL="SELECT salary_date,after_tax_salary,income_tax,hire_insurance,total_deduction,extra_pay,position_pay,\r\n" + 
//				"before_tax_salary,local_income_tax,health_insurance,national_pension "
//				+ "FROM SALARY "
//				+ "WHERE salary_date BETWEEN ? AND ? AND emp_id = ?";
//		System.out.println(new java.sql.Timestamp(start_date.getTime()));
//		System.out.println(new java.sql.Timestamp(end_date.getTime()));
//		try{
//			pstmt = con.prepareStatement(selectSQL);
//			pstmt.setTimestamp(1, new java.sql.Timestamp(start_date.getTime()));
//			pstmt.setTimestamp(2, new java.sql.Timestamp(end_date.getTime()));
//			pstmt.setString(3,id);
//			rs=pstmt.executeQuery(); 
//			rs.next();
//			num=rs.getInt(1);
//			
//			if(rs.getInt(1)==0) {
//				throw new FindException("조회된 건수가 없습니다.");
//			}		
//
//			return num;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}finally {
//			MyConnection.close(con,pstmt,rs);
//		}
//	}
	/**
	 * 
	 * @param start_date 급여검색시작월
	 * @param end_date 급여검색끝월
	 * @param currentPage 검색할 현재페이지
	 * @param cnt_per_page 검색할 페이지에 보여줄 급여목록수
	 * @param id 사원아이디
	 * @return
	 * @throws FindException
	 */
	public List <Salary>selectByTerm(Date start_date, 
									Date end_date, 
									int currentPage, 
									int cnt_per_page, 
									String id)throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con= MyConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String selectSQL=
				"SELECT *\r\n" + 
				"FROM (SELECT s.*, rownum r \r\n" + 
				"      FROM(\r\n" + 
				"        select salary_date,after_tax_salary,income_tax,hire_insurance,total_deduction,extra_pay,position_pay,\r\n" + 
				"            before_tax_salary,local_income_tax,health_insurance,national_pension \r\n" + 
				"        FROM SALARY \r\n" + 
				"        where salary_date BETWEEN ? AND ? and emp_id = ?\r\n" + 
				"        ORDER BY salary_date DESC\r\n" + 
				"     )s\r\n" + 
				")\r\n" + 
				"WHERE r BETWEEN FUN_START_ROW(?,?) AND FUN_END_ROW(?, ?)";
		try {
			pstmt = con.prepareStatement(selectSQL);
//			System.out.println("selectByTerm메서드의 start_date.getTime()을 TimeStamp로 변환한 값:" + new java.sql.Timestamp(start_date.getTime()));
			pstmt.setTimestamp(1, new java.sql.Timestamp(start_date.getTime()));
			pstmt.setTimestamp(2, new java.sql.Timestamp(end_date.getTime()));
			pstmt.setString(3,id);
			pstmt.setInt(4, currentPage);
			pstmt.setInt(5, cnt_per_page);
			pstmt.setInt(6, currentPage);
			pstmt.setInt(7, cnt_per_page);
			rs=pstmt.executeQuery();
			List<Salary> list = new ArrayList<>();
			while(rs.next()){
				Date salary_date =rs.getDate("salary_date");
				int after_tax_salary =rs.getInt("after_tax_salary");
				int income_tax = rs.getInt("income_tax");
				int hire_insurance= rs.getInt("hire_insurance");
				int total_deduction= rs.getInt("total_deduction");
				int extra_pay= rs.getInt("extra_pay");
				int position_pay= rs.getInt("position_pay");
				int before_tax_salary= rs.getInt("before_tax_salary");
				int local_income_tax= rs.getInt("local_income_tax");
				int health_insurance= rs.getInt("health_insurance");
				int national_pension= rs.getInt("national_pension");
				Salary salary= new Salary(salary_date,after_tax_salary,income_tax,hire_insurance,total_deduction,extra_pay,position_pay,
						before_tax_salary,local_income_tax,health_insurance,national_pension );
				list.add(salary);
			}if(list.size()==0) {
				throw new FindException("고객이 한명도 없습니다.");
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con,pstmt,rs);
		}
	}
	
	public List <Salary>selectByTerm(Date start_date, Date end_date,String id)throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con= MyConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String selectSQL="SELECT salary_date,after_tax_salary,income_tax,hire_insurance,total_deduction,extra_pay,position_pay,before_tax_salary,local_income_tax,health_insurance,national_pension FROM SALARY where salary_date BETWEEN ? AND ? AND emp_id = ?";
		try {
			pstmt = con.prepareStatement(selectSQL);
			//System.out.println("selectByTerm메서드의 start_date.getTime()을 TimeStamp로 변환한 값:" + new java.sql.Timestamp(start_date.getTime()));
			pstmt.setTimestamp(1, new java.sql.Timestamp(start_date.getTime()));
			pstmt.setTimestamp(2, new java.sql.Timestamp(end_date.getTime()));
			pstmt.setString(3,id);
			rs=pstmt.executeQuery();
			List<Salary> list = new ArrayList<>();
			while(rs.next()){
				Date salary_date =rs.getDate("salary_date");
				int after_tax_salary =rs.getInt("after_tax_salary");
				int income_tax = rs.getInt("income_tax");
				int hire_insurance= rs.getInt("hire_insurance");
				int total_deduction= rs.getInt("total_deduction");
				int extra_pay= rs.getInt("extra_pay");
				int position_pay= rs.getInt("position_pay");
				int before_tax_salary= rs.getInt("before_tax_salary");
				int local_income_tax= rs.getInt("local_income_tax");
				int health_insurance= rs.getInt("health_insurance");
				int national_pension= rs.getInt("national_pension");
				Salary salary= new Salary(salary_date,after_tax_salary,income_tax,hire_insurance,total_deduction,extra_pay,position_pay,
						before_tax_salary,local_income_tax,health_insurance,national_pension );
				list.add(salary);
			}if(list.size()==0) {
				throw new FindException("고객이 한명도 없습니다.");
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con,pstmt,rs);
		}
	}
	
	public int selectCntByTerm(Date start, Date end, String id) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int num=0;
		try {
			con= MyConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String selectSQL="SELECT COUNT(*) FROM SALARY \r\n" + 
				"WHERE salary_date BETWEEN ? AND ? and emp_id = ?";
		try{
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setTimestamp(1, new java.sql.Timestamp(start.getTime()));
			pstmt.setTimestamp(2, new java.sql.Timestamp(end.getTime()));
			pstmt.setString(3,id);
			rs=pstmt.executeQuery(); 
			
			while(rs.next()) {
				num = rs.getInt(1);
			}
			System.out.println("oracle num : "+num);
			if(num==0) {
				throw new FindException("조회된 건수가 없습니다.");
			}		
			return num;
	
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con,pstmt,rs);
		}
	}

	}

	 



