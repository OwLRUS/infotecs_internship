package org.example;

import java.util.Properties;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

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

    private static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void change() throws Exception{
        Scanner scan = new Scanner(System.in);
        int enter = 1;

        while(enter != 0) {
            clearScreen();
            System.out.println("Select what do you want to change:" +
                    "\n1. Host: " + host +
                    "\n2. Port: " + port +
                    "\n3. User: " + user +
                    "\n4. Password: " + password +
                    "\n5. Local Directory: " + localDir +
                    "\n6. File Path: " + filePath +
                    "\n7. All." +
                    "\n0. Save and exit.");

            enter = scan.nextInt();
            scan.nextLine();
            switch (enter) {
                case (1):
                    clearScreen();
                    System.out.print("Enter Host value: ");
                    host = scan.nextLine();
                    break;
                case (2):
                    clearScreen();
                    System.out.print("Enter Port value: ");
                    port = scan.nextLine();
                    break;
                case (3):
                    clearScreen();
                    System.out.print("Enter User value: ");
                    user = scan.nextLine();
                    break;
                case (4):
                    clearScreen();
                    System.out.print("Enter Password value: ");
                    password = scan.nextLine();
                    break;
                case (5):
                    clearScreen();
                    System.out.print("Enter Local directory value: ");
                    localDir = scan.nextLine();
                    break;
                case (6):
                    clearScreen();
                    System.out.print("Enter File path value: ");
                    filePath = scan.nextLine();
                    break;
                case (7):
                    clearScreen();
                    String isZero;
                    System.out.print("Enter Host value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else host = isZero;
                    System.out.print("Enter Port value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else port = isZero;
                    System.out.print("Enter User value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else user = isZero;
                    System.out.print("Enter Password value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else password = isZero;
                    System.out.print("Enter Local directory value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else localDir = isZero;
                    System.out.print("Enter File path value (0 for save and exit): ");
                    isZero = scan.nextLine();
                    if(isZero.equals("0")) break; else filePath = isZero;
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

        save();
    }

    private void save() throws  Exception{
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
