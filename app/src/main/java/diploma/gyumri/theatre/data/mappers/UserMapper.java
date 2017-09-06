package diploma.gyumri.theatre.data.mappers;

import diploma.gyumri.theatre.data.dto.UserDTO;
import diploma.gyumri.theatre.model.User;

/**
 * Created by Hayk on 05.09.2017.
 */

public class UserMapper {
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurName());
        user.setEmail(userDTO.getEmail());
        user.setLogin(userDTO.getLogin());
        user.setPhone(userDTO.getPhone());
        user.setToken(userDTO.getToken());
        return user;

    }
}
