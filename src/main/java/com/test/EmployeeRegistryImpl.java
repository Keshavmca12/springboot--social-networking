package com.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EmployeeRegistryImpl implements EmployeeRegistry {

	Map<String, Set<Integer>> employeeDepartmentMap = new HashMap<>();

	@Override
	public void addEmployee(int employeeId, String department) {
		Set<Integer> employeeSet = new HashSet<>();
		employeeSet.add(employeeId);
		employeeDepartmentMap.put(department, employeeSet);

	}

	@Override
	public void updateEmployee(int employeeId, String department) {
		if (employeeDepartmentMap.get(department) == null) {
			addEmployee(employeeId, department);
		} else {
			Set<Integer> employeeSet = employeeDepartmentMap.get(department);
			employeeSet.add(employeeId);
			employeeDepartmentMap.put(department, employeeSet);
		}
	}

	@Override
	public Collection<Integer> reportEmployees(String department) {
		return employeeDepartmentMap.get(department);
	}

}
