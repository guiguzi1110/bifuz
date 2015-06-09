package com.example.asynctask;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;



//@ReportsCrashes(mailTo="stefania.popescu26@yahoo.com",
//				mode = ReportingInteractionMode.TOAST,
//				forceCloseDialogAfterToast = false, // optional, default false
//				resToastText = R.string.your_app_has_failed)
//


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }
}