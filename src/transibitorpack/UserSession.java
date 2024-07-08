package transibitorpack;

public class UserSession {
    private static DataUser currentUser;

    public static void login(DataUser user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static DataUser getCurrentUser() {
        return currentUser;
    }
}
