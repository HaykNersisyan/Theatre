package diploma.gyumri.theatre.model;

import android.util.Log;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by Hayk on 03.08.2017.
 */

@Data
public class User extends RealmObject {
    private String login;
    private String password;
    private String confirmPassword;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String token;

    public User() {

    }

    public User(String login, String password, String confirmPassword,
                String name, String surname, String phone, String email) {
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        Log.i("TAGUHI", "User: " + this.surname);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}






