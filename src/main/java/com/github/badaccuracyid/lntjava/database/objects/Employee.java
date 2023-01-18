package com.github.badaccuracyid.lntjava.database.objects;

public class Employee {

    private final String id;
    private String username;
    private Gender gender;
    private Position position;
    private int salary;

    public Employee(String id, String username, Gender gender, Position position) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.position = position;
        this.salary = position.getSalary();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void giveRaise() {
        switch (position) {
            case MANAGER:
                salary += salary * 0.1;
                break;
            case SUPERVISOR:
                salary += salary * 0.075;
                break;
            case ADMIN:
                salary += salary * 0.05;
                break;
        }
    }
}
