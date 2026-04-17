/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.utils;

/**
 *
 * @author koukola
 */
public class TranslationTableGenerator {
    
    public static byte[] generate(byte[] content, int offset) {
        byte[] result = new byte[content.length + 512];
        
        int pageCount = Math.ceilDiv(content.length, 256);
        
        int pageOffset = 512;
        System.out.println(content.length);
        for (int i = 0; i < 256; i++) {
            
            if (i < pageCount) {
                int pageAddr = pageOffset + offset;
                int pte = pageAddr >> 8;
                pte |= 0x1000;
                result[i*2] = (byte)(pte & 0xFF);
                result[i*2 + 1] = (byte)((pte & 0xFF00) >> 8);
                pageOffset += 256;
            } else {
                result[i*2] = (byte)0;
                result[i*2 + 1] = (byte)0;
            }
            
        }
        
        for (int i = 0; i < content.length; i++) {
            
            result[512 + i] = content[i];
                    
        }
        
        return result;
        
    }
}
