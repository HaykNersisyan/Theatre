package diploma.gyumri.theatre.request;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

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


    public static void login(final LoginActivity activity, Object user) {
        Log.i("TAG", "mtneluc: ");

        Call<Object> call = ((MyApplication) activity.getApplication()).getApiService().login(user);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                try {
                    Log.i("TAG", "onResponse: "+((JSONObject) response.body()).getString("token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.i("TAG", "onFail: ");

            }
        });
    }
}
