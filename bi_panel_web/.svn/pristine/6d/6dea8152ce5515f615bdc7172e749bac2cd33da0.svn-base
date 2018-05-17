package com.hoolai.panel.web.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.hoolai.bi.report.model.entity.AnalysisGDT;
import com.hoolai.bi.report.model.entity.SummaryGDT;
import com.hoolai.panel.web.download.DownLoadProcessor.WorkbookType;

public class UploadGDTProcessor1 {

	private Workbook wb;
	private Workbook wwb;
	private WorkbookType workbookType = WorkbookType.XLSX;
	private File upFile;
	private File downFile;
	
	private List<SummaryGDT> summaryGDTs = new ArrayList<SummaryGDT>();
	
	private List<AnalysisGDT> analysisGDTs = new ArrayList<AnalysisGDT>();
	
	public UploadGDTProcessor1(File upFile,File downFile){
		this.upFile = upFile;
		this.downFile = downFile;
		try {
			readFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public UploadGDTProcessor1(MultipartFile upFile) throws IOException {
		if(upFile.getName() != null){
			String subfix = upFile.getName().split(".")[1];
			if("xls".equals(subfix)){
				wb = new HSSFWorkbook(upFile.getInputStream());
			}else if("xlsx".equals(subfix)){
				wb = new HSSFWorkbook(upFile.getInputStream());
			}else{
				throw new RuntimeException();
			}
		}else{
			throw new RuntimeException();
		}
	}

	private void readFile() throws FileNotFoundException {

		//ClassLoader loader = Thread.currentThread().getContextClassLoader();

		InputStream strem = null;
		InputStream strem1 = null;
		//strem = loader.getResourceAsStream(templeteFileName);
		strem =  new FileInputStream(upFile);
		strem1 = new FileInputStream(downFile);
		switch (this.workbookType) {
		case XLS:
			try {
				POIFSFileSystem fs = new POIFSFileSystem(strem);//
				wb = new HSSFWorkbook(fs);
				wwb = new HSSFWorkbook(strem1);
				// wb = new HSSFWorkbook(strem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case XLSX:
		default:
			try {
				wb = new XSSFWorkbook(strem);
				wwb = new XSSFWorkbook(strem1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	public void readSummaryBook(){
        Sheet sheet = wb.getSheetAt(0);
		
		Row headerRow = sheet.getRow(3);
		int allColNum = headerRow.getPhysicalNumberOfCells();
		int allRowNum = sheet.getLastRowNum();
		
		System.out.print("行编号\t\t");
		for(int colNum=0;colNum<allColNum;colNum++){
			Cell headerCell = headerRow.getCell(colNum);
			System.out.print(headerCell.getStringCellValue()+"\t\t\t");
		}
		
		System.out.println();
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
			System.out.print((rownum+1)+"\t\t");
			for(int cellnum=0;cellnum<allColNum;cellnum++){
				Cell cell = dataRow.getCell(cellnum);
				String value = getCellFormatValue(cell);
				System.out.print(value+"\t\t");
				setObj(cellnum, value, summaryGDT);
			}
			System.out.println();
			this.summaryGDTs.add(summaryGDT);
		}
	}
	
	private void writeSummaryBook(){
       Sheet sheet = wwb.getSheetAt(0);
		
//		Row headerRow = zonglanSheet.getRow(0);
//		for(int colNum:dailyReportCurrencyHeader){
//			Cell headerCell = headerRow.getCell(colNum);
//			headerCell.setCellValue(headerCell.getStringCellValue()+"("+games.getCurrency()+")");
//		}
		
		Row dataRow;
		Row templeteRow = sheet.getRow(4);
		Cell dataCell;
		int rownum = 5;
		for (SummaryGDT gdt : summaryGDTs) {
			dataRow = sheet.createRow(rownum);
			dataRow.setHeight(templeteRow.getHeight());
			dataRow.setRowStyle(templeteRow.getRowStyle());

			dataCell = dataRow.createCell(0);
			dataCell.setCellType(templeteRow.getCell(0).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(0).getCellStyle());
			dataCell.setCellValue(gdt.getDate());
			
			dataCell = dataRow.createCell(1);
			dataCell.setCellType(templeteRow.getCell(1).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(1).getCellStyle());
			dataCell.setCellValue(gdt.getExposure());
			
			dataCell = dataRow.createCell(2);
			dataCell.setCellType(templeteRow.getCell(2).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(2).getCellStyle());
			dataCell.setCellValue(gdt.getClickCount());
			
			dataCell = dataRow.createCell(3);
			dataCell.setCellType(templeteRow.getCell(3).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(3).getCellStyle());
			dataCell.setCellValue(gdt.getClickRate());
			
			dataCell = dataRow.createCell(4);
			dataCell.setCellType(templeteRow.getCell(4).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(4).getCellStyle());
			dataCell.setCellValue(gdt.getInstall());
			
			dataCell = dataRow.createCell(5);
			dataCell.setCellType(templeteRow.getCell(5).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(5).getCellStyle());
			dataCell.setCellValue(gdt.getConversionRate());
			
			dataCell = dataRow.createCell(6);
			dataCell.setCellType(templeteRow.getCell(6).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(6).getCellStyle());
			dataCell.setCellValue(gdt.getAllCost());
			
			dataCell = dataRow.createCell(7);
			dataCell.setCellType(templeteRow.getCell(7).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(7).getCellStyle());
			dataCell.setCellValue(gdt.getD0Cost());
			
			dataCell = dataRow.createCell(8);
			dataCell.setCellType(templeteRow.getCell(8).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(8).getCellStyle());
			dataCell.setCellValue(gdt.getCpc());
			
			dataCell = dataRow.createCell(9);
			dataCell.setCellType(templeteRow.getCell(9).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(9).getCellStyle());
			dataCell.setCellValue(gdt.getCpa());
			
			rownum ++;
		}
		
		sheet.shiftRows(5, sheet.getLastRowNum()+1, -1);
		
	}
	
	public void readAnalysisBook(){
        Sheet sheet = wb.getSheetAt(1);
		
		Row headerRow = sheet.getRow(0);
		int allColNum = headerRow.getPhysicalNumberOfCells();
		int allRowNum = sheet.getLastRowNum();
		
		System.out.print("行编号\t\t");
		for(int colNum=0;colNum<allColNum;colNum++){
			Cell headerCell = headerRow.getCell(colNum);
			System.out.print(headerCell.getStringCellValue()+"\t\t");
		}
		
		System.out.println();
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
			System.out.print((rownum+1)+"\t\t");
			for(int cellnum=0;cellnum<allColNum;cellnum++){
				Cell cell = dataRow.getCell(cellnum);
				String value = getCellFormatValue(cell);
				System.out.print(value+"\t\t");
				setAnalysisGDT(cellnum, value, analysisGDT);
			}
			System.out.println();
			this.analysisGDTs.add(analysisGDT);
		}
	}
	
	private void writeAnalysisBook(){
       Sheet sheet = wwb.getSheetAt(1);
		
//		Row headerRow = zonglanSheet.getRow(0);
//		for(int colNum:dailyReportCurrencyHeader){
//			Cell headerCell = headerRow.getCell(colNum);
//			headerCell.setCellValue(headerCell.getStringCellValue()+"("+games.getCurrency()+")");
//		}
		
		Row dataRow;
		Row templeteRow = sheet.getRow(1);
		Cell dataCell;
		int rownum = 2;
		for (AnalysisGDT gdt : analysisGDTs) {
			dataRow = sheet.createRow(rownum);
			dataRow.setHeight(templeteRow.getHeight());
			dataRow.setRowStyle(templeteRow.getRowStyle());

			dataCell = dataRow.createCell(0);
			dataCell.setCellType(templeteRow.getCell(0).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(0).getCellStyle());
			dataCell.setCellValue(gdt.getDate());
			
			dataCell = dataRow.createCell(1);
			dataCell.setCellType(templeteRow.getCell(1).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(1).getCellStyle());
			dataCell.setCellValue(gdt.getAdvInstall());
			
			dataCell = dataRow.createCell(2);
			dataCell.setCellType(templeteRow.getCell(2).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(2).getCellStyle());
			dataCell.setCellValue(gdt.getAdvCost());
			
			dataCell = dataRow.createCell(3);
			dataCell.setCellType(templeteRow.getCell(3).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(3).getCellStyle());
			dataCell.setCellValue(gdt.getD0Payuser());
			
			dataCell = dataRow.createCell(4);
			dataCell.setCellType(templeteRow.getCell(4).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(4).getCellStyle());
			dataCell.setCellValue(gdt.getPayRate());
			
			dataCell = dataRow.createCell(5);
			dataCell.setCellType(templeteRow.getCell(5).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(5).getCellStyle());
			dataCell.setCellValue(gdt.getD0Payment());
			
			dataCell = dataRow.createCell(6);
			dataCell.setCellType(templeteRow.getCell(6).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(6).getCellStyle());
			dataCell.setCellValue(gdt.getD1Payment());
			
			dataCell = dataRow.createCell(7);
			dataCell.setCellType(templeteRow.getCell(7).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(7).getCellStyle());
			dataCell.setCellValue(gdt.getD3Payment());
			
			dataCell = dataRow.createCell(8);
			dataCell.setCellType(templeteRow.getCell(8).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(8).getCellStyle());
			dataCell.setCellValue(gdt.getD7Payment());
			
			dataCell = dataRow.createCell(9);
			dataCell.setCellType(templeteRow.getCell(9).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(9).getCellStyle());
			dataCell.setCellValue(gdt.getD30Payment());
			
			dataCell = dataRow.createCell(10);
			dataCell.setCellType(templeteRow.getCell(10).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(10).getCellStyle());
			dataCell.setCellValue(gdt.getD0Roi());
			
			dataCell = dataRow.createCell(11);
			dataCell.setCellType(templeteRow.getCell(11).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(11).getCellStyle());
			dataCell.setCellValue(gdt.getD1Roi());
			
			dataCell = dataRow.createCell(12);
			dataCell.setCellType(templeteRow.getCell(12).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(12).getCellStyle());
			dataCell.setCellValue(gdt.getD3Roi());
			
			dataCell = dataRow.createCell(13);
			dataCell.setCellType(templeteRow.getCell(13).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(13).getCellStyle());
			dataCell.setCellValue(gdt.getD7Roi());
			
			dataCell = dataRow.createCell(14);
			dataCell.setCellType(templeteRow.getCell(14).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(14).getCellStyle());
			dataCell.setCellValue(gdt.getD30Roi());
			
			rownum ++;
		}
		
		sheet.shiftRows(2, sheet.getLastRowNum()+1, -1);
		
	}
	
	public void writeBook(String fileName){
		readAnalysisBook();
		readSummaryBook();
		writeAnalysisBook();
		writeSummaryBook();
		
		OutputStream out;
		try {
			out = new FileOutputStream(new File(fileName));
			wwb.write(out);
			wwb.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setObj(int cellNum,String value,SummaryGDT summaryGDT){
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
	
	private void setAnalysisGDT(int cellNum,String value,AnalysisGDT analysisGDT){
		if(value == null || "".equals(value.trim())){
			value = "0";
		}
		switch (cellNum) {
		case 0:
			analysisGDT.setDate(value);
			break;
		case 1:
			analysisGDT.setAdvInstall(Long.parseLong(value));
			break;
		case 2:
			analysisGDT.setAdvCost(Double.parseDouble(value));
			break;
		case 3:
			analysisGDT.setD0Payuser(Long.parseLong(value));
			break;
		case 4:
			analysisGDT.setPayRate(Double.parseDouble(value));
			break;
		case 5:
			analysisGDT.setD0Payment(Double.parseDouble(value));
			break;
		case 6:
			analysisGDT.setD1Payment(Double.parseDouble(value));
			break;
		case 7:
			analysisGDT.setD3Payment(Double.parseDouble(value));
			break;
		case 8:
			analysisGDT.setD7Payment(Double.parseDouble(value));
			break;
		case 9:
			analysisGDT.setD30Payment(Double.parseDouble(value));
			break;
		case 10:
			analysisGDT.setD0Roi(Double.parseDouble(value));
			break;
		case 11:
			analysisGDT.setD1Roi(Double.parseDouble(value));
			break;
		case 12:
			analysisGDT.setD3Roi(Double.parseDouble(value));
			break;
		case 13:
			analysisGDT.setD7Roi(Double.parseDouble(value));
			break;
		case 14:
			analysisGDT.setD30Roi(Double.parseDouble(value));
			break;
		default:
			break;
		}
	}
	
    /**
     * 根据HSSFCell类型设置数据
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

	
	public static void main(String[] args) {
		File file = new File("d://【八荒-广点通数据日报】20150322.xlsx");
		File downfile = new File("d://gdt.xlsx");
		UploadGDTProcessor1 processor = new UploadGDTProcessor1(file,downfile);
		processor.writeBook("d://test.xlsx");
	}
}
