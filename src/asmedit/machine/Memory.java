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
    
    
    protected Machine m;

    public Memory(Machine m) {
        this.address = 0;
        this.size = 1 << 20;
        this.content = new byte[this.size];
        this.m = m;
    }
    
    
    protected int translateAddress() {
        int pageTableEntryAddr = m.ptbr.getContent() << 9;
        pageTableEntryAddr |= (address & 0xFF00) >> 7;
        
        int pageTableEntry = 0;
        pageTableEntry |= (getByte(pageTableEntryAddr) & 0xFF);
        pageTableEntry |= (getByte(pageTableEntryAddr + 1) & 0xFF) << 8;
        
        // Not allocated
        if ((pageTableEntry & 0x1000) != 0x1000) {
            m.intr.setPF();
            return -1;
        }
        
        /// TODO: if read only
        
        
        int physicalAddress = ((pageTableEntry << 8) | (this.address & 0xFF)) & 0xFFFFF;
        
        return physicalAddress;
                
    }
    

    public void setAddress(int addr) {
        this.address = addr;
    }
        
    public void writeByteV(byte b) {
        int addr = translateAddress();
        
        if (m.intr.isInterrupt()) {
            return;
        }
        
        
        this.content[addr] = b;
    }
    
    public byte readByteV() {
        int addr = translateAddress();
        
        if (m.intr.isInterrupt()) {
            return -1;
        }
        return this.content[addr];
    }
    
    
    public void writeByteP(int addr, byte v) {
        if (addr >= content.length) {
            return;
        }
        
        content[addr] = v;
    }
    
    public byte readByteP(int addr) {
        if (addr < content.length) {
            return content[addr];
        }
        return 0;
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
    
    public void loadInterruptHandler(byte[] content) {
        setBytes(0, content);
    }
    
    public void initializeVirtualMemory(byte[] content) {
        int pageCount = Math.ceilDiv(content.length, 256);
        int pageTableOffset = 512;
        int pageOffset = 1024;
        System.out.println(content.length);
        for (int i = 0; i < 256; i++) {
            System.out.println("a");
            if (i < pageCount) {
                int pageAddr = pageOffset;
                int pte = pageAddr >> 8;
                pte |= 0x1000;
                setByte(i*2 + pageTableOffset, (byte)(pte & 0xFF));
                setByte(i*2 + 1 + pageTableOffset, (byte)((pte & 0xFF00) >> 8));
                pageOffset += 256;
            } else {
                setByte(i*2 + pageTableOffset, (byte)0);
                setByte(i*2 + 1 + pageTableOffset, (byte)0);
            }
            
        }
        
        setBytes(1024, content);
    }
    
    
}
