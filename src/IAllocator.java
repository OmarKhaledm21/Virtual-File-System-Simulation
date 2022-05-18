import java.util.ArrayList;

public abstract class IAllocator {
    interface IBlock { }
    protected int freeBlocks;
    protected int allocatedBlocks;
    protected ArrayList<IBlock> disk;

    public IAllocator(int N) {
        freeBlocks = N;
        disk = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            disk.add(null);
        }
    }

    public boolean sufficientSpaceAvailable(int size) {
        return (freeBlocks >= size);
    }

    public int getFreeBlockAddress() {
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) == null) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Integer> getFreeBlocksAddresses() {
        ArrayList<Integer> free_block_ind = new ArrayList<>();
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) == null) {
                free_block_ind.add(i);
            }
        }
        return free_block_ind;
    }

    public ArrayList<Integer> getAllocatedBlocksAddresses() {
        ArrayList<Integer> allocated_block_ind = new ArrayList<>();
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) != null) {
                allocated_block_ind.add(i);
            }
        }
        return allocated_block_ind;
    }

    public abstract Object[] allocate(int size) throws Exception;

    public abstract void deallocate(int address, int size);

    public abstract void writeUtil(Object[] obj);

    public void displayDiskStatus() {
        System.out.println("Empty space left = " + this.freeBlocks);
        System.out.println("Allocated space = " + this.allocatedBlocks);
        System.out.println("Empty blocks are {" + getFreeBlocksAddresses().toString() + "}");
        System.out.println("Allocated blocks are {" + getAllocatedBlocksAddresses().toString() + "}");
    }
}
