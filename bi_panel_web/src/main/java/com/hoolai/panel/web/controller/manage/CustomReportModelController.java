package com.hoolai.panel.web.controller.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.vo.PanelPagination;
import com.hoolai.manage.model.CustomReport;
import com.hoolai.manage.model.CustomReportModel;
import com.hoolai.manage.model.CustomReportTemColumns;
import com.hoolai.manage.model.CustomReportTemParams;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.CustomReportModelService;
import com.hoolai.manage.service.CustomReportService;
import com.hoolai.manage.service.CustomReportTemColumnsService;
import com.hoolai.manage.service.CustomReportTemParamsService;
import com.hoolai.manage.service.GroupUsersService;
import com.hoolai.manage.vo.CustomReportModelVO;
import com.hoolai.panel.web.controller.AbstractPanelController;
import com.jian.service.pagination.PaginationResult;

@Controller
@RequestMapping("/panel_manage/customReportModel")
public class CustomReportModelController extends AbstractPanelController{
	
	private Logger log = Logger.getLogger(CustomReportModelController.class);
	
	@Autowired
	private CustomReportModelService customReportModelService;
	@Autowired
	private GroupUsersService groupUserService;
	@Autowired
	private CustomReportService customReportService;
	@Autowired
	private CustomReportTemParamsService paramsService;
	@Autowired
	private CustomReportTemColumnsService columnsService;

	@RequestMapping(value = {"/toCustomReportModel.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toCustomReportModel(HttpServletRequest request,HttpSession session,@ModelAttribute("customReportModelVO") CustomReportModelVO  customReportModelVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		model.addAttribute("gameList", super.getSession(request, "games"));
		return toCustomReportModelList(1, request, session, customReportModelVO, result, model);
	}
	
	@RequestMapping(value = {"/toCustomReportManager.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toCustomReportManager(HttpServletRequest request,HttpSession session,@ModelAttribute("customReportModelVO") CustomReportModelVO  customReportModelVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		
		
		return "manage/customReportManager/customReportTaskList.jsp";
	}
	
	
	
	@RequestMapping(value = {"/toCustomReportModelList_{pageNo:\\d+}.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toCustomReportModelList(@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("customReportModelVO") CustomReportModelVO  customReportModelVO,BindingResult result,Model model)throws Exception {
		
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		String gameIdString = request.getParameter("gameId");
		String modelType = request.getParameter("modelType");
		
		if(!StringUtils.isEmpty(modelType) && !"-1".equals(modelType)){
			customReportModelVO.getEntity().setType(Integer.valueOf(modelType));
			model.addAttribute("modelType", modelType);
		}
		
		if(!StringUtils.isEmpty(gameIdString) && !"-1".equals(gameIdString)){
			long gameId = Long.valueOf(gameIdString);
			
			Games games = super.getSessionGames(request, gameId);
			if(games != null){
				customReportModelVO.setSnid(Integer.valueOf(games.getSnid()));
				customReportModelVO.setGameid(Integer.valueOf(games.getGameid()));
			}
			
			model.addAttribute("gameId", gameId);
		}
		
		PaginationResult<CustomReportModel> paginationResult = customReportModelService.getPaginationResult(customReportModelVO, pageNo, PAGE_SIZE);
		
		List<CustomReportModelVO> displayResultList = new ArrayList<CustomReportModelVO>();
		for(CustomReportModel customReportModel:paginationResult.getResults()){
			displayResultList.add(new CustomReportModelVO(customReportModel));
		}
		
		int count = new Long(paginationResult.getPagination().getRecordCount()).intValue();
		PanelPagination displayPagination = new PanelPagination(pageNo, PAGE_SIZE, count);
		
		model.addAttribute("displayResultList", displayResultList);
		model.addAttribute("pagination", displayPagination);
		
		return "manage/customReportModel/customReportModel.jsp";
	    }
	
	
	@RequestMapping(value = {"/toAddCustomReportModel.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toAddCustomReportModel(HttpServletRequest request,HttpSession session,@ModelAttribute("customReportModelVO") CustomReportModelVO  customReportModelVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		model.addAttribute("gameList", super.getSession(request, "games"));
		return "manage/customReportModel/addCustomReportModel.jsp";
	}
	
	@RequestMapping(value = {"/addCustomReportModel.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String addCustomReportModel(HttpServletRequest request,HttpSession session,@ModelAttribute("customReportModelVO") CustomReportModelVO  customReportModelVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		String gameIdString = request.getParameter("gameId");
		String modelTypeString = request.getParameter("modelType");
		String columnsString = request.getParameter("columns");
		String paramsString = request.getParameter("params");
		
		if(StringUtils.isEmpty(gameIdString)
			|| StringUtils.isEmpty(modelTypeString)
			|| StringUtils.isEmpty(columnsString)
			|| StringUtils.isEmpty(paramsString)
			|| "-1".equals(modelTypeString)
			|| ("1".equals(modelTypeString) && "-1".equals(gameIdString))){
			model.addAttribute("msg", "有必填项未填写！");
			return toAddCustomReportModel(request, session, customReportModelVO, result, model);
		}
		
		int modelType = Integer.valueOf(modelTypeString);
		CustomReportModel customReportModel = customReportModelVO.getEntity();
		if(customReportModelService.isExist(customReportModel.getCode()) > 0){
			model.addAttribute("msg", "此模板CODE已经存在，请重新编辑。");
			return toAddCustomReportModel(request, session, customReportModelVO, result, model);
		}
		customReportModel.setCreateTime(new Date());
		customReportModel.setType(modelType);
		customReportModelService.saveEntity(customReportModelVO.getEntity());
		
		// 如果是个性化模板存储映射表
		if(modelType == 1){
			long gameId = Long.valueOf(gameIdString);
			Games games = super.getSessionGames(request, gameId);
			if(games != null){
				CustomReport customReport = new CustomReport(customReportModel.getId(),
						Integer.valueOf(games.getSnid()),Integer.valueOf(games.getGameid()));
				customReportService.saveEntity(customReport);
			}
		}
		
		// 提取sql变量存储参数表
		insertParams(paramsString,customReportModel);
		
		// 存储返回值中文名称对应表
		insertColumns(columnsString, customReportModel);
		
		model.addAttribute("modelType", modelTypeString);
		model.addAttribute("gameId", gameIdString);
		return toCustomReportModelList(1, request, session, customReportModelVO, result, model);
	}

	private void insertParams(String columnsString,CustomReportModel customReportModel)
			throws Exception {
		String[] parms = columnsString.split(",");
		for(String parm:parms){
			String[] columns = parm.split(":");
			CustomReportTemParams reportTemParams = new CustomReportTemParams();
			reportTemParams.setParamCode(columns[0]);
			reportTemParams.setParamName(columns[1]);
			reportTemParams.setTemplateId(customReportModel.getId());
			paramsService.saveEntity(reportTemParams);
		}
	}

	private void insertColumns(String columnsString,
			CustomReportModel customReportModel) throws Exception {
		String[] columnsStrArr = columnsString.split(",");
		for(String columnStr:columnsStrArr){
			String[] columns = columnStr.split(":");
			CustomReportTemColumns temColumns = new CustomReportTemColumns();
			temColumns.setColumnCode(columns[0]);
			temColumns.setColumnName(columns[1]);
			temColumns.setTemplateId(customReportModel.getId());
			columnsService.saveEntity(temColumns);
		}
	}

	private Set<String> getParm(String sql) {
		Set<String> parm = new HashSet<String>();
		try {

			PatternCompiler orocom = new Perl5Compiler();
			Pattern pattern1 = orocom.compile("\\${[^}]+\\}");
			PatternMatcher matcher = new Perl5Matcher();

			PatternMatcherInput input = new PatternMatcherInput(sql);
			while (matcher.contains(input, pattern1)) {
				MatchResult result = matcher.getMatch();
				parm.add(result.group(0).replace("${", "").replace("}", ""));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return parm;
	}
	
	@RequestMapping(value = {"/getPrams.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getTempPram(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String templateSql = request.getParameter("templateSql");
		if(StringUtils.isEmpty(templateSql)){
			return ret;
		}
		
		ret.put("params", getParm(templateSql));
		return ret;
	}

	// delete  
	@RequestMapping(value = {"/customReportModelDel_{id:\\d+}_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String customReportModelDel (@PathVariable Long id,@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("customReportModelVO") CustomReportModelVO vo,BindingResult result,Model model)
			throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
	
		customReportModelService.removeById(id);
		paramsService.deleteByTemplateId(id);
		columnsService.deleteByTemplateId(id);

		return toCustomReportModelList(pageNo, request, session, vo, result, model);
	}
	
	@RequestMapping(value = {"/toCustomReportModelEdit_{id:\\d+}_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String toCustomReportModelEdit(@PathVariable Long id,@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("customReportModelVO") CustomReportModelVO customReportModelVO,BindingResult result,Model model)
			throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		String modelTypeStr = request.getParameter("type");
		CustomReportModel queryReportModel = new CustomReportModel(id,Integer.valueOf(modelTypeStr));
		CustomReportModelVO queryVO = new CustomReportModelVO(queryReportModel);
		queryVO.setTemplateId(id);
		
		CustomReportModelVO resultVO = customReportModelService.selectEditModel(queryVO);
		
		if(resultVO.getEntity().getType() == 1){
			Games games = super.getSessionGames(request, String.valueOf(resultVO.getSnid()), String.valueOf(resultVO.getGameid()));
			if(games != null){
				model.addAttribute("games", games);
			}
		}
		
		customReportModelVO.setEntity(resultVO.getEntity());
		customReportModelVO.setSnid(resultVO.getSnid());
		customReportModelVO.setGameid(resultVO.getGameid());
		customReportModelVO.setTemplateId(resultVO.getTemplateId());
		model.addAttribute("prePageNo", pageNo);
		model.addAttribute("columns", columnsService.selectColumnsByTemplateId(customReportModelVO.getEntity().getId()));
		model.addAttribute("params", paramsService.selectListByTemplateId(customReportModelVO.getEntity().getId()));
		return "manage/customReportModel/updateCustomReportModel.jsp";
	}
	
	@RequestMapping(value ={"/customReportModelEdit_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String customReportModelEdit(@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("customReportModelVO") CustomReportModelVO customReportModelVO,BindingResult result,Model model)
			throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		if(customReportModelVO == null || customReportModelVO.getEntity() == null){
			return toCustomReportModelList(pageNo, request, session, customReportModelVO, result, model);
		}else{
			// 在这判断vo中不能为空的字段
			CustomReportModel entity = customReportModelService.getById(customReportModelVO.getEntity().getId());
			if(entity == null){
				return toCustomReportModelList(pageNo, request, session, customReportModelVO, result, model);
			}
			
			boolean isSqlChange = !StringUtils.isEmpty(customReportModelVO.getIsSqlChange())
					&& "1".equals(customReportModelVO.getIsSqlChange());
			boolean isColumnChange = isSqlChange || (!StringUtils.isEmpty(customReportModelVO.getIsColumnChange())
					&& "1".equals(customReportModelVO.getIsColumnChange()));
			boolean isParamChange = isSqlChange || (!StringUtils.isEmpty(customReportModelVO.getIsParamChange())
					&& "1".equals(customReportModelVO.getIsParamChange()));
			if(isParamChange){
				// 提取sql变量存储参数表
				paramsService.deleteByTemplateId(customReportModelVO.getEntity().getId());
				insertParams(customReportModelVO.getParams(),customReportModelVO.getEntity());
			}
			if(isColumnChange){
				// 存储返回值中文名称对应表
				columnsService.deleteByTemplateId(customReportModelVO.getEntity().getId());
				insertColumns(customReportModelVO.getColumns(), customReportModelVO.getEntity());
			}
			if(!isSqlChange){
				customReportModelVO.getEntity().setTemplateSql(null);
			}
			customReportModelVO.getEntity().setCreateTime(new Date());
			customReportModelService.modifyEntitySelective(customReportModelVO.getEntity());
		}
		
		return toCustomReportModelList(pageNo, request, session, customReportModelVO, result, model);
	}
	
}
