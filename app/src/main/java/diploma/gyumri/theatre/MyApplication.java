package diploma.gyumri.theatre;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import diploma.gyumri.theatre.constants.Constants;
import diploma.gyumri.theatre.net.APIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 7/30/17.
 */

public class MyApplication extends Application {
    private APIService apiService;

    public APIService getApiService() {
        return apiService;
    }

    public void setApiService(APIService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit myRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = myRetrofit.create(APIService.class);
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);
    }
}
