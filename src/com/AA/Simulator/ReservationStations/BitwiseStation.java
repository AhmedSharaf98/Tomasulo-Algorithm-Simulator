package com.AA.Simulator.ReservationStations;

public class BitwiseStation extends ReservationStation {
    public static int count = 0;
    public static short ET = 1;
    public BitwiseStation() {
        super("Nand" + count, ET);
        count++;
    }
}
