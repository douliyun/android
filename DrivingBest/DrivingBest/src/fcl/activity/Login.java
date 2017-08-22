package fcl.activity;

import wb.DrivingBest.R;
import fcl.data.DBHelper;
import fcl.data.UserDataManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	public int pwdresetFlag=0;  
    EditText mAccount;                        //用户名编辑  
    EditText mPwd;                            //密码编辑  
    Button mRegisterButton;                   //注册按钮  
    Button mLoginButton;                      //登录按钮  
    CheckBox mRememberCheck; 
    Button mCancleButton;                   //注销按钮
    private SharedPreferences login_sp;  
    private String userNameValue,passwordValue;  
  
    private View loginView;                           //登录  
    private View loginSuccessView;  
    private TextView loginSuccessShow;  
    private TextView mChangepwdText;  
    private UserDataManager mUserDataManager;         //用户数据管理类 
  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.login);  
        //通过id找到相应的控件  
        mAccount = (EditText) findViewById(R.id.login_edit_account);  
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);  
        mRegisterButton = (Button) findViewById(R.id.login_btn_register);  
        mLoginButton = (Button) findViewById(R.id.login_btn_login);  
        mRememberCheck = (CheckBox)findViewById(R.id.Login_Remember);
        mCancleButton = (Button)findViewById(R.id.login_btn_cancle);
        loginSuccessView=findViewById(R.id.login_success_view);  
        loginSuccessShow=(TextView) findViewById(R.id.login_success_show);  
  
  
        login_sp = getSharedPreferences("userInfo", 0);  
        String name=login_sp.getString("USER_NAME", "");  
        String pwd =login_sp.getString("PASSWORD", "");  
        boolean choseRemember =login_sp.getBoolean("mRememberCheck", false);  
        boolean choseAutoLogin =login_sp.getBoolean("mAutologinCheck", false);  
        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码  
        if(choseRemember){  
            mAccount.setText(name);  
            mPwd.setText(pwd);  
            mRememberCheck.setChecked(true);  
        }  
        mLoginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				login();  
			}
		});
        mRegisterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent_Login_to_Register = new Intent(Login.this,Register.class) ;    //切换Login Activity至User Activity  
                startActivity(intent_Login_to_Register);  
			}
		});
        
        mCancleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				cancle();
			}
		});
     
        if (mUserDataManager == null) {  
            mUserDataManager = new UserDataManager(this);  
           mUserDataManager.openDataBase();                              //建立本地数据库  
        }  
    }  
  
    public void cancle() {
		// TODO 自动生成的方法存根
    	if (NamaAndPwd()) {
            String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息  
            String userPwd = mPwd.getText().toString().trim();  
            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);  
            if(result==1){                                             //返回1说明用户名和密码均正确  
                Toast.makeText(this, getString(R.string.cancle_success),Toast.LENGTH_SHORT).show();
                mAccount.setText("");  
                mUserDataManager.deleteUserDatabyname(userName);  
            }else if(result==0){  
                Toast.makeText(this, getString(R.string.cancle_fail),Toast.LENGTH_SHORT).show();  //注销失败提示  
            }  
    	}
	}

	public void login() {                                              //登录按钮监听事件   
		if (NamaAndPwd()) { //定义一个包含名字和密码的量
            String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息  
            String userPwd = mPwd.getText().toString().trim();  
            SharedPreferences.Editor editor =login_sp.edit();  
            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);  
            if(result==1){                                             //返回1说明用户名和密码均正确  
                //保存用户名和密码  
                editor.putString("USER_NAME", userName);  
                editor.putString("PASSWORD", userPwd);  
                if(mRememberCheck.isChecked()){  
                    editor.putBoolean("mRememberCheck", true);  
                }else{  
                    editor.putBoolean("mRememberCheck", false);  
                }  
                
                editor.commit();   
                Toast.makeText(this, getString(R.string.login_success),Toast.LENGTH_SHORT).show();//登录成功提示 
                Intent intent_Login_to_Driving = new Intent(Login.this,DrivingBest.class) ;
                startActivity(intent_Login_to_Driving);
            }else if(result==0){  
                Toast.makeText(this, getString(R.string.login_fail),Toast.LENGTH_SHORT).show();  //登录失败提示  
            }  
		}
    }
	//如果登录时用户名或密码为空时出现的提示
	 private boolean NamaAndPwd() {
		// TODO 自动生成的方法存根
		 if (mAccount.getText().toString().trim().equals("")) {  
	            Toast.makeText(this, getString(R.string.account_empty),  
	                    Toast.LENGTH_SHORT).show();  
	            return false;  
	        } else if (mPwd.getText().toString().trim().equals("")) {  
	            Toast.makeText(this, getString(R.string.pwd_empty),  
	                    Toast.LENGTH_SHORT).show();  
	            return false;  
	        }  
	        return true;  
	}
}
