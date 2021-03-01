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
	
	//게시판글 갯수세기 
	public int selectCount() throws FindException{
		
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
	
	//selectByAll()> 게시글목록 보이기
	public List<Board> selectAll() throws FindException{
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
				Date board_date= rs.getDate("board_date");
				//System.out.println(board_date);
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
	
		
	//selectByNo(int no)>해당 게시글 불러오기 
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
		
		String selectByNoSQL="select * from board where BOARD_NO=?";
		
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
				Date board_date=rs.getDate("board_date");
				
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
		
	public void insert(Board b) throws AddException {
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
			Timestamp timestamp=new Timestamp(b.getBoard_date().getTime());
			
			pstmt=con.prepareStatement(insertByBSQL);
//			pstmt.setInt(1,b.getBoard_no()); // 시퀸스로 받는다 
			pstmt.setString(1,b.getBoard_title());
			pstmt.setString(2,b.getBoard_content());
			pstmt.setString(3,b.getEmp_vo().getEmp_id());//여기서 오류
			b.getEmp_vo().setEmp_id(emp_vo.getEmp_id());
			pstmt.setTimestamp(4,timestamp);//여기랑
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
		

	public void update(Board b) throws ModifyException {
		
		Connection con = null;

		try {
			con=MyConnection.getConnection();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}
		
		Statement stmt=null;
		
		String updateSQL="update board set ";
		String updateSQLSet="";
		String updateSQL1="where board_no='"+b.getBoard_no()+"'"; 
		try {

			stmt = con.createStatement();
			boolean flag=false;//수정여부
			
			//여기서 문장 조건을 주는거야  >> set이후에 다르게 처리할부분 
			
			//제목수정
			if(b.getBoard_title()!=null && !b.getBoard_title().equals("")) { 
				updateSQLSet="board_title='"+b.getBoard_title()+"' ";
				flag=true;
			}
			//내용수정
			if(b.getBoard_content()!=null && !b.getBoard_content().equals("")) {
				if(flag) {
					updateSQLSet+=",";
				}
				
				updateSQLSet+="board_content='"+b.getBoard_content()+"' ";
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

	public void delete(int no) throws RemoveException {
		
		Board b;
		
		try {
			b=selectByNo(no);
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
		
		String deleteBoardSQL="DELETE FROM BOARD WHERE BOARD_NO = ?";
		try {
			pstmt=con.prepareStatement(deleteBoardSQL);
			pstmt.setInt(1,no);
			int rowcnt=pstmt.executeUpdate();
			if(rowcnt!=1) {
				throw new RemoveException("지우고자 하는 글이 없습니다"); //지울게시글이 없을경우
			}
			//return b;
		}catch(SQLException e) {
			throw new RemoveException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt);
		}
		
	}
	
	@Override 
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
//		String selectSQL="SELECT num ,board_no , board_title, emp_id, board_date\r\n" + 
//				"  FROM (\r\n" + 
//				"        SELECT ROW_NUMBER() OVER (ORDER BY BOARD_NO) NUM\r\n" + 
//				"             , A.board_no , A.board_title, A.emp_id, A.board_date\r\n" + 
//				"          FROM BOARD A\r\n" + 
//				"         ORDER BY BOARD_NO\r\n" + 
//				"        ) \r\n" + 
//				" WHERE NUM BETWEEN 1 AND 10";
		
		String selectSQL = "SELECT num ,board_no , board_title, emp_id, board_date\r\n" + 
				"FROM (\r\n" + 
				"        SELECT ROW_NUMBER() OVER (ORDER BY BOARD_NO) NUM\r\n" + 
				"             , A.board_no , A.board_title, A.emp_id, A.board_date\r\n" + 
				"          FROM BOARD A\r\n" + 
				"         ORDER BY BOARD_NO\r\n" + 
				"        ) \r\n" + 
				" WHERE NUM  BETWEEN fun_start_row(?,?) AND fun_end_row(?,?)";
		
		try {
			pstmt=con.prepareStatement(selectSQL);
			pstmt.setInt(1, thispage);
			pstmt.setInt(2, cnt_per_page);
			pstmt.setInt(3, thispage);
			pstmt.setInt(4, cnt_per_page);
			rs=pstmt.executeQuery();
			List<Board> list = new ArrayList<>();
			Employee emp_vo=new Employee();
			
			while(rs.next()) {
				int board_no=rs.getInt("board_no");
				String board_title=rs.getString("board_title");
				Date board_date= rs.getDate("board_date");
				//System.out.println(board_date);
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
	

	public static void main(String[] args) {
		BoardDAOOracle dao=new BoardDAOOracle();
		Employee emp_vo=new Employee();
		
//		// selectByAll() 테스트ok
//		try {
//			List<Board> list = dao.selectAll();
//			//이떄 발생가능한 예외처리하자
//			for( Board b : list) {
//				System.out.println(b);
//			}
//			}catch (Exception e) {
//				e.printStackTrace();
//				
//			}
		
		
		
		
//		//update(Board b) 테스트ok	
//		Board b =new Board();
//		b.setBoard_no(43);
//		b.setBoard_title("test456");
//		b.setBoard_content("test456");
//
//		try {
//			dao.update(b);
//		} catch (ModifyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		//delete(int no) 테스트ok
//		int board_no=43;
//		try {
//			Board b =dao.delete(board_no);
//			System.out.println("삭제테스트성공");
//		} catch (RemoveException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		//insert 테스트ok
//		Board b= new Board();
//		Timestamp timestamp=new Timestamp(System.currentTimeMillis());
//		
//		b.setBoard_title("test47");
//		b.setBoard_content("test47");
//		emp_vo.emp_id="1324567";
//		b.setEmp_vo(emp_vo);
//		b.setBoard_date(timestamp);
//		try {
//		dao.insert(b);
//		System.out.println("추가테스트성공");
//		} catch(AddException e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
//		
		//selectByNo 테스트 ok
//		int board_no=62; // id999도 테스트해봐 
//		try {
//			Board b=dao.selectByNo(board_no);
//			System.out.println(b);
//		} catch(FindException e) {
//			e.printStackTrace();
//		}
		
//		//selectPageNum 테스트 ok
//		try {
//			int b =dao.selectPageNum();
//			System.out.println(b);
//		} catch (FindException e) {
//			e.printStackTrace();
//		}

	}


	

	
}
