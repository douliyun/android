package fcl.data;

public class UserData {
	private String userName;                  //�û���  
    private String userPwd;                   //�û�����  
    private int userId;                       //�û�ID��  
    public int pwdresetFlag=0;  
    //��ȡ�û���  
    public String getUserName() {             //��ȡ�û���  
        return userName;  
    }  
    //�����û���  
    public void setUserName(String userName) {  //�����û���  
        this.userName = userName;  
    }  
    //��ȡ�û�����   
    public String getUserPwd() {                //��ȡ�û�����  
        return userPwd;  
    }  
    //�����û�����  
    public void setUserPwd(String userPwd) {     //�����û�����  
        this.userPwd = userPwd;  
    }  
    //��ȡ�û�id  
    public int getUserId() {                   //��ȡ�û�ID��  
        return userId;  
    }  
    //�����û�id  
    public void setUserId(int userId) {       //�����û�ID��  
        this.userId = userId;  
    }  
    public UserData(String userName, String userPwd) {  //����ֻ�����û���������  
        super();  
        this.userName = userName;  
        this.userPwd = userPwd;  
    }  
}
