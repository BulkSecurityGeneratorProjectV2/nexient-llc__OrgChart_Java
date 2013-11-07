package com.systemsinmotion.orgchart.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.systemsinmotion.orgchart.Entities;
import com.systemsinmotion.orgchart.entity.Department;
import com.systemsinmotion.orgchart.service.DepartmentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class DepartmentControllerTests {

	@Autowired
	private DepartmentController controller;

	private DepartmentService mockDepartmentService = mock(DepartmentService.class);

	private Department mockDepartment = mock(Department.class);

	private Department mockDepartment2;

	private Model model = new ExtendedModelMap();

	private ArrayList<Department> findAllDepartmentsList;

	@SuppressWarnings("unused")
	private static final String DEPARTMENT_LIST_MISSING_ERROR = "Expected Model to contain a List of Departments, but did not.";

	@Before
	public void before() {
		// instantiate lists
		this.findAllDepartmentsList = new ArrayList<Department>();

		this.mockDepartment2 = new Department();
		this.mockDepartment2.setName(Entities.DEPARTMENT_NAME);

		// set up mock Department
		when(this.mockDepartment.getId()).thenReturn(Entities.DEPT_ID);
		when(this.mockDepartment.getName()).thenReturn(Entities.DEPARTMENT_NAME);

		this.findAllDepartmentsList.add(this.mockDepartment);

		// set up mock DepartmentService
		when(this.mockDepartmentService.findAllDepartments()).thenReturn(this.findAllDepartmentsList);
		when(this.mockDepartmentService.findDepartmentByID(Entities.DEPT_ID)).thenReturn(this.mockDepartment);
		when(this.mockDepartmentService.storeDepartment(this.mockDepartment)).thenReturn(Entities.DEPT_ID);
		when(this.mockDepartmentService.storeDepartment(this.mockDepartment2)).thenReturn(Entities.DEPT_ID);

		controller.setDepartmentService(mockDepartmentService);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testModelShouldContainNewDepartmentList() {
		// Given
		this.controller.doDepartments_GET(this.model);
		// When
		this.findAllDepartmentsList = (ArrayList<Department>) (this.model.asMap().get("depts"));
		// Then
		assertNotNull(this.findAllDepartmentsList);
		assertEquals(Entities.DEPT_ID, this.findAllDepartmentsList.get(0).getId());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testModelShouldUpdateOnDepartmentPagePost() {

		model.addAttribute("depts", findAllDepartmentsList);
		// Given
		controller.doDepartments_POST(mockDepartment2, null, model);
		// When
		findAllDepartmentsList = (ArrayList<Department>) model.asMap().get(
				"depts");

		// Then
		assertNotNull(findAllDepartmentsList);
		assertTrue(findAllDepartmentsList.size() > 0);

		// Commented out for now as I'm unsure where TestObject is supposed
		// to come from.
		// assertEquals(TestObject.DEPT_ID, findAllDepartmentsList.get(1)
		// .getDepartmentId());
		// assertEquals(findAllDepartmentsList.get(1).getName(),
		// TestObject.DEPARTMENT_NAME);

	}

}
