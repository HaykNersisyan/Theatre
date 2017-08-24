package diploma.gyumri.theatre.data.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by root on 8/24/17.
 */


@Data
public class UserResponseDTO {

    @SerializedName("token")
    String token;
    String login;
    String password;

}
