package com.github.badaccuracyid.lntjava.database;

import com.github.badaccuracyid.lntjava.database.objects.Employee;
import com.github.badaccuracyid.lntjava.database.objects.Position;
import com.github.badaccuracyid.lntjava.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Database {

    private final Map<String, Employee> employeeMap = new HashMap<>();

    public Map<String, Employee> getEmployeeMap() {
        return employeeMap;
    }

    public void insertEmployee(Employee employee) {
        employeeMap.put(employee.getId(), employee);
    }

    public Employee getEmployee(String id) {
        return employeeMap.get(id);
    }

    public boolean checkID(String id) {
        return employeeMap.containsKey(id);
    }

    public void checkForRaises(Position position) {
        List<Employee> employees = employeeMap.values().stream()
                .filter(employee -> employee.getPosition().equals(position))
                .collect(Collectors.toList());

        if (employees.size() % 3 == 0) {
            employees.forEach(Employee::giveRaise);

            switch (position) {
                case MANAGER: {
                    System.out.println("A 10% raise has been given to " + Utils.joinList(employees.stream().map(Employee::getUsername).collect(Collectors.toList())));
                    break;
                }
                case SUPERVISOR: {
                    System.out.println("A 75% raise has been given to " + Utils.joinList(employees.stream().map(Employee::getUsername).collect(Collectors.toList())));
                    break;
                }

                case ADMIN: {
                    System.out.println("A 5% raise has been given to " + Utils.joinList(employees.stream().map(Employee::getUsername).collect(Collectors.toList())));
                    break;
                }

            }
        }
    }

    public void updateEmployee(Employee employee) {
        employeeMap.put(employee.getId(), employee);
    }

    public void deleteEmployee(Employee employee) {
        employeeMap.remove(employee.getId());
    }
}
