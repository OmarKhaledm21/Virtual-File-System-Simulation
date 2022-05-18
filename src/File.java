public class File extends AbstractFile {
    public File(String fullPath,String name, int size, int address) {
        super(fullPath,name,size,address);
    }

    public File(String fullPath, int size, int address) {
        super(fullPath, size, address);
    }
}