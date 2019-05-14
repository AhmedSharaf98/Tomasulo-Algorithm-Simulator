package com.AA.Simulator.ReservationStations;

public class JumpStation extends ReservationStation {
    public static int count = 0;
    public static short ET = 1;
    public JumpStation() {
        super("Jmp" + count, ET);
        count++;
    }
}
