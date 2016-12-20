package cn.com.common.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

/**
 * Created by hellohome on 2016/12/20.
 */

public class MyApp  extends Application{
    private static MyApp Application;

    @Override
    public void onCreate() {
        super.onCreate();
        Application = this;
    }

    public static Context getAppContext() {
        return Application;
    }
    public static Resources getAppResources() {
        return Application.getResources();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 分包
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
