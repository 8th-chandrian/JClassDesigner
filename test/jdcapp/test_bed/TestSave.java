/*
 * 
 */
package jdcapp.test_bed;

import java.io.File;
import java.io.IOException;
import jdcapp.file.FileManager;

/**
 *
 * @author Noah
 */
public class TestSave {
    
    public static void main(String[] args){
        
        ThreadExampleDesign toSave = new ThreadExampleDesign();
        
        try{
            File saveFile = new File("./work/DesignSaveTest.json");
            FileManager testFileManager = new FileManager();
            testFileManager.saveData(toSave.getData(), saveFile.getPath());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
}
