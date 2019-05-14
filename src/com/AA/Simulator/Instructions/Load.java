package com.AA.Simulator.Instructions;

import com.AA.Simulator.Enums.FunctionalUnit;
import com.AA.Simulator.Utilities.Validator;

public class Load extends Instruction {
    @Override
    public boolean Validate() {
        return  operands.length == 3 &&
                Validator.isValidRegister(operands[0]) &&
                Validator.isValidRegister(operands[1]) &&
                Validator.isValidImmediate(operands[2]);
    }

    @Override
    public void AssignFunctionalUnit() {
        functionalUnit = FunctionalUnit.Load;
    }

    @Override
    public void setDependencies() {
        Dependencies.add(1);
    }

    @Override
    public boolean setHasDestination() {
        return true;
    }
}