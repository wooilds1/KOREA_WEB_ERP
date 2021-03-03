package service;

import java.util.List;

import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import exception.RemoveException;
import vo.Board;
import vo.BoardComment;

public interface BoardService {
	
	/**
	 * 육민성
	 * 현재페이지에 해당하는 게시물 목록을 반환한다
	 * @param thispage 현재페이지
	 * @param cnt_per_page 페이지별 보여줄 목록수
	 * @return
	 * @throws FindException
	 */
	//List<Board> findBoardPage(int thispage, int cnt_per_page) throws FindException;
//	List<Board> findBoardPage(int thispage, int cnt_per_page ,String id_no) throws FindException;
	
	/////한해든/////
	//총 게시글 수 가져오기
	int findCount() throws FindException;
	
	//게시글추가
	void addBoard(String title,String content,String id) throws AddException;
	
	//댓글추가
	void addBoardComment(String content, String id,int no) throws AddException;
	
	//글불러오기
	List<Board> findBoardAll() throws FindException;
	
	//특정게시글불러오기
	Board findBoard(int no) throws FindException;

	//해당글의 전체댓글 불러오기
	List<BoardComment> findBoardComment(int no) throws FindException;
	
	//특정댓글 불러오기
	BoardComment findBoardCommentByNo(int no) throws FindException;
	
	//글수정하기
	void modifyBoard(String title, String content, int no) throws ModifyException; 

	//댓글수정하기
	void modifyBoardComment(int no,String content) throws ModifyException;

	//글삭제하기
	void removeBoard(int no, String id) throws RemoveException;
	
	//댓글삭제하기
	void removeBoardComment(int no, String id) throws RemoveException;

	List<Board> findBoardPage(int thisPage, int cnt_per_pagegroup)throws FindException;
}
