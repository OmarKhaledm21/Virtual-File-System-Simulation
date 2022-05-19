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
        if (choice == 1) {
            vfs.fileReader();
        }
        if (choice == 2) {
            System.out.println("1- Linked Allocation.\n" +
                    "2- Indexed Allocation.\n");
            choice = scanner.nextInt();
            System.out.println("Enter disk size:");
            int disk_space = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                vfs = new VFS(new LinkedAllocation(disk_space));
            }else{
                vfs = new VFS(new IndexedAllocation(disk_space));
            }
        }

        System.out.println("Enter exit to quit\n");
        String command_buffer = null;
        boolean flag = true;
        while (flag) {
            command_buffer= scanner.nextLine();
            String[] commands = command_buffer.split(" ");
            switch (commands[0].toLowerCase()){
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
            }
        }

    }
}
