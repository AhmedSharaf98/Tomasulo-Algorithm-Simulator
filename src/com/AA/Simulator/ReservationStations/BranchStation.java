package com.AA.Simulator.ReservationStations;

public class BranchStation extends ReservationStation {
    public static int count = 0;
    public static short ET = 1;
    public BranchStation() {
        super("Beq" + count, ET);
        count++;
    }
}
