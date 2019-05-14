package com.AA.Simulator.ReservationStations;

public class MultiplicationStation extends ReservationStation {
    public static int count = 0;
    public static short ET = 10;
    public MultiplicationStation() {
        super("Mul" + count, ET);
        count++;
    }
}
