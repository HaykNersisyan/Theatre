package diploma.gyumri.theatre.net;

import org.json.JSONObject;

import diploma.gyumri.theatre.constants.Constants;
import diploma.gyumri.theatre.data.dto.EventsDTO;
import diploma.gyumri.theatre.data.dto.UserDTO;
import diploma.gyumri.theatre.data.dto.UserResponseDTO;
import diploma.gyumri.theatre.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by root on 7/30/17.
 */

public interface APIService {
    @GET(Constants.BASE_URL + "events/list")
    Call<EventsDTO> getEvents();

    @POST(Constants.BASE_URL + "login")
    Call<UserDTO> login(@Body UserDTO user);

    @POST(Constants.BASE_URL + "register")
    Call<User> register(@Body User user);
}
