package com.AA.Simulator.StorageElements;

public class Memory{
    private short[] content = null;
    public Memory(int size) {
        this.content = new short[size];
    }

    public void Write(int address, short data){
        content[address] = data;
    }

    public short Read(int address){
        return content[address];
    }
}
