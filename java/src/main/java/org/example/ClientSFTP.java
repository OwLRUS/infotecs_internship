package org.example;

import com.jcraft.jsch.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClientSFTP {
    private JSch jsch;
    private Session session;
    private ChannelSftp sftp;
    private ParserJSON json = new ParserJSON();
    private ConfigLoader cfg;

    public void connect(ConfigLoader config) throws Exception{
        try{
            jsch = new JSch();
            cfg = config;

            session = jsch.getSession(cfg.getParamById(MenuKeys.USER), cfg.getParamById(MenuKeys.HOST),
                                                                Integer.parseInt(cfg.getParamById(MenuKeys.PORT)));
            session.setPassword(cfg.getParamById(MenuKeys.PASSWORD));
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;

            sftp.get(cfg.getParamById(MenuKeys.FILE_PATH), cfg.getParamById(MenuKeys.LOCAL_DIR));
            json.parseString(cfg.getParamById(MenuKeys.LOCAL_DIR) + File.separator + cfg.getParamById(MenuKeys.FILE_PATH));
        } catch (Exception e) {
            System.err.println("Cannot connect to remote host. Exiting...");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void clearScreen(){
        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder builder;

            if (os.contains("win")) {
                builder = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                builder = new ProcessBuilder("/bin/bash", "-c", "clear");
            }

            builder.inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("Screen cleaning error: " + e.getMessage());
        }
    }

    public void list() {
        clearScreen();
        System.out.println("===================ADDRESSES LIST===================");
        List<String> pairs = new ArrayList<>();

        int size = json.domList.size();
        for(int i = 0; i < size; i++){
            pairs.add(json.domList.get(i) + " - " + json.ipList.get(i));
        }

        pairs.sort(Comparator.naturalOrder());
        for(int i = 0; i < size; i++){
            System.out.println((i + 1) + ". " + pairs.get(i));
        }
    }

    public String findByDomain(String domain_str) {
        int index = json.domList.indexOf(domain_str);

        if(index == -1) return "Doesn't find IP by given domain.";
        return json.ipList.get(index);
    }

    public String findByIP(String ip_str) {
        int index = json.ipList.indexOf(ip_str);

        if(index == -1) return "Doesn't find domain by given IP.";
        return json.domList.get(index);
    }

    public String addPair(String domain, String ip) throws Exception{
        String isExist = findByDomain(domain);
        if (isExist.matches("^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.|$)){4}$")) {
            return "This domain is already exist. Returning to menu.";
        }

        isExist = findByIP(ip);
        if (isExist.matches("^([a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,}$")) {
            return "This IP is already exist. Returning to menu.";
        }

        json.domList.add(domain);
        json.ipList.add(ip);

        json.saveJSON(cfg.getParamById(MenuKeys.LOCAL_DIR) + File.separator + cfg.getParamById(MenuKeys.FILE_PATH));
        sftp.put(cfg.getParamById(MenuKeys.LOCAL_DIR) + File.separator + cfg.getParamById(MenuKeys.FILE_PATH),
                                                                                cfg.getParamById(MenuKeys.FILE_PATH));

        return "New pair added successfully";
    }

    public String deleteByDomain(String domain) throws Exception{
        int index = json.domList.indexOf(domain);
        if(index == -1) return "Given domain does not exist.";

        json.domList.remove(index);
        json.ipList.remove(index);

        json.saveJSON(cfg.getParamById(MenuKeys.LOCAL_DIR) + File.separator + cfg.getParamById(MenuKeys.FILE_PATH));
        sftp.put(cfg.getParamById(MenuKeys.LOCAL_DIR) + File.separator + cfg.getParamById(MenuKeys.FILE_PATH),
                                                                                cfg.getParamById(MenuKeys.FILE_PATH));

        return "Pair deleted successfully";
    }

    public String deleteByIP(String ip) throws Exception{
        int index = json.ipList.indexOf(ip);
        if(index == -1) return "Given IP does not exist.";

        json.domList.remove(index);
        json.ipList.remove(index);

        json.saveJSON(cfg.getParamById(MenuKeys.LOCAL_DIR) + File.separator + cfg.getParamById(MenuKeys.FILE_PATH));
        sftp.put(cfg.getParamById(MenuKeys.LOCAL_DIR) + File.separator + cfg.getParamById(MenuKeys.FILE_PATH),
                                                                                cfg.getParamById(MenuKeys.FILE_PATH));

        return "Pair deleted successfully";
    }

    public void disconnect(){
        try {
            sftp.exit();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
