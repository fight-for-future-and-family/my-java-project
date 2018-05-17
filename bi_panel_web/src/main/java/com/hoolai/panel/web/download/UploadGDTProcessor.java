package com.hoolai.panel.web.download;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.hoolai.bi.report.model.entity.AnalysisGDT;
import com.hoolai.bi.report.model.entity.SummaryGDT;

public class UploadGDTProcessor {

	private Workbook wb;
	
	private List<SummaryGDT> summaryGDTs = new ArrayList<SummaryGDT>();
	
	private List<AnalysisGDT> analysisGDTs = new ArrayList<AnalysisGDT>();
	
	public UploadGDTProcessor(String filePath,MultipartFile upFile) throws IOException {
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
	
	public void upload(){
		readSummaryBook();
		readAnalysisBook();
	}
	
	/**
	 * 读取广点通汇总表excel数据
	 */
	private void readSummaryBook(){
        Sheet sheet = wb.getSheetAt(0);
		
		Row headerRow = sheet.getRow(3);
		int allColNum = headerRow.getPhysicalNumberOfCells();
		int allRowNum = sheet.getLastRowNum();
		
		for(int rownum=4;rownum<allRowNum;rownum++){
			SummaryGDT summaryGDT = new SummaryGDT();
			Row dataRow = sheet.getRow(rownum);
			if(dataRow == null){
				continue;
			}else if(dataRow.getCell(0) == null){
				break;
			}else if(dataRow.getCell(0).getCellType() == Cell.CELL_TYPE_STRING){
				continue;
			}else if(dataRow.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK){
				break;
			}
			
			allColNum = dataRow.getPhysicalNumberOfCells();
			for(int cellnum=0;cellnum<allColNum;cellnum++){
				Cell cell = dataRow.getCell(cellnum);
				String value = getCellFormatValue(cell);
				setSummaryObj(cellnum, value, summaryGDT);
			}
			this.summaryGDTs.add(summaryGDT);
		}
	}
	
	/**
	 * 读取广点通分析表excel数据
	 */
	private void readAnalysisBook(){
        Sheet sheet = wb.getSheetAt(1);
		
		Row headerRow = sheet.getRow(0);
		int allColNum = headerRow.getPhysicalNumberOfCells();
		int allRowNum = sheet.getLastRowNum();
		
		for(int rownum=1;rownum<allRowNum;rownum++){
			AnalysisGDT analysisGDT = new AnalysisGDT();
			Row dataRow = sheet.getRow(rownum);
			if(dataRow == null){
				continue;
			}else if(dataRow.getCell(0) == null){
				break;
			}else if(dataRow.getCell(0).getCellType() == Cell.CELL_TYPE_STRING){
				continue;
			}else if(dataRow.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK){
				break;
			}
			
			allColNum = dataRow.getPhysicalNumberOfCells();
			for(int cellnum=0;cellnum<allColNum;cellnum++){
				Cell cell = dataRow.getCell(cellnum);
				String value = getCellFormatValue(cell);
				setAnalysisGDT(cellnum, value, analysisGDT);
			}
			this.analysisGDTs.add(analysisGDT);
		}
	}
	
	/**
	 * 根据excel的数据构建summaryGDT对象
	 * @param cellNum
	 * @param value
	 * @param summaryGDT
	 */
	private void setSummaryObj(int cellNum,String value,SummaryGDT summaryGDT){
		if(value == null || "".equals(value.trim())){
			value = "0";
		}
		switch (cellNum) {
		case 0:
			summaryGDT.setDate(value);
			break;
		case 1:
			summaryGDT.setExposure(Long.parseLong(value));
			break;
		case 2:
			summaryGDT.setClickCount(Long.parseLong(value));
			break;
		case 3:
			summaryGDT.setClickRate(Double.parseDouble(value));
			break;
		case 4:
			summaryGDT.setInstall(Long.parseLong(value));
			break;
		case 5:
			summaryGDT.setConversionRate(Double.parseDouble(value));
			break;
		case 6:
			summaryGDT.setAllCost(Double.parseDouble(value));
			break;
		case 7:
			summaryGDT.setD0Cost(Double.parseDouble(value));
			break;
		case 8:
			summaryGDT.setCpc(Double.parseDouble(value));
			break;
		case 9:
			summaryGDT.setCpa(Double.parseDouble(value));
			break;
		default:
			break;
		}
	}
	
	/**
	 * 根据excel的数据构建analysisGDT对象
	 * @param cellNum
	 * @param value
	 * @param analysisGDT
	 */
	private void setAnalysisGDT(int cellNum,String value,AnalysisGDT analysisGDT){
		if(value == null || "".equals(value.trim())){
			value = null;
		}
		switch (cellNum) {
		case 0:
			analysisGDT.setDate(value);
			break;
		case 1:
			analysisGDT.setAdvInstall(value == null ? null : Long.parseLong(value));
			break;
		case 2:
			analysisGDT.setAdvCost(value == null ? null : Double.parseDouble(value));
			break;
		case 3:
			analysisGDT.setD0Payuser(value == null ? null : Long.parseLong(value));
			break;
		case 4:
			analysisGDT.setPayRate(value == null ? null : Double.parseDouble(value));
			break;
		case 5:
			analysisGDT.setD0Payment(value == null ? null : Double.parseDouble(value));
			break;
		case 6:
			analysisGDT.setD1Payment(value == null ? null : Double.parseDouble(value));
			break;
		case 7:
			analysisGDT.setD3Payment(value == null ? null : Double.parseDouble(value));
			break;
		case 8:
			analysisGDT.setD7Payment(value == null ? null : Double.parseDouble(value));
			break;
		case 9:
			analysisGDT.setD30Payment(value == null ? null : Double.parseDouble(value));
			break;
		case 10:
			analysisGDT.setD0Roi(value == null ? null : Double.parseDouble(value));
			break;
		case 11:
			analysisGDT.setD1Roi(value == null ? null : Double.parseDouble(value));
			break;
		case 12:
			analysisGDT.setD3Roi(value == null ? null : Double.parseDouble(value));
			break;
		case 13:
			analysisGDT.setD7Roi(value == null ? null : Double.parseDouble(value));
			break;
		case 14:
			analysisGDT.setD30Roi(value == null ? null : Double.parseDouble(value));
			break;
		default:
			break;
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
                // 判断当前的cell是否为Date
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                }else if (cell.getCellStyle() != null 
                		&& (cell.getCellStyle().getDataFormat() == 58
                		|| cell.getCellStyle().getDataFormat() == 32
                		|| cell.getCellStyle().getDataFormat() == 31
                		|| cell.getCellStyle().getDataFormat() == 181)) {  
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                    double value = cell.getNumericCellValue();  
                    Date date = DateUtil.getJavaDate(value);  
                    cellvalue = sdf.format(date);  
                } else {
                	// 如果是纯数字  取得当前Cell的数值
                	DecimalFormat df = new DecimalFormat("###.####");  
                    cellvalue = df.format(cell.getNumericCellValue());
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

	
    public List<SummaryGDT> getSummaryGDTs() {
		return summaryGDTs;
	}

	public List<AnalysisGDT> getAnalysisGDTs() {
		return analysisGDTs;
	}
}
