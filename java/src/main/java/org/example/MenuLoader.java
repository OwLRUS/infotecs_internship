package org.example;

import java.util.Scanner;

public class MenuLoader {
    private ConfigLoader cfg;
    public void load(ConfigLoader config) throws Exception{
        cfg = config;

        clearScreen();
        System.out.print("=====================SFTP CLIENT=====================\n" +
                           "Do you want to change these parameters? (y/n)" +
                           "\n1. Host: " + cfg.getParamById(1) +
                           "\n2. Port: " + cfg.getParamById(2) +
                           "\n3. User: " + cfg.getParamById(3) +
                           "\n4. Password: " + cfg.getParamById(4) +
                           "\n5. Local directory: " + cfg.getParamById(5) +
                           "\n6. File path: " + cfg.getParamById(6) +
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
        int enter = 1;

        while(enter != 0) {
            clearScreen();
            System.out.print( "====================CONFIG EDITOR====================\n" +
                    "Select what do you want to change:" +
                    "\n1. Host: " + cfg.getParamById(1) +
                    "\n2. Port: " + cfg.getParamById(2) +
                    "\n3. User: " + cfg.getParamById(3) +
                    "\n4. Password: " + cfg.getParamById(4) +
                    "\n5. Local Directory: " + cfg.getParamById(5) +
                    "\n6. File Path: " + cfg.getParamById(6) +
                    "\n7. All." +
                    "\n0. Save and exit." +
                    "\n> ");

            enter = scan.nextInt();
            scan.nextLine();
            switch (enter) {
                case (1):
                    clearScreen();
                    System.out.print("Enter Host value: ");
                    cfg.changeByID(1, scan.nextLine());
                    break;
                case (2):
                    clearScreen();
                    System.out.print("Enter Port value: ");
                    cfg.changeByID(2, scan.nextLine());
                    break;
                case (3):
                    clearScreen();
                    System.out.print("Enter User value: ");
                    cfg.changeByID(3, scan.nextLine());
                    break;
                case (4):
                    clearScreen();
                    System.out.print("Enter Password value: ");
                    cfg.changeByID(4, scan.nextLine());
                    break;
                case (5):
                    clearScreen();
                    System.out.print("Enter Local directory value: ");
                    cfg.changeByID(5, scan.nextLine());
                    break;
                case (6):
                    clearScreen();
                    System.out.print("Enter File path value: ");
                    cfg.changeByID(6, scan.nextLine());
                    break;
                case (7):
                    clearScreen();
                    String isZero;
                    System.out.print("Enter Host value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else cfg.changeByID(1, isZero);
                    System.out.print("Enter Port value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else cfg.changeByID(2, isZero);
                    System.out.print("Enter User value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else cfg.changeByID(3, isZero);
                    System.out.print("Enter Password value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else cfg.changeByID(4, isZero);
                    System.out.print("Enter Local directory value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else cfg.changeByID(5, isZero);
                    System.out.print("Enter File path value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else cfg.changeByID(6, isZero);
                    break;
                case (0):
                    break;
                default:
                    clearScreen();
                    System.out.println("Unexpected value. Press Enter to try again...");
                    String skip = scan.nextLine();
                    break;
            }
        }

        cfg.save();
    }

    private static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void connection(ClientSFTP client) throws Exception{
        clearScreen();
        System.out.print("=====================CONNECTION=====================\n" +
                "Connecting to " + cfg.getParamById(1) + ":" + cfg.getParamById(2) + "... ");
        client.connect(cfg);
        System.out.println("Connected!");

        int enter = -1;
        while(enter != 0) {
            clearScreen();
            System.out.print("==================HOST " + cfg.getParamById(1) + ":" + cfg.getParamById(2) + "==================\n" +
                    "Menu:" +
                    "\n1. Print domain-ip pair list." +
                    "\n2. Get IP by domain." +
                    "\n3. Get domain by IP." +
                    "\n4. Add new domain-ip pair." +
                    "\n5. Delete domain-ip pair." +
                    "\n0. Exit." +
                    "\n> ");

            Scanner scan = new Scanner(System.in);
            enter = scan.nextInt();
            scan.nextLine();

            switch (enter)
            {
                case(1):
                    client.list();
                    break;
                case(2):
                    clearScreen();
                    System.out.print("==================HOST " + cfg.getParamById(1) + ":" + cfg.getParamById(2) + "==================\n" +
                                     "Enter domain: ");

                    String domain = scan.nextLine();
                    String rIP = client.findByDomain(domain);

                    System.out.println("Result: " + rIP);
                    break;
                case(3):
                    clearScreen();
                    System.out.print("==================HOST " + cfg.getParamById(1) + ":" + cfg.getParamById(2) + "==================\n" +
                            "Enter IP: ");

                    String IP = scan.nextLine();
                    String rDomain = client.findByIP(IP);

                    System.out.println("Result: " + rDomain);
                    break;
                case(4):
                    clearScreen();
                    System.out.print("==================HOST " + cfg.getParamById(1) + ":" + cfg.getParamById(2) + "==================\n" +
                            "Enter domain: ");
                    String domain_pair = scan.nextLine();

                    String IP_pair;
                    while (true) {
                        System.out.print("Enter IP: ");
                        IP_pair = scan.nextLine();
                        if (IP_pair.matches("^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.|$)){4}$")) break;
                        System.out.println("Wrong input. Enter valid IP!");
                    }

                    System.out.println(client.addPair(domain_pair, IP_pair));
                    break;
                case(5):
                    clearScreen();
                    System.out.print("==================HOST " + cfg.getParamById(1) + ":" + cfg.getParamById(2) + "==================\n" +
                            "Enter domain or IP: ");
                    String subj = scan.nextLine();

                    String result;
                    if(subj.matches("^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.|$)){4}$")) result = client.deleteByIP(subj);
                        else result = client.deleteByDomain(subj);

                    System.out.println(result);
                    break;
                case(0):
                    break;
                default:
                    clearScreen();
                    System.out.println("Unexpected value. Press Enter to try again...");
                    String skip = scan.nextLine();
                    break;
            }
        }

        client.disconnect();
    }
}
