import java.util.ArrayList;
import java.util.Hashtable;

public class User {
    private String username;
    private String password;
    private Hashtable<String , accessRights> userCapabilities;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        userCapabilities = new Hashtable<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Hashtable<String, accessRights> getUserCapabilities() {
        return userCapabilities;
    }

    public void setUserCapabilities(Hashtable<String, accessRights> userCapabilities) {
        this.userCapabilities = userCapabilities;
    }

    public void grant(String folderPath , String accessRight){
        switch (accessRight){
            case "00":
                return;
            case "01":
                this.userCapabilities.put(folderPath,accessRights.Delete);
                break;
            case "10":
                this.userCapabilities.put(folderPath,accessRights.Create);
                break;
            case "11":
                this.userCapabilities.put(folderPath,accessRights.CreateDelete);
                break;
        }
    }
}
