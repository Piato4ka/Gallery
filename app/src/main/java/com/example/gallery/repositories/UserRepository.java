package com.example.gallery.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.gallery.DAO.UserDAO;
import com.example.gallery.GalleryRoomDatabase;
import com.example.gallery.entities.User;

import java.util.List;

public class UserRepository {

    private UserDAO userDAO;
    private LiveData<List<User>> AllUsers;


    public UserRepository(Application application) {
        GalleryRoomDatabase db = GalleryRoomDatabase.getDatabase(application);
        userDAO = db.userDAO();
        AllUsers = userDAO.getAllUsers();
    }


    public LiveData<List<User>> getAllUsers() {
        return AllUsers;
    }

    public void insert(User user) {
        new UserRepository.AsyncTaskInsert(userDAO).execute(user);
    }

    public User getAuthorisedUser(String userName) {
        return userDAO.getUserName(userName);
    }

    private static class AsyncTaskInsert extends AsyncTask<User, Void, Void> {
        private UserDAO mAsyncTaskDao;

        AsyncTaskInsert(UserDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
