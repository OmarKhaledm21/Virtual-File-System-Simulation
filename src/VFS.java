public class VFS {
    private Folder root;
    private IAllocator allocator;

    public VFS(Folder root, IAllocator allocator) {
        this.root = root;
        this.allocator = allocator;
    }

    public void createFile(String path, int size) throws Exception {
        // remove '/root' from path
        // check if file doen't already exist
        // check if free space available

        // if true for both
        // allocate and return address
        // create file
    }

    public void createFolder(String path) throws Exception {
        // remove '/root' from path
        // check if folder doen't already exist

        // create folder
    }

    public void deleteFile(String path) throws Exception {
    }

    public void deleteFolder(String path) throws Exception {
    }

    public void displayStatus() {
    }

}
