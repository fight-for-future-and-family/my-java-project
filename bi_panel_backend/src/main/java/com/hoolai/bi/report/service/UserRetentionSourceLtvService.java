package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.UserRetentionSourceLtv;
import com.hoolai.bi.report.vo.UserRetentionSourceLtvVO;
import com.jian.service.GenericService;

public interface UserRetentionSourceLtvService extends GenericService<UserRetentionSourceLtv, Long>{
	/**
	 * 安装、注收比
	 * @param ltvVO
	 * @return
	 */
    List<UserRetentionSourceLtvVO> selectAvgInstallLTVDataList(UserRetentionSourceLtvVO ltvVO);
	
    /**
     * 安装留存
     * @param ltvVO
     * @return
     */
	List<UserRetentionSourceLtvVO> selectAvgInstallRetentionDataList(UserRetentionSourceLtvVO ltvVO);
	
	/**
	 * 创角留存
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionSourceLtvVO> selectAvgRoleRetentionDataList(UserRetentionSourceLtvVO ltvVO);
	
	/**
	 * 创角、创收比
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionSourceLtvVO> selectAvgRoleChioceLTVDataList(UserRetentionSourceLtvVO ltvVO);

	/**
	 * 安装，留存横表
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionSourceLtvVO> selectHorizontal4InstallRetentionRateList(UserRetentionSourceLtvVO ltvVO);
	
	/**
	 * 创角，留存横表
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionSourceLtvVO> selectHorizontal4RoleRetentionRateList(UserRetentionSourceLtvVO ltvVO);
	
	/**
	 * 安装，付费横表
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionSourceLtvVO> selectHorizontal4InstallPayList(UserRetentionSourceLtvVO ltvVO);
	
	/**
	 * 注收比    按注册-分渠道    一个渠道
	 * @param ltvVO
	 * @return
	 */
	UserRetentionSourceLtvVO selectHorizontal4TotalInstallPayList(UserRetentionSourceLtvVO ltvVO);

	
	/**
	 * 创角，付费横表
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionSourceLtvVO> selectHorizontal4RolePayList(UserRetentionSourceLtvVO ltvVO);
	
	
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
	 * 注收比    按注册-分渠道    单渠道  2017-4-17
	 * @param ltvVO
	 * @return
	 */
	UserRetentionSourceLtvVO selectHorizontal4TotalInstallPayList2(UserRetentionSourceLtvVO ltvVO);
	/**
	 * 总览分渠道排名
	 * @param ltvVO
	 * @return
	 */
	List<UserRetentionSourceLtvVO> selectPaymentForBarList(UserRetentionSourceLtvVO ltvVO);

	/**
	 * 所有渠道查询
	 * @param userRetentionSourceLtvVO
	 * @return
	 */
	long selectCount(UserRetentionSourceLtvVO userRetentionSourceLtvVO);

	/**
	 * 所有渠道查询 按创角
	 * @param userRetentionSourceLtvVO
	 * @return
	 */
	List<UserRetentionSourceLtvVO> selectAllRoleRetentionList(UserRetentionSourceLtvVO userRetentionSourceLtvVO);

	/**
	 * 所有渠道查询 按注册
	 * @param userRetentionSourceLtvVO
	 * @return
	 */
	List<UserRetentionSourceLtvVO> selectAllInstallRetentionList(UserRetentionSourceLtvVO userRetentionSourceLtvVO);
	/**
	 * 所有渠道查询 按创角
	 * @param userRetentionSourceLtvVO
	 * @return
	 */
	List<UserRetentionSourceLtvVO> selectAllRolePayList(UserRetentionSourceLtvVO userRetentionSourceLtvVO);
	
	/**
	 * 所有渠道查询 按注册
	 * @param userRetentionSourceLtvVO
	 * @return
	 */
	List<UserRetentionSourceLtvVO> selectAllInstallPayList(UserRetentionSourceLtvVO userRetentionSourceLtvVO);

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
