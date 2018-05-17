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

import com.hoolai.bi.report.dao.EquipModelDayDao;
import com.hoolai.bi.report.model.entity.equip.EquipModelDay;
import com.hoolai.bi.report.service.EquipModelDayService;
import com.hoolai.bi.report.vo.EquipModelDayVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class EquipModelDayServiceImpl extends GenericServiceImpl<EquipModelDay, Long> implements EquipModelDayService {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private EquipModelDayDao entityDao;
	
	@Override
    public GenericDao<EquipModelDay, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public Long selectCount(EquipModelDayVO equipModelDayVO) {
		try {
			return this.entityDao.selecCount(equipModelDayVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public List<EquipModelDay> selectList(EquipModelDayVO equipModelDayVO) {
		String col = equipModelDayVO.getOrderCol();
		String orderType = StringUtils.isEmpty(equipModelDayVO.getOrderType()) ? "desc" : equipModelDayVO.getOrderType();
		if(StringUtils.isEmpty(col)){
			equipModelDayVO.setOrderValue(" order by ds "+orderType);
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
			case 54:
				orderCol = "dau-new_equip";
				break;
			case 6:
				orderCol = "install/new_equip";
				break;
			case 7:
				orderCol = "uninstall";
				break;
			}
			equipModelDayVO.setOrderValue(" order by "+orderCol+" "+orderType);
		}
		
		return this.entityDao.selectList(equipModelDayVO);
	}

	@Override
	public List<EquipModelDayVO> selectInsatll4Pie(
			EquipModelDayVO equipModelDayVO) {
		return this.entityDao.selectInsatll4Pie(equipModelDayVO);
	}

	@Override
	public List<EquipModelDayVO> selectDau4Pie(EquipModelDayVO equipModelDayVO) {
		return this.entityDao.selectDau4Pie(equipModelDayVO);
	}

	@Override
	public List<EquipModelDayVO> selectNewEquip4Pie(
			EquipModelDayVO equipModelDayVO) {
		return this.entityDao.selectNewEquip4Pie(equipModelDayVO);
	}

	@Override
	public List<EquipModelDayVO> selectDau4Bar(EquipModelDayVO equipModelDayVO) {
		try {
			String queryType = "dau";
			int index = concatSql(equipModelDayVO, queryType);
			List<Map<String,String>> list = this.entityDao.selectDau4Bar(equipModelDayVO);
			return exchangeList(index, list);
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<EquipModelDayVO> selectNewEquip4Line(
			EquipModelDayVO equipModelDayVO) {
		try {
			String queryType = "new_equip";
			int index = concatSql(equipModelDayVO, queryType);
			List<Map<String,String>> list = this.entityDao.selectNewEquip4Line(equipModelDayVO);
			return exchangeList(index, list);
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private int concatSql(EquipModelDayVO equipModelDayVO, String queryType)
			throws ParseException {
		StringBuffer sb = new StringBuffer("select * ");
		//verticalToHorizontal1(queryType, equipModelDayVO, sb);
		sb.append(" from ( select model, ");
		int index = verticalToHorizontal(queryType, equipModelDayVO, sb);
		sb.append(" from ( select ds, model,sum("+queryType+") "+queryType);
		sb.append(" from equip_model_day where ");
		screeningConditions(equipModelDayVO, sb);
		sb.append(" group by ds,model) c");
		sb.append(" group by model ");
		sb.append("order by sumData desc ");
		sb.append(" limit 0,5) gg union all select '总和' as model,");
		verticalToHorizontal(queryType, equipModelDayVO, sb);
		sb.append(" from ( select ds, sum("+queryType+") "+queryType);
		sb.append(" from equip_model_day where ");
		screeningConditions(equipModelDayVO, sb);
		sb.append(" group by ds) a");
		sb.append(" group by model ");
		
		equipModelDayVO.setSearchValue(sb.toString());
		return index;
	}

	private List<EquipModelDayVO> exchangeList(int index,
			List<Map<String, String>> list) {
		List<EquipModelDayVO> equipModelDayVOs = new ArrayList<EquipModelDayVO>();
		int[] qita = new int[index];
		for(Map<String,String> map:list){
			EquipModelDayVO modelDayVO = new EquipModelDayVO();
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
				modelDayVO.getEntity().setModel("其他");
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
				modelDayVO.getEntity().setModel(name);
			}
			
			modelDayVO.setDayDatas(modelArr);
			equipModelDayVOs.add(modelDayVO);
		}
		
		return equipModelDayVOs;
	}

	@Override
	public List<EquipModelDayVO> selectNewEquipRate4Line(
			EquipModelDayVO equipModelDayVO) {
		try {
			String queryType = "install/new_equip";
			StringBuffer sb = new StringBuffer("");
			sb.append("select model, ");
			int index = verticalToHorizontal(queryType, equipModelDayVO, sb);
			sb.append(" from ( select ds, model,install,new_equip");
			sb.append(" from equip_model_day where ");
			screeningConditions(equipModelDayVO, sb);
			sb.append(" group by ds,model) c");
			sb.append(" group by model ");
			sb.append("order by sumData desc ");
			sb.append(" limit 0,5");
			
			equipModelDayVO.setSearchValue(sb.toString());
			List<Map<String,Object>> list = this.entityDao.selectNewEquipRate4Line(equipModelDayVO);
			List<EquipModelDayVO> equipModelDayVOs = new ArrayList<EquipModelDayVO>();
			double[] qita = new double[index];
			for(Map<String,Object> map:list){
				EquipModelDayVO modelDayVO = new EquipModelDayVO();
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
				modelDayVO.getEntity().setModel(name);
				
				modelDayVO.setDoubleDayDatas(modelArr);
				equipModelDayVOs.add(modelDayVO);
			}
			
			return equipModelDayVOs;
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void verticalToHorizontal1(String queryType,
			EquipModelDayVO equipModelDayVO, StringBuffer sb)
			throws ParseException {
		int index = 1;
		Date beginDate = sdf.parse(equipModelDayVO.getBeginDate());
		Date endDate = sdf.parse(equipModelDayVO.getEndDate());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		
		do{
			sb.append("day");
			sb.append(index++);
			sb.append(",");
			
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
			
		}while(!(calendar.getTime().getTime() > endDate.getTime() || index >100));
		sb.append(" sumData");
	}
	
	private int verticalToHorizontal(String queryType,
			EquipModelDayVO equipModelDayVO, StringBuffer sb)
			throws ParseException {
		int index = 1;
		Date beginDate = sdf.parse(equipModelDayVO.getBeginDate());
		Date endDate = sdf.parse(equipModelDayVO.getEndDate());
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

	private void screeningConditions(EquipModelDayVO equipModelDayVO, StringBuffer sb) {
		int valueIndex = 0;
		if(equipModelDayVO.getEntity().getId() != null && equipModelDayVO.getEntity().getId() != 0){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("id="+equipModelDayVO.getEntity().getId());
			valueIndex ++;
		}
		if(!StringUtils.isEmpty(equipModelDayVO.getEntity().getSnid())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("snid=\'"+equipModelDayVO.getEntity().getSnid()+"\'");
		}
		if(!StringUtils.isEmpty(equipModelDayVO.getEntity().getGameid())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("gameid=\'"+equipModelDayVO.getEntity().getGameid()+"\'");
		}
		if(!StringUtils.isEmpty(equipModelDayVO.getBeginDate())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("ds>=\'"+equipModelDayVO.getBeginDate()+"\'");
		}
		if(!StringUtils.isEmpty(equipModelDayVO.getEndDate())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("ds<=\'"+equipModelDayVO.getEndDate()+"\'");
		}
	}

}
