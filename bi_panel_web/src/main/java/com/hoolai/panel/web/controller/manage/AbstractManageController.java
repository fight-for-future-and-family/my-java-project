package com.hoolai.panel.web.controller.manage;

import org.springframework.web.bind.annotation.RequestMapping;

import com.hoolai.panel.web.controller.AbstractPanelController;

@RequestMapping("/panel_manage")
public abstract class AbstractManageController extends AbstractPanelController{
	
	protected String processManageUse(String url) {
		return "/panel_manage"+url;
	}
	
	
}
