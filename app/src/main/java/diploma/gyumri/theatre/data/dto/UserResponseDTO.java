package diploma.gyumri.theatre.data.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by root on 8/24/17.
 */


@Data
public class UserResponseDTO {
    private String login;
    private String password;
    @SerializedName("token")
    private String token;
}
