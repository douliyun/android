package fcl.activity;

import wb.DrivingBest.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import fcl.data.UserData;
import fcl.data.UserDataManager;

public class Register extends Activity {
	private EditText mAccount;                        //用户名编辑  
    private EditText mPwd;                            //密码编辑  
    private EditText mPwdCheck;                        //密码确认
    private Button mSureButton;                   //确定按钮  
    private Button mCancleButton;                      //取消按钮 
    private UserDataManager mUserDataManager;         //用户数据管理类 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
		mPwd = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
		mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);  
	    mSureButton = (Button) findViewById(R.id.register_btn_sure);  
	    mCancleButton = (Button) findViewById(R.id.register_btn_cancel);
	    
	    mSureButton.setOnClickListener(n_Listener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件  
	    mCancleButton.setOnClickListener(n_Listener);
	    if (mUserDataManager == null) {  
            mUserDataManager = new UserDataManager(this);  
           mUserDataManager.openDataBase();                              //建立本地数据库  
        }  
	}
	OnClickListener n_Listener = new OnClickListener() {                  //不同按钮按下的监听事件选择  
        public void onClick(View v) {  
            switch (v.getId()) {  
                case R.id.register_btn_sure:                            //注册界面的确定按钮  
                    register_check();
                    break;  
                case R.id.register_btn_cancel:                              //注册界面的取消按钮  
                	Intent intent_Register_to_Login = new Intent(Register.this,Login.class) ;    //切换Register Activity至Login Activity  
                    startActivity(intent_Register_to_Login);  
                    finish();   
                    break;     
            }  
        }
		
    };  
    private void register_check() {
		// TODO 自动生成的方法存根
		String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息  
        String userPwd = mPwd.getText().toString().trim();  
        String userPwdCheck = mPwdCheck.getText().toString().trim();
        int a = mUserDataManager.findUserByName(userName);
        if(a > 0){
        	
        	Toast.makeText(this, getString(R.string.name_haved),Toast.LENGTH_SHORT).show();  //提示用户名已有
        	return;
        }
        if(userPwd.equals(userPwdCheck)==false){
        	Toast.makeText(this, getString(R.string.passwd_different),Toast.LENGTH_SHORT).show();
        	return;
        }
        else{
        	UserData mUser = new UserData(userName, userPwd);  
            mUserDataManager.openDataBase();  
            long flag = mUserDataManager.insertUserData(mUser); //新建用户信息
            Toast.makeText(this, getString(R.string.register_success),Toast.LENGTH_SHORT).show();
            Intent intent_Register_to_Login = new Intent(Register.this,Login.class) ;
            startActivity(intent_Register_to_Login);  
            finish(); 
        }
	}
}
