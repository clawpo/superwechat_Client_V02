/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ucai.superwechat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;

import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatApplication;
import cn.ucai.superwechat.bean.MessageBean;
import cn.ucai.superwechat.data.ApiParams;
import cn.ucai.superwechat.data.GsonRequest;
import cn.ucai.superwechat.data.MultipartRequest;
import cn.ucai.superwechat.listener.OnSetAvatarListener;
import cn.ucai.superwechat.utils.ImageUtils;
import cn.ucai.superwechat.utils.Utils;

/**
 * 注册页
 * 
 */
public class RegisterActivity extends BaseActivity {
	public static final String TAG = RegisterActivity.class.getName();
	RegisterActivity mContext;
	private EditText userNameEditText;
	private EditText userNickEditText;
	private EditText passwordEditText;
	private EditText confirmPwdEditText;
	private ImageView mivAvatar;

	ProgressDialog pd;

    OnSetAvatarListener mOnSetAvatarListener;

    String username;
    String nick;
    String pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		mContext = this;
		initView();
		setListener();
	}

	private void setListener() {
		setLoginClickListener();
		setRegisterClickListener();
        setAvatarClickListener();
	}

    private void setAvatarClickListener() {
        findViewById(R.id.layout_user_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSetAvatarListener = new OnSetAvatarListener(mContext,R.id.layout_register,getUserName(),"user_avatar");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
        mOnSetAvatarListener.setAvatar(requestCode,data,mivAvatar);
    }

    private void setLoginClickListener() {
		findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
			}
		});
	}

	private void setRegisterClickListener() {
		findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				username = userNameEditText.getText().toString().trim();
                nick = userNickEditText.getText().toString().trim();
				pwd = passwordEditText.getText().toString().trim();
				String confirm_pwd = confirmPwdEditText.getText().toString().trim();
				if (TextUtils.isEmpty(username)) {
					userNameEditText.requestFocus();
                    userNameEditText.setError(getResources().getString(R.string.User_name_cannot_be_empty));
					return;
				} else if (!username.matches("[\\w][\\w\\d_]+")){
                    userNameEditText.requestFocus();
                    userNameEditText.setError(getResources().getString(R.string.User_name_cannot_be_wd));
                    return;
                } else if (TextUtils.isEmpty(pwd)) {
					passwordEditText.requestFocus();
                    passwordEditText.setError(getResources().getString(R.string.Password_cannot_be_empty));
					return;
				} else if (TextUtils.isEmpty(confirm_pwd)) {
					confirmPwdEditText.requestFocus();
                    confirmPwdEditText.setError(getResources().getString(R.string.Confirm_password_cannot_be_empty));
					return;
				} else if (!pwd.equals(confirm_pwd)) {
                    confirmPwdEditText.requestFocus();
                    confirmPwdEditText.setError(getResources().getString(R.string.Two_input_password));
					return;
				}

				if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
					pd = new ProgressDialog(mContext);
					pd.setMessage(getResources().getString(R.string.Is_the_registered));
					pd.show();

                    registerAppServer();

				}
			}
		});
	}

    private void registerAppServer(){
        //先注册本地的服务器 REQUEST_REGISTER -->volley
        //注册成功后，上传头像 uploadAvatar
        //注册环信的服务器 registerEMServer
        //如果环信的服务器注册失败，删除服务器上面的账号和头像 unRegister-->volley
        try {
            String path = new ApiParams()
                    .with(I.User.USER_NAME,username)
                    .with(I.User.NICK,nick)
                    .with(I.User.PASSWORD,pwd)
                    .getRequestUrl(I.REQUEST_REGISTER);
			Log.e(TAG,"path="+path);
            executeRequest(new GsonRequest<MessageBean>(path,MessageBean.class,
                    responseRegisterListener(), errorListener()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Response.Listener<MessageBean> responseRegisterListener() {
        return new Response.Listener<MessageBean>() {
            @Override
            public void onResponse(MessageBean messageBean) {
                Log.e(TAG,"messageBean="+messageBean);
                if(messageBean!=null&&messageBean.isSuccess()){
//                    uploadAvatar();
                    uploadAvatarByMultipart();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                boolean isSuccess = false;//NetUtil.uploadAvatar(mContext, "user_avatar", username);
                                if(isSuccess){
                                    registerEMServer();
                                } else {
                                    pd.dismiss();
                                    Utils.showToast(mContext,R.string.upload_avatar_failed,Toast.LENGTH_SHORT);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }else{
                    try {
                        String path = new ApiParams()
                                .with(I.User.USER_NAME,username)
                                .getRequestUrl(I.REQUEST_UNREGISTER);
                        executeRequest(new GsonRequest<MessageBean>(path,MessageBean.class,
                                responseUnRegisterListener(),errorListener()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pd.dismiss();
                    Utils.showToast(mContext,R.string.Registration_failed,Toast.LENGTH_SHORT);
                }
            }
        };
    }
    private Response.Listener<MessageBean> responseUnRegisterListener() {
        return new Response.Listener<MessageBean>() {
            @Override
            public void onResponse(MessageBean messageBean) {
                if(!messageBean.isSuccess()){
                    Utils.showToast(mContext,R.string.cancel_register_failed,Toast.LENGTH_SHORT);
                }
            }
        };
    }

	private void initView() {
		userNameEditText = (EditText) findViewById(R.id.etUserName);
        userNickEditText = (EditText) findViewById(R.id.etNick);
		passwordEditText = (EditText) findViewById(R.id.etPassword);
		confirmPwdEditText = (EditText) findViewById(R.id.etConfirmPassword);
        mivAvatar = (ImageView) findViewById(R.id.iv_avatar);
	}

	public void back(View view) {
		finish();
	}

	private void registerEMServer(){
		new Thread(new Runnable() {
			public void run() {
				try {
					// 调用sdk注册方法
					EMChatManager.getInstance().createAccountOnServer(username, pwd);
					runOnUiThread(new Runnable() {
						public void run() {
							if (!RegisterActivity.this.isFinishing())
								pd.dismiss();
							// 保存用户名
							SuperWeChatApplication.getInstance().setUserName(username);
							Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
							finish();
						}
					});
				} catch (final EaseMobException e) {
					runOnUiThread(new Runnable() {
						public void run() {
							if (!RegisterActivity.this.isFinishing())
								pd.dismiss();
							int errorCode=e.getErrorCode();
							if(errorCode==EMError.NONETWORK_ERROR){
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
							}else if(errorCode == EMError.USER_ALREADY_EXISTS){
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
							}else if(errorCode == EMError.UNAUTHORIZED){
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
							}else if(errorCode == EMError.ILLEGAL_USER_NAME){
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
							}else{
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed) + e.getMessage(), Toast.LENGTH_SHORT).show();
							}
						}
					});
				}
			}
		}).start();
	}

    public String getUserName() {
        String username = userNameEditText.getText().toString();
        if(username.isEmpty()){
            Utils.showToast(mContext,"请先输入用户名",Toast.LENGTH_SHORT);
            return null;
        }

        return username;
    }

	public byte[] getImageBytes(Bitmap bmp){
        if(bmp==null)return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
		byte[] imageBytes = baos.toByteArray();
        return imageBytes;
	}
    //图片转化成base64字符串
    public static String GetImageStr(String filePath) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(filePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        //返回Base64编码过的字节数组字符串
        return Base64.encodeToString(data,Base64.DEFAULT);
    }
	private void uploadAvatar(){
        //Showing the progress dialog
        String url=SuperWeChatApplication.SERVER_ROOT+"?"+I.KEY_REQUEST+"="+I.REQUEST_UPLOAD_AVATAR
                +"&"+I.User.USER_NAME+"="+username
                +"&"+I.AVATAR_TYPE+"=user_avatar";
        executeRequest(new StringRequest(Request.Method.POST,url,uploadResponseListener(),errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                File file = new File(ImageUtils.getAvatarPath(activity, "user_avatar"),
                        username + ".jpg");
                String path = file.getAbsolutePath();
//                bitmap = BitmapFactory.decodeFile(path);
                //Converting Bitmap to String
//                String image = getStringImage(bitmap);
                String image = GetImageStr(path);
                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("image", image);
                params.put("userName", username);

                //returning parameters
                return params;
            }
        });
	}

    private Response.Listener<String> uploadResponseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Utils.showToast(mContext,"upload avatar success!",Toast.LENGTH_SHORT);
            }
        };
    }
    private final String boundary = "apiclient-" + System.currentTimeMillis();
    private final String mimeType = "multipart/form-data;boundary=" + boundary;
    private byte[] multipartBody;
    private Bitmap bitmap;
    private void uploadAvatarByMultipart(){
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        DataOutputStream dos = new DataOutputStream(bos);
        File file = new File(ImageUtils.getAvatarPath(activity, "user_avatar"),
                username + ".jpg");
        String path = file.getAbsolutePath();
        bitmap = BitmapFactory.decodeFile(path);
        multipartBody = getImageBytes(bitmap);
        Log.e(TAG,"mimeType="+mimeType);
        String url=SuperWeChatApplication.SERVER_ROOT+"?"+I.KEY_REQUEST+"="+I.REQUEST_UPLOAD_AVATAR
                +"&"+I.User.USER_NAME+"="+username
                +"&"+I.AVATAR_TYPE+"=user_avatar";
        executeRequest(new MultipartRequest<MessageBean>(url,MessageBean.class,null,
                uploadAvatarByMultipartListener(),errorListener(),mimeType, multipartBody));
    }

    private Response.Listener<MessageBean> uploadAvatarByMultipartListener() {
        return new Response.Listener<MessageBean>() {
            @Override
            public void onResponse(MessageBean messageBean) {
                Utils.showToast(mContext,"upload avatar success!!!",Toast.LENGTH_SHORT);
            }
        };
    }
}
