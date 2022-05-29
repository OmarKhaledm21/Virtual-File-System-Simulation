import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    static String fileLocation = new File("DiskStructure.vfs").getAbsolutePath();
    static String permissionsFileLocation = new File("capabilities.txt").getAbsolutePath();
    static String usersFileLocation = new File("user.txt").getAbsolutePath();

    static public ArrayList<String> getPath(String path){
        return new ArrayList<>(List.of(path.split("/")));
    }
    static public String getFileName(String path){
        ArrayList<String> elements = new ArrayList(List.of(path.split("/")));
        return elements.get(elements.size()-1);
    }
    static public ArrayList<Integer> getAddressesFromFile(String addresses){
        ArrayList<Integer> int_addresses = new ArrayList<>();
        for(var elem :(addresses.split(" "))){
            int_addresses.add(Integer.parseInt(elem));
        }
        System.out.println(int_addresses);
        return int_addresses;
    }





}
