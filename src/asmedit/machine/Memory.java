/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

/**
 *
 * @author koukola
 */
public class Memory {
    
    protected int address;
    protected byte[] content;
    protected int size;
    
    

    public Memory() {
        this.address = 0;
        this.size = 1 << 20;
        this.content = new byte[this.size];
        
    }
    
    
    
    

    public void setAddress(int addr) {
        this.address = addr;
    }
        
   
   
    
    
    

    public void setBytes(int addr, byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            if (addr + i >= this.size) {
                break;
            }
            this.content[addr + i] = bytes[i];
        }
    }
    
    public void setByte(int addr, byte v) {
        if (addr >= content.length) {
            return;
        }
        
        content[addr] = v;
    }
    
    public byte getByte(int addr) {
        if (addr < content.length) {
            return content[addr];
        }
        return 0;
    }
    
    public void clear() {
        this.content = new byte[this.size];
    }
    
    
    
    
}
