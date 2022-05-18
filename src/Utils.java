import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    static String fileLocation = new File("DiskStructure.vfs").getAbsolutePath();

    static public ArrayList<String> getPath(String path){
        return new ArrayList<>(List.of(path.split("/")));
    }
    static public String getFileName(String path){
        ArrayList<String> elements = new ArrayList(List.of(path.split("/")));
        return elements.get(elements.size()-1);
    }


    static  public Object[] readUtil(){


        return null;
    }

    static  public Object[] writeUtil(){


        return null;
    }


}
