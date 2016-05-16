package cn.ucai.superwechat.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;

import java.util.ArrayList;

import cn.ucai.superwechat.I;
import cn.ucai.superwechat.SuperWeChatApplication;
import cn.ucai.superwechat.activity.BaseActivity;
import cn.ucai.superwechat.bean.Group;
import cn.ucai.superwechat.data.ApiParams;
import cn.ucai.superwechat.data.GsonRequest;
import cn.ucai.superwechat.utils.Utils;

/**
 * Created by sks on 2016/4/5.
 */
public class DownloadAllGroupTask extends BaseActivity {
    public static final String TAG = DownloadAllGroupTask.class.getName();
    Context mContext;
    String userName;
    String path;

    public DownloadAllGroupTask(Context context,String userName) {
        this.mContext = context;
        this.userName = userName;
        initPath();
    }

    private void initPath(){
        try {
            path = new ApiParams()
                    .with(I.User.USER_NAME, userName)
                    .getRequestUrl(I.REQUEST_DOWNLOAD_GROUPS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(){
        executeRequest(new GsonRequest<Group[]>(path,Group[].class,
                responseDownloadAllGroupListener(), errorListener()));
    }

    private Response.Listener<Group[]> responseDownloadAllGroupListener() {
        return new Response.Listener<Group[]>(){
            @Override
            public void onResponse(Group[] groupList) {
                if(groupList==null){
                    return;
                }
                Log.e(TAG,"groupList.size="+groupList.length);
                ArrayList<Group> list = SuperWeChatApplication.getInstance().getGroupList();
                ArrayList<Group> groups = Utils.array2List(groupList);
                list.clear();
                list.addAll(groups);
                Intent intent = new Intent("update_group");
                mContext.sendStickyBroadcast(intent);
            }
        };
    }
}
