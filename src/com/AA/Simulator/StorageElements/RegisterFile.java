package com.AA.Simulator.StorageElements;

public class RegisterFile {
    Register[] Registers = new Register[8];

    public RegisterFile() {
        for(int i =0; i < 8; i++) {
            Registers[i] = new Register();
        }
    }

    public Register getByName(String name){
        int index = name.charAt(1) - '0';
        return Registers[index];
    }
    public Register getAt(int index){
        return Registers[index];
    }

    public void Flush() {
        for(int i =0; i < 8; i++) {
            Registers[i].removeQi();
        }
    }
}
