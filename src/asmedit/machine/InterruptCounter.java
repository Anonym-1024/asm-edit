/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.machine;

/**
 *
 * @author koukola
 */
public class InterruptCounter {
    protected int content;

    public InterruptCounter() {
        this.content = 0;
    }

    public int getContent() {
        return content & 0xFF;
    }

    public void setContent(int content) {
        this.content = content & 0xFF;
    }
    
    
    public void increment() {
        System.out.println("in");
        this.content += 1;
    }
    
    public void reset() {
        this.content = 0;
    }
    
    
}
