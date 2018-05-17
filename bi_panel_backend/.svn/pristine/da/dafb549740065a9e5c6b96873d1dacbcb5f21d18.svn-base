package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.UserRetentionClientLtv;
import com.hoolai.bi.report.vo.UserRetentionClientLtvVO;
import com.hoolai.dao.GenericDao;


public interface UserRetentionClientLtvDao extends GenericDao<UserRetentionClientLtv, Long> {

	List<UserRetentionClientLtvVO> selectAvgDataList(UserRetentionClientLtvVO userRetentionClientLtvVO);

	List<UserRetentionClientLtvVO> selectHorizontal4RateList(UserRetentionClientLtvVO userRetentionClientLtvVO);

	List<UserRetentionClientLtvVO> selectHorizontalList(UserRetentionClientLtvVO userRetentionClientLtvVO);

	Long selectCount(UserRetentionClientLtvVO userRetentionClientLtvVO);

	List<UserRetentionClientLtvVO> selectAllInstallRetentionList(
			UserRetentionClientLtvVO userRetentionClientLtvVO);

	List<UserRetentionClientLtvVO> selectAllInstallPayList(
			UserRetentionClientLtvVO userRetentionClientLtvVO);

	/**
	 * 
	 * // 注收比  分服   一个服务器  
	 * 合计
	 * @param userRetentionClientLtvVO
	 * @return
	 */
	UserRetentionClientLtvVO selectHorizontalTotalList(UserRetentionClientLtvVO userRetentionClientLtvVO);
	/**
	 * 注收比   分服   导出各个分服的合计
	 * @param userRetentionClientLtvVO
	 * @return
	 */
	List<UserRetentionClientLtvVO> selectAllInstallPayList_exporp(UserRetentionClientLtvVO userRetentionClientLtvVO);
}
