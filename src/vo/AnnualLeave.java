package vo;

public class AnnualLeave {

	private String emp_id;
	private String apply_year;
	private int max_day;
	private int used_day;
	
	public AnnualLeave() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AnnualLeave(String emp_id, String apply_year, int max_day, int used_day) {
		super();
		this.emp_id = emp_id;
		this.apply_year = apply_year;
		this.max_day = max_day;
		this.used_day = used_day;
	}
	
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getApply_year() {
		return apply_year;
	}
	public void setApply_year(String apply_year) {
		this.apply_year = apply_year;
	}
	public int getMax_day() {
		return max_day;
	}
	public void setMax_day(int max_day) {
		this.max_day = max_day;
	}
	public int getUsed_day() {
		return used_day;
	}
	public void setUsed_day(int used_day) {
		this.used_day = used_day;
	}
	@Override
	public String toString() {
		return "AnnualLeave [emp_id=" + emp_id + ", apply_year=" + apply_year + ", max_day=" + max_day + ", used_day="
				+ used_day + "]";
	}
	
	
}