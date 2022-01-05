package com.example.gallery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.gallery.AuthorizationHelper;
import com.example.gallery.R;
import com.example.gallery.Toasts;
import com.example.gallery.entities.User;
import com.example.gallery.models.UserViewModel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private EditText loginInput;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginInput = findViewById(R.id.login_input);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

    }

    public void onLogin(View view) {
        String login = loginInput.getText().toString().trim();
        if (checkIfRegistered(login)) {
            Intent intent = new Intent(this, MainActivity.class);
            AuthorizationHelper authorizationHelper = new AuthorizationHelper(login);
            startActivity(intent);
            finish();
        } else {
            Toasts.showShort(this, R.string.no_registered_login);
        }
    }

    public void onRegister(View view) {
        String login = loginInput.getText().toString();
        if (checkIfRegistered(login)) {
            Toasts.showShort(this, R.string.has_such_login);
        } else {
            User user = new User(login);
            userViewModel.insert(user);
            Toasts.showShort(this, R.string.registered_success);
        }

    }

    private boolean checkIfRegistered(String login) {

        final User[] user = new User[1];
        user[0] = userViewModel.getAuthorisedUser(login);
        if (user[0] != null)
            return true;
        else
            return false;
    }
}