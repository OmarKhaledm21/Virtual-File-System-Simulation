import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Folder extends File {
    private HashMap<String, File> children;

    public Folder(String name) {
        super(name, 0, -1);
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

    public int createFile(List<String> path, int size, int address) {
        if(path.size() == 1){
            String fileName = path.get(0);
            File newF = new File(fileName, size, address);
            add(newF);
            return size;
        }
        
        String name = path.get(0);
        path.remove(0);
        File component = children.get(name);


        int addedSize = ((Folder)component).createFile(path, size, address);
        setSize(getSize() + addedSize);
        
        return addedSize;
    }

    public void createFolder(List<String> path) {
        if(path.size() == 1){
            String fileName = path.get(0);
            File newF = new Folder(fileName);
            add(newF);
            return;
        }

        String name = path.get(0);
        path.remove(0);
        File component = children.get(name);


        ((Folder)component).createFolder(path);
    }

    public void ls(File root, int level) {
        for(int i = 0; i < level; i++) 
            System.out.print("  ");
        System.out.println(root.getName());

        if(root instanceof Folder){
            Folder folder = (Folder) root;
            for(var child: folder.getChildren().entrySet()){
                ls(child.getValue(), level + 1);
            }
        }
    }

    public static void main(String[] args) {
        Folder root = new Folder("root");
        List<String> path = new ArrayList<>();
        path.add("src");
        root.createFolder(new ArrayList<>(path));
        // root.ls(root, 1);
        path.add("ayoo");
        root.createFolder(new ArrayList<>(path));
        // root.ls(root, 1);
        path.set(1,"thing.java");
        root.createFile(new ArrayList<>(path), 100, 2);
        root.ls(root, 0);
    }
}