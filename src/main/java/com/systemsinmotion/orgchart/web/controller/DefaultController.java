package com.systemsinmotion.orgchart.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.systemsinmotion.orgchart.entity.Department;
import com.systemsinmotion.orgchart.entity.Employee;
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
	// uncomment when database connection is set up. will throw error when
	// run
	List<Department> departments = departmentService.findAllDepartments();
	model.addAttribute("dept", new Department());
	model.addAttribute("depts", departments);
	return View.DEPARTMENTS;
    }

    public void setDepartmentService(DepartmentService departmentService) {
	this.departmentService = departmentService;
    }

    /**
     * 
     * @param dept
     * @param parentDepartmentId
     * @param model
     * @return
     */
    @RequestMapping(value = "depts", method = RequestMethod.POST)
    public String doDepartments_POST(Integer id, String name, Integer parentDepartmentId, Model model) {
	
	Department dept = departmentService.findDepartmentByID(id);
        dept.setName(name);
	
        if(parentDepartmentId != null) {
            Department parentDepartment = this.departmentService.findDepartmentByID(parentDepartmentId);
            dept.setParentDepartment(parentDepartment);
        } else {
            dept.setParentDepartment(null);
        }
        
        departmentService.storeDepartment(dept);
        List<Department> departments = departmentService.findAllDepartments();
        
        /*
         * This is the same depts var that is in the departments.jsp file
         */
        model.addAttribute("depts", departments);
        return View.DEPARTMENTS;
    }
    
    @RequestMapping(value = "emps", method = RequestMethod.POST)
    public String doEmployees_POST(Integer id, String name, Integer parentDepartmentId, Model model) {
	
//	Department dept = departmentService.findDepartmentByID(id);
//        dept.setName(name);
//	
//        if(parentDepartmentId != null) {
//            Department parentDepartment = this.departmentService.findDepartmentByID(parentDepartmentId);
//            dept.setParentDepartment(parentDepartment);
//        } else {
//            dept.setParentDepartment(null);
//        }
//        
//        departmentService.storeDepartment(dept);
//        List<Department> departments = departmentService.findAllDepartments();
//        
//        /*
//         * This is the same depts var that is in the departments.jsp file
//         */
//        model.addAttribute("depts", departments);
//        return View.DEPARTMENTS;
	return View.EMPLOYEES;
    }
    
    @RequestMapping(value = "emps", method = RequestMethod.GET)
    public String doEmployees_GET(Model model) {
	// uncomment when database connection is set up. will throw error when
	// run
	List<Employee> employees = employeeService.findAllEmployees();
//	model.addAttribute("dept", new Department());
	model.addAttribute("emps", employees);
	return View.EMPLOYEES;
    }

}
