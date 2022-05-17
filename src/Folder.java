import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Folder extends AbstractFile {
    private final HashMap<String, AbstractFile> sub_dir;

    public Folder(String fullPath,String name) {
        super(fullPath,name, 0, -1);
        sub_dir = new HashMap<>();
    }

    public void ls(File root, int level) {

    }

    public AbstractFile getDir(String name){
        return this.sub_dir.get(name);
    }

    public boolean isExist(String element){
        return this.sub_dir.containsKey(element);
    }

    public void add(File file){
        this.size += file.getSize();
        sub_dir.put(file.getName(), file);
    }

    public void addFolder(Folder folder){
        sub_dir.put(folder.getName(), folder);
    }

    public void remove(String fileName) {
        sub_dir.remove(fileName);
    }

    public void deleteDirectory (){sub_dir.clear();} //TODO lesa 3aleha Sho8l

    public HashMap<String, AbstractFile> getSub_dir(){ return sub_dir; }
}