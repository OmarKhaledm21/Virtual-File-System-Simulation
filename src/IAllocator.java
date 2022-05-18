import java.util.ArrayList;

public abstract class IAllocator {
    interface IBlock {};
    protected int freeBlocks;
    protected int allocatedBlocks;
    protected ArrayList<IBlock> disk;


    public IAllocator(int N){
        freeBlocks = N;
        disk = new ArrayList<>();
        for(int i=0; i<N; i++){
            disk.add(null);
        }
    }

    public boolean sufficientSpaceAvailable(int size){
        return (freeBlocks >= size);
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
    public abstract void displayDiskStatus();
}
