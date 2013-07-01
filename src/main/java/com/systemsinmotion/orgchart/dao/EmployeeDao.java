package com.systemsinmotion.orgchart.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.systemsinmotion.orgchart.entity.Department;
import com.systemsinmotion.orgchart.entity.Employee;
@Repository("employeeDao")
public class EmployeeDao implements com.systemsinmotion.orgchart.dao.IEmployeeDao 
{

	/**
	 * @see org.slf4j.Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeDao.class);
		
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Integer create(Employee e) 
	{		
		LOG.debug("saving: " + e.getFirstName() + " " + e.getLastName());	
		return(Integer) this.hibernateTemplate.save(e);						 		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Employee>queryAll() 
	{		
		LOG.debug("returning all employees (unfiltered list)");		
		return hibernateTemplate.find("from " + Employee.class.getName() );		
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> queryByFirstNameLastName(String lastName, String firstName) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
		
		criteria.add(Restrictions.eq("firstName", firstName));
		criteria.add(Restrictions.eq("lastName", lastName));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Employee> employees = hibernateTemplate.findByCriteria(criteria); 
								
		return employees;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> queryByDepartment(Department dept) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
		
//		criteria.add(Restrictions.eq("dept_ID", dept_Id));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Employee> employees = hibernateTemplate.findByCriteria(criteria); 
								
		return employees;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> queryByJobTitle(String jobTitle) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
		
		criteria.add(Restrictions.eq("jobTitle", jobTitle));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Employee> employees = hibernateTemplate.findByCriteria(criteria); 
								
		return employees;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> queryByMultipleCriteria(String lastName, String firstName, int dept_Id,  String jobTitle)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
		
		if (jobTitle!=null)
		criteria.add(Restrictions.eq("jobTitle", jobTitle));
				
		if (dept_Id>0)
		criteria.add(Restrictions.eq("dept_ID", dept_Id));
		
		if (firstName!=null)			
		criteria.add(Restrictions.eq("firstName", firstName));
		
		if (lastName!=null)
		criteria.add(Restrictions.eq("lastName", lastName));
						
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Employee> employees = hibernateTemplate.findByCriteria(criteria); 
								
		return employees;
		
	}

	@Override
	public Employee read(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}


	
	



}
