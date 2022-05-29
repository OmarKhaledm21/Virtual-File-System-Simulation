import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1- Read disk structure from DiskStructure.vfs file\n" +
                "2- Start your own new structure\n");
        VFS vfs = new VFS(new LinkedAllocation(1));

        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            vfs.fileReader();
        }
        if (choice == 2) {
            System.out.println("1- Linked Allocation.\n" +
                    "2- Indexed Allocation.\n" +
                    "3- Contiguous Allocation\n");
            choice = scanner.nextInt();
            System.out.println("Enter disk size:");
            int disk_space = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                vfs = new VFS(new LinkedAllocation(disk_space));
            } else if(choice == 2){
                vfs = new VFS(new IndexedAllocation(disk_space));
            }else if(choice == 3){
                vfs = new VFS(new ContiguousAllocation(disk_space));
            }else{
                System.out.println("Invalid choice!\n");
            }
        }

        System.out.println("**************************");
        System.out.println("Hello There!\n" +
                "Please Enter One Of the Following Commands!\n" +
                "- CreateFile -> File Path -> File Size\n" +
                "- Create Folder -> Folder Path\n" +
                "- Delete Folder (File) -> Folder (File) Path\n" +
                "- DisplayDiskStructure\n" +
                "- DisplayDiskStatus\n" +
                "- TellUser\n" +
                "- CUser -> Username -> Password\n" +
                "- Grant -> Username -> FullPath -> Bits\n" +
                "- Login -> Username -> Password\n");

        System.out.println("Enter exit to quit");
        String command_buffer = null;
        boolean flag = true;
        while (flag) {
            try {
                command_buffer = scanner.nextLine();
                String[] commands = command_buffer.split(" ");
                switch (commands[0].toLowerCase()) {
                    case "exit":
                        flag = false;
                        vfs.fileWriter();
                        break;
                    case "createfile":
                        vfs.createFile(commands[1], Integer.parseInt(commands[2]));
                        break;
                    case "createfolder":
                        vfs.createFolder(commands[1]);
                        break;
                    case "deletefolder":
                        vfs.deleteFolder(commands[1]);
                        break;
                    case "deletefile":
                        vfs.deleteFile(commands[1]);
                        break;
                    case "displaydiskstatus":
                        vfs.displayDiskStatus();
                        break;
                    case "displaydiskstructure":
                        vfs.displayDiskStructure();
                        break;

                    case "telluser":
                        UserManager.getInstance().tellUser();
                        break;
                    case "cuser":
                        UserManager.getInstance().createUser(commands[1],commands[2]);
                        break;
                    case "grant":
                        UserManager.getInstance().grantPermission(commands[1],commands[2],commands[3]);
                        break;
                    case "login":
                        UserManager.getInstance().login(commands[1],commands[2]);
                        break;
                    default:
                        //System.out.println("Wrong Command!");
                        break;
                }
                if (!commands[0].equals("exit")) {
                    System.out.println("Nice Now Enter One Of the Following Commands!\n" +
                            "- CreateFile -> File Path -> File Size\n" +
                            "- Create Folder -> Folder Path\n" +
                            "- Delete Folder (File) -> Folder (File) Path\n" +
                            "- DisplayDiskStructure\n" +
                            "- DisplayDiskStatus");
                    System.out.println("Enter exit to quit");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
