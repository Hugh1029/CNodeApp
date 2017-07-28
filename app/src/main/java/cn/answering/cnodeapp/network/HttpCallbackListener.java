package cn.answering.cnodeapp.network;

/**
 * Created by HP on 2017/7/27.
 */

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
