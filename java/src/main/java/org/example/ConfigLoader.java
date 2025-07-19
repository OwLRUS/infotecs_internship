package org.example;

import java.io.File;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class ConfigLoader {
    private String host;
    private String port;
    private String user;
    private String password;
    private String localDir;
    private String filePath;

    public String getParamById(int ID) throws Exception
    {
        switch (ID){
            case MenuKeys.HOST:
                return host;
            case MenuKeys.PORT:
                return port;
            case MenuKeys.USER:
                return user;
            case MenuKeys.PASSWORD:
                return password;
            case MenuKeys.LOCAL_DIR:
                return localDir;
            case MenuKeys.FILE_PATH:
                return filePath;
            default:
                throw new RuntimeException("There is no parameter with same ID");
        }
    }

    public void load() throws Exception {
        Properties props = new Properties();

        File jarDir = new File(ClientSFTP.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()).getParentFile();

        File configFile = new File(jarDir, "resources/config.properties");

        try (InputStream in = new FileInputStream(configFile)) {
            props.load(in);
        }

        host = props.getProperty("sftp.host");
        port = props.getProperty("sftp.port");
        user = props.getProperty("sftp.user");
        password = props.getProperty("sftp.password");
        filePath = props.getProperty("path.filePath");

        localDir = new File(jarDir, "resources").getAbsolutePath();
    }

    public void changeByID(int ID, String value)
    {
        switch (ID){
            case MenuKeys.HOST:
                host = value;
                break;
            case MenuKeys.PORT:
                port = value;
                break;
            case MenuKeys.USER:
                user = value;
                break;
            case MenuKeys.PASSWORD:
                password = value;
                break;
            case MenuKeys.LOCAL_DIR:
                localDir = value;
                break;
            case MenuKeys.FILE_PATH:
                filePath = value;
                break;
        }
    }

    public void save() throws  Exception{
        Properties props = new Properties();

        File jarDir = new File(ClientSFTP.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()).getParentFile();

        props.setProperty("sftp.host", host);
        props.setProperty("sftp.port", port);
        props.setProperty("sftp.user", user);
        props.setProperty("sftp.password", password);
        props.setProperty("path.filePath", filePath);

        try (FileOutputStream out = new FileOutputStream(jarDir + File.separator +"resources/config.properties")) {
            props.store(out, "App Configuration");
        }
    }
}
