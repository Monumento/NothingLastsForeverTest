/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SettingsGUI;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mygame.Main;

/**
 *
 * @author Jonas
 */
public class SettingsGUI {
        boolean done;
        public JFrame frame;
        public JPanel panel;
        
        public JButton button1;
        public JButton button2;
        public JButton button3;
        public JButton button4;
        
        public void inputSettings(){
         done = false;
         frame = new JFrame();
         panel = new JPanel();
        
         button1  = new JButton("Start Local Server");
         button2  = new JButton("Start Local Client");
         button3  = new JButton("Start Online Server");
         button4  = new JButton("Start Online Client");
            
        frame.setVisible(true);

        button1.setBounds( 100, 100, 100, 100 );
        button2.setBounds( 200, 200, 100, 100 );
        button3.setBounds( 300, 300, 100, 100 );
        button4.setBounds( 400, 400, 100, 100 );
        
        button1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
              Main.isServer = 1;
              done = true;
              System.out.println(1);
           
            }
        });
       
        button2.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
              Main.isServer = 2;
              done = true;
            }
        });
       
        button3.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
              Main.isServer = 3;
              done = true;
            }
        });
        button4.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                 
              Main.isServer = 4;
             done = true;
            }
        });

        frame.setSize(500, 500);
 
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        
        frame.add(panel);
        frame.setLocation(500, 500);
        frame.pack();
      
        
        while(!done){
            System.out.print("s");
        }
            frame.dispose();
        }
}
