/*
 * 
 */
package jdcapp.test_bed;

import javafx.concurrent.Task;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import jdcapp.file.FileManager;
import javafx.util.converter.BigDecimalStringConverter;

/**
 *
 * @author Noah
 */
public class TestExportCode {
    
    public static void main(String[] args){
        
        ThreadExampleDesign test = new ThreadExampleDesign();
        FileManager fileManager = new FileManager();
        String filePath = "/Users/mac/Desktop";
        
        int i = fileManager.exportCode(test.getData(), filePath);
        System.out.println(i);
    }
}
