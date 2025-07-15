package org.example;

import com.jcraft.jsch.*;

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

            session = jsch.getSession(cfg.getParamById(3), cfg.getParamById(1), Integer.parseInt(cfg.getParamById(2)));
            session.setPassword(cfg.getParamById(4));
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;

            sftp.get(cfg.getParamById(6), cfg.getParamById(5));
            json.parseString(cfg.getParamById(5) + "\\" + cfg.getParamById(6));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
            System.out.println(i + ". " + pairs.get(i));
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

        json.saveJSON(cfg.getParamById(5) + "\\" + cfg.getParamById(6));
        sftp.put(cfg.getParamById(5) + "\\" + cfg.getParamById(6), cfg.getParamById(6));

        return "New pair added successfully";
    }

    public String deleteByDomain(String domain) throws Exception{
        int index = json.domList.indexOf(domain);
        if(index == -1) return "Given domain does not exist.";

        json.domList.remove(index);
        json.ipList.remove(index);

        json.saveJSON(cfg.getParamById(5) + "\\" + cfg.getParamById(6));
        sftp.put(cfg.getParamById(5) + "\\" + cfg.getParamById(6), cfg.getParamById(6));

        return "Pair deleted successfully";
    }

    public String deleteByIP(String ip) throws Exception{
        int index = json.ipList.indexOf(ip);
        if(index == -1) return "Given IP does not exist.";

        json.domList.remove(index);
        json.ipList.remove(index);

        json.saveJSON(cfg.getParamById(5) + "\\" + cfg.getParamById(6));
        sftp.put(cfg.getParamById(5) + "\\" + cfg.getParamById(6), cfg.getParamById(6));

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
