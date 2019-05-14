package com.AA.Simulator.Instructions;

public class InstructionBuffer  {
    private int head;
    private int tail;
    private int currentSize;
    private int size;
    private Instruction[] Entries;
    public InstructionBuffer(int size){
        head = -1;
        tail = 0;
        this.size = size;
        currentSize = 0;
        Entries = new Instruction[size];
    }

    public boolean isAvailable(){
        return (currentSize < size);
    }

    public boolean isEmpty(){
        return  (currentSize == 0);
    }

    public void Attach(Instruction instruction){
        Entries[tail] = instruction;
        tail = (tail + 1) % size;
        currentSize++;
        if(head == -1) head = 0;
        instruction.fetch();
    }
    public void Flush(){
        head = -1;
        tail = 0;
        currentSize = 0;
        Entries = new Instruction[size];
    }

    public Instruction getNext(){
        return Entries[head];
    }

    public void MoveHead() {
        Entries[head] = null;
        head = (head + 1) %size;
        currentSize--;
    }

    public int getSize() {
        return size;
    }

    public Instruction getAt(int index){
        return Entries[index];
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }
}
