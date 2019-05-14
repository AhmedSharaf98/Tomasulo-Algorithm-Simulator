package com.AA;

import com.AA.Simulator.Simulator;
import com.AA.Simulator.Interfaces.SimulatorDataReader;
import com.AA.Simulator.Utilities.Validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConsoleReader implements SimulatorDataReader {
    @Override
    public void FillData() {
        try {
            ReadInstructions();
        } catch (FileNotFoundException e){
            System.out.println("File you specified For Assembly Instructions couldn't be found.");
            return;
        }
        try {
            ReadData();
        } catch (FileNotFoundException e) {
            System.out.println("File you specified For Data Memory couldn't be found.");
        }
    }

    private void ReadInstructions() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        int decision;
        System.out.println("Please Enter Assembly Source (0: File, 1: Manual)");

        while (!sc.hasNextInt() || ((decision = sc.nextInt()) > 1) || decision < 0)
            System.out.println("!Invalid Input!");

        if(decision == 0){
            sc = getScanner(sc);
            while (sc.hasNextLine())
                Simulator.getInstance().RegisterInstruction(sc.nextLine());
        } else {
            System.out.println("Please Enter Assembly Code Line By Line.. (-1) For Ending");
            while (sc.hasNextLine()){
                String in = sc.nextLine();
                if(in.equals("-1")) break;
                Simulator.getInstance().RegisterInstruction(in);
            }
        }
    }


    private void ReadData() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        int decision;
        System.out.println("Please Enter Memory Source (0: File, 1: Manual, 2: No Initial Memory)");

        while (!sc.hasNextInt() || ((decision = sc.nextInt()) > 2) || decision < 0)
            System.out.println("!Invalid Input!");

        int dataCounter = 1;
        int add = 0;
        short data;
        if(decision == 1){
            boolean valid = true;
            System.out.println("\033[31mPlease Enter Memory Data, Non-Int or Negative location values for ending input" +
                    " Valid Range: [0:" + Simulator.getInstance().getMemorySize() + "]\033[0m");
            while (valid){
                System.out.println("Enter Address Location For Data Entry Number: " + dataCounter);
                do{
                    if(!sc.hasNextInt() || ((add = sc.nextInt()) < 0)) valid = false;
                } while (!Validator.InRangeInt(add, 0 , Simulator.getInstance().getMemorySize()) && valid);

                if(valid) {
                    System.out.println("Enter Data @Location: " + add);
                    while (!sc.hasNextShort()) {
                        System.out.println("Input Must Be 16-bit value");
                    }
                    data = sc.nextShort();
                    Simulator.getInstance().getMemory().Write(add, data);
                }
            }
        } else if(decision == 0){
            sc = getScanner(sc);
            while (sc.hasNextInt()) {
                add = sc.nextInt();
                if(!Validator.InRangeInt(add, 0 , Simulator.getInstance().getMemorySize())){
                    System.out.println("\033[31mInvalid Memory Address: " + add +"File will not procced\033[0m");
                    return;
                }
                if(!sc.hasNextShort()){
                    System.out.println("Invalid Memory Data, Input Must Be 16-bit value");
                    return;
                } else data = sc.nextShort();
                Simulator.getInstance().getMemory().Write(add, data);
            }
        }
        System.out.println("\033[32mMemory Initialized Successfully\033[0m");
    }

    private Scanner getScanner(Scanner sc) throws FileNotFoundException {
        String path;
        System.out.println("Please Enter File Path");
        path = sc.next();
//        path = "/home/shayaf/Desktop/Test002.txt"; //TODO : Remove this after finishing testing..
        File file = new File(path);
        sc = new Scanner(file);
        return sc;
    }
}