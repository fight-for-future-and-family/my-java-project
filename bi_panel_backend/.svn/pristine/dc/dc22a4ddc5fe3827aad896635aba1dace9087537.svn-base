package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.RealTimeGameSource;
import com.hoolai.bi.report.vo.RealTimeGameClientVo;
import com.hoolai.bi.report.vo.RealTimeGameSourceVo;
import com.hoolai.bi.report.vo.RealTimeGameVo;
import com.hoolai.dao.GenericDao;

public interface RealTimeGameSourceDao extends GenericDao<RealTimeGameSource, Long> {

	//保存分渠道数据
	int saveRealTimeSourceDataList(List<RealTimeGameSource> realTimeGameSource);
	//查询分渠道
	List<RealTimeGameSourceVo> getSelectGamesSourceList(RealTimeGameSourceVo realTimeGameSourceVo);
	
	//分渠道图
	List<RealTimeGameSourceVo> getSelectGamesSourceIconList(RealTimeGameSourceVo realTimeGameSourceVo);
	//总行数
	long getSelectGamesSourceCount(RealTimeGameSourceVo realTimeGameSourceVo);
	
	//清空表数据
	void deleteRealTimeSourceData(RealTimeGameSourceVo realTimeGameSourceVo);
	//查询表中是否有数据
	long getSelectCountSourcedata(RealTimeGameSourceVo realTimeGameSourceVo);
	
	String getSelectMaxDau(RealTimeGameSourceVo realTimeGameSourceVo);

    public List<String> selectGameSources(RealTimeGameSourceVo realTimeGameSourceVo);
}
