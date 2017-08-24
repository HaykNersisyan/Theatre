package diploma.gyumri.theatre.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.constants.Constants;
import diploma.gyumri.theatre.data.dto.UserResponseDTO;
import diploma.gyumri.theatre.request.Request;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.loginUser)
    EditText loginUser;
    @BindView(R.id.passwordUser)
    EditText passwordUser;
    private Unbinder unbinder;

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
        UserResponseDTO user = new UserResponseDTO();
        user.setLogin(loginUser.getText().toString());
        user.setPassword(passwordUser.getText().toString());
        Request.login(this, user);
//            }
//        }
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
