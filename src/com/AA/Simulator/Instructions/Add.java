package com.AA.Simulator.Instructions;

import com.AA.Simulator.Enums.FunctionalUnit;
import com.AA.Simulator.Utilities.Validator;

public class Add extends Instruction {

    @Override
    public boolean Validate() {
        return  operands.length == 3 &&
                Validator.isValidRegister(operands[0]) &&
                Validator.isValidRegister(operands[1]) &&
                Validator.isValidRegister(operands[2]);
    }

    @Override
    public void AssignFunctionalUnit() {
        this.functionalUnit = FunctionalUnit.Arthematic;
    }

    @Override
    public void setDependencies() {
        Dependencies.add(1);
        Dependencies.add(2);
    }

    @Override
    public boolean setHasDestination() {
        return true;
    }
}
