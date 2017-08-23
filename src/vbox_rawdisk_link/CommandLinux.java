/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vbox_rawdisk_link;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author urbanus
 */
public class CommandLinux {

    private Logger logger;
    
    public CommandLinux(Logger logger){
        this.logger = logger;
    }
    
    public ArrayList<String> cmdListDisks() {
        ArrayList<String> returnList = new ArrayList<String>();
        String s;
        Process p;
        try {
            String cmd = "lsblk -io KNAME,TYPE,SIZE,MODEL";
            p = Runtime.getRuntime().exec(cmd);
            //logger.logg(cmd);
            
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
                //logger.logg(s);
                
                int column = 1;
                String disk = "";
                String type = "";
                String size = "";
                String model = "";
                
                s = s.replaceAll("\\s+"," ");
                String currentValue = "";
                
                for (int i = 0; i < s.length(); i++) {
                    String character = String.valueOf(s.charAt(i));
                    
                    if(character.equals(" ")){
                        //System.out.println(column);
                        //System.out.println(currentValue);
                        column++;
                        if(column < 4){
                            currentValue = "";
                        }
                    }else{
                        currentValue = currentValue + character;
                    }
                    
                    switch (column) {
                        case 1:
                            disk = currentValue;
                            break;
                        case 2:
                            type = currentValue;
                            break;
                        case 3:
                            size = currentValue;
                            break;
                        default:
                            model = currentValue;
                            break;
                    }
                    
                }
                
                if(type.contains("disk") || type.contains("loop")){
                    returnList.add(disk + " (" + size + " / " + model + ")");
                }
            }
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
            logger.logg(e.getMessage());
            
            return new ArrayList<String>();
        }

        return returnList;
    }
    
    public String cmdGetCurrentFolder() {
        String s;
        Process p;
        try {
            String cmd = "pwd";
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
                return s;
            }
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
            logger.logg(e.getMessage());
        }
        
        return "";
    }

    public void cmdRunMultipleCmds(ArrayList<String> commands) {
        String commandsString = "'";
        for (String command : commands) {
            if(!commandsString.equals("'")){
                commandsString += " ; ";
            }
            commandsString += command;
        }
        commandsString += "'";
        
        String[] cmd = {"x-terminal-emulator", "-e", "sudo sh -c " + commandsString};
                
        String cmdPrint = "";
        for (String string : cmd) {
            cmdPrint += string + " ";
        }
        logger.logg(cmdPrint);
        
        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
                logger.logg(s);
            }
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
            logger.logg(e.getMessage());
        }
        
        logger.logg("Done!");
    }
    
    public String getVmdkFileCmd(String filename, String diskpath){
        return "VBoxManage internalcommands createrawvmdk -filename " + filename + " -rawdisk " + diskpath;
    }
    
    public String getCurrentUserAsOwnerCmd(String filename) {
        return "chown $USER:$USER " + filename;
        
    }
    
    public String getAddDiskGroupToUserCmd() {
        return "usermod -a -G disk $USER";
        
    }
    
}
