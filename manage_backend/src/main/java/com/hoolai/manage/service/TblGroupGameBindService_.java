package com.hoolai.manage.service;

import com.hoolai.manage.model.TblGroupGameBind;
import com.jian.service.GenericService;

public interface TblGroupGameBindService_ extends GenericService<TblGroupGameBind, Long> {

	boolean isHaveAdminAuth(Integer valueOf);

}
