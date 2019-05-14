package com.AA.Simulator.StorageElements;

import com.AA.Simulator.ReOrdering.ROBEntry;
import com.AA.Simulator.ReservationStations.ReservationStation;

public class Register {
    private String Name;
    private short value;
    private ROBEntry Qi;
    private static int count = 0;

    public Register() {
        Name = "R" + count++;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public ROBEntry getQi() {
        return Qi;
    }

    public void setQi(ROBEntry qi) {
        Qi = qi;
    }

    public void removeQi() {
        Qi = null;
    }
}
