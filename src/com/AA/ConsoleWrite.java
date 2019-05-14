package com.AA;

import com.AA.Simulator.Instructions.Instruction;
import com.AA.Simulator.Instructions.InstructionBuffer;
import com.AA.Simulator.Interfaces.SimulatorDataRepresenter;
import com.AA.Simulator.ReOrdering.ROBEntry;
import com.AA.Simulator.ReOrdering.ReOrderBuffer;
import com.AA.Simulator.ReservationStations.ReservationStation;
import com.AA.Simulator.ReservationStations.ReservationStationController;
import com.AA.Simulator.Simulator;
import com.AA.Simulator.StorageElements.Register;
import com.AA.Simulator.StorageElements.RegisterFile;

import java.util.ArrayList;
import java.util.Arrays;


public class ConsoleWrite implements SimulatorDataRepresenter {
    @Override
    public void displayMetaData(Simulator simulator) {
        System.out.println("\033[31mCycle: " + simulator.getClockCycles() + "\033[0m");
    }

    @Override
    public void displayInstructionQueue(ArrayList<Instruction> instructions) {
        System.out.println("\033[32mContent of the Instruction Queue\033[0m");
        final Object[][] table = new String[instructions.size()][];
        for(int i = 0; i < instructions.size(); i++){
            Instruction in = instructions.get(i);
            table[i] = new String[]{
                    in.getName(),
                    Arrays.toString(in.getOperands()),
                    "F: " + ((in.getFetch()!= null)?in.getFetch(): "" ),
                    "I: " + ((in.getIssued()!= null)?in.getIssued(): "" ),
                    "E: " + ((in.getExecuted()!= null)?in.getExecuted(): "" ),
                    "W: " + ((in.getWritten()!= null)?in.getWritten(): "" ),
                    "C: " + ((in.getCommited()!= null)?in.getCommited(): "" )
            };
        }

        for (final Object[] row : table)
            System.out.format("%-5s%-20s%-7s%-7s%-7s%-7s%-7s\n", row);
    }

    @Override
    public void displayInstructionBuffer(InstructionBuffer instructionBuffer) {
        System.out.println("\033[32mContent of the Instruction Buffer\033[0m");
        for(int i = 1; i <= instructionBuffer.getSize(); i++){
            PrintBufferState(i, instructionBuffer.getHead(), instructionBuffer.getTail());
            if(instructionBuffer.getAt(i - 1) != null) {
                System.out.print("  "  + instructionBuffer.getAt(i - 1).getName()+ "  " + Arrays.toString(instructionBuffer.getAt(i - 1).getOperands()));
            } else System.out.print("\t\t");
            System.out.println();
        }
    }

    @Override
    public void displayReservationStations(ReservationStationController reservationStationController) {
        System.out.println("\033[32mContent of the ReservationStations\033[0m");
        for (ReservationStation[] reservationStation : reservationStationController.getStations())
            for (ReservationStation r : reservationStation){
                System.out.print(r.getName()+ '\t' + r.isBusy() + "  ");
                if(r.getQj() != null) System.out.print(r.getInstruction().getName() + "  ");
                else System.out.print("    ");
                if(r.getVj() != null) System.out.print(r.getVj() + "  ");
                else System.out.print("     ");
                if(r.getVk() != null) System.out.print(r.getVk() + "  ");
                else System.out.print("     ");
                if(r.getQj() != null) System.out.print("#" + r.getQj().getNumber() + "  ");
                else System.out.print("     ");
                if(r.getQk() != null) System.out.print("#" + r.getQk().getNumber() + "  ");
                else System.out.print("     ");
                if(r.getDest() != null) System.out.print("#" + r.getDest().getNumber());
                else System.out.print("     ");
                System.out.println();
            }
    }

    @Override
    public void displayReorderBuffer(ReOrderBuffer reOrderBuffer) {
        System.out.println("\033[32mContent of the Reorder Buffer\033[0m");
        for(int i = 1; i <= reOrderBuffer.getSize(); i++){
            PrintBufferState(i, reOrderBuffer.getHead(), reOrderBuffer.getTail());
            if(reOrderBuffer.getAt(i - 1) != null) {
                ROBEntry tmp = reOrderBuffer.getAt(i - 1);
                System.out.print("  "  + tmp.getType().toString() + "  ");
                if(tmp.getDest() != null)
                    System.out.print(tmp.getDest().getName());
                if(tmp.getMemDest() != null)
                    System.out.print(tmp.getMemDest());
                System.out.print("  " + tmp.getValue() + "  " + tmp.isReady());
            } else System.out.print("\t\t");
            System.out.println();
        }
    }

    @Override
    public void displayRegisterFile(RegisterFile registerFile) {
        System.out.println("\033[32mContent of the RegisterFile\033[0m");
        for(int i = 0; i < 8; i++){
            Register r = registerFile.getAt(i);
            System.out.print(r.getName() + '\t' + r.getValue() + '\t');
            if(r.getQi() != null) System.out.print("#" + r.getQi().getNumber());
            System.out.println();
        }
        System.out.println("\033[32mContent of the Memory\033[0m");
        System.out.println("@300 " + Simulator.getInstance().getMemory().Read(300));
        System.out.println("@301 " + Simulator.getInstance().getMemory().Read(301));
        System.out.println("@302 " + Simulator.getInstance().getMemory().Read(302));
        System.out.println("@303 " + Simulator.getInstance().getMemory().Read(303));

//        System.out.println("\033[32mContent of the Memory\033[0m");
//        System.out.println("@100 " + Simulator.getInstance().getMemory().Read(100));
//        System.out.println("@101 " + Simulator.getInstance().getMemory().Read(101));
//        System.out.println("@102 " + Simulator.getInstance().getMemory().Read(102));
//        System.out.println("@103 " + Simulator.getInstance().getMemory().Read(103));
//        System.out.println("@104 " + Simulator.getInstance().getMemory().Read(104));
//        System.out.println("@105 " + Simulator.getInstance().getMemory().Read(105));
//        System.out.println("@106 " + Simulator.getInstance().getMemory().Read(106));
    }

    @Override
    public void DisplayPerformance(Simulator instance, ReservationStationController reservationStationController) {
      System.out.println("\033[31m=====================Performance=====================\033[0m");
      System.out.println("\033[32mET: " + instance.getClockCycles() + " Cycles\033[0m");
      System.out.println("\033[32mPIC: " + (double) Simulator.ExuctedInstructions /instance.getClockCycles() + "\033[0m");
      System.out.println("\033[32mTotal Branches: " + Simulator.Branches + " Branch\033[0m");
      System.out.println("\033[32mMissPrediction: " + (double) Simulator.MisPredicted / Simulator.Branches + "%\033[0m");
      System.out.println("\033[31m=====================ReservationStations Summary=====================\033[0m");

      String[] out = new String[4];
        for (ReservationStation[] reservationStation : reservationStationController.getStations())
            for (ReservationStation reservationStation1 : reservationStation){
                out[0] = reservationStation1.getName();
                out[1] = "Active Cycles";
                out[2] = Integer.toString(reservationStation1.getActiveCycles());
                out[3] = "Cycles";
                System.out.format("\033[32m%-8s%-15s%-4s%-7s\n\033[0m", out);
            }
        System.out.println("\033[31m==============================================\033[0m");
    }

    private void PrintBufferState(int i, int head, int tail) {
        if(head == -1 && i == 1) System.out.print("H ");
        else if(head == i - 1) System.out.print("H ");
        else System.out.print("  ");
        if(tail == i - 1) System.out.print("T ");
        else System.out.print("  ");
        System.out.print(i);
    }
}
