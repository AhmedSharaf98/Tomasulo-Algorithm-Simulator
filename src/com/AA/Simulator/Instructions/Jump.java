package com.AA.Simulator.Instructions;

import com.AA.Simulator.Enums.FunctionalUnit;
import com.AA.Simulator.Utilities.Validator;

public class Jump extends Instruction {
    @Override
    public boolean Validate() {
        return  operands.length == 1 &&
                Validator.isValidImmediate(operands[0]);
    }

    @Override
    public void AssignFunctionalUnit() {
        functionalUnit = FunctionalUnit.JUMP;
    }

    @Override
    public void setDependencies() {

    }

    @Override
    public boolean setHasDestination() {
        return false;
    }
}
