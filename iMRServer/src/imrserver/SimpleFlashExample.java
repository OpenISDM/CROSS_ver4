/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imrserver;

import java.awt.BorderLayout; 
 
import javax.swing.JFrame; 
import javax.swing.JPanel; 
import javax.swing.SwingUtilities; 
 
import chrriis.dj.nativeswing.swtimpl.NativeInterface; 
import chrriis.dj.nativeswing.swtimpl.components.JFlashPlayer; 
 
public class SimpleFlashExample extends JPanel { 
 
  public SimpleFlashExample() { 
    super(new BorderLayout(0, 0)); 
    JFlashPlayer flashPlayer = new JFlashPlayer(null); 
    flashPlayer.load("http://www.youtube.com/v/ZUXOLy0OEg0&autoplay=1"); 
    add(flashPlayer); 
  } 
  public static void main(String[] args) {  
    NativeInterface.open(); 
    SwingUtilities.invokeLater(new Runnable() { 
      public void run() { 
        JFrame frame = new JFrame("DJ Native Swing Test"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.getContentPane().add(new SimpleFlashExample(), BorderLayout.CENTER); 
        frame.setSize(500, 440); 
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true); 
      } 
    }); 
  }  
}
