/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

import java.util.function.BiConsumer;

/**
 *
 * @author koukola
 */
public class ControlUnit {
    
    protected Machine m;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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
                break;
            case 4:
                m.ptbr.setByte0(src);
                break;
            case 5:
                m.ptbr.setByte1(src);
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
        m.memory.setAddress(addr);
        int read = m.memory.readByteV();
        
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
        
        int src = m.registers[i.getArg1()].getContent();
        m.memory.setAddress(addr);
        m.memory.writeByteV((byte)src);
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
        
        m.pc.setContent(addr);
        
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
        
        link.setContent(m.pc.getContent());
        m.pc.setContent(addr);
    }

    // --- Port / System I/O ---
    public void ptr(Instruction i) {
    }

    public void ptw(Instruction i) {
    }

    public void ptsr(Instruction i) {
    }

    // --- System / Exit ---
    public void svc(Instruction i) {
        m.intr.setSVC();
    }

    public void exit(Instruction i) {
        m.stop();
        
    }
    
    
    
}