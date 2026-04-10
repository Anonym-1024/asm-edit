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
    protected byte[] content;

    public void setContent(byte[] content) {
        this.content = content;
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
