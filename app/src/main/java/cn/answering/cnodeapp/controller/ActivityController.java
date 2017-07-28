package cn.answering.cnodeapp.controller;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjp on 2017/7/27.
 */

public class ActivityController {

    public static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity : activityList){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        activityList.clear();

    }

}
