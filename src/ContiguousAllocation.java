import java.io.FileWriter;
import java.util.ArrayList;

public class ContiguousAllocation extends IAllocator {
    static class Block implements IBlock {
        int startIndex;
        int length;

        public Block(int startIndex, int length) {
            this.startIndex = startIndex;
            this.length = length;
        }

        @Override
        public String toString() {
            return "start index: +" + startIndex + " length: " + length;
        }
    }

    public ContiguousAllocation(int N) {
        super(N);
    }

    @Override
    public Object[] allocate(int size) throws Exception {
        int startIndex = -1;
        int free_contig_blocks = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) == null) {
                int start = i;
                for (; i < disk.size(); i++) {
                    if (disk.get(i) != null) {
                        break;
                    } else {
                        free_contig_blocks++;
                    }
                    if (free_contig_blocks == size) {
                        break;
                    }
                }
                if (free_contig_blocks == size) {
                    startIndex = start;
                    break;
                } else {
                    free_contig_blocks = 0;
                }
            }
        }
        if(startIndex == -1){
            throw new Exception("No contiguous space available");
        }
        ArrayList<Block> blocks = new ArrayList<>();
        Block block = new Block(startIndex, size);
        int tempSize = size;
        int tempIndex = startIndex;
        for (int i = startIndex; i < startIndex + size; i++) {
            disk.set(i, block);
            blocks.add(new Block(tempIndex,tempSize));
            tempIndex++;
            tempSize--;
        }

        freeBlocks -= size;
        allocatedBlocks += size;

        return new Object[]{startIndex,blocks};
    }


    @Override
    public void deallocate(int address, int size) {
        for(int i=address; i<address+size; i++){
            disk.set(i,null);
        }
        freeBlocks += size;
        allocatedBlocks -= size;

    }

    @Override
    public void writeUtil(Object[] obj) {
        try {
            FileWriter writer = new FileWriter(Utils.fileLocation, true);
            writer.write(obj[0].toString() + " ");
            ArrayList<ContiguousAllocation.Block> blocks = (ArrayList<ContiguousAllocation.Block>) obj[1];
            writer.write(blocks.get(0).startIndex + " " + blocks.get(0).length + "\n");
            for (ContiguousAllocation.Block block : blocks) {
                writer.write(block.startIndex + " ");
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
        int length = blocks.size();
        for(int i=0; i<blocks.size(); i++){
            int index = blocks.get(i);
            Block b = new Block(index,length);
            disk.set(index,b);
            fileBlocks.add(b);
            length--;
        }
        return fileBlocks;
    }

    public static void main(String[] args) throws Exception {
        ContiguousAllocation contiguousAllocation = new ContiguousAllocation(10);
        Object[] obj = contiguousAllocation.allocate(3);
        contiguousAllocation.allocate(3);
        contiguousAllocation.allocate(3);

        ArrayList<Block> blocks = (ArrayList<Block>) obj[1];
        contiguousAllocation.deallocate((Integer) obj[0],blocks.get(0).length);

        contiguousAllocation.allocate(4);

    }
}
