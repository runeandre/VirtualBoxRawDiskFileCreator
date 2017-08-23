/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vbox_rawdisk_link;

import javax.swing.JTextArea;

/**
 *
 * @author urbanus
 */
public class Logger {
    
    private JTextArea jTextAreaLogg = null;
    
    public Logger(JTextArea jTextAreaLogg){
        this.jTextAreaLogg = jTextAreaLogg;
    }
    
    public void logg(String text){
        jTextAreaLogg.append(System.lineSeparator() + text);
    }
    
}
