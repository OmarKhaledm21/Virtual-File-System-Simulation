import java.util.ArrayList;

class Block{
    int currentAddress;
    Block next;
    ArrayList<Integer> nextIndex;

    public Block(int currentAddress, Block next) {
        this.currentAddress = currentAddress;
        this.next = next;
        nextIndex = new ArrayList<>();
    }
}

public class LinkedAllocation extends IAllocator{
//    private ArrayList<Block> disk;
    public LinkedAllocation(int N) {
        super(N);
        disk = new ArrayList<>();
        for(int i=0; i<N; i++){
            disk.add(null);
        }
    }

    @Override
    public int allocate(int size) throws Exception {
        if(! sufficientSpaceAvailable(size)){
            throw new Exception("No space available");
        }
        int start_address = getFreeBlockAddress();
        Block start = new Block(start_address,null);
        disk.set(start_address,start);
        while (size >0){
            int next_address = getFreeBlockAddress();
            Block next_block = new Block(next_address,null);
            start.next = next_block;
            start = next_block;
            disk.set(next_address,next_block);
            size--;
        }
        freeBlocks -= size;
        allocatedBlocks += size;
        return start_address;
    }

    @Override
    public void deallocate(int start_address, int size) {
        Block cursor = null;
        int next_address = start_address;
        while (size>0){
            int current_address = next_address;
            cursor = disk.get(current_address);
            if(cursor!=null) {
                next_address = cursor.next.currentAddress;
            }else{
                break;
            }
            disk.set(current_address,null);
            size--;
        }
        freeBlocks += size;
        allocatedBlocks -= size;
    }


    @Override
    public void printStats() {

    }

//    public int getFreeBlockAddress(){
//        for(int i=0; i<disk.size(); i++){
//            if(disk.get(i) == null){
//                return i;
//            }
//        }
//        return -1;
//    }

    public static void main(String[] args) {}
}
