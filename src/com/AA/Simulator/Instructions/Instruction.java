package com.AA.Simulator.Instructions;

import com.AA.Simulator.Enums.FunctionalUnit;
import com.AA.Simulator.ReservationStations.ReservationStation;
import com.AA.Simulator.Simulator;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Instruction implements Cloneable {
    private ReservationStation reservationStation;
    private String name;
    private short Address;
    String[] operands;
    Instruction(){
        AssignFunctionalUnit();
        setDependencies();
        hasDestination = setHasDestination();
    }
    public Instruction(Instruction copy){
        name = copy.getName();
        operands = copy.operands;
        Address = copy.Address;
        Dependencies = copy.Dependencies;
        functionalUnit = copy.functionalUnit;
        hasDestination = copy.hasDestination;
    }
    FunctionalUnit functionalUnit;
    private Integer Fetch;
    private Integer Issued;
    private Integer Executed;
    private Integer Written;
    private Integer Commited;
    protected ArrayList<Integer> Dependencies = new ArrayList<>(); //indexes of operands that might need to wait (Registers)
    private boolean hasDestination;
    
    public void fetch(){Fetch = Simulator.getInstance().getClockCycles(); }
    public void issue(){Issued = Simulator.getInstance().getClockCycles(); }
    public void Execute(){ Executed = Simulator.getInstance().getClockCycles(); }
    public void write(){ Written = Simulator.getInstance().getClockCycles(); }
    public void commit(){Commited = Simulator.getInstance().getClockCycles(); }
    public Integer getFetch() { return Fetch; }
    public Integer getIssued() { return Issued; }
    public Integer getExecuted() { return Executed; }
    public Integer getWritten() { return Written; }
    public Integer getCommited() { return Commited; }
    public ArrayList<Integer> getDependencies() {
        return Dependencies;
    }
    public abstract boolean Validate();
    public abstract void AssignFunctionalUnit();
    public abstract void setDependencies();
    public abstract boolean setHasDestination();
    public boolean isHasDestination() { return hasDestination; }
    public ReservationStation getReservationStation() {
        return reservationStation;
    }
    public void setReservationStation(ReservationStation reservationStation) {
        this.reservationStation = reservationStation;
    }
    public void setOperands(String[] operands){
        this.operands = operands;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() { return name; }
    public String[] getOperands() {
        return operands;
    }
    public short getAdress() {
        return Address;
    }
    public void setAddress(short address) {
        Address = address;
    }
    public FunctionalUnit getFunctionalUnit() {
        return functionalUnit;
    }
    public void removeReservationStation(){reservationStation = null;}
}
