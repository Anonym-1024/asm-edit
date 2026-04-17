/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

/**
 *
 * @author koukola
 */
public class InterruptProgramCounter {
    protected int content;
    protected int defaultAddress;

    public InterruptProgramCounter() {
        this.content = 0;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public void setDefaultAddress(int defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
    
    
    
    public void increment() {
        this.content += 1;
    }
    
    public void setByte0(int byte0) {
        this.content &= 0xFF00;
        this.content |= (byte0 & 0x00FF);
    }
    
    public void setByte1(int byte1) {
        this.content &= 0x00FF;
        this.content |= (byte1 & 0xFF00);
    }
    
    public int getByte0() {
        return this.content & 0x00FF;
    }
    public int getByte1() {
        return (this.content & 0xFF00) >> 8;
    }
    
    public void reset() {
        this.content = this.defaultAddress;
    }
    
    
}
