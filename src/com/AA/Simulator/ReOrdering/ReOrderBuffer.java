package com.AA.Simulator.ReOrdering;

import com.AA.Simulator.Instructions.Instruction;

public class ReOrderBuffer {
    private int head;
    private int tail;
    private int currentSize;
    private int size;
    private ROBEntry[] Entries;
    public ReOrderBuffer(int size){
        head = -1;
        tail = 0;
        this.size = size;
        Entries = new ROBEntry[size];
        currentSize = 0;
    }
    public void commit(){
        Entries[head].commit();
        MoveHead();
    }


    public ROBEntry Attach(Instruction instruction){
        Entries[tail] = new ROBEntry();
        Entries[tail].Attach(instruction, tail + 1);
        ROBEntry tmp = Entries[tail];
        tail = (tail + 1) % size;
        currentSize++;
        if(head == -1) head = 0;
        return tmp;
    }
    public void MoveHead() {
        Entries[head] = null;
        head = (head + 1) %size;
        currentSize--;
    }

    public boolean isAvailable(){
        return (currentSize < size);
    }

    public boolean isEmpty(){
        return  (currentSize == 0);
    }

    public void Flush(){
        head = -1;
        tail = 0;
        currentSize = 0;
        Entries = new ROBEntry[size];
    }


    public ROBEntry getNext(){
        return Entries[head];
    }

    public int getSize() {
        return size;
    }

    public ROBEntry getAt(int index){
        return Entries[index];
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }
}
