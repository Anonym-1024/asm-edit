/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package asmedit;

import asmedit.machine.Machine;
import asmedit.machine.Register;
import asmedit.ui.MainMenuWindow;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author koukola
 */
public class AsmEdit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File file = new File("/home/koukola/Documents/coding/asm/resources/res");
        byte[] fileContent = new byte[(int) file.length()];

        try (FileInputStream fis = new FileInputStream(file)) {
            int bytesRead = fis.read(fileContent);
            System.out.println("Read " + bytesRead + " bytes.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Machine m = new Machine();
        m.reset();
        m.loadMemory(fileContent);
        
        for (Register r: m.getRegisters()) {
            System.out.println(r.getContent());
        }
        
        
        for (Register r: m.getRegisters()) {
            System.out.println(r.getContent());
        }
    }
    
}
