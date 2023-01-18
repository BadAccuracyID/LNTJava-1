package com.github.badaccuracyid.lntjava.database.objects;

public enum Position {

    MANAGER(4000000),
    SUPERVISOR(6000000),
    ADMIN(8000000);

    private final int salary;

    Position(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }
}
