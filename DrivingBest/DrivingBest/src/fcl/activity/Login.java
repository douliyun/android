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
    EditText mAccount;                        //�û����༭  
    EditText mPwd;                            //����༭  
    Button mRegisterButton;                   //ע�ᰴť  
    Button mLoginButton;                      //��¼��ť  
    CheckBox mRememberCheck; 
    Button mCancleButton;                   //ע����ť
    private SharedPreferences login_sp;  
    private String userNameValue,passwordValue;  
  
    private View loginView;                           //��¼  
    private View loginSuccessView;  
    private TextView loginSuccessShow;  
    private TextView mChangepwdText;  
    private UserDataManager mUserDataManager;         //�û����ݹ����� 
  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.login);  
        //ͨ��id�ҵ���Ӧ�Ŀؼ�  
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
        //����ϴ�ѡ�˼�ס���룬�ǽ����¼ҳ��Ҳ�Զ���ѡ��ס���룬�������û���������  
        if(choseRemember){  
            mAccount.setText(name);  
            mPwd.setText(pwd);  
            mRememberCheck.setChecked(true);  
        }  
        mLoginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				login();  
			}
		});
        mRegisterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				Intent intent_Login_to_Register = new Intent(Login.this,Register.class) ;    //�л�Login Activity��User Activity  
                startActivity(intent_Login_to_Register);  
			}
		});
        
        mCancleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				cancle();
			}
		});
     
        if (mUserDataManager == null) {  
            mUserDataManager = new UserDataManager(this);  
           mUserDataManager.openDataBase();                              //�����������ݿ�  
        }  
    }  
  
    public void cancle() {
		// TODO �Զ����ɵķ������
    	if (NamaAndPwd()) {
            String userName = mAccount.getText().toString().trim();    //��ȡ��ǰ������û�����������Ϣ  
            String userPwd = mPwd.getText().toString().trim();  
            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);  
            if(result==1){                                             //����1˵���û������������ȷ  
                Toast.makeText(this, getString(R.string.cancle_success),Toast.LENGTH_SHORT).show();
                mAccount.setText("");  
                mUserDataManager.deleteUserDatabyname(userName);  
            }else if(result==0){  
                Toast.makeText(this, getString(R.string.cancle_fail),Toast.LENGTH_SHORT).show();  //ע��ʧ����ʾ  
            }  
    	}
	}

	public void login() {                                              //��¼��ť�����¼�   
		if (NamaAndPwd()) { //����һ���������ֺ��������
            String userName = mAccount.getText().toString().trim();    //��ȡ��ǰ������û�����������Ϣ  
            String userPwd = mPwd.getText().toString().trim();  
            SharedPreferences.Editor editor =login_sp.edit();  
            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);  
            if(result==1){                                             //����1˵���û������������ȷ  
                //�����û���������  
                editor.putString("USER_NAME", userName);  
                editor.putString("PASSWORD", userPwd);  
                if(mRememberCheck.isChecked()){  
                    editor.putBoolean("mRememberCheck", true);  
                }else{  
                    editor.putBoolean("mRememberCheck", false);  
                }  
                
                editor.commit();   
                Toast.makeText(this, getString(R.string.login_success),Toast.LENGTH_SHORT).show();//��¼�ɹ���ʾ 
                Intent intent_Login_to_Driving = new Intent(Login.this,DrivingBest.class) ;
                startActivity(intent_Login_to_Driving);
            }else if(result==0){  
                Toast.makeText(this, getString(R.string.login_fail),Toast.LENGTH_SHORT).show();  //��¼ʧ����ʾ  
            }  
		}
    }
	//�����¼ʱ�û���������Ϊ��ʱ���ֵ���ʾ
	 private boolean NamaAndPwd() {
		// TODO �Զ����ɵķ������
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
