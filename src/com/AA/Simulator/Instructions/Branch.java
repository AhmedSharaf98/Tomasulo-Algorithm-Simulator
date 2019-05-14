package com.AA.Simulator.Instructions;

import com.AA.Simulator.Enums.FunctionalUnit;
import com.AA.Simulator.Utilities.Validator;

public class Branch extends Instruction {

    @Override
    public boolean Validate() {
        return  operands.length == 3 &&
                Validator.isValidRegister(operands[0]) &&
                Validator.isValidRegister(operands[1]) &&
                Validator.isValidImmediate(operands[2]);
    }

    @Override
    public void AssignFunctionalUnit() {
        functionalUnit = FunctionalUnit.Branch;
    }

    @Override
    public void setDependencies() {
        Dependencies.add(0);
        Dependencies.add(1);
    }

    @Override
    public boolean setHasDestination() {
        return false;
    }
}
