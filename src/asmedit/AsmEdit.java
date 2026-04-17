/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package asmedit;

import asmedit.machine.Machine;
import asmedit.machine.MachineConfig;
import asmedit.machine.Register;
import asmedit.utils.TranslationTableGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.print.attribute.standard.PrinterState;

/**
 *
 * @author koukola
 */
public class AsmEdit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        
        
        
        File file = new File("/Users/koukola/Documents/asm/resources/example");
        byte[] fileContent = new byte[(int) file.length()];

        try (FileInputStream fis = new FileInputStream(file)) {
            int bytesRead = fis.read(fileContent);
            System.out.println("Read " + bytesRead + " bytes.");
        } catch (IOException e) {
            // e.printStackTrace();
        }
        
        
        File file2 = new File("/Users/koukola/Documents/asm/resources/isr");
        byte[] fileContent2 = new byte[(int) file2.length()];

        try (FileInputStream fis = new FileInputStream(file2)) {
            int bytesRead = fis.read(fileContent2);
            System.out.println("Read " + bytesRead + " bytes.");
        } catch (IOException e) {
            // e.printStackTrace();
        }
        
        Machine m = new Machine();
        
        m.getConfig().setDefaultMemory(fileContent2);
        
        Scanner s = new Scanner(System.in);
        
        m.startAndBoot();
        m.getMemory().setBytes(512, TranslationTableGenerator.generate(fileContent, 512));
        
        for (int i = 0; i < 10000; i++) {
            System.out.println(i + ": " + m.getMemory().getByte(i));
        }
        
        while (m.getState() != Machine.State.STOPPED) {
            m.nextCycle();
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
