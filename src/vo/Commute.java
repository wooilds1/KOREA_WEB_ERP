package vo;

import java.sql.Timestamp;

public class Commute {
	
//	public String emp_id;   //Employee 포함 
	private Timestamp start_time;
	private Timestamp end_time;
	
	private Employee emp_vo;


	public Commute() {
		super();
	}

	public Commute(Timestamp start_time, Timestamp end_time) {
		super();
		this.start_time = start_time;
		this.end_time = end_time;
	}


	public Commute(Timestamp start_time, Timestamp end_time, Employee emp_vo) {
		super();
		this.start_time = start_time;
		this.end_time = end_time;
		this.emp_vo = emp_vo;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	public Employee getEmp_vo() {
		return emp_vo;
	}

	public void setEmp_vo(Employee emp_vo) {
		this.emp_vo = emp_vo;
	}

	@Override
	public String toString() {
		return "Commute [start_time=" + start_time + ", end_time=" + end_time + ", emp_vo=" + emp_vo + "]";
	} 
	
}