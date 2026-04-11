/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package asmedit;

import asmedit.machine.Machine;
import asmedit.machine.Register;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author koukola
 */
public class AsmEdit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File file = new File("/Users/koukola/Documents/asm/resources/res");
        byte[] fileContent = new byte[(int) file.length()];

        try (FileInputStream fis = new FileInputStream(file)) {
            int bytesRead = fis.read(fileContent);
            System.out.println("Read " + bytesRead + " bytes.");
        } catch (IOException e) {
            // e.printStackTrace();
        }
        
        Machine m = new Machine();
        m.reset();
        m.getMemory().setContent(fileContent);
        
        Scanner s = new Scanner(System.in);
        
        
        while (true) {
            m.next();
            int n = 0;
            for (Register r: m.getRegisters()) {
                System.out.println("r" + n + ": " + r.getContent());
                n+=1;
            }
            System.out.print("Z: " + m.getPsr().getZ());
            System.out.print("     N: " + m.getPsr().getN());
            System.out.print("     C: " + m.getPsr().getC());
            System.out.println("     V: " + m.getPsr().getV());
            s.nextLine();
            
        }
        
    }
    
}
