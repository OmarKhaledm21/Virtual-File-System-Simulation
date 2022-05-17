import java.util.ArrayList;

public abstract class IAllocator {
    private int diskSpace;
    private ArrayList<Integer> disk;

    public IAllocator(int diskSpace) {
        this.diskSpace = diskSpace;
    }

    public abstract void allocate();
    public abstract void deallocate();
}
