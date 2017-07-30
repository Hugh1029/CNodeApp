package cn.answering.cnodeapp.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zjp on 2017/7/29.
 * 运行权限申请管理
 */
public class PermissionManager {
    private static final String TAG = "PermissionManager";

    /**
     *
     * @param object Activity
     * @param requestCode  申请权限时的Code码
     * @param listener  如果申请的权限已经同意，调用监听中相应的函数
     * @param permissions   申请的权限列表
     */
    public void permissionManagerRequest(Object object,int requestCode,PermissionManagerListener listener,String... permissions ){
        List<String> permissionsList = new ArrayList<String>();
        Activity act = getActivity(object);

        for (int i = 0;i < permissions.length;i ++){
            if (ContextCompat.checkSelfPermission(act,permissions[i]) != PackageManager.PERMISSION_GRANTED){
                permissionsList.add(permissions[i]);
            }
        }

        if (!permissionsList.isEmpty()){
            String[] permissionArray = permissionsList.toArray(new String[permissionsList.size()]);
            requestPermisssion(object,requestCode,permissionArray);
        }else {
            listener.allPermisionsAllow();
        }
    }

    //获取activity参数
    private Activity getActivity(Object object){
        if (object instanceof  Activity){
            return (Activity)object;
        }else if (object instanceof  Fragment){
            return ((Fragment) object).getActivity();
        }
        return null;
    }

    //根据不同情况申请权限
    private void requestPermisssion(Object object,int requestCode,String... permissions){
        if (object instanceof Activity){
            ActivityCompat.requestPermissions((Activity) object,permissions,requestCode);
        }else if (object instanceof Fragment){
            ((Fragment) object).requestPermissions(permissions,requestCode);
        }else {
            throw new IllegalArgumentException(object.getClass().getName() + " is not supported");
        }
    }

}
