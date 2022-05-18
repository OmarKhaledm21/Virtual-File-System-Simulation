import java.util.HashMap;
import java.util.Hashtable;

public class Folder extends AbstractFile {
    private final HashMap<String, AbstractFile> sub_dir;

    public Folder(String fullPath, String name) {
        super(fullPath, name, 0, -1);
        sub_dir = new HashMap<>();
    }

    public void ls() {
        for (var key : sub_dir.keySet()) {
            System.out.print(sub_dir.get(key).toString() + " ");
        }
        System.out.println();
    }


    public AbstractFile getDir(String name) {
        return this.sub_dir.get(name);
    }

    public boolean isExist(String element) {
        return this.sub_dir.containsKey(element);
    }

    public void add(File file) {
        this.fileSize += file.getFileSize();
        sub_dir.put(file.getFileName(), file);
    }

    public void addFolder(Folder folder) {
        sub_dir.put(folder.getFileName(), folder);
    }

    public void remove(String fileName) {
        sub_dir.remove(fileName);
    }

    public HashMap<Integer,Integer> deleteDirectory() {
        HashMap<Integer,Integer> size_address_pair = new HashMap<>();
        for (var fileName : sub_dir.keySet()) {
            AbstractFile file = sub_dir.get(fileName);
            if(file instanceof File) {
                size_address_pair.put(file.getAddress(),file.getFileSize());
            }else if (file instanceof Folder){
                HashMap<Integer, Integer> subDir_size_address = ((Folder) file).deleteDirectory();
                for (Integer key : subDir_size_address.keySet()){
                    size_address_pair.put(key,subDir_size_address.get(key));
                }
            }
        }

        sub_dir.clear();
        return size_address_pair;
    }

    public HashMap<String, AbstractFile> getSub_dir() {
        return sub_dir;
    }
}