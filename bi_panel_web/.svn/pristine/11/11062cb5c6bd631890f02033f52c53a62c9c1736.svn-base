package com.hoolai.panel.web.controller.manage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoolai.bi.report.core.Constant;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.UserGame;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.UserGamesService;
import com.hoolai.bi.report.util.DateUtils;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.PanelPagination;
import com.hoolai.manage.model.DRelationship;
import com.hoolai.manage.model.Groups;
import com.hoolai.manage.model.SysConfig;
import com.hoolai.manage.model.TblGroup;
import com.hoolai.manage.model.TblGroupGameBind;
import com.hoolai.manage.model.TblGroupSnBind;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.GroupUsersService;
import com.hoolai.manage.service.GroupsService;
import com.hoolai.manage.service.SysConfigService;
import com.hoolai.manage.util.Types.SysConfigType;
import com.hoolai.manage.vo.SysConfigVO;
import com.hoolai.manage.vo.TblGroupVO;
import com.jian.service.pagination.PaginationResult;
import com.jian.tools.TimeUtils;
import com.jian.tools.util.JSONUtils;

@Controller
@RequestMapping("/panel_manage/gameManager")
public class GameManagerController extends AbstractManageController{
	
	private Logger log = Logger.getLogger(GameManagerController.class);
	
	@Autowired
	private GroupUsersService groupUserService;
	@Autowired
	private GamesService gamesService;
	@Autowired
	private GroupsService groupsService;
	@Autowired
	private UserGamesService userGamesService;
	@Autowired
	private SysConfigService sysConfigService;
	
	@RequestMapping(value = {"/toGameTypesList.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String toGameTypesList(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		return "manage/games/gameTypes.jsp";
	}
	
	@RequestMapping(value = {"/delGameTypes.ac"}, method = {RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> delGameTypes(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		Map<String,Object> ret = new HashMap<String, Object>();
		String code = request.getParameter("id");
		
		if(StringUtils.isEmpty(code)){
			ret.put("msg", "1");//内容不能为空，请重新输入！
			return ret;
		}
		
		SysConfig temp = sysConfigService.getById(Long.valueOf(code));
		if(temp == null){
			ret.put("msg", "1");//内容不能为空，请重新输入！
			return ret;
		}else{
			sysConfigService.removeById(Long.valueOf(temp.getId()));
		}
		
		ret.put("msg", "2");// 修改成功
		return ret;
	}
	
	@RequestMapping(value = {"/saveGameTypes.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> saveGameTypes(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		Map<String,Object> ret = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		//String configType = request.getParameter("configType");
		//String parentId = request.getParameter("parentId");
		
		if(StringUtils.isEmpty(name)
			//|| StringUtils.isEmpty(configType)
			//|| StringUtils.isEmpty(parentId)
			){
			ret.put("msg", "1");//内容不能为空，请重新输入！
			return ret;
		}
		
		SysConfig sysConfig = new SysConfig();
		sysConfig.setName(name);
		sysConfig.setConfigType(1);
		sysConfig.setParentId(11);
		if(StringUtils.isEmpty(id)){
			sysConfigService.saveEntity(sysConfig);
		}else{
			SysConfig temp = sysConfigService.getById(Long.valueOf(id));
			if(temp == null){
				sysConfigService.saveEntity(sysConfig);
			}else{
				sysConfig.setId(temp.getId());
				sysConfigService.modifyEntity(sysConfig);
			}
		}
		
		ret.put("msg", "2");// 修改成功
		ret.put("sysConfig", sysConfig);
		return ret;
	}
	
	@RequestMapping(value = {"/getGameTypes.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameTypes(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		Map<String,Object> ret = new HashMap<String, Object>();
 
		Map<String,Object> pageInfo = this.findPageInfo(request);
		
		SysConfig sysConfig = new SysConfig();
		SysConfigVO sysConfigVO = new SysConfigVO(sysConfig);
		sysConfig.setConfigType(1);//游戏类型
		
		//总记录数
		long recordsTotal = sysConfigService.selectCount(sysConfigVO);
		
		// 过滤记录数
		sysConfigVO.setSearchValue((String) pageInfo.get("searchValue"));
		long recordsFiltered = sysConfigService.selectCount(sysConfigVO);
		
		// 分页数据
		sysConfigVO.setOffset((Long) pageInfo.get("start"));
		sysConfigVO.setRows((Long) pageInfo.get("length"));
		sysConfigVO.setOrderValue("order by id asc");
		
		ret.put("recordsTotal", recordsTotal);
		ret.put("recordsFiltered", recordsFiltered);
		ret.put("data", sysConfigService.selectList(sysConfigVO));
		return ret;
	}
	
	@RequestMapping(value = {"/toGameList.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String toGameList(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		return gameList(1,request, session, gamesVO, result, model);
	}
	
	@RequestMapping(value = {"/gameList_{pageNo:\\d+}.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String gameList(@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		PaginationResult<Games> paginationResult = gamesService.getPaginationResult(gamesVO, pageNo, PAGE_SIZE);
		
		List<GamesVO> displayResultList = new ArrayList<GamesVO>();
		for(Games game:paginationResult.getResults()){
			displayResultList.add(new GamesVO(game));
		}
		
		int count = new Long(paginationResult.getPagination().getRecordCount()).intValue();
		PanelPagination displayPagination = new PanelPagination(pageNo, PAGE_SIZE, count);
		
		model.addAttribute("displayResultList", displayResultList);
		model.addAttribute("pagination", displayPagination);
		
		return "manage/games/games.jsp";
	}
	
	@RequestMapping(value = {"/toGameEdit_{id:\\d+}_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String gameEditGet(@PathVariable Long id,@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO vo,BindingResult result,Model model)
			throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		Games games = gamesService.getById(id);
		if(games.getOnlineDate() != null){
			vo.setOnlineDate(com.jian.tools.util.DateUtils.format(games.getOnlineDate(), "yyyy-MM-dd"));
		}
		vo.setEntity(games);
		
		fillListData(model);
		model.addAttribute("gameGroupList",toGameString(groupsService.getGroupsByGameId(games.getId())));
		model.addAttribute("prePageNo", pageNo);
		return "manage/games/updateGame.jsp";
	}
	
	private String toGameString(List<Groups> Groups){
		StringBuffer buf = new StringBuffer();
		for(Groups group:Groups){
			buf.append(group.getId()+",");
		}
		return buf.toString();
	}
	
	@RequestMapping(value ={"/gameEdit_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String gameEditPost(@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO vo,BindingResult result,Model model)
			throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		//检查传入ID是否正确
		Games entity = gamesService.getById(vo.getEntity().getId());
		if(entity == null || vo == null || checkAddGamesIsNull(vo, model) != null){
			return this.gameEditGet(entity.getId(), pageNo, request, session, vo, result, model);
		}
		
		//检查游戏是否下线
		if(!StringUtils.isEmpty(vo.getEntity().getStats())
			&& "0".equals(vo.getEntity().getStats())){
			userGamesService.removeByGameId(entity.getId().intValue());
			gamesService.modifyEntitySelective(vo.getEntity());
			return gameList(1, request, session, vo, result, model);
		}
		
		//检查logo文件是否更改
		moveFilePath4Edit(request, vo, entity);
		
		// 检查上线日期是否更改
		if(!StringUtils.isEmpty(vo.getOnlineDate())){
			vo.getEntity().setOnlineDate(DateUtils.parseDate(vo.getOnlineDate(), "yyyy-MM-dd"));
		}
		
		// 兼容游戏时PC端时，系统为默认。
		if(vo.getEntity().getTerminalType() == 0){
			vo.getEntity().setSystemType(0l);
		}
		
		// 修改
		gamesService.modifyEntitySelective(vo.getEntity());
		
		
		// 检查是否需要更新权限
		if(!StringUtils.isEmpty(vo.getGameManageUsers())){
			if("1".equals(vo.getEntity().getStats().trim())){
				gamesGroup(vo);
			}
		}
		
		//检查货币比率是否改变，同步给ETL
		if(vo.getEntity().getRate().doubleValue() != entity.getRate().doubleValue()
				|| !vo.getEntity().getCurrency().equals(entity.getCurrency())){
			this.synGameRate();
		}
		
		return gameList(1, request, session, vo, result, model);
	}

	private void moveFilePath4Edit(HttpServletRequest request, GamesVO vo,Games entity) throws IOException {
		if(!StringUtils.isEmpty(vo.getEntity().getLogo())){
		   //删除临时目录图片，移入正式目录
		   String realPath = request.getSession().getServletContext().getRealPath("/static");
		   String filePath = vo.getEntity().getLogo();
		   
		   String newFilePath = filePath.replace("/temp", "");
		   
		   if(!entity.getLogo().equals(newFilePath)){
			   if(!StringUtils.isEmpty(entity.getLogo())){
				   new File(Constant.UPLOAD_PATH+"/upload"+entity.getLogo()).delete();
			   }
			   
			   File file = new File(realPath+filePath);
			   if(file.exists()){
				   FileUtils.copyFile(file, new File(Constant.UPLOAD_PATH+"/upload"+newFilePath));
				   file.delete();
				   vo.getEntity().setLogo(newFilePath);
			   
			   }
		   }
		}
	}

	
	
	@RequestMapping(value = {"/gameDel_{id:\\d+}_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String gameDel(@PathVariable Long id,@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO vo,BindingResult result,Model model)
			throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		gamesService.removeById(id);
		userGamesService.removeByGameId(id.intValue());
		return gameList(pageNo, request, session, vo, result, model);
	}
	
	@RequestMapping(value = {"/toAddGame.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String toAddGame(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO vo,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		fillListData(model);
		return "manage/games/addGame.jsp";
	}
	
	@RequestMapping(value = {"/addGame.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String addGame(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO vo,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		if(checkAddGamesIsNull(vo, model) != null){
			return "manage/games/addGame.jsp";
		}
		Games games = gamesService.getGames(vo.getEntity().getSnid(), vo.getEntity().getGameid());
		if(games == null){
			moveFilePath4Add(request,vo);
			
			if(!StringUtils.isEmpty(vo.getOnlineDate())){
				vo.getEntity().setOnlineDate(DateUtils.parseDate(vo.getOnlineDate(), "yyyy-MM-dd"));
			}
			if(vo.getEntity().getTerminalType() == 0){
				vo.getEntity().setSystemType(0l);
			}
			gamesService.saveEntity(vo.getEntity());
			gamesGroup(vo);
		}
		synGameRate();
		
		return this.gameList(1, request, session, vo, result, model);
	}
	
	@RequestMapping(value = {"/synExchangeRate.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String synExchangeRate(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO vo,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		synExchangeRate();
		model.addAttribute("synStatus", "1");
		
		return this.gameList(1, request, session, vo, result, model);
	}

	private void synExchangeRate() throws InterruptedException, IOException {
        if(Constant.IS_PUBLISHED){
			
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        	String date = df.format(TimeUtils.currentTimeMillis());
			int shell = Runtime.getRuntime().exec("/usr/local/services/biutility/bin/python data/exchange-rate/exchangeRate_new.py "+date).waitFor();
			if(shell == 0){
				log.info("call game_rate_shell(python data/exchange-rate/exchangeRate_new.py)error!");
			}
		}
	}
	
	private void synGameRate() throws Exception, InterruptedException,
			IOException {
		if(Constant.IS_PUBLISHED){
			
			List<Games> gameList = gamesService.selectAllGames();
			File file = new File(Constant.SYN_GAME_RATE_FILE_PATH+"/games.txt");
            try {
				file.delete();
			} catch (Exception e1) {
				log.error("文件不存在，或已经被删除！");
			}
			   
            try{
			   FileWriter fw = new FileWriter(file.getAbsoluteFile());
			   BufferedWriter bw = new BufferedWriter(fw);
			   for(Games game:gameList){
				   bw.write(game.getSnid()+"\t"+game.getGameid()+"\t"
						   + game.getName()+"\t"+game.getRate()+"\t"
						   + game.getCurrency()+"\n");
			   }
			   
			   bw.close();
			  } catch (IOException e) {
			     e.printStackTrace();
			  }
			
			int shell = Runtime.getRuntime().exec("python "+Constant.SYN_GAME_RATE_SHELL+" "+file.getAbsoluteFile()).waitFor();
			if(shell == 0){
				log.info("call game_rate_shell(python "+Constant.SYN_GAME_RATE_SHELL+") error!");
			}
		}
	}

	private void gamesGroup(GamesVO vo) throws Exception {
		String[] gameManageUsers = StringUtils.split(vo.getGameManageUsers(), ",");
		if(gameManageUsers.length > 0){
			vo.setGameManageUsers(Groups.GAME_BASE_GROUP);
			userGamesService.removeByGroupId(vo);
			for(String auth:gameManageUsers){
				List<Long> userIds = groupUserService.getUsersByGroupId(Long.valueOf(auth));
				for(long userId:userIds){
					UserGame userGame = new UserGame(userId,vo.getEntity().getId().intValue());
					userGamesService.saveEntity(userGame);
				}
			}
		}
	}
	
	

	private void moveFilePath4Add(HttpServletRequest request,GamesVO vo) throws IOException {
		if(!StringUtils.isEmpty(vo.getEntity().getLogo())){
			//删除临时目录图片，移入正式目录
			 String realPath = request.getSession().getServletContext().getRealPath("/static");
			String filePath = vo.getEntity().getLogo();
			String newFilePath = filePath.replace("/temp", "");
			FileUtils.copyFile(new File(realPath+filePath), new File(Constant.UPLOAD_PATH+"/upload"+newFilePath));
			new File(realPath+filePath).delete();
			vo.getEntity().setLogo(newFilePath);
		}
	}

	private String checkAddGamesIsNull(GamesVO vo, Model model) {
		if(vo != null){
			if(StringUtils.isEmpty(vo.getEntity().getName())
			|| vo.getEntity().getRate() == null || vo.getEntity().getRate() == 0
			|| StringUtils.isEmpty(vo.getEntity().getCurrency())
			|| StringUtils.isEmpty(vo.getEntity().getSnid())
			|| StringUtils.isEmpty(vo.getEntity().getGameid())
		    || StringUtils.isEmpty(vo.getEntity().getStats())
		    || vo.getEntity().getParentId() == null || vo.getEntity().getParentId() == 0){
				model.addAttribute("msg", "带*字段不能为空");
				return "null";
		   }
		}else{
			model.addAttribute("msg", "系统出错");
			return "error";
		}
		return null;
	}
	
	private void fillListData(Model model) throws Exception {
		List<Groups> groups = groupsService.selectGameGroups();
		SysConfig sysConfig = new SysConfig();
		
		sysConfig.setConfigType(SysConfigType.PART_OF_GAME.value());
		List<SysConfig> gamesTypes = sysConfigService.selectGamesType(new SysConfigVO(sysConfig));
		
		model.addAttribute("groupsList", groups);
		model.addAttribute("gamesTypes",gamesTypes);
	}
	
	@RequestMapping(value = "/uf.ac", method = {RequestMethod.POST })
	public Map<String, Object> uploadFile(HttpServletRequest request,
			HttpServletResponse response, @RequestParam MultipartFile imageFile,
			Model model) throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String realPath = request.getSession().getServletContext().getRealPath("/static");
		PrintWriter out = response.getWriter();
		Calendar calendar = Calendar.getInstance();
		String filePath =  "/manage/images/temp/"+calendar.getTimeInMillis()+".jpg";
		String realFilePath = realPath + filePath;
		
		String oldImagePath = request.getParameter("oldImageFile");
		String oldRealPath = realPath + oldImagePath;
		
		try {
			FileUtils.copyInputStreamToFile(imageFile.getInputStream(),new File(realFilePath));
			if(oldRealPath.indexOf("/temp") > 0){
				new File(oldRealPath).delete();
			}
		} catch (Exception e) {
            e.printStackTrace();
		}
		ret.put("filePath", filePath);
		out.print("0"  + JSONUtils.toJSON(ret));
		out.flush();
		return null;
	}

	
}
