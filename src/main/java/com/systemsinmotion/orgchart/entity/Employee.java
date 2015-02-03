package com.systemsinmotion.orgchart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends BaseEntity {

	@Column(name = "FIRST_NAME", nullable = false, length = 20)
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 20)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 45)
	private String lastName;
	
	@Column(name = "EMAIL", nullable = true, unique = true, length = 100)
	private String email;
	
	@Column(name = "SKYPE_NAME", nullable = true, unique = true, length = 100)
	private String skypeName;
	
	@Column(name = "IS_MANAGER")
	private Boolean isManager = false;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JOB_TITLE_ID")
	private JobTitle jobTitle;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department department;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MANAGER_ID")
	private Employee manager;
	
	@Column(name = "MIDDLE_INITIAL")
	private Character middleInitial;
	
	public String getFirstName(){
		return firstName;
	}
	
	public Character getMiddleInitial(){
		return middleInitial;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getSkypeName(){
		return skypeName;
	}
	
	public JobTitle getJobTitle(){
		return jobTitle;
	}
	
	public Department getDepartment(){
		return department;
	}
	
	public Employee getManager(){
		return manager;
	}
	
	public Boolean getIsManager(){
		return isManager;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public void setMiddleInitial(Character middleInitial){
		this.middleInitial = middleInitial;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setSkypeName(String skypeName){
		this.skypeName = skypeName;
	}
	
	public void setIsManager(Boolean isManager){
		this.isManager = isManager;
	}
	
	public void setJobTitle(JobTitle jobTitle){
		this.jobTitle = jobTitle;
	}
	
	public void setDepartment(Department department){
		this.department = department;
	}
	
	public void setManager(Employee manager){
		this.manager = manager;
	}
	

}
