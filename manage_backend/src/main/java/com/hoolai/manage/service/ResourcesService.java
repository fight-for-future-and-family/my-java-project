package com.hoolai.manage.service;

import java.util.List;

import com.hoolai.manage.model.Resources;
import com.jian.service.GenericService;

public interface ResourcesService extends GenericService<Resources, Long> {

	List<Resources> getResourcesByAuthorityId(Long authId);
	
	List<Resources> getResourcesByUserId(Long userId);
	
}
