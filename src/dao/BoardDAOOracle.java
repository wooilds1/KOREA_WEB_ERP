package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import exception.RemoveException;
import sql.MyConnection;
import vo.Board;
import vo.Employee;

public class BoardDAOOracle implements BoardDAO{
	
	@Override
	public int selectCount() throws FindException {
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		int pageNum=0;
		
		try {
			con=MyConnection.getConnection();
			} catch(Exception e) {
				e.printStackTrace();
				throw new FindException(e.getMessage());
			}
		
		String selectPageNumSQL="select count(*) from board";
		
		try {
			pstmt=con.prepareStatement(selectPageNumSQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				pageNum=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pageNum;
		
	}
	

	@Override
	public void insert(String board_title, String board_content, String id) throws AddException {
		Connection con = null;
		try {
			con= MyConnection.getConnection();
		}catch (Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
			
		}
		
		PreparedStatement pstmt=null;
		String insertByBSQL="insert INTO board(BOARD_NO,BOARD_TITLE,board_content,emp_id,board_date) "
				+ "values (BOARD_SEQ.nextval,?,?,?,?)";
		
		try {
			Employee emp_vo=new Employee();
			Timestamp timestamp=new Timestamp(System.currentTimeMillis());
			
			pstmt=con.prepareStatement(insertByBSQL);
			pstmt.setString(1,board_title);
			pstmt.setString(2,board_content);
			pstmt.setString(3,id);
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

	@Override //selectByNo(int no)>해당 게시글 불러오기 
	public Board selectByNo(int no) throws FindException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		
		try {
			con=MyConnection.getConnection();
			} catch(Exception e) {
				e.printStackTrace();
				throw new FindException(e.getMessage());
			}
		
		String selectByNoSQL="select * from board b join employee e on b.emp_id=e.emp_id\r\n" + 
							 "where BOARD_NO=?";
		
		try {
			pstmt=con.prepareStatement(selectByNoSQL);
			pstmt.setInt(1,no);
			rs=pstmt.executeQuery();
			Employee emp_vo=new Employee();
			if(rs.next()) {
				int board_no=rs.getInt("board_no");
				String board_title=rs.getString("board_title");
				String board_content=rs.getString("board_content");
				String emp_id=rs.getString("emp_id");
				emp_vo.setEmp_id(emp_id);
				String emp_name=rs.getString("emp_name");
				emp_vo.setName(emp_name);
				

				Timestamp board_date=rs.getTimestamp("board_date");
				
				return new Board(board_no,board_title,board_content,board_date,emp_vo);
			}else {
				throw new FindException("해당 게시글이 없습니다");
			}
		}catch(SQLException e) {
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con,pstmt,rs);
		}		
	}

	@Override //selectByAll()> 게시글목록 보이기
	public List<Board> selectAll() throws FindException {
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		
		
		try {
		con=MyConnection.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectSQL="select * from board";
		
		try {
			pstmt=con.prepareStatement(selectSQL);
			rs=pstmt.executeQuery();
			List<Board> list = new ArrayList<>();
			Employee emp_vo=new Employee();
			
			while(rs.next()) {
				int board_no=rs.getInt("board_no");
				String board_title=rs.getString("board_title");
				Timestamp board_date=rs.getTimestamp("board_date");
				String emp_id=rs.getString("emp_id");
				emp_vo.setEmp_id(emp_id);

				Board b=new Board(board_no,board_title,board_date,emp_vo);
				list.add(b);
				
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
	public void update(String title, String content, int no) throws ModifyException {
		Connection con = null;
		Board b=new Board();

		try {
			con=MyConnection.getConnection();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}
		
		Statement stmt=null;
		
		String updateSQL="update board set ";
		String updateSQLSet="";
		String updateSQL1="where board_no='"+no+"'"; 
		try {

			stmt = con.createStatement();
			boolean flag=false;//수정여부
			
			
			//제목수정
			if(title!=null && !title.equals("")) { 
				updateSQLSet="board_title='"+title+"' ";
				flag=true;
			}
			//내용수정
			if(content!=null && !content.equals("")) {
				if(flag) {
					updateSQLSet+=",";
				}
				
				updateSQLSet+="board_content='"+content+"' ";
				flag=true;
			}
			//수정여부에 따라
			if(flag) {
				stmt.executeUpdate(updateSQL+updateSQLSet+updateSQL1);
			}else {
				//수정할 내용이 없는경우
				throw new ModifyException("수정할내용이 없습니다");
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}finally {
			MyConnection.close(con,stmt);
		}
		
	}

	@Override
	public void delete(int no) throws RemoveException {
		
		//연결시 오류(예외)처리 
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con=MyConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
		
		String deleteBoardSQL="DELETE FROM BOARD WHERE BOARD_NO = ?";
		try {
			pstmt=con.prepareStatement(deleteBoardSQL);
			pstmt.setInt(1,no);
			int rowcnt=pstmt.executeUpdate();
			if(rowcnt!=1) {
				throw new RemoveException("지우고자 하는 글이 없습니다"); //지울게시글이 없을경우
			}

		}catch(SQLException e) {
			throw new RemoveException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt);
		}
		
	}

	@Override
	   //dao추가 육민성
	   public List<Board> selectPage(int thispage, int cnt_per_page) throws FindException {
	      Connection con = null;
	      PreparedStatement pstmt=null;
	      ResultSet rs =null;
	      
	      try {
	      con=MyConnection.getConnection();
	      } catch(Exception e) {
	         e.printStackTrace();
	         throw new FindException(e.getMessage());
	      }
	      String selectSQL = 
						"SELECT ae.* \r\n" + 
						"FROM (\r\n" + 
						"       SELECT ROW_NUMBER() OVER (ORDER BY BOARD_NO DESC) NUM\r\n" + 
						"             , A.board_no , A.board_title, A.board_date, E.*\r\n" + 
						"          FROM BOARD A JOIN EMPLOYEE E on A.EMP_ID = E.EMP_ID\r\n" + 
						"        ORDER BY BOARD_NO DESC \r\n" + 
						"       )   ae\r\n" + 
						"WHERE NUM  BETWEEN fun_start_row(?,?) AND fun_end_row(?,?)";
	      
	      try {
	         pstmt=con.prepareStatement(selectSQL);
	         pstmt.setInt(1, thispage);
	         pstmt.setInt(2, cnt_per_page);
	         pstmt.setInt(3, thispage);
	         pstmt.setInt(4, cnt_per_page);
	         rs=pstmt.executeQuery();
	         List<Board> list = new ArrayList<>();
	      
	         while(rs.next()) {
	            int board_no=rs.getInt("board_no");
	            String board_title=rs.getString("board_title");
	            Date board_date= rs.getDate("board_date");
	            
	            Employee emp_vo=new Employee();            
	            String emp_id =rs.getString("emp_id");
	            String emp_name = rs.getString("emp_name");
	            emp_vo.setEmp_id(emp_id);
	            emp_vo.setName(emp_name);


	            Board b=new Board(board_no,board_title,board_date,emp_vo);
	            list.add(b);
	         }
	         
	         return list;
	      } catch(Exception e) {
	         e.printStackTrace();
	         throw new FindException(e.getMessage());
	      }finally {
	         MyConnection.close(con,pstmt,rs);
	      }
	   }
}