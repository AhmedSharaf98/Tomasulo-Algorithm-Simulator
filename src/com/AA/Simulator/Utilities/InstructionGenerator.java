package com.AA.Simulator.Utilities;

import com.AA.Simulator.Instructions.*;

public class InstructionGenerator {
    public static Instruction generate(String name){
        switch (name){
            case "ADD": return new Add();
            case "LW" : return new Load();
            case "SW" : return  new Store();
            case "JMP" : return  new Jump();
            case "BEQ" : return  new Branch();
            case "JALR" : return  new JumpR();
            case "RET" : return  new Return();
            case "SUB" : return  new Sub();
            case "ADDI": return  new AddImm();
            case "NAND": return  new Nand();
            case "MUL" : return new Multiply();
        }
        return new Add();
    }
    public static Instruction GetCopy(Instruction instruction){
        return new Instruction(instruction) {
            @Override
            public boolean Validate() { return true; }
            @Override
            public void AssignFunctionalUnit() { }
            @Override
            public void setDependencies() { }
            @Override
            public boolean setHasDestination() { return false; }
        };
    }
}
