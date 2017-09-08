package diploma.gyumri.theatre.request;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import diploma.gyumri.theatre.MyApplication;
import diploma.gyumri.theatre.constants.Constants;
import diploma.gyumri.theatre.data.dto.EventsDTO;
import diploma.gyumri.theatre.data.dto.UserDTO;
import diploma.gyumri.theatre.data.mappers.EventsMapper;
import diploma.gyumri.theatre.data.mappers.UserMapper;
import diploma.gyumri.theatre.model.Event;
import diploma.gyumri.theatre.model.User;
import diploma.gyumri.theatre.view.activities.LoginActivity;
import diploma.gyumri.theatre.view.activities.MainActivity;
import diploma.gyumri.theatre.view.activities.RegistrationActivity;
import diploma.gyumri.theatre.view.fragments.MainFragment;
import io.realm.Realm;
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


    public static void login(final LoginActivity activity, UserDTO userDTO) {
        Log.i("TAG", "mtneluc: ");

        Call<UserDTO> call = ((MyApplication) activity.getApplication()).getApiService().login(userDTO);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.code() != 400) {
                    User user = UserMapper.toUser(response.body());
                    saveUser(user);
                    activity.aaa();
                } else {
                    Toast.makeText(activity, "Մուտքանունը կամ գաղտնաբառը սխալ է", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.i("TAG", "onFail: ");

            }
        });
    }

    public static void register(final RegistrationActivity activity, User user) {
        Call<User> call = ((MyApplication) activity.getApplication()).getApiService().register(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 400) {
                    Toast.makeText(activity, "Ստուգեք տվյալները", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Դուք հաջողությամբ գրանցվել եք", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(activity, "Ստուգեք ինտերնետը", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private static void saveUser(final User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User rUser = realm.createObject(User.class);
                rUser.setLogin(user.getLogin());
                rUser.setSurname(user.getSurname());
                rUser.setName(user.getName());
                rUser.setToken(user.getToken());
                rUser.setEmail(user.getEmail());
                rUser.setPhone(user.getPhone());
                Constants.USER = user;
            }
        });
    }
}
