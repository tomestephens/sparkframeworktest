package com.toste.sparktest.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class EmployeeDto {

    public EmployeeDto() {
        status = EmploymentStatus.ACTIVE;
    }

	private UUID id;

	private String firstName;

	private String middleInitial;

	private String lastName;

	private LocalDate dateOfBirth;

    private LocalDate dateOfEmployment;

    private EmploymentStatus status;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(LocalDate dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public EmploymentStatus getStatus() {
	    return status;
    }

    public void setStatus(EmploymentStatus status) {
	    this.status = status;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		EmployeeDto employee = (EmployeeDto) o;
		return Objects.equals(id, employee.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("EmployeeDto{");
		sb.append("id=").append(id);
		sb.append(", firstName='").append(firstName).append('\'');
		sb.append(", lastName='").append(lastName).append('\'');
		sb.append(", dateOfBirth=").append(dateOfBirth);
        sb.append(", dateOfEmployment=").append(dateOfEmployment);
		sb.append('}');
		return sb.toString();
	}

	public enum EmploymentStatus {
	    ACTIVE,
        INACTIVE;
    }
}
