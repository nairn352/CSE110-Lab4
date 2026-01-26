package edu.ucsd.spendingtracker.model;

public class Expense {
    private String name;
    private Category category;
    private double amount;
    private int id;


    public Expense(int id,String name, Category category, double amount) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.id = id;
    }

    public Expense(String name, Category category, double amount) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.id = -1;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }
}


