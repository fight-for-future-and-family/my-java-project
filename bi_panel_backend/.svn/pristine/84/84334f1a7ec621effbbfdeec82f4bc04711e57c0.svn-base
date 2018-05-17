package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.UserRetentionLtv;
import com.hoolai.bi.report.vo.UserRetentionLtvVO;
import com.hoolai.dao.GenericDao;


public interface UserRetentionLtvDao extends GenericDao<UserRetentionLtv, Long> {

	/**
	 * 安装、注收比
	 * @param ltvVO
	 * @return
	 */
    List<UserRetentionLtvVO> selectAvgInstallLTVDataList(UserRetentionLtvVO ltvVO);
	
    /**
     * 安装留存
     * @param ltvVO
     * @return
     */
	List<UserRetentionLtvVO> selectAvgInstallRetentionDataList(UserRetentionLtvVO ltvVO);
	
	/**
	 * 创角留存
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionLtvVO> selectAvgRoleRetentionDataList(UserRetentionLtvVO ltvVO);
	
	/**
	 * 创角、创收比
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionLtvVO> selectAvgRoleChioceLTVDataList(UserRetentionLtvVO ltvVO);

	/**
	 * 安装，留存横表
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionLtvVO> selectHorizontal4InstallRetentionRateList(UserRetentionLtvVO ltvVO);
	
	/**
	 * 创角，留存横表
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionLtvVO> selectHorizontal4RoleRetentionRateList(UserRetentionLtvVO ltvVO);
	
	/**
	 * 安装，付费横表
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionLtvVO> selectHorizontal4InstallPayList(UserRetentionLtvVO ltvVO);
	/**
	 * 创角，付费横表
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionLtvVO> selectHorizontal4RolePayList(UserRetentionLtvVO ltvVO);
	
	/**
	 * 金额比汇总数据
	 * @date 2017.3.15
	 * @param ltvVO
	 * @return
	 */
	UserRetentionLtvVO selectHorizontal4TotalPayList(UserRetentionLtvVO ltvVO); 
	
	/**
	 * 注收比  汇总数据  按注册
	 * @date 2017.4.17
	 * @param ltvVO
	 * @return
	 */
	UserRetentionLtvVO selectHorizontal4TotalPayList2(UserRetentionLtvVO ltvVO); 
	/**
	 * 付费金额
	 * @date 2017.3.15
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionLtvVO> selectHorizontal4MoneyPayList(UserRetentionLtvVO ltvVO);
	/**
	 * 金额汇总数据
	 * @date 2017.3.15
	 * @param ltvVO
	 * @return
	 */
	UserRetentionLtvVO selectHorizontal4MoneyTotalPayList(UserRetentionLtvVO ltvVO); 
	/**
	 * 金额     按角色-总览
	 * @param ltvVO
	 * @return
	 */
	UserRetentionLtvVO selectHorizontal4TotalRolePayList(UserRetentionLtvVO ltvVO); 
	
	/**
	 * 注收比     按角色-总览22
	 * @param ltvVO
	 * @return
	 */
	UserRetentionLtvVO selectHorizontal4TotalRolePayList2(UserRetentionLtvVO ltvVO);

	List<UserRetentionLtvVO> selectClientUserRetentionLtv(UserRetentionLtvVO userRetentionLtvVO);

	List<UserRetentionLtvVO> selectClientUserRetentionLtvInstallUserCount(UserRetentionLtvVO userRetentionLtvVO); 
}
