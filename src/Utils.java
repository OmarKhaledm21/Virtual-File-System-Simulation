import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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





}
