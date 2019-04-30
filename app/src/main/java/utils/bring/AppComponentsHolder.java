package utils.bring;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>Author:MrIcefox
 * <p>Email:extremetsa@gmail.com
 * <p>Description: 运行中Activities以及Application容器
 * <p>Date:2017/4/19
 */

public class AppComponentsHolder implements Application.ActivityLifecycleCallbacks {
    private Application application;
    private AtomicBoolean initialized = new AtomicBoolean();
    private List<WeakReference<Activity>> runningActivities = new LinkedList<>();

    private static class InstanceHolder {
        private static AppComponentsHolder INSTANCE = new AppComponentsHolder();
    }

    public static AppComponentsHolder inst() {
        return InstanceHolder.INSTANCE;
    }

    public AppComponentsHolder init(Application application) {
        if (application == null) {
            throw new IllegalArgumentException("Application should not be null");
        }
        if (initialized.get()) {
            L.e("AppComponentsHolder was initialized!");
        } else {
            this.application = application;
            this.application.registerActivityLifecycleCallbacks(this);
            initialized.set(true);
        }
        return this;
    }

    public Application getApplication() {
        if (this.application == null) {
            throw new NullPointerException("Invoke init before getApplication");
        }
        return this.application;
    }

    public List<WeakReference<Activity>> getRunningActivities() {
        return this.runningActivities;
    }

    public void removeFromRunningActivities(Activity activity) {
        if (activity == null) {
            return;
        }
        for (Iterator<WeakReference<Activity>> itr = runningActivities.iterator(); itr.hasNext(); ) {
            WeakReference<Activity> ref = itr.next();
            if (ref.get() == activity) {
                itr.remove();
            }
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        runningActivities.add(new WeakReference<>(activity));
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        removeFromRunningActivities(activity);
    }
}
