package com.AA.Simulator.ReservationStations;

import com.AA.Simulator.Enums.FunctionalUnit;
import com.AA.Simulator.Instructions.Instruction;
import com.AA.Simulator.Instructions.Load;

public class ReservationStationController {
    private ReservationStation[][] stations = new ReservationStation[7][];
    public ReservationStationController(int Load, int Store, int Jump, int Branch, int Arth, int bitwise, int mult) {
        LoadStation.count = 0;
        stations[0] = new LoadStation[Load];
    }

    public ReservationStationController() {
        stations[0] = new LoadStation[2];
        stations[1] = new StoreStation[2];
        stations[2] = new JumpStation[3];
        stations[3] = new BranchStation[2];
        stations[4] = new ArithmeticStation[3];
        stations[5] = new BitwiseStation[1];
        stations[6] = new MultiplicationStation[2];
        for(int i = 0; i < stations[0].length; i++){ stations[0][i] = new LoadStation(); }
        for(int i = 0; i < stations[1].length; i++){ stations[1][i] = new StoreStation(); }
        for(int i = 0; i < stations[2].length; i++){ stations[2][i] = new JumpStation(); }
        for(int i = 0; i < stations[3].length; i++){ stations[3][i] = new BranchStation(); }
        for(int i = 0; i < stations[4].length; i++){ stations[4][i] = new ArithmeticStation(); }
        for(int i = 0; i < stations[5].length; i++){ stations[5][i] = new BitwiseStation(); }
        for(int i = 0; i < stations[6].length; i++){ stations[6][i] = new MultiplicationStation(); }
    }

    private int getUnitIndex(FunctionalUnit functionalUnit) {
        switch (functionalUnit){
            case Load: return 0;
            case Store: return 1;
            case JUMP: return 2;
            case Branch: return 3;
            case Arthematic: return 4;
            case Bitwise: return 5;
            case Multiplier: return 6;
        }
        return  0; //Default
    }

    public boolean isWorking() {
        for (ReservationStation[] reservationStation : stations)
            for (ReservationStation reservationStation1 : reservationStation)
                if(reservationStation1.isBusy()) return true;

        return false;
    }

    public boolean TryToReserve(Instruction instruction) {
        FunctionalUnit FU = instruction.getFunctionalUnit();
        int FU_Index = getUnitIndex(FU);
        for(ReservationStation reservationStation:  stations[FU_Index]) {
            if (!reservationStation.isBusy()){
                reservationStation.Attach(instruction);
                return true;
            }
        }
        return false;
    }
    public ReservationStation[][] getStations() {
        return stations;
    }

    public void Flush() {
        for (ReservationStation[] reservationStation : stations)
            for (ReservationStation reservationStation1 : reservationStation)
                reservationStation1.Detach();
    }
}
