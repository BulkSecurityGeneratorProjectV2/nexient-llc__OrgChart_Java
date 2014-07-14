package com.systemsinmotion.orgchart.web.controller;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.systemsinmotion.orgchart.entity.Department;
import com.systemsinmotion.orgchart.entity.Employee;
import com.systemsinmotion.orgchart.entity.JobTitle;
import com.systemsinmotion.orgchart.service.DepartmentService;
import com.systemsinmotion.orgchart.service.EmployeeService;
import com.systemsinmotion.orgchart.service.JobTitleService;
import com.systemsinmotion.orgchart.web.View;

@Controller
public class DefaultController {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(DefaultController.class);

	@Autowired
	EmployeeService employeeService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	JobTitleService jobTitleService;
	

	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String doGet() {
		return View.HOME;
	}
	
	@RequestMapping(value = "depts", method = RequestMethod.GET)
	public String doDepartments_GET(Model model) {
		refreshDepartmentModel(model);
		return View.DEPARTMENTS;
	}
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@RequestMapping(value = "newDepart", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String doDepartmentNew_POST(Department department, Model model) {
		departmentService.storeDepartment(department);
		refreshDepartmentModel(model);
		return View.DEPARTMENTS;
	}

	@RequestMapping(value = "updateDepart", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
//	public void doDepartments_POST(Department department, @RequestParam(value = "doDelete", defaultValue="false") boolean doDelete, Model model) {
	public String doDepartmentUpdate_POST(Department department, Model model) {
		departmentService.storeDepartment(department);
		refreshDepartmentModel(model);
		return View.DEPARTMENTS;
	}

	@RequestMapping(value = "depart/delete/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void doDepartmentDelete_DELETE(@PathVariable("id") Integer deptId, Model model)
	{
		departmentService.removeDepartment(departmentService.findDepartmentByID(deptId));
		refreshDepartmentModel(model);
	}
	
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@RequestMapping(value = "emps", method = RequestMethod.GET)
	public String doEmployees_GET(@RequestParam(value = "filterName", defaultValue="") String fullName,
			@RequestParam(value = "deptid", defaultValue="") String deptid,
			@RequestParam(value = "jobid", defaultValue="") String jobid,
			String string4,
			Model model) {
		refreshAllModels(model);
		return View.EMPLOYEES;
	}
	
	@RequestMapping(value = "searchemps", method = RequestMethod.GET)
	public String doSearchEmployees_GET(@RequestParam(value = "filterName", defaultValue="") String fullName,
			@RequestParam(value = "deptid", defaultValue="") String deptId,
			@RequestParam(value = "jobid", defaultValue="") String jobId,
			String string4,
			Model model) {
		List<Employee> employees = getFilteredEmployees(fullName, deptId, jobId);
		model.addAttribute("emps", employees);
		refreshJobTitleModel(model);
		refreshDepartmentModel(model);
		return View.EMPLOYEES;
	}
	
	@RequestMapping(value = "searchEmployeeName/{name}", method = RequestMethod.GET)
	public @ResponseBody String doSearchEmployees_GET(@PathVariable("name") String fullName) {
		List<Employee> employees = getFilteredEmployees(fullName, "", "");
		return employeeService.putCommaDelimitersInAListOfEmployees(employees);
	}

	@RequestMapping(value = "emp/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> doEmployeeDelete_DELETE(@PathVariable("id") Integer empId, Model model)
	{
		employeeService.removeEmployee(employeeService.findEmployeeByID(empId));
		refreshAllModels(model);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

	// Solution 1: Readable and few lines of code, but requires 2+ queries
	private List<Employee> getFilteredEmployees(String fullName, String deptId,
			String jobId) {
		String[] name = fullName.split("\\s");
		List<Employee> employees = employeeService.findAllActiveEmployees();
		if (name[0].length() > 0)
			employees.retainAll(employeeService.findEmployeesByFirstName(name[0]));
		if (name.length > 1 && name[1].length() > 0)
			employees.retainAll(employeeService.findEmployeesByLastName(name[1]));
		if (deptId.length() > 0)
			employees.retainAll(employeeService.findEmployeesByDepartment(departmentService.findDepartmentByID(Integer.parseInt(deptId))));
		if (jobId.length() > 0)
			employees.retainAll(employeeService.findEmployeesByJobTitle(jobTitleService.findJobTitleByID(Integer.parseInt(jobId))));

		return employees;
	}
	
	// Solution 2: Query Criteria
//	private List<Employee> getFilteredEmployees(String fullName, String deptId, String jobId)
//	{
//		return null;
//	}

	@RequestMapping(value = "newEmp", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String doEmployeeNew_POST(Employee employee, Model model) {
		employeeService.storeEmployee(employee);
		refreshAllModels(model);
		return View.EMPLOYEES;
	}

	@RequestMapping(value = "updateEmp", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String doEmployeeUpdate_POST(Employee employee, Model model) {
		employeeService.storeEmployee(employee);
		refreshAllModels(model);
		return View.EMPLOYEES;
	}

	public void setJobTitleService(JobTitleService jobTitleService) {
		this.jobTitleService = jobTitleService;
	}

	@RequestMapping(value = "jobs", method = RequestMethod.GET)
	public String doJobTitles_GET(Model model) {
		refreshJobTitleModel(model);
		return View.JOB_TITLES;
	}

	@RequestMapping(value = "newJob", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String doJobTitleNew_POST(JobTitle jobTitle, Model model) {
		jobTitleService.storeJobTitle(jobTitle);
		refreshJobTitleModel(model);
		return View.JOB_TITLES;
	}

	@RequestMapping(value = "updateJob", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String doJobTitleUpdate_POST(JobTitle jobTitle, Model model) {
		jobTitleService.storeJobTitle(jobTitle);
		refreshJobTitleModel(model);
		return View.JOB_TITLES;
	}
	
	@RequestMapping(value = "job/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> doJobTitleDelete_DELETE(@PathVariable("id") Integer jobId, Model model)
	{
		jobTitleService.removeJobTitle(jobTitleService.findJobTitleByID(jobId));
		refreshAllModels(model);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

	private void refreshAllModels(Model model) {
		refreshDepartmentModel(model);
		refreshJobTitleModel(model);
		refreshEmployeeModel(model);
	}
	
	private void refreshJobTitleModel(Model model) {
		List<JobTitle> titles = jobTitleService.findAllActiveJobTitles();
		model.addAttribute("jobs", titles);
	}
	
	private void refreshEmployeeModel(Model model) {
		List<Employee> employees = employeeService.findAllActiveEmployees();
		model.addAttribute("emps", employees);
		model.addAttribute("allEmps", employees);
	}

	private void refreshDepartmentModel(Model model) {
		List<Department> departments = departmentService.findAllActiveDepartments();
		 model.addAttribute("depts", departments);
	}
}
