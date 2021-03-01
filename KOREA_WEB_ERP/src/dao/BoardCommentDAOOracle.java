package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
import vo.Board;
import vo.BoardComment;
import vo.Employee;

public class BoardCommentDAOOracle implements BoardCommentDAO{
	
	@Override
	public void insert(BoardComment bc) throws AddException {
		
		Connection con = null;
		PreparedStatement pstmt=null;
		
		
		try {
			con= MyConnection.getConnection();
		}catch (Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
			
		}

		String insertByBCSQL="insert INTO board_comment(CMT_NO,emp_id,CMT_CONTENT,BOARD_NO,CMT_DATE) "
								+ "values (BOARD_COMMENT_SEQ.nextval,?,?,?,?)";
		
		try {
			Timestamp timestamp=new Timestamp(bc.getCmt_date().getTime());
			Employee emp_vo=new Employee();
			
			pstmt=con.prepareStatement(insertByBCSQL);
			
//			pstmt.setInt(1,bc.getCmt_no());//시퀸스로 받음 	
			pstmt.setNString(1,bc.getEmp_vo().getEmp_id());
			bc.getEmp_vo().setEmp_id(emp_vo.getEmp_id());
			pstmt.setString(2,bc.getCmt_content());
			pstmt.setInt(3,bc.getBoard_no());
			pstmt.setTimestamp(4,timestamp);
			pstmt.executeUpdate();
		}catch(SQLException e) {
//			
			if(e.getErrorCode()==1) {//pk가 중복인경우
				throw new AddException(e.getMessage());
			}else {
				e.printStackTrace();
			}
		}finally {
			MyConnection.close(con,pstmt);
		}
		
	}

	//해당글의 댓글들 나열하기 . 댓글목록 불러오기>>board의 selectByNo이용하여 댓글들 selectAll
	@Override
	public List<BoardComment> selectAllByBoardNo(int no) throws FindException{
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		
		try {
		con=MyConnection.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectByNoSQL=
				"SELECT bc.CMT_NO, bc.EMP_ID, e.EMP_NAME, bc.CMT_CONTENT, bc.CMT_DATE\r\n" + 
				"FROM BOARD_COMMENT bc JOIN EMPLOYEE e ON bc.emp_id = e.emp_id\r\n" + 
				"WHERE BOARD_NO = ?";
		
		try {
			pstmt=con.prepareStatement(selectByNoSQL);
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			List<BoardComment> list = new ArrayList<>();
			Employee emp_vo=new Employee();
			
			while(rs.next()) {
				int cmt_no=rs.getInt("cmt_no");
				String emp_id=rs.getString("emp_id");
				emp_vo.setEmp_id(emp_id);
				String emp_name=rs.getString("emp_name");
				emp_vo.setName(emp_name);
				Date cmt_date=rs.getDate("cmt_date");
				String cmt_content=rs.getString("cmt_content");
				
				BoardComment bc=new BoardComment(cmt_no,cmt_content,cmt_date,emp_vo);
				list.add(bc);
			}
			return list;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con,pstmt,rs);
		}

		
	}
	//특정댓글 찾기
	@Override
	public BoardComment selectByCmtNo(int no) throws FindException{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		
		
		try {
			con=MyConnection.getConnection();
			} catch(Exception e) {
				e.printStackTrace();
				throw new FindException(e.getMessage());
			}
		String selectByCmtNoSQL="SELECT *\r\n" + 
								"FROM BOARD_COMMENT\r\n" + 
								"WHERE CMT_NO = ?";
		try {
			Employee emp_vo=new Employee();
			pstmt=con.prepareStatement(selectByCmtNoSQL);
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				String emp_id=rs.getString("emp_id");
				emp_vo.setEmp_id(emp_id);
				int cmt_no=rs.getInt("cmt_no");
				String cmt_content=rs.getString("cmt_content");
				int board_no=rs.getInt("board_no");
				Date cmt_date=rs.getDate("cmt_date");
				
				return new BoardComment(cmt_no,emp_vo,cmt_content,board_no,cmt_date);

			}else {
				throw new FindException("찾고자하는 댓글없음");
			}
		} catch (SQLException e) {
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con,pstmt,rs);
		}
	}
	
	@Override
	public void update(BoardComment bc) throws ModifyException {
		Connection con = null;

		try {
			con=MyConnection.getConnection();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}
		
		Statement stmt=null;

		String updateSQL="update board_comment set ";
		String updateSQLSet="";
		String updateSQL1="where cmt_no='"+bc.getCmt_no()+"'";
		try {

			stmt = con.createStatement();
			boolean flag=false;
			
			if(bc.getCmt_content()!=null && !bc.getCmt_content().equals("")) {
				if(flag) {
					updateSQLSet+=",";
				}
				updateSQLSet+="cmt_content='"+bc.getCmt_content()+"' ";
				flag=true;
			}
			if(flag) {
				stmt.executeUpdate(updateSQL+updateSQLSet+updateSQL1);
				}else {
					//수정안됨
					throw new ModifyException("댓글내용이 없습니다");
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}finally {
			MyConnection.close(con,stmt);
		}
		
		
	}
	
	@Override
	public void delete(int no) throws RemoveException {
		
		BoardComment bc;
		
		try {
			bc=selectByCmtNo(no);
		} catch (FindException e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
		
		//연결시 오류(예외)처리 
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con=MyConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
		
		String deleteBoardCommentSQL="DELETE FROM BOARD_COMMENT WHERE CMT_NO = ?";
		try {
			pstmt=con.prepareStatement(deleteBoardCommentSQL);
			pstmt.setInt(1,no);
			int rowcnt=pstmt.executeUpdate();
			if(rowcnt!=1) {
				throw new RemoveException("지우고자하는 댓글이 없습니다"); //지울댓글이 없을경우
			}
			// return bc;
		}catch(SQLException e) {
			throw new RemoveException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt);
		}
		
	}
}
