package cn.ucai.superwechat.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.easemob.util.HanziToPinyin;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.ucai.superwechat.Constant;
import cn.ucai.superwechat.DemoHXSDKHelper;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatApplication;
import cn.ucai.superwechat.applib.controller.HXSDKHelper;
import cn.ucai.superwechat.bean.Contact;
import cn.ucai.superwechat.bean.Group;
import cn.ucai.superwechat.bean.Member;
import cn.ucai.superwechat.bean.User;
import cn.ucai.superwechat.data.RequestManager;
import cn.ucai.superwechat.domain.EMUser;

public class UserUtils {
    public static final String TAG = UserUtils.class.getName();
    /**
     * 根据username获取相应user，由于demo没有真实的用户数据，这里给的模拟的数据；
     * @param username
     * @return
     */
    public static EMUser getUserInfo(String username){
        EMUser user = ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList().get(username);
        if(user == null){
            user = new EMUser(username);
        }
            
        if(user != null){
            //demo没有这些数据，临时填充
        	if(TextUtils.isEmpty(user.getNick()))
        		user.setNick(username);
        }
        return user;
    }

    public static Contact getUserBeanInfo(String username){
        Contact user = SuperWeChatApplication.getInstance().getUserList().get(username);
        return user;
    }

    public static Group getGroupBeanInfo(String hxid){
        if(hxid==null){
            return null;
        }
        ArrayList<Group> groupList = SuperWeChatApplication.getInstance().getGroupList();
        if(groupList!=null){
            for(Group g : groupList){
                if(g.getMGroupHxid().equals(hxid)){
                    return g;
                }
            }
        }
        return null;
    }

    public static ArrayList<Member> getGroupMembersInfo(String hxid){
        ArrayList<Member> groups=SuperWeChatApplication.getInstance()
                .getGroupMembers().get(hxid);
        return groups;
    }

    public static Member getGroupMemberInfo(String hxid, String username){
        ArrayList<Member> members = getGroupMembersInfo(hxid);
        if(members!=null && members.size()>0){
            for(Member u:members){
                if(u.getMMemberUserName().equals(username)){
                    return u;
                }
            }
        }
        return null;
    }
    
    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	EMUser user = getUserInfo(username);
        if(user != null && user.getAvatar() != null){
            Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
        }else{
            Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
        }
    }

    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserBeanAvatar(String username, NetworkImageView imageView){
        Contact user = getUserBeanInfo(username);
        if(user!=null) {
            setUserAvatar(user, imageView);
        }else{
            String path = I.DOWNLOAD_USER_AVATAR_URL + username;
            imageView.setImageUrl(path, RequestManager.getImageLoader());
        }
    }

    public static void setGroupMemberAvatar(String hxid,String username,NetworkImageView imageView){
        Member user = getGroupMemberInfo(hxid,username);
        setMemberAvatar(user,imageView);
    }

    /**
     * 设置用户头像
     */
    public static void setUserBeanAvatar(User user, NetworkImageView imageView){
        setUserAvatar(user,imageView);
    }
    
    /**
     * 设置当前用户头像
     */
	public static void setCurrentUserAvatar(Context context, ImageView imageView) {
		EMUser user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
		if (user != null && user.getAvatar() != null) {
			Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
		} else {
			Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
		}
	}

	public static void setCurrentUserBeanAvatar(NetworkImageView imageView){
		User user = SuperWeChatApplication.getInstance().getUser();
        setUserAvatar(user,imageView);
	}


    public static void setMemberAvatar(Member user, NetworkImageView imageView) {
        if(user != null && user.getMMemberUserName()!=null){
            String path = I.DOWNLOAD_USER_AVATAR_URL + user.getMMemberUserName();
            imageView.setImageUrl(path, RequestManager.getImageLoader());
        }else {
            imageView.setErrorImageResId(R.drawable.default_avatar);
        }
    }

    private static void setUserAvatar(User user, NetworkImageView imageView){
        imageView.setDefaultImageResId(R.drawable.default_avatar);
        if(user != null ){
            if(user.getMAvatarPath() != null) {
                String path = I.DOWNLOAD_USER_AVATAR_URL + user.getMAvatarUserName();
                imageView.setImageUrl(path, RequestManager.getImageLoader());
            }
            if(user.getMUserName()!=null){
                String path = I.DOWNLOAD_USER_AVATAR_URL + user.getMUserName();
                imageView.setImageUrl(path, RequestManager.getImageLoader());
            }
        } else {
            imageView.setErrorImageResId(R.drawable.default_avatar);
        }
    }

    public static void setGroupAvatar(String hxid,NetworkImageView imageView){
        Group group = getGroupBeanInfo(hxid);
        setGroupBeanAvatar(group,imageView);
    }

    public static void setGroupBeanAvatar(Group group,NetworkImageView imageView){
        imageView.setDefaultImageResId(R.drawable.group_icon);
        if(group!=null && group.getMGroupHxid()!=null){
            String path = I.DOWNLOAD_GROUP_AVATAR_URL + group.getMGroupHxid();
            imageView.setImageUrl(path,RequestManager.getImageLoader());
        }else{
            imageView.setErrorImageResId(R.drawable.group_icon);
        }
    }


    
    /**
     * 设置用户昵称
     */
    public static void setUserNick(String username,TextView textView){
    	EMUser user = getUserInfo(username);
    	if(user != null){
    		textView.setText(user.getNick());
    	}else{
    		textView.setText(username);
    	}
    }

    /**
     * 设置用户昵称
     */
    public static void setUserBeanNick(String username,TextView textView){
        Contact user = getUserBeanInfo(username);
        if(user != null && user.getMUserNick()!=null){
            textView.setText(user.getMUserNick());
        }else{
            textView.setText(username);
        }
    }

    public static void setGroupMemberNick(String hxid,String username,TextView textView){
        Member user = getGroupMemberInfo(hxid,username);
        if(user!=null && user.getMUserNick()!=null){
            textView.setText(user.getMUserNick());
        }else{
            textView.setText(username);
        }
    }

    /**
     * 设置用户昵称
     */
    public static void setUserBeanNickNF(User user,TextView textView){
        if(user != null && user.getMUserNick()!=null){
            textView.setText(user.getMUserNick());
        }else{
            textView.setText(user.getMUserName());
        }
    }
    
    /**
     * 设置当前用户昵称
     */
    public static void setCurrentUserNick(TextView textView){
        EMUser user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
        if(textView != null){
            textView.setText(user.getNick());
        }
    }

    /**
     * 设置当前用户昵称
     */
    public static void setCurrentUserBeanNick(TextView textView){
        User user = SuperWeChatApplication.getInstance().getUser();
        if(textView != null && user.getMUserNick()!=null){
            textView.setText(user.getMUserNick());
        }
    }
    
    /**
     * 保存或更新某个用户
     * @param newUser
     */
	public static void saveUserInfo(EMUser newUser) {
		if (newUser == null || newUser.getUsername() == null) {
			return;
		}
		((DemoHXSDKHelper) HXSDKHelper.getInstance()).saveContact(newUser);
	}


    /**
     * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
     *
     * @param username
     * @param user
     */
    public static void setUserHearder(String username, User user) {
        String headerName = null;
        if (!TextUtils.isEmpty(user.getMUserNick())) {
            headerName = user.getMUserNick();
        } else if(!TextUtils.isEmpty(user.getMUserName())) {
            headerName = user.getMUserName();
        } else {
            headerName = ((Contact)user).getMContactCname();
        }
        if (username.equals(Constant.NEW_FRIENDS_USERNAME)
                || username.equals(Constant.GROUP_USERNAME)) {
            user.setHeader("");
        } else if (Character.isDigit(headerName.charAt(0))) {
            user.setHeader("#");
        } else {
            user.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1)).get(0).target.substring(0, 1)
                    .toUpperCase());
            char header = user.getHeader().toLowerCase().charAt(0);
            if (header < 'a' || header > 'z') {
                user.setHeader("#");
            }
        }
    }
    
}
