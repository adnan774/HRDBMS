package models;

public class Salaries {
	
   public Salaries(int salary_id, String job_title, Float salary ) {
	   
		super();
		this.salary_id = salary_id;
		this.job_title = job_title;
		this.salary = salary;
	}
   
   
public Salaries() {
	super();
}

	int salary_id;
	String job_title;
	Float salary;


public int getSalary_id() {
	return salary_id;
}
public void setSalary_id(int salary_id) {
	this.salary_id = salary_id;
}
public String getJob_title() {
	return job_title;
}
public void setJob_title(String job_title) {
	this.job_title = job_title;
}

public Float getSalary() {
	return salary;
}
public void setSalary(Float salary) {
	this.salary = salary;
}

@Override
public String toString() {
	return "Salaries [salary_id=" + salary_id + ", job_title=" + job_title + ", salary=" + salary +"]";
}   
}