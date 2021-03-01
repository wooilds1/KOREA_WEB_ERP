package vo;

import java.sql.Timestamp;
import java.util.Date;

public class BoardComment {
	
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
	public BoardComment(int cmt_no, String emp_name, Date cmt_date, String cmt_content) {
		super();
		this.cmt_no = cmt_no;
		this.cmt_content = cmt_content;
		this.cmt_date = cmt_date;

	}

	public BoardComment(int cmt_no, String cmt_content, Date cmt_date, Employee emp_vo) {
		super();
		this.cmt_no = cmt_no;
		this.cmt_content = cmt_content;
		this.cmt_date = cmt_date;
		this.emp_vo = emp_vo;
	}

	@Override
	public String toString() {
		return "BoardComment [cmt_no=" + cmt_no + ", cmt_content=" + cmt_content + ", board_no=" + board_no
				+ ", cmt_date=" + cmt_date + ", emp_id=" + emp_vo.getEmp_id() + "]";
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