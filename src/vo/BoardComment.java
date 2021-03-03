package vo;

import java.sql.Timestamp;
import java.util.Date;

public class BoardComment {
	
	/*
	CMT_NO		NUMBER(4,0)			No		댓글번호(시퀀스)	PK
	EMP_ID		VARCHAR2(10 BYTE)	No		사원번호			FK
	CMT_CONTENT	VARCHAR2(500 BYTE)	Yes		댓글내용			
	BOARD_NO	NUMBER(4,0)			No		게시글번호			FK
	CMT_DATE	DATE				No		댓글작성일자
	*/

	private int cmt_no;
	private String cmt_content;
	private int board_no;
	private Date cmt_date;
	private Employee emp_vo;
	
	public BoardComment(int cmt_no, Employee emp_vo, String cmt_content, int board_no, Date cmt_date) {
		super();
		this.cmt_no = cmt_no;
		this.cmt_content = cmt_content;
		this.board_no = board_no;
		this.cmt_date = cmt_date;
		this.emp_vo = emp_vo;
	}
	
	public BoardComment() {
		super();
	}
	public BoardComment(String cmt_content) {
		super();
		this.cmt_content = cmt_content;
	}
	
	public BoardComment(String cmt_content,Employee emp_vo) {
		super();
		this.cmt_content = cmt_content;
		this.emp_vo = emp_vo;
	}
	
	public BoardComment(int cmt_no,String cmt_content) {
		super();
		this.cmt_content = cmt_content;
		this.cmt_no = cmt_no;
	}

	public BoardComment(int cmt_no, String cmt_content, Date cmt_date, Employee emp_vo) {
		super();
		this.cmt_no = cmt_no;
		this.cmt_content = cmt_content;
		this.cmt_date = cmt_date;
		this.emp_vo = emp_vo;
	}
	
	public BoardComment(int cmt_no, String cmt_content,int board_no, Date cmt_date, Employee emp_vo) {
		super();
		this.cmt_no = cmt_no;
		this.cmt_content = cmt_content;
		this.board_no=board_no;
		this.cmt_date = cmt_date;
		this.emp_vo = emp_vo;
	}	

	@Override
	public String toString() {
		return "BoardComment [cmt_no=" + cmt_no + ", cmt_content=" + cmt_content + ", board_no=" + board_no
				+ ", cmt_date=" + cmt_date + ", emp_id=" + emp_vo.getEmp_id() + ", emp_name=" + emp_vo.getName() + "]";
	}
	public int getCmt_no() {
		return cmt_no;
	}
	public void setCmt_no(int cmt_no) {
		this.cmt_no = cmt_no;
	}
	public String getCmt_content() {
		return cmt_content;
	}
	public void setCmt_content(String cmt_content) {
		this.cmt_content = cmt_content;
	}
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public Date getCmt_date() {
		return cmt_date;
	}
	public void setCmt_date(Timestamp cmt_date) {
		this.cmt_date = cmt_date;
	}
	public Employee getEmp_vo() {
		return emp_vo;
	}
	public void setEmp_vo(Employee emp_vo) {
		this.emp_vo = emp_vo;
	} 
	
}