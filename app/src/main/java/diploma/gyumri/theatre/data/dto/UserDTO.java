package diploma.gyumri.theatre.data.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Hayk on 05.09.2017.
 */
@Data
public class UserDTO {
    private String password;
    @SerializedName("login")
    private String login;
    @SerializedName("name")
    private String name;
    @SerializedName("surname")
    private String surName;
    @SerializedName("phone")
    private String phone;
    @SerializedName("email")
    private String email;
    @SerializedName("token")
    private String token;
}
