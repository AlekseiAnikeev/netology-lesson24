package ru.agentche.grocerybasket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 13.09.2022
 */
public class ClientLog {
    private final List<String> clientLog = new ArrayList<>();
    private boolean firstStart = true;

    public void log(int productNum, int amount) {
        if (firstStart) {
            clientLog.add("productNum,amount\n");
            clientLog.add(productNum + "," + amount + "\n");
            firstStart = !firstStart;
        } else {
            clientLog.add(productNum + "," + amount + "\n");
        }
    }

    public void exportAsCSV(File txtFile) {
        try (BufferedWriter buff = new BufferedWriter(new FileWriter(txtFile))) {
            for (String line : clientLog) {
                buff.write(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
