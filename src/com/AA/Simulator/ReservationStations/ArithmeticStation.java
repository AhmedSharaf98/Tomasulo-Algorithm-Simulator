package com.AA.Simulator.ReservationStations;

public class ArithmeticStation extends ReservationStation {
    public static int count = 0;
    public static short ET = 2;
    public ArithmeticStation() {
        super("Add" + count, ET);
        count++;
    }
}
