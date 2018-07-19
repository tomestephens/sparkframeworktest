package com.toste.sparktest;

import static com.toste.sparktest.dto.JsonUtil.*;
import static spark.Spark.*;

import com.toste.sparktest.dto.EmployeeDto;
import com.toste.sparktest.dto.MessageDto;
import com.toste.sparktest.service.AuthorizationService;
import com.toste.sparktest.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SparkApp {

	private static final Logger log = LoggerFactory.getLogger(SparkApp.class);


	public static void main(final String[] args) {
		final EmployeeService employeeService = new EmployeeService();
		final AuthorizationService authService = new AuthorizationService();

		path("/api/employees", () -> {
			get("", (req, res) -> {
				return employeeService.getEmployees();
			}, json());

			post("", (req, res) -> {
				final String body = req.body();
				final EmployeeDto employeeDto = fromJson(body, EmployeeDto.class);
				employeeService.createEmployee(employeeDto);
				return employeeDto;
			}, json());

			put("", (req, res) -> {
				final String body = req.body();
				final EmployeeDto employeeDto = fromJson(body, EmployeeDto.class);
				employeeService.updateEmployee(employeeDto);
				return employeeDto;
			}, json());

			get("/:id", (req, res) -> {
				final String id = req.params(":id");
				final EmployeeDto employee = employeeService.getEmployeeById(id);
				if (employee != null) {
					return employee;
				}
				res.status(404);
				return new MessageDto("Employee with id '%s' was not found", id);
			}, json());

			delete("/:id", (req, res) -> {
			    final String authHeader = req.headers("AuthToken");
			    if (authHeader == null) {
			        res.status(401);
                } else if(authService.allowed(authHeader)) {
                    final String id = req.params(":id");
                    employeeService.deactivateEmployee(id);

                    res.status(200);
                } else {
			        res.status(403);
                }
                return res;
            });
		});

		notFound(toJson(new MessageDto("Not found.")));

		internalServerError(toJson(new MessageDto("Internal server error.")));

		exception(IllegalArgumentException.class, (e, req, res) -> {
			log.warn("An error occurred",  e);
			res.status(400);
			res.body(toJson(new MessageDto(e)));
		});

		afterAfter((req, res) -> {
			if (res.type() == null) {
				res.type("application/json");
			}
		});
	}
}