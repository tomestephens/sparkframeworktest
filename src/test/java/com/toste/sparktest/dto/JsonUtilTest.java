package com.toste.sparktest.dto;

import org.junit.Test;
import org.junit.Assert;

public class JsonUtilTest {

    @Test
    public void TestParseJson_Empty() {
        EmployeeDto employee = JsonUtil.fromJson("{}", EmployeeDto.class);
        Assert.assertNotNull(employee);
    }

    @Test
    public void TestParseJson_FullObject() {
        EmployeeDto employee = JsonUtil.fromJson("{\"firstName\": \"Ryan\"," +
                " \"middleInitial\": \"S\"," +
                " \"lastName\": \"Tibauld\"," +
                " \"dateOfBirth\": \"1974-05-05\"," +
                " \"dateOfEmployment\": \"2018-08-01\"}",
                EmployeeDto.class);
        Assert.assertNotNull(employee);
    }

}
