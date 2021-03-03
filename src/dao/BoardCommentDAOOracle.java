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
	public void insert(String content, String id,int no) throws AddException {
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
			Timestamp timestamp=new Timestamp(System.currentTimeMillis());
			
			pstmt=con.prepareStatement(insertByBCSQL);
			
			pstmt.setString(1,id);
			pstmt.setString(2,content);
			pstmt.setInt(3,no);
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

	@Override
	public List<BoardComment> selectAllByBoardNo(int no) throws FindException {
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		List<BoardComment> list = new ArrayList<>();
		
		try {
		con=MyConnection.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectByNoSQL=
				"SELECT bc.CMT_NO, bc.EMP_ID, bc.BOARD_NO,e.EMP_NAME, bc.CMT_CONTENT, bc.CMT_DATE\r\n" + 
				"FROM BOARD_COMMENT bc JOIN EMPLOYEE e ON bc.emp_id = e.emp_id\r\n" + 
				"WHERE BOARD_NO = ?\r\n" + 
				"order by bc.CMT_NO";
		
		try {
			pstmt=con.prepareStatement(selectByNoSQL);
			pstmt.setInt(1,no);
			rs=pstmt.executeQuery();

			
			while(rs.next()) {
				Employee emp_vo=new Employee();
				
				int cmt_no=rs.getInt("cmt_no");
				int board_no=rs.getInt("board_no");
				Date cmt_date=rs.getDate("cmt_date");
				String cmt_content=rs.getString("cmt_content");
				String emp_name=rs.getString("emp_name");
				emp_vo.setName(emp_name);
				String emp_id=rs.getString("emp_id");
				emp_vo.setEmp_id(emp_id);

				BoardComment bc=new BoardComment(cmt_no,cmt_content,board_no,cmt_date,emp_vo);
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

	@Override
	public BoardComment selectByCmtNo(int no) throws FindException {
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

				int cmt_no=rs.getInt("cmt_no");
				String cmt_content=rs.getString("cmt_content");
				int board_no=rs.getInt("board_no");
				Date cmt_date=rs.getDate("cmt_date");
				String emp_id=rs.getString("emp_id");
				emp_vo.setEmp_id(emp_id);
				
				return new BoardComment(cmt_no,cmt_content,board_no,cmt_date,emp_vo);

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
	public void update(int no, String content) throws ModifyException {
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
		String updateSQL1="where cmt_no='"+no+"'";
		try {

			stmt = con.createStatement();
			boolean flag=false;
			
			if(content!=null && !content.equals("")) {
				if(flag) {
					updateSQLSet+=",";
				}
				updateSQLSet+="cmt_content='"+content+"' ";
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

		}catch(SQLException e) {
			throw new RemoveException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt);
		}
		
	}
		
	public static void main(String[] args) {
		BoardCommentDAOOracle dao=new BoardCommentDAOOracle();
		Employee emp_vo=new Employee();		
		
		// selectAllByBoardNo() 테스트ok
//		try {
//			List<BoardComment> list = dao.selectAllByBoardNo(3);
//			//이떄 발생가능한 예외처리하자
//			for( BoardComment bc : list) {
//				System.out.println(bc);
//			}
//			}catch (Exception e) {
//				e.printStackTrace();
//				
//			}
		
//		//update(BoardComment bc) 테스트ok		
//		BoardComment bc =new BoardComment();
//		bc.setCmt_no(3);
//		bc.setCmt_content("cmttest22");
//
//		try {
//			dao.update(bc);
//		} catch (ModifyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
//		//delete(int no) 테스트ok
//		int cmt_no=62;
//		try {
//			BoardComment bc =dao.delete(cmt_no);
//			System.out.println("삭제테스트성공");
//		} catch (RemoveException e) {
//			e.printStackTrace();
//		}
		
		//insert 테스트ok
//		BoardComment bc= new BoardComment();
//		Timestamp timestamp=new Timestamp(System.currentTimeMillis());
//	
//		emp_vo.emp_id="20200007";
//		bc.setEmp_vo(emp_vo);
//		bc.setCmt_content("댓글테스트");
//		bc.setCmt_date(timestamp);
//		bc.setBoard_no(3);
//		try {
//		dao.insert(bc);
//		System.out.println("추가테스트성공");
//		} catch(AddException e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
		
////		//selectByCmtNo 테스트 ok
//		int cmt_no=81; // id999도 테스트해봐 
//		try {
//			BoardComment bc=dao.selectByCmtNo(cmt_no);
//			System.out.println(bc);
//		} catch(FindException e) {
//			e.printStackTrace();
//		}
		

	}
}
