/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

/**
 *
 * @author koukola
 */

/* PTBR
|=====|=====|======|=====|=====|=====|=====|=====|
|     |     |      |     |     |     |     |     |
|=====|=====|======|=====|=====|=====|=====|=====|
|  7  |  6  |  5   |  4  |  3  |  2  |  1  |  0  |
|=====|=====|======|=====|=====|=====|=====|=====|
*/

/* PTER
|=====|=====|======|=====|========================================================================|
|     |     |      |  A  |                                                                        |
|=====|=====|======|=====|=====|=====|=====|=====|=====|=====|======|=====|=====|=====|=====|=====|
| 15  | 14  | 13   | 12  | 11  | 10  |  9  |  8  |  7  |  6  |  5   |  4  |  3  |  2  |  1  |  0  |
|=====|=====|======|=====|=====|=====|=====|=====|=====|=====|======|=====|=====|=====|=====|=====|
*/


public class PageTableBaseRegister {
    protected int content;

    public PageTableBaseRegister() {
        this.content = 1;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }
    
    public void increment() {
        this.content += 1;
    }
    
    public void setByte0(int byte0) {
        this.content &= 0xFF00;
        this.content |= (content & 0x00FF);
    }
    
    public void setByte1(int byte1) {
        this.content &= 0x00FF;
        this.content |= (content & 0xFF00);
    }
    
    public int getByte0() {
        return this.content & 0x00FF;
    }
    public int getByte1() {
        return (this.content & 0xFF00) >> 8;
    }
    
    
    public void reset() {
        this.content = 1;
    }
    
}
