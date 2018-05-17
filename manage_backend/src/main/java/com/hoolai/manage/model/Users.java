package com.hoolai.manage.model;

import java.util.Random;
import java.util.regex.Pattern;

public class Users {
	
	public static final String PASSWORD_UPPER_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String PASSWORD_LOWER_STR = "abcdefghijklmnopqrstuvwxyz";
	public static final String PASSWORD_NUM_STR = "0123456789";
	public static final String PASSWORD_SIGN_STR = "_~!@#$%^&*";
	public static final String pattern = "yyyy-MM-dd HH:mm:ss";
	
	public static String randomPass() {
		char[] passUpperArr = Users.PASSWORD_UPPER_STR.toCharArray();
		char[] passLowerArr = Users.PASSWORD_LOWER_STR.toCharArray();
		char[] passNumArr = Users.PASSWORD_NUM_STR.toCharArray();
		char[] passSignArr = Users.PASSWORD_SIGN_STR.toCharArray();
		char passArr[][] = {passUpperArr,passLowerArr,passNumArr,passSignArr};
		
		Random randGen = new Random();
		char[] randBuffer = new char[10];
		randBuffer[0] = passUpperArr[randGen.nextInt(passUpperArr.length-1)];
		randBuffer[1] = passLowerArr[randGen.nextInt(passLowerArr.length-1)];
		randBuffer[2] = passNumArr[randGen.nextInt(passNumArr.length-1)];
		for (int i = 3; i < 10; i++) {
			char temp[] = passArr[randGen.nextInt(3)];
			randBuffer[i] = temp[randGen.nextInt(temp.length-1)];
		}
		return new String(randBuffer);
	}
	
	/**
	 * 检查密码的合法性
	 * 符合要求返回true 不符合返回false
	 * @param newPassword
	 * @return
	 */
	public static boolean checkVaildPassWord(String newPassword) {
		String regUpper = "[A-Z]";
		String regLower = "[a-z]";
		String regNum = "[0-9]";
        int complex = 0;
        Pattern pattern = Pattern.compile(regUpper);
        complex = pattern.matcher(newPassword).find() ? ++complex : complex;
        
        pattern = Pattern.compile(regLower);
        complex = pattern.matcher(newPassword).find() ? ++complex : complex;
        
        pattern = Pattern.compile(regNum);
        complex = pattern.matcher(newPassword).find() ? ++complex : complex;
        
         if (complex < 3 || newPassword.length() < 8 || newPassword.length() >20) {
   			return false;
          }
		return true;
	}
	
	public enum UserStatus{
		NORMAL,FROZEN,ADMIN_FROZEN
	}
	
    private Long id;

    private String loginName;

    private String password;

    private Integer sex;

    private Integer department;

    private String email;

    private String telepone;

    private String createDate;

    private String lastLoginTime;

    private String updateDate;
    
    private String realName;
    
    private String userBiId;
    
    private Integer loginErrorTimes;
    
    private Integer status;

    public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserBiId() {
		return userBiId;
	}

	public void setUserBiId(String userBiId) {
		this.userBiId = userBiId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelepone() {
        return telepone;
    }

    public void setTelepone(String telepone) {
        this.telepone = telepone;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

	public Integer getLoginErrorTimes() {
		return loginErrorTimes;
	}

	public void setLoginErrorTimes(Integer loginErrorTimes) {
		this.loginErrorTimes = loginErrorTimes;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}