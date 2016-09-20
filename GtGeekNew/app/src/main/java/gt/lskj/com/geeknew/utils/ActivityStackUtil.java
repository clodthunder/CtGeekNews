package gt.lskj.com.geeknew.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Home on 16/9/18.
 */
public class ActivityStackUtil {
    //管理Activity
    private static Stack<Activity> mActivityStack = null;

    public static Stack<Activity> getmActivityStack() {
        if (mActivityStack == null) {
            synchronized (ActivityStackUtil.class) {
                mActivityStack = new Stack<>();
            }
        }
        return mActivityStack;
    }

    //add Activity 添加Activity到栈
    public void pushActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    //get current Activity 获取当前Activity（栈中最后一个压入的）
    public Activity currentActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    //结束当前Activity（栈中最后一个压入的）
    public void finishActivity() {
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    //结束指定的Activity
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    //结束指定类名的Activity
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    //结束所有Activity
    public static void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }
}
