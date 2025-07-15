package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        ConfigLoader cfg = new ConfigLoader(); cfg.load(); cfg.change();
        //MenuLoader menu = MenuLoader.load(cfg);
        //ClientSFTP client = ClientSFTP.load();
    }
}