import java.io.FileWriter;
import java.util.ArrayList;

public class IndexedAllocation extends IAllocator {
    static class Block implements IBlock {

        int startIndex;
        ArrayList<Integer> nextIndex;

        public Block(int startIndex) {
            this.startIndex = startIndex;
            nextIndex = new ArrayList<>();
        }

        @Override
        public String toString() {
            StringBuilder indices = new StringBuilder();
            for (Integer integer : nextIndex){
                indices.append(String.valueOf(integer)).append(" ");
            }

            return indices.toString();
        }
    }

    public IndexedAllocation(int N) {
        super(N);
    }

    @Override
    public Object[] allocate(int size) throws Exception {
        ArrayList<Block> blocks = new ArrayList<>();
        if (!sufficientSpaceAvailable(size)) {
            throw new Exception("No space available");
        }
        freeBlocks -= size;
        allocatedBlocks += size;
        int startIndex = getFreeBlockAddress();
        Block startBlock = new Block(startIndex);
        disk.set(startIndex, startBlock);
        blocks.add(startBlock);
        size--;
        while (size > 0) {
            int nextIndex = getFreeBlockAddress();
            Block nextBlock = new Block(startIndex);
            disk.set(nextIndex, nextBlock);
            startBlock.nextIndex.add(nextIndex);
            blocks.add(nextBlock);
            size--;
        }
        return new Object[]{startIndex,blocks};
    }

    @Override
    public void deallocate(int address, int size) {
        Block current = (Block) disk.get(address);
        freeBlocks += size;
        allocatedBlocks -= size;
        while (size - 1 > 0) {
            int tempIndex = current.nextIndex.size();
            current.nextIndex.remove(tempIndex - 1);
            disk.set(tempIndex, null);
            size--;
        }
        disk.set(address, null);
    }

    @Override
    public void writeUtil(Object[] obj) {
        try {

            FileWriter writer = new FileWriter(Utils.fileLocation, true);
            writer.write(obj[0].toString() + " ");
            ArrayList<IndexedAllocation.Block> blocks = (ArrayList<IndexedAllocation.Block>) obj[1];
            writer.write(blocks.get(0).startIndex + "\n");
            StringBuilder indices = new StringBuilder();
            indices.append(blocks.get(0).startIndex).append(" ");
            for (Integer integer : blocks.get(0).nextIndex){
                indices.append(String.valueOf(integer)).append(" ");
            }
            writer.write(indices.toString());
            writer.write("\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    public static void main(String[] args) throws Exception {
//        IndexedAllocation indexedAllocation = new IndexedAllocation(32);
//        int startIndex1 = indexedAllocation.allocate(10);
//        int startIndex2 = indexedAllocation.allocate(7);
//        indexedAllocation.deallocate(startIndex1, 10);
//        int startIndex3 = indexedAllocation.allocate(5);
//    }
}
