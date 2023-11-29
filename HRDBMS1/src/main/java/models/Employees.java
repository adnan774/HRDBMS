package models;
public class Employees {
	private Departments departments;
	private Salaries salaries;
	
	public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }
    
    public Salaries getSalaries() {
        return salaries;
    }

    public void setSalaries(Salaries salaries) {
        this.salaries = salaries;
    }
	
   public Employees(int emp_id, String first_name, String last_name, String email, 
		   String date_of_birth, String phone_number, String hire_date, 
		   String address, String city, String town, String post_code, Departments department, Salaries salary ) {
	   
		super();
		this.emp_id = emp_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.date_of_birth = date_of_birth;
		this.phone_number = phone_number;
		this.hire_date = hire_date;
		this.address = address;
		this.city = city;
		this.town = town;
		this.post_code = post_code;
		this.departments = department;
	    this.salaries = salary;
	}
   
public Employees() {
	super();
}
	int emp_id;
	String first_name;
	String last_name;
	String email;
	String date_of_birth;
	String phone_number;
	String hire_date;
	String address;
	String city;
	String town; 
	String post_code;
	
public int getEmp_id() {
	return emp_id;
}
public void setEmp_id(int emp_id) {
	this.emp_id = emp_id;
}
public String getFirst_name() {
	return first_name;
}
public void setFirst_name(String first_name) {
	this.first_name = first_name;
}

public String getLast_name() {
	return last_name;
}
public void setLast_name(String last_name) {
	this.last_name = last_name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getDob() {
	return date_of_birth;
}
public void setDob(String date_of_birth) {
	this.date_of_birth = date_of_birth;
}
public String getPhone_number() {
	return phone_number;
}
public void setPhone_number(String phone_number) {
	this.phone_number = phone_number;
}
public String getHire_date() {
	return hire_date;
}
public void setHire_date(String hire_date) {
	this.hire_date = hire_date;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getTown() {
	return town;
}
public void setTown(String town) {
	this.town = town;
}
public String getPost_code() {
	return post_code;
}
public void setPost_code(String post_code) {
	this.post_code = post_code;
}
@Override
public String toString() {
	return "Employees [emp_id=" + emp_id + ", first_name=" + first_name + ", last_name=" + last_name
			+ ", email=" + email + ", date_of_birth=" + date_of_birth + ", phone_number=" + phone_number + ", hire_date="
			+ hire_date + ", address=" + address + ", city=" + city + ", town=" + town 
			+ ", post_code=" + post_code +"]";
}   
}