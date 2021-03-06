/*
 * 
 */
package jdcapp.test_bed;

import java.io.IOException;
import java.util.ArrayList;
import jdcapp.JDCApp;
import jdcapp.data.CustomBox;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.CustomMethod;
import jdcapp.data.CustomVar;
import jdcapp.data.DataManager;
import jdcapp.file.FileManager;

/**
 *
 * @author Noah
 */
public class TestLoad {
    
    public static void main(String[] args){
        
        DataManager dataManager = new DataManager(new JDCApp());
        FileManager fileManager = new FileManager();
        
        //NOTE: Change this to ThreadExampleDesignAbstract or ThreadExampleDesignInterface to test other designs
        ThreadExampleDesign toLoad = new ThreadExampleDesign();
        
        try{
            fileManager.loadData(dataManager, toLoad.getFilePath() + "json");
        } catch (IOException e){
            e.printStackTrace();
        }
        
        ArrayList<CustomBox> testClasses = dataManager.getClasses();
        for(int i = 0; i < testClasses.size(); i++){
            if(testClasses.get(i) instanceof CustomClassWrapper){
                CustomClassWrapper testClassWrapper = (CustomClassWrapper) testClasses.get(i);
                
                System.out.println("#######################################################################");
                System.out.println("Class name: " + testClassWrapper.getData().getClassName() + "\n");
                System.out.println("\tPackage name: " + testClassWrapper.getData().getPackageName());
                System.out.println("\tIs interface: " + testClassWrapper.getData().isInterface());
                System.out.println("\tIs abstract: " + testClassWrapper.getData().isAbstract() + "\n");

                System.out.println("\tStarting x value: " + testClassWrapper.getStartX());
                System.out.println("\tStarting y value: " + testClassWrapper.getStartY());
                System.out.println("\tWidth: " + testClassWrapper.getWidth());
                System.out.println("\tHeight: " + testClassWrapper.getHeight());

                System.out.println("\tExtended Class: " + testClassWrapper.getData().getExtendedClass());
                System.out.println("\tImplemented Classes: ");
                ArrayList<String> testImplementedClasses = testClassWrapper.getData().getImplementedClasses();
                for(int j = 0; j < testImplementedClasses.size(); j++){
                    System.out.println("\t\t" + testImplementedClasses.get(j));
                }

                System.out.println("\n\tVariables: ");
                ArrayList<CustomVar> testVariables = testClassWrapper.getData().getVariables();
                for(int j = 0; j < testVariables.size(); j++){
                    CustomVar testVar = testVariables.get(j);
                    System.out.println("\t\tVariable name: " + testVar.getVarName());
                    System.out.println("\t\tVariable type: " + testVar.getVarType());
                    System.out.println("\t\tIs static: " + testVar.isStatic());
                    System.out.println("\t\tAccess: " + testVar.getAccess() + "\n");
                }

                System.out.println("\tMethods: ");
                ArrayList<CustomMethod> testMethods = testClassWrapper.getData().getMethods();
                for(int j = 0; j < testMethods.size(); j++){
                    CustomMethod testMethod = testMethods.get(j);
                    System.out.println("\t\tMethod name: " + testMethod.getMethodName());
                    System.out.println("\t\tMethod return type: " + testMethod.getReturnType());
                    System.out.println("\t\tIs static: " + testMethod.isStatic());
                    System.out.println("\t\tIs abstract: " + testMethod.isAbstract());
                    System.out.println("\t\tAccess: " + testMethod.getAccess());

                    ArrayList<String> testArguments = testMethod.getArguments();
                    System.out.println("\t\tArguments: ");
                    for(int k = 0; k < testArguments.size(); k++){
                        System.out.println("\t\t\t" + testArguments.get(k));
                    }
                    System.out.println("");
                }
            }
            //TODO: INCLUDE CASE HERE FOR CUSTOM IMPORTS
        }
    }
}
