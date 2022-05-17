public abstract class AbstractFile {
    private String fullPath;
    private String name;
    protected int size;
    private int address;



    public AbstractFile(String fullPath,String name, int size, int address) {
        this(fullPath,size,address);
        this.setName(name);
    }

    public AbstractFile(String fullPath, int size, int address) {
        this.fullPath = fullPath;
        this.size = size;
        this.address = address;
        this.name = Utils.getFileName(fullPath);
    }



    public int getAddress() {
        return address;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
