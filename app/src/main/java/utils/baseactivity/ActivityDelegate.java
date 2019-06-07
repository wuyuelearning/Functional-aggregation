package utils.baseactivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * A delegate for Activities.
 * The following methods must be invoked from the corresponding Activities lifecycle methods:
 */
public abstract class ActivityDelegate {

    public static ActivityDelegate create(Dialog dialog) {
        return create(dialog.getContext());
    }

    public static ActivityDelegate create(Context context) {
        return new ActivityDelegateImpl(context);
    }

    ActivityDelegate() {
    }

    /**
     * This method must be called from {@link Activity#onCreate(Bundle)}.
     * This method internally creates the presenter and attaches the view to it.
     */
    public abstract void onCreate(Bundle bundle);

    /**
     * This method must be called from {@link Activity#onStart()}
     */
    public abstract void onStart();

    /**
     * This method must be called from {@link Activity#onResume()}
     */
    public abstract void onResume();

    /**
     * This method must be called from {@link Activity#onPause()}
     */
    public abstract void onPause();

    /**
     * This method must be called from {@link Activity#onStop()}
     */
    public abstract void onStop();

    /**
     * This method must be called from {@link Activity#onRestart()}
     */
    public abstract void onRestart();

    /**
     * This method must be called from {@link Activity#onDestroy()}}.
     * This method internally detaches the view from presenter
     */
    public abstract void onDestroy();

    /**
     * This method must be called from {@link Activity#onContentChanged()}
     */
    public abstract void onContentChanged();

    /**
     * This method must be called from {@link Activity#onSaveInstanceState(Bundle)}
     */
    public abstract void onSaveInstanceState(Bundle outState);

    /**
     * This method must be called from {@link Activity#onRestoreInstanceState(Bundle)}
     */
    public abstract void onRestoreInstanceState(Bundle savedInstanceState);

    /**
     * This method must be called from {@link Activity#onPostCreate(Bundle)}
     */
    public abstract void onPostCreate(Bundle savedInstanceState);

    /**
     * This method must be called from {@link Activity#onBackPressed()}
     */
    public abstract void onBackPressed();
}
