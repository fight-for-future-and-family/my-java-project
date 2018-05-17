package com.hoolai.manage.vo;

import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.model.Authorities;
import com.hoolai.manage.model.Groups;
import com.hoolai.manage.model.Users;

public class UsersVO extends AbstractObjectVO<Users> {

    private static final long serialVersionUID = -4862956885373318170L;

    /** addUser/updateUser页面 */
    private Long authoritiesId;
    
    /** modifyPass页面 */
    private String newPassword;
    
    /** userList页面选择字段 */
    private Groups groups;
    private Authorities authorities;
    private String userGames;
    private String userGroups;
    
    private String loginType;
    
    //4source
    private Long gamesId;
    
    //4index
    private String veriCode;
    private String smiple;
    
    public String getVeriCode() {
		return veriCode;
	}

	public void setVeriCode(String veriCode) {
		this.veriCode = veriCode;
	}

	public UsersVO() {
        super();
        this.entity = new Users();
    }

    public UsersVO(Users entity) {
        super(entity);
    }

    public Long getAuthoritiesId() {
        return authoritiesId;
    }

    public void setAuthoritiesId(Long authoritiesId) {
        this.authoritiesId = authoritiesId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	public Authorities getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Authorities authorities) {
		this.authorities = authorities;
	}

	public String getUserGames() {
		return userGames;
	}

	public void setUserGames(String userGames) {
		this.userGames = userGames;
	}

	public String getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(String userGroups) {
		this.userGroups = userGroups;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public Long getGamesId() {
		return gamesId;
	}

	public void setGamesId(Long gamesId) {
		this.gamesId = gamesId;
	}

	public String getSmiple() {
		return smiple;
	}

	public void setSmiple(String smiple) {
		this.smiple = smiple;
	}

}
