/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license=default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

/*
 *
 * @author koukola
 */

/*
|=====|=====|======|=====|=====|=====|=====|=====|
|     PL    | IRQM |#####|  Z  |  N  |  C  |  V  |
|=====|=====|======|=====|=====|=====|=====|=====|
|  7  |  6  |  5   |  4  |  3  |  2  |  1  |  0  |
|=====|=====|======|=====|=====|=====|=====|=====|
*/

public class ProcessStateRegister {
    int content;

    public ProcessStateRegister() {
        this.content = 0;
    }
    
    
    
    public void setContent(int content) {
        this.content = content;
        
    }
    
    public void setAluFlags(int content) {
        this.content &= 0xF0;
        this.content |= (content & 0x0F);
    }
    
    public int getAluFlags() {
        
        return this.content & 0x0F;
    }
    
    public int getContent() {
        return this.content;
    }
    
    public int getZ() {
        return (this.content & (1 << 3)) >> 3;
    }
    
    public int getN() {
        return (this.content & (1 << 2)) >> 2;
    }
    
    public int getC() {
        return (this.content & (1 << 1)) >> 1;
    }
    
    public int getV() {
        return (this.content & (1));
    }
    
    public int getPL() {
        return (this.content & (0b11 << 6)) >> 6;
    }
    
    public int getIRQM() {
        return (this.content & (1 << 5)) >> 5;
    }
    
    public void reset() {
        this.content = 0;
    }
    
    
}
