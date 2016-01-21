package com.hh.ehh.push;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;


public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /****Push Notifications*****/
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        // Add your initialization code here
        Parse.initialize(this, "l3gHQ5XzYri2jGFACJkZKiupEHe3xDF2MgoRbrz4", "rppSTdZZ75f7rrY56ljsYtQsqivqg2XaxfH1hp3P");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}