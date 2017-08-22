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
       
        //3秒之后执行  
        myhHandler.sendEmptyMessageDelayed(111, 3000);  
         
    }  
    /** 
     * 处理页面跳转到主Activity 
     * */  
    Handler myhHandler = new Handler(){  
        @Override  
        public void handleMessage(Message msg){  
            switch (msg.what) {  
            case 111:  
                Intent intent = new Intent(); 
              //结束后跳转到登录选择界面
                intent.setClass(Welcome.this, LoginMethod.class);  //结束后
                startActivity(intent);  
                break;  
  
            default:  
                break;  
            }  
        }  
    };  
	
}
