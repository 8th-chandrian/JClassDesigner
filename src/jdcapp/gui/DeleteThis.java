/*
 * 
 */
package jdcapp.gui;

/**
 *
 * @author Noah
 */
public class DeleteThis {
    public static void main(String[] args){
        Package[] packages = Package.getPackages();
        for(Package p : packages){
            System.out.println(p.getName());
        }
    }
}
