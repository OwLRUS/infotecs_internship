package org.example;

import java.util.Properties;
import java.io.InputStream;
import java.io.FileOutputStream;

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
            case(1):
                return host;
            case(2):
                return port;
            case(3):
                return user;
            case(4):
                return password;
            case(5):
                return localDir;
            case(6):
                return filePath;
            default:
                throw new RuntimeException("There is no parameter with same ID");
        }
    }

    public void load() throws Exception {
        Properties props = new Properties();

        try(InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if(in == null) {
                throw new RuntimeException("config.properties not found");
            }
            props.load(in);
        }

        host = props.getProperty("sftp.host");
        port = props.getProperty("sftp.port");
        user = props.getProperty("sftp.user");
        password = props.getProperty("sftp.password");
        localDir = props.getProperty("path.localDir");
        filePath = props.getProperty("path.filePath");
    }

    public void changeByID(int ID, String value)
    {
        switch (ID){
            case(1):
                host = value;
                break;
            case(2):
                port = value;
                break;
            case(3):
                user = value;
                break;
            case(4):
                password = value;
                break;
            case(5):
                localDir = value;
                break;
            case(6):
                filePath = value;
                break;
        }
    }

    public void save() throws  Exception{
        Properties props = new Properties();

        props.setProperty("sftp.host", host);
        props.setProperty("sftp.port", port);
        props.setProperty("sftp.user", user);
        props.setProperty("sftp.password", password);
        props.setProperty("path.localDir", localDir);
        props.setProperty("path.filePath", filePath);

        try (FileOutputStream out = new FileOutputStream("src/main/resources/config.properties")) {
            props.store(out, "App Configuration");
        }
    }
}
