package fcl.activity;

import wb.DrivingBest.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Welcome extends Activity {
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.welcome);  
       
        //3��֮��ִ��  
        myhHandler.sendEmptyMessageDelayed(111, 3000);  
         
    }  
    /** 
     * ����ҳ����ת����Activity 
     * */  
    Handler myhHandler = new Handler(){  
        @Override  
        public void handleMessage(Message msg){  
            switch (msg.what) {  
            case 111:  
                Intent intent = new Intent(); 
              //��������ת����¼ѡ�����
                intent.setClass(Welcome.this, LoginMethod.class);  //������
                startActivity(intent);  
                break;  
  
            default:  
                break;  
            }  
        }  
    };  
	
}
