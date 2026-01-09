package edu.vassar.cmpu203.myfirstapplication;

import org.junit.Test;

import static org.junit.Assert.*;

import static edu.vassar.cmpu203.myfirstapplication.model.ExpenseType.*;

import java.time.LocalDateTime;
import java.util.*;

import java.time.LocalDate;

import edu.vassar.cmpu203.myfirstapplication.model.Expense;
import edu.vassar.cmpu203.myfirstapplication.model.ExpenseType;
import edu.vassar.cmpu203.myfirstapplication.model.Trip;
import edu.vassar.cmpu203.myfirstapplication.model.User;

public class ExpenseTest {

    @Test
    public void expenseConstructorTest(){
        Expense expense = new Expense();

        assertEquals(0, expense.getCost(), 0.001);
        assertEquals("Example memo", expense.getMemo());
        assertEquals(ExpenseType.MISCELLANEOUS, expense.getExpenseType());
        assertEquals(LocalDateTime.now().toLocalDate(), expense.getTimeStamp().toLocalDate());
        assertEquals("Matthew Vassar", expense.getUserTag());

        expense = new Expense(5, EXCURSION, 9.00, "fun at beach", LocalDate.parse("2025-01-01").atStartOfDay(), "L");

        assertTrue(5==expense.getId());
        assertEquals(9.00, expense.getCost(), 0.001);
        assertEquals("fun at beach", expense.getMemo());
        assertEquals(EXCURSION, expense.getExpenseType());
        assertEquals(LocalDate.parse("2025-01-01").atStartOfDay(), expense.getTimeStamp());
        assertEquals("L", expense.getUserTag());
    }

    @Test
    public void editExpenseTest(){
        Expense expense = new Expense();

        expense.setExpenseType(MISCELLANEOUS);
        assertEquals(MISCELLANEOUS, expense.getExpenseType());

        expense.setCost(10.01);
        assertEquals(10.01, expense.getCost(), 0.001);

        expense.setMemo("soup");
        assertEquals("soup", expense.getMemo());

        expense.setTimeStamp(LocalDate.parse("2024-12-25").atStartOfDay());
        assertEquals(LocalDate.parse("2024-12-25").atStartOfDay(), expense.getTimeStamp());

        expense.setUserTag("S");
        assertEquals("S", expense.getUserTag());

        expense.setId(5);
        assertTrue(5==expense.getId());

        expense.editExpense(FOOD, 9.99, "pizza", LocalDate.parse("2024-12-20").atStartOfDay(), "Will");

        assertEquals("FOOD", expense.getExpenseType().toString());
        assertEquals(9.99, expense.getCost(), 0.001);
        assertEquals("pizza", expense.getMemo());
        assertEquals(LocalDate.parse("2024-12-20").atStartOfDay(), expense.getTimeStamp());
        assertEquals("Will", expense.getUserTag());
    }
}
