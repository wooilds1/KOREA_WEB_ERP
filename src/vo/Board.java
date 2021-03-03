package vo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Board {
	/*
	BOARD_NO		NUMBER(3,0)			No      게시글번호(시퀀스)	PK
	BOARD_TITLE		VARCHAR2(100 BYTE)	No		글제목
	BOARD_CONTENT	VARCHAR2(500 BYTE)	Yes		글내용
	EMP_ID			VARCHAR2(10 BYTE)	No		사원번호			FK
	BOARD_DATE		DATE				No		작성일자
	*/
	
	private int board_no;
	private String board_title;
	private String board_content;
	private Date board_date;
	private Employee emp_vo;
	
	public Board() {
		super();
	}
	
	public Board(int board_no ) {
		this(board_no,null,null,null,null);
	}
	
	public Board(int board_no, String board_title ) {
		this(board_no,board_title,null,null,null);
	}
	
	public Board(int board_no, String board_title, String board_content) {
		this(board_no,board_title,board_content,null,null);
	}
	
	public Board(int board_no, String board_title, String board_content, Date board_date) {
		this(board_no,board_title,board_content,board_date,null);
	}

	public Board(int board_no, String board_title, String board_content, Date board_date, Employee emp_vo) {
		super();
		this.board_no = board_no;
		this.board_title = board_title;
		this.board_content = board_content;
		this.board_date = board_date;
		this.emp_vo = emp_vo;
	}

	public Board(int board_no, String board_title, Date board_date, Employee emp_vo) {
		
		this.board_no = board_no;
		this.board_title = board_title;
		this.board_date = board_date;
		this.emp_vo = emp_vo;
	}

	@Override
	public String toString() {
		return "Board [board_no=" + board_no + ", board_title=" + board_title + ", board_content=" + board_content
				+ ", board_date=" + board_date + ", emp_id=" + emp_vo + "]";
	}

	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public Date getBoard_date() {
		return board_date;
	}
	public void setBoard_date(Timestamp board_date) {
		this.board_date = board_date;
	}
	public Employee getEmp_vo() {
		return emp_vo;
	}
	public void setEmp_vo(Employee emp_vo) {
		this.emp_vo = emp_vo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
///////////////////////////////////////////////////////////////////////	
//	private int board_no;
//	private String board_title;
//	private String board_content;
//	private Date board_date;
//	//public List<BoardComment> board_comment_vo; // has a 관계 쓰려면 DAO에 JOIN문으로 게시글을 불러와야함
//	private Employee emp_vo;
//	
//	
//	public Board() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	
//	
//	public Board(int board_no, String board_title, String board_content) {
//		super();
//		this.board_no = board_no;
//		this.board_title = board_title;
//		this.board_content = board_content;
//		
//		
//	}
//	
//	public Board(int board_no, String board_title, String board_content, Date board_date) {
//		super();
//		this.board_no = board_no;
//		this.board_title = board_title;
//		this.board_content = board_content;
//		this.board_date = board_date;
//		
//	}
//
//
//	public Board(int board_no, String board_title, String board_content, Date board_date, Employee emp_vo) {
//		super();
//		this.board_no = board_no;
//		this.board_title = board_title;
//		this.board_content = board_content;
//		this.board_date = board_date;
//		this.emp_vo = emp_vo;
//	}
//
//
//	public Board(int board_no, String board_title, Date board_date, Employee emp_vo) {
//		
//		this.board_no = board_no;
//		this.board_title = board_title;
//		this.board_date = board_date;
//		this.emp_vo = emp_vo;
//	}
//
//
//	@Override
//	public String toString() {
//		return "Board [board_no=" + board_no + ", board_title=" + board_title + ", board_content=" + board_content
//				+ ", board_date=" + board_date + ", emp_vo=" + emp_vo + "]";
//	}
//
//
//	public int getBoard_no() {
//		return board_no;
//	}
//
//
//	public void setBoard_no(int board_no) {
//		this.board_no = board_no;
//	}
//
//
//	public String getBoard_title() {
//		return board_title;
//	}
//
//
//	public void setBoard_title(String board_title) {
//		this.board_title = board_title;
//	}
//
//
//	public String getBoard_content() {
//		return board_content;
//	}
//
//
//	public void setBoard_content(String board_content) {
//		this.board_content = board_content;
//	}
//
//
//	public Date getBoard_date() {
//		return board_date;
//	}
//
//
//	public void setBoard_date(Date board_date) {
//		this.board_date = board_date;
//	}
//
//
//	public Employee getEmp_vo() {
//		return emp_vo;
//	}
//
//
//	public void setEmp_vo(Employee emp_vo) {
//		this.emp_vo = emp_vo;
//	}
	
	
	

}