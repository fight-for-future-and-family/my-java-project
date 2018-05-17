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

import com.hoolai.bi.report.model.entity.ConsumeDimension;
import com.hoolai.bi.report.model.entity.Games;

public class UploadEconomyProcessor {

	private Workbook wb;
	
	private List<ConsumeDimension> consumeDimensions = new ArrayList<ConsumeDimension>();
	
	private Games games;
	
	public UploadEconomyProcessor(String filePath,MultipartFile upFile,Games games) throws IOException {
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
	
	/**
	 * 读取消费点维表
	 */
	public void readBook(){
        Sheet sheet = wb.getSheetAt(0);
		
		int allRowNum = sheet.getLastRowNum();
		for(int rownum=1;rownum<=allRowNum;rownum++){
			ConsumeDimension pointDimension = new ConsumeDimension(games.getSnid(),games.getGameid());
			Row dataRow = sheet.getRow(rownum);
			if(dataRow == null){
				continue;
			}else if(dataRow.getCell(0) == null){
				break;
			}else if(dataRow.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK){
				break;
			}
			
			System.out.println("rownum:"+rownum);
			pointDimension.setConsumeCode(getCellFormatValue(dataRow.getCell(0)));
			pointDimension.setConsumeName(getCellFormatValue(dataRow.getCell(1)));
			
			this.consumeDimensions.add(pointDimension);
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

	public List<ConsumeDimension> getConsumeDimensions() {
		return consumeDimensions;
	}

}
