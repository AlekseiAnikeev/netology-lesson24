package ru.agentche.grocerybasket;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 14.09.2022
 */
public class Config {
    // load config
    private boolean loadEnables = false;
    private String loadFileName;
    private String loadFormat;
    // save config
    private boolean saveEnables = false;
    private String saveFileName;
    private String saveFormat;
    //log config
    private boolean logEnables = false;
    private String logFileName;

    public void readConfig() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new File("shop.xml"));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        readLoadConfig(doc);
        readSaveConfig(doc);
        readLogConfig(doc);
    }

    private void readLogConfig(Document doc) {
        String usr = doc.getElementsByTagName("log").item(0).getTextContent().replace(" ", "").trim();
        String[] asd = usr.split("\n");
        if ("true".equals(asd[0])) {
            logEnables = true;
        }
        logFileName = asd[1];
    }

    private void readSaveConfig(Document doc) {
        String usr = doc.getElementsByTagName("save").item(0).getTextContent().replace(" ", "").trim();
        String[] asd = usr.split("\n");
        if ("true".equals(asd[0])) {
            saveEnables = true;
        }
        saveFileName = asd[1];
        saveFormat = asd[2];
    }

    private void readLoadConfig(Document doc) {
        String usr = doc.getElementsByTagName("load").item(0).getTextContent().replace(" ", "").trim();
        String[] asd = usr.split("\n");
        if ("true".equals(asd[0])) {
            loadEnables = true;
        }
        loadFileName = asd[1];
        loadFormat = asd[2];
    }

    public boolean isLoadEnables() {
        return loadEnables;
    }

    public String getLoadFileName() {
        return loadFileName;
    }

    public String getLoadFormat() {
        return loadFormat;
    }

    public boolean isSaveEnables() {
        return saveEnables;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public String getSaveFormat() {
        return saveFormat;
    }

    public boolean isLogEnables() {
        return logEnables;
    }

    public String getLogFileName() {
        return logFileName;
    }
}
