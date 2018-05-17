package com.hoolai.bi.report.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.EquipSourceDayDao;
import com.hoolai.bi.report.model.entity.equip.EquipSourceDay;
import com.hoolai.bi.report.service.EquipSourceDayService;
import com.hoolai.bi.report.vo.EquipSourceDayVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class EquipSourceDayServiceImpl extends GenericServiceImpl<EquipSourceDay, Long> implements EquipSourceDayService {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private EquipSourceDayDao entityDao;
	
	@Override
    public GenericDao<EquipSourceDay, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public Long selectCount(EquipSourceDayVO equipSourceDayVO) {
		try {
			return this.entityDao.selecCount(equipSourceDayVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public List<EquipSourceDay> selectList(EquipSourceDayVO equipSourceDayVO) {
		String col = equipSourceDayVO.getOrderCol();
		String orderType = StringUtils.isEmpty(equipSourceDayVO.getOrderType()) ? "desc" : equipSourceDayVO.getOrderType();
		if(StringUtils.isEmpty(col)){
			equipSourceDayVO.setOrderValue(" order by ds "+orderType);
		}else{
			String orderCol = "ds";
			int colInt = Integer.valueOf(col);
			switch(colInt){
			case 0:
			case 1:
				orderCol = "ds";
				break;
			case 2:
				orderCol = "dau";
				break;
			case 3:
				orderCol = "new_equip";
				break;
			case 4:
				orderCol = "install";
				break;
			case 5:
				orderCol = "dau-new_equip";
				break;
			case 6:
				orderCol = "install/new_equip";
				break;
			case 7:
				orderCol = "uninstall";
				break;
			}
			equipSourceDayVO.setOrderValue(" order by "+orderCol+" "+orderType);
		}
		
		return this.entityDao.selectList(equipSourceDayVO);
	}

	@Override
	public List<String> selectEquipSources(EquipSourceDayVO equipSourceDayVO) {
		return this.entityDao.selectEquipSources(equipSourceDayVO);
	}

	@Override
	public List<EquipSourceDayVO> selectInsatll4Pie(EquipSourceDayVO equipSourceDayVO) {
		return this.entityDao.selectInsatll4Pie(equipSourceDayVO);
	}

	@Override
	public List<EquipSourceDayVO> selectDau4Pie(
			EquipSourceDayVO equipSourceDayVO) {
		return this.entityDao.selectDau4Pie(equipSourceDayVO);
	}

	@Override
	public List<EquipSourceDayVO> selectNewEquip4Pie(
			EquipSourceDayVO equipSourceDayVO) {
		return this.entityDao.selectNewEquip4Pie(equipSourceDayVO);
	}

	@Override
	public List<EquipSourceDayVO> selectNewEquip4Line(
			EquipSourceDayVO equipSourceDayVO) {
		try {
			String queryType = "new_equip";
			int index = concatSql(equipSourceDayVO, queryType);
			List<Map<String,String>> list = this.entityDao.selectNewEquip4Line(equipSourceDayVO);
			return exchangeList(index, list);
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private int concatSql(EquipSourceDayVO equipSourceDayVO, String queryType)
			throws ParseException {
		StringBuffer sb = new StringBuffer("select * ");
		//verticalToHorizontal1(queryType, equipSourceDayVO, sb);
		sb.append(" from ( select model, ");
		int index = verticalToHorizontal(queryType, equipSourceDayVO, sb);
		sb.append(" from ( select ds, model,sum("+queryType+") "+queryType);
		sb.append(" from equip_model_day where ");
		screeningConditions(equipSourceDayVO, sb);
		sb.append(" group by ds,model) c");
		sb.append(" group by model ");
		sb.append("order by sumData desc ");
		sb.append(" limit 0,5) gg union all select '总和' as model,");
		verticalToHorizontal(queryType, equipSourceDayVO, sb);
		sb.append(" from ( select ds, sum("+queryType+") "+queryType);
		sb.append(" from equip_model_day where ");
		screeningConditions(equipSourceDayVO, sb);
		sb.append(" group by ds) a");
		sb.append(" group by model ");
		
		equipSourceDayVO.setSearchValue(sb.toString());
		return index;
	}

	private List<EquipSourceDayVO> exchangeList(int index,
			List<Map<String, String>> list) {
		List<EquipSourceDayVO> equipModelDayVOs = new ArrayList<EquipSourceDayVO>();
		int[] qita = new int[index];
		for(Map<String,String> map:list){
			EquipSourceDayVO modelDayVO = new EquipSourceDayVO();
			Integer[] modelArr = new Integer[index];
			
			String name = map.get("model");
			if("总和".equals(name)){
				for(int i=0;i<index;i++){
					Object o = map.get("day"+(i+1));
					String dayData = o ==null ?"":String.valueOf(o);
					if(StringUtils.isEmpty(dayData)){
						modelArr[i] = 0;
					}else{
						modelArr[i] = Integer.valueOf(dayData).intValue() - qita[i];
					}
				}
				modelDayVO.getEntity().setSource("其他");
			}else{
				for(int i=0;i<index;i++){
					Object o = map.get("day"+(i+1));
					String dayData = o ==null ?"":String.valueOf(o);
					if(StringUtils.isEmpty(dayData)){
						modelArr[i] = 0;
					}else{
						modelArr[i] = Integer.valueOf(dayData);
					}
					qita[i] += modelArr[i];
				}
				modelDayVO.getEntity().setSource(name);
			}
			
			modelDayVO.setDayDatas(modelArr);
			equipModelDayVOs.add(modelDayVO);
		}
		
		return equipModelDayVOs;
	}

	@Override
	public List<EquipSourceDayVO> selectNewEquipRate4Line(
			EquipSourceDayVO equipSourceDayVO) {
		try {
			String queryType = "install/new_equip";
			StringBuffer sb = new StringBuffer("");
			sb.append("select model, ");
			int index = verticalToHorizontal(queryType, equipSourceDayVO, sb);
			sb.append(" from ( select ds, model,install,new_equip");
			sb.append(" from equip_model_day where ");
			screeningConditions(equipSourceDayVO, sb);
			sb.append(" group by ds,model) c");
			sb.append(" group by model ");
			sb.append("order by sumData desc ");
			sb.append(" limit 0,5");
			
			equipSourceDayVO.setSearchValue(sb.toString());
			List<Map<String,Object>> list = this.entityDao.selectNewEquipRate4Line(equipSourceDayVO);
			List<EquipSourceDayVO> equipModelDayVOs = new ArrayList<EquipSourceDayVO>();
			double[] qita = new double[index];
			for(Map<String,Object> map:list){
				EquipSourceDayVO modelDayVO = new EquipSourceDayVO();
				Double[] modelArr = new Double[index];
				
				String name = (String)map.get("model");
				for(int i=0;i<index;i++){
					Object o = map.get("day"+(i+1));
					String dayData = o ==null ?"":String.valueOf(o);
					if(StringUtils.isEmpty(dayData)){
						modelArr[i] = 0d;
					}else{
						modelArr[i] = Double.valueOf(dayData);
					}
					qita[i] += modelArr[i];
				}
				modelDayVO.getEntity().setSource(name);
				
				modelDayVO.setDoubleDayDatas(modelArr);
				equipModelDayVOs.add(modelDayVO);
			}
			
			return equipModelDayVOs;
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private int verticalToHorizontal(String queryType,
			EquipSourceDayVO equipSourceDayVO, StringBuffer sb)
			throws ParseException {
		int index = 1;
		Date beginDate = sdf.parse(equipSourceDayVO.getBeginDate());
		Date endDate = sdf.parse(equipSourceDayVO.getEndDate());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		
		do{
			sb.append("max(case when ds=\'");
			sb.append(sdf.format(calendar.getTime()));
			sb.append("\' then ");
			sb.append(queryType);
			sb.append(" end) as day");
			sb.append(index++);
			sb.append(",");
			
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
			
		}while(!(calendar.getTime().getTime() > endDate.getTime() || index >100));
		sb.append("sum("+queryType+") sumData");
		return index - 1;
	}

	private void screeningConditions(EquipSourceDayVO equipSourceDayVO, StringBuffer sb) {
		int valueIndex = 0;
		if(equipSourceDayVO.getEntity().getId() != null && equipSourceDayVO.getEntity().getId() != 0){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("id="+equipSourceDayVO.getEntity().getId());
			valueIndex ++;
		}
		if(!StringUtils.isEmpty(equipSourceDayVO.getEntity().getSnid())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("snid=\'"+equipSourceDayVO.getEntity().getSnid()+"\'");
		}
		if(!StringUtils.isEmpty(equipSourceDayVO.getEntity().getGameid())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("gameid=\'"+equipSourceDayVO.getEntity().getGameid()+"\'");
		}
		if(!StringUtils.isEmpty(equipSourceDayVO.getBeginDate())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("ds>=\'"+equipSourceDayVO.getBeginDate()+"\'");
		}
		if(!StringUtils.isEmpty(equipSourceDayVO.getEndDate())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("ds<=\'"+equipSourceDayVO.getEndDate()+"\'");
		}
	}
	
	@Override
	public List<EquipSourceDayVO> selectDau4Bar(EquipSourceDayVO equipSourceDayVO) {
		try {
			String queryType = "dau";
			int index = concatSql(equipSourceDayVO, queryType);
			List<Map<String,String>> list = this.entityDao.selectDau4Bar(equipSourceDayVO);
			return exchangeList(index, list);
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
