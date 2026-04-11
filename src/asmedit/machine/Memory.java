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
    
    
    protected PageTableBaseRegister ptbr;
    protected ProcessStateRegister psr;

    public Memory(PageTableBaseRegister ptbr, ProcessStateRegister psr) {
        this.address = 0;
        this.content = new byte[1 << 20];
        this.ptbr = ptbr;
        this.psr = psr;
    }
    
    

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setAddress(int addr) {
        this.address = addr;
    }
        
    
    
    
    public byte getByte(int addr) {
        if (addr < content.length) {
            return content[addr];
        }
        return 0;
    }
    
    public void setByte(int addr, byte v) {
        if (addr >= content.length) {
            return;
        }
        
        content[addr] = v;
    }
}
