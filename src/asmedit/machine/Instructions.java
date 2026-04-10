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
public class Instructions {
    public static void mov(Instruction i, Machine m) {
        Register dst = m.registers[i.getArg1()];
        if (i.isI()) {
            int imm = i.getByte3();
            dst.setContent(imm);
        } else {
            int src = m.registers[i.getArg2()].getContent();
            dst.setContent(src);
        }
    }

    public static void mova(Instruction i, Machine m) {
        Register dst1 = m.registers[i.getArg1()];
        Register dst2 = m.registers[(i.getArg1() + 1) % 16];
        
        if (i.isI()) {
            dst1.setContent(i.getByte2());
            dst2.setContent(i.getByte3());
        } else {
            int src1 = m.registers[i.getArg2()].getContent();
            int src2 = m.registers[(i.getArg2() + 1) % 16].getContent();
            
            dst1.setContent(src1);
            dst2.setContent(src2);
        }
        
    }

    public static void movs(Instruction i, Machine m) {
        
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

    public static void mvn(Instruction i, Machine m) {
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

    public static void mvns(Instruction i, Machine m) {
        
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

    public static void srw(Instruction i, Machine m) {
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
            
        }
    }

    public static void srr(Instruction i, Machine m) {
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
            
        }
        
        m.registers[i.getArg1()].setContent(src);
        
        
    }

    // --- Memory Access ---
    public static void ldr(Instruction i, Machine m) {
        int addr = 0;
        if (i.isI()) {
            addr |= i.getByte2();
            addr |= i.getByte3() << 8;
        } else {
            addr |= m.registers[i.getArg2()].getContent();
            addr |= m.registers[(i.getArg2() + 1) % 16].getContent() << 8;
        }
        
        Register dst = m.registers[i.getArg1()];
        dst.setContent(m.memory.getByte(addr));
    }

    public static void str(Instruction i, Machine m) {
        int addr = 0;
        if (i.isI()) {
            addr |= i.getByte2();
            addr |= i.getByte3() << 8;
        } else {
            addr |= m.registers[i.getArg2()].getContent();
            addr |= m.registers[(i.getArg2() + 1) % 16].getContent() << 8;
        }
        
        int src = m.registers[i.getArg1()].getContent();
        m.memory.setByte(addr, (byte)src);
    }

    // --- Arithmetic ---
    public static void add(Instruction i, Machine m) {
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

    public static void adds(Instruction i, Machine m) {
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

    public static void addc(Instruction i, Machine m) {
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

    public static void addcs(Instruction i, Machine m) {
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

    public static void sub(Instruction i, Machine m) {
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

    public static void subs(Instruction i, Machine m) {
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

    public static void subc(Instruction i, Machine m) {
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

    public static void subcs(Instruction i, Machine m) {
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
    public static void and(Instruction i, Machine m) {
        
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

    public static void ands(Instruction i, Machine m) {
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

    public static void or(Instruction i, Machine m) {
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

    public static void ors(Instruction i, Machine m) {
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

    public static void eor(Instruction i, Machine m) {
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

    public static void eors(Instruction i, Machine m) {
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
    public static void lsl(Instruction i, Machine m) {
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

    public static void lsls(Instruction i, Machine m) {
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

    public static void lsr(Instruction i, Machine m) {
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

    public static void lsrs(Instruction i, Machine m) {
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

    public static void asr(Instruction i, Machine m) {
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

    public static void asrs(Instruction i, Machine m) {
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

    public static void csl(Instruction i, Machine m) {
    }

    public static void csls(Instruction i, Machine m) {
    }

    public static void csr(Instruction i, Machine m) {
        
    }

    public static void csrs(Instruction i, Machine m) {
    }

    // --- Compare and Discard/Update Flags ---
    public static void cmn(Instruction i, Machine m) {
        
        
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.adds(src1, src2, m.psr);
        
        
    }

    public static void addcd(Instruction i, Machine m) {
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.addcs(src1, src2, m.psr);
    }

    public static void cmp(Instruction i, Machine m) {
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.subs(src1, src2, m.psr);
    }

    public static void subcd(Instruction i, Machine m) {
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.subcs(src1, src2, m.psr);
    }

    public static void andd(Instruction i, Machine m) {
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.ands(src1, src2, m.psr);
    }

    public static void ord(Instruction i, Machine m) {
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.ors(src1, src2, m.psr);
    }

    public static void eord(Instruction i, Machine m) {
        int src1 = m.registers[i.getArg2()].getContent();
        int src2;
        
        if (i.isI()) {
            src2 = i.getByte3();
        } else {
            src2 = m.registers[i.getArg3()].getContent();
        }
        
        int res = ALU.eors(src1, src2, m.psr);
    }

    public static void lsld(Instruction i, Machine m) {
        
        
        
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.lsls(src, m.psr);
        
        
    }

    public static void lsrd(Instruction i, Machine m) {
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.lsrs(src, m.psr);
    }

    public static void asrd(Instruction i, Machine m) {
        int src;
        
        if (i.isI()) {
            src = i.getByte3();
        } else {
            src = m.registers[i.getArg2()].getContent();
        }
        
        int res = ALU.asrs(src, m.psr);
        
    }

    public static void csld(Instruction i, Machine m) {
    }

    public static void csrd(Instruction i, Machine m) {
    }

    // --- Branching ---
    public static void br(Instruction i, Machine m) {
        int addr = 0;
        if (i.isI()) {
            addr |= i.getByte2();
            addr |= i.getByte3() << 8;
        } else {
            addr |= m.registers[i.getArg1()].getContent();
            addr |= m.registers[(i.getArg1() + 1) % 16].getContent() << 8;
        }
        
        m.pc.setContent(addr);
        
    }

    public static void brl(Instruction i, Machine m) {
        
        Register link = m.registers[i.getArg1()];
        
        int addr = 0;
        if (i.isI()) {
            addr |= i.getByte2();
            addr |= i.getByte3() << 8;
        } else {
            addr |= m.registers[i.getArg2()].getContent();
            addr |= m.registers[(i.getArg2() + 1) % 16].getContent() << 8;
        }
        
        link.setContent(m.pc.getContent());
        m.pc.setContent(addr);
    }

    // --- Port / System I/O ---
    public static void ptr(Instruction i, Machine m) {
    }

    public static void ptw(Instruction i, Machine m) {
    }

    public static void ptsr(Instruction i, Machine m) {
    }

    // --- System / Exit ---
    public static void svc(Instruction i, Machine m) {
    }

    public static void exit(Instruction i, Machine m) {
        m.stop();
    }
    
    
    


// 2. Create the array of method references in the exact requested order
public static final InstructionHandler[] INSTRUCTION_TABLE = new InstructionHandler[] {
        Instructions::mov,
        Instructions::mova,
        Instructions::movs,
        Instructions::mvn,
        Instructions::mvns,
        Instructions::srw,
        Instructions::srr,

        Instructions::ldr,
        Instructions::str,

        Instructions::add,
        Instructions::adds,
        Instructions::addc,
        Instructions::addcs,
        Instructions::sub,
        Instructions::subs,
        Instructions::subc,
        Instructions::subcs,

        Instructions::and,
        Instructions::ands,
        Instructions::or,
        Instructions::ors,
        Instructions::eor,
        Instructions::eors,

        Instructions::lsl,
        Instructions::lsls,
        Instructions::lsr,
        Instructions::lsrs,
        Instructions::asr,
        Instructions::asrs,

        Instructions::csl,
        Instructions::csls,
        Instructions::csr,
        Instructions::csrs,

        Instructions::cmn,
        Instructions::addcd,
        Instructions::cmp,
        Instructions::subcd,

        Instructions::andd,
        Instructions::ord,
        Instructions::eord,

        Instructions::lsld,
        Instructions::lsrd,
        Instructions::asrd,
        Instructions::csld,
        Instructions::csrd,

        Instructions::br,
        Instructions::brl,

        Instructions::ptr,
        Instructions::ptw,
        Instructions::ptsr,

        Instructions::svc,
        Instructions::exit
    };
    
}


@FunctionalInterface
interface InstructionHandler {
    void execute(Instruction i, Machine m);
}