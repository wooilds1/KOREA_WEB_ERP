package vo;

import java.util.Date;

public class Employee {

	private String emp_id;
	private String position;
	private Date hire_date;
	private String office_tel;
	private String password;
	private String name;
	private String email;
	private Date birth_date;
	private String h_phone;
	private int status;
	private String address;
	private AnnualLeave a;
	private Department d;
	
	public Employee() {
		super();
	}
	
	public Employee(String id, String position, Date hire_date, String office_tel, String password, String name,
			String email, Date birth_date, String h_phone, int status, String address, AnnualLeave a, Department d) {
		super();
		this.emp_id = id;
		this.position = position;
		this.hire_date = hire_date;
		this.office_tel = office_tel;
		this.password = password;
		this.name = name;
		this.email = email;
		this.birth_date = birth_date;
		this.h_phone = h_phone;
		this.status = status;
		this.address = address;
		this.a = a;
		this.d = d;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + emp_id + ", position=" + position + ", hire_date=" + hire_date + ", office_tel="
				+ office_tel + ", password=" + password + ", name=" + name + ", email=" + email + ", birth_date="
				+ birth_date + ", h_phone=" + h_phone + ", status=" + status + ", address=" + address + ", a=" + a
				+ ", d=" + d + "]";
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String id) {
		this.emp_id = id;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Date getHire_date() {
		return hire_date;
	}
	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}
	public String getOffice_tel() {
		return office_tel;
	}
	public void setOffice_tel(String office_tel) {
		this.office_tel = office_tel;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}
	public String getH_phone() {
		return h_phone;
	}
	public void setH_phone(String h_phone) {
		this.h_phone = h_phone;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public AnnualLeave getA() {
		return a;
	}
	public void setA(AnnualLeave a) {
		this.a = a;
	}
	public Department getD() {
		return d;
	}
	public void setD(Department d) {
		this.d = d;
	}
}	