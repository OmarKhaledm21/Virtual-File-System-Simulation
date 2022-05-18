import java.util.ArrayList;


public class LinkedAllocation extends IAllocator {
    static class Block implements IBlock {
        int currentAddress;
        Block next;

        public Block(int currentAddress, Block next) {
            this.currentAddress = currentAddress;
            this.next = next;
        }
    }

    public LinkedAllocation(int N) {
        super(N);
    }

    @Override
    public int allocate(int size) throws Exception {
        if (!sufficientSpaceAvailable(size)) {
            throw new Exception("No space available");
        }
        int start_address = getFreeBlockAddress();
        Block start = new Block(start_address, null);
        disk.set(start_address, start);
        freeBlocks -= size;
        allocatedBlocks += size;
        size--;
        while (size > 0) {
            int next_address = getFreeBlockAddress();
            Block next_block = new Block(next_address, null);
            start.next = next_block;
            start = next_block;
            disk.set(next_address, next_block);
            size--;
        }
        return start_address;
    }

    @Override
    public void deallocate(int start_address, int size) {
        freeBlocks += size;
        allocatedBlocks -= size;

        Block cursor = null;
        int next_address = start_address;
        while (size > 0) {
            int current_address = next_address;
            cursor = (Block) disk.get(current_address);
            if (cursor != null) {
                disk.set(current_address, null);
                size--;
            } else {
                break;
            }
            if (cursor.next != null) {
                next_address = cursor.next.currentAddress;
            }
        }
    }


    public static void main(String[] args) {

    }
}
