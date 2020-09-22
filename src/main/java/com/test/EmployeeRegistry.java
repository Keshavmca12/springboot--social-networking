package com.test;

import java.util.Collection;

public interface EmployeeRegistry {
	void addEmployee(int employeeId, String department);

	void updateEmployee(int employeeId, String department);

	Collection<Integer> reportEmployees(String department);
}
