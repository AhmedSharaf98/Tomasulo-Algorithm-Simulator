package com.AA;

import com.AA.Simulator.Instructions.DefaultInstructionSet;
import com.AA.Simulator.Simulator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        Simulator.getInstance().ConfigFU((short)2,(short)1,(short)1,(short)2,(short)1,(short)10)  ;
        Simulator.getInstance().ConfigSim(new DefaultInstructionSet(),2,6,4,true);
        Simulator.getInstance().FillData(new ConsoleReader());
        Scanner sc = new Scanner(System.in);
        while (!(sc.nextLine()).equals("END")){
            if(Simulator.getInstance().Simulate())
                Simulator.getInstance().display(new ConsoleWrite());
            else {
                Simulator.getInstance().displayPerformance(new ConsoleWrite());
                break;
            }
        }
    }
}
