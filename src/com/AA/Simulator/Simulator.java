package com.AA.Simulator;

import com.AA.ConsoleWrite;
import com.AA.Simulator.Enums.Status;
import com.AA.Simulator.Instructions.Instruction;
import com.AA.Simulator.Instructions.InstructionBuffer;
import com.AA.Simulator.Instructions.InstructionSet;
import com.AA.Simulator.Interfaces.SimulatorDataReader;
import com.AA.Simulator.Interfaces.SimulatorDataRepresenter;
import com.AA.Simulator.ReOrdering.ReOrderBuffer;
import com.AA.Simulator.ReservationStations.*;
import com.AA.Simulator.StorageElements.Memory;
import com.AA.Simulator.StorageElements.RegisterFile;
import com.AA.Simulator.Utilities.InstructionGenerator;
import java.util.ArrayList;

public class Simulator {
    public static boolean flushed = false;
    public static int AvaialbeWrites;
    public static boolean dependentJumpt = false;
    public static short PC = 0; //Program Counter
    public static short ExuctedInstructions = 0;
    public static short Branches = 0;
    public static short MisPredicted = 0;
    static short instCount = 0;
    RegisterFile registerFile = new RegisterFile();
    private final int MemorySize = 65536;
    private Memory memory = new Memory(MemorySize);
    ReOrderBuffer ROB;
    InstructionBuffer instructionBuffer;
    private ArrayList<Instruction> instructions = new ArrayList<>(); //Base program
    private ArrayList<Instruction> overAllinstructions = new ArrayList<>(); //Executed instructions..
    private ReservationStationController reservationStationController;
    private int ClockCycles = 0; //Program Counter
    private InstructionSet instructionSet;
    private boolean EducationalMode, StepForward;
    private Status status;
    //Singleton Pattern
    private static Simulator ourInstance = new Simulator();
    private int PipeLineWidth;

    public static Simulator getInstance() {
        return ourInstance;
    }
    private Simulator() { }

    //Setters-Getters
    public RegisterFile getRegisterFile() {
        return registerFile;
    }
    public Memory getMemory() {
        return memory;
    }
    public int getMemorySize() {
        return MemorySize;
    }
    public int getClockCycles() {
        return ClockCycles;
    }
    public void ConfigSim(InstructionSet simInstructionSet,int pipeLineWidth, int ROBsize, int InstBufferSize, Boolean EducationMode){
        instructionSet =  simInstructionSet;
        this.PipeLineWidth = pipeLineWidth;
        ROB = new ReOrderBuffer(ROBsize);
        instructionBuffer = new InstructionBuffer(InstBufferSize);
        this.EducationalMode = EducationMode;
        reservationStationController = new ReservationStationController();
    }

    public void ConfigFU(short load, short store, short branch, short arth, short bitwise, short mult){
        LoadStation.ET = load;
        StoreStation.ET = store;
        BranchStation.ET = branch;
        ArithmeticStation.ET = arth;
        BitwiseStation.ET = bitwise;
        MultiplicationStation.ET = mult;

    }
    public boolean RegisterInstruction(String instructionLine){
        String[] parts  = instructionLine.split(" ", 2);
        if(parts.length != 2) return false;
        parts[1] = parts[1].replaceAll("\\s",""); //Removing all white spaces from operands to validate them
        String[] operands = parts[1].split(","); //Splitting operands..
        if(!instructionSet.HasInstruction(parts[0])){
            System.out.println("\033[31mInstruction \"" + parts[0] + "\" Is not Supported in this InstructionSet\033[0m");
            return false;
        }
//        Instruction i = instructionSet.GetInstruction(parts[0]);
        Instruction i = InstructionGenerator.generate(parts[0]);
        i.setName(parts[0]);
        i.setAddress(instCount++);
        i.setOperands(operands);
        if(!i.Validate()){
            System.out.println("\033[31mError in Instruction \"" + instructionLine + "\" , " + parts[0] + " Operands couldn't be validated\033[0m");
            return false;
        }
        System.out.println("\033[32mValid!\033[0m");
        this.instructions.add(i);
        return true;
    }
    public void FillData(SimulatorDataReader simulatorDataReader){
        simulatorDataReader.FillData();
    }
    public ReOrderBuffer getROB() {
        return ROB;
    }
    public boolean Simulate(){
        flushed = false;
        AvaialbeWrites = PipeLineWidth;
        StepForward = true;
        while (!EducationalMode || StepForward) {
            if(status == Status.Finished) return false;
            status = Status.Working;
            ClockCycles++;
            for (int i = 0; i < PipeLineWidth &&  !ROB.isEmpty() && ROB.getNext().isReady() && !flushed; i++)
               ROB.commit();

            if(flushed){
                Flush();
            }

            for (Instruction instruction1 : overAllinstructions)
                if (instruction1.getReservationStation() != null)
                    instruction1.getReservationStation().WriteBack();


            for (int i = 0; i < PipeLineWidth && ROB.isAvailable() && !instructionBuffer.isEmpty() && status != Status.Stalled; i++) {
                if (!reservationStationController.TryToReserve(instructionBuffer.getNext()))
                    status = Status.Stalled;
                else instructionBuffer.MoveHead();
            }


            for (Instruction instruction1 : overAllinstructions)
                if (instruction1.getReservationStation() != null)
                    instruction1.getReservationStation().Tick();

            for (int i = 0; i < PipeLineWidth && instructionBuffer.isAvailable() && PC < instructions.size()&& !dependentJumpt; i++) {
                Instruction instruction = instructions.get(PC);
                overAllinstructions.add(InstructionGenerator.GetCopy(instruction));
                instructionBuffer.Attach(overAllinstructions.get(overAllinstructions.size() - 1));
                if(instruction.getName().equals("JALR") || instruction.getName().equals("RET")) dependentJumpt = true;
                if(instruction.getName().equals("JMP"))
                    PC = Short.parseShort(instruction.getOperands()[0]);
                else if(instruction.getName().equals("BEQ") && Short.parseShort(instruction.getOperands()[2]) < 0)
                    PC = Short.parseShort(instruction.getOperands()[2]);
                else PC++;

            }

            if (!reservationStationController.isWorking() && instructionBuffer.isEmpty() && ROB.isEmpty())
                status = Status.Finished;
            StepForward = false;
        }
        return true;
    }

    public void Flush() {
        System.out.println("Flushing");
        flushed = true;
        ROB.Flush();
        instructionBuffer.Flush();
        reservationStationController.Flush();
        registerFile.Flush();
        System.out.println(ROB.isEmpty());
        System.out.println(PC);
    }

    public void display(SimulatorDataRepresenter simulatorDataRepresenter){
        simulatorDataRepresenter.displayMetaData(this);
        simulatorDataRepresenter.displayInstructionQueue(overAllinstructions);
        simulatorDataRepresenter.displayInstructionBuffer(instructionBuffer);
        simulatorDataRepresenter.displayReorderBuffer(ROB);
        simulatorDataRepresenter.displayReservationStations(reservationStationController);
        simulatorDataRepresenter.displayRegisterFile(registerFile);
    }

    public void displayPerformance(SimulatorDataRepresenter simulatorDataRepresenter) {
        simulatorDataRepresenter.DisplayPerformance(this, reservationStationController);
    }
}