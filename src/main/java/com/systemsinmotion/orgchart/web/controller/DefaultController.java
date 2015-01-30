package com.systemsinmotion.orgchart.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
		 List<Department> departments = departmentService.findAllDepartments();
		 model.addAttribute("depts", departments);
		return View.DEPARTMENTS;
	}

	@RequestMapping(value = "emps", method = RequestMethod.GET)
	public String doEmployees_GET(Employee employee, Model model) {		
		List<Employee> employees = employeeService.findAllEmployees();
		List<Department> departments = departmentService.findAllDepartments();
		List<JobTitle> jobTitle = jobTitleService.findAllJobTitles();
		 model.addAttribute("emps", employees);
		 model.addAttribute("depts", departments);
		 model.addAttribute("jobTitles", jobTitle);
		return View.EMPLOYEES;
	}
	
	@RequestMapping(value = "jobs", method = RequestMethod.GET)
	public String doJobTitles_GET(Model model) {
		 List<JobTitle> jobTitle = jobTitleService.findAllJobTitles();
		 model.addAttribute("jobs", jobTitle);
		return View.JOB_TITLES;
	}
	
	@RequestMapping(value = "dept/{id}", method = RequestMethod.GET)
	public @ResponseBody String getDeptAjax(@PathVariable("id") Integer id) {
		Department dept = departmentService.findDepartmentByID(id);
		if (dept == null) {
			return "";
		}
		return dept.toString();
	}

	@RequestMapping(value = "delete/dept/{id}", method = RequestMethod.GET)
	public @ResponseBody String deleteDeptAjax(@PathVariable("id") Integer id) {
		Department dept = departmentService.findDepartmentByID(id);
		if (dept == null) {
			return "";
		}
		dept.setIsActive(false);
		departmentService.storeDepartment(dept);
		return dept.toString();
	}	
	
	@RequestMapping(value = "emp/{id}", method = RequestMethod.GET)
	public @ResponseBody String getEmpAjax(@PathVariable("id") Integer id) {
		Employee emp = employeeService.findEmployeeByID(id);
		if (emp == null) {
			return "";
		}
		return emp.toString();
	}
	
	@RequestMapping(value = "suggestions/{firstName}", method = RequestMethod.GET)
	public @ResponseBody String getEmployeeName(@PathVariable("firstName") String name) {		
		String returnValue = "";
		List<Employee> suggestions = employeeService.getEmployeeSuggestions(name);
		
		if(suggestions != null) {
			for (int i = 0; i < suggestions.size(); i++)
				returnValue += suggestions.get(i).getFirstName() + " " + suggestions.get(i).getLastName() + ",";
			return returnValue;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "job/{id}", method = RequestMethod.GET)
	public @ResponseBody String getJobAjax(@PathVariable("id") Integer id) {
		JobTitle jobTitle = jobTitleService.findJobTitleByID(id);
		if (jobTitle == null) {
			return "";
		}
		return jobTitle.toString();
	}
	
	

	@RequestMapping(value = "depts", method = RequestMethod.POST)
	public String doDepartments_POST(Department department, Integer parent_id, Model model) {
		
		if(parent_id != null){	
			department.setParentDepartment(departmentService.findDepartmentByID(parent_id));		
			if(department.getParentDepartment().getId().equals(department.getId())){
				List<Department> departments = departmentService.findAllActiveDepartments();
				model.addAttribute("depts", departments);
				return View.DEPARTMENTS;
			}	
		}
		departmentService.storeDepartment(department);
		 List<Department> departments = departmentService.findAllDepartments();
		 model.addAttribute("depts", departments);
		return View.DEPARTMENTS;
	}
	
	@RequestMapping(value = "emps", method = RequestMethod.POST)
	public String doEmployees_POST(Employee employee, Integer departmentId, Integer jobTitleId, Boolean search, Model model) {
		
			List<Department> departments = departmentService.findAllDepartments();
			List<JobTitle> jobTitle = jobTitleService.findAllJobTitles();
			model.addAttribute("depts", departments);
			model.addAttribute("jobTitles", jobTitle);
			
			if(departmentId != null){
				employee.setDepartment(departmentService.findDepartmentByID(departmentId));
			}
			if(jobTitleId != null){
				employee.setJobTitle(jobTitleService.findJobTitleByID(jobTitleId));
			}
			if(search != null && search == true){
					String nameArr[] = employee.getFirstName().split(" ");
					
					if(nameArr.length == 1) {
						if(nameArr[0].equals("")){
							employee.setFirstName(null);
						}
						else{
							employee.setFirstName(nameArr[0]);
						}
					}
					else if(nameArr.length == 2) {
						employee.setFirstName(nameArr[0]);
						employee.setLastName(nameArr[1]);
					}
					List<Employee> employees = employeeService.employeeSearch(employee);
					
					model.addAttribute("emps", employees);
					return View.EMPLOYEES;					
			}
			
			employeeService.storeEmployee(employee);
			List<Employee> employees = employeeService.findAllEmployees();
			 model.addAttribute("emps", employees);
			return View.EMPLOYEES;
	}
	
	@RequestMapping(value = "jobs", method = RequestMethod.POST)
	public String doJobTitles_POST(JobTitle jobTitle, Model model) {
		jobTitleService.storeJobTitle(jobTitle);
		 List<JobTitle> jobTitles = jobTitleService.findAllJobTitles();
		 model.addAttribute("jobs", jobTitles);
		return View.JOB_TITLES;
	}
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setEmployeeService(EmployeeService employeeService){
		this.employeeService = employeeService;
	}

	public void setJobTitleService(JobTitleService jobTitleService){
		this.jobTitleService = jobTitleService;
	}
}
