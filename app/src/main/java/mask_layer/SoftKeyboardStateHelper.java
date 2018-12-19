package mask_layer;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuyue on 2018/12/17.
 * describe:
 */

public class SoftKeyboardStateHelper implements ViewTreeObserver.OnGlobalLayoutListener {
    private final List<SoftKeyboardStateListener> listeners;
    private final View activityRootView;
    private int lastSoftKeyboardHeightInPx;
    private boolean isSoftKeyboardOpened;

    public SoftKeyboardStateHelper(View activityRootView) {
        this(activityRootView, false);
    }

    public SoftKeyboardStateHelper(View activityRootView, boolean isSoftKeyboardOpened) {
        this.listeners = new LinkedList();
        this.activityRootView = activityRootView;
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void onGlobalLayout() {
        Rect r = new Rect();
        this.activityRootView.getWindowVisibleDisplayFrame(r);
        int rootHeight = this.activityRootView.getRootView().getHeight();
        int heightDiff = rootHeight - (r.bottom - r.top);
        if(!this.isSoftKeyboardOpened && heightDiff > rootHeight / 3) {
            this.isSoftKeyboardOpened = true;
            this.notifyOnSoftKeyboardOpened(heightDiff);
        } else if(this.isSoftKeyboardOpened && heightDiff < rootHeight / 3) {
            this.isSoftKeyboardOpened = false;
            this.notifyOnSoftKeyboardClosed();
        }

    }

    public void setIsSoftKeyboardOpened(boolean isSoftKeyboardOpened) {
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
    }

    public boolean isSoftKeyboardOpened() {
        return this.isSoftKeyboardOpened;
    }

    public int getLastSoftKeyboardHeightInPx() {
        return this.lastSoftKeyboardHeightInPx;
    }

    public void addSoftKeyboardStateListener(SoftKeyboardStateHelper.SoftKeyboardStateListener listener) {
        this.listeners.add(listener);
    }

    public void removeSoftKeyboardStateListener(SoftKeyboardStateHelper.SoftKeyboardStateListener listener) {
        this.listeners.remove(listener);
    }

    private void notifyOnSoftKeyboardOpened(int keyboardHeightInPx) {
        this.lastSoftKeyboardHeightInPx = keyboardHeightInPx;
        Iterator var2 = this.listeners.iterator();

        while(var2.hasNext()) {
            SoftKeyboardStateHelper.SoftKeyboardStateListener listener = (SoftKeyboardStateHelper.SoftKeyboardStateListener)var2.next();
            if(listener != null) {
                listener.onSoftKeyboardOpened(keyboardHeightInPx);
            }
        }

    }

    private void notifyOnSoftKeyboardClosed() {
        Iterator var1 = this.listeners.iterator();

        while(var1.hasNext()) {
            SoftKeyboardStateHelper.SoftKeyboardStateListener listener = (SoftKeyboardStateHelper.SoftKeyboardStateListener)var1.next();
            if(listener != null) {
                listener.onSoftKeyboardClosed();
            }
        }

    }

    public interface SoftKeyboardStateListener {
        void onSoftKeyboardOpened(int var1);

        void onSoftKeyboardClosed();
    }
}

