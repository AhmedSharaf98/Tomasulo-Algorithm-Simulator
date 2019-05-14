package com.AA.Simulator.ReservationStations;

import com.AA.Simulator.Enums.FunctionalUnit;
import com.AA.Simulator.Enums.Stage;
import com.AA.Simulator.Instructions.Instruction;
import com.AA.Simulator.ReOrdering.ROBEntry;
import com.AA.Simulator.ReOrdering.ReOrderBuffer;
import com.AA.Simulator.Simulator;
import com.AA.Simulator.StorageElements.Register;

public abstract class ReservationStation {
    private String Name;
    private short TotalExecutionTime, RemainingTime;
    private Short Vj;
    private Short Vk;
    private ROBEntry Qj, Qk, Dest;
    int A;
    private Stage stage;
    Instruction instruction;
    private int ActiveCycles = 0;

    ReservationStation(String name, short ET) {
        this.Name = name;
        this.TotalExecutionTime = ET;
        stage = Stage.IDLE;
    }


    void Attach(Instruction instruction){
        instruction.setReservationStation(this);
        this.instruction = instruction;
        switch (instruction.getName()){
            case "LW": case "SW": case "BEQ" :A = Integer.parseInt(instruction.getOperands()[2]); break;
            case "JMP" : A = Integer.parseInt(instruction.getOperands()[0]); break;
            case "ADDI" : Vk = Short.parseShort(instruction.getOperands()[2]); break;
        }
        for(int i = 0; i < instruction.getDependencies().size(); i++){
            Register tmp = Simulator.getInstance()
                    .getRegisterFile()
                    .getByName(instruction.getOperands()[instruction.getDependencies().get(i)]);
            if(i == 0){
                if(tmp.getQi() == null) Vj = tmp.getValue();
                else if(tmp.getQi().isReady()) Vj = tmp.getQi().getValue();
                else Qj = tmp.getQi();
            }
            else{
                if(tmp.getQi() == null) Vk = tmp.getValue();
                else if(tmp.getQi().isReady()) Vk = tmp.getQi().getValue();
                else Qk = tmp.getQi();
            }
        }
        Dest = Simulator.getInstance().getROB().Attach(instruction);
        RemainingTime = TotalExecutionTime;
        stage = Stage.ISSUE;
        instruction.issue();
    }
     void Detach(){
        Vk = Vj = null;
        Qj = Qk = Dest = null;
        stage = Stage.IDLE;
        if(instruction != null)
            instruction.removeReservationStation();
     }

    public boolean isBusy() {
        return stage != Stage.IDLE;
    }

     public void WriteBack(){
         if(stage == Stage.WRITE && Simulator.AvaialbeWrites > 0){
             Simulator.AvaialbeWrites--;
             instruction.write();
             Dest.setReady();
             Dest.setValue(Compute());
             Detach(); // Frees the reservation station.
         }
     }
     public void Tick(){ //Simulates actions when the a new cycle ticks..
         ActiveCycles++;
        if(stage == Stage.ISSUE){
            if(Qk != null && Qk.isReady()){
                Vk = Qk.getValue();
                Qk = null;
            }
            if(Qj != null && Qj.isReady()){
                Vj = Qj.getValue();
                Qj = null;
            }
            if(Qj == null && Qk == null){
                //Load Must stall until any store instruction that uses same destination is committed.
                if(instruction.getName().equals("LW")) {
                    ReOrderBuffer rob = Simulator.getInstance().getROB();
                    for (int i = 0; i < rob.getSize(); i++)
                        if (rob.getAt(i) != null && rob.getAt(i).getType() == FunctionalUnit.Store)
                            if (rob.getAt(i).getMemDest() == A) return;
                }
                stage = Stage.EXE;
            }
        }
        else if(stage == Stage.EXE){
            RemainingTime--;
            if(RemainingTime == 0) {
                stage = Stage.WRITE;
                instruction.Execute();
            }
        }
        else if(stage == Stage.COMMIT){
            //Detach();
        }
     }


     private Short Compute(){
        switch (instruction.getName()){
            case "LW":
                return Simulator.getInstance().getMemory().Read(Short.parseShort(instruction.getOperands()[2]) + Vj);
            case "SW":Dest.setMemDest((short)(Vk+ Short.parseShort(instruction.getOperands()[2])));
                return Vj;
            case "BEQ": if (Vj.equals(Vk)) return 1;
                        else return 0;
            case "JALR":
                Simulator.dependentJumpt = false;
                Simulator.PC = Vj;
                return (short)(instruction.getAdress() + 1);
            case "RET":
                Simulator.dependentJumpt = false;
                Simulator.PC = Vj;
                return null;
            case "ADD": case "ADDI": return (short)(Vk + Vj);
            case "SUB": return (short)(Vj - Vk);
            case "NAND": return (short)(~(Vk & Vj));
            case "MUL": return (short)(Vk * Vj);
        }
        return null;
     }

    public String getName() {
        return Name;
    }

    public ROBEntry getQj() {
        return Qj;
    }

    public ROBEntry getQk() {
        return Qk;
    }

    public Short getVj() {
        return Vj;
    }

    public Short getVk() {
        return Vk;
    }

    public ROBEntry getDest() {
        return Dest;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public int getActiveCycles() {
        return ActiveCycles;
    }
}