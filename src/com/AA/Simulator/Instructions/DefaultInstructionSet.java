package com.AA.Simulator.Instructions;

public class DefaultInstructionSet extends InstructionSet {
    @Override
    public void SetInstructionSet() {
        this.instructionMap.add("LW"  );
        this.instructionMap.add("SW"  );
        this.instructionMap.add("JMP" );
        this.instructionMap.add("BEQ" );
        this.instructionMap.add("JALR");
        this.instructionMap.add("RET" );
        this.instructionMap.add("ADD" );
        this.instructionMap.add("SUB" );
        this.instructionMap.add("ADDI");
        this.instructionMap.add("NAND");
        this.instructionMap.add("MUL" );
    }
}
