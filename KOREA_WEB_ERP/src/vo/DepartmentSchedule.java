package vo;

import java.util.Date;

public class DepartmentSchedule {
	
	private int dept_schedule_no;
	private String dept_title;
	private String dept_content;
	private Date dept_task_start;
	private Date dept_task_end;
	
	private Employee emp_vo;
	private Department dept_vo;
	
	public DepartmentSchedule() {
	}

	public DepartmentSchedule(int dept_schedule_no, String dept_title, Date dept_task_start, Date dept_task_end,
			String dept_content, Employee emp_vo, Department dept_vo) {
		super();
		this.dept_schedule_no = dept_schedule_no;
		this.dept_title = dept_title;
		this.dept_task_start = dept_task_start;
		this.dept_task_end = dept_task_end;
		this.dept_content = dept_content;
		this.emp_vo = emp_vo;
		this.dept_vo = dept_vo;
	}

	@Override
	public String toString() {
		return "DepartmentSchedule [dept_schedule_no=" + dept_schedule_no + ", dept_title=" + dept_title
				+ ", dept_task_start=" + dept_task_start + ", dept_task_end=" + dept_task_end + ", dept_content="
				+ dept_content + ", emp_vo=" + emp_vo + ", dept_vo=" + dept_vo + "]";
	}
	
	public int getDept_schedule_no() {
		return dept_schedule_no;
	}


	public void setDept_schedule_no(int dept_schedule_no) {
		this.dept_schedule_no = dept_schedule_no;
	}


	public String getDept_title() {
		return dept_title;
	}


	public void setDept_title(String dept_title) {
		this.dept_title = dept_title;
	}


	public Date getDept_task_start() {
		return dept_task_start;
	}


	public void setDept_task_start(Date dept_task_start) {
		this.dept_task_start = dept_task_start;
	}


	public Date getDept_task_end() {
		return dept_task_end;
	}


	public void setDept_task_end(Date dept_task_end) {
		this.dept_task_end = dept_task_end;
	}


	public String getDept_content() {
		return dept_content;
	}


	public void setDept_content(String dept_content) {
		this.dept_content = dept_content;
	}


	public Employee getEmp_vo() {
		return emp_vo;
	}


	public void setEmp_vo(Employee emp_vo) {
		this.emp_vo = emp_vo;
	}


	public Department getDept_vo() {
		return dept_vo;
	}


	public void setDept_vo(Department dept_vo) {
		this.dept_vo = dept_vo;
	}
}