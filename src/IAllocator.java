import java.util.ArrayList;

public abstract class IAllocator {
    private int freeBlocks;
    private int allocatedBlocks;
    public ArrayList<Block> disk;


    public IAllocator(int N){
        freeBlocks = N;
    }
    
    public boolean sufficientSpaceAvailable(int space){
        return freeBlocks >= space;
    }

    public int getFreeBlockAddress(){
        for(int i=0; i<disk.size(); i++){
            if(disk.get(i) == null){
                return i;
            }
        }
        return -1;
    }

    public abstract int allocate(int size) throws Exception;
    public abstract void deallocate(int address,int size);
    public abstract void printStats();
}
