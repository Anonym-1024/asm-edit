/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

/**
 *
 * @author koukola
 */
public class ALU {
    public static int add(int a, int b) {
        return (a + b) & 0xFF;
    }
    
    public static int adds(int a, int b, ProcessStateRegister psr) {
        int res =  (a + b);
        int flags = 0;
        if ((res & 0xFF) == 0) {
            flags |= 0b1000;
        }
        if ((res & 0x80) == 0x80) {
            flags |= 0b0100;
        }
        if ((res & 0x100) == 0x100) {
            flags |= 0b0010;
        }
        if ((a & 0x80) == (b & 0x80) && (res & 0x80) != (a & 0x80)) {
            flags |= 0b0001;
        }
        
        psr.setAluFlags(flags);
        
        return res & 0xFF;
    }
    
    public static int addc(int a, int b, ProcessStateRegister psr) {
        return (a + b + psr.getC()) & 0xFF;
    }
    
    public static int addcs(int a, int b, ProcessStateRegister psr) {
        int res =  (a + b + psr.getC());
        int flags = 0;
        if ((res & 0xFF) == 0) {
            flags |= 0b1000;
        }
        if ((res & 0x80) == 0x80) {
            flags |= 0b0100;
        }
        if ((res & 0x100) == 0x100) {
            flags |= 0b0010;
        }
        if ((a & 0x80) == (b & 0x80) && (res & 0x80) != (a & 0x80)) {
            flags |= 0b0001;
        }
        
        psr.setAluFlags(flags);
        
        return res & 0xFF;
    }
    
    
    public static int sub(int a, int b) {
        b = -b;
        return (a + b) & 0xFF;
    }
    
    public static int subs(int a, int b, ProcessStateRegister psr) {
        b = -b;
        int res =  (a + b);
        int flags = 0;
        if ((res & 0xFF) == 0) {
            flags |= 0b1000;
        }
        if ((res & 0x80) == 0x80) {
            flags |= 0b0100;
        }
        if ((res & 0x100) == 0x100) {
            flags |= 0b0010;
        }
        if ((a & 0x80) == (b & 0x80) && (res & 0x80) != (a & 0x80)) {
            flags |= 0b0001;
        }
        
        psr.setAluFlags(flags);
        
        return res & 0xFF;
    }
    
    public static int subc(int a, int b, ProcessStateRegister psr) {
        b = -b;
        return (a + b + psr.getC()) & 0xFF;
    }
    
    public static int subcs(int a, int b, ProcessStateRegister psr) {
        b = -b;
        int res =  (a + b + psr.getC());
        int flags = 0;
        if ((res & 0xFF) == 0) {
            flags |= 0b1000;
        }
        if ((res & 0x80) == 0x80) {
            flags |= 0b0100;
        }
        if ((res & 0x100) == 0x100) {
            flags |= 0b0010;
        }
        if ((a & 0x80) == (b & 0x80) && (res & 0x80) != (a & 0x80)) {
            flags |= 0b0001;
        }
        
        psr.setAluFlags(flags);
        
        return res & 0xFF;
    }
    
    public static int and(int a, int b) {
        
        return (a & b) & 0xFF;
    }
    
    public static int ands(int a, int b, ProcessStateRegister psr) {
        
        int res =  (a & b);
        int flags = 0;
        if ((res & 0xFF) == 0) {
            flags |= 0b1000;
        }
        if ((res & 0x80) == 0x80) {
            flags |= 0b0100;
        }
        if ((res & 0x100) == 0x100) {
            flags |= 0b0010;
        }
        if ((a & 0x80) == (b & 0x80) && (res & 0x80) != (a & 0x80)) {
            flags |= 0b0001;
        }
        
        psr.setAluFlags(flags);
        
        return res & 0xFF;
    }
    
    public static int or(int a, int b) {
        
        return (a | b) & 0xFF;
    }
    
    public static int ors(int a, int b, ProcessStateRegister psr) {
        
        int res =  (a | b);
        int flags = 0;
        if ((res & 0xFF) == 0) {
            flags |= 0b1000;
        }
        if ((res & 0x80) == 0x80) {
            flags |= 0b0100;
        }
        if ((res & 0x100) == 0x100) {
            flags |= 0b0010;
        }
        if ((a & 0x80) == (b & 0x80) && (res & 0x80) != (a & 0x80)) {
            flags |= 0b0001;
        }
        
        psr.setAluFlags(flags);
        
        return res & 0xFF;
    }
    
    public static int eor(int a, int b) {
        
        return (a ^ b) & 0xFF;
    }
    
    public static int eors(int a, int b, ProcessStateRegister psr) {
        
        int res =  (a ^ b);
        int flags = 0;
        if ((res & 0xFF) == 0) {
            flags |= 0b1000;
        }
        if ((res & 0x80) == 0x80) {
            flags |= 0b0100;
        }
        if ((res & 0x100) == 0x100) {
            flags |= 0b0010;
        }
        if ((a & 0x80) == (b & 0x80) && (res & 0x80) != (a & 0x80)) {
            flags |= 0b0001;
        }
        
        psr.setAluFlags(flags);
        
        return res & 0xFF;
    }
    
    public static int lsl(int a) {
        
        return (a << 1) & 0xFF;
    }
    
    public static int lsls(int a, ProcessStateRegister psr) {
        
        int res =  (a << 1);
        int flags = 0;
        if ((res & 0xFF) == 0) {
            flags |= 0b1000;
        }
        if ((res & 0x80) == 0x80) {
            flags |= 0b0100;
        }
        if ((res & 0x100) == 0x100) {
            flags |= 0b0010;
        }
        
        
        psr.setAluFlags(flags);
        
        return res & 0xFF;
    }
    
    
    public static int lsr(int a) {
        
        return (a >> 1) & 0xFF;
    }
    
    public static int lsrs(int a, ProcessStateRegister psr) {
        int flags = 0;
        if ((a & 1) == 1) {
            flags |= 0b0010;
        }
        int res =  (a >> 1);
        
        if ((res & 0xFF) == 0) {
            flags |= 0b1000;
        }
        if ((res & 0x80) == 0x80) {
            flags |= 0b0100;
        }
        
        
        
        psr.setAluFlags(flags);
        
        return res & 0xFF;
    }
    
    
    public static int asr(int a) {
        
        int res =  (a >> 1);
        res |= (res & 0x4F) << 1;
        return res;
    }
    
    public static int asrs(int a, ProcessStateRegister psr) {
        int flags = 0;
        if ((a & 1) == 1) {
            flags |= 0b0010;
        }
        int res =  (a >> 1);
        res |= (res & 0x4F) << 1;
        if ((res & 0xFF) == 0) {
            flags |= 0b1000;
        }
        if ((res & 0x80) == 0x80) {
            flags |= 0b0100;
        }
        
        
        
        psr.setAluFlags(flags);
        
        return res & 0xFF;
    }
    
}
