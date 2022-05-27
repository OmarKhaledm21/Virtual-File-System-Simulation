import java.util.ArrayList;

public class File extends AbstractFile {
    ArrayList<IAllocator.IBlock> blocks;

    public File(String fullPath, String name, int size, int address) {
        super(fullPath, name, size, address);
        blocks = new ArrayList<>();
    }

    public File(String fullPath, int size, int address) {
        super(fullPath, size, address);
        blocks = new ArrayList<>();
    }

    public void setBlocks(ArrayList<IAllocator.IBlock> blocks) {
        this.blocks = blocks;
    }



    public ArrayList<IAllocator.IBlock> getBlocks() {
        return blocks;
    }
    public Object[] pathAndBlocks(){
        return new Object[]{this.getFullPath(),this.getBlocks()};
    }
}