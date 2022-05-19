import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class VFS {

    private Folder root;
    private IAllocator allocator;

    public VFS(IAllocator allocator) {
        this.root = new Folder("root/", "root");
        this.allocator = allocator;
    }

    public boolean pathExists(String path) throws Exception {
        ArrayList<String> dirs = Utils.getPath(path); // root/c1/c2/c3
        if (!dirs.get(0).equals("root")) {
            throw new Exception("Invalid path!");
        }
        Folder current = root;
        for (int i = 1; i < dirs.size() - 1; i++) {
            String dir_name = dirs.get(i);
            if (!current.isExist(dir_name)) {
                throw new Exception(dir_name + " does not exist!");
            }
            if (current.getDir(dir_name) instanceof File) {
                throw new Exception(dir_name + " is not a folder!");
            }
            current = (Folder) current.getDir(dir_name);
        }
        return current.isExist(dirs.get(dirs.size() - 1));
    }

    public void createFile(String fullPath, int size) throws Exception {
        /* Allocate then path to createFile of Folder class*/
        if (!pathExists(fullPath)) {
            Object[] ret = this.allocator.allocate(size);
            int fileAddress = (int) ret[0];
            File file = new File(fullPath, size, fileAddress);
            file.setBlocks((ArrayList<IAllocator.IBlock>) ret[1]);
            ArrayList<String> path = Utils.getPath(file.getFullPath());
            Folder currentFolder = root;
            for (int i = 1; i < path.size() - 1; i++) {
                currentFolder.setFileSize(currentFolder.getFileSize() + size);
                currentFolder = (Folder) currentFolder.getDir(path.get(i));
            }
            currentFolder.add(file);
        }
    }

    public void manualAllocate(String fullPath, ArrayList<Integer> blocks) throws Exception {
        if (!pathExists(fullPath)) {

            File file = new File(fullPath, blocks.size(), blocks.get(0));

            file.setBlocks(this.allocator.manualAllocate(blocks));

            ArrayList<String> path = Utils.getPath(file.getFullPath());
            Folder currentFolder = root;
            for (int i = 1; i < path.size() - 1; i++) {
                currentFolder.setFileSize(currentFolder.getFileSize() + blocks.size());
                currentFolder = (Folder) currentFolder.getDir(path.get(i));
            }
            currentFolder.add(file);
        }
    }

    public void createFolder(String path) throws Exception {
        // remove '/root' from path
        // check if folder doesn't already exist

        if (!pathExists(path)) {
            String folderName = Utils.getFileName(path);
            Folder folder = new Folder(path, folderName);
            ArrayList<String> tempPath = Utils.getPath(folder.getFullPath());
            Folder currentFolder = root;
            for (int i = 1; i < tempPath.size() - 1; i++) {
                currentFolder = (Folder) currentFolder.getDir(tempPath.get(i));
            }
            currentFolder.addFolder(folder); // hyt7at k2eno file brdo :( maybe hn3ml abstract class ydom el file w el folder?
        } else {
            throw new Exception("Folder Already Exists!");
        }

        // create folder
    }

    public void deleteFile(String path) throws Exception {
        if (pathExists(path)) {
            String fileName = Utils.getFileName(path);
            ArrayList<String> Folders = Utils.getPath(path);
            Folder currentFolder = root;
            for (int i = 1; i < Folders.size() - 1; i++) {
                currentFolder = (Folder) currentFolder.getDir(Folders.get(i));
            }
            AbstractFile file = currentFolder.getSub_dir().get(fileName);
            currentFolder.fileSize -= file.fileSize;
            allocator.deallocate(file.getAddress(), file.getFileSize());
            currentFolder.remove(fileName);
            //System.out.println("File Removed");
        } else {
            throw new Exception("File Was Not Found!");
        }
    }

    public void deleteFolder(String path) throws Exception { //TODO Lesa feha sho8l!!
        if (pathExists(path)) {
            ArrayList<String> Folders = Utils.getPath(path);
            Folder currentFolder = root;
            Folder parentFolder = root;
            for (int i = 1; i < Folders.size(); i++) {
                parentFolder = currentFolder;
                currentFolder = (Folder) currentFolder.getDir(Folders.get(i));
            }
            HashMap<Integer, Integer> address_size_pairs = currentFolder.deleteDirectory();
            for (var address : address_size_pairs.keySet()) {
                allocator.deallocate(address, address_size_pairs.get(address));
            }
            parentFolder.remove(currentFolder.getFileName());
            System.out.println("Folder Removed");
        } else {
            throw new Exception("File Was Not Found!");
        }
    }

    public void displayDiskStatus() {
        this.allocator.displayDiskStatus();
    }

    public void displayDiskStructure() {
        displayDiskStructure(this.root, 0);
    }

    public void displayDiskStructure(AbstractFile root, int level) {
        for (int i = 0; i < level; i++)
            System.out.print("  ");
        System.out.println(root.getFileName());

        if (root instanceof Folder) {
            Folder folder = (Folder) root;
            for (var child : folder.getSub_dir().entrySet()) {
                displayDiskStructure(child.getValue(), level + 1);
            }
        }
    }

    public void fileWriter() throws IOException {
        int total_disk_size = this.allocator.freeBlocks + this.allocator.allocatedBlocks;
        FileWriter writer = new FileWriter(Utils.fileLocation);
        if (this.allocator instanceof LinkedAllocation) {
            writer.write("linked_allocation\n");
        } else {
            writer.write("indexed_allocation\n");
        }
        writer.write("disk_size:" + total_disk_size + "\n");
        writer.close();
        fileWriter(this.root);
    }

    public void fileWriter(AbstractFile root) throws IOException {
        if (root instanceof Folder) {
            Folder folder = (Folder) root;

            for (var child : folder.getSub_dir().entrySet()) {
                fileWriter(child.getValue());
            }
        } else if (root instanceof File) {
            Object[] writer = new Object[]{root.getFullPath(), ((File) root).getBlocks()};
            this.allocator.writeUtil(writer);
        }
    }

    public void fileReader() throws Exception {
        int totalAllocated = 0;
        System.out.println("Reading from DiskStructure.vfs");
        this.root = new Folder("root/", "root");
        FileInputStream file_reader = new FileInputStream(Utils.fileLocation);
        Scanner reader = new Scanner(file_reader);
        String allocation_method = reader.nextLine();
        String total_disk_size_line = reader.nextLine();
        String disk_size_filter = total_disk_size_line.substring(total_disk_size_line.indexOf(":") + 1);

        int disk_size = Integer.parseInt(disk_size_filter);


        System.out.println(allocation_method);
        System.out.println(total_disk_size_line);

        ArrayList<String> dirs = new ArrayList<>();
        ArrayList<String> start_end_address = new ArrayList<>();
        if (allocation_method.equals("linked_allocation")) { // WE START TO READ LINKED ALLOCATION FILE FORMAT
            this.allocator = new LinkedAllocation(disk_size);
            /**
             * READ FROM FILE ALL DIRS AND ADDRESSES*/
            int le = 2;
            while (reader.hasNext()) {
                String dir = reader.nextLine();
                if (le % 2 == 0) {
                    int stop = dir.indexOf(" ");
                    dir = dir.substring(0, stop);
                    dirs.add(dir);
                } else {
                    start_end_address.add(dir);
                }
                le++;
            }

            System.out.println(dirs);
            System.out.println(start_end_address);
            /**
             * INITIALIZE SYSTEM WITH DATA FROM DiskStructure.vfs */
            for (int i = 0; i < dirs.size(); i++) {
                ArrayList<Integer> addresses = Utils.getAddressesFromFile(start_end_address.get(i)); //TODO This contains addresses of blocks for next created file
                int dirSize = addresses.size();
                totalAllocated += dirSize;
                ArrayList<String> path = Utils.getPath(dirs.get(i));
                StringBuilder path_builder = new StringBuilder();
                path_builder.append("root/");
                for (int j = 1; j < path.size(); j++) {
                    path_builder.append(path.get(j));
                    if (j == path.size() - 1) {
                        manualAllocate(dirs.get(i), addresses);//Here you create the file pointed to by the previous TODO so u must implement func to take this seq of addresses and use it

                    } else {
                        if (!pathExists(path_builder.toString())) {
                            createFolder(path_builder.toString());

                        }
                        path_builder.append("/");
                    }

                }
            }

        } else {//TODO SAME AS THE ABOVE BUT FOR INDEXED
            this.allocator = new IndexedAllocation(disk_size);
            ArrayList<Integer> addresses = new ArrayList<>();
            while (reader.hasNext()) {
                StringBuilder path_builder = new StringBuilder();
                String line = reader.nextLine(); //read directory and start address
                String[] dirSizeArr = line.split(" ");
                line = reader.nextLine(); // read addresses array
                String[] addressesArray = line.split(" ");
                totalAllocated += addressesArray.length;

                for (String address : addressesArray)
                    addresses.add(Integer.parseInt(address));

                String filePath = dirSizeArr[0];
                ArrayList<String> path = Utils.getPath(filePath);
                path_builder.append("root/");

                for (int i = 1; i < path.size(); i++) {
                    String folder = path.get(i);
                    path_builder.append(folder);
                    if (i == path.size() - 1) {
                        manualAllocate(filePath, addresses);
                    } else {
                        if (!pathExists(path_builder.toString())) {
                            createFolder(path_builder.toString());
                        }
                        path_builder.append("/");
                    }
                }
                addresses.clear();
            }
        }
        this.allocator.allocatedBlocks = totalAllocated;
        this.allocator.freeBlocks = this.allocator.freeBlocks - totalAllocated;
        System.out.println("Done vfs");
    }

    public static void main(String[] args) throws Exception {

        VFS vfs = new VFS(new IndexedAllocation(20));
        vfs.createFile("root/p1.txt", 3);
        vfs.createFile("root/p2.txt", 4);
        vfs.createFolder("root/p3f");
        vfs.createFolder("root/p3f/pxy");
        vfs.createFile("root/p3f/pxy/zip.folder", 2);


        vfs.createFile("root/p3f/p22.txt", 4);

        vfs.deleteFile("root/p1.txt");

        vfs.createFile("root/p1edit.txt", 3);

        vfs.displayDiskStructure(vfs.root, 0);
        vfs.root.ls();
        Folder f = (Folder) vfs.root.getSub_dir().get("p3f");
        f.ls();
        vfs.root.ls();

        //vfs.deleteFolder("root/p3f");
        vfs.root.ls();
        vfs.displayDiskStatus();
        vfs.fileWriter();
        vfs.fileReader();

    }
}
