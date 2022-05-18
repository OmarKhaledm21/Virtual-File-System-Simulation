import java.util.ArrayList;
import java.util.HashMap;

public class VFS {
    private final Folder root;
    private final IAllocator allocator;

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
            int fileAddress = this.allocator.allocate(size);
            File file = new File(fullPath, size, fileAddress);

            ArrayList<String> path = Utils.getPath(file.getFullPath());
            Folder currentFolder = root;
            for (int i = 1; i < path.size() - 1; i++) {
                currentFolder.setFileSize(currentFolder.getFileSize() + size);
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
            allocator.deallocate(file.getAddress(),file.getFileSize());
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
            HashMap<Integer,Integer> address_size_pairs = currentFolder.deleteDirectory();
            for(var address : address_size_pairs.keySet()){
                allocator.deallocate(address, address_size_pairs.get(address));
            }
            parentFolder.remove(currentFolder.getFileName());
            System.out.println("Folder Removed");
        } else {
            throw new Exception("File Was Not Found!");
        }
    }

    public void displayStatus() {
    }

    public static void main(String[] args) throws Exception {

        VFS vfs = new VFS(new LinkedAllocation(14));
        vfs.createFile("root/p1.txt", 2);
        vfs.createFile("root/p2.txt", 4);
        vfs.createFolder("root/p3f");
        vfs.createFile("root/p3f/p22.txt", 4);

        vfs.deleteFile("root/p1.txt");

        vfs.createFile("root/p1edit.txt",3);

        vfs.root.ls();
        Folder f = (Folder) vfs.root.getSub_dir().get("p3f");
        f.ls();
        vfs.root.ls();

        vfs.deleteFolder("root/p3f");
        vfs.root.ls();
        vfs.allocator.displayDiskStatus();
    }
}
