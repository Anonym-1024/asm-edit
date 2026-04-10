/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

/**
 *
 * @author koukola
 */
public class Machine {
    protected Register[] registers;
    protected ProgramCounter pc;
    protected InterruptRegister intr;
    protected ProcessStateRegister psr;
    protected Memory memory;

    public Register[] getRegisters() {
        return registers;
    }
    
    
    public Machine() {
        this.registers = new Register[16];
        for (int i = 0; i < 16; i++) {
            this.registers[i] = new Register();
        }
        this.pc = new ProgramCounter();
        this.psr = new ProcessStateRegister();
        this.intr = new InterruptRegister();
        this.memory = new Memory();
    }
    
    public void reset() {
        for (Register r: registers) {
            r.setContent(0);
        }
        
        pc.setContent(0);
        
        psr.setContent(0);
        memory.setContent(new byte[0]);
    }
    
    public void loadMemory(byte[] content) {
        memory.setContent(content);
    }
    
    
    
    boolean isCondValid(int cond) {
        switch (cond) {
            case 0:
                return true;
                
                
                
            case 1:
                return psr.getZ() == 1;
                
            case 2:
                return psr.getN() == 1;
                
            case 3:
                return psr.getV() == 1;
                
            case 4:
                return psr.getC() == 0;
                
            case 5:
                return psr.getC() == 1 && psr.getZ() == 0;
                
            case 6:
                return psr.getV() != psr.getN();
                
            case 7:
                return psr.getV() == psr.getN() && psr.getZ() == 0;
                
            case 9:
                return !(psr.getZ() == 1);
                
            case 10:
                return !(psr.getN() == 1);
                
            case 11:
                return !(psr.getV() == 1);
                
            case 12:
                return !(psr.getC() == 0);
                
            case 13:
                return !(psr.getC() == 1 && psr.getZ() == 0);
                
            case 14:
                return !(psr.getV() != psr.getN());
                
            case 15:
                return !(psr.getV() == psr.getN() && psr.getZ() == 0);
        }
        
        return false;
        
    }
    
    public void next() {
        byte byte0 = memory.getByte(pc.getContent());
        pc.increment();
        byte byte1 = memory.getByte(pc.getContent());
        pc.increment();
        byte byte2 = memory.getByte(pc.getContent());
        pc.increment();
        byte byte3 = memory.getByte(pc.getContent());
        pc.increment();
        
        
        
        Instruction i = new Instruction(byte0, byte1, byte2, byte3);
        
        if (isCondValid(i.getCond())) {
            i.execute(this);
        }
        
        
                
    }
    
    public void start() {
        
    }
    
    public void stop() {
        
    }
    
    public ProcessStateRegister getPsr() {
        return this.psr;
    }
    
    
    
}
