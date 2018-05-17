package com.hoolai.panel.web.download;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hoolai.bi.report.model.entity.ClientEconomyNewReport;
import com.hoolai.bi.report.model.entity.EconomyNewReport;
import com.hoolai.bi.report.model.entity.Games;

public class DownLoadEconomyProcessor {
	
	private Games games;
	private HttpServletResponse response;
	Workbook wb;
	CellStyle style;
	OutputStream out;
	
	private List<ClientEconomyNewReport> clientEconomyReports1;
	private List<ClientEconomyNewReport> clientEconomyReports2;
	private List<ClientEconomyNewReport> clientEconomyReports3;
	private List<ClientEconomyNewReport> clientEconomyReports4;
	
	private List<EconomyNewReport> economyReports1;
	private List<EconomyNewReport> economyReports2;
	private List<EconomyNewReport> economyReports3;
	private List<EconomyNewReport> economyReports4;
	
	public DownLoadEconomyProcessor(Games games,HttpServletResponse response){
		this.games = games;
		this.response = response;
		initResponse();
	}
	
	private void initResponse(){
		try {
			out = response.getOutputStream();
			wb = new XSSFWorkbook();
			
			style = wb.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
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
			Cell cell=row.createCell(i);
			cell.setCellStyle(style);
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

	public void writeClientEconomyNewReport(String beginClientid,String endClientid,String groupType) throws UnsupportedEncodingException {
		String fileName = "client_economy_report_"+groupType+".xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = {"服务器","日期","消费点","消费额","消费人次","消费次数","消费道具数"};
		
		createCilentEconomySheet(beginClientid, endClientid, header,clientEconomyReports1,"数据","百分比");
		//createCilentEconomySheet(beginClientid, endClientid, header,clientEconomyReports2,"二级分类","二级分类（%）");
		//createCilentEconomySheet(beginClientid, endClientid, header,clientEconomyReports3,"三级分类","三级分类（%）");
		//createCilentEconomySheet(beginClientid, endClientid, header,clientEconomyReports4,"四级分类","四级分类（%）");
		
		flushAndClose();
	}

	private void createCilentEconomySheet(String beginClientid, String endClientid,
			String[] header,List<ClientEconomyNewReport> clientEconomyReports,
			String shName,String sh1Name) {
		Sheet sheet=wb.createSheet(shName);
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		Map<String,Double> countMap = new HashMap<String, Double>();
		for (ClientEconomyNewReport eer : clientEconomyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					beginClientid+"~"+endClientid,
					eer.getDs(),
					eer.getPhylum(),
					eer.getAmount(),
					eer.getUsers(),
					eer.getTimes(),
					eer.getItemNum()
			};
			
			for(int i=3;i<data.length;i++){
				Double dt = countMap.get(data[1]+"_"+i+"");
				
				countMap.put(data[1]+"_"+i+"", dt == null ? Double.valueOf(String.valueOf(data[i])).doubleValue()
						: dt.doubleValue() + Double.valueOf(String.valueOf(data[i])).doubleValue());
			}
			
			writeRow(row, data);
		}
		
		Sheet sheet1=wb.createSheet(sh1Name);
		int rowIdx1=0;
		Row headerRow1=sheet1.createRow(rowIdx1++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow1.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (ClientEconomyNewReport eer : clientEconomyReports)  {
			Row row=sheet1.createRow(rowIdx1++);
			Object data[] = {
					beginClientid+"~"+endClientid,
					eer.getDs(),
					eer.getPhylum(),
					Math.round((eer.getAmount()/countMap.get(eer.getDs()+"_3"))*10000d)/100d+"%",
					Math.round((Double.valueOf(eer.getUsers()).doubleValue() / countMap.get(eer.getDs()+"_4"))*10000d)/100d+"%",
					Math.round((Double.valueOf(eer.getTimes()).doubleValue() / countMap.get(eer.getDs()+"_5"))*10000d)/100d+"%",
					Math.round((Double.valueOf(eer.getItemNum()).doubleValue() / countMap.get(eer.getDs()+"_6"))*10000d)/100d+"%"
			};
			
			writeRow(row, data);
		}
	}
	
	public void initClientEconomyNewReportList(
			List<ClientEconomyNewReport> clientEconomyReports12,
			List<ClientEconomyNewReport> clientEconomyReports22,
			List<ClientEconomyNewReport> clientEconomyReports32,
			List<ClientEconomyNewReport> clientEconomyReports42) {
		this.clientEconomyReports1 = clientEconomyReports12;
		this.clientEconomyReports2 = clientEconomyReports22;
		this.clientEconomyReports3 = clientEconomyReports32;
		this.clientEconomyReports4 = clientEconomyReports42;
	}

	public void writeEconomyNewReport(String groupType) throws UnsupportedEncodingException {
		String fileName = "economy_report_"+groupType+".xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","消费点","消费额","消费人次","消费次数","消费道具数"};
		
		
		createEconomySheet(header,economyReports1,"数据","百分比");
		//createEconomySheet(header,economyReports2,"二级分类","二级分类（%）");
		//createEconomySheet(header,economyReports3,"三级分类","三级分类（%）");
		//createEconomySheet(header,economyReports4,"四级分类","四级分类（%）");
		
		flushAndClose();
	}

	private void createEconomySheet(String[] header, List<EconomyNewReport> economyReports,String shName,String sh1Name) {
		Sheet sheet=wb.createSheet(shName);
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		Map<String,Double> countMap = new HashMap<String, Double>();
		for (EconomyNewReport er : economyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					er.getDs(),
					er.getPhylum(),
					er.getAmount(),
					er.getUsers(),
					er.getTimes(),
					er.getItemNum()
			};
			
			for(int i=2;i<data.length;i++){
				Double dt = countMap.get(data[0]+"_"+i+"");
				
				countMap.put(data[0]+"_"+i+"", dt == null ? Double.valueOf(String.valueOf(data[i])).doubleValue()
						: dt.doubleValue() + Double.valueOf(String.valueOf(data[i])).doubleValue());
			}
			
			writeRow(row, data);
		}
		
		Sheet sheet1=wb.createSheet(sh1Name);
		int rowIdx1=0;
		Row headerRow1=sheet1.createRow(rowIdx1++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow1.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (EconomyNewReport er : economyReports) {
			Row row=sheet1.createRow(rowIdx1++);
			Object data[] = {
					er.getDs(),
					er.getPhylum(),
					Math.round((er.getAmount()/countMap.get(er.getDs()+"_2"))*10000d)/100d+"%",
					Math.round((Double.valueOf(er.getUsers()).doubleValue() / countMap.get(er.getDs()+"_3"))*10000d)/100d+"%",
					Math.round((Double.valueOf(er.getTimes()).doubleValue() / countMap.get(er.getDs()+"_4"))*10000d)/100d+"%",
					Math.round((Double.valueOf(er.getItemNum()).doubleValue() / countMap.get(er.getDs()+"_5"))*10000d)/100d+"%"
			};
			
			writeRow(row, data);
		}
	}

	public void initEconomyNewReportList(
			List<EconomyNewReport> economyReports12,
			List<EconomyNewReport> economyReports22,
			List<EconomyNewReport> economyReports32,
			List<EconomyNewReport> economyReports42) {
		this.economyReports1 = economyReports12;
		this.economyReports2 = economyReports22;
		this.economyReports3 = economyReports32;
		this.economyReports4 = economyReports42;
	}
}
