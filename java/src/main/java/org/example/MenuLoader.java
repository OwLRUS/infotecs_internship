package org.example;

import java.util.Scanner;

public class MenuLoader {
    private ConfigLoader cfg;

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
            System.out.flush();
        } catch (Exception e) {
            System.out.println("Screen cleaning error: " + e.getMessage());
        }
    }

    public void load(ConfigLoader config) throws Exception{
        cfg = config;

        clearScreen();
        System.out.print("=====================SFTP CLIENT=====================\n" +
                           "Do you want to change these parameters? (y/n)" +
                           "\n1. Host: " + cfg.getParamById(MenuKeys.HOST) +
                           "\n2. Port: " + cfg.getParamById(MenuKeys.PORT) +
                           "\n3. User: " + cfg.getParamById(MenuKeys.USER) +
                           "\n4. Password: " + cfg.getParamById(MenuKeys.PASSWORD) +
                           "\n5. Local directory: " + cfg.getParamById(MenuKeys.LOCAL_DIR) +
                           "\n6. File path: " + cfg.getParamById(MenuKeys.FILE_PATH) +
                           "\n> ");

        Scanner scan = new Scanner(System.in);

        while(true) {
            String enter = scan.nextLine();

            if (enter.equals("y") || enter.equals("Y")) {
                change();
                break;
            }
            if (enter.equals("n") || enter.equals("N")) break;

            System.out.print("Unexpected value. Enter y or n.\n> ");
        }
    }

    private void change() throws Exception{
        Scanner scan = new Scanner(System.in);
        int enter = -1;

        while(enter != MenuKeys.EXIT) {
            clearScreen();
            System.out.print( "====================CONFIG EDITOR====================\n" +
                    "Select what do you want to change:" +
                    "\n1. Host: " + cfg.getParamById(MenuKeys.HOST) +
                    "\n2. Port: " + cfg.getParamById(MenuKeys.PORT) +
                    "\n3. User: " + cfg.getParamById(MenuKeys.USER) +
                    "\n4. Password: " + cfg.getParamById(MenuKeys.PASSWORD) +
                    "\n5. Local directory: " + cfg.getParamById(MenuKeys.LOCAL_DIR) +
                    "\n6. File path: " + cfg.getParamById(MenuKeys.FILE_PATH) +
                    "\n7. All." +
                    "\n0. Save and exit." +
                    "\n> ");

            enter = scan.nextInt();
            scan.nextLine();
            String input;
            switch (enter) {
                case MenuKeys.HOST:
                    clearScreen();
                    while (true) {
                        System.out.print("Enter IP (0 for exit): ");
                        input = scan.nextLine();

                        if (input.trim().equals("0")) break;
                        if (input.matches("^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$")) break;

                        System.out.println("Wrong input. Enter valid IP!");
                    }
                    if (input.trim().equals("0")) break;

                    cfg.changeByID(MenuKeys.HOST, input);
                    break;

                case MenuKeys.PORT:
                    clearScreen();
                    while (true) {
                        System.out.print("Enter Port (0 for exit): ");
                        input = scan.nextLine();

                        if (input.trim().equals("0")) break;
                        if (input.matches("^(0|[1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$")) break;

                        System.out.println("Wrong input. Enter valid Port!");
                    }
                    if (input.trim().equals("0")) break;

                    cfg.changeByID(MenuKeys.PORT, input);
                    break;

                case MenuKeys.USER:
                    clearScreen();
                    System.out.print("Enter User value: ");
                    cfg.changeByID(MenuKeys.USER, scan.nextLine());
                    break;

                case MenuKeys.PASSWORD:
                    clearScreen();
                    System.out.print("Enter Password value: ");
                    cfg.changeByID(MenuKeys.PASSWORD, scan.nextLine());
                    break;

                case MenuKeys.LOCAL_DIR:
                    clearScreen();
                    System.out.print("Enter Local directory value: ");
                    cfg.changeByID(MenuKeys.LOCAL_DIR, scan.nextLine());
                    break;

                case MenuKeys.FILE_PATH:
                    clearScreen();
                    System.out.print("Enter File path value: ");
                    cfg.changeByID(MenuKeys.FILE_PATH, scan.nextLine());
                    break;

                case MenuKeys.ALL:
                    String isZero;
                    while (true) {
                        System.out.print("Enter IP value (0 for save and exit): ");
                        isZero = scan.nextLine();

                        if (isZero.trim().equals("0")) break;
                        if (isZero.matches("^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$")) break;

                        System.out.println("Wrong input. Enter valid IP!");
                    }
                    if(isZero.equals("0")) break; else cfg.changeByID(MenuKeys.HOST, isZero);
                    while (true) {
                        System.out.print("Enter Port value(0 for save and exit): ");
                        isZero = scan.nextLine();

                        if (isZero.trim().equals("0")) break;
                        if (isZero.matches("^(0|[1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$")) break;

                        System.out.println("Wrong input. Enter valid Port!");
                    }
                    if(isZero.equals("0")) break; else cfg.changeByID(MenuKeys.PORT, isZero);
                    System.out.print("Enter User value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else cfg.changeByID(MenuKeys.USER, isZero);
                    System.out.print("Enter Password value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else cfg.changeByID(MenuKeys.PASSWORD, isZero);
                    System.out.print("Enter Local directory value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else cfg.changeByID(MenuKeys.LOCAL_DIR, isZero);
                    System.out.print("Enter File path value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else cfg.changeByID(MenuKeys.FILE_PATH, isZero);
                    break;

                case MenuKeys.EXIT:
                    break;

                default:
                    clearScreen();
                    System.out.println("Unexpected value. Press Enter to try again...");
                    String pause = scan.nextLine();
                    break;
            }
        }

        cfg.save();
    }

    public void connection(ClientSFTP client) throws Exception{
        clearScreen();
        System.out.print("=====================CONNECTION=====================\n" +
                "Connecting to " + cfg.getParamById(MenuKeys.HOST) + ":" + cfg.getParamById(MenuKeys.PORT) + "... ");
        client.connect(cfg);
        System.out.println("Connected!");
        System.out.println("Press Enter to continue...");
        Scanner scan = new Scanner(System.in);
        String pause = scan.nextLine();

        int enter = -1;
        while(enter != MenuKeys.EXIT) {
            clearScreen();
            System.out.print("==================HOST " + cfg.getParamById(MenuKeys.HOST) + ":" + cfg.getParamById(MenuKeys.PORT) + "==================\n" +
                    "Menu:" +
                    "\n1. Print domain-ip pair list." +
                    "\n2. Get IP by domain." +
                    "\n3. Get domain by IP." +
                    "\n4. Add new domain-ip pair." +
                    "\n5. Delete domain-ip pair." +
                    "\n0. Exit." +
                    "\n> ");

            enter = scan.nextInt();
            scan.nextLine();

            switch (enter)
            {
                case MenuKeys.PRINT_LIST:
                    client.list();
                    System.out.println("Press Enter to continue...");
                    pause = scan.nextLine();
                    break;

                case MenuKeys.GET_IP:
                    clearScreen();
                    System.out.print("==================HOST " + cfg.getParamById(MenuKeys.HOST) + ":" + cfg.getParamById(MenuKeys.PORT) + "==================\n");

                    String domain;
                    while (true) {
                        System.out.print("Enter domain (0 for exit): ");
                        domain = scan.nextLine();

                        if (domain.trim().equals("0")) break;
                        if (domain.matches("^(?=.{1,253}$)([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$")) break;

                        System.out.println("Wrong input. Enter valid domain!");
                    }
                    if (domain.trim().equals("0")) break;

                    String rIP = client.findByDomain(domain);

                    System.out.println("Result: " + rIP);
                    System.out.println("Press Enter to continue...");
                    pause = scan.nextLine();
                    break;

                case MenuKeys.GET_DOMAIN:
                    clearScreen();
                    System.out.print("==================HOST " + cfg.getParamById(MenuKeys.HOST) + ":" + cfg.getParamById(MenuKeys.PORT) + "==================\n");

                    String IP;
                    while (true) {
                        System.out.print("Enter IP (0 for exit): ");
                        IP = scan.nextLine();

                        if (IP.trim().equals("0")) break;
                        if (IP.matches("^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$")) break;

                        System.out.println("Wrong input. Enter valid IP!");
                    }
                    if (IP.trim().equals("0")) break;

                    String rDomain = client.findByIP(IP);

                    System.out.println("Result: " + rDomain);
                    System.out.println("Press Enter to continue...");
                    pause = scan.nextLine();
                    break;

                case MenuKeys.ADD_PAIR:
                    clearScreen();
                    System.out.print("==================HOST " + cfg.getParamById(MenuKeys.HOST) + ":" + cfg.getParamById(MenuKeys.PORT) + "==================\n");

                    String domain_pair;
                    while (true) {
                        System.out.print("Enter domain (0 for exit): ");
                        domain_pair = scan.nextLine();

                        if(domain_pair.trim().equals("0")) break;
                        if (domain_pair.matches("^(?=.{1,253}$)([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$")) break;

                        System.out.println("Wrong input. Enter valid domain!");
                    }
                    if(domain_pair.trim().equals("0")) break;

                    String IP_pair;
                    while (true) {
                        System.out.print("Enter IP (0 for exit): ");
                        IP_pair = scan.nextLine();

                        if(IP_pair.trim().equals("0")) break;
                        if (IP_pair.matches("^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$")) break;

                        System.out.println("Wrong input. Enter valid IP!");
                    }
                    if(IP_pair.trim().equals("0")) break;

                    System.out.println(client.addPair(domain_pair, IP_pair));
                    System.out.println("Press Enter to continue...");
                    pause = scan.nextLine();
                    break;

                case MenuKeys.DELETE_PAIR:
                    clearScreen();
                    System.out.print("==================HOST " + cfg.getParamById(MenuKeys.HOST) + ":" + cfg.getParamById(MenuKeys.PORT) + "==================\n");

                    String subj = new String();
                    String result = new String();

                    while(true)
                    {
                        System.out.print("Enter domain or IP (0 for exit): ");
                        subj = scan.nextLine();

                        if(subj.matches("^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$")) {
                            result = client.deleteByIP(subj);
                            break;
                        }
                        else if(subj.matches("^(?=.{1,253}$)([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$")) {
                            result = client.deleteByDomain(subj);
                            break;
                        }
                        else
                        {
                            if(subj.trim().equals("0")) break;
                            System.out.println("Wrong input. Enter valid domain or IP!");
                        }
                    }
                    if(subj.trim().equals("0")) break;

                    System.out.println(result);
                    System.out.println("Press Enter to continue...");
                    pause = scan.nextLine();
                    break;

                case MenuKeys.EXIT:
                    clearScreen();
                    break;

                default:
                    clearScreen();
                    System.out.println("Unexpected value. Press Enter to try again...");
                    pause = scan.nextLine();
                    break;
            }
        }

        client.disconnect();
    }
}
