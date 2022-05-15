import java.util.HashMap;
import java.util.List;

public class Folder extends File {
    private HashMap<String, File> children;

    public Folder(String name, int size) {
        super(name, size, -1);
        children = new HashMap<>();
    }

    public void add(File c){
        children.put(c.getName(), c);
    }
    
    public void remove(File c) {
        children.remove(c.getName());
    }

    public HashMap<String, File> getChildren(){ return children; }

    public boolean pathExists(List<String> path) throws Exception {
        String name = path.get(0);
        File component = children.get(name);
        path.remove(0);

        if(component == null){
            if(path.isEmpty()) 
                return false;
            else 
                throw new Exception("Intermediate folder: " + name + " on path not found");
        }

        if(path.isEmpty()){
            return true;
        }

        else if (component instanceof File){
            throw new Exception(name + " is not a folder");
        }

        return ((Folder)component).pathExists(path);
    }
}