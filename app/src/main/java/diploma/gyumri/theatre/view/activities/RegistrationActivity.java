package diploma.gyumri.theatre.view.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;
import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.constants.Constants;
import diploma.gyumri.theatre.model.User;
import diploma.gyumri.theatre.request.Request;

public class RegistrationActivity extends AppCompatActivity {
    @BindView(R.id.registerLogin)
    EditText login;
    @BindView(R.id.registerPassword)
    EditText password;
    @BindView(R.id.registerConfirmPassword)
    EditText confirmPassword;
    @BindView(R.id.registerName)
    EditText name;
    @BindView(R.id.registerSurName)
    EditText surName;
    @BindView(R.id.registerPhone)
    EditText phone;
    @BindView(R.id.registerEmail)
    EditText email;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        unbinder = ButterKnife.bind(this);
        email.addTextChangedListener(tw);

    }

    private TextWatcher tw = new TextWatcher() {
        public void afterTextChanged(Editable s) {

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // you can check for enter key here
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (validateEmail(s)) {
                email.setError(null);
            } else if (s.equals("") ) {
                email.setError(null);
            } else {
                email.setError("Էլ․ հասցեն սխալ է");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnTouch(R.id.registerBtn)
    boolean onTouch(Button button, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                button.setTextColor(ResourcesCompat.getColor(getResources(), R.color.buttonColor, null));
                button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_pressed, null));
                return false;
            case MotionEvent.ACTION_UP:
                ResourcesCompat.getDrawable(getResources(), R.drawable.button, null);
                button.setTextColor(ResourcesCompat.getColor(getResources(), R.color.textColor, null));
                button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button, null));
                return false;
        }
        return false;
    }

    @OnClick(R.id.registerBtn)
    void onClick() {
        if (fieldsValidation()) {
            if (!validePhone(phone.toString().trim())) {
                phone.setError("Մուտքգրեք ճիշտ հեռախոսահամար");
            }
            if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                password.setError("Գաղտնաբառերը չեն համընկնում");
                confirmPassword.setError("Գաղտնաբառերը չեն համընկնում");
            }
            if (!validateEmail(email.getText().toString().trim())) {
                email.setError("Էլ․ հասցեն սխալ է");
            }
            if (!validePassword(password.getText().toString())) {
                password.setError("Գաղտնաբառը պետք է լինի 8 - 12 նիշ և պարտադիր պարունակի թվեր և սիմվոլներ");
            } else {
                Request.register(this, userCreate());
            }
        } else {
            Toast.makeText(this, "Լրացրեք բոլոր դաշտերը", Toast.LENGTH_SHORT).show();

        }
    }

    private User userCreate() {
        String login = this.login.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String confirmPassword = this.confirmPassword.getText().toString().trim();
        String name = this.name.getText().toString().trim();
        String surName = this.surName.getText().toString().trim();
        String phone = "+374" + this.phone.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        Log.i("TAGUHI", "userCreate: " + surName);
        return new User(login, password, confirmPassword, name, surName, phone, email);
    }

    private boolean fieldsValidation() {
        return !login.getText().toString().trim().equals("")
                && !password.getText().toString().trim().equals("")
                && !confirmPassword.getText().toString().trim().equals("")
                && !name.getText().toString().trim().equals("")
                && !surName.getText().toString().trim().equals("")
                && !phone.getText().toString().trim().equals("")
                && !email.getText().toString().trim().equals("");
    }


    private boolean validateEmail(CharSequence emailStr) {
        Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private boolean validePassword(String password) {
        Matcher matcher = Constants.VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    private boolean validePhone(String phone) {
        Matcher matcher = Constants.VALID_PHONE_REGEX.matcher(phone);
        return matcher.find();
    }
}
