package com.hoolai.panel.web.controller.manage;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoolai.bi.notifyer.mail.MailSenderProxy;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.UserGame;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.UserGamesService;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.PanelPagination;
import com.hoolai.manage.model.GroupUsers;
import com.hoolai.manage.model.Groups;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.model.Users.UserStatus;
import com.hoolai.manage.service.GroupUsersService;
import com.hoolai.manage.service.GroupsService;
import com.hoolai.manage.service.UsersService;
import com.hoolai.manage.vo.UsersVO;
import com.jian.service.pagination.PaginationResult;
import com.jian.tools.util.ServletUtils;

@Controller
@RequestMapping("/panel_manage/user")
public class UserController extends AbstractManageController{
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private GroupUsersService groupUserService;
	
	@Autowired
	private GroupsService groupsService;
	
	@Autowired
	private Md5PasswordEncoder md5PasswordEncoder;
	
	@Autowired
	private GamesService gamesService;
	
	@Autowired
	private UserGamesService userGamesService;
	
	
	private String jspUrl = "manage/user/";
	
	@RequestMapping(value = {"/toUserList.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	public String toUserList(HttpServletRequest request,HttpSession session,@ModelAttribute("usersVO") UsersVO usersVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		boolean bool = !(groupUserService.isAdmin(users.getId()));
		if(bool){
			if((Boolean) request.getSession().getAttribute("isProduct")){
				return "index/gamesDailySave.jsp";
			}else{
				throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
			}
		}
		return userList(1, request, session, usersVO, result, model);
		
	}
	
	@RequestMapping(value = {"/userList_{pageNo:\\d+}.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	public String userList(@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("usersVO") UsersVO usersVO,BindingResult result,Model model)throws Exception {
		
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		//过滤分组查询 0不查询 -1查询未分组
		if(usersVO.getGroups()!= null && usersVO.getGroups().getId() == 0){
			usersVO.setGroups(null);
		}
		
		PaginationResult<Users> paginationResult = usersService.getPaginationResult(usersVO, pageNo, PAGE_SIZE);
		List<UsersVO> displayResultList = new ArrayList<UsersVO>();
		for(Users user:paginationResult.getResults()){
			UsersVO usersVO2 = new UsersVO(user);
			if(checkPass(user.getLoginName(), user.getPassword())){
				usersVO2.setSmiple("smiple");
			}
			displayResultList.add(usersVO2);
		}
		
		int count = new Long(paginationResult.getPagination().getRecordCount()).intValue();
		PanelPagination displayPagination = new PanelPagination(pageNo, PAGE_SIZE, count);
		List<Groups> groups = groupsService.selectAllGroups();
		
		model.addAttribute("displayResultList", displayResultList);
		model.addAttribute("pagination", displayPagination);
		model.addAttribute("groupsList", groups);
		
		return jspUrl + "userlist.jsp";
	}
	
	private boolean checkPass(String loginName, String pass) {
		if(md5PasswordEncoder.encodePassword(loginName, null).equals(pass)
			|| md5PasswordEncoder.encodePassword("123456",null).equals(pass)
			|| md5PasswordEncoder.encodePassword((loginName+"123"),null).equals(pass)
			//|| (pass.length() < 8 || pass.length() > 20)//先不强制，免得大批的人登录出现问题
			//|| !Users.checkVaildPassWord(pass)
			){
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = {"/addUser.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String addUser(HttpServletRequest request,HttpSession session,@ModelAttribute("usersVO") UsersVO usersVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		if(isUsersExtis(usersVO.getEntity().getLoginName())){
			model.addAttribute("msg", "抱歉，用户名已使用");
			return toAddUser(request, session, usersVO, result, model);
		}
		
		String pass = Users.randomPass();
		String passMd5 = md5PasswordEncoder.encodePassword(pass, null);
		usersVO.getEntity().setPassword(passMd5);
		usersVO.getEntity().setCreateDate(DateFormatUtils.format(new Date(), DATE_PATTERN));
		usersVO.getEntity().setLastLoginTime(DateFormatUtils.format(new Date(), DATE_PATTERN));
		usersVO.getEntity().setUpdateDate(DateFormatUtils.format(new Date(), DATE_PATTERN));
		usersVO.getEntity().setUserBiId("0000");
		usersVO.getEntity().setLoginErrorTimes(0);
		usersVO.getEntity().setStatus(UserStatus.NORMAL.ordinal());
		usersService.saveEntity(usersVO.getEntity());
		
		if(!StringUtils.isEmpty(usersVO.getUserGroups())){
			addGroupUsers(usersVO);
		}
		
		if(!StringUtils.isEmpty(usersVO.getUserGames())){
			addUserGames(usersVO);
		}
		
		// 发送邮件
		try{
			MailSenderProxy proxy = new MailSenderProxy();
			proxy.sendMail(usersVO.getEntity().getEmail(), "胡莱游戏报表系统开通权限", "报表系统权限已开通！ 用户名："+ usersVO.getEntity().getLoginName() +",密码："+pass+",请登录系统更改初始密码并妥善保管密码！");
		}catch(Exception e){
			e.printStackTrace();
			MailSenderProxy proxy = new MailSenderProxy();
			proxy.sendMail(users.getEmail(), "胡莱游戏报表系统开通权限邮件失败", "用户名："+ usersVO.getEntity().getLoginName() +",报表系统权限已开通，但是通知邮件发送失败，请查看用户邮箱是否正确！");
		}
		
		usersVO.getEntity().setId(null);
		return this.userList(1, request, session, usersVO, result, model);
	}
	
	@RequestMapping(value = {"/toAddUser.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String toAddUser(HttpServletRequest request,HttpSession session,@ModelAttribute("usersVO") UsersVO usersVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		fillListData(model);
		return jspUrl + "addUser.jsp";
	}

	private void fillListData(Model model) throws Exception {
		List<Groups> groups = groupsService.selectAllGroups();
        List<Games> games = gamesService.getMatch(new GamesVO());
		
		model.addAttribute("groupsList", groups);
		model.addAttribute("gamesList", games);
	}

	@RequestMapping(value = {"/toUserEdit_{id:\\d+}_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String userEditGet(@PathVariable Long id,@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("usersVO") UsersVO vo,BindingResult result,Model model)
			throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		Users entity=this.usersService.getById(id);
		vo.setEntity(entity);
		vo.setUserGames(toGamesString(id));
		vo.setUserGroups(toGroupsString(id));
		fillListData(model);
		model.addAttribute("prePageNo", pageNo);
		return jspUrl + "updateUser.jsp";
	}
	
	private String toGamesString(long userId){
		List<Games> gameses = gamesService.selectGamesByUserId(userId);
		StringBuffer sb = new StringBuffer("");
		int index = 0;
		for(Games games:gameses){
			if(index == gameses.size()-1){
				sb.append(games.getId());
			}else{
				sb.append(games.getId()+",");
			}
			index++;
		}
		return sb.toString();
	}
	
	private String toGroupsString(long userId){
		List<Groups> groupList = groupsService.getGroupsByUserId(userId);
		StringBuffer sb = new StringBuffer("");
		for(Groups games:groupList){
			sb.append(games.getId()+",");
		}
		return sb.toString();
	}
	
	@RequestMapping(value ={"/userEdit_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String userEditPost(@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("usersVO") UsersVO usersVO,BindingResult result,Model model)
			throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		Users entity = this.usersService.getById(usersVO.getEntity().getId());
		
		
		
		if(entity == null){
			return userList(pageNo, request, session, usersVO, result, model);
		}
		
		if(!StringUtils.isEmpty(usersVO.getUserGroups())){
			groupUserService.removeByUserId(entity.getId());
			addGroupUsers(usersVO);
		}
		
		if(!StringUtils.isEmpty(usersVO.getUserGames())){
			userGamesService.removeByUserId(entity.getId());
			addUserGames(usersVO);
		}
		
		usersVO.getEntity().setUpdateDate(DateFormatUtils.format(new Date(), DATE_PATTERN));
		usersService.modifyEntitySelective(usersVO.getEntity());
		
		
		
		usersVO.getEntity().setId(null);
		return userList(pageNo, request, session, usersVO, result, model);
	}

	private void addUserGames(UsersVO usersVO) throws Exception {
		String[] gamesIdArr = StringUtils.split(usersVO.getUserGames(), ",");
		for(String id : gamesIdArr){
			UserGame userGame = new UserGame();
			userGame.setGameId(Integer.valueOf(id));
			userGame.setUserId(usersVO.getEntity().getId());
			userGamesService.saveEntity(userGame);
		}
	}

	private void addGroupUsers(UsersVO usersVO) throws Exception {
		String[] groupIdArr = StringUtils.split(usersVO.getUserGroups(), ",");
		for(String id : groupIdArr){
			GroupUsers groupUsers = new GroupUsers();
			groupUsers.setGroupId(Long.valueOf(id));
			groupUsers.setUserId(usersVO.getEntity().getId());
			groupUserService.saveEntity(groupUsers);
		}
	}
	
	@RequestMapping(value = {"/userDel_{id:\\d+}_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String userDel(@PathVariable Long id,@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("usersVO") UsersVO usersVO,BindingResult result,Model model)
			throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		if(id == super.getSessionUsers(request).getId()){
			model.addAttribute("msg", "不能删除自己！");
			return userList(pageNo, request, session, usersVO, result, model);
		}
		
		Users user = usersService.getById(id);
		
		usersService.removeById(id);
		userGamesService.removeByUserId(id);
		groupUserService.removeByUserId(id);
		
		
		return userList(pageNo, request, session, usersVO, result, model);
	}
	
	@RequestMapping(value = {"/userStatus_{id:\\d+}_{op:\\d+}_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String userStatus(@PathVariable Long id,@PathVariable Integer op,@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("usersVO") UsersVO usersVO,BindingResult result,Model model)
			throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		if(id == super.getSessionUsers(request).getId()){
			model.addAttribute("msg", "不能冻结自己！");
			return userList(pageNo, request, session, usersVO, result, model);
		}
		
		Users user = usersService.getById(id);
		if(op == 1){
			user.setStatus(UserStatus.NORMAL.ordinal());
			user.setLoginErrorTimes(0);
		}else if(op == 2){
			user.setLastLoginTime(DateFormatUtils.format(new Date(), Users.pattern));
			user.setStatus(UserStatus.ADMIN_FROZEN.ordinal());
		}else if(op == 3){
			user.setLastLoginTime(DateFormatUtils.format(new Date(), Users.pattern));
			user.setStatus(UserStatus.FROZEN.ordinal());
		}
		
		usersService.modifyEntitySelective(user);
		
		return userList(pageNo, request, session, usersVO, result, model);
	}
	
	@RequestMapping(value = "/modifyPass.ac", method = { RequestMethod.GET,RequestMethod.POST })
	public String modifyPass(HttpServletRequest request,HttpSession session,
			@ModelAttribute("usersVO") UsersVO usersVO,@ModelAttribute("gamesVO") GamesVO gamesVO,
			BindingResult result,Model model)
			throws Exception {
		
		String op = request.getParameter("op");
		if(op == null || "".equals(op.trim())){
			return jspUrl + "modifyPass.jsp";
		}
		
		Users entity = this.usersService.getById(((Users)session.getAttribute(SESSION_USER_KEY)).getId());
		if(entity == null){
			model.addAttribute("msg", "session失效，请重新登录");
			return jspUrl + "modifyPass.jsp";
		}
		
		if(usersVO != null && usersVO.getEntity() != null){
			if(StringUtils.isEmpty(usersVO.getEntity().getLoginName())
					|| StringUtils.isEmpty(usersVO.getEntity().getRealName())
					|| StringUtils.isEmpty(usersVO.getEntity().getTelepone())){
				model.addAttribute("passmsg", "基本信息不能为空！");
				return jspUrl + "modifyPass.jsp";
			}
			
			if("1".equals(op)){
				if(StringUtils.isEmpty(usersVO.getEntity().getPassword())
						|| StringUtils.isEmpty(usersVO.getNewPassword())){
					model.addAttribute("passmsg", "密码不能为空！");
					return jspUrl + "modifyPass.jsp";
				}
				String passMd5 = md5PasswordEncoder.encodePassword(usersVO.getEntity().getPassword(), null);
				if(!entity.getPassword().equals(passMd5)){
					model.addAttribute("passmsg", "原密码输入错误！");
					return jspUrl + "modifyPass.jsp";
				}
				if(!Users.checkVaildPassWord(usersVO.getNewPassword())){
					model.addAttribute("passmsg", "密码必须包含大小写字母+数字,长度要求8-20位！");
					return jspUrl + "modifyPass.jsp";
				}
				String newPassMd5 = md5PasswordEncoder.encodePassword(usersVO.getNewPassword(), null);
				usersVO.getEntity().setPassword(newPassMd5);
			}else{
				usersVO.getEntity().setPassword(null);
			}
			
			usersVO.getEntity().setId(entity.getId());
			usersService.modifyEntitySelective(usersVO.getEntity());
		}
		if(isWanDa(request)){
			return "index/wanda.jsp";
		}
		return "index/main.jsp";
	}
	
	@RequestMapping(value = {"/checkLoginName.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String checkLoginName(HttpServletRequest request,HttpServletResponse response,HttpSession session,@ModelAttribute("usersVO") UsersVO usersVO,BindingResult result,Model model)throws Exception {
		String loginName = request.getParameter("loginName");
		
		if(isUsersExtis(loginName)){
			response.setCharacterEncoding("UTF-8");   
			PrintWriter pw = response.getWriter();
			pw.write("1");//此用户名已使用
			pw.flush();
			pw.close() ;
			return null;	
		}else{
			response.setCharacterEncoding("UTF-8");
			//response.setContentType("text/xml;charset=utf-8");            
			//response.setHeader("Cache-Control","no-cache");
			PrintWriter pw = response.getWriter();
			pw.write("2");//恭喜，用户名可以使用
			pw.flush();
			pw.close() ;
			return null;
		}
	}
	
	private boolean isUsersExtis(String loginName) {
		Users user = new Users();
		user.setLoginName(loginName);
		com.hoolai.manage.vo.UsersVO usersVO = new com.hoolai.manage.vo.UsersVO(user);
		return isUsersExtis(usersVO);
	}

	private boolean isUsersExtis(com.hoolai.manage.vo.UsersVO usersVO){
		
		Users user = usersService.getUserByLoginName(usersVO);
		return user != null;
	}
}

