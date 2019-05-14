package com.AA.Simulator.Interfaces;

import com.AA.Simulator.Instructions.Instruction;
import com.AA.Simulator.Instructions.InstructionBuffer;
import com.AA.Simulator.ReOrdering.ReOrderBuffer;
import com.AA.Simulator.ReservationStations.ReservationStationController;
import com.AA.Simulator.Simulator;
import com.AA.Simulator.StorageElements.RegisterFile;

import java.util.ArrayList;

public interface SimulatorDataRepresenter {
    void displayMetaData(Simulator simulator);
    void displayInstructionQueue(ArrayList<Instruction> instructions);
    void displayInstructionBuffer(InstructionBuffer instructionBuffer);
    void displayReservationStations(ReservationStationController reservationStationController);
    void displayReorderBuffer(ReOrderBuffer reOrderBuffer);
    void displayRegisterFile(RegisterFile registerFile);
    void DisplayPerformance(Simulator instance, ReservationStationController reservationStationController);
}
