package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.UserRetentionSourceLtv;
import com.hoolai.bi.report.vo.UserRetentionSourceLtvVO;
import com.hoolai.dao.GenericDao;


public interface UserRetentionSourceLtvDao extends GenericDao<UserRetentionSourceLtv, Long> {

	public List<UserRetentionSourceLtvVO> selectAvgInstallLTVDataList(
			UserRetentionSourceLtvVO ltvVO);
	
	public List<UserRetentionSourceLtvVO> selectAvgInstallRetentionDataList(
			UserRetentionSourceLtvVO ltvVO);

	public List<UserRetentionSourceLtvVO> selectAvgRoleRetentionDataList(
			UserRetentionSourceLtvVO ltvVO);

	public List<UserRetentionSourceLtvVO> selectAvgRoleChioceLTVDataList(
			UserRetentionSourceLtvVO ltvVO);

	public List<UserRetentionSourceLtvVO> selectHorizontal4InstallRetentionRateList(
			UserRetentionSourceLtvVO ltvVO);

	public List<UserRetentionSourceLtvVO> selectHorizontal4RoleRetentionRateList(
			UserRetentionSourceLtvVO ltvVO);

	public List<UserRetentionSourceLtvVO> selectHorizontal4InstallPayList(
			UserRetentionSourceLtvVO ltvVO);

	public List<UserRetentionSourceLtvVO> selectHorizontal4RolePayList(
			UserRetentionSourceLtvVO ltvVO);

	public List<UserRetentionSourceLtvVO> selectPaymentForBarList(UserRetentionSourceLtvVO ltvVO);

	public Long selectCount(UserRetentionSourceLtvVO userRetentionSourceLtvVO);

	public List<UserRetentionSourceLtvVO> selectAllInstallRetentionList(
			UserRetentionSourceLtvVO userRetentionSourceLtvVO);

	public List<UserRetentionSourceLtvVO> selectAllRoleRetentionList(
			UserRetentionSourceLtvVO userRetentionSourceLtvVO);

	public List<UserRetentionSourceLtvVO> selectAllRolePayList(
			UserRetentionSourceLtvVO userRetentionSourceLtvVO);

	public List<UserRetentionSourceLtvVO> selectAllInstallPayList(
			UserRetentionSourceLtvVO userRetentionSourceLtvVO);
    
	/**
	 * 金额   按角色--分渠道  
	 * 合计
	 * @param ltvVO
	 * @return
	 */
	UserRetentionSourceLtvVO selectHorizontal4TotalRolePayList(UserRetentionSourceLtvVO ltvVO);
	
	/**
	 * 注收比   按角色--分渠道  
	 * 合计
	 * @param ltvVO
	 * @return
	 */
	UserRetentionSourceLtvVO selectHorizontal4TotalRolePayList2(UserRetentionSourceLtvVO ltvVO);
	
	/**
	 * 注收比    按注册-分渠道    单渠道
	 * @param ltvVO
	 * @return
	 */
	UserRetentionSourceLtvVO selectHorizontal4TotalInstallPayList(UserRetentionSourceLtvVO ltvVO);
	
	/**
	 * 注收比    按注册-分渠道    单渠道  2017-4-17
	 * @param ltvVO
	 * @return
	 */
	UserRetentionSourceLtvVO selectHorizontal4TotalInstallPayList2(UserRetentionSourceLtvVO ltvVO);
	/**
	 * 注收比    按注册-分渠道  导出所有渠道的合计
	 * @return
	 */
	public List<UserRetentionSourceLtvVO> selectHorizontal4TotalInstallPayList_export(UserRetentionSourceLtvVO ltvVO);
	/**
	 * 
	 * 注收比  按创角-分渠道   导出所有渠道合计
	 * @return
	 */
	public List<UserRetentionSourceLtvVO> selectAllRolePayList_export(UserRetentionSourceLtvVO ltvVO);
}
