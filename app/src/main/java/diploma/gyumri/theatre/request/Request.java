package diploma.gyumri.theatre.request;

import android.util.Log;

import java.util.List;

import diploma.gyumri.theatre.MyApplication;
import diploma.gyumri.theatre.data.dto.EventsDTO;
import diploma.gyumri.theatre.data.dto.UserResponseDTO;
import diploma.gyumri.theatre.data.mappers.EventsMapper;
import diploma.gyumri.theatre.model.Event;
import diploma.gyumri.theatre.view.activities.LoginActivity;
import diploma.gyumri.theatre.view.activities.MainActivity;
import diploma.gyumri.theatre.view.fragments.MainFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 7/30/17.
 */

public class Request {
    public static void requestEvents(final MainActivity activity, final MainFragment fragment) {
        ((MyApplication) activity.getApplication()).getApiService().getEvents().enqueue(new Callback<EventsDTO>() {
            @Override
            public void onResponse(Call<EventsDTO> call, Response<EventsDTO> response) {
                EventsDTO eventsDTO = response.body();
                List<Event> model = EventsMapper.toEvents(eventsDTO);
                Log.i("TAG", "onResponse: " + eventsDTO.getEvents().get(0).getName());
                fragment.listInit(model);
            }

            @Override
            public void onFailure(Call<EventsDTO> call, Throwable t) {

            }
        });
    }


    public static void login(final LoginActivity activity, UserResponseDTO user) {
        Call<UserResponseDTO> call = ((MyApplication) activity.getApplication()).getApiService().login(user);
        call.enqueue(new Callback<UserResponseDTO>() {
            @Override
            public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {
                response.raw();
                Log.i("TAG", "onResponse: " + response.body().getToken());
            }

            @Override
            public void onFailure(Call<UserResponseDTO> call, Throwable t) {

            }
        });
//        ((MyApplication) activity.getApplication()).getApiService().login(user).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse( Response<String> response) {
//                Log.i("TAG", "onResponse: " + response.body());
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.i("TAG", "onResponse: fail" + call.toString());
//
//            }
//        });
    }
}
