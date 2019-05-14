package com.AA.Simulator.Utilities;

public class Validator {
    public static Boolean InRangeInt(int data, int min, int max){
        return (data >= min && data <= max);
    }

    public static Boolean isValidRegister(String operand){
        return operand.matches("^[R][0-7]$"); // Regex to check (R0, R1... R7) otherwise false..
    }

    public static Boolean isValidImmediate(String operand){
        try {
            Integer.parseInt(operand);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
