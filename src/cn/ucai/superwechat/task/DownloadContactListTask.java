package cn.ucai.superwechat.task;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.HashMap;

import cn.ucai.superwechat.I;
import cn.ucai.superwechat.SuperWeChatApplication;
import cn.ucai.superwechat.activity.BaseActivity;
import cn.ucai.superwechat.bean.Contact;
import cn.ucai.superwechat.data.ApiParams;
import cn.ucai.superwechat.data.GsonRequest;
import cn.ucai.superwechat.utils.Utils;

/**
 * Created by sks on 2016/4/5.
 */
public class DownloadContactListTask extends BaseActivity {
    public static final String TAG = DownloadContactListTask.class.getName();
    Context mContext;
    String userName;
    int pageId;
    int pageSize;
    String path;

    public DownloadContactListTask(Context context,String userName, int pageId, int pageSize) {
        this.mContext = context;
        this.userName = userName;
        this.pageId = pageId;
        this.pageSize = pageSize;
        initPath();
    }

    private void initPath(){
        try {
            path = new ApiParams()
                    .with(I.Contact.USER_NAME, userName)
                    .with(I.PAGE_ID, pageId + "")
                    .with(I.PAGE_SIZE, pageSize + "")
                    .getRequestUrl(I.REQUEST_DOWNLOAD_CONTACT_LIST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(){
        executeRequest(new GsonRequest<Contact[]>(path,Contact[].class,
                responseDownloadUserListListener(), errorListener()));
    }

    private Response.Listener<Contact[]> responseDownloadUserListListener() {
        return new Response.Listener<Contact[]>(){
            @Override
            public void onResponse(Contact[] userList) {
                if(userList==null){
                    return;
                }
                ArrayList<Contact> contactList = SuperWeChatApplication.getInstance().getContactList();
                ArrayList<Contact> users = Utils.array2List(userList);
                contactList.clear();
                contactList.addAll(users);
                HashMap<String, Contact> userBeanMap = SuperWeChatApplication.getInstance().getUserList();
                HashMap<String, Contact> userMap = new HashMap<String, Contact>();
                for (Contact u : userList){
                    userMap.put(u.getMContactCname(),u);
                }
                userBeanMap.clear();
                userBeanMap.putAll(userMap);
                Intent intent = new Intent("update_contact_list");
                mContext.sendStickyBroadcast(intent);
            }
        };
    }
}
