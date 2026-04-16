/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

/**
 *
 * @author koukola
 */

/*TODO:
|=====|=====|======|=====|=====|=====|=====|=====|
|     |     |      | IRQ | SVC | PF  | INI | INT |
|=====|=====|======|=====|=====|=====|=====|=====|
|  7  |  6  |  5   |  4  |  3  |  2  |  1  |  0  |
|=====|=====|======|=====|=====|=====|=====|=====|
*/

public class InterruptRegister {
    protected byte content;
    
    

    public InterruptRegister() {
        this.content = 0;
    }
    
    public void setINT() {
        this.content |= 0b00001;
        System.out.println("Interrupt set");
        
    }
    
    public void setINI() {
        this.content |= 0b00010;
        setINT();
    }
    
    public void setPF() {
        this.content |= 0b00100;
        setINT();
    }
    
    public void setIRQ() {
        this.content |= 0b10000;
    }
    
    public void setSVC() {
        this.content |= 0b01000;
        setINT();
    }
    
    public int getContent() {
        return (int)this.content;
    }
    
    public boolean isInterrupt() {
        return (this.content & 1) == 1;
    }
    
    public boolean isIRQ() {
        return (this.content & 0b10000) == 0b10000;
    }
    
    public void clear() {
        this.content = 0;
    }
    
    
    
    
    
    
}
