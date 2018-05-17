package com.hoolai.panel.web.download;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.hoolai.bi.report.model.entity.AdminOpMonitorStandard;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.util.DateUtils;
import com.hoolai.manage.model.Users;

public class UploadAdminOpMonitorProcessor {

	private Workbook wb;
	
	private List<AdminOpMonitorStandard> adminOpMonitorStandards = new ArrayList<AdminOpMonitorStandard>();
	
	private Games games;
	
	private String date;
	
	public UploadAdminOpMonitorProcessor(String filePath,MultipartFile upFile,String date,Games games) throws IOException {
		this.games = games;
		this.date = date;
		checkDate();
		if(!StringUtils.isEmpty(filePath)){
			String subfix[] = filePath.split("\\.");
			
			if("xls".equals(subfix[1])){
				wb = new HSSFWorkbook(upFile.getInputStream());
			}else if("xlsx".equals(subfix[1])){
				wb = new XSSFWorkbook(upFile.getInputStream());
			}else{
				throw new RuntimeException();
			}
		}else{
			throw new RuntimeException();
		}
	}

	private void checkDate() {
		if(StringUtils.isEmpty(date)){
			throw new RuntimeException();
		}else{
			try {
				DateUtils.parseDate(date, "yyyy-MM");
			} catch (ParseException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}
	
	public void readBook(){
        Sheet sheet = wb.getSheetAt(0);
		
		int allRowNum = sheet.getLastRowNum();
		for(int rownum=1;rownum<=allRowNum;rownum++){
			AdminOpMonitorStandard monitor = new AdminOpMonitorStandard(games.getSnid(),games.getGameid(),games.getName());
			Row dataRow = sheet.getRow(rownum);
			if(dataRow == null){
				continue;
			}else if(dataRow.getCell(0) == null){
				break;
			}else if(dataRow.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK){
				break;
			}
			
			monitor.setMonth(date);
			monitor.setClientid(Integer.valueOf(getCellFormatValue(dataRow.getCell(0))));
			monitor.setOpCode(getCellFormatValue(dataRow.getCell(1)));
			monitor.setOpUserName(getCellFormatValue(dataRow.getCell(2)));
			monitor.setItemId(Long.valueOf(getCellFormatValue(dataRow.getCell(3))));
			monitor.setItemName(getCellFormatValue(dataRow.getCell(4)));
			monitor.setItemNum(Integer.valueOf(getCellFormatValue(dataRow.getCell(5))));
			monitor.setApplyUserName(getCellFormatValue(dataRow.getCell(6)));
			monitor.setUseDesc(getCellFormatValue(dataRow.getCell(7)));
			monitor.setBeOperatedUserName(getCellFormatValue(dataRow.getCell(8)));
			monitor.setBeOperatedUserRole(getCellFormatValue(dataRow.getCell(9)));
			monitor.setOpDate(getCellFormatValue(dataRow.getCell(10)));
			
			this.adminOpMonitorStandards.add(monitor);
		}
	}
	
    /**
     * 根据Cell类型设置数据
     * @param cell
     * @return
     */
    private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
            case Cell.CELL_TYPE_FORMULA:
            	// 如果是纯数字  取得当前Cell的数值
            	double d = cell.getNumericCellValue();
            	if(d % 1 == 0){
            		 cellvalue = String.valueOf(new Double(d).intValue());
            	}else{
            		cellvalue =String.valueOf(d);
            	}
                
                break;
            // 如果当前Cell的Type为STRIN
            case Cell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

	public List<AdminOpMonitorStandard> getAdminOpMonitorStandards() {
		return adminOpMonitorStandards;
	}

}
