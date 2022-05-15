public abstract class IAllocator {
    private int freeBlocks;
    private int allocatedBlocks;

    public IAllocator(int N){
        freeBlocks = N;
    }
    
    public boolean sufficientSpaceAvailable(int space){
        return freeBlocks >= space;
    }

    public abstract int allocate();
    public abstract void deallocate();
    public abstract void printStats();
}
