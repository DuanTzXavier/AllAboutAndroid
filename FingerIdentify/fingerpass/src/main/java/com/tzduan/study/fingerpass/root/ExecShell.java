package com.tzduan.study.fingerpass.root;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by tzduan on 17/10/31.
 */

public class ExecShell {

    public enum SHELL_CMD {
        check_su_binary(new String[] {"/system/xbin/which","su"});

        String[] command;

        SHELL_CMD(String[] command){
            this.command = command;
        }
    }

    public ArrayList<String> executeCommand(SHELL_CMD shellCmd){
        String line;
        ArrayList<String> fullResponse = new ArrayList<>();
        Process localProcess;

        try {
            localProcess = Runtime.getRuntime().exec(shellCmd.command);
        } catch (Exception e) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));

        try {
            while ((line = in.readLine()) != null) {
                fullResponse.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fullResponse;
    }
}
