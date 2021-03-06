package cn.ucai.superwechat.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.easemob.EMValueCallBack;
import com.easemob.chat.EMMessage;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import cn.ucai.superwechat.DemoHXSDKHelper;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatApplication;
import cn.ucai.superwechat.applib.controller.HXSDKHelper;
import cn.ucai.superwechat.bean.Contact;
import cn.ucai.superwechat.domain.EMUser;
import cn.ucai.superwechat.utils.UserUtils;
import cn.ucai.superwechat.utils.Utils;

public class UserProfileActivity extends BaseActivity implements OnClickListener{
    public static final String TAG = UserProfileActivity.class.getName();
	
	private static final int REQUESTCODE_PICK = 1;
	private static final int REQUESTCODE_CUTTING = 2;
	private NetworkImageView headAvatar;
	private ImageView headPhotoUpdate;
	private ImageView iconRightArrow;
	private TextView tvNickName;
	private TextView tvUsername;
	private ProgressDialog dialog;
	private RelativeLayout rlNickName;
	private Button btnAddFirend;
    Context mContext;
    private String username;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_profile);
        mContext = this;
		initView();
		initListener();
	}
	
	private void initView() {
		headAvatar = (NetworkImageView) findViewById(R.id.user_head_avatar);
		headPhotoUpdate = (ImageView) findViewById(R.id.user_head_headphoto_update);
		tvUsername = (TextView) findViewById(R.id.user_username);
		tvNickName = (TextView) findViewById(R.id.user_nickname);
		rlNickName = (RelativeLayout) findViewById(R.id.rl_nickname);
		iconRightArrow = (ImageView) findViewById(R.id.ic_right_arrow);
        btnAddFirend = (Button) findViewById(R.id.btn_to_chat);
	}
	
	private void initListener() {
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		String groupId = intent.getStringExtra("groupId");
		EMMessage.ChatType chatType = (EMMessage.ChatType)intent.getSerializableExtra("chatType");
        String currentUserName = SuperWeChatApplication.getInstance().getUserName();
		boolean enableUpdate = intent.getBooleanExtra("setting", false);
		if (enableUpdate) {
			headPhotoUpdate.setVisibility(View.VISIBLE);
			iconRightArrow.setVisibility(View.VISIBLE);
			rlNickName.setOnClickListener(this);
			headAvatar.setOnClickListener(this);
		} else {
			headPhotoUpdate.setVisibility(View.GONE);
			iconRightArrow.setVisibility(View.INVISIBLE);
		}
		if (username == null || username.equals(currentUserName)) {
			tvUsername.setText(currentUserName);
			UserUtils.setCurrentUserBeanNick(tvNickName);
			UserUtils.setCurrentUserBeanAvatar(headAvatar);
		} else {
            tvUsername.setText(username);
			if(chatType== EMMessage.ChatType.GroupChat){
                UserUtils.setGroupMemberNick(groupId,username,tvNickName);
                UserUtils.setGroupMemberAvatar(groupId,username,headAvatar);
			}else{
				UserUtils.setUserBeanNick(username, tvNickName);
				UserUtils.setUserBeanAvatar(username, headAvatar);
			}
//			asyncFetchUserInfo(username);
		}
        showAddOrToChat();
        setAddOrToChatOnClickListener();
	}
    public static enum UserAction {
        UNKNOW,
        SEND_MESSAGE,
        ADD_FIREND
    }

    UserAction action = UserAction.UNKNOW;
    private void setAddOrToChatOnClickListener(){
        btnAddFirend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(action == UserAction.ADD_FIREND){
                    Utils.showToast(mContext,"该功能稍后实现",Toast.LENGTH_SHORT);
                }
                if(action == UserAction.SEND_MESSAGE){
                    finish();
                    startActivity(new Intent(mContext,
                            MainActivity.class).putExtra("userId", username));
                }
            }
        });
    }
    private void showAddOrToChat() {
        String currentUserName = SuperWeChatApplication.getInstance().getUserName();
        if (username == null || username.equals(currentUserName)) {
            btnAddFirend.setVisibility(View.INVISIBLE);
        }else{
			HashMap<String, Contact> userList = SuperWeChatApplication.getInstance().getUserList();
			if(userList.containsKey(username)){
                btnAddFirend.setText(R.string.send_message);
                action = UserAction.SEND_MESSAGE;
            }else{
                btnAddFirend.setText(R.string.add_friend);
                action = UserAction.ADD_FIREND;
            }
        }
    }

    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_head_avatar:
			uploadHeadPhoto();
			break;
		case R.id.rl_nickname:
			final EditText editText = new EditText(this);
			new AlertDialog.Builder(this).setTitle(R.string.setting_nickname).setIcon(android.R.drawable.ic_dialog_info).setView(editText)
					.setPositiveButton(R.string.dl_ok, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							String nickString = editText.getText().toString();
							if (TextUtils.isEmpty(nickString)) {
								Toast.makeText(UserProfileActivity.this, getString(R.string.toast_nick_not_isnull), Toast.LENGTH_SHORT).show();
								return;
							}
							updateRemoteNick(nickString);
						}
					}).setNegativeButton(R.string.dl_cancel, null).show();
			break;
		default:
			break;
		}

	}
	
	public void asyncFetchUserInfo(String username){
		((DemoHXSDKHelper) HXSDKHelper.getInstance()).getUserProfileManager()
                .asyncGetUserInfo(username, new EMValueCallBack<EMUser>() {
			
			@Override
			public void onSuccess(EMUser user) {
				if (user != null) {
					tvNickName.setText(user.getNick());
					if(!TextUtils.isEmpty(user.getAvatar())){
						 Picasso.with(UserProfileActivity.this).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(headAvatar);
					}else{
						Picasso.with(UserProfileActivity.this).load(R.drawable.default_avatar).into(headAvatar);
					}
					UserUtils.saveUserInfo(user);
				}
			}
			
			@Override
			public void onError(int error, String errorMsg) {
			}
		});
	}
	
	
	
	private void uploadHeadPhoto() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(R.string.dl_title_upload_photo);
		builder.setItems(new String[] { getString(R.string.dl_msg_take_photo), getString(R.string.dl_msg_local_upload) },
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0:
							Toast.makeText(UserProfileActivity.this, getString(R.string.toast_no_support),
									Toast.LENGTH_SHORT).show();
							break;
						case 1:
							Intent pickIntent = new Intent(Intent.ACTION_PICK,null);
							pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
							startActivityForResult(pickIntent, REQUESTCODE_PICK);
							break;
						default:
							break;
						}
					}
				});
		builder.create().show();
	}
	
	

	private void updateRemoteNick(final String nickName) {
		dialog = ProgressDialog.show(this, getString(R.string.dl_update_nick), getString(R.string.dl_waiting));
		new Thread(new Runnable() {

			@Override
			public void run() {
				boolean updatenick = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().updateParseNickName(nickName);
				if (UserProfileActivity.this.isFinishing()) {
					return;
				}
				if (!updatenick) {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatenick_fail), Toast.LENGTH_SHORT)
									.show();
							dialog.dismiss();
						}
					});
				} else {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							dialog.dismiss();
							Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatenick_success), Toast.LENGTH_SHORT)
									.show();
							tvNickName.setText(nickName);
						}
					});
				}
			}
		}).start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUESTCODE_PICK:
			if (data == null || data.getData() == null) {
				return;
			}
			startPhotoZoom(data.getData());
			break;
		case REQUESTCODE_CUTTING:
			if (data != null) {
				setPicToView(data);
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", true);
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, REQUESTCODE_CUTTING);
	}
	
	/**
	 * save the picture data
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(getResources(), photo);
			headAvatar.setImageDrawable(drawable);
			uploadUserAvatar(Bitmap2Bytes(photo));
		}

	}
	
	private void uploadUserAvatar(final byte[] data) {
		dialog = ProgressDialog.show(this, getString(R.string.dl_update_photo), getString(R.string.dl_waiting));
		new Thread(new Runnable() {

			@Override
			public void run() {
				final String avatarUrl = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().uploadUserAvatar(data);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dialog.dismiss();
						if (avatarUrl != null) {
							Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatephoto_success),
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(UserProfileActivity.this, getString(R.string.toast_updatephoto_fail),
									Toast.LENGTH_SHORT).show();
						}

					}
				});

			}
		}).start();

		dialog.show();
	}
	
	
	public byte[] Bitmap2Bytes(Bitmap bm){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
}
