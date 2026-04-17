/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

import java.util.Vector;

/**
 *
 * @author koukola
 */

public class Machine {
    
    protected MachineConfig config;
    protected State state;
    
    protected ControlUnit controlUnit;
    
    protected Register[] registers;
    protected ALU alu;
    protected ProgramCounter pc;
    protected InterruptProgramCounter intpc;
    
    protected ProcessStateRegister psr;
    protected InterruptRegister intr;
    
    protected PageTableBaseRegister ptbr;
    protected Memory memory;
    
    
    
    public enum State {
        IDLE,
        STOPPED,
        RUNNING,
    }
    
    

    
    
    
    public Machine() {
        
       this.config = new MachineConfig();
       this.state = State.STOPPED;
       this.controlUnit = new ControlUnit(this);
       this.registers = new Register[16];
       for (int i = 0; i < 16; i++) {
           registers[i] = new Register();
       }
       this.alu = new ALU();
       this.pc = new ProgramCounter();
       this.intpc = new InterruptProgramCounter();
       this.psr = new ProcessStateRegister();
       this.intr = new InterruptRegister();
       this.ptbr = new PageTableBaseRegister();
       this.memory = new Memory();
       
        
    }

    public Register[] getRegisters() {
        return registers;
    }

    public ProgramCounter getPc() {
        return pc;
    }

    public InterruptRegister getIntr() {
        return intr;
    }

    public ProcessStateRegister getPsr() {
        return psr;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setConfig(MachineConfig config) {
        this.config = config;
    }

    public MachineConfig getConfig() {
        return config;
    }

    public State getState() {
        return state;
    }
    
    
    
    
    
    
    
    public void startAndBoot() {
        if (state != State.STOPPED) {
            return;
        }
        
        for (Register r: registers) {
            r.setContent(0);
        }
        
        pc.setContent(config.bootAddress);
        intpc.setDefaultAddress(config.interruptAddress);
        intpc.reset();
        
        psr.setContent(0);
        intr.clear();
        ptbr.setContent(0);
        
        memory.setBytes(0, config.defaultMemory);
        
        state = State.IDLE;
    }
    
    
    
    
    public void nextCycle() {
        if (state == State.IDLE) {
            controlUnit.cycle();
        }
    }
    
    public void run() {
        state = State.RUNNING;
        while (state == State.RUNNING) {
            controlUnit.cycle();
        }
    }
    
    public void stop() {
        state = State.STOPPED;
    }
   
    public void pause() {
        state = State.IDLE;
    }
    
    
    
    
    
    
    
    
    
    
}
