package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        ConfigLoader cfg = new ConfigLoader(); cfg.load();
        MenuLoader menu = new MenuLoader(); menu.load(cfg);
        ClientSFTP client = new ClientSFTP();
        menu.connection(client);
    }
}
/*
* 1. Решить как будут определяться пути к файлу SFTP
* 2. Решить вопрос с чисткой консоли
* 3. Числа на константы
* 4. Добавить паузы после действий
* */