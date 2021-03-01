package service;

import java.util.List;

import exception.FindException;
import vo.Board;
import vo.BoardComment;

public interface BoardService {
	/**
	 * 총게시물수를 반환한다
	 * @return 게시물 수
	 * @throws FindException
	 */
	//int getPageNum() throws FindException;
	int findCount() throws FindException;
	
	void addBoard(Board b);

	void addBoardComment(BoardComment bc);
	
	List<Board> findBoardAll() throws FindException;
	/**
	 * 현재페이지에 해당하는 게시물 목록을 반환한다
	 * @param thispage 현재페이지
	 * @param cnt_per_page 페이지별 보여줄 목록수
	 * @return
	 * @throws FindException
	 */
	List<Board> findBoardPage(int thispage, int cnt_per_page) throws FindException;
	
	Board findBoard(int no);

	List<BoardComment> findBoardComment(int no); 

	void modifyBoard(Board b); 

	void modifyBoardComment(BoardComment bc);

	void removeBoard(int no, String id);
	
	void removeBoardComment(int no, String id); // 글번호?
}
