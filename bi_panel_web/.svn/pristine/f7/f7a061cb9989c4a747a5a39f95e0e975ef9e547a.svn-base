package com.hoolai.panel.web.download;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.LevelInstallDauReport;
import com.hoolai.bi.report.service.impl.LevelDauReportServiceImpl;

public class DownLoadGameLevelProcessor {
	
	private Games games;
	private SimpleDateFormat sdf = LevelDauReportServiceImpl.sdf;
	private HttpServletResponse response;
	Workbook wb;
	CellStyle style;
	OutputStream out;
	
	List<LevelInstallDauReport> reports;
	List<LevelInstallDauReport> paymentReports;
	List<LevelInstallDauReport> payersReports;
	
	
	public DownLoadGameLevelProcessor(Games games,HttpServletResponse response){
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


	
	public void writeDauLevelReport(String beginDate,String endDate) throws UnsupportedEncodingException, ParseException {
		String fileName = "dau_level_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = getDateHeader(beginDate, endDate);
		createDauLevelSheet(reports,header, "dau_data", "dau_point(%)","dau");
		flushAndClose();
	}
	
	public void writeInstallLevelReport(String beginDate) throws UnsupportedEncodingException, ParseException{
		String fileName = "install_level_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = getInstallHeader(beginDate);
		createDauLevelSheet(reports,header, "install_data", "install_point(%)","install");
		flushAndClose();
	}
	
	private String[] getInstallHeader(String beginDateStr) throws ParseException{
		int index = 1;
		Date beginDate = sdf.parse(beginDateStr);
		Date endDate = sdf.parse(sdf.format(new Date()));//我只是想要去掉时分秒
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		
		String[] header = new String[16];
		header[0] = "等级";
		do{
			if(index == 1){
				header[index++] = "当日";
			}else if(index == 2){
				header[index++] = "次日";
			}else{
				header[index] = (index-1)+"日";
				index++;
			}
			
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
			
		}while(!(calendar.getTime().getTime() > endDate.getTime() || index >15));
		
		return Arrays.copyOf(header, index);
	}
	
	private String[] getDateHeader(String beginDateStr,String endDateStr)
			throws ParseException {
		int index = 1;
		Date beginDate = sdf.parse(beginDateStr);
		Date endDate = sdf.parse(endDateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		
		String[] header = new String[16];
		header[0] = "等级";
		do{
			header[index++] = sdf.format(calendar.getTime());
			
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
			
		}while(!(calendar.getTime().getTime() > endDate.getTime() || index >15));
		
		return Arrays.copyOf(header, index);
	}

	private void createDauLevelSheet(List<LevelInstallDauReport> reports,String[] header,String shName,String sh1Name,String downType) {
		Sheet sheet=wb.createSheet(shName);
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		boolean isHaveD0 = "payment".equals(downType) || "payers".equals(downType);
		if(reports.size() > 0){
			Object[] data_all = createDauLevelAllDataRow(reports,isHaveD0,downType);
			Row row=sheet.createRow(rowIdx++);
			writeRow(row, Arrays.copyOf(data_all, header.length));
			
			for (LevelInstallDauReport er : reports) {
				createDataRow(header, sheet, rowIdx++, isHaveD0, er,downType);
			}
			
			Sheet sheet1=wb.createSheet(sh1Name);
			int rowIdx1=0;
			Row headerRow1=sheet1.createRow(rowIdx1++);
			for (int i = 0; i < header.length; i++) {
				Cell cell=headerRow1.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(header[i]);
			}
			
			Row row_all=sheet1.createRow(rowIdx1++);
			writeRow(row_all, Arrays.copyOf(data_all, header.length));
			
			for (LevelInstallDauReport er : reports) {
				createPointRow(header, isHaveD0, data_all, sheet1,rowIdx1++, er,downType);
			}
		}
	}

	private void createPointRow(String[] header, boolean isHaveD0,Object[] data_all, Sheet sheet1, int rowIdx1,
			LevelInstallDauReport er,String downType) {
		Row row=sheet1.createRow(rowIdx1);
		Object data[] = null;
		boolean ispay = downType.equals("payment") || downType.equals("newPayment");
		double rate = ispay ? games.getRate() : 1;
		if(isHaveD0){
			  Object dataTemp[] = {
					er.getLevel(),
				    Math.round(((er.getDay0() == null ? 0 : er.getDay0()) / (Double)data_all[1] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay1() == null ? 0 : er.getDay1()) / (Double)data_all[2] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay2() == null ? 0 : er.getDay2()) / (Double)data_all[3] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay3() == null ? 0 : er.getDay3()) / (Double)data_all[4] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay4() == null ? 0 : er.getDay4()) / (Double)data_all[5] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay5() == null ? 0 : er.getDay5()) / (Double)data_all[6] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay6() == null ? 0 : er.getDay6()) / (Double)data_all[7] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay7() == null ? 0 : er.getDay7()) / (Double)data_all[8] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay8() == null ? 0 : er.getDay8()) / (Double)data_all[9] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay9() == null ? 0 : er.getDay9()) / (Double)data_all[10] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay10() == null ? 0 : er.getDay10()) / (Double)data_all[11] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay11() == null ? 0 : er.getDay11()) / (Double)data_all[12] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay12() == null ? 0 : er.getDay12()) / (Double)data_all[13] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay13() == null ? 0 : er.getDay13()) / (Double)data_all[14] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay14() == null ? 0 : er.getDay14()) / (Double)data_all[15] / rate) * 10000d)/100d + "%"
				};
			  data = dataTemp;
			}else{
			  Object dataTemp[] = {
					er.getLevel(),
					Math.round(((er.getDay0() == null ? 0 : er.getDay0()) / (Double)data_all[1] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay1() == null ? 0 : er.getDay1()) / (Double)data_all[2] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay2() == null ? 0 : er.getDay2()) / (Double)data_all[3] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay3() == null ? 0 : er.getDay3()) / (Double)data_all[4] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay4() == null ? 0 : er.getDay4()) / (Double)data_all[5] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay5() == null ? 0 : er.getDay5()) / (Double)data_all[6] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay6() == null ? 0 : er.getDay6()) / (Double)data_all[7] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay7() == null ? 0 : er.getDay7()) / (Double)data_all[8] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay8() == null ? 0 : er.getDay8()) / (Double)data_all[9] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay9() == null ? 0 : er.getDay9()) / (Double)data_all[10] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay10() == null ? 0 : er.getDay10()) / (Double)data_all[11] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay11() == null ? 0 : er.getDay11()) / (Double)data_all[12] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay12() == null ? 0 : er.getDay12()) / (Double)data_all[13] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay13() == null ? 0 : er.getDay13()) / (Double)data_all[14] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay14() == null ? 0 : er.getDay14()) / (Double)data_all[15] / rate) * 10000d)/100d + "%",
					Math.round(((er.getDay15() == null ? 0 : er.getDay15()) / (Double)data_all[16] / rate) * 10000d)/100d + "%"
				};
			  data = dataTemp;
			}
		writeRow(row, Arrays.copyOf(data, header.length));
	}

	private void createDataRow(String[] header, Sheet sheet, int rowIdx, boolean isHaveD0,LevelInstallDauReport er,String downType) {
		Row row = sheet.createRow(rowIdx++);
		Object data[] = null;
		boolean ispay = downType.equals("payment") || downType.equals("newPayment");
		double rate = ispay ? games.getRate() : 1;
		if(isHaveD0){
		  Object dataTemp[] = {
					er.getLevel(),
					er.getDay0() == null ? 0 : Math.round((er.getDay0()/rate)*100d)/100d,
					er.getDay1() == null ? 0 : Math.round((er.getDay1()/rate)*100d)/100d,
					er.getDay2() == null ? 0 : Math.round((er.getDay2()/rate)*100d)/100d,
					er.getDay3() == null ? 0 : Math.round((er.getDay3()/rate)*100d)/100d,
					er.getDay4() == null ? 0 : Math.round((er.getDay4()/rate)*100d)/100d,
					er.getDay5() == null ? 0 : Math.round((er.getDay5()/rate)*100d)/100d,
					er.getDay6() == null ? 0 : Math.round((er.getDay6()/rate)*100d)/100d,
					er.getDay7() == null ? 0 : Math.round((er.getDay7()/rate)*100d)/100d,
					er.getDay8() == null ? 0 : Math.round((er.getDay8()/rate)*100d)/100d,
					er.getDay9() == null ? 0 : Math.round((er.getDay9()/rate)*100d)/100d,
					er.getDay10() == null ? 0 : Math.round((er.getDay10()/rate)*100d)/100d,
					er.getDay11() == null ? 0 : Math.round((er.getDay11()/rate)*100d)/100d,
					er.getDay12() == null ? 0 : Math.round((er.getDay12()/rate)*100d)/100d,
					er.getDay13() == null ? 0 : Math.round((er.getDay13()/rate)*100d)/100d,
					er.getDay14() == null ? 0 : Math.round((er.getDay14()/rate)*100d)/100d
			};
		  data = dataTemp;
		}else{
		  Object dataTemp[] = {
					er.getLevel(),
					er.getDay1() == null ? 0 : Math.round((er.getDay1()/rate)*100d)/100d,
					er.getDay2() == null ? 0 : Math.round((er.getDay2()/rate)*100d)/100d,
					er.getDay3() == null ? 0 : Math.round((er.getDay3()/rate)*100d)/100d,
					er.getDay4() == null ? 0 : Math.round((er.getDay4()/rate)*100d)/100d,
					er.getDay5() == null ? 0 : Math.round((er.getDay5()/rate)*100d)/100d,
					er.getDay6() == null ? 0 : Math.round((er.getDay6()/rate)*100d)/100d,
					er.getDay7() == null ? 0 : Math.round((er.getDay7()/rate)*100d)/100d,
					er.getDay8() == null ? 0 : Math.round((er.getDay8()/rate)*100d)/100d,
					er.getDay9() == null ? 0 : Math.round((er.getDay9()/rate)*100d)/100d,
					er.getDay10() == null ? 0 : Math.round((er.getDay10()/rate)*100d)/100d,
					er.getDay11() == null ? 0 : Math.round((er.getDay11()/rate)*100d)/100d,
					er.getDay12() == null ? 0 : Math.round((er.getDay12()/rate)*100d)/100d,
					er.getDay13() == null ? 0 : Math.round((er.getDay13()/rate)*100d)/100d,
					er.getDay14() == null ? 0 : Math.round((er.getDay14()/rate)*100d)/100d,
			        er.getDay15() == null ? 0 : Math.round((er.getDay15()/rate)*100d)/100d
			};
		  data = dataTemp;
		}
		
		writeRow(row, Arrays.copyOf(data, header.length));
	}

	private Object[] createDauLevelAllDataRow(List<LevelInstallDauReport> reports,boolean isHaveD0,String downType) {
		LevelInstallDauReport countReport = reports.get(reports.size()-1);
		reports.remove(reports.size()-1);
		boolean ispay = downType.equals("payment") || downType.equals("newPayment");
		if(isHaveD0){
			Object data_all[] = {
					ispay ? "总消费额("+games.getCurrency()+")" : "总人数",
					countReport.getDay0() == null ? 0 : ispay ? Math.round((countReport.getDay0()/games.getRate())*100d)/100d : countReport.getDay0(),
					countReport.getDay1() == null ? 0 : ispay ? Math.round((countReport.getDay1()/games.getRate())*100d)/100d : countReport.getDay1(),
					countReport.getDay2() == null ? 0 : ispay ? Math.round((countReport.getDay2()/games.getRate())*100d)/100d : countReport.getDay2(),
					countReport.getDay3() == null ? 0 : ispay ? Math.round((countReport.getDay3()/games.getRate())*100d)/100d : countReport.getDay3(),
					countReport.getDay4() == null ? 0 : ispay ? Math.round((countReport.getDay4()/games.getRate())*100d)/100d : countReport.getDay4(),
					countReport.getDay5() == null ? 0 : ispay ? Math.round((countReport.getDay5()/games.getRate())*100d)/100d : countReport.getDay5(),
					countReport.getDay6() == null ? 0 : ispay ? Math.round((countReport.getDay6()/games.getRate())*100d)/100d : countReport.getDay6(),
					countReport.getDay7() == null ? 0 : ispay ? Math.round((countReport.getDay7()/games.getRate())*100d)/100d : countReport.getDay7(),
					countReport.getDay8() == null ? 0 : ispay ? Math.round((countReport.getDay8()/games.getRate())*100d)/100d : countReport.getDay8(),
					countReport.getDay9() == null ? 0 : ispay ? Math.round((countReport.getDay9()/games.getRate())*100d)/100d : countReport.getDay9(),
					countReport.getDay10() == null ? 0 : ispay ? Math.round((countReport.getDay10()/games.getRate())*100d)/100d : countReport.getDay10(),
					countReport.getDay11() == null ? 0 : ispay ? Math.round((countReport.getDay11()/games.getRate())*100d)/100d : countReport.getDay11(),
					countReport.getDay12() == null ? 0 : ispay ? Math.round((countReport.getDay12()/games.getRate())*100d)/100d : countReport.getDay12(),
					countReport.getDay13() == null ? 0 : ispay ? Math.round((countReport.getDay13()/games.getRate())*100d)/100d : countReport.getDay13(),
					countReport.getDay14() == null ? 0 : ispay ? Math.round((countReport.getDay14()/games.getRate())*100d)/100d : countReport.getDay14()
			};
			return data_all;
		}else{
			Object data_all[] = {
					ispay ? "总消费额("+games.getCurrency()+")" : "总人数",
					countReport.getDay0() == null ? 0 : ispay ? Math.round((countReport.getDay0()/games.getRate())*100d)/100d : countReport.getDay0(),
					countReport.getDay1() == null ? 0 : ispay ? Math.round((countReport.getDay1()/games.getRate())*100d)/100d : countReport.getDay1(),
					countReport.getDay2() == null ? 0 : ispay ? Math.round((countReport.getDay2()/games.getRate())*100d)/100d : countReport.getDay2(),
					countReport.getDay3() == null ? 0 : ispay ? Math.round((countReport.getDay3()/games.getRate())*100d)/100d : countReport.getDay3(),
					countReport.getDay4() == null ? 0 : ispay ? Math.round((countReport.getDay4()/games.getRate())*100d)/100d : countReport.getDay4(),
					countReport.getDay5() == null ? 0 : ispay ? Math.round((countReport.getDay5()/games.getRate())*100d)/100d : countReport.getDay5(),
					countReport.getDay6() == null ? 0 : ispay ? Math.round((countReport.getDay6()/games.getRate())*100d)/100d : countReport.getDay6(),
					countReport.getDay7() == null ? 0 : ispay ? Math.round((countReport.getDay7()/games.getRate())*100d)/100d : countReport.getDay7(),
					countReport.getDay8() == null ? 0 : ispay ? Math.round((countReport.getDay8()/games.getRate())*100d)/100d : countReport.getDay8(),
					countReport.getDay9() == null ? 0 : ispay ? Math.round((countReport.getDay9()/games.getRate())*100d)/100d : countReport.getDay9(),
					countReport.getDay10() == null ? 0 : ispay ? Math.round((countReport.getDay10()/games.getRate())*100d)/100d : countReport.getDay10(),
					countReport.getDay11() == null ? 0 : ispay ? Math.round((countReport.getDay11()/games.getRate())*100d)/100d : countReport.getDay11(),
					countReport.getDay12() == null ? 0 : ispay ? Math.round((countReport.getDay12()/games.getRate())*100d)/100d : countReport.getDay12(),
					countReport.getDay13() == null ? 0 : ispay ? Math.round((countReport.getDay13()/games.getRate())*100d)/100d : countReport.getDay13(),
					countReport.getDay14() == null ? 0 : ispay ? Math.round((countReport.getDay14()/games.getRate())*100d)/100d : countReport.getDay14(),
					countReport.getDay15() == null ? 0 : ispay ? Math.round((countReport.getDay15()/games.getRate())*100d)/100d : countReport.getDay15(),
			};
			return data_all;
		}
	}

	public void setReports(List<LevelInstallDauReport> reports) {
		this.reports = reports;
	}
	
	public void setReports(List<LevelInstallDauReport> paymentReports,List<LevelInstallDauReport> payersReports) {
		this.payersReports = payersReports;
		this.paymentReports = paymentReports;
	}

	public void writeInstallPaymentLevelReport(String beginDate) throws UnsupportedEncodingException, ParseException {
		String fileName = "install_level_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = getInstallHeader(beginDate);
		createDauLevelSheet(paymentReports,header, "install_payment_data", "install_payment_point(%)","payment");
		createDauLevelSheet(payersReports,header, "install_payers_data", "install_payers_point(%)","payers");
		flushAndClose();
	}
	
	public void writeNewPaymentLevelReport(String beginDate,String endDate,int clientid) throws UnsupportedEncodingException, ParseException {
		String fileName = clientid == 0 ? "newPay_level_report.xlsx" : "client_level_report_"+clientid+".xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = getDateHeader(beginDate, endDate);
		createDauLevelSheet(paymentReports,header, "new_payment_data", "new_payment_point(%)","newPayment");
		createDauLevelSheet(payersReports,header, "new_payers_data", "new_payers_point(%)","newPayers");
		flushAndClose();
	}
	
}
