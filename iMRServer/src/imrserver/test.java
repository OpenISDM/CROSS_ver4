/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imrserver;

/**
 *
 * @author Bruce's Lab Computer
 */
import java.io.*;

public class test {
  public static void main(String[] args) {
    Runtime runtime = Runtime.getRuntime();
    Process process = null;
    try {
//      process = runtime.exec("C:/Program Files/Windows Media Player/wmplayer.exe c:/Give.mp3");
        process = runtime.exec("C:/Program Files/Microsoft office/office14/EXCEL.exe");
    }
    catch (IOException e) {
      System.out.println(e);
      runtime.exit(0);
    }
  }//end main method
}//end class