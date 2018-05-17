package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.whaleUser.PayUserRetentionLtv;
import com.hoolai.bi.report.vo.PayUserRetentionLtvVO;
import com.jian.service.GenericService;

public interface PayUserRetentionLtvService extends GenericService<PayUserRetentionLtv, Long>{

	Long selectCount(PayUserRetentionLtvVO retentionVO);

	List<PayUserRetentionLtvVO> selectList(PayUserRetentionLtvVO retentionVO);

	List<PayUserRetentionLtvVO> selectAvgRetentionDataList(PayUserRetentionLtvVO retentionVO);

}
