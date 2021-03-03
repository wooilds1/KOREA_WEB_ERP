package dao;

import java.util.List;

import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import exception.RemoveException;
import vo.Board;

public interface BoardDAO {
	
	/**
	 * 글갯수세기
	 * @return
	 * @throws FindException
	 */
	int selectCount() throws FindException; // service에서는 selectPageNum
	
	/**
	 * 글작성
	 * @param b
	 * @throws AddException
	 */
	//void insert(Board b) throws AddException;
	void insert(String board_title, String board_content, String id) throws AddException;
	/**
	 * 해당 글 불러오기
	 * @param no
	 * @return
	 * @throws FindException
	 */
	Board selectByNo(int no) throws FindException;
	
	/**
	 * 글목록 불러오기
	 * @return
	 * @throws FindException
	 */
	List<Board> selectAll() throws FindException;
	
	/**
	 * 글 수정하기
	 * @param b
	 * @throws ModifyException
	 */
	//void update(Board b) throws ModifyException; 
	void update(String title, String content, int no) throws ModifyException;
	/**
	 * 글삭제하기
	 * @param no
	 * @throws RemoveException
	 */
	void delete(int no) throws RemoveException;
	
	//페이징 dao 추가한거
	/**
	 * 육민성
	 * 현재페이지에 해당하는 게시글 목록을 반환한다
	 * @param thispage 현재페이지
	 * @param cnt_per_page 페이지별 보여줄 목록수
	 * @return
	 * @throws FindException
	 */
	//List<Board> selectPage(int thispage, int cnt_per_page) throws FindException;
	List<Board> selectPage(int thispage, int cnt_per_page) throws FindException;

}
