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
            switch (enter) {
                case MenuKeys.HOST:
                    clearScreen();
                    System.out.print("Enter Host value: ");
                    cfg.changeByID(MenuKeys.HOST, scan.nextLine());
                    break;
                case MenuKeys.PORT:
                    clearScreen();
                    System.out.print("Enter Port value: ");
                    cfg.changeByID(MenuKeys.PORT, scan.nextLine());
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
                    clearScreen();
                    String isZero;
                    System.out.print("Enter Host value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else cfg.changeByID(MenuKeys.HOST, isZero);
                    System.out.print("Enter Port value (0 for save and exit): ");
                    isZero = scan.nextLine();
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
                    System.out.print("==================HOST " + cfg.getParamById(MenuKeys.HOST) + ":" + cfg.getParamById(MenuKeys.PORT) + "==================\n" +
                                     "Enter domain: ");

                    String domain = scan.nextLine();
                    String rIP = client.findByDomain(domain);

                    System.out.println("Result: " + rIP);
                    System.out.println("Press Enter to continue...");
                    pause = scan.nextLine();
                    break;
                case MenuKeys.GET_DOMAIN:
                    clearScreen();
                    System.out.print("==================HOST " + cfg.getParamById(MenuKeys.HOST) + ":" + cfg.getParamById(MenuKeys.PORT) + "==================\n" +
                            "Enter IP: ");

                    String IP = scan.nextLine();
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
                        System.out.print("Enter domain: ");
                        domain_pair = scan.nextLine();
                        if (domain_pair.matches("^(?!-)[A-Za-z0-9-]{1,63}(?<!-)(\\.(?!-)[A-Za-z0-9-]{1,63}(?<!-))*$")) break;
                        System.out.println("Wrong input. Enter valid domain!");
                    }

                    String IP_pair;
                    while (true) {
                        System.out.print("Enter IP: ");
                        IP_pair = scan.nextLine();
                        if (IP_pair.matches("^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.|$)){4}$")) break;
                        System.out.println("Wrong input. Enter valid IP!");
                    }

                    System.out.println(client.addPair(domain_pair, IP_pair));
                    System.out.println("Press Enter to continue...");
                    pause = scan.nextLine();
                    break;
                case MenuKeys.DELETE_PAIR:
                    clearScreen();
                    System.out.print("==================HOST " + cfg.getParamById(MenuKeys.HOST) + ":" + cfg.getParamById(MenuKeys.PORT) + "==================\n" +
                            "Enter domain or IP: ");
                    String subj = scan.nextLine();

                    String result;
                    if(subj.matches("^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.|$)){4}$")) result = client.deleteByIP(subj);
                        else result = client.deleteByDomain(subj);

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
