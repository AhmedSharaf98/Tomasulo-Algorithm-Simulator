package com.AA.Simulator.Instructions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InstructionSet {
    List<String> instructionMap = new ArrayList<>();

    public InstructionSet() {
        SetInstructionSet();
    }

    public boolean HasInstruction(String key){
        return instructionMap.contains(key);
    }

    public abstract void SetInstructionSet();
}
