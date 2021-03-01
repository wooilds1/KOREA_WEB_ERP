package vo;

import java.util.Date;

public class Salary {

	private String emp_id; //조회용으로 살려둠
	private Date salary_date;
	private int after_tax_salary;
	private int income_tax;
	private int hire_insurance;
	private int total_deduction;
	private int extra_pay;
	private int position_pay;
	private int before_tax_salary;
	private int local_income_tax;
	private int health_insurance;
	private int national_pension;
	
	public Salary() {
		super();
	
	}
	
	@Override
	public String toString() {
		return "Salary [salary_date=" + salary_date + ", after_tax_salary=" + after_tax_salary + ", income_tax="
				+ income_tax + ", hire_insurance=" + hire_insurance + ", total_deduction=" + total_deduction
				+ ", extra_pay=" + extra_pay + ", position_pay=" + position_pay + ", before_tax_salary="
				+ before_tax_salary + ", local_income_tax=" + local_income_tax + ", health_insurance="
				+ health_insurance + ", national_pension=" + national_pension + "]";
	}

	public Salary(Date salary_date, int after_tax_salary, int income_tax, int hire_insurance, int total_deduction,
			int extra_pay, int position_pay, int before_tax_salary, int local_income_tax, int health_insurance,
			int national_pension) {
		this.salary_date = salary_date;
		this.after_tax_salary = after_tax_salary;
		this.income_tax = income_tax;
		this.hire_insurance = hire_insurance;
		this.total_deduction = total_deduction;
		this.extra_pay = extra_pay;
		this.position_pay = position_pay;
		this.before_tax_salary = before_tax_salary;
		this.local_income_tax = local_income_tax;
		this.health_insurance = health_insurance;
		this.national_pension = national_pension;
	}
	public Date getSalary_date() {
		return salary_date;
	}
	public void setSalary_date(Date salary_date) {
		this.salary_date = salary_date;
	}
	public int getAfter_tax_salary() {
		return after_tax_salary;
	}
	public void setAfter_tax_salary(int after_tax_salary) {
		this.after_tax_salary = after_tax_salary;
	}
	public int getIncome_tax() {
		return income_tax;
	}
	public void setIncome_tax(int income_tax) {
		this.income_tax = income_tax;
	}
	public int getHire_insurance() {
		return hire_insurance;
	}
	public void setHire_insurance(int hire_insurance) {
		this.hire_insurance = hire_insurance;
	}
	public int getTotal_deduction() {
		return total_deduction;
	}
	public void setTotal_deduction(int total_deduction) {
		this.total_deduction = total_deduction;
	}
	public int getExtra_pay() {
		return extra_pay;
	}
	public void setExtra_pay(int extra_pay) {
		this.extra_pay = extra_pay;
	}
	public int getPosition_pay() {
		return position_pay;
	}
	public void setPosition_pay(int position_pay) {
		this.position_pay = position_pay;
	}
	public int getBefore_tax_salary() {
		return before_tax_salary;
	}
	public void setBefore_tax_salary(int before_tax_salary) {
		this.before_tax_salary = before_tax_salary;
	}
	public int getLocal_income_tax() {
		return local_income_tax;
	}
	public void setLocal_income_tax(int local_income_tax) {
		this.local_income_tax = local_income_tax;
	}
	public int getHealth_insurance() {
		return health_insurance;
	}
	public void setHealth_insurance(int health_insurance) {
		this.health_insurance = health_insurance;
	}
	public int getNational_pension() {
		return national_pension;
	}
	public void setNational_pension(int national_pension) {
		this.national_pension = national_pension;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	
	// 당장 sql구문 비교했을때는 필요하지 않음
	// 추후 확장성 ex)인사정보-사원별 급여조회  등
	//private Employee emp_vo; 
	}
