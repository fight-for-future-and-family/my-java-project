package com.hoolai.panel.web.download;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.hoolai.bi.report.model.entity.CostPerSourceDimension;
import com.hoolai.bi.report.model.entity.Games;

public class UploadCostPerSourceProcessor {

	private Workbook wb;
	
	private List<CostPerSourceDimension> costPerSourceDimensions = new ArrayList<CostPerSourceDimension>();
	
	private Games games;
	
	public UploadCostPerSourceProcessor(String filePath,MultipartFile upFile,Games games) throws IOException {
		this.games = games;
		if(filePath != null){
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
	
	public void readBook(){
        Sheet sheet = wb.getSheetAt(0);
		
		int allRowNum = sheet.getLastRowNum();
		for(int rownum=1;rownum<=allRowNum;rownum++){
			CostPerSourceDimension dimension = new CostPerSourceDimension(games.getSnid(),games.getGameid());
			Row dataRow = sheet.getRow(rownum);
			if(dataRow == null){
				continue;
			}else if(dataRow.getCell(0) == null){
				break;
			}else if(dataRow.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK){
				break;
			}
			
			dimension.setSourceCode(getCellFormatValue(dataRow.getCell(0)));
			dimension.setSourceName(getCellFormatValue(dataRow.getCell(1)));
			
			String payUserRate = getCellFormatValue(dataRow.getCell(2));
			String payTimesRate = getCellFormatValue(dataRow.getCell(3));
			String payRate = getCellFormatValue(dataRow.getCell(4));
			
			dimension.setPayUserRate(Double.valueOf(payUserRate)/100);
			dimension.setPayTimesRate(Double.valueOf(payTimesRate)/100);
			dimension.setPayRate(Double.valueOf(payRate)/100);
			
			this.costPerSourceDimensions.add(dimension);
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

	public List<CostPerSourceDimension> getCostPerSourceDimensions() {
		return costPerSourceDimensions;
	}

}
