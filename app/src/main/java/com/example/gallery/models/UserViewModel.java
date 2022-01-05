package com.example.gallery.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gallery.repositories.UserRepository;
import com.example.gallery.entities.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> AllUsers;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        AllUsers = userRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return AllUsers;
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public User getAuthorisedUser(String userName) {
        return userRepository.getAuthorisedUser(userName);
    }
}
