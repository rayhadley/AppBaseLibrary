
package com.stardust.app.base.common;

import android.app.Activity;

import java.util.Stack;

public class AppManager {

    private static Stack<Activity> activityStack;

    private static AppManager instance;

    private int forgroundCount = 0;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activityStack != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 获取当前Activity
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void addForgroundCount() {
        forgroundCount++;
    }

    public void deleteForgroundCount() {
        forgroundCount--;
    }

    /**
     * @author xugs
     * @Description: 当前应用是否处于前台
     * @return
     * @date 2015-8-31 下午12:51:32
     */
    public boolean isInForground() {
        return forgroundCount > 0;
    }

    
//    public NoticeActivity topIsNoticeActivity(){
//        if(activityStack != null && !activityStack.isEmpty()){
//            Activity activity = activityStack.peek();
//            if(activity instanceof NoticeActivity){
//                return ((NoticeActivity)activity);
//            }
//        }
//        return null;
//    }
    
//    public boolean topIsLauncherActivity(){
//        if(activityStack != null && !activityStack.isEmpty()){
//            Activity activity = activityStack.peek();
//            if(activity instanceof LauncherActivity){
//                return true;
//            }
//        }
//        return false;
//    }
    
    public int getStackSize(){
        return activityStack.size();
    }
    
}
