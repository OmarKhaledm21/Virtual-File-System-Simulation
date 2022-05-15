public class File {
    private String name;
    private int size;
    private int address;
    
    public File(String name, int size, int address) {
        this.setName(name);
        this.setSize(size);
        this.address = address;
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
}