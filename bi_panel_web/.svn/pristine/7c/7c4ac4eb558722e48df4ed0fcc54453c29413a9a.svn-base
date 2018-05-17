package com.hoolai.panel.web.controller.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoolai.bi.report.vo.PanelPagination;
import com.hoolai.manage.model.EtlengineManage;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.EtlengineManageService;
import com.hoolai.manage.service.GroupUsersService;
import com.hoolai.manage.vo.EtlengineManageVO;
import com.hoolai.panel.web.controller.AbstractPanelController;
import com.jian.service.pagination.PaginationResult;

@Controller
@RequestMapping("/panel_manage/EtlengineManage")
public class EtlengineManageController extends AbstractPanelController{
	
	private Logger log = Logger.getLogger(EtlengineManageController.class);
	
	@Autowired
	private EtlengineManageService etlengineManageService;
	@Autowired
	private GroupUsersService groupUserService;

	private EtlengineManageVO  etlengineManageVO;

	//  ==================================================
	
	@RequestMapping(value = {"/toEtlengineManage.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toEtlengineManage(HttpServletRequest request,HttpSession session,@ModelAttribute("etlengineManageVO") EtlengineManageVO  etlengineManageVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}

		return toEtlengineManageList(1, request, session, etlengineManageVO, result, model);
	}
	
	@RequestMapping(value = {"/toEtlengineManageList_{pageNo:\\d+}.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toEtlengineManageList(@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("etlengineManageVO") EtlengineManageVO  etlengineManageVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		PaginationResult<EtlengineManage> paginationResult = etlengineManageService.getPaginationResult(etlengineManageVO, pageNo, PAGE_SIZE);
		
		List<EtlengineManageVO> displayResultList = new ArrayList<EtlengineManageVO>();
		for(EtlengineManage etlengineManage:paginationResult.getResults()){
			displayResultList.add(new EtlengineManageVO(etlengineManage));
		}
		
		int count = new Long(paginationResult.getPagination().getRecordCount()).intValue();
		PanelPagination displayPagination = new PanelPagination(pageNo, PAGE_SIZE, count);
		
		model.addAttribute("displayResultList", displayResultList);
		model.addAttribute("pagination", displayPagination);
		
		return "manage/EtlengineManage/EtlengineManage.jsp";
		}
	
	@RequestMapping(value = {"/toAddEtlengineManage.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toAddCustomReport(HttpServletRequest request,HttpSession session,@ModelAttribute("etlengineManageVO") EtlengineManageVO  etlengineManageVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		return "manage/EtlengineManage/addEtlengineManage.jsp";
	}
	@RequestMapping(value = {"/AddEtlengineManage.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String addCustomReport(HttpServletRequest request,HttpSession session,@ModelAttribute("etlengineManageVO") EtlengineManageVO  etlengineManageVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		etlengineManageVO.getEntity().setModifyAt(new Date());
		etlengineManageVO.getEntity().setAddedAt(new Date());
		etlengineManageService.saveEntity(etlengineManageVO.getEntity());
		return toEtlengineManageList(1, request, session, etlengineManageVO, result, model);
	}
	
	
	@RequestMapping(value = {"/toEtlengineManageDel_{id:\\d+}_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String toEtlengineManagetDel (@PathVariable Long id,@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("etlengineManageVO") EtlengineManageVO vo,BindingResult result,Model model)
			throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		etlengineManageService.removeById(id);

		return toEtlengineManageList(pageNo, request, session, vo, result, model);
	}
	
	
	
	@RequestMapping(value = {"/toEtlengineManageEdit_{id:\\d+}_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String toEtlengineManageEdit(@PathVariable Long id,@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("etlengineManageVO") EtlengineManageVO vo,BindingResult result,Model model)
			throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		vo.setEntity(etlengineManageService.getById(id));
		model.addAttribute("prePageNo", pageNo);
		return "manage/EtlengineManage/updateEtlengineManage.jsp";
	}
	
	@RequestMapping(value ={"/EtlengineManageEdit_{pageNo:\\d+}.ac"},  method = { RequestMethod.GET,RequestMethod.POST })
	public String etlengineManageEdit(@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("etlengineManageVO") EtlengineManageVO vo,BindingResult result,Model model)
			throws Exception {
		
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		EtlengineManage entity = etlengineManageService.getById(Long.valueOf(vo.getEntity().getId()));
		if(entity == null || vo == null){
			return this.toEtlengineManageEdit(Long.valueOf(entity.getId()), pageNo, request, session, vo, result, model);
		}
		
		vo.getEntity().setModifyAt(new Date());
		
		etlengineManageService.modifyEntitySelective(vo.getEntity());
		
		vo.setEntity(null);
		return toEtlengineManageList(pageNo, request, session, vo, result, model);
	}
}
