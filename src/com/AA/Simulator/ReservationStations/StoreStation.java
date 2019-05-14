package com.AA.Simulator.ReservationStations;

public class StoreStation extends ReservationStation {
    public static int count = 0;
    public static short ET = 1;
    public StoreStation() {
        super("Store" + count, ET);
        count++;
    }
}
