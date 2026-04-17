/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

/**
 *
 * @author koukola
 */
public class MachineConfig {

    public int getBootAddress() {
        return bootAddress;
    }

    public void setBootAddress(int bootAddress) {
        this.bootAddress = bootAddress;
    }

    public int getInterruptAddress() {
        return interruptAddress;
    }

    public void setInterruptAddress(int interruptAddress) {
        this.interruptAddress = interruptAddress;
    }

    public byte[] getDefaultMemory() {
        return defaultMemory;
    }

    public void setDefaultMemory(byte[] defaultMemory) {
        this.defaultMemory = defaultMemory;
    }

    public MachineConfig() {
        this.bootAddress = 0x1000;
        this.interruptAddress = 0x0000;
        this.defaultMemory = new byte[0];
    }
    
    
    
    protected int bootAddress;
    protected int interruptAddress;
    protected byte[] defaultMemory;
}
