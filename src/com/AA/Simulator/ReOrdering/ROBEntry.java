package com.AA.Simulator.ReOrdering;

import com.AA.Simulator.Enums.FunctionalUnit;
import com.AA.Simulator.Instructions.Instruction;
import com.AA.Simulator.Simulator;
import com.AA.Simulator.StorageElements.Register;
import com.AA.Simulator.Utilities.Validator;

public class ROBEntry {


    private int number;
    FunctionalUnit type;
    Register dest;
    Short value;

    public Short getMemDest() {
        return memDest;
    }

    public void setMemDest(Short memDest) {
        this.memDest = memDest;
    }

    private Short memDest;
    boolean ready;
    private Instruction instruction;

    void Attach(Instruction instruction, int number) {
        this.number = number;
        this.instruction = instruction;
        ready = false;
        type = instruction.getFunctionalUnit();
        if(instruction.isHasDestination()) { //Since Some inst doesn't have
            dest = Simulator.getInstance().getRegisterFile().getByName(instruction.getOperands()[0]);
            dest.setQi(this);
        }
    }

    public boolean isReady() {
        return ready;
    }
    public void setReady() {
        this.ready = true;
    }
    public FunctionalUnit getType() {
        return type;
    }

    public Register getDest() {
        return dest;
    }

    public Short getValue() {
        return value;
    }
    public void setValue(Short value) {
        this.value = value;
    }
    void commit(){
        Simulator.ExuctedInstructions++;
        instruction.commit();
        if(instruction.getName().equals("SW"))
            Simulator.getInstance().getMemory().Write(memDest, value);
        else if(instruction.isHasDestination()) {
            dest.setValue(value);
            dest.removeQi();
        } else { //For Jump/Branch insructions
//            if(instruction.getName().equals("JMP")) return; //Jump does nothing
            switch (instruction.getName()) {
                case "BEQ":
                    Simulator.Branches++;
                    if (value == 0 && Short.parseShort(instruction.getOperands()[2]) < 0){
                        Simulator.PC = (short)(instruction.getAdress() + 1);
                        Simulator.MisPredicted++;
                        Simulator.flushed = true;
                    } else if( value == 1 && Short.parseShort(instruction.getOperands()[2]) >= 0){
                        Simulator.PC = Short.parseShort(instruction.getOperands()[2]);
                        Simulator.MisPredicted++;
                        Simulator.flushed = true;
                    }
                    break;
            }
        }
    }

    public int getNumber() {
        return number;
    }
}
