import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Folder extends File {
    private final HashMap<String, File> sub_dir;

    public Folder(String fullPath,String name) {
        super(fullPath,name, 0, -1);
        sub_dir = new HashMap<>();
    }

    public void ls(File root, int level) {

    }

    public File getDir(String name){
        return this.sub_dir.get(name);
    }

    public boolean isExist(String element){
        return this.sub_dir.containsKey(element);
    }

    public void add(File file){
        this.size += file.getSize();
        sub_dir.put(file.getName(), file);
    }

    public void remove(String fileName) {
        sub_dir.remove(fileName);
    }

    public HashMap<String, File> getSub_dir(){ return sub_dir; }
}