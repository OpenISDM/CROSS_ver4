/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imrserver;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Bruce's Lab Computer
 */
public class sensorInWebTIBBO extends JPanel {
    public String URL = new String(); 
    final JWebBrowser webBrowser = new JWebBrowser();
    
    public void setURL(String url){
        this.URL = url;
    }
    
    public void reload(){
//        webBrowser.reloadPage();
//        webBrowser.setHTMLContent(URL);
        webBrowser.navigate(URL);
    }
    
    public sensorInWebTIBBO(String inputURL){
        super(new BorderLayout());
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        webBrowserPanel.setBorder(BorderFactory.createTitledBorder("Native Web Browser component"));

//        URL = "http://140.125.45.107/";
//        URL = "http://140.125.45.107/";
        webBrowser.navigate(inputURL);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);
        // Create an additional bar allowing to show/hide the menu bar of the web browser.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
        JCheckBox menuBarCheckBox = new JCheckBox("Menu Bar", webBrowser.isMenuBarVisible());
        webBrowser.setBarsVisible(false);
        menuBarCheckBox.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            webBrowser.setMenuBarVisible(e.getStateChange() == ItemEvent.SELECTED);
          }
        });
        webBrowser.setMenuBarVisible(false);
        buttonPanel.add(menuBarCheckBox);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /* Standard main method to try that test as a standalone application. */
  public void main(String[] args) {
    UIUtils.setPreferredLookAndFeel();
    NativeInterface.open();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JFrame frame = new JFrame("Web browser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new SimpleWebBrowserExample(), BorderLayout.CENTER);
        frame.setSize(30, 30);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
      }
    });
    NativeInterface.runEventPump();
  }
}
