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
	private EditText mAccount;                        //�û����༭  
    private EditText mPwd;                            //����༭  
    private EditText mPwdCheck;                        //����ȷ��
    private Button mSureButton;                   //ȷ����ť  
    private Button mCancleButton;                      //ȡ����ť 
    private UserDataManager mUserDataManager;         //�û����ݹ����� 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
		mPwd = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
		mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);  
	    mSureButton = (Button) findViewById(R.id.register_btn_sure);  
	    mCancleButton = (Button) findViewById(R.id.register_btn_cancel);
	    
	    mSureButton.setOnClickListener(n_Listener);                      //����OnClickListener�������ò�ͬ��ť����֮��ļ����¼�  
	    mCancleButton.setOnClickListener(n_Listener);
	    if (mUserDataManager == null) {  
            mUserDataManager = new UserDataManager(this);  
           mUserDataManager.openDataBase();                              //�����������ݿ�  
        }  
	}
	OnClickListener n_Listener = new OnClickListener() {                  //��ͬ��ť���µļ����¼�ѡ��  
        public void onClick(View v) {  
            switch (v.getId()) {  
                case R.id.register_btn_sure:                            //ע������ȷ����ť  
                    register_check();
                    break;  
                case R.id.register_btn_cancel:                              //ע������ȡ����ť  
                	Intent intent_Register_to_Login = new Intent(Register.this,Login.class) ;    //�л�Register Activity��Login Activity  
                    startActivity(intent_Register_to_Login);  
                    finish();   
                    break;     
            }  
        }
		
    };  
    private void register_check() {
		// TODO �Զ����ɵķ������
		String userName = mAccount.getText().toString().trim();    //��ȡ��ǰ������û�����������Ϣ  
        String userPwd = mPwd.getText().toString().trim();  
        String userPwdCheck = mPwdCheck.getText().toString().trim();
        int a = mUserDataManager.findUserByName(userName);
        if(a > 0){
        	
        	Toast.makeText(this, getString(R.string.name_haved),Toast.LENGTH_SHORT).show();  //��ʾ�û�������
        	return;
        }
        if(userPwd.equals(userPwdCheck)==false){
        	Toast.makeText(this, getString(R.string.passwd_different),Toast.LENGTH_SHORT).show();
        	return;
        }
        else{
        	UserData mUser = new UserData(userName, userPwd);  
            mUserDataManager.openDataBase();  
            long flag = mUserDataManager.insertUserData(mUser); //�½��û���Ϣ
            Toast.makeText(this, getString(R.string.register_success),Toast.LENGTH_SHORT).show();
            Intent intent_Register_to_Login = new Intent(Register.this,Login.class) ;
            startActivity(intent_Register_to_Login);  
            finish(); 
        }
	}
}
