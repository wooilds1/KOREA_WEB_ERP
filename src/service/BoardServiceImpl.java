package service;

import java.util.List;

import dao.BoardCommentDAO;
import dao.BoardCommentDAOOracle;
import dao.BoardDAO;
import dao.BoardDAOOracle;
import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import exception.RemoveException;
import vo.Board;
import vo.BoardComment;

public class BoardServiceImpl implements BoardService {
	private BoardDAO dao=new BoardDAOOracle();
	private BoardCommentDAO cdao=new BoardCommentDAOOracle();

	@Override
	public int findCount() throws FindException {
		return dao.selectCount();
	}

	@Override
	public void addBoard(String title, String content, String id) throws AddException {
		dao.insert(title, content, id);
		
	}

	@Override
	public void addBoardComment(String content, String id,int no) throws AddException {
		cdao.insert(content,id, no);
		
	}

	@Override
	public List<Board> findBoardAll() throws FindException {
		return dao.selectAll();
	}

	@Override
//	public List<Board> findBoardPage(int thispage, int cnt_per_page) throws FindException {
//		return dao.selectPage(thispage, cnt_per_page);
//	}
	public List<Board> findBoardPage(int thispage, int cnt_per_page) throws FindException {
		return dao.selectPage(thispage, cnt_per_page);
	}

	@Override
	public Board findBoard(int no) throws FindException {
		return dao.selectByNo(no);
	}

	@Override
	public List<BoardComment> findBoardComment(int no) throws FindException {
		return cdao.selectAllByBoardNo(no);
	}

	@Override
	public BoardComment findBoardCommentByNo(int no) throws FindException {
		return cdao.selectByCmtNo(no);
	}

	@Override
	public void modifyBoard(String title, String content, int no) throws ModifyException {
		 dao.update(title, content, no);
		
	}

	@Override
	public void modifyBoardComment(int no, String content) throws ModifyException {
		cdao.update(no,content);
		
	}

	@Override
	public void removeBoard(int no, String id) throws RemoveException {
		try {
			Board b=dao.selectByNo(no);
			String boardId=b.getEmp_vo().getEmp_id();
			if(boardId.equals(id)) {
				dao.delete(no);
			}else {
				throw new RemoveException("삭제권한이 없는 아이디입니다&&"+boardId+"no is : "+no+" String id is : "+id);
			}
		} catch (FindException e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	public void removeBoardComment(int no, String id) throws RemoveException {
		try {
			BoardComment bc=cdao.selectByCmtNo(no);
			String commentId=bc.getEmp_vo().getEmp_id();
			if(commentId.equals(id)) {
				cdao.delete(no);
			}
		} catch (FindException e) {
			e.printStackTrace();
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
////////////////////minsung Service//////////////////////
//	@Override 
//	public int findCount() throws FindException {
//		return dao.selectCount();
//	}
//
//	@Override
//	public void addBoard(Board b) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void addBoardComment(BoardComment bc) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public List<Board> findBoardAll() throws FindException{
//		return dao.selectAll();
//	}
//
//	@Override
//	public Board findBoard(int no) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<BoardComment> findBoardComment(int no) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void modifyBoard(Board b) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void modifyBoardComment(BoardComment bc) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void removeBoard(int no, String id) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void removeBoardComment(int no, String id) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public List<Board> findBoardPage(int thispage, int cnt_per_page) throws FindException {
//		
//		return dao.selectPage(thispage, cnt_per_page);
//	}


}
