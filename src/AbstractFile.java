public abstract class AbstractFile {
    private String fullPath;
    private String fileName;
    protected int fileSize;
    private int address;



    public AbstractFile(String fullPath, String fileName, int fileSize, int address) {
        this(fullPath, fileSize,address);
        this.setFileName(fileName);
    }

    public AbstractFile(String fullPath, int fileSize, int address) {
        this.fullPath = fullPath;
        this.fileSize = fileSize;
        this.address = address;
        this.fileName = Utils.getFileName(fullPath);
    }



    public int getAddress() {
        return address;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    @Override
    public String toString() {
        return fileName;
    }
}
