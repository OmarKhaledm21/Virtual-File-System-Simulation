import java.util.Hashtable;

public class UserManager {
    private static UserManager currentInstance;

    private User currentUser;
    private Hashtable<String, User> users;

    private UserManager() {
        this.currentUser = null;
        users = new Hashtable<>();
        users.put("admin", new User("admin", "admin"));
    }

    public UserManager getInstance() {
        if (currentInstance == null) {
            currentInstance = new UserManager();
        }
        return currentInstance;
    }

    public void createUser(String username, String password) {
        User user = new User(username, password);
        this.users.put(username, user);
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
            if (user == null){
                return;
            }
            user.grant(folderPath, accessRight);
        } else {
            System.out.println("A7a 7aramy tany!");
        }
    }

}
