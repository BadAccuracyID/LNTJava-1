package com.github.badaccuracyid.lntjava;

import com.github.badaccuracyid.lntjava.database.Database;
import com.github.badaccuracyid.lntjava.database.objects.Employee;
import com.github.badaccuracyid.lntjava.database.objects.Gender;
import com.github.badaccuracyid.lntjava.database.objects.Position;
import com.github.badaccuracyid.lntjava.utils.Utils;

import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private final Scanner scanner;
    private final Database database;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.database = new Database();
        this.mainMenu();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void mainMenu() {
        int selection;
        do {
            System.out.println("Welcome to the LNT Java application!");
            System.out.println("Please select an option:");
            System.out.println("1. Insert new account");
            System.out.println("2. View all accounts");
            System.out.println("3. Update an account");
            System.out.println("4. Delete an account");
            System.out.println("5. Exit");
            System.out.print("Selection: ");
            selection = scanner.nextInt();
        } while (selection < 1 || selection > 5);

        switch (selection) {
            case 1:
                this.insertAccount();
                break;
            case 2:
                this.viewAccounts();
                break;
            case 3:
                this.updateAccount();
                break;
            case 4:
                this.deleteAccount();
                break;
            case 5:
                System.out.println("Thank you for using the LNT Java application!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid selection!");
                break;
        }

        this.mainMenu();
    }

    private void insertAccount() {
        String name;
        do {
            System.out.print("Please enter the name of the employee [>= 3]: ");
            name = scanner.next();
        } while (name.length() < 3);

        String gender;
        do {
            System.out.print("Please input the gender of the employee [Male | Female]: ");
            gender = scanner.next();
        } while (!gender.equals("Male") && !gender.equals("Female"));
        Gender genderEnum = Gender.valueOf(gender.toUpperCase());

        String position;
        do {
            System.out.print("Please input the position of the employee [Manager | Supervisor | Admin]: ");
            position = scanner.next();
        } while (!position.equals("Manager") && !position.equals("Supervisor") && !position.equals("Admin"));
        Position positionEnum = Position.valueOf(position.toUpperCase());

        String id;
        do {
            id = Utils.randomIDGenerator();
        } while (database.checkID(id));

        // check raise
        database.checkForRaises(positionEnum);

        Employee employee = new Employee(id, name, genderEnum, positionEnum);
        database.insertEmployee(employee);

        System.out.print("Press enter to continue...");
        scanner.nextLine();
    }

    private void viewAccounts() {
        System.out.printf("%-2s %-10s %-10s %-10s %-10s %-10s\n",
                "No", "ID", "Username", "Gender", "Position", "Salary");
        AtomicInteger i = new AtomicInteger();
        database.getEmployeeMap().values().stream()
                .sorted(Comparator.comparing(Employee::getUsername))
                .forEach(it -> {
                    i.getAndIncrement();
                    System.out.printf("%-2d %-10s %-10s %-10s %-10s %-10s\n",
                            i.get(),
                            it.getId(),
                            it.getUsername(),
                            it.getGender().toString(),
                            it.getPosition().toString(),
                            it.getSalary());
                });

        System.out.print("Press enter to continue...");
        scanner.nextLine();
    }

    private void updateAccount() {
        System.out.printf("%-2s %-10s %-10s %-10s %-10s %-10s\n",
                "No", "ID", "Username", "Gender", "Position", "Salary");
        AtomicInteger i = new AtomicInteger();
        database.getEmployeeMap().values().stream()
                .sorted(Comparator.comparing(Employee::getUsername))
                .forEach(it -> {
                    i.getAndIncrement();
                    System.out.printf("%-2d %-10s %-10s %-10s %-10s %-10s\n",
                            i.get(),
                            it.getId(),
                            it.getUsername(),
                            it.getGender().toString(),
                            it.getPosition().toString(),
                            it.getSalary());
                });

        int idx = 0;
        do {
            System.out.print("Please select an account to update [1 - " + i.get() + "]: ");
            idx = scanner.nextInt();
        } while (idx < 1 || idx > i.get());

        Employee employee = database.getEmployeeMap().values().stream()
                .sorted(Comparator.comparing(Employee::getUsername))
                .skip(idx - 1)
                .findFirst()
                .orElse(null);
        assert employee != null;

        // ask all attributes, if 0 then no change
        String name;
        do {
            System.out.print("Please enter the name of the employee [>= 3]: ");
            name = scanner.next();
        } while (name.length() < 3 && !name.equals("0"));

        String gender;
        do {
            System.out.print("Please input the gender of the employee [Male | Female]: ");
            gender = scanner.next();
        } while (!gender.equals("Male") && !gender.equals("Female") && !gender.equals("0"));
        Gender genderEnum = null;
        if (!gender.equals("0")) {
            genderEnum = Gender.valueOf(gender.toUpperCase());
        }

        String position;
        do {
            System.out.print("Please input the position of the employee [Manager | Supervisor | Admin]: ");
            position = scanner.next();
        } while (!position.equals("Manager") && !position.equals("Supervisor") && !position.equals("Admin")
                && !position.equals("0"));
        Position positionEnum = null;
        if (!position.equals("0")) {
            positionEnum = Position.valueOf(position.toUpperCase());
        }

        // check raise
        database.checkForRaises(positionEnum);

        if (!name.equals("0")) {
            employee.setUsername(name);
        }
        if (genderEnum != null) {
            employee.setGender(genderEnum);
        }
        if (positionEnum != null) {
            employee.setPosition(positionEnum);
        }

        database.updateEmployee(employee);

        System.out.print("Press enter to continue...");
        scanner.nextLine();
    }

    private void deleteAccount() {
        System.out.printf("%-2s %-10s %-10s %-10s %-10s %-10s\n",
                "No", "ID", "Username", "Gender", "Position", "Salary");
        AtomicInteger i = new AtomicInteger();
        database.getEmployeeMap().values().stream()
                .sorted(Comparator.comparing(Employee::getUsername))
                .forEach(it -> {
                    i.getAndIncrement();
                    System.out.printf("%-2d %-10s %-10s %-10s %-10s %-10s\n",
                            i.get(),
                            it.getId(),
                            it.getUsername(),
                            it.getGender().toString(),
                            it.getPosition().toString(),
                            it.getSalary());
                });

        int idx = 0;
        do {
            System.out.print("Please select an account to update [1 - " + i.get() + "]: ");
            idx = scanner.nextInt();
        } while (idx < 1 || idx > i.get());

        Employee employee = database.getEmployeeMap().values().stream()
                .sorted(Comparator.comparing(Employee::getUsername))
                .skip(idx - 1)
                .findFirst()
                .orElse(null);
        assert employee != null;

        database.deleteEmployee(employee);
        System.out.print(employee.getUsername() + "(" + employee.getId() + ") has been deleted.");

        System.out.print("Press enter to continue...");
        scanner.nextLine();
    }
}
