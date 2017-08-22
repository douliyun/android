package fcl.activity;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import wb.DrivingBest.R;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginMobile extends Activity implements OnClickListener{
	
	private EditText etphone;              // �ֻ��������  
    private EditText etcode;                // ��֤�������  
    private Button btnrequest;                   // ��ȡ��֤�밴ť  
    private Button btnsub;                           // ע�ᰴť  
    int i = 30;                     // ���ö�����֤��ʾʱ��Ϊ30s  
	@Override
	protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.mobile); 
        etphone = (EditText) findViewById(R.id.login_input_phone_et);  
        etcode = (EditText) findViewById(R.id.login_input_code_et);  
        btnrequest = (Button) findViewById(R.id.login_request_code_btn);  
        btnsub = (Button) findViewById(R.id.login_commit_btn);  
        btnrequest.setOnClickListener(this);  
        btnsub.setOnClickListener(this);  
  
        Log.e("ms", "1");  
  
        // ����������֤sdk  
        SMSSDK.initSDK(this, "1f8924e3f748a",  
                "8924801eaa3b7ba44cf55ae9a5626f3f");  
        EventHandler eventHandler = new EventHandler() {  
            @Override  
            public void afterEvent(int event, int result, Object data) {  
                Message msg = new Message();  
                msg.arg1 = event;  
                msg.arg2 = result;  
                msg.obj = data;  
                handler.sendMessage(msg);  
            }  
        };  
        SMSSDK.registerEventHandler(eventHandler); // ע��ص������ӿ�  
    }  
  
    @Override  
    public void onClick(View v) {  
        String phoneNums = etphone.getText().toString();  
        switch (v.getId()) {  
        case R.id.login_request_code_btn:  
            if (!judgePhoneNums(phoneNums)) {// �ж���������Ƿ���ȷ  
                return;  
            }  
            SMSSDK.getVerificationCode("86", phoneNums); // ����sdk���Ͷ�����֤  
            btnrequest.setClickable(false);// ���ð�ť���ɵ�� ��ʾ����ʱ  
            btnrequest.setText("���·���(" + i + ")");  
            new Thread(new Runnable() {  
                @Override  
                public void run() {  
                    for (i = 30; i > 0; i--) {  
                        handler.sendEmptyMessage(-9);  
                        if (i <= 0) {  
                            break;  
                        }  
                        try {  
                            Thread.sleep(1000);// �߳�����ʵ�ֶ��빦��  
                        } catch (InterruptedException e) {  
                            e.printStackTrace();  
                        }  
                    }  
                    handler.sendEmptyMessage(-8);// ��30���������ʾΪ��ȡ��֤��  
                }  
            }).start();  
            break;  
  
        case R.id.login_commit_btn:  
            SMSSDK.submitVerificationCode("86", phoneNums, etcode.getText()  
                    .toString());  
            createProgressBar();  
            break;  
        }  
    }  
  
    /** 
     *  
     */  
    Handler handler = new Handler() {  
        public void handleMessage(Message msg) {  
            if (msg.what == -9) {  
                btnrequest.setText("���·���(" + i + ")");  
            } else if (msg.what == -8) {  
                btnrequest.setText("��ȡ��֤��");  
                btnrequest.setClickable(true); // ���ÿɵ��  
                i = 30;  
            } else {  
                int event = msg.arg1;  
                int result = msg.arg2;  
                Object data = msg.obj;  
                if (result == SMSSDK.RESULT_COMPLETE) {  
                    // ����ע��ɹ��󣬷���MainActivity,Ȼ����ʾ  
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// �ύ��֤��ɹ�  
                        Toast.makeText(getApplicationContext(), "�ύ��֤��ɹ�",  
                                Toast.LENGTH_SHORT).show();  
                        // ��֤�ɹ�����ת������  
                        //�˴���ע�͵�  
                        Intent intent = new Intent(LoginMobile.this, DrivingBest.class);  
                        intent.putExtra("flag", "newuser");  
                        startActivity(intent);  
                        Log.e("ע��", "�ɹ���");  
                        finish();// �ɹ���ת֮�����ٵ�ǰҳ��  
  
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {  
                        Toast.makeText(getApplicationContext(), "��֤���Ѿ�����",  
                                Toast.LENGTH_SHORT).show();  
                    } else {  
                        ((Throwable) data).printStackTrace();  
                    }  
                }  
            }  
        }  
    };  
  
    /** 
     * �ж��ֻ������Ƿ���� 
     *  
     * @param phoneNums 
     */  
    private boolean judgePhoneNums(String phoneNums) {  
        if (isMatchLength(phoneNums, 11) && isMobileNO(phoneNums)) {  
            return true;  
        }  
        Toast.makeText(this, "�ֻ�������������", Toast.LENGTH_SHORT).show();  
        return false;  
    }  
  
    public static boolean isMatchLength(String str, int length) {  
        if (str.isEmpty()) {  
            return false;  
        } else {  
            return str.length() == length ? true : false;  
        }  
    }  
    public static boolean isMobileNO(String mobileNums) {  
        String telRegex = "[1][3578]\\d{9}";
        // "[1]"�����1λΪ����1��"[358]"����ڶ�λ����Ϊ3��5��8�е�һ����"\\d{9}"��������ǿ�����0��9�����֣���9λ��  
        if (TextUtils.isEmpty(mobileNums))  
            return false;  
        else  
            return mobileNums.matches(telRegex);  
    }  
  
    private void createProgressBar() {  
        FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);  
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(  
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
        layoutParams.gravity = Gravity.CENTER;  
        ProgressBar mProBar = new ProgressBar(this);  
        mProBar.setLayoutParams(layoutParams);  
        mProBar.setVisibility(View.VISIBLE);  
        layout.addView(mProBar);  
    }  
  
    @Override  
    protected void onDestroy() {  
        SMSSDK.unregisterAllEventHandler();  
        super.onDestroy();  
    }  
	
}
