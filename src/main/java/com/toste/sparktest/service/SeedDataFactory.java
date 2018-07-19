package com.toste.sparktest.service;

import com.toste.sparktest.dto.EmployeeDto;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SeedDataFactory {

    public List<EmployeeDto> getSeedData() {
        try {
            return loadFromFile();
        } catch (IOException ex) {
            // do nothing, just return a hard coded list...
        }

        List<EmployeeDto> employees = new ArrayList<>();
        employees.add(new EmployeeDto("Adam", "B", "Chance", LocalDate.of(1990, 01, 01), LocalDate.of(2015, 02,02)));
        employees.add(new EmployeeDto("Doug", "E", "Fitzsimmons", LocalDate.of(1988, 03, 03), LocalDate.of(2016, 04,04)));
        return employees;
    }

    private List<EmployeeDto> loadFromFile() throws IOException {
        File seedFile = new File("seed_data.csv");
        List<EmployeeDto> employees = new ArrayList<>();
        if(seedFile.exists() && seedFile.isFile()) {
            BufferedReader reader = null;
            try {
                String line;
                reader = new BufferedReader(new FileReader(seedFile.getAbsoluteFile()));
                while((line = reader.readLine()) != null) {
                    // this isn't going to handle quoted lines that might have a comma or something
                    // but that's ok for this situation
                    String[] tokens = line.split(",");
                    // could fail if the file format is wrong, but that shouldn't be realistic
                    employees.add(new EmployeeDto(tokens[0], tokens[1], tokens[2], LocalDate.parse(tokens[3]), LocalDate.parse(tokens[4])));
                }
            } catch(IOException ex) {
                System.out.println(ex.getMessage());
                throw ex;
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }

            return employees;
        }

        throw new IOException();
    }

}
