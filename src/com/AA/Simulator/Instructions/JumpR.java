package com.AA.Simulator.Instructions;

import com.AA.Simulator.Enums.FunctionalUnit;
import com.AA.Simulator.Utilities.Validator;

public class JumpR extends Instruction {
    @Override
    public boolean Validate() {
        return  operands.length == 2 &&
                Validator.isValidRegister(operands[0]) &&
                Validator.isValidRegister(operands[1]);
    }

    @Override
    public void AssignFunctionalUnit() {
        functionalUnit = FunctionalUnit.JUMP;
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
