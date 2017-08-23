/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vbox_rawdisk_link;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author urbanus
 */
public class ListDisks extends AbstractListModel{

    private CommandLinux cmds;
    private ArrayList<String> list = new ArrayList<String>();
    private Logger logger;
    
    public ListDisks(Logger logger) {
        this.logger = logger;
        cmds = new CommandLinux(logger);
        
        list = cmds.cmdListDisks();
    }

    
    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Object getElementAt(int i) {
        return list.get(i);
    }
    
}
