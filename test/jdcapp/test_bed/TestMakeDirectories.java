/*
 * 
 */
package jdcapp.test_bed;

import jdcapp.file.FileManager;

/**
 *
 * @author Noah
 */
public class TestMakeDirectories {
    
    public static void main(String[] args){
        
        ThreadExampleDesign test = new ThreadExampleDesign();
        FileManager fileManager = new FileManager();
        String filePath = "/Users/mac/Desktop";
        
        int i = fileManager.exportCode(test.getData(), filePath);
        System.out.println(i);
        
    }
}
