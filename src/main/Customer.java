package main;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    ArrayList<Long> objectId;

    public ArrayList<Long> getObjectId() {
        return objectId;
    }

    public void setObjectId(ArrayList<Long> objectId) {
        this.objectId = objectId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    private String login;
    private String password;
    public Customer(String login, String password, ArrayList<Long> objectId) {
        this.login = login;
        this.password = password;
        this.objectId = objectId;
    }

}
