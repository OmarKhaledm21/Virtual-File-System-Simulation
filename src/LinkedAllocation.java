import java.io.FileWriter;
import java.util.ArrayList;


public class LinkedAllocation extends IAllocator {
    static class Block implements IBlock {
        int currentAddress;
        Block next;

        public Block(int currentAddress, Block next) {
            this.currentAddress = currentAddress;
            this.next = next;
        }

        @Override
        public String toString() {
            return String.valueOf(currentAddress);
        }
    }

    public LinkedAllocation(int N) {
        super(N);
    }

    @Override
    public Object[] allocate(int size) throws Exception {
        ArrayList<Block> blocks = new ArrayList<>();
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
            blocks.add(start);
            start = next_block;
            disk.set(next_address, next_block);
            size--;
        }
        blocks.add(start);
        return new Object[]{start_address, blocks};
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

    @Override
    public void writeUtil(Object[] obj) {
        try {
            FileWriter writer = new FileWriter(Utils.fileLocation, true);
            writer.write(obj[0].toString() + " ");
            ArrayList<Block> blocks = (ArrayList<Block>) obj[1];
            writer.write(blocks.get(0).toString() + " " + blocks.get(blocks.size() - 1).toString() + "\n");

            for (Block block : blocks) {
                writer.write(block.toString() + " ");
            }
            writer.write("\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<IBlock> manualAllocate(ArrayList<Integer> blocks) {
        ArrayList<IBlock> fileBlocks = new ArrayList<>();
        Block startBlock = new Block(blocks.get(0), null);
        Block prev = startBlock;
        for (int i = 1; i < blocks.size() - 1; i++) {
            Block curr = new Block(blocks.get(i), null);
            prev.next = curr;
            fileBlocks.add(prev);
            prev = curr;
        }
        prev.next = new Block(blocks.get(blocks.size() - 1), null);
        fileBlocks.add(prev);
        prev = prev.next;
        fileBlocks.add(prev);
        return fileBlocks;
    }

}
