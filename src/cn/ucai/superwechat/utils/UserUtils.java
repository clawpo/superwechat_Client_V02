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
import cn.ucai.superwechat.bean.GroupBean;
import cn.ucai.superwechat.bean.UserBean;
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
        EMUser EMUser = ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList().get(username);
        if(EMUser == null){
            EMUser = new EMUser(username);
        }
            
        if(EMUser != null){
            //demo没有这些数据，临时填充
        	if(TextUtils.isEmpty(EMUser.getNick()))
        		EMUser.setNick(username);
        }
        return EMUser;
    }

    public static UserBean getUserBeanInfo(String username){
        UserBean user = SuperWeChatApplication.getInstance().getUserList().get(username);
        return user;
    }

    public static GroupBean getGroupBeanInfo(String groupName){
        if(groupName==null){
            return null;
        }
        ArrayList<GroupBean> groupList = SuperWeChatApplication.getInstance().getGroupList();
        if(groupList!=null){
            for(GroupBean g : groupList){
                if(g.getName().equals(groupName)){
                    return g;
                }
            }
        }
        return null;
    }

    public static ArrayList<UserBean> getGroupMembersInfo(String groupId){
        ArrayList<UserBean> groups=SuperWeChatApplication.getInstance()
                .getGroupMembers().get(groupId);
        return groups;
    }

    public static UserBean getGroupMemberInfo(String groupId, String username){
        ArrayList<UserBean> members = getGroupMembersInfo(groupId);
        UserBean user = new UserBean(username);
        if(members!=null && members.contains(user)){
            for(UserBean u:members){
                if(user.getUserName().equals(u.getUserName())){
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
    	EMUser EMUser = getUserInfo(username);
        if(EMUser != null && EMUser.getAvatar() != null){
            Picasso.with(context).load(EMUser.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
        }else{
            Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
        }
    }

    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserBeanAvatar(String username, NetworkImageView imageView){
        UserBean user = getUserBeanInfo(username);
        setUserAvatar(user,imageView);
    }

    public static void setGroupMemberAvatar(String groupId,String username,NetworkImageView imageView){
        UserBean user = getGroupMemberInfo(groupId,username);
        setUserAvatar(user,imageView);
    }

    /**
     * 设置用户头像
     */
    public static void setUserBeanAvatar(UserBean user, NetworkImageView imageView){
        setUserAvatar(user,imageView);
    }
    
    /**
     * 设置当前用户头像
     */
	public static void setCurrentUserAvatar(Context context, ImageView imageView) {
		EMUser EMUser = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
		if (EMUser != null && EMUser.getAvatar() != null) {
			Picasso.with(context).load(EMUser.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
		} else {
			Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
		}
	}

	public static void setCurrentUserBeanAvatar(NetworkImageView imageView){
		UserBean user = SuperWeChatApplication.getInstance().getUser();
        setUserAvatar(user,imageView);
	}

    private static void setUserAvatar(UserBean user, NetworkImageView imageView){
        imageView.setDefaultImageResId(R.drawable.default_avatar);
        if(user != null && user.getAvatar() != null){
            String path = I.DOWNLOAD_AVATAR_URL + user.getAvatar();
            imageView.setImageUrl(path,RequestManager.getImageLoader());
        } else {
            imageView.setErrorImageResId(R.drawable.default_avatar);
        }
    }

    public static void setGroupAvatar(String groupName,NetworkImageView imageView){
        GroupBean group = getGroupBeanInfo(groupName);
        setGroupBeanAvatar(group,imageView);
    }

    public static void setGroupBeanAvatar(GroupBean group,NetworkImageView imageView){
        imageView.setDefaultImageResId(R.drawable.group_icon);
        if(group!=null && group.getName()!=null){
            String path = I.DOWNLOAD_AVATAR_URL + group.getAvatar();
            imageView.setImageUrl(path,RequestManager.getImageLoader());
        }else{
            imageView.setErrorImageResId(R.drawable.group_icon);
        }
    }


    
    /**
     * 设置用户昵称
     */
    public static void setUserNick(String username,TextView textView){
    	EMUser EMUser = getUserInfo(username);
    	if(EMUser != null){
    		textView.setText(EMUser.getNick());
    	}else{
    		textView.setText(username);
    	}
    }

    /**
     * 设置用户昵称
     */
    public static void setUserBeanNick(String username,TextView textView){
        UserBean user = getUserBeanInfo(username);
        if(user != null){
            textView.setText(user.getNick());
        }else{
            textView.setText(username);
        }
    }

    public static void setGroupMemberNick(String groupId,String username,TextView textView){
        UserBean user = getGroupMemberInfo(groupId,username);
        if(user!=null){
            textView.setText(user.getNick());
        }else{
            textView.setText(username);
        }
    }

    /**
     * 设置用户昵称
     */
    public static void setUserBeanNickNF(UserBean user,TextView textView){
        if(user != null && user.getAvatar()!=null){
            textView.setText(user.getNick());
        }else{
            textView.setText(user.getUserName());
        }
    }
    
    /**
     * 设置当前用户昵称
     */
    public static void setCurrentUserNick(TextView textView){
        EMUser EMUser = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
        if(textView != null){
            textView.setText(EMUser.getNick());
        }
    }

    /**
     * 设置当前用户昵称
     */
    public static void setCurrentUserBeanNick(TextView textView){
        UserBean user = SuperWeChatApplication.getInstance().getUser();
        if(textView != null){
            textView.setText(user.getNick());
        }
    }
    
    /**
     * 保存或更新某个用户
     * @param newEMUser
     */
	public static void saveUserInfo(EMUser newEMUser) {
		if (newEMUser == null || newEMUser.getUsername() == null) {
			return;
		}
		((DemoHXSDKHelper) HXSDKHelper.getInstance()).saveContact(newEMUser);
	}


    /**
     * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
     *
     * @param username
     * @param user
     */
    public static void setUserHearder(String username, UserBean user) {
        String headerName = null;
        if (!TextUtils.isEmpty(user.getNick())) {
            headerName = user.getNick();
        } else {
            headerName = user.getUserName();
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
