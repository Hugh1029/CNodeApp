package cn.answering.cnodeapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.answering.cnodeapp.R;
import cn.answering.cnodeapp.model.Topic;
import cn.answering.cnodeapp.network.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zjp on 2017/7/27.
 */

public class InfoFragment extends Fragment {
    private static final String TAG = "InfoFragment";
    private static final int GET_NODEDATA_SUCCESS = 1;
    static String mess;
    private List<Topic> topicList = new ArrayList<>();
    private TopicAdapter adapter;

    public static InfoFragment newInstance(){
        return new InfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendRequestToNode();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment,container,false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.info_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new TopicAdapter(topicList);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        return view;
    }


    /**
     *the function is send a request to cnode and get json data
     * after that,use handle send message to ui thread and update ui data
     *
     *
     */
    private void sendRequestToNode(){
        String address = " https://cnodejs.org/api/v1/topics";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    for (int i = 0; i< jsonArray.length();i++){
                        JSONObject top = jsonArray.getJSONObject(i);
                        Topic topic = new Topic();
                        topic.setId(top.getString("id"));
                        topic.setContent(top.getString("content"));
                        topic.setTitle(top.getString("title"));
                        topicList.add(topic);
                    }
                    handler.sendEmptyMessage(GET_NODEDATA_SUCCESS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * this is the handle
     *
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GET_NODEDATA_SUCCESS){
                adapter.notifyDataSetChanged();
            }
        }
    };


    /**
     * the class is ViewHolder
     * this is control recyvlerview  bind with data
     *
     */
    private class TopicViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public TopicViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.testView);
        }

        //the function is bind data to view
        public void bindTopicItem(Topic topic){
            textView.setText(topic.getTitle());
        }
    }

    /**
     * the class is Adapter
     * this is control recyclerView
     *
     */

    private class TopicAdapter extends RecyclerView.Adapter<TopicViewHolder>{
        private List<Topic> mTopicList;

        public TopicAdapter(List<Topic> topics){
            mTopicList = topics;
        }


        @Override
        public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
            TopicViewHolder holder = new TopicViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(TopicViewHolder holder, int position) {
            Topic topic = mTopicList.get(position);
            holder.bindTopicItem(topic);

        }

        @Override
        public int getItemCount() {
            return mTopicList.size();
        }
    }

}
