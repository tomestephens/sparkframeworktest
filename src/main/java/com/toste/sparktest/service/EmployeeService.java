package com.toste.sparktest.service;

import java.util.*;
import java.util.stream.Collectors;

import com.toste.sparktest.dto.EmployeeDto;

public class EmployeeService {

	private final HashMap<UUID, EmployeeDto> allEmployees;

	public EmployeeService() {
		allEmployees = new HashMap<>();
	}

	public void createEmployee(EmployeeDto employee) {
		if (employee == null) {
			throw new IllegalArgumentException("Cannot create empty employee.");
		}
		if(employee.getId() == null) {
		    employee.setId(UUID.randomUUID());
        }
        if(allEmployees.get(employee.getId()) != null) {
		    throw new IllegalArgumentException("Employee with that ID already exists.");
        }
        // is there something we should consider required for this to be a valid

		allEmployees.put(employee.getId(), employee);
	}

    public void updateEmployee(EmployeeDto employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if(employee.getId() == null || allEmployees.get(employee.getId()) == null) {
            throw new IllegalArgumentException("Employee does not exist.");
        }

        // what if

        allEmployees.put(employee.getId(), employee);
    }

	public List<EmployeeDto> getEmployees() {
		return Collections.unmodifiableList(allEmployees.values().stream()
                .filter(emp -> !emp.getStatus().equals(EmployeeDto.EmploymentStatus.INACTIVE))
                .collect(Collectors.toList()));
	}

	public EmployeeDto getEmployeeById(final String id) {
		final UUID empId;
		try {
			empId = UUID.fromString(id);
		} catch (Exception ex) {
			throw new IllegalArgumentException("User id must be a valid UUID.");
		}
		return allEmployees.values().stream()
                .filter(e -> e.getId().equals(empId) && e.getStatus().equals(EmployeeDto.EmploymentStatus.ACTIVE))
                .findFirst().orElse(null);
	}

	public void deactivateEmployee(final String id) {
        final UUID empId;
        try {
            empId = UUID.fromString(id);
        } catch (Exception ex) {
            throw new IllegalArgumentException("User id must be a valid UUID.");
        }

        EmployeeDto employee = allEmployees.get(empId);
        if(employee == null) {
            throw new IllegalArgumentException("Employee does not exist.");
        }

        employee.setStatus(EmployeeDto.EmploymentStatus.INACTIVE);
        allEmployees.put(empId, employee);
    }
}
