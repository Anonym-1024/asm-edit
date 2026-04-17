/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

import java.util.function.BiConsumer;
import javax.sound.midi.Instrument;

/**
 *
 * @author koukola
 */
public class ControlUnit {
    
    protected Machine m;

    public ControlUnit(Machine m) {
        this.m = m;
    }
    
    
    
    
    
    
    
    
    
    public void cycle() {
        
        if (m.psr.getIRQM() == 1 && m.intr.isIRQ()) {
            m.intr.setINT();
        } 
        
        Instruction i = fetch();
        
        if (i == null) {
            return;
        }
        
        if (isCondValid(i.getCond())) {
            execute(i);
        }
        
        
        
    }
    
    
    protected Instruction fetch() {
        int base_addr;
        
        if (m.intr.isInterrupt()) {
            base_addr = m.intpc.getContent();
        } else {
            
            base_addr = m.pc.getContent();
        }
        
        int addr = base_addr;
        
        if (m.psr.getState() != 0 && !m.intr.isInterrupt()) {
            addr = translateAddress(addr);
            if (m.intr.isInterrupt() == true) {
                return null;
            }
        }
     
        byte byte0 = m.memory.getByte(addr);
        
        addr = base_addr + 1;
        if (m.psr.getState() != 0 && !m.intr.isInterrupt()) {
            addr = translateAddress(addr);
            if (m.intr.isInterrupt() == true) {
                return null;
            }
        }
        byte byte1 = m.memory.getByte(addr);
        
        addr = base_addr + 2;
        if (m.psr.getState() != 0 && !m.intr.isInterrupt()) {
            addr = translateAddress(addr);
            if (m.intr.isInterrupt() == true) {
                return null;
            }
        }
        byte byte2 = m.memory.getByte(addr);
        
        addr = base_addr + 3;
        if (m.psr.getState() != 0 && !m.intr.isInterrupt()) {
            addr = translateAddress(addr);
            if (m.intr.isInterrupt() == true) {
                return null;
            }
        }
        byte byte3 = m.memory.getByte(addr);
        
        
        if (m.intr.isInterrupt()) {
            m.intpc.setContent(base_addr + 4);
        } else {
            m.pc.setContent(base_addr + 4);
        }
        
        
        
        
        return new Instruction(byte0, byte1, byte2, byte3);
    }
    
    protected void execute(Instruction i) {
        System.out.println(i.getOpcode());
        switch (i.getOpcode()) {
        case 0:  mov(i);    break;
        case 1:  mova(i);   break;
        case 2:  movs(i);   break;
        case 3:  mvn(i);    break;
        case 4:  mvns(i);   break;
        case 5:  srw(i);    break;
        case 6:  srr(i);    break;
        case 7:  ldr(i);    break;
        case 8:  str(i);    break;
        case 9:  add(i);    break;
        case 10: adds(i);   break;
        case 11: addc(i);   break;
        case 12: addcs(i);  break;
        case 13: sub(i);    break;
        case 14: subs(i);   break;
        case 15: subc(i);   break;
        case 16: subcs(i);  break;
        case 17: and(i);    break;
        case 18: ands(i);   break;
        case 19: or(i);     break;
        case 20: ors(i);    break;
        case 21: eor(i);    break;
        case 22: eors(i);   break;
        case 23: lsl(i);    break;
        case 24: lsls(i);   break;
        case 25: lsr(i);    break;
        case 26: lsrs(i);   break;
        case 27: asr(i);    break;
        case 28: asrs(i);   break;
        case 29: csl(i);    break;
        case 30: csls(i);   break;
        case 31: csr(i);    break;
        case 32: csrs(i);   break;
        case 33: cmn(i);    break;
        case 34: addcd(i);  break;
        case 35: cmp(i);    break;
        case 36: subcd(i);  break;
        case 37: andd(i);   break;
        case 38: ord(i);    break;
        case 39: eord(i);   break;
        case 40: lsld(i);   break;
        case 41: lsrd(i);   break;
        case 42: asrd(i);   break;
        case 43: csld(i);   break;
        case 44: csrd(i);   break;
        case 45: br(i);     break;
        case 46: brl(i);    break;
        case 47: ptr(i);    break;
        case 48: ptw(i);    break;
        case 49: ptsr(i);   break;
        case 50: svc(i);    break;
        case 51: exit(i);   break;
        default:
            m.intr.setINI();
            
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
     protected boolean isCondValid(int cond) {
        switch (cond) {
            case 0:
                return true;
            case 1:
                return m.psr.getZ() == 1;
                
            case 2:
                return m.psr.getN() == 1;
                
            case 3:
                return m.psr.getV() == 1;
                
            case 4:
                return m.psr.getC() == 0;
                
            case 5:
                return m.psr.getC() == 1 && m.psr.getZ() == 0;
                
            case 6:
                return m.psr.getV() != m.psr.getN();
                
            case 7:
                return m.psr.getV() == m.psr.getN() && m.psr.getZ() == 0;
                
            case 9:
                return !(m.psr.getZ() == 1);
                
            case 10:
                return !(m.psr.getN() == 1);
                
            case 11:
                return !(m.psr.getV() == 1);
                
            case 12:
                return !(m.psr.getC() == 0);
                
            case 13:
                return !(m.psr.getC() == 1 && m.psr.getZ() == 0);
                
            case 14:
                return !(m.psr.getV() != m.psr.getN());
                
            case 15:
                return !(m.psr.getV() == m.psr.getN() && m.psr.getZ() == 0);
                
            default:
                return false;
        }
        
        
        
    }
    
    

    protected int translateAddress(int addr) {
        System.out.println("Addr: " + addr);
        System.out.println("PTBR: " + m.ptbr.getContent() );
        int pageTableEntryAddr = m.ptbr.getContent() << 9;
        pageTableEntryAddr |= (addr & 0xFF00) >> 7;
        
        int pageTableEntry = 0;
        pageTableEntry |= (m.memory.getByte(pageTableEntryAddr) & 0xFF);
        pageTableEntry |= (m.memory.getByte(pageTableEntryAddr + 1) & 0xFF) << 8;
        
        // Not allocated
        if ((pageTableEntry & 0x1000) != 0x1000) {
            m.intr.setPF();
            return -1;
        }
        
        /// TODO: if read only
        
        
        int physicalAddress = ((pageTableEntry << 8) | (addr & 0xFF)) & 0xFFFFF;
        
        return physicalAddress;
    }
     
    
    
    
    public void mov(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        if (i.isI()) {
            int imm = i.getByte3();
            dst.setContent(imm);
        } else {
            int src = m.registers[i.getArg2()].getContent();
            dst.setContent(src);
        }
    }

    public void mova(Instruction i) {
        Register dst1 = m.registers[i.getArg1()];
        Register dst2 = m.registers[(i.getArg1() + 1) % 16];
        
        if (i.isI()) {
            dst1.setContent(i.getByte3());
            dst2.setContent(i.getByte2());
        } else {
            int src1 = m.registers[i.getArg2()].getContent();
            int src2 = m.registers[(i.getArg2() + 1) % 16].getContent();
            
            dst1.setContent(src1);
            dst2.setContent(src2);
        }
        
    }

    public void movs(Instruction i) {
        
        Register dst = m.registers[i.getArg1()];
        
        if (i.isI()) {
            int src = i.getByte3();
            int res = ALU.ors(src, 0, m.psr);
            dst.setContent(res);
        } else {
            int src = m.registers[i.getArg2()].getContent();
            int res = ALU.ors(src, 0, m.psr);
            dst.setContent(res);
        }
    }

    public void mvn(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        if (i.isI()) {
            int src = i.getByte3();
            int res = ALU.ors(src, 0, m.psr);
            dst.setContent(res);
        } else {
            int src = m.registers[i.getArg2()].getContent();
            int res = ALU.eor(src, 1);
            dst.setContent(res);
        }
    }

    public void mvns(Instruction i) {
        
        Register dst = m.registers[i.getArg1()];
        
        if (i.isI()) {
            int src = i.getByte3();
            int res = ALU.ors(src, 0, m.psr);
            dst.setContent(res);
        } else {
            int src = m.registers[i.getArg2()].getContent();
            int res = ALU.eors(src, 0, m.psr);
            dst.setContent(res);
        }
    }

    public void srw(Instruction i) {
        
        if (m.psr.getState() > 1) {
            // INTERRUPT INI????
            return;
        }
        
        int src;
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        switch (i.getArg1()) {
            case 0:
                m.pc.setByte0(src);
                break;
            case 1:
                m.pc.setByte1(src);
                break;
            case 2:
                m.psr.setContent(src);
                break;
            case 3:
                m.intr.clear();
                m.intpc.reset();
                break;
            case 4:
                m.ptbr.setByte0(src);
                System.out.println("PTBR SET: " + src);
                break;
            case 5:
                m.ptbr.setByte1(src);
                System.out.println("PTBR SET: " + src);
                break;
        }
    }

    public void srr(Instruction i) {
        int src = 0;
        switch (i.getArg2()) {
            case 0:
                src = m.pc.getByte0();
                break;
            case 1:
                src = m.pc.getByte1();
                break;
            case 2:
                src = m.psr.getContent();
                break;
            case 3:
                src = m.intr.getContent();
                break;
            case 4:
                 src = m.ptbr.getByte0();
                break;
            case 5:
                src = m.ptbr.getByte1();
                break;
        }
        
        m.registers[i.getArg1()].setContent(src);
        
        
    }

    
    
     

   
    
    
    // --- Memory Access ---
    private void ldr(Instruction i) {
        
        
        
        int addr = 0;
        if (i.isI()) {
            addr |= i.getByte3();
            addr |= i.getByte2() << 8;
        } else {
            addr |= m.registers[i.getArg2()].getContent();
            addr |= m.registers[(i.getArg2() + 1) % 16].getContent() << 8;
        }
        
        Register dst = m.registers[i.getArg1()];
        
        if (m.psr.getState() != 0 && !m.intr.isInterrupt()) {
            addr = translateAddress(addr);
            if (m.intr.isInterrupt() == true) {
                return;
            }
        }
        
        int read = m.memory.getByte(addr);
        dst.setContent(read);
    }

    public void str(Instruction i) {
        
        
        
        int addr = 0;
        if (i.isI()) {
            addr |= i.getByte3();
            addr |= i.getByte2() << 8;
        } else {
            addr |= m.registers[i.getArg2()].getContent();
            addr |= m.registers[(i.getArg2() + 1) % 16].getContent() << 8;
        }
        
        
        if (m.psr.getState() != 0 && !m.intr.isInterrupt()) {
            addr = translateAddress(addr);
            if (m.intr.isInterrupt()) {
                return;
            }
        }
        
        
        int src = m.registers[i.getArg1()].getContent();
        m.memory.setByte(addr, (byte)src);
    }

    // --- Arithmetic ---
    public void add(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.add(src1, src2);
        
        dst.setContent(res);
                
    }

    public void adds(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.adds(src1, src2, m.psr);
        
        dst.setContent(res);
    }

    public void addc(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.addc(src1, src2, m.psr);
        
        dst.setContent(res);
    }

    public void addcs(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.addcs(src1, src2, m.psr);
        
        dst.setContent(res);
    }

    public void sub(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.sub(src1, src2);
        
        dst.setContent(res);
    }

    public void subs(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.subs(src1, src2, m.psr);
        
        dst.setContent(res);
    }

    public void subc(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.subc(src1, src2, m.psr);
        
        dst.setContent(res);
    }

    public void subcs(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.subcs(src1, src2, m.psr);
        
        dst.setContent(res);
    }

    // --- Logical ---
    public void and(Instruction i) {
        
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.and(src1, src2);
        
        dst.setContent(res);
                
    

    }

    public void ands(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.ands(src1, src2, m.psr);
        
        dst.setContent(res);
    }

    public void or(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.or(src1, src2);
        
        dst.setContent(res);
    }

    public void ors(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.ors(src1, src2, m.psr);
        
        dst.setContent(res);
    }

    public void eor(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.eor(src1, src2);
        
        dst.setContent(res);
    }

    public void eors(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.eors(src1, src2, m.psr);
        
        dst.setContent(res);
    }

    // --- Shifts and Rotates ---
    public void lsl(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        System.out.println("shift: " + src);
        int res = ALU.lsl(src);
        
        dst.setContent(res);
    }

    public void lsls(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.lsls(src, m.psr);
        System.out.println("shift: " + src);
        
        dst.setContent(res);
    }

    public void lsr(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.lsr(src);
        
        dst.setContent(res);
    }

    public void lsrs(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.lsrs(src, m.psr);
        
        dst.setContent(res);
    }

    public void asr(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.asr(src);
        
        dst.setContent(res);
    }

    public void asrs(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.asrs(src, m.psr);
        
        dst.setContent(res);
    }

    public void csl(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.csl(src, m.psr);
        
        dst.setContent(res);
    }

    public void csls(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.csls(src, m.psr);
        
        dst.setContent(res);
    }

    public void csr(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.csr(src, m.psr);
        
        dst.setContent(res);
        
    }

    public void csrs(Instruction i) {
        Register dst = m.registers[i.getArg1()];
        
        
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.csrs(src, m.psr);
        
        dst.setContent(res);
    }

    // --- Compare and Discard/Update Flags ---
    public void cmn(Instruction i) {
        
        
        int src1 = m.registers[i.getArg1()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.adds(src1, src2, m.psr);
        
        
    }

    public void addcd(Instruction i) {
        int src1 = m.registers[i.getArg1()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.addcs(src1, src2, m.psr);
    }

    public void cmp(Instruction i) {
        int src1 = m.registers[i.getArg1()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.subs(src1, src2, m.psr);
    }

    public void subcd(Instruction i) {
        int src1 = m.registers[i.getArg1()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.subcs(src1, src2, m.psr);
    }

    public void andd(Instruction i) {
        int src1 = m.registers[i.getArg1()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.ands(src1, src2, m.psr);
    }

    public void ord(Instruction i) {
        int src1 = m.registers[i.getArg1()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.ors(src1, src2, m.psr);
    }

    public void eord(Instruction i) {
        int src1 = m.registers[i.getArg1()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.eors(src1, src2, m.psr);
    }

    public void lsld(Instruction i) {
        
        
        
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg1()].getContent();
        }
        
        int res = ALU.lsls(src, m.psr);
        
        
    }

    public void lsrd(Instruction i) {
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg1()].getContent();
        }
        
        int res = ALU.lsrs(src, m.psr);
    }

    public void asrd(Instruction i) {
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg1()].getContent();
        }
        
        int res = ALU.asrs(src, m.psr);
        
    }

    public void csld(Instruction i) {
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg1()].getContent();
        }
        
        int res = ALU.csls(src, m.psr);
    }

    public void csrd(Instruction i) {
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg1()].getContent();
        }
        
        int res = ALU.csrs(src, m.psr);
    }

    // --- Branching ---
    
    
    
    
    
    
    
    public void br(Instruction i) {
        
        
        
        int addr = 0;
        if (i.isI()) {
            addr |= i.getByte3();
            addr |= i.getByte2() << 8;
        } else {
            addr |= m.registers[i.getArg1()].getContent();
            addr |= m.registers[(i.getArg1() + 1) % 16].getContent() << 8;
        }
        
        if (m.intr.isInterrupt()) {
            m.intpc.setContent(addr);
        } else {
            m.pc.setContent(addr);
        }
        
        
    }

    public void brl(Instruction i) {
        
        
        
        Register link = m.registers[i.getArg1()];
        
        int addr = 0;
        if (i.isI()) {
            addr |= i.getByte3();
            addr |= i.getByte2() << 8;
        } else {
            addr |= m.registers[i.getArg2()].getContent();
            addr |= m.registers[(i.getArg2() + 1) % 16].getContent() << 8;
        }
        
        if (m.intr.isInterrupt()) {
            link.setContent(m.intpc.getContent());
            m.intpc.setContent(addr);
        } else {
            link.setContent(m.pc.getContent());
            m.pc.setContent(addr);
        }
    }

    // --- Port / System I/O ---
    public void ptr(Instruction i) {
        if (m.psr.getState() > 1) {
            // INTERRUPT INI????
            return;
        }
    }

    public void ptw(Instruction i) {
        if (m.psr.getState() > 1) {
            // INTERRUPT INI????
            return;
        }
    }

    public void ptsr(Instruction i) {
        if (m.psr.getState() > 1) {
            // INTERRUPT INI????
            return;
        }
    }

    // --- System / Exit ---
    public void svc(Instruction i) {
        m.intr.setSVC();
    }

    public void exit(Instruction i) {
        if (m.psr.getState() > 1) {
            // INTERRUPT INI????
            return;
        }
        m.stop();
        
    }
    
    
    
}