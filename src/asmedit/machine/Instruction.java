/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

import java.lang.annotation.Annotation;
import java.util.function.BiConsumer;

/**
 *
 * @author koukola
 */
public class Instruction {
    byte byte0;
    byte byte1;
    byte byte2;
    byte byte3;
    
    public Instruction(byte byte0, byte byte1, byte byte2, byte byte3) {
        this.byte0 = byte0;
        this.byte1 = byte1;
        this.byte2 = byte2;
        this.byte3 = byte3;
    }
    

    
    public int getOpcode() {
        return (((byte0 & 0x0F) << 4) | ((byte1 & 0xF0) >> 4)) >> 1;
    }
    
    public int getCond() {
        return (byte0 & 0xF0) >> 4;
    }
    
    public boolean isI() {
        return ((byte1 & 0x10) >> 4) == 1;
    }
    
    public int getArg1() {
        return (byte1 & 0x0F);
    }
    
    public int getArg2() {
        return (byte2 & 0xF0) >> 4;
    }
    
    public int getArg3() {
        return (byte2 & 0x0F);
    }
    
    public int getByte2() {
        return byte2;
    }
    
    public int getByte3() {
        return byte3;
    }

    public void execute(Machine m) {
        Instructions.INSTRUCTION_TABLE[this.getOpcode()].execute(this, m);
    }
    
    

}
