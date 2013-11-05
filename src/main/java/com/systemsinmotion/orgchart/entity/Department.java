package com.systemsinmotion.orgchart.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DEPARTMENT")
public class Department implements java.io.Serializable {

	private static final long serialVersionUID = -5379179412533671591L;

	private Integer id;
	
	private Department parentDepartment;

	private String name;

	private Set<Employee> employees = new HashSet<Employee>();

	private Set<Department> departments = new HashSet<Department>(0);

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentDepartment")
	public Set<Department> getDepartments() {
		return this.departments;
	}

	 public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "department")
	 public Set<Employee> getEmployees() {
		 return this.employees;
	 }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARENT_DEPARTMENT_ID", referencedColumnName = "ID")
	public Department getParentDepartment() {
		return this.parentDepartment;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	public void setId(Integer departmentId) {
		this.id = departmentId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentDepartment(Department department) {
		this.parentDepartment = department;
	}

}