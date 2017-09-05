package diploma.gyumri.theatre.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.constants.Constants;
import diploma.gyumri.theatre.data.dto.UserDTO;
import diploma.gyumri.theatre.data.dto.UserResponseDTO;
import diploma.gyumri.theatre.model.User;
import diploma.gyumri.theatre.request.Request;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.loginUser)
    EditText loginUser;
    @BindView(R.id.passwordUser)
    EditText passwordUser;
    private Unbinder unbinder;

    public void aaa() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.signInBtn)
    void onClick() {
//        if (validUser()) {
//            if (validPassword(passwordUser.getText().toString())){
        UserDTO user = new UserDTO();
        user.setLogin(loginUser.getText().toString());
        user.setPassword(passwordUser.getText().toString());
        Request.login(this, user);

//            }
//        }
    }

    public void login(String token) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    private boolean validUser() {
        return loginUser.getText().toString().trim().equals("")
                && passwordUser.getText().toString().trim().equals("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private boolean validPassword(String password) {
        Matcher matcher = Constants.VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }
}
