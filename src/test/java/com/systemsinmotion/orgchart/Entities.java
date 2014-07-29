package com.systemsinmotion.orgchart;

import java.util.Random;

import com.systemsinmotion.orgchart.entity.Authorities;
import com.systemsinmotion.orgchart.entity.Department;
import com.systemsinmotion.orgchart.entity.Employee;
import com.systemsinmotion.orgchart.entity.JobTitle;
import com.systemsinmotion.orgchart.entity.SimpleEmployee;
import com.systemsinmotion.orgchart.entity.User;

public class Entities {

	public static final String DEPARTMENT_NAME = "Department";
	public static final Integer DEPT_ID = 22;
	public static final Integer PARENT_DEPT_ID = 24;
	public static final String EMAIL = "email";
	public static final Integer EMPLOYEE_ID = 5;
	public static final Integer SIMPLE_EMPLOYEE_ID = 3;
	public static final String FIRST_NAME = "firstName";
	public static final String JOB_TITLE = "Job Title";
	public static final Integer JOB_TITLE_ID = 5;
	public static final String JOB_TITLE_NAME = "JobTitle";
	public static final String LAST_NAME = "lastName";
	public static final Integer MANAGER_ID = 1;
	public static final String SKYPE_NAME = "skype name";
	public static final String FULL_NAME = FIRST_NAME + " " + LAST_NAME;
	
	public static final Integer EMPLOYEE_ID_2 = 7;
	public static final Integer SIMPLE_EMPLOYEE_ID_2 = 8;
	public static final String FIRST_NAME_2 = "1stName";
	public static final String LAST_NAME_2 = "2ndName";
	public static final String EMAIL_2 = "mail@google.com";
	public static final String SKYPE_NAME_2 = "name.skype.dot";
	public static final String FULL_NAME_2 = FIRST_NAME_2 + " " + LAST_NAME_2;

	public static final Integer EMPLOYEE_ID_3 = 9;
	public static final Integer SIMPLE_EMPLOYEE_ID_3 = 10;
	public static final String FIRST_NAME_3 = "1stNameAgain";
	public static final String LAST_NAME_3 = "2ndNameAgain";
	public static final String EMAIL_3 = "no-reply@google.com";
	public static final String SKYPE_NAME_3 = "error.skype.dot";
	public static final String FULL_NAME_3 = FIRST_NAME_3 + " " + LAST_NAME_3;

	public static final String USER_NAME = "userName";
	public static final String USER_PASSWORD = "password";
	
	public static final String AUTHORITY = "authority";
	
	public static final String NOT_PRESENT_VALUE = "XXX";
	public static final Integer NOT_PRESENT_ID = -666;
	

	private static Random random = new Random();

	public static Department department() {
		Department department = new Department();
		department.setName(departmentName());
		department.setIsActive(true);
		return department;
	}

	public static Department department(Integer id) {
		Department department = department();
		department.setId(id);
		return department;
	}
	
	public static Department department(Department parent) {
		Department department = department();
		department.setParentDepartment(parent);
		return department;
	}

	private static String departmentName() {
		return DEPARTMENT_NAME + random.nextInt();
	}

	public static Employee employee(Department dept) {
		Employee emp = employee();
		emp.setFirstName(FIRST_NAME);
		emp.setLastName(LAST_NAME);
		emp.setEmail(EMAIL + random.nextInt());
		emp.setSkypeName(SKYPE_NAME + random.nextInt());
		emp.setIsActive(true);
		emp.setIsManager(false);
		return emp;
	}

	public static Employee employee() {
		Employee emp = new Employee();
		emp.setDepartment(department());
		emp.setFirstName(FIRST_NAME);
		emp.setLastName(LAST_NAME);
		emp.setEmail(EMAIL + random.nextInt());
		emp.setSkypeName(SKYPE_NAME + random.nextInt());
		emp.setIsActive(true);
		emp.setIsManager(false);
		return emp;
	}
	
	public static Employee employee2() {
		Employee emp = new Employee();
		emp.setDepartment(department());
		emp.setFirstName(FIRST_NAME_2);
		emp.setLastName(LAST_NAME_2);
		emp.setEmail(EMAIL_2 + random.nextInt());
		emp.setSkypeName(SKYPE_NAME_2 + random.nextInt());
		emp.setIsActive(true);
		return emp;
	}

	public static Employee employee3() {
		Employee emp = new Employee();
		emp.setDepartment(department());
		emp.setFirstName(FIRST_NAME_3);
		emp.setLastName(LAST_NAME_3);
		emp.setEmail(EMAIL_3 + random.nextInt());
		emp.setSkypeName(SKYPE_NAME_3 + random.nextInt());
		emp.setIsActive(true);
		return emp;
	}

	public static JobTitle jobTitle() {
		JobTitle jobTitle = new JobTitle();
		jobTitle.setName(JOB_TITLE_NAME + random.nextInt());
		jobTitle.setIsActive(true);
		return jobTitle;
	}

	public static Employee manager() {
		Employee mgr = new Employee();
		mgr.setFirstName(FIRST_NAME);
		mgr.setLastName(LAST_NAME);
		mgr.setEmail(EMAIL + random.nextInt());
		mgr.setSkypeName(SKYPE_NAME + random.nextInt());
		mgr.setIsActive(true);
		mgr.setIsManager(true);
		return mgr;
	}

	public static JobTitle jobTitle(Integer jobTitleId) {
		JobTitle title = jobTitle();
		title.setId(JOB_TITLE_ID);
		title.setIsActive(true);
		return title;
	}

	public static Employee employee(Integer employeeId) {
		Employee emp = employee();
		emp.setId(EMPLOYEE_ID);
		emp.setIsActive(true);
		return emp;
	}

	public static Employee employee2(Integer employeeId) {
		Employee emp = employee2();
		emp.setId(employeeId);
		emp.setIsActive(true);
		return emp;
	}

	public static Employee employee3(Integer employeeId) {
		Employee emp = employee3();
		emp.setId(employeeId);
		emp.setIsActive(true);
		return emp;
	}

	public static SimpleEmployee simpleEmployee() {
		SimpleEmployee emp = new SimpleEmployee();
		emp.setFirstName(FIRST_NAME);
		emp.setLastName(LAST_NAME);
		emp.setIsActive(true);
		return emp;
	}

	public static SimpleEmployee simpleEmployee2() {
		SimpleEmployee emp = new SimpleEmployee();
		emp.setFirstName(FIRST_NAME_2);
		emp.setLastName(LAST_NAME_2);
		emp.setIsActive(true);
		return emp;		
	}

	public static SimpleEmployee simpleEmployee(Integer employeeId) {
		SimpleEmployee emp = simpleEmployee();
		emp.setId(employeeId);
		emp.setIsActive(true);
		return emp;
	}

	public static SimpleEmployee simpleEmployee2(Integer employeeId) {
		SimpleEmployee emp = simpleEmployee2();
		emp.setId(employeeId);
		emp.setIsActive(true);
		return emp;
	}
	
	public static User user() {
		User user = new User();
		user.setUserName(USER_NAME);
		user.setPassword(USER_PASSWORD);
		user.setEnabled(true);
		return user;
	}

	public static Authorities authority() {
		Authorities auth = new Authorities();
		auth.setAuthority(AUTHORITY);
		return auth;
	}
	
	public static Authorities authority(User user) {
		Authorities auth = authority();
		auth.setUserName(user.getUserName());
		return auth;
	}
}
