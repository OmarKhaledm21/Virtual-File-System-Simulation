import java.util.Hashtable;

public class UserManager {
    private static UserManager currentInstance;

    private static User currentUser;
    private final Hashtable<String, User> users;

    private UserManager() {
        users = new Hashtable<>();
        User admin = new User("admin", "admin");
        users.put("admin", admin);
        currentUser = admin;
        this.grantPermission(admin.getUsername(), "root/", "11");

    }

    public static UserManager getInstance() {
        if (currentInstance == null) {
            currentInstance = new UserManager();
        }
        return currentInstance;
    }

    public Hashtable<String, User> getUsers() {
        return users;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public void createUser(String username, String password) {
        if(currentUser.getUsername().equals("admin")){
            User user = new User(username, password);
            this.users.put(username, user);
        }else{
            System.out.println("Admin only can create users!");
        }
    }

    public void deleteUser(String username){
        if(currentUser.getUsername().equals("admin")){
            this.users.remove(username);
        }
    }

    public User login(String username, String password) {
        currentUser = null;
        User user = users.get(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                currentUser = user;
            } else {
                System.out.println("A7a 7aramy!!!!!!!");
            }
        }
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    public void grantPermission(String username, String folderPath, String accessRight) {
        if (currentUser.getUsername().equals("admin")) {
            User user = users.get(username);
            if (user == null) {
                return;
            }
            user.grant(folderPath, accessRight);
        } else {
            System.out.println("A7a 7aramy tany!");
        }
    }

    public void tellUser(){
        System.out.println(currentUser.getUsername());
    }

}
