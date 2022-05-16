import java.util.ArrayList;

public class IndexedAllocation extends IAllocator{

    public IndexedAllocation(int N) {
        super(N);
        disk = new ArrayList<>();
        for(int i=0; i<N; i++){
            disk.add(null);
        }
    }

    @Override
    public int allocate(int size) throws Exception {
        if(!sufficientSpaceAvailable(size)){
            throw new Exception("No space available");
        }
        int startIndex = getFreeBlockAddress();
        Block startBlock = new Block(startIndex, null);
        disk.set(startIndex, startBlock);
        size--;
        while (size > 0){
            int nextIndex = getFreeBlockAddress();
            Block nextBlock = new Block(nextIndex, null);
            disk.set(nextIndex, nextBlock);
            startBlock.nextIndex.add(nextIndex);
            size--;
        }
        return startIndex;
    }

    @Override
    public void deallocate(int address, int size) {
        Block current = disk.get(address);
        while (size - 1 > 0){
            int tempIndex = current.nextIndex.size();
            current.nextIndex.remove(tempIndex - 1);
            disk.set(tempIndex, null);
            size--;
        }
        disk.set(address, null);
    }

    @Override
    public void printStats() {

    }

    public static void main(String[] args) throws Exception {
        IndexedAllocation indexedAllocation = new IndexedAllocation(32);
        int startIndex1 = indexedAllocation.allocate(10);
        int startIndex2 = indexedAllocation.allocate(7);
        indexedAllocation.deallocate(startIndex1, 10);
        int startIndex3 = indexedAllocation.allocate(5);
    }
}
