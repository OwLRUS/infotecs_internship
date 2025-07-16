package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        ConfigLoader cfg = new ConfigLoader(); cfg.load();
        MenuLoader menu = new MenuLoader(); menu.load(cfg);
        ClientSFTP client = new ClientSFTP();
        menu.connection(client);
    }
}