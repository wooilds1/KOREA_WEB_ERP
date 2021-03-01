package service;

import java.util.List;

import dao.BoardDAO;
import dao.BoardDAOOracle;
import exception.FindException;
import vo.Board;
import vo.BoardComment;

public class BoardServiceImpl implements BoardService {
	BoardDAO dao = new BoardDAOOracle();
	
	
	@Override 
	public int findCount() throws FindException {
		return dao.selectCount();
	}

	@Override
	public void addBoard(Board b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addBoardComment(BoardComment bc) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Board> findBoardAll() throws FindException{
		return dao.selectAll();
	}

	@Override
	public Board findBoard(int no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoardComment> findBoardComment(int no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyBoard(Board b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyBoardComment(BoardComment bc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBoard(int no, String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBoardComment(int no, String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Board> findBoardPage(int thispage, int cnt_per_page) throws FindException {
		
		return dao.selectPage(thispage, cnt_per_page);
	}


}
