package diploma.gyumri.theatre.model;

import android.widget.EditText;

import butterknife.BindView;
import diploma.gyumri.theatre.R;
import io.realm.RealmObject;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Hayk on 03.08.2017.
 */

@Data
public class User extends RealmObject {
    private String login;
    private String password;
    private String confirmPassword;
    private String name;
    private String surName;
    private String phone;
    private String email;
    private String token;

    public User() {

    }

    public User(String login, String password, String confirmPassword,
                String name, String surName, String phone, String email) {
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
        this.surName = surName;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}






