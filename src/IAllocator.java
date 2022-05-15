public abstract class IAllocator {
    private int freeBlocks;
    private int allocatedBlocks;

    public IAllocator(int N){
        freeBlocks = N;
    }
    
    public boolean sufficientSpaceAvailable(int space){
        return freeBlocks >= space;
    }

    public abstract int allocate(int size) throws Exception;
    public abstract void deallocate(int address,int size);
    public abstract void printStats();
}
