package com.systemsinmotion.orgchart.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.systemsinmotion.orgchart.Entities;
import com.systemsinmotion.orgchart.data.AuthorityRepository;
import com.systemsinmotion.orgchart.data.DepartmentRepository;
import com.systemsinmotion.orgchart.data.EmployeeRepository;
import com.systemsinmotion.orgchart.data.JobTitleRepository;
import com.systemsinmotion.orgchart.data.UsersRepository;
import com.systemsinmotion.orgchart.entity.Authorities;
import com.systemsinmotion.orgchart.entity.Department;
import com.systemsinmotion.orgchart.entity.Employee;
import com.systemsinmotion.orgchart.entity.JobTitle;
import com.systemsinmotion.orgchart.entity.Users;
import com.systemsinmotion.orgchart.service.AuthoritiesService;
import com.systemsinmotion.orgchart.service.DepartmentService;
import com.systemsinmotion.orgchart.service.EmployeeService;
import com.systemsinmotion.orgchart.service.JobTitleService;
import com.systemsinmotion.orgchart.service.UsersService;

@Configuration
@ComponentScan({ "com.systemsinmotion.orgchart.web.controller" })
public class TestControllerConfig {

	private List<Authorities> listOfFoundAuthorities;
	private List<Employee> listOfFoundEmployees;
	private List<Department> listOfFoundDepts;
	private List<JobTitle> listOfFoundTitles;
	private List<Users> listOfFoundUsers;

	private Authorities mockAuthority;
	private Department mockDepartment;
	private Employee mockEmployee;
	private JobTitle mockTitle;
	private Users mockUser;

	@PostConstruct
	private void init() {
		listOfFoundDepts = new ArrayList<Department>();
		mockDepartment = Entities.department(Entities.DEPT_ID);
		listOfFoundDepts.add(mockDepartment);
		// mockDepartment = Entities.department(Entities.DEPT_ID);

		listOfFoundTitles = new ArrayList<JobTitle>();
		mockTitle = Entities.jobTitle(Entities.JOB_TITLE_ID);
		listOfFoundTitles.add(mockTitle);

		listOfFoundEmployees = new ArrayList<Employee>();
		mockEmployee = Entities.employee(Entities.EMPLOYEE_ID);
		listOfFoundEmployees.add(mockEmployee);

		listOfFoundUsers = new ArrayList<Users>();
		mockUser = Entities.user();
		listOfFoundUsers.add(mockUser);

		listOfFoundAuthorities = new ArrayList<Authorities>();
		mockAuthority = Entities.authority();
		listOfFoundAuthorities.add(mockAuthority);

	}

	// Authorities bean configuration
	@Bean
	Authorities getAuthorities() {
		return this.mockAuthority;
	}

	@Bean
	AuthoritiesService getAuthoritiesService() {
		AuthoritiesService authoritiesService = mock(AuthoritiesService.class);
		when(authoritiesService.findAllByAuthority(Entities.AUTHORITY))
				.thenReturn(listOfFoundAuthorities);
		when(authoritiesService.findUserByUserName(Entities.USERNAME))
				.thenReturn(mockAuthority);
		return authoritiesService;
	}

	@Bean
	AuthorityRepository getAuthorityRepository() {
		AuthorityRepository repo = mock(AuthorityRepository.class);
		when(repo.findAll()).thenReturn(this.listOfFoundAuthorities);
		when(repo.findOne(Entities.USERNAME)).thenReturn(mockAuthority);
		when(repo.save(this.mockAuthority)).thenReturn(this.mockAuthority);
		return repo;
	}

	// end of authorities configuration

	// Department beans configuration
	@Bean
	Department getDepartment() {
		return this.mockDepartment;
	}

	@Bean
	DepartmentRepository getDepartmentRepository() {
		DepartmentRepository repo = mock(DepartmentRepository.class);
		when(repo.findAll()).thenReturn(this.listOfFoundDepts);
		when(repo.findByIsActiveIsTrue()).thenReturn(this.listOfFoundDepts);
		when(repo.findOne(Entities.DEPT_ID)).thenReturn(this.mockDepartment);
		when(repo.save(this.mockDepartment)).thenReturn(this.mockDepartment);
		return repo;
	}

	@Bean
	DepartmentService getDepartmentService() {
		final DepartmentService service = mock(DepartmentService.class);

		when(service.findAllDepartments()).then(new Answer<List<Department>>() {

			@Override
			public List<Department> answer(InvocationOnMock invocation)
					throws Throwable {
				listOfFoundDepts = service.activeDepartments();
				// TODO Auto-generated method stub
				return listOfFoundDepts;
			}
		});
		when(service.activeDepartments()).thenReturn(listOfFoundDepts);

		when(service.storeDepartment(mockDepartment)).thenAnswer(
				new Answer<Department>() {
					public Department answer(InvocationOnMock invocation) {
						listOfFoundDepts.add(mockDepartment);
						return mockDepartment;
					}
				});

		return service;
	} // end of department beans

	// employee beans configuration
	@Bean
	Employee getEmployee() {
		return this.mockEmployee;
	}

	@Bean
	EmployeeRepository getEmployeeRepository() {
		EmployeeRepository repo = mock(EmployeeRepository.class);
		when(repo.findAll()).thenReturn(listOfFoundEmployees);
		when(repo.findOne(Entities.EMPLOYEE_ID)).thenReturn(mockEmployee);

		when(repo.save(this.mockEmployee)).thenReturn(mockEmployee);
		return repo;
	}

	@Bean
	EmployeeService getEmployeeService() {
		EmployeeService service = mock(EmployeeService.class);
		when(service.findAllEmployees()).thenReturn(listOfFoundEmployees);
		when(service.activeEmployees()).thenReturn(listOfFoundEmployees);
		when(service.findEmployeeByID(Entities.EMPLOYEE_ID)).thenReturn(
				mockEmployee);
		when(
				service.autoComplete(String.valueOf(Entities.FIRST_NAME
						.charAt(0)))).thenReturn(mockEmployee.getFirstName());
		when(
				service.filterEmployees(Entities.FIRST_NAME,
						Entities.LAST_NAME, Entities.DEPT_ID.toString(),
						Entities.JOB_TITLE_ID.toString())).thenReturn(
				listOfFoundEmployees);
		when(service.filterEmployees(Entities.FIRST_NAME, " ", "", ""))
				.thenReturn(listOfFoundEmployees);
		when(service.filterEmployees(" ", Entities.LAST_NAME, "", ""))
				.thenReturn(listOfFoundEmployees);
		when(service.filterEmployees(" ", " ", "", "")).thenReturn(
				listOfFoundEmployees);
		when(service.storeEmployee(mockEmployee, new Object())).thenAnswer(
				new Answer<Employee>() {
					public Employee answer(InvocationOnMock invocation) {
						listOfFoundEmployees.add(mockEmployee);
						return mockEmployee;
					}
				});
		return service;
	}

	// JobTitle bean configuration
	@Bean
	JobTitle getJobTitle() {
		return this.mockTitle;
	}

	@Bean
	JobTitleRepository getJobTitleRepository() {
		JobTitleRepository repo = mock(JobTitleRepository.class);
		when(repo.findAll()).thenReturn(this.listOfFoundTitles);
		when(repo.findOne(Entities.JOB_TITLE_ID)).thenReturn(mockTitle);
		when(repo.save(this.mockTitle)).thenReturn(this.mockTitle);
		return repo;
	}

	@Bean
	JobTitleService getJobTitleService() {
		JobTitleService service = mock(JobTitleService.class);
		when(service.findAllJobTitles()).thenReturn(listOfFoundTitles);
		when(service.storeJobTitle(mockTitle)).thenAnswer(
				new Answer<JobTitle>() {
					public JobTitle answer(InvocationOnMock invocation) {
						listOfFoundTitles.add(mockTitle);
						return mockTitle;
					}
				});
		return service;
	}

	// end of JobTitle configu
	@Bean
	Users getUsers() {
		return this.mockUser;
	}

	@Bean
	UsersRepository getUserRepository() {
		UsersRepository repo = mock(UsersRepository.class);
		when(repo.findAll()).thenReturn(listOfFoundUsers);
		when(repo.findOne(Entities.USERNAME)).thenReturn(mockUser);
		when(repo.save(this.mockUser)).thenReturn(mockUser);
		return repo;
	}

	@Bean
	UsersService getUsersService() {
		UsersService service = mock(UsersService.class);
		when(service.findAllUsers()).thenReturn(listOfFoundUsers);
		when(service.findUsersByEnabled()).thenReturn(listOfFoundUsers);
		when(service.findByUserPassword(Entities.USERPASSWORD)).thenReturn(
				mockUser);
		when(service.findUserByUserName(Entities.FIRST_NAME)).thenReturn(
				mockUser);

		return service;
	}

}
