package com.AA.Simulator.ReservationStations;

public class LoadStation extends ReservationStation {
    public static int count = 0;
    public static short ET = 2;
    public LoadStation() {
        super("Load" + count, ET);
        count++;
    }
}
