import java.util.ArrayList;
import java.util.List;

public class Utils {
    static public ArrayList<String> getPath(String path){
        return new ArrayList<>(List.of(path.split("/")));
    }
    static public String getFileName(String path){
        ArrayList<String> elements = new ArrayList(List.of(path.split("/")));
        return elements.get(elements.size()-1);
    }
}
