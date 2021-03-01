package vo;

import java.util.Date;


public class EmployeeSchedule {

	private int emp_schedule_no;
	private String emp_title;
	private int emp_task_status;
	private Date emp_task_start;
	private Date emp_task_end;
	private String emp_content;
	private Employee emp_vo;

	public EmployeeSchedule() {
		super();
	}
	
	public EmployeeSchedule(int emp_schedule_no, String emp_title, int emp_task_status, Date start,
			Date end, String emp_content, Employee emp_vo) {
		this.emp_schedule_no = emp_schedule_no;
		this.emp_title = emp_title;
		this.emp_task_status = emp_task_status;
		this.emp_task_start = start;
		this.emp_task_end = end;
		this.emp_content = emp_content;
		this.emp_vo = emp_vo;
	}

	@Override
	public String toString() {
		return "EmployeeSchedule [emp_schedule_no=" + emp_schedule_no + ", emp_title=" + emp_title
				+ ", emp_task_status=" + emp_task_status + ", emp_task_start=" + emp_task_start + ", emp_task_end="
				+ emp_task_end + ", emp_content=" + emp_content + ", emp_id=" + emp_vo.getEmp_id() + "]";
	}

	public int getEmp_schedule_no() {
		return emp_schedule_no;
	}

	public void setEmp_schedule_no(int emp_schedule_no) {
		this.emp_schedule_no = emp_schedule_no;
	}

	public String getEmp_title() {
		return emp_title;
	}

	public void setEmp_title(String emp_title) {
		this.emp_title = emp_title;
	}

	public int getEmp_task_status() {
		return emp_task_status;
	}

	public void setEmp_task_status(int emp_task_status) {
		this.emp_task_status = emp_task_status;
	}

	public Date getEmp_task_start() {
		return emp_task_start;
	}

	public void setEmp_task_start(Date emp_task_start) {
		this.emp_task_start = emp_task_start;
	}

	public Date getEmp_task_end() {
		return emp_task_end;
	}

	public void setEmp_task_end(Date emp_task_end) {
		this.emp_task_end = emp_task_end;
	}

	public String getEmp_content() {
		return emp_content;
	}

	public void setEmp_content(String emp_content) {
		this.emp_content = emp_content;
	}

	public Employee getEmp_vo() {
		return emp_vo;
	}

	public void setEmp_vo(Employee emp_vo) {
		this.emp_vo = emp_vo;
	}
	
}