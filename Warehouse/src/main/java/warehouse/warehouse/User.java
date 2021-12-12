package warehouse.warehouse;

public class User {
    private Integer user_id;
    private String user_name;
    private String user_password;
    private String user_firstname;
    private String user_lastname;

    public User() {}

    public User(Integer user_id, String user_name, String user_password, String user_firstname, String user_lastname) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_firstname = user_firstname;
        this.user_lastname = user_lastname;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_firstname() {
        return user_firstname;
    }

    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }
}