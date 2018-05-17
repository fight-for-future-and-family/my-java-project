package com.hoolai.panel.web.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hoolai.bi.report.model.entity.Games;
import com.jian.tools.util.JSONUtils;

public class DownLoadPredictionProcessor {
	
	static class PredictParm implements Serializable {
		private static final long serialVersionUID = 3582570247944946485L;
		private String predictAmount;
		private String retention;
		private String install;
		private String arppuBefore;
		private String arppuIncrease;
		private String arppuUseTime;
		private String arpuBefore;
		private String arpuIncrease;
		private String payRateBefore;
		private String payRateIncrease;
		private String oldUserBefore;
		private String oldUserIncrease;
		private String oldUserUseTime;
		public String getPredictAmount() {
			return predictAmount;
		}
		public void setPredictAmount(String predictAmount) {
			this.predictAmount = predictAmount;
		}
		public String getRetention() {
			return retention;
		}
		public void setRetention(String retention) {
			this.retention = retention;
		}
		public String getInstall() {
			return install;
		}
		public void setInstall(String install) {
			this.install = install;
		}
		public String getArppuBefore() {
			return arppuBefore;
		}
		public void setArppuBefore(String arppuBefore) {
			this.arppuBefore = arppuBefore;
		}
		public String getArppuIncrease() {
			return arppuIncrease;
		}
		public void setArppuIncrease(String arppuIncrease) {
			this.arppuIncrease = arppuIncrease;
		}
		public String getArppuUseTime() {
			return arppuUseTime;
		}
		public void setArppuUseTime(String arppuUseTime) {
			this.arppuUseTime = arppuUseTime;
		}
		public String getArpuBefore() {
			return arpuBefore;
		}
		public void setArpuBefore(String arpuBefore) {
			this.arpuBefore = arpuBefore;
		}
		public String getArpuIncrease() {
			return arpuIncrease;
		}
		public void setArpuIncrease(String arpuIncrease) {
			this.arpuIncrease = arpuIncrease;
		}
		public String getPayRateBefore() {
			return payRateBefore;
		}
		public void setPayRateBefore(String payRateBefore) {
			this.payRateBefore = payRateBefore;
		}
		public String getPayRateIncrease() {
			return payRateIncrease;
		}
		public void setPayRateIncrease(String payRateIncrease) {
			this.payRateIncrease = payRateIncrease;
		}
		public String getOldUserBefore() {
			return oldUserBefore;
		}
		public void setOldUserBefore(String oldUserBefore) {
			this.oldUserBefore = oldUserBefore;
		}
		public String getOldUserIncrease() {
			return oldUserIncrease;
		}
		public void setOldUserIncrease(String oldUserIncrease) {
			this.oldUserIncrease = oldUserIncrease;
		}
		public String getOldUserUseTime() {
			return oldUserUseTime;
		}
		public void setOldUserUseTime(String oldUserUseTime) {
			this.oldUserUseTime = oldUserUseTime;
		}
		
	}
	
	static class PredictionConfig implements Serializable{
		private static final long serialVersionUID = -8585019779307249231L;
		private PredictParm parm;
		private String[] columns;
		private String[][] rowData;
		public PredictParm getParm() {
			return parm;
		}
		public void setParm(PredictParm parm) {
			this.parm = parm;
		}
		public String[] getColumns() {
			return columns;
		}
		public void setColumns(String[] columns) {
			this.columns = columns;
		}
		public String[][] getRowData() {
			return rowData;
		}
		public void setRowData(String[][] rowData) {
			this.rowData = rowData;
		}
		
	}

	private static final String templeteFileName = "templates/predict_template.xlsx";
	
	private Games games;
	private String dataTableValue;
	private PredictionConfig config;
	private HttpServletResponse response;
	Workbook wb;
	OutputStream out;
	
	public DownLoadPredictionProcessor(Games games,String dataTableValue,HttpServletResponse response){
		this.games = games;
		this.dataTableValue = dataTableValue;
		this.response = response;
		initResponse();
		initData();
	}
	
	private void initData() {
		this.config = JSONUtils.fromJSON(dataTableValue, PredictionConfig.class);
	}

	private void initResponse(){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
		
		InputStream strem = loader.getResourceAsStream(templeteFileName);
		try {
			out = response.getOutputStream();
			wb = new XSSFWorkbook(strem);
			
			response.reset();
			response.setContentType("application/msexcel;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void flushAndClose(){
		try {
			wb.write(out);
			out.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeRow(Row row, Object[] data) {
		for (int i = 0; i < data.length; i++) {
			Cell cell=row.getCell(i);
			if(data[i] instanceof String){
				cell.setCellValue((String)data[i]);
			}else if(data[i] instanceof Integer){
				cell.setCellValue((Integer)data[i]);
			}else if(data[i] instanceof Double){
				cell.setCellValue(data[i] == null ? 0 : Double.valueOf(String.valueOf(data[i])));
			}else{
				cell.setCellValue(data[i] == null ? "-" : String.valueOf(data[i]));
			}
		}
	}
	
	private void createRow(Row dataRow, Row templeteRow, Object[] data) {
		Cell dataCell;
		for (int i = 0; i < data.length; i++) {
			dataCell=dataRow.createCell(i);
			dataCell.setCellType(templeteRow.getCell(i).getCellType());
			dataCell.setCellStyle(templeteRow.getCell(i).getCellStyle());
			if(data[i] instanceof String){
				dataCell.setCellValue((String)data[i]);
			}else if(data[i] instanceof Integer){
				dataCell.setCellValue((Integer)data[i]);
			}else if(data[i] instanceof Long){
				dataCell.setCellValue((Long)data[i]);
			}else{
				dataCell.setCellValue(Double.valueOf(String.valueOf(data[i])));
			}
		}
	}
	
	public void write() throws UnsupportedEncodingException{
		String fileName = "prediction_kpi.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = config.getColumns();
		Sheet sheet=wb.getSheetAt(0);
		int rowIdx=11;
		
		modifyPredictionHead(sheet);
		
		Row headerRow=sheet.getRow(9);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.getCell(i);
			cell.setCellValue(header[i]);
		}
		
		Row templeteRow = sheet.getRow(11);
		for (String[] data : config.getRowData()) {
			Row row = null;
			if((rowIdx-1) == 10){
				row=sheet.getRow(rowIdx-1);
				writeRow(row, data);
			}else{
				row = sheet.createRow(rowIdx);
				row.setHeight(templeteRow.getHeight());
				row.setRowStyle(templeteRow.getRowStyle());
				
				createRow(row, templeteRow, data);
			}
			rowIdx ++;
		}
		sheet.shiftRows(12, sheet.getLastRowNum()+1, -1);
		flushAndClose();
	}

	private void modifyPredictionHead(Sheet sheet) {
		Row row1 = sheet.getRow(1);
		Row row2 = sheet.getRow(2);
		Row row3 = sheet.getRow(3);
		Row row4 = sheet.getRow(4);
		Row row5 = sheet.getRow(5);
		
		String payStr = row1.getCell(0).getStringCellValue();
		row1.getCell(0).setCellValue(payStr+"("+games.getCurrency()+")");
		row1.getCell(1).setCellValue(config.getParm().getPredictAmount());
		row1.getCell(3).setCellValue(config.getParm().getRetention());
		row1.getCell(5).setCellValue(config.getParm().getInstall());
		
		String arpuStr1 = row2.getCell(0).getStringCellValue();
		String arpuStr2 = row2.getCell(2).getStringCellValue();
		row2.getCell(0).setCellValue(arpuStr1+"("+games.getCurrency()+")");
		row2.getCell(1).setCellValue(config.getParm().getArppuBefore());
		row2.getCell(2).setCellValue(arpuStr2+"("+games.getCurrency()+")");
		row2.getCell(3).setCellValue(config.getParm().getArppuIncrease());
		row2.getCell(5).setCellValue(config.getParm().getArppuUseTime());
		
		String arppuStr1 = row3.getCell(0).getStringCellValue();
		String arppuStr2 = row3.getCell(2).getStringCellValue();
		row3.getCell(0).setCellValue(arppuStr1+"("+games.getCurrency()+")");
		row3.getCell(1).setCellValue(config.getParm().getArpuBefore());
		row3.getCell(2).setCellValue(arppuStr2+"("+games.getCurrency()+")");
		row3.getCell(3).setCellValue(config.getParm().getArpuIncrease());
		
		row4.getCell(1).setCellValue(config.getParm().getPayRateBefore());
		row4.getCell(3).setCellValue(config.getParm().getPayRateIncrease());
		
		row5.getCell(1).setCellValue(config.getParm().getOldUserBefore());
		row5.getCell(3).setCellValue(config.getParm().getOldUserIncrease());
		row5.getCell(5).setCellValue(config.getParm().getOldUserUseTime());
	}
	
}
