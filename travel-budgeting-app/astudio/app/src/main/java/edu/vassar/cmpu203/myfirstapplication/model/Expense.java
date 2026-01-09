package edu.vassar.cmpu203.myfirstapplication.model;
import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Expense {

    int id;
    ExpenseType expenseType;
    double cost;
    String memo;
    LocalDateTime timeStamp;
    String userTag; // User who added the expense

    public Expense() {
        this.id = new Random().nextInt();
        this.cost = 0;
        this.memo = "Example memo";
        this.expenseType = ExpenseType.MISCELLANEOUS;
        this.timeStamp = LocalDateTime.now();
        this.userTag = "Matthew Vassar";
    }

    public Expense(int id, ExpenseType expenseType, double cost, String memo, LocalDateTime timeStamp, String userTag) {
        this.id = id;
        this.expenseType = expenseType;
        this.cost = cost;
        this.memo = memo;
        this.timeStamp = timeStamp;
        this.userTag = userTag;
    }

    public Integer getId() { return this.id; }

    public double getCost() { return this.cost; }
    public String getMemo() { return this.memo; }
    public ExpenseType getExpenseType() { return this.expenseType; }
    public LocalDateTime getTimeStamp() { return this.timeStamp; }
    public String getUserTag() { return this.userTag; }


    public void setId(int id){ this.id = id;}

    public void editExpense(ExpenseType expenseType, double cost, String memo, LocalDateTime timeStamp, String userTag) {
        this.expenseType = expenseType;
        this.cost = cost;
        this.memo = memo;
        this.timeStamp = timeStamp;
        this.userTag = userTag;
    }

    public void setExpenseType(ExpenseType expenseType) { this.expenseType = expenseType; }
    public void setCost(double cost) { this.cost = cost; }
    public void setMemo(String memo) { this.memo = memo; }
    public void setUserTag(String userTag) {this.userTag = userTag; }
    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }

    public static Expense fromMap(Map<String, Object> data, int iterations) {
        Expense expense = new Expense(Integer.parseInt(String.valueOf(data.get("id"))), ExpenseType.parse(String.valueOf(data.get("expenseType"))), (double) data.get("cost"), (String) data.get("memo"), LocalDateTime.parse(data.get("timeStamp").toString()), (String) data.get("userTag"));
        return expense;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> expenseInfo = new HashMap<>();

        expenseInfo.put("id", id);
        expenseInfo.put("expenseType", expenseType.toString());
        expenseInfo.put("cost", cost);
        expenseInfo.put("memo", memo);
        expenseInfo.put("timeStamp", timeStamp.toString());
        expenseInfo.put("userTag", userTag);

        return expenseInfo;
    }

}
