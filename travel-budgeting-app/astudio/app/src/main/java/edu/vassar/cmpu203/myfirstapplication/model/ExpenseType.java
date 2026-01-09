package edu.vassar.cmpu203.myfirstapplication.model;

import androidx.annotation.NonNull;

public enum ExpenseType {
    FOOD,
    TRANSPORTATION,
    HOUSING,
    MERCHANDISE,
    EXCURSION,
    MISCELLANEOUS;

    @NonNull
    @Override
    public String toString(){
        if (this.equals(FOOD)){
            return "FOOD";
        }
        else if (this.equals(TRANSPORTATION)){
            return "TRANSPORTATION";
        }
        else if (this.equals(HOUSING)){
            return "HOUSING";
        }
        else if (this.equals(MERCHANDISE)){
            return "MERCHANDISE";
        }
        else if (this.equals(EXCURSION)){
            return "EXCURSION";
        }
        else {
            return "MISCELLANEOUS";
        }
    }


    public static ExpenseType parse(String string) {
        if (string.toUpperCase().equals("FOOD")){
            return FOOD;
        }
        else if (string.toUpperCase().equals("TRANSPORTATION")){
            return TRANSPORTATION;
        }
        else if (string.toUpperCase().equals("HOUSING")){
            return HOUSING;
        }
        else if (string.toUpperCase().equals("MERCHANDISE")){
            return MERCHANDISE;
        }
        else if (string.toUpperCase().equals("EXCURSION")){
            return EXCURSION;
        }
        else {
            return MISCELLANEOUS;
        }
    }
}
