package com.hoolai.panel.web.download;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hoolai.bi.report.model.Types.GameplayerType;
import com.hoolai.bi.report.model.entity.AdminOpMonitorStandard;
import com.hoolai.bi.report.model.entity.ChannelCpa;
import com.hoolai.bi.report.model.entity.ClientDailyReport;
import com.hoolai.bi.report.model.entity.CostPerSourceDimension;
import com.hoolai.bi.report.model.entity.CpaSourceDimension;
import com.hoolai.bi.report.model.entity.GameLtv;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.HourClientDailyReport;
import com.hoolai.bi.report.model.entity.HourLtvSourceDailyReport;
import com.hoolai.bi.report.model.entity.HourSourceDailyReport;
import com.hoolai.bi.report.model.entity.HourSourceRetentionDailyReport;
import com.hoolai.bi.report.model.entity.LevelPay;
import com.hoolai.bi.report.model.entity.RealTimeGameClient;
import com.hoolai.bi.report.model.entity.RealTimeNoClientResult;
import com.hoolai.bi.report.model.entity.RealTimeResult;
import com.hoolai.bi.report.model.entity.RealtimeSourceReport;
import com.hoolai.bi.report.model.entity.ReportingEachTimeNC;
import com.hoolai.bi.report.model.entity.SourceCpaDailyReport;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.bi.report.model.entity.equip.EquipDay;
import com.hoolai.bi.report.model.entity.equip.EquipLtv;
import com.hoolai.bi.report.model.entity.equip.EquipModelDay;
import com.hoolai.bi.report.model.entity.equip.EquipSourceDay;
import com.hoolai.bi.report.model.entity.equip.EquipVersionDau;
import com.hoolai.bi.report.model.entity.whaleUser.WhaleUser;
import com.hoolai.bi.report.util.DateUtils;
import com.hoolai.bi.report.vo.PayUserRetentionLtvVO;
import com.hoolai.bi.report.vo.PaylevelAmountCntClientVO;
import com.hoolai.bi.report.vo.PaylevelAmountCntSourceVO;
import com.hoolai.bi.report.vo.RealTimeGameClientVo;
import com.hoolai.bi.report.vo.RealTimeGameSourceVo;
import com.hoolai.bi.report.vo.UserRetentionClientLtvVO;
import com.hoolai.bi.report.vo.UserRetentionLtvVO;
import com.hoolai.bi.report.vo.UserRetentionSourceLtvVO;
import com.hoolai.manage.model.Platforms;

public class DownLoadCsvProcessor {

	private List<SourceDailyReport> sourceDailyReports;
	private List<WandaDailyReport> wandaDailyReports;
	private List<ClientDailyReport> clientDailyReports;
	private List<UserRetentionSourceLtvVO> userRetentionSourceLtvs;
	private List<UserRetentionClientLtvVO> userRetentionClientLtvs;
	private List<UserRetentionLtvVO> userRetentionLtvVO;
	
	List<PaylevelAmountCntSourceVO> cntPaySourceList;
	List<PaylevelAmountCntSourceVO> cntUserSourceList;
	List<PaylevelAmountCntClientVO> cntPayClientList;
	List<PaylevelAmountCntClientVO> cntUserClientList;
	List<RealTimeResult> realTimeList;
	List<RealTimeGameClientVo> realTimeGameClientidVo;
	List<RealTimeGameSourceVo> realTimeGameSourceVo;
	
	List<HourSourceDailyReport> hourSourceDailyReports;
	List<HourLtvSourceDailyReport> hourLtvSourceDailyReports;
	List<HourSourceRetentionDailyReport> hourSourceRetentionDailyReports;
	List<HourClientDailyReport> hourClientDailyReports;
	List<RealtimeSourceReport> sourceReports;
	List<Platforms> platforms;
	
	private Games games;
	private HttpServletResponse response;
	Workbook wb;
	CellStyle style;
	OutputStream out;
	
	public DownLoadCsvProcessor(Games games,HttpServletResponse response){
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
	
	public void writeChannelCpa(List<SourceCpaDailyReport> channelCpss, List<CpaSourceDimension> dimensions,boolean isOutSider,String name) throws UnsupportedEncodingException {
		String fileName = name+"_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header1 = {"日期","渠道代码","渠道名称","付费人数","付费次数",
				"付费额("+games.getCurrency()+")"};
		String[] header2 = {"日期","渠道代码","渠道名称","付费人数","付费次数",
				"付费额("+games.getCurrency()+")","扣量付费人数","扣量付费次数",
				"扣量付费额("+games.getCurrency()+")"};
		String[] header3 = {"渠道代码","渠道名称","付费人数","付费次数",
				"付费额("+games.getCurrency()+")"};
		String[] header4 = {"渠道代码","渠道名称","付费人数","付费次数",
				"付费额("+games.getCurrency()+")","扣量付费人数","扣量付费次数",
				"扣量付费额("+games.getCurrency()+")"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		String[] header = "cpa_channel".equals(name) ? isOutSider ? header3 : header4 : isOutSider ? header1 :header2;
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (SourceCpaDailyReport sdr : channelCpss) {
			Row row=sheet.createRow(rowIdx++);
			
			CpaSourceDimension dimension = getCpaDimension(dimensions,sdr.getSource());
			
			if(isOutSider){
				if("cpa_channel".equals(name)){
					Object data1[] = {
							sdr.getSource(),
							dimension == null ? sdr.getSource() : dimension.getSourceName(),
							dimension == null ? sdr.getPu() : Math.round(sdr.getPu()*dimension.getPayUserRate()),
							dimension == null ? sdr.getPaymentCnt() : Math.round(sdr.getPaymentCnt()*dimension.getPayTimesRate()),
						    dimension == null ? sdr.getPaymentAmount() : Math.round((sdr.getPaymentAmount()/games.getRate())*dimension.getPayUserRate() * 100)/100
					};
					writeRow(row, data1);
				}else{
					Object data1[] = {
							sdr.getDay(),
							sdr.getSource(),
							dimension == null ? sdr.getSource() : dimension.getSourceName(),
							dimension == null ? sdr.getPu() : Math.round(sdr.getPu()*dimension.getPayUserRate()),
							dimension == null ? sdr.getPaymentCnt() : Math.round(sdr.getPaymentCnt()*dimension.getPayTimesRate()),
						    dimension == null ? sdr.getPaymentAmount() : Math.round((sdr.getPaymentAmount()/games.getRate())*dimension.getPayUserRate() * 100)/100
					};
					writeRow(row, data1);
				}
				
			}else{
				if("cpa_channel".equals(name)){
					Object data2[] = {
							sdr.getSource(),
							dimension == null ? sdr.getSource() : dimension.getSourceName(),
							sdr.getPu(),
							sdr.getPaymentCnt(),
							Math.round(sdr.getPaymentAmount()/games.getRate()*100)/100,
							dimension == null ? sdr.getPu() : Math.round(sdr.getPu()*dimension.getPayUserRate()),
							dimension == null ? sdr.getPaymentCnt() : Math.round(sdr.getPaymentCnt()*dimension.getPayTimesRate()),
						    dimension == null ? sdr.getPaymentAmount() : Math.round((sdr.getPaymentAmount()/games.getRate())*dimension.getPayUserRate() * 100)/100
					};
					
					writeRow(row, data2);
				}else{
					Object data2[] = {
							sdr.getDay(),
							sdr.getSource(),
							dimension == null ? sdr.getSource() : dimension.getSourceName(),
							sdr.getPu(),
							sdr.getPaymentCnt(),
							Math.round(sdr.getPaymentAmount()/games.getRate()*100)/100,
							dimension == null ? sdr.getPu() : Math.round(sdr.getPu()*dimension.getPayUserRate()),
							dimension == null ? sdr.getPaymentCnt() : Math.round(sdr.getPaymentCnt()*dimension.getPayTimesRate()),
						    dimension == null ? sdr.getPaymentAmount() : Math.round((sdr.getPaymentAmount()/games.getRate())*dimension.getPayUserRate() * 100)/100
					};
					writeRow(row, data2);
				}
			}
		}
		flushAndClose();
	}
	
	public static CpaSourceDimension getCpaDimension(List<CpaSourceDimension> dimensions, String source) {
		for(CpaSourceDimension dimension:dimensions){
			if(source.equals(dimension.getSourceCode())){
				return dimension;
			}
		}
		return null;
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
	
	public void initWandsList(List<WandaDailyReport> wandaDailyReports){
		this.wandaDailyReports = wandaDailyReports;
	}
	
	public void initSourceList(List<SourceDailyReport> sourceDailyReports){
		this.sourceDailyReports = sourceDailyReports;
	}
	
	public void initClientList(List<ClientDailyReport> clientDailyReports){
		this.clientDailyReports = clientDailyReports;
	}
	
	public void initSourceLtvList(List<UserRetentionSourceLtvVO> lts) {
		this.userRetentionSourceLtvs = lts;
	}
	
	public void initClientLtvList(List<UserRetentionClientLtvVO> lts) {
		this.userRetentionClientLtvs = lts;
	}
	
	public void initClientMoneyList(List<UserRetentionLtvVO> money){
		this.userRetentionLtvVO = money;
	}
	
	public void initClientRegList(List<UserRetentionLtvVO> ltvs){
		this.userRetentionLtvVO = ltvs;
	}
	
	public void initSourcePayBehaviorList(
			List<PaylevelAmountCntSourceVO> selectPayList,
			List<PaylevelAmountCntSourceVO> selectUserList) {
		this.cntPaySourceList = selectPayList;
		this.cntUserSourceList = selectUserList;
	}
	
	public void initClientPayBehaviorList(
			List<PaylevelAmountCntClientVO> selectPayList,
			List<PaylevelAmountCntClientVO> selectUserList) {
		this.cntPayClientList = selectPayList;
		this.cntUserClientList = selectUserList;
	}
	
	public void initRealTimeList(List<RealTimeResult> realTimeList) {
		this.realTimeList = realTimeList;
	}
	
	public void writeSourceInstall()throws IOException {
		String fileName = "source_install.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道","新注册","创角数","注册付费("+games.getCurrency()+")","注册付费率","ARPU("+games.getCurrency()+")","ARPPU("+games.getCurrency()+")"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (SourceDailyReport sdr : sourceDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					sdr.getDay(),
					sdr.getSource(),
					sdr.getDnu(),
					sdr.getRoleChoice(),
					Math.round((sdr.getInstallPayAmount()/games.getRate())*100d)/100d,
					sdr.getDnu()==0 ? "-%" : (Math.round((Double.valueOf(sdr.getInstallPu())/Double.valueOf(sdr.getDnu()))*100d*100d)/100d)+"%",
					sdr.getDnu()==0 ? "-" :Math.round((sdr.getInstallPayAmount()/Double.valueOf(sdr.getDnu())/games.getRate())*100d)/100d,
					sdr.getInstallPu()==0 ? "-" :Math.round((sdr.getInstallPayAmount()/Double.valueOf(sdr.getInstallPu())/games.getRate())*100d)/100d
					
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}
	
	public void writeWandaDaily(String beginDate, String endDate) throws IOException {
		String fileName = "万达游戏流水表-"+beginDate+"至"+endDate+".xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = {"序号","游戏名称","上线时间","收入期间","渠道","流水(元)","暂估分成比例(%)","互爱互动收入"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for(WandaDailyReport wdr: wandaDailyReports){
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					wdr.getId(),
					wdr.getSeriesName(),
					wdr.getOnlineDate(),
					wdr.getDs(),
					wdr.getCreative(),
					wdr.getPaymentAmount().doubleValue(),
					wdr.getRate(),
					wdr.getHulaiAmount().doubleValue()
			};
			writeRow(row, data);
		}
		flushAndClose();
	}
	
	public void writeClientInstall()throws IOException {
		String fileName = "client_install.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = {"日期","服务器","新注册","创角数","注册付费("+games.getCurrency()+")","注册付费率","ARPU("+games.getCurrency()+")","ARPPU("+games.getCurrency()+")"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (ClientDailyReport cdr : clientDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					cdr.getDay(),
					cdr.getClientid(),
					cdr.getDnu(),
					"-",
					Math.round((cdr.getInstallPayAmount()/games.getRate())*100d)/100d,
					cdr.getDnu()==0 ? "-%" : (Math.round((Double.valueOf(cdr.getInstallPu())/Double.valueOf(cdr.getDnu()))*100d*100d)/100d)+"%",
					cdr.getDnu()==0 ? "-" :Math.round((cdr.getInstallPayAmount()/Double.valueOf(cdr.getDnu())/games.getRate())*100d)/100d,
					cdr.getInstallPu()==0 ? "-" :Math.round((cdr.getInstallPayAmount()/Double.valueOf(cdr.getInstallPu())/games.getRate())*100d)/100d
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	public void writeSourceDau() throws UnsupportedEncodingException {
		String fileName = "source_dau.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道","dau","新增活跃","老玩家活跃","最高在线人数","平均在线人数","平均在线时长"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (SourceDailyReport sdr : sourceDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					sdr.getDay(),
					sdr.getSource(),
					sdr.getDau(),
					sdr.getDnu(),
					sdr.getDau() - sdr.getDnu(),
					sdr.getPcu(),
					Math.round(sdr.getAcu()*10d)/10d,
					(Math.round((sdr.getAvgUserTime()/60d)*10d)/10d)+"分"
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	
	}

	public void writeClientDau() throws UnsupportedEncodingException {
		String fileName = "client_dau.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","服务器","dau","新增活跃","老玩家活跃","最高在线人数","平均在线人数","平均在线时长"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (ClientDailyReport cdr : clientDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					cdr.getDay(),
					cdr.getClientid(),
					cdr.getDau(),
					cdr.getDnu(),
					cdr.getDau() - cdr.getDnu(),
					cdr.getPcu(),
					Math.round(cdr.getAcu()*10d)/10d,
					(Math.round((cdr.getAvgUserTime()/60d)*10d)/10d)+"分"
			};
			
			writeRow(row, data);
		}
		flushAndClose();
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
	
	public void writeSourceRetention(GameplayerType queryType)throws IOException {
		String fileName = "source_retention_install.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道",
				queryType == GameplayerType.INSATLL ? "新注册" : "创角数",
						"D1","D2","D3","D4","D5","D6","D7","D8","D9","D10","D11",
						"D12","D13","D14","D15","D16","D17","D18","D19","D20","D21",
						"D22","D23","D24","D25","D26","D27","D28","D29","D30","D31",
						"D32","D33","D34","D35","D36","D37","D38","D39","D40","D41",
						"D42","D43","D44","D45","D46","D47","D48","D49","D50","D51",
						"D52","D53","D54","D55","D56","D57","D58","D59","D60","D61",
						"D62","D63","D64","D65","D66","D67","D68","D69","D70","D71",
						"D72","D73","D74","D75","D76","D77","D78","D79","D80","D81",
						"D82","D83","D84","D85","D86","D87","D88","D89","D90"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (UserRetentionSourceLtvVO ltvVO : userRetentionSourceLtvs) {
			GameLtv ltv = ltvVO.getGameLtv();
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					ltv.getInstallDay(),
					ltv.getSourceOrClientName(),
					ltv.getInstall(),
					ltv.getD1() == null ? "-" : Math.round(ltv.getD1() * 100d * 100d)/100d + "%",
					ltv.getD2() == null ? "-" : Math.round(ltv.getD2() * 100d * 100d)/100d + "%", 
					ltv.getD3() == null ? "-" : Math.round(ltv.getD3() * 100d * 100d)/100d + "%", 
					ltv.getD4() == null ? "-" : Math.round(ltv.getD4() * 100d * 100d)/100d + "%", 
					ltv.getD5() == null ? "-" : Math.round(ltv.getD5() * 100d * 100d)/100d + "%", 
					ltv.getD6() == null ? "-" : Math.round(ltv.getD6() * 100d * 100d)/100d + "%", 
					ltv.getD7() == null ? "-" : Math.round(ltv.getD7() * 100d * 100d)/100d + "%",
					ltv.getD8() == null ? "-" : Math.round(ltv.getD8() * 100d * 100d)/100d + "%", 
					ltv.getD9() == null ? "-" : Math.round(ltv.getD9() * 100d * 100d)/100d + "%", 
					ltv.getD10() == null ? "-" : Math.round(ltv.getD10() * 100d * 100d)/100d + "%",
					ltv.getD11() == null ? "-" : Math.round(ltv.getD11() * 100d * 100d)/100d + "%",
					ltv.getD12() == null ? "-" : Math.round(ltv.getD12() * 100d * 100d)/100d + "%",
					ltv.getD13() == null ? "-" : Math.round(ltv.getD13() * 100d * 100d)/100d + "%",
					ltv.getD14() == null ? "-" : Math.round(ltv.getD14() * 100d * 100d)/100d + "%",
					ltv.getD15() == null ? "-" : Math.round(ltv.getD15() * 100d * 100d)/100d + "%",
					ltv.getD16() == null ? "-" : Math.round(ltv.getD16() * 100d * 100d)/100d + "%",
					ltv.getD17() == null ? "-" : Math.round(ltv.getD17() * 100d * 100d)/100d + "%",
					ltv.getD18() == null ? "-" : Math.round(ltv.getD18() * 100d * 100d)/100d + "%",
					ltv.getD19() == null ? "-" : Math.round(ltv.getD19() * 100d * 100d)/100d + "%",
					ltv.getD20() == null ? "-" : Math.round(ltv.getD20() * 100d * 100d)/100d + "%",
					ltv.getD21() == null ? "-" : Math.round(ltv.getD21() * 100d * 100d)/100d + "%",
					ltv.getD22() == null ? "-" : Math.round(ltv.getD22() * 100d * 100d)/100d + "%",
					ltv.getD23() == null ? "-" : Math.round(ltv.getD23() * 100d * 100d)/100d + "%",
					ltv.getD24() == null ? "-" : Math.round(ltv.getD24() * 100d * 100d)/100d + "%",
					ltv.getD25() == null ? "-" : Math.round(ltv.getD25() * 100d * 100d)/100d + "%",
					ltv.getD26() == null ? "-" : Math.round(ltv.getD26() * 100d * 100d)/100d + "%",
					ltv.getD27() == null ? "-" : Math.round(ltv.getD27() * 100d * 100d)/100d + "%",
					ltv.getD28() == null ? "-" : Math.round(ltv.getD28() * 100d * 100d)/100d + "%",
					ltv.getD29() == null ? "-" : Math.round(ltv.getD29() * 100d * 100d)/100d + "%",
					ltv.getD30() == null ? "-" : Math.round(ltv.getD30() * 100d * 100d)/100d + "%",
					ltv.getD31() == null ? "-" : Math.round(ltv.getD31() * 100d * 100d)/100d + "%",
					ltv.getD32() == null ? "-" : Math.round(ltv.getD32() * 100d * 100d)/100d + "%",
					ltv.getD33() == null ? "-" : Math.round(ltv.getD33() * 100d * 100d)/100d + "%",
					ltv.getD34() == null ? "-" : Math.round(ltv.getD34() * 100d * 100d)/100d + "%",
					ltv.getD35() == null ? "-" : Math.round(ltv.getD35() * 100d * 100d)/100d + "%",
					ltv.getD36() == null ? "-" : Math.round(ltv.getD36() * 100d * 100d)/100d + "%",
					ltv.getD37() == null ? "-" : Math.round(ltv.getD37() * 100d * 100d)/100d + "%",
					ltv.getD38() == null ? "-" : Math.round(ltv.getD38() * 100d * 100d)/100d + "%",
					ltv.getD39() == null ? "-" : Math.round(ltv.getD39() * 100d * 100d)/100d + "%",
					ltv.getD40() == null ? "-" : Math.round(ltv.getD40() * 100d * 100d)/100d + "%",
					ltv.getD41() == null ? "-" : Math.round(ltv.getD41() * 100d * 100d)/100d + "%",
					ltv.getD42() == null ? "-" : Math.round(ltv.getD42() * 100d * 100d)/100d + "%",
					ltv.getD43() == null ? "-" : Math.round(ltv.getD43() * 100d * 100d)/100d + "%",
					ltv.getD44() == null ? "-" : Math.round(ltv.getD44() * 100d * 100d)/100d + "%",
					ltv.getD45() == null ? "-" : Math.round(ltv.getD45() * 100d * 100d)/100d + "%",
					ltv.getD46() == null ? "-" : Math.round(ltv.getD46() * 100d * 100d)/100d + "%",
					ltv.getD47() == null ? "-" : Math.round(ltv.getD47() * 100d * 100d)/100d + "%",
					ltv.getD48() == null ? "-" : Math.round(ltv.getD48() * 100d * 100d)/100d + "%",
					ltv.getD49() == null ? "-" : Math.round(ltv.getD49() * 100d * 100d)/100d + "%",
					ltv.getD50() == null ? "-" : Math.round(ltv.getD50() * 100d * 100d)/100d + "%",
					ltv.getD51() == null ? "-" : Math.round(ltv.getD51() * 100d * 100d)/100d + "%",
					ltv.getD52() == null ? "-" : Math.round(ltv.getD52() * 100d * 100d)/100d + "%",
					ltv.getD53() == null ? "-" : Math.round(ltv.getD53() * 100d * 100d)/100d + "%",
					ltv.getD54() == null ? "-" : Math.round(ltv.getD54() * 100d * 100d)/100d + "%",
					ltv.getD55() == null ? "-" : Math.round(ltv.getD55() * 100d * 100d)/100d + "%",
					ltv.getD56() == null ? "-" : Math.round(ltv.getD56() * 100d * 100d)/100d + "%",
					ltv.getD57() == null ? "-" : Math.round(ltv.getD57() * 100d * 100d)/100d + "%",
					ltv.getD58() == null ? "-" : Math.round(ltv.getD58() * 100d * 100d)/100d + "%",
					ltv.getD59() == null ? "-" : Math.round(ltv.getD59() * 100d * 100d)/100d + "%",
					ltv.getD60() == null ? "-" : Math.round(ltv.getD60() * 100d * 100d)/100d + "%",
					ltv.getD61() == null ? "-" : Math.round(ltv.getD61() * 100d * 100d)/100d + "%",
					ltv.getD62() == null ? "-" : Math.round(ltv.getD62() * 100d * 100d)/100d + "%",
					ltv.getD63() == null ? "-" : Math.round(ltv.getD63() * 100d * 100d)/100d + "%",
					ltv.getD64() == null ? "-" : Math.round(ltv.getD64() * 100d * 100d)/100d + "%",
					ltv.getD65() == null ? "-" : Math.round(ltv.getD65() * 100d * 100d)/100d + "%",
					ltv.getD66() == null ? "-" : Math.round(ltv.getD66() * 100d * 100d)/100d + "%",
					ltv.getD67() == null ? "-" : Math.round(ltv.getD67() * 100d * 100d)/100d + "%",
					ltv.getD68() == null ? "-" : Math.round(ltv.getD68() * 100d * 100d)/100d + "%",
					ltv.getD69() == null ? "-" : Math.round(ltv.getD69() * 100d * 100d)/100d + "%",
					ltv.getD70() == null ? "-" : Math.round(ltv.getD70() * 100d * 100d)/100d + "%",
					ltv.getD71() == null ? "-" : Math.round(ltv.getD71() * 100d * 100d)/100d + "%",
					ltv.getD72() == null ? "-" : Math.round(ltv.getD72() * 100d * 100d)/100d + "%",
					ltv.getD73() == null ? "-" : Math.round(ltv.getD73() * 100d * 100d)/100d + "%",
					ltv.getD74() == null ? "-" : Math.round(ltv.getD74() * 100d * 100d)/100d + "%",
					ltv.getD75() == null ? "-" : Math.round(ltv.getD75() * 100d * 100d)/100d + "%",
					ltv.getD76() == null ? "-" : Math.round(ltv.getD76() * 100d * 100d)/100d + "%",
					ltv.getD77() == null ? "-" : Math.round(ltv.getD77() * 100d * 100d)/100d + "%",
					ltv.getD78() == null ? "-" : Math.round(ltv.getD78() * 100d * 100d)/100d + "%",
					ltv.getD79() == null ? "-" : Math.round(ltv.getD79() * 100d * 100d)/100d + "%",
					ltv.getD80() == null ? "-" : Math.round(ltv.getD80() * 100d * 100d)/100d + "%",
					ltv.getD81() == null ? "-" : Math.round(ltv.getD81() * 100d * 100d)/100d + "%",
					ltv.getD82() == null ? "-" : Math.round(ltv.getD82() * 100d * 100d)/100d + "%",
					ltv.getD83() == null ? "-" : Math.round(ltv.getD83() * 100d * 100d)/100d + "%",
					ltv.getD84() == null ? "-" : Math.round(ltv.getD84() * 100d * 100d)/100d + "%",
					ltv.getD85() == null ? "-" : Math.round(ltv.getD85() * 100d * 100d)/100d + "%",
					ltv.getD86() == null ? "-" : Math.round(ltv.getD86() * 100d * 100d)/100d + "%",
					ltv.getD87() == null ? "-" : Math.round(ltv.getD87() * 100d * 100d)/100d + "%",
					ltv.getD88() == null ? "-" : Math.round(ltv.getD88() * 100d * 100d)/100d + "%",
					ltv.getD89() == null ? "-" : Math.round(ltv.getD89() * 100d * 100d)/100d + "%",
					ltv.getD90() == null ? "-" : Math.round(ltv.getD90() * 100d * 100d)/100d + "%" 
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}
	
	public void writeClientRetention()throws IOException {
		String fileName = "client_retention_role.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","服务器","新注册",
				"D1","D2","D3","D4","D5","D6","D7","D8","D9","D10","D11",
				"D12","D13","D14","D15","D16","D17","D18","D19","D20","D21",
				"D22","D23","D24","D25","D26","D27","D28","D29","D30","D31",
				"D32","D33","D34","D35","D36","D37","D38","D39","D40","D41",
				"D42","D43","D44","D45","D46","D47","D48","D49","D50","D51",
				"D52","D53","D54","D55","D56","D57","D58","D59","D60","D61",
				"D62","D63","D64","D65","D66","D67","D68","D69","D70","D71",
				"D72","D73","D74","D75","D76","D77","D78","D79","D80","D81",
				"D82","D83","D84","D85","D86","D87","D88","D89","D90"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (UserRetentionClientLtvVO ltvVO : userRetentionClientLtvs) {
			GameLtv ltv = ltvVO.getGameLtv();
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					ltv.getInstallDay(),
					ltv.getSourceOrClientName(),
					ltv.getInstall(),
					ltv.getD1() == null ? "-" : Math.round(ltv.getD1() * 100d * 100d)/100d + "%",
					ltv.getD2() == null ? "-" : Math.round(ltv.getD2() * 100d * 100d)/100d + "%", 
					ltv.getD3() == null ? "-" : Math.round(ltv.getD3() * 100d * 100d)/100d + "%", 
					ltv.getD4() == null ? "-" : Math.round(ltv.getD4() * 100d * 100d)/100d + "%", 
					ltv.getD5() == null ? "-" : Math.round(ltv.getD5() * 100d * 100d)/100d + "%", 
					ltv.getD6() == null ? "-" : Math.round(ltv.getD6() * 100d * 100d)/100d + "%", 
					ltv.getD7() == null ? "-" : Math.round(ltv.getD7() * 100d * 100d)/100d + "%",
					ltv.getD8() == null ? "-" : Math.round(ltv.getD8() * 100d * 100d)/100d + "%", 
					ltv.getD9() == null ? "-" : Math.round(ltv.getD9() * 100d * 100d)/100d + "%", 
					ltv.getD10() == null ? "-" : Math.round(ltv.getD10() * 100d * 100d)/100d + "%",
					ltv.getD11() == null ? "-" : Math.round(ltv.getD11() * 100d * 100d)/100d + "%",
					ltv.getD12() == null ? "-" : Math.round(ltv.getD12() * 100d * 100d)/100d + "%",
					ltv.getD13() == null ? "-" : Math.round(ltv.getD13() * 100d * 100d)/100d + "%",
					ltv.getD14() == null ? "-" : Math.round(ltv.getD14() * 100d * 100d)/100d + "%",
					ltv.getD15() == null ? "-" : Math.round(ltv.getD15() * 100d * 100d)/100d + "%",
					ltv.getD16() == null ? "-" : Math.round(ltv.getD16() * 100d * 100d)/100d + "%",
					ltv.getD17() == null ? "-" : Math.round(ltv.getD17() * 100d * 100d)/100d + "%",
					ltv.getD18() == null ? "-" : Math.round(ltv.getD18() * 100d * 100d)/100d + "%",
					ltv.getD19() == null ? "-" : Math.round(ltv.getD19() * 100d * 100d)/100d + "%",
					ltv.getD20() == null ? "-" : Math.round(ltv.getD20() * 100d * 100d)/100d + "%",
					ltv.getD21() == null ? "-" : Math.round(ltv.getD21() * 100d * 100d)/100d + "%",
					ltv.getD22() == null ? "-" : Math.round(ltv.getD22() * 100d * 100d)/100d + "%",
					ltv.getD23() == null ? "-" : Math.round(ltv.getD23() * 100d * 100d)/100d + "%",
					ltv.getD24() == null ? "-" : Math.round(ltv.getD24() * 100d * 100d)/100d + "%",
					ltv.getD25() == null ? "-" : Math.round(ltv.getD25() * 100d * 100d)/100d + "%",
					ltv.getD26() == null ? "-" : Math.round(ltv.getD26() * 100d * 100d)/100d + "%",
					ltv.getD27() == null ? "-" : Math.round(ltv.getD27() * 100d * 100d)/100d + "%",
					ltv.getD28() == null ? "-" : Math.round(ltv.getD28() * 100d * 100d)/100d + "%",
					ltv.getD29() == null ? "-" : Math.round(ltv.getD29() * 100d * 100d)/100d + "%",
					ltv.getD30() == null ? "-" : Math.round(ltv.getD30() * 100d * 100d)/100d + "%",
					ltv.getD31() == null ? "-" : Math.round(ltv.getD31() * 100d * 100d)/100d + "%",
					ltv.getD32() == null ? "-" : Math.round(ltv.getD32() * 100d * 100d)/100d + "%",
					ltv.getD33() == null ? "-" : Math.round(ltv.getD33() * 100d * 100d)/100d + "%",
					ltv.getD34() == null ? "-" : Math.round(ltv.getD34() * 100d * 100d)/100d + "%",
					ltv.getD35() == null ? "-" : Math.round(ltv.getD35() * 100d * 100d)/100d + "%",
					ltv.getD36() == null ? "-" : Math.round(ltv.getD36() * 100d * 100d)/100d + "%",
					ltv.getD37() == null ? "-" : Math.round(ltv.getD37() * 100d * 100d)/100d + "%",
					ltv.getD38() == null ? "-" : Math.round(ltv.getD38() * 100d * 100d)/100d + "%",
					ltv.getD39() == null ? "-" : Math.round(ltv.getD39() * 100d * 100d)/100d + "%",
					ltv.getD40() == null ? "-" : Math.round(ltv.getD40() * 100d * 100d)/100d + "%",
					ltv.getD41() == null ? "-" : Math.round(ltv.getD41() * 100d * 100d)/100d + "%",
					ltv.getD42() == null ? "-" : Math.round(ltv.getD42() * 100d * 100d)/100d + "%",
					ltv.getD43() == null ? "-" : Math.round(ltv.getD43() * 100d * 100d)/100d + "%",
					ltv.getD44() == null ? "-" : Math.round(ltv.getD44() * 100d * 100d)/100d + "%",
					ltv.getD45() == null ? "-" : Math.round(ltv.getD45() * 100d * 100d)/100d + "%",
					ltv.getD46() == null ? "-" : Math.round(ltv.getD46() * 100d * 100d)/100d + "%",
					ltv.getD47() == null ? "-" : Math.round(ltv.getD47() * 100d * 100d)/100d + "%",
					ltv.getD48() == null ? "-" : Math.round(ltv.getD48() * 100d * 100d)/100d + "%",
					ltv.getD49() == null ? "-" : Math.round(ltv.getD49() * 100d * 100d)/100d + "%",
					ltv.getD50() == null ? "-" : Math.round(ltv.getD50() * 100d * 100d)/100d + "%",
					ltv.getD51() == null ? "-" : Math.round(ltv.getD51() * 100d * 100d)/100d + "%",
					ltv.getD52() == null ? "-" : Math.round(ltv.getD52() * 100d * 100d)/100d + "%",
					ltv.getD53() == null ? "-" : Math.round(ltv.getD53() * 100d * 100d)/100d + "%",
					ltv.getD54() == null ? "-" : Math.round(ltv.getD54() * 100d * 100d)/100d + "%",
					ltv.getD55() == null ? "-" : Math.round(ltv.getD55() * 100d * 100d)/100d + "%",
					ltv.getD56() == null ? "-" : Math.round(ltv.getD56() * 100d * 100d)/100d + "%",
					ltv.getD57() == null ? "-" : Math.round(ltv.getD57() * 100d * 100d)/100d + "%",
					ltv.getD58() == null ? "-" : Math.round(ltv.getD58() * 100d * 100d)/100d + "%",
					ltv.getD59() == null ? "-" : Math.round(ltv.getD59() * 100d * 100d)/100d + "%",
					ltv.getD60() == null ? "-" : Math.round(ltv.getD60() * 100d * 100d)/100d + "%",
					ltv.getD61() == null ? "-" : Math.round(ltv.getD61() * 100d * 100d)/100d + "%",
					ltv.getD62() == null ? "-" : Math.round(ltv.getD62() * 100d * 100d)/100d + "%",
					ltv.getD63() == null ? "-" : Math.round(ltv.getD63() * 100d * 100d)/100d + "%",
					ltv.getD64() == null ? "-" : Math.round(ltv.getD64() * 100d * 100d)/100d + "%",
					ltv.getD65() == null ? "-" : Math.round(ltv.getD65() * 100d * 100d)/100d + "%",
					ltv.getD66() == null ? "-" : Math.round(ltv.getD66() * 100d * 100d)/100d + "%",
					ltv.getD67() == null ? "-" : Math.round(ltv.getD67() * 100d * 100d)/100d + "%",
					ltv.getD68() == null ? "-" : Math.round(ltv.getD68() * 100d * 100d)/100d + "%",
					ltv.getD69() == null ? "-" : Math.round(ltv.getD69() * 100d * 100d)/100d + "%",
					ltv.getD70() == null ? "-" : Math.round(ltv.getD70() * 100d * 100d)/100d + "%",
					ltv.getD71() == null ? "-" : Math.round(ltv.getD71() * 100d * 100d)/100d + "%",
					ltv.getD72() == null ? "-" : Math.round(ltv.getD72() * 100d * 100d)/100d + "%",
					ltv.getD73() == null ? "-" : Math.round(ltv.getD73() * 100d * 100d)/100d + "%",
					ltv.getD74() == null ? "-" : Math.round(ltv.getD74() * 100d * 100d)/100d + "%",
					ltv.getD75() == null ? "-" : Math.round(ltv.getD75() * 100d * 100d)/100d + "%",
					ltv.getD76() == null ? "-" : Math.round(ltv.getD76() * 100d * 100d)/100d + "%",
					ltv.getD77() == null ? "-" : Math.round(ltv.getD77() * 100d * 100d)/100d + "%",
					ltv.getD78() == null ? "-" : Math.round(ltv.getD78() * 100d * 100d)/100d + "%",
					ltv.getD79() == null ? "-" : Math.round(ltv.getD79() * 100d * 100d)/100d + "%",
					ltv.getD80() == null ? "-" : Math.round(ltv.getD80() * 100d * 100d)/100d + "%",
					ltv.getD81() == null ? "-" : Math.round(ltv.getD81() * 100d * 100d)/100d + "%",
					ltv.getD82() == null ? "-" : Math.round(ltv.getD82() * 100d * 100d)/100d + "%",
					ltv.getD83() == null ? "-" : Math.round(ltv.getD83() * 100d * 100d)/100d + "%",
					ltv.getD84() == null ? "-" : Math.round(ltv.getD84() * 100d * 100d)/100d + "%",
					ltv.getD85() == null ? "-" : Math.round(ltv.getD85() * 100d * 100d)/100d + "%",
					ltv.getD86() == null ? "-" : Math.round(ltv.getD86() * 100d * 100d)/100d + "%",
					ltv.getD87() == null ? "-" : Math.round(ltv.getD87() * 100d * 100d)/100d + "%",
					ltv.getD88() == null ? "-" : Math.round(ltv.getD88() * 100d * 100d)/100d + "%",
					ltv.getD89() == null ? "-" : Math.round(ltv.getD89() * 100d * 100d)/100d + "%",
					ltv.getD90() == null ? "-" : Math.round(ltv.getD90() * 100d * 100d)/100d + "%" 
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	public void writeSourcePayLtv(GameplayerType queryType) throws UnsupportedEncodingException {
		String fileName = "source_pay_install.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道",
				queryType == GameplayerType.INSATLL ? "新注册" : "创角数",
						"D0("+games.getCurrency()+")","D1("+games.getCurrency()+")","D2("+games.getCurrency()+")",
						"D3("+games.getCurrency()+")","D4("+games.getCurrency()+")","D5("+games.getCurrency()+")",
						"D6("+games.getCurrency()+")","D7("+games.getCurrency()+")","D8("+games.getCurrency()+")",
						"D9("+games.getCurrency()+")","D10("+games.getCurrency()+")","D11("+games.getCurrency()+")",
						"D12("+games.getCurrency()+")","D13("+games.getCurrency()+")","D14("+games.getCurrency()+")",
						"D15("+games.getCurrency()+")","D16("+games.getCurrency()+")","D17("+games.getCurrency()+")",
						"D18("+games.getCurrency()+")","D19("+games.getCurrency()+")","D20("+games.getCurrency()+")",
						"D21("+games.getCurrency()+")","D22("+games.getCurrency()+")","D23("+games.getCurrency()+")",
						"D24("+games.getCurrency()+")","D25("+games.getCurrency()+")","D26("+games.getCurrency()+")",
						"D27("+games.getCurrency()+")","D28("+games.getCurrency()+")","D29("+games.getCurrency()+")",
						"D30("+games.getCurrency()+")","D31("+games.getCurrency()+")","D32("+games.getCurrency()+")",
						"D33("+games.getCurrency()+")","D34("+games.getCurrency()+")","D35("+games.getCurrency()+")",
						"D36("+games.getCurrency()+")","D37("+games.getCurrency()+")","D38("+games.getCurrency()+")",
						"D39("+games.getCurrency()+")","D40("+games.getCurrency()+")","D41("+games.getCurrency()+")",
						"D42("+games.getCurrency()+")","D43("+games.getCurrency()+")","D44("+games.getCurrency()+")",
						"D45("+games.getCurrency()+")","D46("+games.getCurrency()+")","D47("+games.getCurrency()+")",
						"D48("+games.getCurrency()+")","D49("+games.getCurrency()+")","D50("+games.getCurrency()+")",
						"D51("+games.getCurrency()+")","D52("+games.getCurrency()+")","D53("+games.getCurrency()+")",
						"D54("+games.getCurrency()+")","D55("+games.getCurrency()+")","D56("+games.getCurrency()+")",
						"D57("+games.getCurrency()+")","D58("+games.getCurrency()+")","D59("+games.getCurrency()+")",
						"D60("+games.getCurrency()+")","D61("+games.getCurrency()+")","D62("+games.getCurrency()+")",
						"D63("+games.getCurrency()+")","D64("+games.getCurrency()+")","D65("+games.getCurrency()+")",
						"D66("+games.getCurrency()+")","D67("+games.getCurrency()+")","D68("+games.getCurrency()+")",
						"D69("+games.getCurrency()+")","D70("+games.getCurrency()+")","D71("+games.getCurrency()+")",
						"D72("+games.getCurrency()+")","D73("+games.getCurrency()+")","D74("+games.getCurrency()+")",
						"D75("+games.getCurrency()+")","D76("+games.getCurrency()+")","D77("+games.getCurrency()+")",
						"D78("+games.getCurrency()+")","D79("+games.getCurrency()+")","D80("+games.getCurrency()+")",
						"D81("+games.getCurrency()+")","D82("+games.getCurrency()+")","D83("+games.getCurrency()+")",
						"D84("+games.getCurrency()+")","D85("+games.getCurrency()+")","D86("+games.getCurrency()+")",
						"D87("+games.getCurrency()+")","D88("+games.getCurrency()+")","D89("+games.getCurrency()+")",
						"D90("+games.getCurrency()+")"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (UserRetentionSourceLtvVO ltvVO : userRetentionSourceLtvs) {
			GameLtv ltv = ltvVO.getGameLtv();
			Row row=sheet.createRow(rowIdx++);
			if(ltv.getInstall() == null || ltv.getInstall() == 0){
				Object data[] = {ltv.getInstallDay(),
						ltv.getSourceOrClientName(),
						ltv.getInstall(),
						"-","-","-","-","-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-"};
				writeRow(row, data);
			}else{
				Object data[] = {
						ltv.getInstallDay(),
						ltv.getSourceOrClientName(),
						ltv.getInstall(),
						ltv.getD0() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD0()/games.getRate()) * 100d)/100d : Math.round((ltv.getD0()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD1() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD1()/games.getRate()) * 100d)/100d:Math.round((ltv.getD1()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD2() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD2()/games.getRate()) * 100d)/100d:Math.round((ltv.getD2()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD3() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD3()/games.getRate()) * 100d)/100d : Math.round((ltv.getD3()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD4() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD4()/games.getRate()) * 100d)/100d : Math.round((ltv.getD4()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD5() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD5()/games.getRate()) * 100d)/100d:Math.round((ltv.getD5()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD6() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD6()/games.getRate()) * 100d)/100d:Math.round((ltv.getD6()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD7() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD7()/games.getRate()) * 100d)/100d:Math.round((ltv.getD7()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD8() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD8()/games.getRate()) * 100d)/100d:Math.round((ltv.getD8()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD9() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD9()/games.getRate()) * 100d)/100d:Math.round((ltv.getD9()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD10() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD10()/games.getRate()) * 100d)/100d:Math.round((ltv.getD10()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD11() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD11()/games.getRate()) * 100d)/100d:Math.round((ltv.getD11()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD12() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD12()/games.getRate()) * 100d)/100d:Math.round((ltv.getD12()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD13() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD13()/games.getRate()) * 100d)/100d:Math.round((ltv.getD13()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD14() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD14()/games.getRate()) * 100d)/100d:Math.round((ltv.getD14()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD15() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD15()/games.getRate()) * 100d)/100d:Math.round((ltv.getD15()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD16() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD16()/games.getRate()) * 100d)/100d:Math.round((ltv.getD16()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD17() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD17()/games.getRate()) * 100d)/100d:Math.round((ltv.getD17()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD18() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD18()/games.getRate()) * 100d)/100d:Math.round((ltv.getD18()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD19() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD19()/games.getRate()) * 100d)/100d:Math.round((ltv.getD19()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD20() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD20()/games.getRate()) * 100d)/100d:Math.round((ltv.getD20()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD21() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD21()/games.getRate()) * 100d)/100d:Math.round((ltv.getD21()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD22() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD22()/games.getRate()) * 100d)/100d:Math.round((ltv.getD22()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD23() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD23()/games.getRate()) * 100d)/100d:Math.round((ltv.getD23()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD24() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD24()/games.getRate()) * 100d)/100d:Math.round((ltv.getD24()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD25() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD25()/games.getRate()) * 100d)/100d:Math.round((ltv.getD25()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD26() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD26()/games.getRate()) * 100d)/100d:Math.round((ltv.getD26()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD27() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD27()/games.getRate()) * 100d)/100d:Math.round((ltv.getD27()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD28() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD28()/games.getRate()) * 100d)/100d:Math.round((ltv.getD28()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD29() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD29()/games.getRate()) * 100d)/100d:Math.round((ltv.getD29()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD30() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD30()/games.getRate()) * 100d)/100d:Math.round((ltv.getD30()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD31() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD31()/games.getRate()) * 100d)/100d:Math.round((ltv.getD31()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD32() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD32()/games.getRate()) * 100d)/100d:Math.round((ltv.getD32()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD33() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD33()/games.getRate()) * 100d)/100d:Math.round((ltv.getD33()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD34() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD34()/games.getRate()) * 100d)/100d:Math.round((ltv.getD34()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD35() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD35()/games.getRate()) * 100d)/100d:Math.round((ltv.getD35()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD36() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD36()/games.getRate()) * 100d)/100d:Math.round((ltv.getD36()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD37() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD37()/games.getRate()) * 100d)/100d:Math.round((ltv.getD37()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD38() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD38()/games.getRate()) * 100d)/100d:Math.round((ltv.getD38()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD39() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD39()/games.getRate()) * 100d)/100d:Math.round((ltv.getD39()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD40() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD40()/games.getRate()) * 100d)/100d:Math.round((ltv.getD40()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD41() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD41()/games.getRate()) * 100d)/100d:Math.round((ltv.getD41()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD42() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD42()/games.getRate()) * 100d)/100d:Math.round((ltv.getD42()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD43() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD43()/games.getRate()) * 100d)/100d:Math.round((ltv.getD43()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD44() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD44()/games.getRate()) * 100d)/100d:Math.round((ltv.getD44()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD45() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD45()/games.getRate()) * 100d)/100d:Math.round((ltv.getD45()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD46() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD46()/games.getRate()) * 100d)/100d:Math.round((ltv.getD46()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD47() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD47()/games.getRate()) * 100d)/100d:Math.round((ltv.getD47()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD48() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD48()/games.getRate()) * 100d)/100d:Math.round((ltv.getD48()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD49() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD49()/games.getRate()) * 100d)/100d:Math.round((ltv.getD49()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD50() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD50()/games.getRate()) * 100d)/100d:Math.round((ltv.getD50()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD51() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD51()/games.getRate()) * 100d)/100d:Math.round((ltv.getD51()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD52() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD52()/games.getRate()) * 100d)/100d:Math.round((ltv.getD52()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD53() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD53()/games.getRate()) * 100d)/100d:Math.round((ltv.getD53()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD54() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD54()/games.getRate()) * 100d)/100d:Math.round((ltv.getD54()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD55() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD55()/games.getRate()) * 100d)/100d:Math.round((ltv.getD55()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD56() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD56()/games.getRate()) * 100d)/100d:Math.round((ltv.getD56()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD57() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD57()/games.getRate()) * 100d)/100d:Math.round((ltv.getD57()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD58() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD58()/games.getRate()) * 100d)/100d:Math.round((ltv.getD58()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD59() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD59()/games.getRate()) * 100d)/100d:Math.round((ltv.getD59()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD60() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD60()/games.getRate()) * 100d)/100d:Math.round((ltv.getD60()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD61() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD61()/games.getRate()) * 100d)/100d:Math.round((ltv.getD61()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD62() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD62()/games.getRate()) * 100d)/100d:Math.round((ltv.getD62()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD63() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD63()/games.getRate()) * 100d)/100d:Math.round((ltv.getD63()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD64() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD64()/games.getRate()) * 100d)/100d:Math.round((ltv.getD64()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD65() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD65()/games.getRate()) * 100d)/100d:Math.round((ltv.getD65()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD66() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD66()/games.getRate()) * 100d)/100d:Math.round((ltv.getD66()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD67() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD67()/games.getRate()) * 100d)/100d:Math.round((ltv.getD67()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD68() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD68()/games.getRate()) * 100d)/100d:Math.round((ltv.getD68()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD69() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD69()/games.getRate()) * 100d)/100d:Math.round((ltv.getD69()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD70() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD70()/games.getRate()) * 100d)/100d:Math.round((ltv.getD70()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD71() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD71()/games.getRate()) * 100d)/100d:Math.round((ltv.getD71()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD72() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD72()/games.getRate()) * 100d)/100d:Math.round((ltv.getD72()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD73() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD73()/games.getRate()) * 100d)/100d:Math.round((ltv.getD73()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD74() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD74()/games.getRate()) * 100d)/100d:Math.round((ltv.getD74()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD75() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD75()/games.getRate()) * 100d)/100d:Math.round((ltv.getD75()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD76() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD76()/games.getRate()) * 100d)/100d:Math.round((ltv.getD76()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD77() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD77()/games.getRate()) * 100d)/100d:Math.round((ltv.getD77()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD78() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD78()/games.getRate()) * 100d)/100d:Math.round((ltv.getD78()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD79() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD79()/games.getRate()) * 100d)/100d:Math.round((ltv.getD79()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD80() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD80()/games.getRate()) * 100d)/100d:Math.round((ltv.getD80()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD81() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD81()/games.getRate()) * 100d)/100d:Math.round((ltv.getD81()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD82() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD82()/games.getRate()) * 100d)/100d:Math.round((ltv.getD82()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD83() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD83()/games.getRate()) * 100d)/100d:Math.round((ltv.getD83()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD84() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD84()/games.getRate()) * 100d)/100d:Math.round((ltv.getD84()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD85() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD85()/games.getRate()) * 100d)/100d:Math.round((ltv.getD85()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD86() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD86()/games.getRate()) * 100d)/100d:Math.round((ltv.getD86()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD87() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD87()/games.getRate()) * 100d)/100d:Math.round((ltv.getD87()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD88() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD88()/games.getRate()) * 100d)/100d:Math.round((ltv.getD88()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD89() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD89()/games.getRate()) * 100d)/100d:Math.round((ltv.getD89()/ltv.getInstall()/games.getRate()) * 100d)/100d,
								ltv.getD90() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD90()/games.getRate()) * 100d)/100d:Math.round((ltv.getD90()/ltv.getInstall()/games.getRate()) * 100d)/100d
				};
				writeRow(row, data);
			};
		}
		flushAndClose();
	}
	
	/**
	 * 数据版  
	 * 金额导出
	 * @param queryType
	 * @throws UnsupportedEncodingException
	 */
	public void writeSourcePayLtv_Datamoney(GameplayerType queryType) throws UnsupportedEncodingException {
		String fileName = "source_pay_install.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道",
				queryType == GameplayerType.INSATLL ? "新注册" : "创角数",
						"D0("+games.getCurrency()+")","D1("+games.getCurrency()+")","D2("+games.getCurrency()+")",
						"D3("+games.getCurrency()+")","D4("+games.getCurrency()+")","D5("+games.getCurrency()+")",
						"D6("+games.getCurrency()+")","D7("+games.getCurrency()+")","D8("+games.getCurrency()+")",
						"D9("+games.getCurrency()+")","D10("+games.getCurrency()+")","D11("+games.getCurrency()+")",
						"D12("+games.getCurrency()+")","D13("+games.getCurrency()+")","D14("+games.getCurrency()+")",
						"D15("+games.getCurrency()+")","D16("+games.getCurrency()+")","D17("+games.getCurrency()+")",
						"D18("+games.getCurrency()+")","D19("+games.getCurrency()+")","D20("+games.getCurrency()+")",
						"D21("+games.getCurrency()+")","D22("+games.getCurrency()+")","D23("+games.getCurrency()+")",
						"D24("+games.getCurrency()+")","D25("+games.getCurrency()+")","D26("+games.getCurrency()+")",
						"D27("+games.getCurrency()+")","D28("+games.getCurrency()+")","D29("+games.getCurrency()+")",
						"D30("+games.getCurrency()+")","D31("+games.getCurrency()+")","D32("+games.getCurrency()+")",
						"D33("+games.getCurrency()+")","D34("+games.getCurrency()+")","D35("+games.getCurrency()+")",
						"D36("+games.getCurrency()+")","D37("+games.getCurrency()+")","D38("+games.getCurrency()+")",
						"D39("+games.getCurrency()+")","D40("+games.getCurrency()+")","D41("+games.getCurrency()+")",
						"D42("+games.getCurrency()+")","D43("+games.getCurrency()+")","D44("+games.getCurrency()+")",
						"D45("+games.getCurrency()+")","D46("+games.getCurrency()+")","D47("+games.getCurrency()+")",
						"D48("+games.getCurrency()+")","D49("+games.getCurrency()+")","D50("+games.getCurrency()+")",
						"D51("+games.getCurrency()+")","D52("+games.getCurrency()+")","D53("+games.getCurrency()+")",
						"D54("+games.getCurrency()+")","D55("+games.getCurrency()+")","D56("+games.getCurrency()+")",
						"D57("+games.getCurrency()+")","D58("+games.getCurrency()+")","D59("+games.getCurrency()+")",
						"D60("+games.getCurrency()+")","D61("+games.getCurrency()+")","D62("+games.getCurrency()+")",
						"D63("+games.getCurrency()+")","D64("+games.getCurrency()+")","D65("+games.getCurrency()+")",
						"D66("+games.getCurrency()+")","D67("+games.getCurrency()+")","D68("+games.getCurrency()+")",
						"D69("+games.getCurrency()+")","D70("+games.getCurrency()+")","D71("+games.getCurrency()+")",
						"D72("+games.getCurrency()+")","D73("+games.getCurrency()+")","D74("+games.getCurrency()+")",
						"D75("+games.getCurrency()+")","D76("+games.getCurrency()+")","D77("+games.getCurrency()+")",
						"D78("+games.getCurrency()+")","D79("+games.getCurrency()+")","D80("+games.getCurrency()+")",
						"D81("+games.getCurrency()+")","D82("+games.getCurrency()+")","D83("+games.getCurrency()+")",
						"D84("+games.getCurrency()+")","D85("+games.getCurrency()+")","D86("+games.getCurrency()+")",
						"D87("+games.getCurrency()+")","D88("+games.getCurrency()+")","D89("+games.getCurrency()+")",
						"D90("+games.getCurrency()+")"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (UserRetentionSourceLtvVO ltvVO : userRetentionSourceLtvs) {
			GameLtv ltv = ltvVO.getGameLtv();
			Row row=sheet.createRow(rowIdx++);
			if(ltv.getInstall() == null || ltv.getInstall() == 0){
				Object data[] = {ltv.getInstallDay(),
						ltv.getSourceOrClientName(),
						ltv.getInstall(),
						"-","-","-","-","-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-"};
				writeRow(row, data);
			}else{
				Object data[] = {
						ltv.getInstallDay(),
						ltv.getSourceOrClientName(),
						ltv.getInstall(),
						ltv.getD0() == null ? "-" : Math.round((ltv.getD0()/games.getRate()) * 100d)/100d,
						ltv.getD1() == null ? "-" : Math.round((ltv.getD1()/games.getRate()) * 100d)/100d,
						ltv.getD2() == null ? "-" : Math.round((ltv.getD2()/games.getRate()) * 100d)/100d,
						ltv.getD3() == null ? "-" : Math.round((ltv.getD3()/games.getRate()) * 100d)/100d,
						ltv.getD4() == null ? "-" : Math.round((ltv.getD4()/games.getRate()) * 100d)/100d,
						ltv.getD5() == null ? "-" : Math.round((ltv.getD5()/games.getRate()) * 100d)/100d,
						ltv.getD6() == null ? "-" : Math.round((ltv.getD6()/games.getRate()) * 100d)/100d,
						ltv.getD7() == null ? "-" : Math.round((ltv.getD7()/games.getRate()) * 100d)/100d,
						ltv.getD8() == null ? "-" : Math.round((ltv.getD8()/games.getRate()) * 100d)/100d,
						ltv.getD9() == null ? "-" : Math.round((ltv.getD9()/games.getRate()) * 100d)/100d,
						ltv.getD10() == null ? "-" : Math.round((ltv.getD10()/games.getRate()) * 100d)/100d,
						ltv.getD11() == null ? "-" : Math.round((ltv.getD11()/games.getRate()) * 100d)/100d,
						ltv.getD12() == null ? "-" : Math.round((ltv.getD12()/games.getRate()) * 100d)/100d,
						ltv.getD13() == null ? "-" : Math.round((ltv.getD13()/games.getRate()) * 100d)/100d,
						ltv.getD14() == null ? "-" : Math.round((ltv.getD14()/games.getRate()) * 100d)/100d,
						ltv.getD15() == null ? "-" : Math.round((ltv.getD15()/games.getRate()) * 100d)/100d,
						ltv.getD16() == null ? "-" : Math.round((ltv.getD16()/games.getRate()) * 100d)/100d,
						ltv.getD17() == null ? "-" : Math.round((ltv.getD17()/games.getRate()) * 100d)/100d,
						ltv.getD18() == null ? "-" : Math.round((ltv.getD18()/games.getRate()) * 100d)/100d,
						ltv.getD19() == null ? "-" : Math.round((ltv.getD19()/games.getRate()) * 100d)/100d,
						ltv.getD20() == null ? "-" : Math.round((ltv.getD20()/games.getRate()) * 100d)/100d,
						ltv.getD21() == null ? "-" : Math.round((ltv.getD21()/games.getRate()) * 100d)/100d,
						ltv.getD22() == null ? "-" : Math.round((ltv.getD22()/games.getRate()) * 100d)/100d,
						ltv.getD23() == null ? "-" : Math.round((ltv.getD23()/games.getRate()) * 100d)/100d,
						ltv.getD24() == null ? "-" : Math.round((ltv.getD24()/games.getRate()) * 100d)/100d,
						ltv.getD25() == null ? "-" : Math.round((ltv.getD25()/games.getRate()) * 100d)/100d,
						ltv.getD26() == null ? "-" : Math.round((ltv.getD26()/games.getRate()) * 100d)/100d,
						ltv.getD27() == null ? "-" : Math.round((ltv.getD27()/games.getRate()) * 100d)/100d,
						ltv.getD28() == null ? "-" : Math.round((ltv.getD28()/games.getRate()) * 100d)/100d,
						ltv.getD29() == null ? "-" : Math.round((ltv.getD29()/games.getRate()) * 100d)/100d,
						ltv.getD30() == null ? "-" : Math.round((ltv.getD30()/games.getRate()) * 100d)/100d,
						ltv.getD31() == null ? "-" : Math.round((ltv.getD31()/games.getRate()) * 100d)/100d,
						ltv.getD32() == null ? "-" : Math.round((ltv.getD32()/games.getRate()) * 100d)/100d,
						ltv.getD33() == null ? "-" : Math.round((ltv.getD33()/games.getRate()) * 100d)/100d,
						ltv.getD34() == null ? "-" : Math.round((ltv.getD34()/games.getRate()) * 100d)/100d,
						ltv.getD35() == null ? "-" : Math.round((ltv.getD35()/games.getRate()) * 100d)/100d,
						ltv.getD36() == null ? "-" : Math.round((ltv.getD36()/games.getRate()) * 100d)/100d,
						ltv.getD37() == null ? "-" : Math.round((ltv.getD37()/games.getRate()) * 100d)/100d,
						ltv.getD38() == null ? "-" : Math.round((ltv.getD38()/games.getRate()) * 100d)/100d,
						ltv.getD39() == null ? "-" : Math.round((ltv.getD39()/games.getRate()) * 100d)/100d,
						ltv.getD40() == null ? "-" : Math.round((ltv.getD40()/games.getRate()) * 100d)/100d,
						ltv.getD41() == null ? "-" : Math.round((ltv.getD41()/games.getRate()) * 100d)/100d,
						ltv.getD42() == null ? "-" : Math.round((ltv.getD42()/games.getRate()) * 100d)/100d,
						ltv.getD43() == null ? "-" : Math.round((ltv.getD43()/games.getRate()) * 100d)/100d,
						ltv.getD44() == null ? "-" : Math.round((ltv.getD44()/games.getRate()) * 100d)/100d,
						ltv.getD45() == null ? "-" : Math.round((ltv.getD45()/games.getRate()) * 100d)/100d,
						ltv.getD46() == null ? "-" : Math.round((ltv.getD46()/games.getRate()) * 100d)/100d,
						ltv.getD47() == null ? "-" : Math.round((ltv.getD47()/games.getRate()) * 100d)/100d,
						ltv.getD48() == null ? "-" : Math.round((ltv.getD48()/games.getRate()) * 100d)/100d,
						ltv.getD49() == null ? "-" : Math.round((ltv.getD49()/games.getRate()) * 100d)/100d,
						ltv.getD50() == null ? "-" : Math.round((ltv.getD50()/games.getRate()) * 100d)/100d,
						ltv.getD51() == null ? "-" : Math.round((ltv.getD51()/games.getRate()) * 100d)/100d,
						ltv.getD52() == null ? "-" : Math.round((ltv.getD52()/games.getRate()) * 100d)/100d,
						ltv.getD53() == null ? "-" : Math.round((ltv.getD53()/games.getRate()) * 100d)/100d,
						ltv.getD54() == null ? "-" : Math.round((ltv.getD54()/games.getRate()) * 100d)/100d,
						ltv.getD55() == null ? "-" : Math.round((ltv.getD55()/games.getRate()) * 100d)/100d,
						ltv.getD56() == null ? "-" : Math.round((ltv.getD56()/games.getRate()) * 100d)/100d,
						ltv.getD57() == null ? "-" : Math.round((ltv.getD57()/games.getRate()) * 100d)/100d,
						ltv.getD58() == null ? "-" : Math.round((ltv.getD58()/games.getRate()) * 100d)/100d,
						ltv.getD59() == null ? "-" : Math.round((ltv.getD59()/games.getRate()) * 100d)/100d,
						ltv.getD60() == null ? "-" : Math.round((ltv.getD60()/games.getRate()) * 100d)/100d,
						ltv.getD61() == null ? "-" : Math.round((ltv.getD61()/games.getRate()) * 100d)/100d,
						ltv.getD62() == null ? "-" : Math.round((ltv.getD62()/games.getRate()) * 100d)/100d,
						ltv.getD63() == null ? "-" : Math.round((ltv.getD63()/games.getRate()) * 100d)/100d,
						ltv.getD64() == null ? "-" : Math.round((ltv.getD64()/games.getRate()) * 100d)/100d,
						ltv.getD65() == null ? "-" : Math.round((ltv.getD65()/games.getRate()) * 100d)/100d,
						ltv.getD66() == null ? "-" : Math.round((ltv.getD66()/games.getRate()) * 100d)/100d,
						ltv.getD67() == null ? "-" : Math.round((ltv.getD67()/games.getRate()) * 100d)/100d,
						ltv.getD68() == null ? "-" : Math.round((ltv.getD68()/games.getRate()) * 100d)/100d,
						ltv.getD69() == null ? "-" : Math.round((ltv.getD69()/games.getRate()) * 100d)/100d,
						ltv.getD70() == null ? "-" : Math.round((ltv.getD70()/games.getRate()) * 100d)/100d,
						ltv.getD71() == null ? "-" : Math.round((ltv.getD71()/games.getRate()) * 100d)/100d,
						ltv.getD72() == null ? "-" : Math.round((ltv.getD72()/games.getRate()) * 100d)/100d,
						ltv.getD73() == null ? "-" : Math.round((ltv.getD73()/games.getRate()) * 100d)/100d,
						ltv.getD74() == null ? "-" : Math.round((ltv.getD74()/games.getRate()) * 100d)/100d,
						ltv.getD75() == null ? "-" : Math.round((ltv.getD75()/games.getRate()) * 100d)/100d,
						ltv.getD76() == null ? "-" : Math.round((ltv.getD76()/games.getRate()) * 100d)/100d,
						ltv.getD77() == null ? "-" : Math.round((ltv.getD77()/games.getRate()) * 100d)/100d,
						ltv.getD78() == null ? "-" : Math.round((ltv.getD78()/games.getRate()) * 100d)/100d,
						ltv.getD79() == null ? "-" : Math.round((ltv.getD79()/games.getRate()) * 100d)/100d,
						ltv.getD80() == null ? "-" : Math.round((ltv.getD80()/games.getRate()) * 100d)/100d,
						ltv.getD81() == null ? "-" : Math.round((ltv.getD81()/games.getRate()) * 100d)/100d,
						ltv.getD82() == null ? "-" : Math.round((ltv.getD82()/games.getRate()) * 100d)/100d,
						ltv.getD83() == null ? "-" : Math.round((ltv.getD83()/games.getRate()) * 100d)/100d,
						ltv.getD84() == null ? "-" : Math.round((ltv.getD84()/games.getRate()) * 100d)/100d,
						ltv.getD85() == null ? "-" : Math.round((ltv.getD85()/games.getRate()) * 100d)/100d,
						ltv.getD86() == null ? "-" : Math.round((ltv.getD86()/games.getRate()) * 100d)/100d,
						ltv.getD87() == null ? "-" : Math.round((ltv.getD87()/games.getRate()) * 100d)/100d,
						ltv.getD88() == null ? "-" : Math.round((ltv.getD88()/games.getRate()) * 100d)/100d,
						ltv.getD89() == null ? "-" : Math.round((ltv.getD89()/games.getRate()) * 100d)/100d,
						ltv.getD90() == null ? "-" : Math.round((ltv.getD90()/games.getRate()) * 100d)/100d
				};
				writeRow(row, data);
			};
		}
		flushAndClose();
	}
	
	//金额  渠道导出
	public void writeSourcePayLtv_money(GameplayerType queryType) throws UnsupportedEncodingException {
		String fileName = "source_pay_install.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道",
				queryType == GameplayerType.INSATLL ? "新注册" : "创角数",
						"D0("+games.getCurrency()+")","D1("+games.getCurrency()+")","D2("+games.getCurrency()+")",
						"D3("+games.getCurrency()+")","D4("+games.getCurrency()+")","D5("+games.getCurrency()+")",
						"D6("+games.getCurrency()+")","D7("+games.getCurrency()+")","D8("+games.getCurrency()+")",
						"D9("+games.getCurrency()+")","D10("+games.getCurrency()+")","D11("+games.getCurrency()+")",
						"D12("+games.getCurrency()+")","D13("+games.getCurrency()+")","D14("+games.getCurrency()+")",
						"D15("+games.getCurrency()+")","D16("+games.getCurrency()+")","D17("+games.getCurrency()+")",
						"D18("+games.getCurrency()+")","D19("+games.getCurrency()+")","D20("+games.getCurrency()+")",
						"D21("+games.getCurrency()+")","D22("+games.getCurrency()+")","D23("+games.getCurrency()+")",
						"D24("+games.getCurrency()+")","D25("+games.getCurrency()+")","D26("+games.getCurrency()+")",
						"D27("+games.getCurrency()+")","D28("+games.getCurrency()+")","D29("+games.getCurrency()+")",
						"D30("+games.getCurrency()+")","D31("+games.getCurrency()+")","D32("+games.getCurrency()+")",
						"D33("+games.getCurrency()+")","D34("+games.getCurrency()+")","D35("+games.getCurrency()+")",
						"D36("+games.getCurrency()+")","D37("+games.getCurrency()+")","D38("+games.getCurrency()+")",
						"D39("+games.getCurrency()+")","D40("+games.getCurrency()+")","D41("+games.getCurrency()+")",
						"D42("+games.getCurrency()+")","D43("+games.getCurrency()+")","D44("+games.getCurrency()+")",
						"D45("+games.getCurrency()+")","D46("+games.getCurrency()+")","D47("+games.getCurrency()+")",
						"D48("+games.getCurrency()+")","D49("+games.getCurrency()+")","D50("+games.getCurrency()+")",
						"D51("+games.getCurrency()+")","D52("+games.getCurrency()+")","D53("+games.getCurrency()+")",
						"D54("+games.getCurrency()+")","D55("+games.getCurrency()+")","D56("+games.getCurrency()+")",
						"D57("+games.getCurrency()+")","D58("+games.getCurrency()+")","D59("+games.getCurrency()+")",
						"D60("+games.getCurrency()+")","D61("+games.getCurrency()+")","D62("+games.getCurrency()+")",
						"D63("+games.getCurrency()+")","D64("+games.getCurrency()+")","D65("+games.getCurrency()+")",
						"D66("+games.getCurrency()+")","D67("+games.getCurrency()+")","D68("+games.getCurrency()+")",
						"D69("+games.getCurrency()+")","D70("+games.getCurrency()+")","D71("+games.getCurrency()+")",
						"D72("+games.getCurrency()+")","D73("+games.getCurrency()+")","D74("+games.getCurrency()+")",
						"D75("+games.getCurrency()+")","D76("+games.getCurrency()+")","D77("+games.getCurrency()+")",
						"D78("+games.getCurrency()+")","D79("+games.getCurrency()+")","D80("+games.getCurrency()+")",
						"D81("+games.getCurrency()+")","D82("+games.getCurrency()+")","D83("+games.getCurrency()+")",
						"D84("+games.getCurrency()+")","D85("+games.getCurrency()+")","D86("+games.getCurrency()+")",
						"D87("+games.getCurrency()+")","D88("+games.getCurrency()+")","D89("+games.getCurrency()+")",
						"D90("+games.getCurrency()+")"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (UserRetentionSourceLtvVO ltvVO : userRetentionSourceLtvs) {
			GameLtv ltv = ltvVO.getGameLtv();
			Row row=sheet.createRow(rowIdx++);
			if(ltv.getInstall() == null || ltv.getInstall() == 0){
				Object data[] = {ltv.getInstallDay(),
						ltv.getSourceOrClientName(),
						ltv.getInstall(),
						"-","-","-","-","-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-"};
				writeRow(row, data);
			}else{
				Object data[] = {
						ltv.getInstallDay(),
						ltv.getSourceOrClientName(),
						ltv.getInstall(),
						ltv.getD0() == null ? "-" : Math.round((ltv.getD0()/games.getRate()) * 100d)/100d,
						ltv.getD1() == null ? "-" : Math.round((ltv.getD1()/games.getRate()) * 100d)/100d,
						ltv.getD2() == null ? "-" : Math.round((ltv.getD2()/games.getRate()) * 100d)/100d,
						ltv.getD3() == null ? "-" : Math.round((ltv.getD3()/games.getRate()) * 100d)/100d,
						ltv.getD4() == null ? "-" : Math.round((ltv.getD4()/games.getRate()) * 100d)/100d,
						ltv.getD5() == null ? "-" : Math.round((ltv.getD5()/games.getRate()) * 100d)/100d,
						ltv.getD6() == null ? "-" : Math.round((ltv.getD6()/games.getRate()) * 100d)/100d,
						ltv.getD7() == null ? "-" : Math.round((ltv.getD7()/games.getRate()) * 100d)/100d,
						ltv.getD8() == null ? "-" : Math.round((ltv.getD8()/games.getRate()) * 100d)/100d,
						ltv.getD9() == null ? "-" : Math.round((ltv.getD9()/games.getRate()) * 100d)/100d,
						ltv.getD10() == null ? "-" : Math.round((ltv.getD10()/games.getRate()) * 100d)/100d,
						ltv.getD11() == null ? "-" : Math.round((ltv.getD11()/games.getRate()) * 100d)/100d,
						ltv.getD12() == null ? "-" : Math.round((ltv.getD12()/games.getRate()) * 100d)/100d,
						ltv.getD13() == null ? "-" : Math.round((ltv.getD13()/games.getRate()) * 100d)/100d,
						ltv.getD14() == null ? "-" : Math.round((ltv.getD14()/games.getRate()) * 100d)/100d,
						ltv.getD15() == null ? "-" : Math.round((ltv.getD15()/games.getRate()) * 100d)/100d,
						ltv.getD16() == null ? "-" : Math.round((ltv.getD16()/games.getRate()) * 100d)/100d,
						ltv.getD17() == null ? "-" : Math.round((ltv.getD17()/games.getRate()) * 100d)/100d,
						ltv.getD18() == null ? "-" : Math.round((ltv.getD18()/games.getRate()) * 100d)/100d,
						ltv.getD19() == null ? "-" : Math.round((ltv.getD19()/games.getRate()) * 100d)/100d,
						ltv.getD20() == null ? "-" : Math.round((ltv.getD20()/games.getRate()) * 100d)/100d,
						ltv.getD21() == null ? "-" : Math.round((ltv.getD21()/games.getRate()) * 100d)/100d,
						ltv.getD22() == null ? "-" : Math.round((ltv.getD22()/games.getRate()) * 100d)/100d,
						ltv.getD23() == null ? "-" : Math.round((ltv.getD23()/games.getRate()) * 100d)/100d,
						ltv.getD24() == null ? "-" : Math.round((ltv.getD24()/games.getRate()) * 100d)/100d,
						ltv.getD25() == null ? "-" : Math.round((ltv.getD25()/games.getRate()) * 100d)/100d,
						ltv.getD26() == null ? "-" : Math.round((ltv.getD26()/games.getRate()) * 100d)/100d,
						ltv.getD27() == null ? "-" : Math.round((ltv.getD27()/games.getRate()) * 100d)/100d,
						ltv.getD28() == null ? "-" : Math.round((ltv.getD28()/games.getRate()) * 100d)/100d,
						ltv.getD29() == null ? "-" : Math.round((ltv.getD29()/games.getRate()) * 100d)/100d,
						ltv.getD30() == null ? "-" : Math.round((ltv.getD30()/games.getRate()) * 100d)/100d,
						ltv.getD31() == null ? "-" : Math.round((ltv.getD31()/games.getRate()) * 100d)/100d,
						ltv.getD32() == null ? "-" : Math.round((ltv.getD32()/games.getRate()) * 100d)/100d,
						ltv.getD33() == null ? "-" : Math.round((ltv.getD33()/games.getRate()) * 100d)/100d,
						ltv.getD34() == null ? "-" : Math.round((ltv.getD34()/games.getRate()) * 100d)/100d,
						ltv.getD35() == null ? "-" : Math.round((ltv.getD35()/games.getRate()) * 100d)/100d,
						ltv.getD36() == null ? "-" : Math.round((ltv.getD36()/games.getRate()) * 100d)/100d,
						ltv.getD37() == null ? "-" : Math.round((ltv.getD37()/games.getRate()) * 100d)/100d,
						ltv.getD38() == null ? "-" : Math.round((ltv.getD38()/games.getRate()) * 100d)/100d,
						ltv.getD39() == null ? "-" : Math.round((ltv.getD39()/games.getRate()) * 100d)/100d,
						ltv.getD40() == null ? "-" : Math.round((ltv.getD40()/games.getRate()) * 100d)/100d,
						ltv.getD41() == null ? "-" : Math.round((ltv.getD41()/games.getRate()) * 100d)/100d,
						ltv.getD42() == null ? "-" : Math.round((ltv.getD42()/games.getRate()) * 100d)/100d,
						ltv.getD43() == null ? "-" : Math.round((ltv.getD43()/games.getRate()) * 100d)/100d,
						ltv.getD44() == null ? "-" : Math.round((ltv.getD44()/games.getRate()) * 100d)/100d,
						ltv.getD45() == null ? "-" : Math.round((ltv.getD45()/games.getRate()) * 100d)/100d,
						ltv.getD46() == null ? "-" : Math.round((ltv.getD46()/games.getRate()) * 100d)/100d,
						ltv.getD47() == null ? "-" : Math.round((ltv.getD47()/games.getRate()) * 100d)/100d,
						ltv.getD48() == null ? "-" : Math.round((ltv.getD48()/games.getRate()) * 100d)/100d,
						ltv.getD49() == null ? "-" : Math.round((ltv.getD49()/games.getRate()) * 100d)/100d,
						ltv.getD50() == null ? "-" : Math.round((ltv.getD50()/games.getRate()) * 100d)/100d,
						ltv.getD51() == null ? "-" : Math.round((ltv.getD51()/games.getRate()) * 100d)/100d,
						ltv.getD52() == null ? "-" : Math.round((ltv.getD52()/games.getRate()) * 100d)/100d,
						ltv.getD53() == null ? "-" : Math.round((ltv.getD53()/games.getRate()) * 100d)/100d,
						ltv.getD54() == null ? "-" : Math.round((ltv.getD54()/games.getRate()) * 100d)/100d,
						ltv.getD55() == null ? "-" : Math.round((ltv.getD55()/games.getRate()) * 100d)/100d,
						ltv.getD56() == null ? "-" : Math.round((ltv.getD56()/games.getRate()) * 100d)/100d,
						ltv.getD57() == null ? "-" : Math.round((ltv.getD57()/games.getRate()) * 100d)/100d,
						ltv.getD58() == null ? "-" : Math.round((ltv.getD58()/games.getRate()) * 100d)/100d,
						ltv.getD59() == null ? "-" : Math.round((ltv.getD59()/games.getRate()) * 100d)/100d,
						ltv.getD60() == null ? "-" : Math.round((ltv.getD60()/games.getRate()) * 100d)/100d,
						ltv.getD61() == null ? "-" : Math.round((ltv.getD61()/games.getRate()) * 100d)/100d,
						ltv.getD62() == null ? "-" : Math.round((ltv.getD62()/games.getRate()) * 100d)/100d,
						ltv.getD63() == null ? "-" : Math.round((ltv.getD63()/games.getRate()) * 100d)/100d,
						ltv.getD64() == null ? "-" : Math.round((ltv.getD64()/games.getRate()) * 100d)/100d,
						ltv.getD65() == null ? "-" : Math.round((ltv.getD65()/games.getRate()) * 100d)/100d,
						ltv.getD66() == null ? "-" : Math.round((ltv.getD66()/games.getRate()) * 100d)/100d,
						ltv.getD67() == null ? "-" : Math.round((ltv.getD67()/games.getRate()) * 100d)/100d,
						ltv.getD68() == null ? "-" : Math.round((ltv.getD68()/games.getRate()) * 100d)/100d,
						ltv.getD69() == null ? "-" : Math.round((ltv.getD69()/games.getRate()) * 100d)/100d,
						ltv.getD70() == null ? "-" : Math.round((ltv.getD70()/games.getRate()) * 100d)/100d,
						ltv.getD71() == null ? "-" : Math.round((ltv.getD71()/games.getRate()) * 100d)/100d,
						ltv.getD72() == null ? "-" : Math.round((ltv.getD72()/games.getRate()) * 100d)/100d,
						ltv.getD73() == null ? "-" : Math.round((ltv.getD73()/games.getRate()) * 100d)/100d,
						ltv.getD74() == null ? "-" : Math.round((ltv.getD74()/games.getRate()) * 100d)/100d,
						ltv.getD75() == null ? "-" : Math.round((ltv.getD75()/games.getRate()) * 100d)/100d,
						ltv.getD76() == null ? "-" : Math.round((ltv.getD76()/games.getRate()) * 100d)/100d,
						ltv.getD77() == null ? "-" : Math.round((ltv.getD77()/games.getRate()) * 100d)/100d,
						ltv.getD78() == null ? "-" : Math.round((ltv.getD78()/games.getRate()) * 100d)/100d,
						ltv.getD79() == null ? "-" : Math.round((ltv.getD79()/games.getRate()) * 100d)/100d,
						ltv.getD80() == null ? "-" : Math.round((ltv.getD80()/games.getRate()) * 100d)/100d,
						ltv.getD81() == null ? "-" : Math.round((ltv.getD81()/games.getRate()) * 100d)/100d,
						ltv.getD82() == null ? "-" : Math.round((ltv.getD82()/games.getRate()) * 100d)/100d,
						ltv.getD83() == null ? "-" : Math.round((ltv.getD83()/games.getRate()) * 100d)/100d,
						ltv.getD84() == null ? "-" : Math.round((ltv.getD84()/games.getRate()) * 100d)/100d,
						ltv.getD85() == null ? "-" : Math.round((ltv.getD85()/games.getRate()) * 100d)/100d,
						ltv.getD86() == null ? "-" : Math.round((ltv.getD86()/games.getRate()) * 100d)/100d,
						ltv.getD87() == null ? "-" : Math.round((ltv.getD87()/games.getRate()) * 100d)/100d,
						ltv.getD88() == null ? "-" : Math.round((ltv.getD88()/games.getRate()) * 100d)/100d,
						ltv.getD89() == null ? "-" : Math.round((ltv.getD89()/games.getRate()) * 100d)/100d,
						ltv.getD90() == null ? "-" : Math.round((ltv.getD90()/games.getRate()) * 100d)/100d
				};
				writeRow(row, data);
			};
		}
		flushAndClose();
	}
	
	public void writeClientPayLtv() throws UnsupportedEncodingException {
		String fileName = "client_pay_install.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道","新注册",
				"D0("+games.getCurrency()+")","D1("+games.getCurrency()+")","D2("+games.getCurrency()+")",
				"D3("+games.getCurrency()+")","D4("+games.getCurrency()+")","D5("+games.getCurrency()+")",
				"D6("+games.getCurrency()+")","D7("+games.getCurrency()+")","D8("+games.getCurrency()+")",
				"D9("+games.getCurrency()+")","D10("+games.getCurrency()+")","D11("+games.getCurrency()+")",
				"D12("+games.getCurrency()+")","D13("+games.getCurrency()+")","D14("+games.getCurrency()+")",
				"D15("+games.getCurrency()+")","D16("+games.getCurrency()+")","D17("+games.getCurrency()+")",
				"D18("+games.getCurrency()+")","D19("+games.getCurrency()+")","D20("+games.getCurrency()+")",
				"D21("+games.getCurrency()+")","D22("+games.getCurrency()+")","D23("+games.getCurrency()+")",
				"D24("+games.getCurrency()+")","D25("+games.getCurrency()+")","D26("+games.getCurrency()+")",
				"D27("+games.getCurrency()+")","D28("+games.getCurrency()+")","D29("+games.getCurrency()+")",
				"D30("+games.getCurrency()+")","D31("+games.getCurrency()+")","D32("+games.getCurrency()+")",
				"D33("+games.getCurrency()+")","D34("+games.getCurrency()+")","D35("+games.getCurrency()+")",
				"D36("+games.getCurrency()+")","D37("+games.getCurrency()+")","D38("+games.getCurrency()+")",
				"D39("+games.getCurrency()+")","D40("+games.getCurrency()+")","D41("+games.getCurrency()+")",
				"D42("+games.getCurrency()+")","D43("+games.getCurrency()+")","D44("+games.getCurrency()+")",
				"D45("+games.getCurrency()+")","D46("+games.getCurrency()+")","D47("+games.getCurrency()+")",
				"D48("+games.getCurrency()+")","D49("+games.getCurrency()+")","D50("+games.getCurrency()+")",
				"D51("+games.getCurrency()+")","D52("+games.getCurrency()+")","D53("+games.getCurrency()+")",
				"D54("+games.getCurrency()+")","D55("+games.getCurrency()+")","D56("+games.getCurrency()+")",
				"D57("+games.getCurrency()+")","D58("+games.getCurrency()+")","D59("+games.getCurrency()+")",
				"D60("+games.getCurrency()+")","D61("+games.getCurrency()+")","D62("+games.getCurrency()+")",
				"D63("+games.getCurrency()+")","D64("+games.getCurrency()+")","D65("+games.getCurrency()+")",
				"D66("+games.getCurrency()+")","D67("+games.getCurrency()+")","D68("+games.getCurrency()+")",
				"D69("+games.getCurrency()+")","D70("+games.getCurrency()+")","D71("+games.getCurrency()+")",
				"D72("+games.getCurrency()+")","D73("+games.getCurrency()+")","D74("+games.getCurrency()+")",
				"D75("+games.getCurrency()+")","D76("+games.getCurrency()+")","D77("+games.getCurrency()+")",
				"D78("+games.getCurrency()+")","D79("+games.getCurrency()+")","D80("+games.getCurrency()+")",
				"D81("+games.getCurrency()+")","D82("+games.getCurrency()+")","D83("+games.getCurrency()+")",
				"D84("+games.getCurrency()+")","D85("+games.getCurrency()+")","D86("+games.getCurrency()+")",
				"D87("+games.getCurrency()+")","D88("+games.getCurrency()+")","D89("+games.getCurrency()+")",
				"D90("+games.getCurrency()+")"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (UserRetentionClientLtvVO ltvVO : userRetentionClientLtvs) {
			GameLtv ltv = ltvVO.getGameLtv();
			Row row=sheet.createRow(rowIdx++);
			if(ltv.getInstall() == null || ltv.getInstall() == 0){
				Object data[] = {ltv.getInstallDay(),
						ltv.getSourceOrClientName(),
						ltv.getInstall(),
						"-","-","-","-","-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-"};
				writeRow(row, data);
			}else{
				Object data[] = {
						ltv.getInstallDay(),
						ltv.getSourceOrClientName(),
						ltv.getInstall(),
						ltv.getD0() == null ? "-" : Math.round((ltv.getD0()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD1() == null ? "-" : Math.round((ltv.getD1()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD2() == null ? "-" : Math.round((ltv.getD2()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD3() == null ? "-" : Math.round((ltv.getD3()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD4() == null ? "-" : Math.round((ltv.getD4()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD5() == null ? "-" : Math.round((ltv.getD5()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD6() == null ? "-" : Math.round((ltv.getD6()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD7() == null ? "-" : Math.round((ltv.getD7()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD8() == null ? "-" : Math.round((ltv.getD8()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD9() == null ? "-" : Math.round((ltv.getD9()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD10() == null ? "-" : Math.round((ltv.getD10()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD11() == null ? "-" : Math.round((ltv.getD11()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD12() == null ? "-" : Math.round((ltv.getD12()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD13() == null ? "-" : Math.round((ltv.getD13()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD14() == null ? "-" : Math.round((ltv.getD14()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD15() == null ? "-" : Math.round((ltv.getD15()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD16() == null ? "-" : Math.round((ltv.getD16()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD17() == null ? "-" : Math.round((ltv.getD17()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD18() == null ? "-" : Math.round((ltv.getD18()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD19() == null ? "-" : Math.round((ltv.getD19()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD20() == null ? "-" : Math.round((ltv.getD20()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD21() == null ? "-" : Math.round((ltv.getD21()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD22() == null ? "-" : Math.round((ltv.getD22()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD23() == null ? "-" : Math.round((ltv.getD23()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD24() == null ? "-" : Math.round((ltv.getD24()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD25() == null ? "-" : Math.round((ltv.getD25()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD26() == null ? "-" : Math.round((ltv.getD26()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD27() == null ? "-" : Math.round((ltv.getD27()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD28() == null ? "-" : Math.round((ltv.getD28()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD29() == null ? "-" : Math.round((ltv.getD29()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD30() == null ? "-" : Math.round((ltv.getD30()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD31() == null ? "-" : Math.round((ltv.getD31()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD32() == null ? "-" : Math.round((ltv.getD32()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD33() == null ? "-" : Math.round((ltv.getD33()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD34() == null ? "-" : Math.round((ltv.getD34()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD35() == null ? "-" : Math.round((ltv.getD35()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD36() == null ? "-" : Math.round((ltv.getD36()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD37() == null ? "-" : Math.round((ltv.getD37()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD38() == null ? "-" : Math.round((ltv.getD38()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD39() == null ? "-" : Math.round((ltv.getD39()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD40() == null ? "-" : Math.round((ltv.getD40()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD41() == null ? "-" : Math.round((ltv.getD41()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD42() == null ? "-" : Math.round((ltv.getD42()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD43() == null ? "-" : Math.round((ltv.getD43()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD44() == null ? "-" : Math.round((ltv.getD44()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD45() == null ? "-" : Math.round((ltv.getD45()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD46() == null ? "-" : Math.round((ltv.getD46()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD47() == null ? "-" : Math.round((ltv.getD47()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD48() == null ? "-" : Math.round((ltv.getD48()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD49() == null ? "-" : Math.round((ltv.getD49()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD50() == null ? "-" : Math.round((ltv.getD50()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD51() == null ? "-" : Math.round((ltv.getD51()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD52() == null ? "-" : Math.round((ltv.getD52()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD53() == null ? "-" : Math.round((ltv.getD53()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD54() == null ? "-" : Math.round((ltv.getD54()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD55() == null ? "-" : Math.round((ltv.getD55()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD56() == null ? "-" : Math.round((ltv.getD56()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD57() == null ? "-" : Math.round((ltv.getD57()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD58() == null ? "-" : Math.round((ltv.getD58()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD59() == null ? "-" : Math.round((ltv.getD59()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD60() == null ? "-" : Math.round((ltv.getD60()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD61() == null ? "-" : Math.round((ltv.getD61()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD62() == null ? "-" : Math.round((ltv.getD62()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD63() == null ? "-" : Math.round((ltv.getD63()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD64() == null ? "-" : Math.round((ltv.getD64()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD65() == null ? "-" : Math.round((ltv.getD65()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD66() == null ? "-" : Math.round((ltv.getD66()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD67() == null ? "-" : Math.round((ltv.getD67()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD68() == null ? "-" : Math.round((ltv.getD68()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD69() == null ? "-" : Math.round((ltv.getD69()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD70() == null ? "-" : Math.round((ltv.getD70()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD71() == null ? "-" : Math.round((ltv.getD71()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD72() == null ? "-" : Math.round((ltv.getD72()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD73() == null ? "-" : Math.round((ltv.getD73()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD74() == null ? "-" : Math.round((ltv.getD74()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD75() == null ? "-" : Math.round((ltv.getD75()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD76() == null ? "-" : Math.round((ltv.getD76()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD77() == null ? "-" : Math.round((ltv.getD77()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD78() == null ? "-" : Math.round((ltv.getD78()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD79() == null ? "-" : Math.round((ltv.getD79()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD80() == null ? "-" : Math.round((ltv.getD80()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD81() == null ? "-" : Math.round((ltv.getD81()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD82() == null ? "-" : Math.round((ltv.getD82()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD83() == null ? "-" : Math.round((ltv.getD83()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD84() == null ? "-" : Math.round((ltv.getD84()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD85() == null ? "-" : Math.round((ltv.getD85()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD86() == null ? "-" : Math.round((ltv.getD86()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD87() == null ? "-" : Math.round((ltv.getD87()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD88() == null ? "-" : Math.round((ltv.getD88()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD89() == null ? "-" : Math.round((ltv.getD89()/ltv.getInstall()/games.getRate()) * 100d)/100d,
						ltv.getD90() == null ? "-" : Math.round((ltv.getD90()/ltv.getInstall()/games.getRate()) * 100d)/100d
				};
				writeRow(row, data);
			}
		}
		flushAndClose();
	}
	/**
	 * 数据版   金额  分服   导出
	 * @throws UnsupportedEncodingException
	 */
	public void writeClientPayLtv_Datamoney() throws UnsupportedEncodingException {
		String fileName = "client_pay_install.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道","新注册",
				"D0("+games.getCurrency()+")","D1("+games.getCurrency()+")","D2("+games.getCurrency()+")",
				"D3("+games.getCurrency()+")","D4("+games.getCurrency()+")","D5("+games.getCurrency()+")",
				"D6("+games.getCurrency()+")","D7("+games.getCurrency()+")","D8("+games.getCurrency()+")",
				"D9("+games.getCurrency()+")","D10("+games.getCurrency()+")","D11("+games.getCurrency()+")",
				"D12("+games.getCurrency()+")","D13("+games.getCurrency()+")","D14("+games.getCurrency()+")",
				"D15("+games.getCurrency()+")","D16("+games.getCurrency()+")","D17("+games.getCurrency()+")",
				"D18("+games.getCurrency()+")","D19("+games.getCurrency()+")","D20("+games.getCurrency()+")",
				"D21("+games.getCurrency()+")","D22("+games.getCurrency()+")","D23("+games.getCurrency()+")",
				"D24("+games.getCurrency()+")","D25("+games.getCurrency()+")","D26("+games.getCurrency()+")",
				"D27("+games.getCurrency()+")","D28("+games.getCurrency()+")","D29("+games.getCurrency()+")",
				"D30("+games.getCurrency()+")","D31("+games.getCurrency()+")","D32("+games.getCurrency()+")",
				"D33("+games.getCurrency()+")","D34("+games.getCurrency()+")","D35("+games.getCurrency()+")",
				"D36("+games.getCurrency()+")","D37("+games.getCurrency()+")","D38("+games.getCurrency()+")",
				"D39("+games.getCurrency()+")","D40("+games.getCurrency()+")","D41("+games.getCurrency()+")",
				"D42("+games.getCurrency()+")","D43("+games.getCurrency()+")","D44("+games.getCurrency()+")",
				"D45("+games.getCurrency()+")","D46("+games.getCurrency()+")","D47("+games.getCurrency()+")",
				"D48("+games.getCurrency()+")","D49("+games.getCurrency()+")","D50("+games.getCurrency()+")",
				"D51("+games.getCurrency()+")","D52("+games.getCurrency()+")","D53("+games.getCurrency()+")",
				"D54("+games.getCurrency()+")","D55("+games.getCurrency()+")","D56("+games.getCurrency()+")",
				"D57("+games.getCurrency()+")","D58("+games.getCurrency()+")","D59("+games.getCurrency()+")",
				"D60("+games.getCurrency()+")","D61("+games.getCurrency()+")","D62("+games.getCurrency()+")",
				"D63("+games.getCurrency()+")","D64("+games.getCurrency()+")","D65("+games.getCurrency()+")",
				"D66("+games.getCurrency()+")","D67("+games.getCurrency()+")","D68("+games.getCurrency()+")",
				"D69("+games.getCurrency()+")","D70("+games.getCurrency()+")","D71("+games.getCurrency()+")",
				"D72("+games.getCurrency()+")","D73("+games.getCurrency()+")","D74("+games.getCurrency()+")",
				"D75("+games.getCurrency()+")","D76("+games.getCurrency()+")","D77("+games.getCurrency()+")",
				"D78("+games.getCurrency()+")","D79("+games.getCurrency()+")","D80("+games.getCurrency()+")",
				"D81("+games.getCurrency()+")","D82("+games.getCurrency()+")","D83("+games.getCurrency()+")",
				"D84("+games.getCurrency()+")","D85("+games.getCurrency()+")","D86("+games.getCurrency()+")",
				"D87("+games.getCurrency()+")","D88("+games.getCurrency()+")","D89("+games.getCurrency()+")",
				"D90("+games.getCurrency()+")"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (UserRetentionClientLtvVO ltvVO : userRetentionClientLtvs) {
			GameLtv ltv = ltvVO.getGameLtv();
			Row row=sheet.createRow(rowIdx++);
			if(ltv.getInstall() == null || ltv.getInstall() == 0){
				Object data[] = {ltv.getInstallDay(),
						ltv.getSourceOrClientName(),
						ltv.getInstall(),
						"-","-","-","-","-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-"};
				writeRow(row, data);
			}else{
				Object data[] = {
						ltv.getInstallDay(),
						ltv.getSourceOrClientName(),
						ltv.getInstall(),
						ltv.getD0() == null ? "-" : Math.round((ltv.getD0()/games.getRate()) * 100d)/100d,
						ltv.getD1() == null ? "-" : Math.round((ltv.getD1()/games.getRate()) * 100d)/100d,
						ltv.getD2() == null ? "-" : Math.round((ltv.getD2()/games.getRate()) * 100d)/100d,
						ltv.getD3() == null ? "-" : Math.round((ltv.getD3()/games.getRate()) * 100d)/100d,
						ltv.getD4() == null ? "-" : Math.round((ltv.getD4()/games.getRate()) * 100d)/100d,
						ltv.getD5() == null ? "-" : Math.round((ltv.getD5()/games.getRate()) * 100d)/100d,
						ltv.getD6() == null ? "-" : Math.round((ltv.getD6()/games.getRate()) * 100d)/100d,
						ltv.getD7() == null ? "-" : Math.round((ltv.getD7()/games.getRate()) * 100d)/100d,
						ltv.getD8() == null ? "-" : Math.round((ltv.getD8()/games.getRate()) * 100d)/100d,
						ltv.getD9() == null ? "-" : Math.round((ltv.getD9()/games.getRate()) * 100d)/100d,
						ltv.getD10() == null ? "-" : Math.round((ltv.getD10()/games.getRate()) * 100d)/100d,
						ltv.getD11() == null ? "-" : Math.round((ltv.getD11()/games.getRate()) * 100d)/100d,
						ltv.getD12() == null ? "-" : Math.round((ltv.getD12()/games.getRate()) * 100d)/100d,
						ltv.getD13() == null ? "-" : Math.round((ltv.getD13()/games.getRate()) * 100d)/100d,
						ltv.getD14() == null ? "-" : Math.round((ltv.getD14()/games.getRate()) * 100d)/100d,
						ltv.getD15() == null ? "-" : Math.round((ltv.getD15()/games.getRate()) * 100d)/100d,
						ltv.getD16() == null ? "-" : Math.round((ltv.getD16()/games.getRate()) * 100d)/100d,
						ltv.getD17() == null ? "-" : Math.round((ltv.getD17()/games.getRate()) * 100d)/100d,
						ltv.getD18() == null ? "-" : Math.round((ltv.getD18()/games.getRate()) * 100d)/100d,
						ltv.getD19() == null ? "-" : Math.round((ltv.getD19()/games.getRate()) * 100d)/100d,
						ltv.getD20() == null ? "-" : Math.round((ltv.getD20()/games.getRate()) * 100d)/100d,
						ltv.getD21() == null ? "-" : Math.round((ltv.getD21()/games.getRate()) * 100d)/100d,
						ltv.getD22() == null ? "-" : Math.round((ltv.getD22()/games.getRate()) * 100d)/100d,
						ltv.getD23() == null ? "-" : Math.round((ltv.getD23()/games.getRate()) * 100d)/100d,
						ltv.getD24() == null ? "-" : Math.round((ltv.getD24()/games.getRate()) * 100d)/100d,
						ltv.getD25() == null ? "-" : Math.round((ltv.getD25()/games.getRate()) * 100d)/100d,
						ltv.getD26() == null ? "-" : Math.round((ltv.getD26()/games.getRate()) * 100d)/100d,
						ltv.getD27() == null ? "-" : Math.round((ltv.getD27()/games.getRate()) * 100d)/100d,
						ltv.getD28() == null ? "-" : Math.round((ltv.getD28()/games.getRate()) * 100d)/100d,
						ltv.getD29() == null ? "-" : Math.round((ltv.getD29()/games.getRate()) * 100d)/100d,
						ltv.getD30() == null ? "-" : Math.round((ltv.getD30()/games.getRate()) * 100d)/100d,
						ltv.getD31() == null ? "-" : Math.round((ltv.getD31()/games.getRate()) * 100d)/100d,
						ltv.getD32() == null ? "-" : Math.round((ltv.getD32()/games.getRate()) * 100d)/100d,
						ltv.getD33() == null ? "-" : Math.round((ltv.getD33()/games.getRate()) * 100d)/100d,
						ltv.getD34() == null ? "-" : Math.round((ltv.getD34()/games.getRate()) * 100d)/100d,
						ltv.getD35() == null ? "-" : Math.round((ltv.getD35()/games.getRate()) * 100d)/100d,
						ltv.getD36() == null ? "-" : Math.round((ltv.getD36()/games.getRate()) * 100d)/100d,
						ltv.getD37() == null ? "-" : Math.round((ltv.getD37()/games.getRate()) * 100d)/100d,
						ltv.getD38() == null ? "-" : Math.round((ltv.getD38()/games.getRate()) * 100d)/100d,
						ltv.getD39() == null ? "-" : Math.round((ltv.getD39()/games.getRate()) * 100d)/100d,
						ltv.getD40() == null ? "-" : Math.round((ltv.getD40()/games.getRate()) * 100d)/100d,
						ltv.getD41() == null ? "-" : Math.round((ltv.getD41()/games.getRate()) * 100d)/100d,
						ltv.getD42() == null ? "-" : Math.round((ltv.getD42()/games.getRate()) * 100d)/100d,
						ltv.getD43() == null ? "-" : Math.round((ltv.getD43()/games.getRate()) * 100d)/100d,
						ltv.getD44() == null ? "-" : Math.round((ltv.getD44()/games.getRate()) * 100d)/100d,
						ltv.getD45() == null ? "-" : Math.round((ltv.getD45()/games.getRate()) * 100d)/100d,
						ltv.getD46() == null ? "-" : Math.round((ltv.getD46()/games.getRate()) * 100d)/100d,
						ltv.getD47() == null ? "-" : Math.round((ltv.getD47()/games.getRate()) * 100d)/100d,
						ltv.getD48() == null ? "-" : Math.round((ltv.getD48()/games.getRate()) * 100d)/100d,
						ltv.getD49() == null ? "-" : Math.round((ltv.getD49()/games.getRate()) * 100d)/100d,
						ltv.getD50() == null ? "-" : Math.round((ltv.getD50()/games.getRate()) * 100d)/100d,
						ltv.getD51() == null ? "-" : Math.round((ltv.getD51()/games.getRate()) * 100d)/100d,
						ltv.getD52() == null ? "-" : Math.round((ltv.getD52()/games.getRate()) * 100d)/100d,
						ltv.getD53() == null ? "-" : Math.round((ltv.getD53()/games.getRate()) * 100d)/100d,
						ltv.getD54() == null ? "-" : Math.round((ltv.getD54()/games.getRate()) * 100d)/100d,
						ltv.getD55() == null ? "-" : Math.round((ltv.getD55()/games.getRate()) * 100d)/100d,
						ltv.getD56() == null ? "-" : Math.round((ltv.getD56()/games.getRate()) * 100d)/100d,
						ltv.getD57() == null ? "-" : Math.round((ltv.getD57()/games.getRate()) * 100d)/100d,
						ltv.getD58() == null ? "-" : Math.round((ltv.getD58()/games.getRate()) * 100d)/100d,
						ltv.getD59() == null ? "-" : Math.round((ltv.getD59()/games.getRate()) * 100d)/100d,
						ltv.getD60() == null ? "-" : Math.round((ltv.getD60()/games.getRate()) * 100d)/100d,
						ltv.getD61() == null ? "-" : Math.round((ltv.getD61()/games.getRate()) * 100d)/100d,
						ltv.getD62() == null ? "-" : Math.round((ltv.getD62()/games.getRate()) * 100d)/100d,
						ltv.getD63() == null ? "-" : Math.round((ltv.getD63()/games.getRate()) * 100d)/100d,
						ltv.getD64() == null ? "-" : Math.round((ltv.getD64()/games.getRate()) * 100d)/100d,
						ltv.getD65() == null ? "-" : Math.round((ltv.getD65()/games.getRate()) * 100d)/100d,
						ltv.getD66() == null ? "-" : Math.round((ltv.getD66()/games.getRate()) * 100d)/100d,
						ltv.getD67() == null ? "-" : Math.round((ltv.getD67()/games.getRate()) * 100d)/100d,
						ltv.getD68() == null ? "-" : Math.round((ltv.getD68()/games.getRate()) * 100d)/100d,
						ltv.getD69() == null ? "-" : Math.round((ltv.getD69()/games.getRate()) * 100d)/100d,
						ltv.getD70() == null ? "-" : Math.round((ltv.getD70()/games.getRate()) * 100d)/100d,
						ltv.getD71() == null ? "-" : Math.round((ltv.getD71()/games.getRate()) * 100d)/100d,
						ltv.getD72() == null ? "-" : Math.round((ltv.getD72()/games.getRate()) * 100d)/100d,
						ltv.getD73() == null ? "-" : Math.round((ltv.getD73()/games.getRate()) * 100d)/100d,
						ltv.getD74() == null ? "-" : Math.round((ltv.getD74()/games.getRate()) * 100d)/100d,
						ltv.getD75() == null ? "-" : Math.round((ltv.getD75()/games.getRate()) * 100d)/100d,
						ltv.getD76() == null ? "-" : Math.round((ltv.getD76()/games.getRate()) * 100d)/100d,
						ltv.getD77() == null ? "-" : Math.round((ltv.getD77()/games.getRate()) * 100d)/100d,
						ltv.getD78() == null ? "-" : Math.round((ltv.getD78()/games.getRate()) * 100d)/100d,
						ltv.getD79() == null ? "-" : Math.round((ltv.getD79()/games.getRate()) * 100d)/100d,
						ltv.getD80() == null ? "-" : Math.round((ltv.getD80()/games.getRate()) * 100d)/100d,
						ltv.getD81() == null ? "-" : Math.round((ltv.getD81()/games.getRate()) * 100d)/100d,
						ltv.getD82() == null ? "-" : Math.round((ltv.getD82()/games.getRate()) * 100d)/100d,
						ltv.getD83() == null ? "-" : Math.round((ltv.getD83()/games.getRate()) * 100d)/100d,
						ltv.getD84() == null ? "-" : Math.round((ltv.getD84()/games.getRate()) * 100d)/100d,
						ltv.getD85() == null ? "-" : Math.round((ltv.getD85()/games.getRate()) * 100d)/100d,
						ltv.getD86() == null ? "-" : Math.round((ltv.getD86()/games.getRate()) * 100d)/100d,
						ltv.getD87() == null ? "-" : Math.round((ltv.getD87()/games.getRate()) * 100d)/100d,
						ltv.getD88() == null ? "-" : Math.round((ltv.getD88()/games.getRate()) * 100d)/100d,
						ltv.getD89() == null ? "-" : Math.round((ltv.getD89()/games.getRate()) * 100d)/100d,
						ltv.getD90() == null ? "-" : Math.round((ltv.getD90()/games.getRate()) * 100d)/100d
				};
				writeRow(row, data);
			}
		}
		flushAndClose();
	}
	//金额  按注册-分服  导出
	public void writeClientPayLtv_money() throws UnsupportedEncodingException {
		String fileName = "client_pay_install.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道","新注册",
				"D0("+games.getCurrency()+")","D1("+games.getCurrency()+")","D2("+games.getCurrency()+")",
				"D3("+games.getCurrency()+")","D4("+games.getCurrency()+")","D5("+games.getCurrency()+")",
				"D6("+games.getCurrency()+")","D7("+games.getCurrency()+")","D8("+games.getCurrency()+")",
				"D9("+games.getCurrency()+")","D10("+games.getCurrency()+")","D11("+games.getCurrency()+")",
				"D12("+games.getCurrency()+")","D13("+games.getCurrency()+")","D14("+games.getCurrency()+")",
				"D15("+games.getCurrency()+")","D16("+games.getCurrency()+")","D17("+games.getCurrency()+")",
				"D18("+games.getCurrency()+")","D19("+games.getCurrency()+")","D20("+games.getCurrency()+")",
				"D21("+games.getCurrency()+")","D22("+games.getCurrency()+")","D23("+games.getCurrency()+")",
				"D24("+games.getCurrency()+")","D25("+games.getCurrency()+")","D26("+games.getCurrency()+")",
				"D27("+games.getCurrency()+")","D28("+games.getCurrency()+")","D29("+games.getCurrency()+")",
				"D30("+games.getCurrency()+")","D31("+games.getCurrency()+")","D32("+games.getCurrency()+")",
				"D33("+games.getCurrency()+")","D34("+games.getCurrency()+")","D35("+games.getCurrency()+")",
				"D36("+games.getCurrency()+")","D37("+games.getCurrency()+")","D38("+games.getCurrency()+")",
				"D39("+games.getCurrency()+")","D40("+games.getCurrency()+")","D41("+games.getCurrency()+")",
				"D42("+games.getCurrency()+")","D43("+games.getCurrency()+")","D44("+games.getCurrency()+")",
				"D45("+games.getCurrency()+")","D46("+games.getCurrency()+")","D47("+games.getCurrency()+")",
				"D48("+games.getCurrency()+")","D49("+games.getCurrency()+")","D50("+games.getCurrency()+")",
				"D51("+games.getCurrency()+")","D52("+games.getCurrency()+")","D53("+games.getCurrency()+")",
				"D54("+games.getCurrency()+")","D55("+games.getCurrency()+")","D56("+games.getCurrency()+")",
				"D57("+games.getCurrency()+")","D58("+games.getCurrency()+")","D59("+games.getCurrency()+")",
				"D60("+games.getCurrency()+")","D61("+games.getCurrency()+")","D62("+games.getCurrency()+")",
				"D63("+games.getCurrency()+")","D64("+games.getCurrency()+")","D65("+games.getCurrency()+")",
				"D66("+games.getCurrency()+")","D67("+games.getCurrency()+")","D68("+games.getCurrency()+")",
				"D69("+games.getCurrency()+")","D70("+games.getCurrency()+")","D71("+games.getCurrency()+")",
				"D72("+games.getCurrency()+")","D73("+games.getCurrency()+")","D74("+games.getCurrency()+")",
				"D75("+games.getCurrency()+")","D76("+games.getCurrency()+")","D77("+games.getCurrency()+")",
				"D78("+games.getCurrency()+")","D79("+games.getCurrency()+")","D80("+games.getCurrency()+")",
				"D81("+games.getCurrency()+")","D82("+games.getCurrency()+")","D83("+games.getCurrency()+")",
				"D84("+games.getCurrency()+")","D85("+games.getCurrency()+")","D86("+games.getCurrency()+")",
				"D87("+games.getCurrency()+")","D88("+games.getCurrency()+")","D89("+games.getCurrency()+")",
				"D90("+games.getCurrency()+")"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (UserRetentionClientLtvVO ltvVO : userRetentionClientLtvs) {
			GameLtv ltv = ltvVO.getGameLtv();
			Row row=sheet.createRow(rowIdx++);
			if(ltv.getInstall() == null || ltv.getInstall() == 0){
				Object data[] = {ltv.getInstallDay(),
						ltv.getSourceOrClientName(),
						ltv.getInstall(),
						"-","-","-","-","-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-"};
				writeRow(row, data);
			}else{
				Object data[] = {
						ltv.getInstallDay(),
						ltv.getSourceOrClientName(),
						ltv.getInstall(),
						ltv.getD0() == null ? "-" : Math.round((ltv.getD0()/games.getRate()) * 100d)/100d,
						ltv.getD1() == null ? "-" : Math.round((ltv.getD1()/games.getRate()) * 100d)/100d,
						ltv.getD2() == null ? "-" : Math.round((ltv.getD2()/games.getRate()) * 100d)/100d,
						ltv.getD3() == null ? "-" : Math.round((ltv.getD3()/games.getRate()) * 100d)/100d,
						ltv.getD4() == null ? "-" : Math.round((ltv.getD4()/games.getRate()) * 100d)/100d,
						ltv.getD5() == null ? "-" : Math.round((ltv.getD5()/games.getRate()) * 100d)/100d,
						ltv.getD6() == null ? "-" : Math.round((ltv.getD6()/games.getRate()) * 100d)/100d,
						ltv.getD7() == null ? "-" : Math.round((ltv.getD7()/games.getRate()) * 100d)/100d,
						ltv.getD8() == null ? "-" : Math.round((ltv.getD8()/games.getRate()) * 100d)/100d,
						ltv.getD9() == null ? "-" : Math.round((ltv.getD9()/games.getRate()) * 100d)/100d,
						ltv.getD10() == null ? "-" : Math.round((ltv.getD10()/games.getRate()) * 100d)/100d,
						ltv.getD11() == null ? "-" : Math.round((ltv.getD11()/games.getRate()) * 100d)/100d,
						ltv.getD12() == null ? "-" : Math.round((ltv.getD12()/games.getRate()) * 100d)/100d,
						ltv.getD13() == null ? "-" : Math.round((ltv.getD13()/games.getRate()) * 100d)/100d,
						ltv.getD14() == null ? "-" : Math.round((ltv.getD14()/games.getRate()) * 100d)/100d,
						ltv.getD15() == null ? "-" : Math.round((ltv.getD15()/games.getRate()) * 100d)/100d,
						ltv.getD16() == null ? "-" : Math.round((ltv.getD16()/games.getRate()) * 100d)/100d,
						ltv.getD17() == null ? "-" : Math.round((ltv.getD17()/games.getRate()) * 100d)/100d,
						ltv.getD18() == null ? "-" : Math.round((ltv.getD18()/games.getRate()) * 100d)/100d,
						ltv.getD19() == null ? "-" : Math.round((ltv.getD19()/games.getRate()) * 100d)/100d,
						ltv.getD20() == null ? "-" : Math.round((ltv.getD20()/games.getRate()) * 100d)/100d,
						ltv.getD21() == null ? "-" : Math.round((ltv.getD21()/games.getRate()) * 100d)/100d,
						ltv.getD22() == null ? "-" : Math.round((ltv.getD22()/games.getRate()) * 100d)/100d,
						ltv.getD23() == null ? "-" : Math.round((ltv.getD23()/games.getRate()) * 100d)/100d,
						ltv.getD24() == null ? "-" : Math.round((ltv.getD24()/games.getRate()) * 100d)/100d,
						ltv.getD25() == null ? "-" : Math.round((ltv.getD25()/games.getRate()) * 100d)/100d,
						ltv.getD26() == null ? "-" : Math.round((ltv.getD26()/games.getRate()) * 100d)/100d,
						ltv.getD27() == null ? "-" : Math.round((ltv.getD27()/games.getRate()) * 100d)/100d,
						ltv.getD28() == null ? "-" : Math.round((ltv.getD28()/games.getRate()) * 100d)/100d,
						ltv.getD29() == null ? "-" : Math.round((ltv.getD29()/games.getRate()) * 100d)/100d,
						ltv.getD30() == null ? "-" : Math.round((ltv.getD30()/games.getRate()) * 100d)/100d,
						ltv.getD31() == null ? "-" : Math.round((ltv.getD31()/games.getRate()) * 100d)/100d,
						ltv.getD32() == null ? "-" : Math.round((ltv.getD32()/games.getRate()) * 100d)/100d,
						ltv.getD33() == null ? "-" : Math.round((ltv.getD33()/games.getRate()) * 100d)/100d,
						ltv.getD34() == null ? "-" : Math.round((ltv.getD34()/games.getRate()) * 100d)/100d,
						ltv.getD35() == null ? "-" : Math.round((ltv.getD35()/games.getRate()) * 100d)/100d,
						ltv.getD36() == null ? "-" : Math.round((ltv.getD36()/games.getRate()) * 100d)/100d,
						ltv.getD37() == null ? "-" : Math.round((ltv.getD37()/games.getRate()) * 100d)/100d,
						ltv.getD38() == null ? "-" : Math.round((ltv.getD38()/games.getRate()) * 100d)/100d,
						ltv.getD39() == null ? "-" : Math.round((ltv.getD39()/games.getRate()) * 100d)/100d,
						ltv.getD40() == null ? "-" : Math.round((ltv.getD40()/games.getRate()) * 100d)/100d,
						ltv.getD41() == null ? "-" : Math.round((ltv.getD41()/games.getRate()) * 100d)/100d,
						ltv.getD42() == null ? "-" : Math.round((ltv.getD42()/games.getRate()) * 100d)/100d,
						ltv.getD43() == null ? "-" : Math.round((ltv.getD43()/games.getRate()) * 100d)/100d,
						ltv.getD44() == null ? "-" : Math.round((ltv.getD44()/games.getRate()) * 100d)/100d,
						ltv.getD45() == null ? "-" : Math.round((ltv.getD45()/games.getRate()) * 100d)/100d,
						ltv.getD46() == null ? "-" : Math.round((ltv.getD46()/games.getRate()) * 100d)/100d,
						ltv.getD47() == null ? "-" : Math.round((ltv.getD47()/games.getRate()) * 100d)/100d,
						ltv.getD48() == null ? "-" : Math.round((ltv.getD48()/games.getRate()) * 100d)/100d,
						ltv.getD49() == null ? "-" : Math.round((ltv.getD49()/games.getRate()) * 100d)/100d,
						ltv.getD50() == null ? "-" : Math.round((ltv.getD50()/games.getRate()) * 100d)/100d,
						ltv.getD51() == null ? "-" : Math.round((ltv.getD51()/games.getRate()) * 100d)/100d,
						ltv.getD52() == null ? "-" : Math.round((ltv.getD52()/games.getRate()) * 100d)/100d,
						ltv.getD53() == null ? "-" : Math.round((ltv.getD53()/games.getRate()) * 100d)/100d,
						ltv.getD54() == null ? "-" : Math.round((ltv.getD54()/games.getRate()) * 100d)/100d,
						ltv.getD55() == null ? "-" : Math.round((ltv.getD55()/games.getRate()) * 100d)/100d,
						ltv.getD56() == null ? "-" : Math.round((ltv.getD56()/games.getRate()) * 100d)/100d,
						ltv.getD57() == null ? "-" : Math.round((ltv.getD57()/games.getRate()) * 100d)/100d,
						ltv.getD58() == null ? "-" : Math.round((ltv.getD58()/games.getRate()) * 100d)/100d,
						ltv.getD59() == null ? "-" : Math.round((ltv.getD59()/games.getRate()) * 100d)/100d,
						ltv.getD60() == null ? "-" : Math.round((ltv.getD60()/games.getRate()) * 100d)/100d,
						ltv.getD61() == null ? "-" : Math.round((ltv.getD61()/games.getRate()) * 100d)/100d,
						ltv.getD62() == null ? "-" : Math.round((ltv.getD62()/games.getRate()) * 100d)/100d,
						ltv.getD63() == null ? "-" : Math.round((ltv.getD63()/games.getRate()) * 100d)/100d,
						ltv.getD64() == null ? "-" : Math.round((ltv.getD64()/games.getRate()) * 100d)/100d,
						ltv.getD65() == null ? "-" : Math.round((ltv.getD65()/games.getRate()) * 100d)/100d,
						ltv.getD66() == null ? "-" : Math.round((ltv.getD66()/games.getRate()) * 100d)/100d,
						ltv.getD67() == null ? "-" : Math.round((ltv.getD67()/games.getRate()) * 100d)/100d,
						ltv.getD68() == null ? "-" : Math.round((ltv.getD68()/games.getRate()) * 100d)/100d,
						ltv.getD69() == null ? "-" : Math.round((ltv.getD69()/games.getRate()) * 100d)/100d,
						ltv.getD70() == null ? "-" : Math.round((ltv.getD70()/games.getRate()) * 100d)/100d,
						ltv.getD71() == null ? "-" : Math.round((ltv.getD71()/games.getRate()) * 100d)/100d,
						ltv.getD72() == null ? "-" : Math.round((ltv.getD72()/games.getRate()) * 100d)/100d,
						ltv.getD73() == null ? "-" : Math.round((ltv.getD73()/games.getRate()) * 100d)/100d,
						ltv.getD74() == null ? "-" : Math.round((ltv.getD74()/games.getRate()) * 100d)/100d,
						ltv.getD75() == null ? "-" : Math.round((ltv.getD75()/games.getRate()) * 100d)/100d,
						ltv.getD76() == null ? "-" : Math.round((ltv.getD76()/games.getRate()) * 100d)/100d,
						ltv.getD77() == null ? "-" : Math.round((ltv.getD77()/games.getRate()) * 100d)/100d,
						ltv.getD78() == null ? "-" : Math.round((ltv.getD78()/games.getRate()) * 100d)/100d,
						ltv.getD79() == null ? "-" : Math.round((ltv.getD79()/games.getRate()) * 100d)/100d,
						ltv.getD80() == null ? "-" : Math.round((ltv.getD80()/games.getRate()) * 100d)/100d,
						ltv.getD81() == null ? "-" : Math.round((ltv.getD81()/games.getRate()) * 100d)/100d,
						ltv.getD82() == null ? "-" : Math.round((ltv.getD82()/games.getRate()) * 100d)/100d,
						ltv.getD83() == null ? "-" : Math.round((ltv.getD83()/games.getRate()) * 100d)/100d,
						ltv.getD84() == null ? "-" : Math.round((ltv.getD84()/games.getRate()) * 100d)/100d,
						ltv.getD85() == null ? "-" : Math.round((ltv.getD85()/games.getRate()) * 100d)/100d,
						ltv.getD86() == null ? "-" : Math.round((ltv.getD86()/games.getRate()) * 100d)/100d,
						ltv.getD87() == null ? "-" : Math.round((ltv.getD87()/games.getRate()) * 100d)/100d,
						ltv.getD88() == null ? "-" : Math.round((ltv.getD88()/games.getRate()) * 100d)/100d,
						ltv.getD89() == null ? "-" : Math.round((ltv.getD89()/games.getRate()) * 100d)/100d,
						ltv.getD90() == null ? "-" : Math.round((ltv.getD90()/games.getRate()) * 100d)/100d
				};
				writeRow(row, data);
			}
		}
		flushAndClose();
	}
	//主收比导出
	public void writeClientPayLtvs() throws UnsupportedEncodingException {
		String fileName = "client_reg_install.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","新注册",
				"D0("+games.getCurrency()+")","D1("+games.getCurrency()+")","D2("+games.getCurrency()+")",
				"D3("+games.getCurrency()+")","D4("+games.getCurrency()+")","D5("+games.getCurrency()+")",
				"D6("+games.getCurrency()+")","D7("+games.getCurrency()+")","D8("+games.getCurrency()+")",
				"D9("+games.getCurrency()+")","D10("+games.getCurrency()+")","D11("+games.getCurrency()+")",
				"D12("+games.getCurrency()+")","D13("+games.getCurrency()+")","D14("+games.getCurrency()+")",
				"D15("+games.getCurrency()+")","D16("+games.getCurrency()+")","D17("+games.getCurrency()+")",
				"D18("+games.getCurrency()+")","D19("+games.getCurrency()+")","D20("+games.getCurrency()+")",
				"D21("+games.getCurrency()+")","D22("+games.getCurrency()+")","D23("+games.getCurrency()+")",
				"D24("+games.getCurrency()+")","D25("+games.getCurrency()+")","D26("+games.getCurrency()+")",
				"D27("+games.getCurrency()+")","D28("+games.getCurrency()+")","D29("+games.getCurrency()+")",
				"D30("+games.getCurrency()+")","D31("+games.getCurrency()+")","D32("+games.getCurrency()+")",
				"D33("+games.getCurrency()+")","D34("+games.getCurrency()+")","D35("+games.getCurrency()+")",
				"D36("+games.getCurrency()+")","D37("+games.getCurrency()+")","D38("+games.getCurrency()+")",
				"D39("+games.getCurrency()+")","D40("+games.getCurrency()+")","D41("+games.getCurrency()+")",
				"D42("+games.getCurrency()+")","D43("+games.getCurrency()+")","D44("+games.getCurrency()+")",
				"D45("+games.getCurrency()+")","D46("+games.getCurrency()+")","D47("+games.getCurrency()+")",
				"D48("+games.getCurrency()+")","D49("+games.getCurrency()+")","D50("+games.getCurrency()+")",
				"D51("+games.getCurrency()+")","D52("+games.getCurrency()+")","D53("+games.getCurrency()+")",
				"D54("+games.getCurrency()+")","D55("+games.getCurrency()+")","D56("+games.getCurrency()+")",
				"D57("+games.getCurrency()+")","D58("+games.getCurrency()+")","D59("+games.getCurrency()+")",
				"D60("+games.getCurrency()+")","D61("+games.getCurrency()+")","D62("+games.getCurrency()+")",
				"D63("+games.getCurrency()+")","D64("+games.getCurrency()+")","D65("+games.getCurrency()+")",
				"D66("+games.getCurrency()+")","D67("+games.getCurrency()+")","D68("+games.getCurrency()+")",
				"D69("+games.getCurrency()+")","D70("+games.getCurrency()+")","D71("+games.getCurrency()+")",
				"D72("+games.getCurrency()+")","D73("+games.getCurrency()+")","D74("+games.getCurrency()+")",
				"D75("+games.getCurrency()+")","D76("+games.getCurrency()+")","D77("+games.getCurrency()+")",
				"D78("+games.getCurrency()+")","D79("+games.getCurrency()+")","D80("+games.getCurrency()+")",
				"D81("+games.getCurrency()+")","D82("+games.getCurrency()+")","D83("+games.getCurrency()+")",
				"D84("+games.getCurrency()+")","D85("+games.getCurrency()+")","D86("+games.getCurrency()+")",
				"D87("+games.getCurrency()+")","D88("+games.getCurrency()+")","D89("+games.getCurrency()+")",
				"D90("+games.getCurrency()+")"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (UserRetentionLtvVO ltvVO : userRetentionLtvVO) {
			GameLtv ltv = ltvVO.getGameLtv();
			Row row=sheet.createRow(rowIdx++);
			if(ltv.getInstall() == null || ltv.getInstall() == 0){
				Object data[] = {ltv.getInstallDay(),
						//ltv.getSourceOrClientName(),
						ltv.getInstall(),
						"-","-","-","-","-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-"};
				writeRow(row, data);
			}else{
				// data[] = new Object[93];
				//if(ltv.getInstallDay().equals("合计")){
					Object data[] = {
							ltv.getInstallDay(),
							//ltv.getSourceOrClientName(),
							ltv.getInstall(),
							ltv.getD0() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD0()/games.getRate()) * 100d)/100d : Math.round((ltv.getD0()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD1() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD1()/games.getRate()) * 100d)/100d:Math.round((ltv.getD1()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD2() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD2()/games.getRate()) * 100d)/100d:Math.round((ltv.getD2()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD3() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD3()/games.getRate()) * 100d)/100d : Math.round((ltv.getD3()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD4() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD4()/games.getRate()) * 100d)/100d : Math.round((ltv.getD4()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD5() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD5()/games.getRate()) * 100d)/100d:Math.round((ltv.getD5()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD6() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD6()/games.getRate()) * 100d)/100d:Math.round((ltv.getD6()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD7() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD7()/games.getRate()) * 100d)/100d:Math.round((ltv.getD7()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD8() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD8()/games.getRate()) * 100d)/100d:Math.round((ltv.getD8()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD9() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD9()/games.getRate()) * 100d)/100d:Math.round((ltv.getD9()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD10() == null ? "-" : ltv.getInstallDay() =="合计"? Math.round((ltv.getD10()/games.getRate()) * 100d)/100d:Math.round((ltv.getD10()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD11() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD11()/games.getRate()) * 100d)/100d:Math.round((ltv.getD11()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD12() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD12()/games.getRate()) * 100d)/100d:Math.round((ltv.getD12()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD13() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD13()/games.getRate()) * 100d)/100d:Math.round((ltv.getD13()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD14() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD14()/games.getRate()) * 100d)/100d:Math.round((ltv.getD14()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD15() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD15()/games.getRate()) * 100d)/100d:Math.round((ltv.getD15()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD16() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD16()/games.getRate()) * 100d)/100d:Math.round((ltv.getD16()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD17() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD17()/games.getRate()) * 100d)/100d:Math.round((ltv.getD17()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD18() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD18()/games.getRate()) * 100d)/100d:Math.round((ltv.getD18()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD19() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD19()/games.getRate()) * 100d)/100d:Math.round((ltv.getD19()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD20() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD20()/games.getRate()) * 100d)/100d:Math.round((ltv.getD20()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD21() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD21()/games.getRate()) * 100d)/100d:Math.round((ltv.getD21()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD22() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD22()/games.getRate()) * 100d)/100d:Math.round((ltv.getD22()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD23() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD23()/games.getRate()) * 100d)/100d:Math.round((ltv.getD23()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD24() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD24()/games.getRate()) * 100d)/100d:Math.round((ltv.getD24()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD25() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD25()/games.getRate()) * 100d)/100d:Math.round((ltv.getD25()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD26() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD26()/games.getRate()) * 100d)/100d:Math.round((ltv.getD26()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD27() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD27()/games.getRate()) * 100d)/100d:Math.round((ltv.getD27()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD28() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD28()/games.getRate()) * 100d)/100d:Math.round((ltv.getD28()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD29() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD29()/games.getRate()) * 100d)/100d:Math.round((ltv.getD29()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD30() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD30()/games.getRate()) * 100d)/100d:Math.round((ltv.getD30()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD31() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD31()/games.getRate()) * 100d)/100d:Math.round((ltv.getD31()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD32() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD32()/games.getRate()) * 100d)/100d:Math.round((ltv.getD32()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD33() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD33()/games.getRate()) * 100d)/100d:Math.round((ltv.getD33()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD34() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD34()/games.getRate()) * 100d)/100d:Math.round((ltv.getD34()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD35() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD35()/games.getRate()) * 100d)/100d:Math.round((ltv.getD35()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD36() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD36()/games.getRate()) * 100d)/100d:Math.round((ltv.getD36()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD37() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD37()/games.getRate()) * 100d)/100d:Math.round((ltv.getD37()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD38() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD38()/games.getRate()) * 100d)/100d:Math.round((ltv.getD38()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD39() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD39()/games.getRate()) * 100d)/100d:Math.round((ltv.getD39()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD40() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD40()/games.getRate()) * 100d)/100d:Math.round((ltv.getD40()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD41() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD41()/games.getRate()) * 100d)/100d:Math.round((ltv.getD41()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD42() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD42()/games.getRate()) * 100d)/100d:Math.round((ltv.getD42()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD43() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD43()/games.getRate()) * 100d)/100d:Math.round((ltv.getD43()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD44() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD44()/games.getRate()) * 100d)/100d:Math.round((ltv.getD44()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD45() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD45()/games.getRate()) * 100d)/100d:Math.round((ltv.getD45()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD46() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD46()/games.getRate()) * 100d)/100d:Math.round((ltv.getD46()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD47() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD47()/games.getRate()) * 100d)/100d:Math.round((ltv.getD47()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD48() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD48()/games.getRate()) * 100d)/100d:Math.round((ltv.getD48()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD49() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD49()/games.getRate()) * 100d)/100d:Math.round((ltv.getD49()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD50() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD50()/games.getRate()) * 100d)/100d:Math.round((ltv.getD50()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD51() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD51()/games.getRate()) * 100d)/100d:Math.round((ltv.getD51()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD52() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD52()/games.getRate()) * 100d)/100d:Math.round((ltv.getD52()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD53() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD53()/games.getRate()) * 100d)/100d:Math.round((ltv.getD53()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD54() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD54()/games.getRate()) * 100d)/100d:Math.round((ltv.getD54()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD55() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD55()/games.getRate()) * 100d)/100d:Math.round((ltv.getD55()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD56() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD56()/games.getRate()) * 100d)/100d:Math.round((ltv.getD56()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD57() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD57()/games.getRate()) * 100d)/100d:Math.round((ltv.getD57()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD58() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD58()/games.getRate()) * 100d)/100d:Math.round((ltv.getD58()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD59() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD59()/games.getRate()) * 100d)/100d:Math.round((ltv.getD59()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD60() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD60()/games.getRate()) * 100d)/100d:Math.round((ltv.getD60()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD61() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD61()/games.getRate()) * 100d)/100d:Math.round((ltv.getD61()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD62() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD62()/games.getRate()) * 100d)/100d:Math.round((ltv.getD62()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD63() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD63()/games.getRate()) * 100d)/100d:Math.round((ltv.getD63()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD64() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD64()/games.getRate()) * 100d)/100d:Math.round((ltv.getD64()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD65() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD65()/games.getRate()) * 100d)/100d:Math.round((ltv.getD65()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD66() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD66()/games.getRate()) * 100d)/100d:Math.round((ltv.getD66()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD67() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD67()/games.getRate()) * 100d)/100d:Math.round((ltv.getD67()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD68() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD68()/games.getRate()) * 100d)/100d:Math.round((ltv.getD68()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD69() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD69()/games.getRate()) * 100d)/100d:Math.round((ltv.getD69()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD70() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD70()/games.getRate()) * 100d)/100d:Math.round((ltv.getD70()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD71() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD71()/games.getRate()) * 100d)/100d:Math.round((ltv.getD71()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD72() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD72()/games.getRate()) * 100d)/100d:Math.round((ltv.getD72()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD73() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD73()/games.getRate()) * 100d)/100d:Math.round((ltv.getD73()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD74() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD74()/games.getRate()) * 100d)/100d:Math.round((ltv.getD74()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD75() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD75()/games.getRate()) * 100d)/100d:Math.round((ltv.getD75()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD76() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD76()/games.getRate()) * 100d)/100d:Math.round((ltv.getD76()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD77() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD77()/games.getRate()) * 100d)/100d:Math.round((ltv.getD77()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD78() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD78()/games.getRate()) * 100d)/100d:Math.round((ltv.getD78()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD79() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD79()/games.getRate()) * 100d)/100d:Math.round((ltv.getD79()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD80() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD80()/games.getRate()) * 100d)/100d:Math.round((ltv.getD80()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD81() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD81()/games.getRate()) * 100d)/100d:Math.round((ltv.getD81()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD82() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD82()/games.getRate()) * 100d)/100d:Math.round((ltv.getD82()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD83() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD83()/games.getRate()) * 100d)/100d:Math.round((ltv.getD83()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD84() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD84()/games.getRate()) * 100d)/100d:Math.round((ltv.getD84()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD85() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD85()/games.getRate()) * 100d)/100d:Math.round((ltv.getD85()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD86() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD86()/games.getRate()) * 100d)/100d:Math.round((ltv.getD86()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD87() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD87()/games.getRate()) * 100d)/100d:Math.round((ltv.getD87()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD88() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD88()/games.getRate()) * 100d)/100d:Math.round((ltv.getD88()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD89() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD89()/games.getRate()) * 100d)/100d:Math.round((ltv.getD89()/ltv.getInstall()/games.getRate()) * 100d)/100d,
							ltv.getD90() == null ? "-" : ltv.getInstallDay() =="合计"?Math.round((ltv.getD90()/games.getRate()) * 100d)/100d:Math.round((ltv.getD90()/ltv.getInstall()/games.getRate()) * 100d)/100d
					};
					
				//}
				
				writeRow(row, data);
			}
		}
		flushAndClose();
	}
	
	//金额
	public void writeClientPayMoney() throws UnsupportedEncodingException {
		String fileName = "client_money_install.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","新注册",
				"D0("+games.getCurrency()+")","D1("+games.getCurrency()+")","D2("+games.getCurrency()+")",
				"D3("+games.getCurrency()+")","D4("+games.getCurrency()+")","D5("+games.getCurrency()+")",
				"D6("+games.getCurrency()+")","D7("+games.getCurrency()+")","D8("+games.getCurrency()+")",
				"D9("+games.getCurrency()+")","D10("+games.getCurrency()+")","D11("+games.getCurrency()+")",
				"D12("+games.getCurrency()+")","D13("+games.getCurrency()+")","D14("+games.getCurrency()+")",
				"D15("+games.getCurrency()+")","D16("+games.getCurrency()+")","D17("+games.getCurrency()+")",
				"D18("+games.getCurrency()+")","D19("+games.getCurrency()+")","D20("+games.getCurrency()+")",
				"D21("+games.getCurrency()+")","D22("+games.getCurrency()+")","D23("+games.getCurrency()+")",
				"D24("+games.getCurrency()+")","D25("+games.getCurrency()+")","D26("+games.getCurrency()+")",
				"D27("+games.getCurrency()+")","D28("+games.getCurrency()+")","D29("+games.getCurrency()+")",
				"D30("+games.getCurrency()+")","D31("+games.getCurrency()+")","D32("+games.getCurrency()+")",
				"D33("+games.getCurrency()+")","D34("+games.getCurrency()+")","D35("+games.getCurrency()+")",
				"D36("+games.getCurrency()+")","D37("+games.getCurrency()+")","D38("+games.getCurrency()+")",
				"D39("+games.getCurrency()+")","D40("+games.getCurrency()+")","D41("+games.getCurrency()+")",
				"D42("+games.getCurrency()+")","D43("+games.getCurrency()+")","D44("+games.getCurrency()+")",
				"D45("+games.getCurrency()+")","D46("+games.getCurrency()+")","D47("+games.getCurrency()+")",
				"D48("+games.getCurrency()+")","D49("+games.getCurrency()+")","D50("+games.getCurrency()+")",
				"D51("+games.getCurrency()+")","D52("+games.getCurrency()+")","D53("+games.getCurrency()+")",
				"D54("+games.getCurrency()+")","D55("+games.getCurrency()+")","D56("+games.getCurrency()+")",
				"D57("+games.getCurrency()+")","D58("+games.getCurrency()+")","D59("+games.getCurrency()+")",
				"D60("+games.getCurrency()+")","D61("+games.getCurrency()+")","D62("+games.getCurrency()+")",
				"D63("+games.getCurrency()+")","D64("+games.getCurrency()+")","D65("+games.getCurrency()+")",
				"D66("+games.getCurrency()+")","D67("+games.getCurrency()+")","D68("+games.getCurrency()+")",
				"D69("+games.getCurrency()+")","D70("+games.getCurrency()+")","D71("+games.getCurrency()+")",
				"D72("+games.getCurrency()+")","D73("+games.getCurrency()+")","D74("+games.getCurrency()+")",
				"D75("+games.getCurrency()+")","D76("+games.getCurrency()+")","D77("+games.getCurrency()+")",
				"D78("+games.getCurrency()+")","D79("+games.getCurrency()+")","D80("+games.getCurrency()+")",
				"D81("+games.getCurrency()+")","D82("+games.getCurrency()+")","D83("+games.getCurrency()+")",
				"D84("+games.getCurrency()+")","D85("+games.getCurrency()+")","D86("+games.getCurrency()+")",
				"D87("+games.getCurrency()+")","D88("+games.getCurrency()+")","D89("+games.getCurrency()+")",
				"D90("+games.getCurrency()+")"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (UserRetentionLtvVO ltvVO : userRetentionLtvVO) {
			GameLtv ltv = ltvVO.getGameLtv();
			Row row=sheet.createRow(rowIdx++);
			if(ltv.getInstall() == null || ltv.getInstall() == 0){
				Object data[] = {ltv.getInstallDay(),
						//ltv.getSourceOrClientName(),
						ltv.getInstall(),
						"-","-","-","-","-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-",
						"-","-","-","-","-","-","-","-","-","-"};
				writeRow(row, data);
			}else{
				Object data[] = {
						ltv.getInstallDay(),
						//ltv.getSourceOrClientName(),
						ltv.getInstall(),
						ltv.getD0() == null ? "-" : Math.round((ltv.getD0()/games.getRate()) * 100d)/100d,
						ltv.getD1() == null ? "-" : Math.round((ltv.getD1()/games.getRate()) * 100d)/100d,
						ltv.getD2() == null ? "-" : Math.round((ltv.getD2()/games.getRate()) * 100d)/100d,
						ltv.getD3() == null ? "-" : Math.round((ltv.getD3()/games.getRate()) * 100d)/100d,
						ltv.getD4() == null ? "-" : Math.round((ltv.getD4()/games.getRate()) * 100d)/100d,
						ltv.getD5() == null ? "-" : Math.round((ltv.getD5()/games.getRate()) * 100d)/100d,
						ltv.getD6() == null ? "-" : Math.round((ltv.getD6()/games.getRate()) * 100d)/100d,
						ltv.getD7() == null ? "-" : Math.round((ltv.getD7()/games.getRate()) * 100d)/100d,
						ltv.getD8() == null ? "-" : Math.round((ltv.getD8()/games.getRate()) * 100d)/100d,
						ltv.getD9() == null ? "-" : Math.round((ltv.getD9()/games.getRate()) * 100d)/100d,
						ltv.getD10() == null ? "-" : Math.round((ltv.getD10()/games.getRate()) * 100d)/100d,
						ltv.getD11() == null ? "-" : Math.round((ltv.getD11()/games.getRate()) * 100d)/100d,
						ltv.getD12() == null ? "-" : Math.round((ltv.getD12()/games.getRate()) * 100d)/100d,
						ltv.getD13() == null ? "-" : Math.round((ltv.getD13()/games.getRate()) * 100d)/100d,
						ltv.getD14() == null ? "-" : Math.round((ltv.getD14()/games.getRate()) * 100d)/100d,
						ltv.getD15() == null ? "-" : Math.round((ltv.getD15()/games.getRate()) * 100d)/100d,
						ltv.getD16() == null ? "-" : Math.round((ltv.getD16()/games.getRate()) * 100d)/100d,
						ltv.getD17() == null ? "-" : Math.round((ltv.getD17()/games.getRate()) * 100d)/100d,
						ltv.getD18() == null ? "-" : Math.round((ltv.getD18()/games.getRate()) * 100d)/100d,
						ltv.getD19() == null ? "-" : Math.round((ltv.getD19()/games.getRate()) * 100d)/100d,
						ltv.getD20() == null ? "-" : Math.round((ltv.getD20()/games.getRate()) * 100d)/100d,
						ltv.getD21() == null ? "-" : Math.round((ltv.getD21()/games.getRate()) * 100d)/100d,
						ltv.getD22() == null ? "-" : Math.round((ltv.getD22()/games.getRate()) * 100d)/100d,
						ltv.getD23() == null ? "-" : Math.round((ltv.getD23()/games.getRate()) * 100d)/100d,
						ltv.getD24() == null ? "-" : Math.round((ltv.getD24()/games.getRate()) * 100d)/100d,
						ltv.getD25() == null ? "-" : Math.round((ltv.getD25()/games.getRate()) * 100d)/100d,
						ltv.getD26() == null ? "-" : Math.round((ltv.getD26()/games.getRate()) * 100d)/100d,
						ltv.getD27() == null ? "-" : Math.round((ltv.getD27()/games.getRate()) * 100d)/100d,
						ltv.getD28() == null ? "-" : Math.round((ltv.getD28()/games.getRate()) * 100d)/100d,
						ltv.getD29() == null ? "-" : Math.round((ltv.getD29()/games.getRate()) * 100d)/100d,
						ltv.getD30() == null ? "-" : Math.round((ltv.getD30()/games.getRate()) * 100d)/100d,
						ltv.getD31() == null ? "-" : Math.round((ltv.getD31()/games.getRate()) * 100d)/100d,
						ltv.getD32() == null ? "-" : Math.round((ltv.getD32()/games.getRate()) * 100d)/100d,
						ltv.getD33() == null ? "-" : Math.round((ltv.getD33()/games.getRate()) * 100d)/100d,
						ltv.getD34() == null ? "-" : Math.round((ltv.getD34()/games.getRate()) * 100d)/100d,
						ltv.getD35() == null ? "-" : Math.round((ltv.getD35()/games.getRate()) * 100d)/100d,
						ltv.getD36() == null ? "-" : Math.round((ltv.getD36()/games.getRate()) * 100d)/100d,
						ltv.getD37() == null ? "-" : Math.round((ltv.getD37()/games.getRate()) * 100d)/100d,
						ltv.getD38() == null ? "-" : Math.round((ltv.getD38()/games.getRate()) * 100d)/100d,
						ltv.getD39() == null ? "-" : Math.round((ltv.getD39()/games.getRate()) * 100d)/100d,
						ltv.getD40() == null ? "-" : Math.round((ltv.getD40()/games.getRate()) * 100d)/100d,
						ltv.getD41() == null ? "-" : Math.round((ltv.getD41()/games.getRate()) * 100d)/100d,
						ltv.getD42() == null ? "-" : Math.round((ltv.getD42()/games.getRate()) * 100d)/100d,
						ltv.getD43() == null ? "-" : Math.round((ltv.getD43()/games.getRate()) * 100d)/100d,
						ltv.getD44() == null ? "-" : Math.round((ltv.getD44()/games.getRate()) * 100d)/100d,
						ltv.getD45() == null ? "-" : Math.round((ltv.getD45()/games.getRate()) * 100d)/100d,
						ltv.getD46() == null ? "-" : Math.round((ltv.getD46()/games.getRate()) * 100d)/100d,
						ltv.getD47() == null ? "-" : Math.round((ltv.getD47()/games.getRate()) * 100d)/100d,
						ltv.getD48() == null ? "-" : Math.round((ltv.getD48()/games.getRate()) * 100d)/100d,
						ltv.getD49() == null ? "-" : Math.round((ltv.getD49()/games.getRate()) * 100d)/100d,
						ltv.getD50() == null ? "-" : Math.round((ltv.getD50()/games.getRate()) * 100d)/100d,
						ltv.getD51() == null ? "-" : Math.round((ltv.getD51()/games.getRate()) * 100d)/100d,
						ltv.getD52() == null ? "-" : Math.round((ltv.getD52()/games.getRate()) * 100d)/100d,
						ltv.getD53() == null ? "-" : Math.round((ltv.getD53()/games.getRate()) * 100d)/100d,
						ltv.getD54() == null ? "-" : Math.round((ltv.getD54()/games.getRate()) * 100d)/100d,
						ltv.getD55() == null ? "-" : Math.round((ltv.getD55()/games.getRate()) * 100d)/100d,
						ltv.getD56() == null ? "-" : Math.round((ltv.getD56()/games.getRate()) * 100d)/100d,
						ltv.getD57() == null ? "-" : Math.round((ltv.getD57()/games.getRate()) * 100d)/100d,
						ltv.getD58() == null ? "-" : Math.round((ltv.getD58()/games.getRate()) * 100d)/100d,
						ltv.getD59() == null ? "-" : Math.round((ltv.getD59()/games.getRate()) * 100d)/100d,
						ltv.getD60() == null ? "-" : Math.round((ltv.getD60()/games.getRate()) * 100d)/100d,
						ltv.getD61() == null ? "-" : Math.round((ltv.getD61()/games.getRate()) * 100d)/100d,
						ltv.getD62() == null ? "-" : Math.round((ltv.getD62()/games.getRate()) * 100d)/100d,
						ltv.getD63() == null ? "-" : Math.round((ltv.getD63()/games.getRate()) * 100d)/100d,
						ltv.getD64() == null ? "-" : Math.round((ltv.getD64()/games.getRate()) * 100d)/100d,
						ltv.getD65() == null ? "-" : Math.round((ltv.getD65()/games.getRate()) * 100d)/100d,
						ltv.getD66() == null ? "-" : Math.round((ltv.getD66()/games.getRate()) * 100d)/100d,
						ltv.getD67() == null ? "-" : Math.round((ltv.getD67()/games.getRate()) * 100d)/100d,
						ltv.getD68() == null ? "-" : Math.round((ltv.getD68()/games.getRate()) * 100d)/100d,
						ltv.getD69() == null ? "-" : Math.round((ltv.getD69()/games.getRate()) * 100d)/100d,
						ltv.getD70() == null ? "-" : Math.round((ltv.getD70()/games.getRate()) * 100d)/100d,
						ltv.getD71() == null ? "-" : Math.round((ltv.getD71()/games.getRate()) * 100d)/100d,
						ltv.getD72() == null ? "-" : Math.round((ltv.getD72()/games.getRate()) * 100d)/100d,
						ltv.getD73() == null ? "-" : Math.round((ltv.getD73()/games.getRate()) * 100d)/100d,
						ltv.getD74() == null ? "-" : Math.round((ltv.getD74()/games.getRate()) * 100d)/100d,
						ltv.getD75() == null ? "-" : Math.round((ltv.getD75()/games.getRate()) * 100d)/100d,
						ltv.getD76() == null ? "-" : Math.round((ltv.getD76()/games.getRate()) * 100d)/100d,
						ltv.getD77() == null ? "-" : Math.round((ltv.getD77()/games.getRate()) * 100d)/100d,
						ltv.getD78() == null ? "-" : Math.round((ltv.getD78()/games.getRate()) * 100d)/100d,
						ltv.getD79() == null ? "-" : Math.round((ltv.getD79()/games.getRate()) * 100d)/100d,
						ltv.getD80() == null ? "-" : Math.round((ltv.getD80()/games.getRate()) * 100d)/100d,
						ltv.getD81() == null ? "-" : Math.round((ltv.getD81()/games.getRate()) * 100d)/100d,
						ltv.getD82() == null ? "-" : Math.round((ltv.getD82()/games.getRate()) * 100d)/100d,
						ltv.getD83() == null ? "-" : Math.round((ltv.getD83()/games.getRate()) * 100d)/100d,
						ltv.getD84() == null ? "-" : Math.round((ltv.getD84()/games.getRate()) * 100d)/100d,
						ltv.getD85() == null ? "-" : Math.round((ltv.getD85()/games.getRate()) * 100d)/100d,
						ltv.getD86() == null ? "-" : Math.round((ltv.getD86()/games.getRate()) * 100d)/100d,
						ltv.getD87() == null ? "-" : Math.round((ltv.getD87()/games.getRate()) * 100d)/100d,
						ltv.getD88() == null ? "-" : Math.round((ltv.getD88()/games.getRate()) * 100d)/100d,
						ltv.getD89() == null ? "-" : Math.round((ltv.getD89()/games.getRate()) * 100d)/100d,
						ltv.getD90() == null ? "-" : Math.round((ltv.getD90()/games.getRate()) * 100d)/100d
				};
				writeRow(row, data);
			}
		}
		flushAndClose();
	}
	
	
	public void writeSourceAllPay() throws UnsupportedEncodingException {
		String fileName = "source_allPay.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道","付费额("+games.getCurrency()+")",
				"付费人数","ARPU("+games.getCurrency()+")","ARPPU("+games.getCurrency()+")","付费率"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (SourceDailyReport sdr : sourceDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					sdr.getDay(),
					sdr.getSource(),
					Math.round((sdr.getPaymentAmount()/games.getRate())*100d)/100d,
					sdr.getPu(),
					Math.round((sdr.getArpu()/games.getRate())*100d)/100d,
					Math.round((sdr.getArppu()/games.getRate())*100d)/100d,
					Math.round(sdr.getPayRate() * 100d*100d)/100d + "%"
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	public void writeClientAllPay() throws UnsupportedEncodingException {
		String fileName = "client_allPay.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","服务器","付费额("+games.getCurrency()+")",
				"付费人数","ARPU("+games.getCurrency()+")","ARPPU("+games.getCurrency()+")","付费率"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (ClientDailyReport cdr : clientDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					cdr.getDay(),
					cdr.getClientid(),
					Math.round((cdr.getPaymentAmount()/games.getRate())*100d)/100d,
					cdr.getPu(),
					Math.round((cdr.getArpu()/games.getRate())*100d)/100d,
					Math.round((cdr.getArppu()/games.getRate())*100d)/100d,
					Math.round(cdr.getPayRate() * 100d*100d)/100d + "%"
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	public void writeClientNewPay() throws UnsupportedEncodingException {
		String fileName = "client_newPay.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","服务器","新付费人数","新付费额("+games.getCurrency()+")",
				"新ARPPU("+games.getCurrency()+")","注册付费人数","注册付费额("+games.getCurrency()+")",
				"注册付费ARPPU("+games.getCurrency()+")"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (ClientDailyReport cdr : clientDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					cdr.getDay(),
					cdr.getClientid(),
					cdr.getNewPu(),
					Math.round((cdr.getNewPayAmount()/games.getRate())*100d)/100d,
					cdr.getNewPu() == 0 ? "-" : Math.round((cdr.getNewPayAmount() / cdr.getNewPu() / games.getRate())*100d)/100d,
				    cdr.getInstallPu(),
				    Math.round((cdr.getInstallPayAmount() / games.getRate())*100d)/100d,
				    cdr.getInstallPu() == 0 ? "-" : Math.round((cdr.getInstallPayAmount() /  cdr.getInstallPu() / games.getRate())*100)/100
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}
	
	public void writeSourceNewPay() throws UnsupportedEncodingException {
		String fileName = "source_newPay.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道","新付费人数","新付费额("+games.getCurrency()+")",
				"新ARPPU("+games.getCurrency()+")","注册付费人数","注册付费额("+games.getCurrency()+")",
				"注册付费ARPPU("+games.getCurrency()+")"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (SourceDailyReport sdr : sourceDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					sdr.getDay(),
					sdr.getSource(),
					sdr.getNewPu(),
					Math.round((sdr.getNewPayAmount()/games.getRate())*100d)/100d,
					sdr.getNewPu() == 0 ? "-" : Math.round((sdr.getNewPayAmount() / sdr.getNewPu() / games.getRate())*100d)/100d,
				    sdr.getInstallPu(),
				    Math.round((sdr.getInstallPayAmount() / games.getRate())*100d)/100d,
				    sdr.getInstallPu() == 0 ? "-" : Math.round((sdr.getInstallPayAmount() /  sdr.getInstallPu() / games.getRate())*100)/100
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}
	
	public void writewapSourceDaily() throws UnsupportedEncodingException {
		String fileName = "source_daily.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道","新注册","激活设备数","创角数","dau","老玩家活跃","付费额("+games.getCurrency()+")",
				"付费人数","ARPU("+games.getCurrency()+")","ARPPU("+games.getCurrency()+")",
				"付费次数","付费率(%)","新付费额("+games.getCurrency()+")","新付费人数","注册付费额("+games.getCurrency()+")",
				"注册付费人数","滚服付费额("+games.getCurrency()+")","滚服付费人数"," 滚服人数","最高在线人数","平均在线人数","平均在线时长",
				"广告点击总数","广告点击总数(去重)","ip总数(去重)"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (SourceDailyReport sdr : sourceDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					sdr.getDay(),
					sdr.getSource(),
					sdr.getDnu(),
					sdr.getNewEquip(),
					sdr.getRoleChoice(),
					sdr.getDau(),
					sdr.getDau()-sdr.getDnu(),
					Math.round((sdr.getPaymentAmount()/games.getRate())*100d)/100d,
					sdr.getPu(),
					Math.round((sdr.getArpu()/games.getRate())*100d)/100d,
					Math.round((sdr.getArppu()/games.getRate())*100d)/100d,
					sdr.getPaymentCnt(),
					Math.round(sdr.getPayRate()*10000)/100d,
					Math.round((sdr.getNewPayAmount()/games.getRate())*100d)/100d,
					sdr.getNewPu(),
					Math.round((sdr.getInstallPayAmount()/games.getRate())*100d)/100d,
					sdr.getInstallPu(),
					Math.round((sdr.getRollAmount()/games.getRate())*100d)/100d,
					sdr.getRollPayUsers(),
					sdr.getRollUsers(),
					sdr.getPcu(),
					Math.round((sdr.getAcu() == null ? 0 : sdr.getAcu())*100d)/100d,
					Math.round(((sdr.getAvgUserTime() == null ? 0 : sdr.getAvgUserTime())/60)*100d)/100d +"分",
					sdr.getIdfa() == null ? 0 : sdr.getIdfa(),
					sdr.getDistinctIdfa() == null ? 0 : sdr.getDistinctIdfa(),
					sdr.getDistinctIp() == null ? 0 : sdr.getDistinctIp()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}
	//实时分服导出
	/*
	public void writeClientRealTime() throws UnsupportedEncodingException {
		String fileName = "client_realTime.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = {"日期","服务器","新注册(含滚服)","活跃","收入("+games.getCurrency()+")",
				"ARPU("+games.getCurrency()+")","ARPPU("+games.getCurrency()+")","付费人数"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (RealTimeResult rtr : realTimeList) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					rtr.getDs(),
					rtr.getClientid(),
					rtr.getInstall(),
					rtr.getDau(),
					(rtr.getAmount() == null || rtr.getAmount() == 0) ? 0 : Math.round(rtr.getAmount()/games.getRate()*100d)/100d,
					(rtr.getAmount() == null || rtr.getDau() == null || rtr.getDau() == 0) ? 0 : Math.round(rtr.getAmount()/rtr.getDau()/games.getRate()*100d)/100d,
					(rtr.getAmount() == null || rtr.getPayment() == null || rtr.getPayment() == 0) ? 0 : Math.round(rtr.getAmount()/rtr.getPayment()/games.getRate()*100d)/100d,
					rtr.getPayment(),
					rtr.getPaytimes()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}
	*/
	public void writeClientRealTime() throws UnsupportedEncodingException {
		String fileName = "client_realTime.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = {"日期","服务器","新注册(含滚服)","活跃","收入("+games.getCurrency()+")",
				"ARPU("+games.getCurrency()+")","ARPPU("+games.getCurrency()+")","付费人数","付费次数"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (RealTimeResult rtr : realTimeList) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					rtr.getDs(),
					rtr.getClientid(),
					rtr.getInstall(),
					rtr.getDau(),
					(rtr.getAmount() == null || rtr.getAmount() == 0) ? 0 : Math.round(rtr.getAmount()/games.getRate()*100d)/100d,
					(rtr.getAmount() == null || rtr.getDau() == null || rtr.getDau() == 0) ? 0 : Math.round(rtr.getAmount()/rtr.getDau()/games.getRate()*100d)/100d,
					(rtr.getAmount() == null || rtr.getPayment() == null || rtr.getPayment() == 0) ? 0 : Math.round(rtr.getAmount()/rtr.getPayment()/games.getRate()*100d)/100d,
					rtr.getPayment(),
					rtr.getPaytimes()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}
	
	public void writewapClientDaily() throws UnsupportedEncodingException {
		String fileName = "client_daily.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","服务器","新注册","创角数","dau","老玩家活跃","付费额("+games.getCurrency()+")",
				"付费人数","ARPU("+games.getCurrency()+")","ARPPU("+games.getCurrency()+")",
				"付费次数","付费率(%)","新付费额("+games.getCurrency()+")","新付费人数","注册付费额("+games.getCurrency()+")",
				"注册付费人数","滚服付费额("+games.getCurrency()+")","滚服付费人数"," 滚服人数","最高在线人数","平均在线人数","平均在线时长"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (ClientDailyReport cdr : clientDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					cdr.getDay(),
					cdr.getClientid(),
					cdr.getDnu(),
					"-",
					cdr.getDau(),
					cdr.getDau()-cdr.getDnu(),
					Math.round((cdr.getPaymentAmount()/games.getRate())*100d)/100d,
					cdr.getPu(),
					Math.round((cdr.getArpu()/games.getRate())*100d)/100d,
					Math.round((cdr.getArppu()/games.getRate())*100d)/100d,
					cdr.getPaymentCnt(),
					Math.round(cdr.getPayRate()*10000)/100d,
					Math.round((cdr.getNewPayAmount()/games.getRate())*100d)/100d,
					cdr.getNewPu(),
					Math.round((cdr.getInstallPayAmount()/games.getRate())*100d)/100d,
					cdr.getInstallPu(),
					Math.round((cdr.getRollAmount()/games.getRate())*100d)/100d,
					cdr.getRollPayUsers(),
					cdr.getRollUsers(),
					cdr.getPcu(),
					Math.round(cdr.getAcu()*100d)/100d,
					Math.round((cdr.getAvgUserTime()/60)*100d)/100d +"分"
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	public void writeClientPayBehavior() throws UnsupportedEncodingException {
		String fileName = "client_payBehavior.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = {"日期","渠道","[0,10]","(10,50]",
				"(50,100]","(100,200]","(200,500]","(500,1000]",
				"(1000,2000]","2000以上"};
		
		Sheet sheet_pay=wb.createSheet("pay");
		Sheet sheet_user=wb.createSheet("user");
		
		CellStyle style_header = herderStyle();
		
		int rowIdx_pay=3;
		int rowIdx_user=3;
		
		
		sheet_pay.addMergedRegion(new CellRangeAddress(0, 2, 0, 9));
		Cell cell_header = sheet_pay.createRow(0).createCell(0);
		cell_header.setCellStyle(style_header);
		cell_header.setCellValue("等级付费额("+games.getCurrency()+")");
		
		sheet_user.addMergedRegion(new CellRangeAddress(0, 2, 0, 9));
		cell_header = sheet_user.createRow(0).createCell(0);
		cell_header.setCellStyle(style_header);
		cell_header.setCellValue("付费人数");
		
		
		Row headerRow_pay = sheet_pay.createRow(rowIdx_pay++);
		Row headerRow_user = sheet_user.createRow(rowIdx_user++);
		
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow_pay.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
			
			cell=headerRow_user.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (PaylevelAmountCntClientVO sdr : cntPayClientList) {
			Row row=sheet_pay.createRow(rowIdx_pay++);
			LevelPay lp = sdr.getLevelPay();
			Object data[] = {
					lp.getStatDay(),
					lp.getSourceOrClientName(),
					lp.getLp10(),
					lp.getLp50(),
					lp.getLp100(),
					lp.getLp200(),
					lp.getLp500(),
					lp.getLp1000(),
					lp.getLp2000(),
					lp.getLp2000up()
			};
			
			writeRow(row, data);
		}
		for (PaylevelAmountCntClientVO sdr : cntUserClientList) {
			Row row=sheet_user.createRow(rowIdx_user++);
			LevelPay lp = sdr.getLevelPay();
			Object data[] = {
					lp.getStatDay(),
					lp.getSourceOrClientName(),
					lp.getLp10(),
					lp.getLp50(),
					lp.getLp100(),
					lp.getLp200(),
					lp.getLp500(),
					lp.getLp1000(),
					lp.getLp2000(),
					lp.getLp2000up()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}
	
	public void writeSourcePayBehavior() throws UnsupportedEncodingException {
		String fileName = "source_payBehavior.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = {"日期","渠道","[0,10]","(10,50]",
				"(50,100]","(100,200]","(200,500]","(500,1000]",
				"(1000,2000]","2000以上"};
		
		Sheet sheet_pay=wb.createSheet("pay");
		Sheet sheet_user=wb.createSheet("user");
		
		CellStyle style_header = herderStyle();
		
		int rowIdx_pay=3;
		int rowIdx_user=3;
		
		
		sheet_pay.addMergedRegion(new CellRangeAddress(0, 2, 0, 9));
		Cell cell_header = sheet_pay.createRow(0).createCell(0);
		cell_header.setCellStyle(style_header);
		cell_header.setCellValue("等级付费额("+games.getCurrency()+")");
		
		sheet_user.addMergedRegion(new CellRangeAddress(0, 2, 0, 9));
		cell_header = sheet_user.createRow(0).createCell(0);
		cell_header.setCellStyle(style_header);
		cell_header.setCellValue("付费人数");
		
		
		Row headerRow_pay = sheet_pay.createRow(rowIdx_pay++);
		Row headerRow_user = sheet_user.createRow(rowIdx_user++);
		
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow_pay.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
			
			cell=headerRow_user.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (PaylevelAmountCntSourceVO sdr : cntPaySourceList) {
			Row row=sheet_pay.createRow(rowIdx_pay++);
			LevelPay lp = sdr.getLevelPay();
			Object data[] = {
					lp.getStatDay(),
					lp.getSourceOrClientName(),
					lp.getLp10(),
					lp.getLp50(),
					lp.getLp100(),
					lp.getLp200(),
					lp.getLp500(),
					lp.getLp1000(),
					lp.getLp2000(),
					lp.getLp2000up()
			};
			
			writeRow(row, data);
		}
		for (PaylevelAmountCntSourceVO sdr : cntUserSourceList) {
			Row row=sheet_user.createRow(rowIdx_user++);
			LevelPay lp = sdr.getLevelPay();
			Object data[] = {
					lp.getStatDay(),
					lp.getSourceOrClientName(),
					lp.getLp10(),
					lp.getLp50(),
					lp.getLp100(),
					lp.getLp200(),
					lp.getLp500(),
					lp.getLp1000(),
					lp.getLp2000(),
					lp.getLp2000up()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	private CellStyle herderStyle() {
		Font font = wb.createFont();  
        font.setFontHeightInPoints((short) 10);  
        font.setFontName("黑体");  
        font.setColor(HSSFColor.BLACK.index);  
        font.setBoldweight((short) 1.2);  
        
		CellStyle style_header = wb.createCellStyle();  
        style_header.setAlignment(CellStyle.ALIGN_CENTER);  
        style_header.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  
        style_header.setFont(font); //调用字体样式对象  
        style_header.setWrapText(true);  
        style_header.setFillBackgroundColor(HSSFColor.YELLOW.index);
		return style_header;
	}

	public void initHourSourceDailyReport(List<HourSourceDailyReport> match) {
		this.hourSourceDailyReports = match;
	}
	
	public void initHourLtvSourceDailyReport(List<HourLtvSourceDailyReport> match) {
		this.hourLtvSourceDailyReports = match;
	}
	
	public void initHourSourceRetentionDailyReport(List<HourSourceRetentionDailyReport> match) {
		this.hourSourceRetentionDailyReports = match;
	}
	
	public void initHourClientDailyReport(List<HourClientDailyReport> match) {
		this.hourClientDailyReports = match;
	}
	
	public void writeHourClientDailyReport() throws UnsupportedEncodingException {
		String fileName = "client_hour_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = {"日期","时间","服务器ID","新注册","活跃","老用户","最高在线人数",
				"平均在线人数","平均在线时长",
//				"次日留存",
				"付费人数",
				"付费额("+games.getCurrency()+")","交易笔数","付费率","ARPU("+games.getCurrency()+")",
				"ARPPU("+games.getCurrency()+")"};
//				"注册付费人数","注册付费额("+games.getCurrency()+")"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (HourClientDailyReport sdr : hourClientDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					sdr.getDay(),
					"0:00~"+sdr.getHour()+":"+(sdr.getMinute()>=10?sdr.getMinute():"0"+sdr.getMinute()),
					sdr.getClientid(),
					sdr.getDnu(),
					sdr.getDau(),
					sdr.getLoyalUser(),
					sdr.getPcu()==null?"0":sdr.getPcu(),
					sdr.getAcu()==null?"0":sdr.getAcu(),
					sdr.getAvgUserTime()==null?"0":sdr.getAvgUserTime(),
//					sdr.getD1()==null?0:Math.round(sdr.getD1()*100d*100d)/100d + "%",
					sdr.getPu(),
					sdr.getPayment()==null?0:Math.round((sdr.getPayment()/games.getRate())*100)/100,
					sdr.getPaymentTimes(),
					sdr.getPayRate()==null?0:Math.round(sdr.getPayRate()*100d*100d)/100d + "%",
					Math.round((sdr.getArpu()/games.getRate())*100d)/100d,
					Math.round((sdr.getArppu()/games.getRate())*100d)/100d
//					sdr.getInstallPu(),
//					Math.round((sdr.getInstallPayAmount()/games.getRate())*100d)/100d
			};
			writeRow(row, data);
		}
		this.flushAndClose();
	}
	
	public void writeHourLtvSourceDailyReport() throws UnsupportedEncodingException {
		String fileName = "source_ltv_hour_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = {"日期","时间","渠道","新注册","d0","d1","d2","d3","d4","d5","d6","d7"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (HourLtvSourceDailyReport sdr : hourLtvSourceDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					sdr.getDay(),
					"0:00~"+(sdr.getHour()+1)+":00",
					sdr.getSource(),
					sdr.getReg(),
					sdr.getD0(),
					sdr.getD1(),
					sdr.getD2(),
					sdr.getD3(),
					sdr.getD4(),
					sdr.getD5(),
					sdr.getD6(),
					sdr.getD7()
					
			};
			
			writeRow(row, data);
		}
		this.flushAndClose();
	}

	//实时分渠道留存
	public void writeHourSourceRetentionDailyReport() throws UnsupportedEncodingException {
		String fileName = "source_Retention_hour_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = {"日期","时间","渠道","新注册","d1","d2","d3","d4","d5","d6","d7"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (HourSourceRetentionDailyReport sdr : hourSourceRetentionDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					sdr.getDay(),
					"0:00~"+(sdr.getHour()+1)+":00",
					sdr.getSource(),
					sdr.getReg(),
					sdr.getD1(),
					sdr.getD2(),
					sdr.getD3(),
					sdr.getD4(),
					sdr.getD5(),
					sdr.getD6(),
					sdr.getD7()
					
			};
			
			writeRow(row, data);
		}
		this.flushAndClose();
	}
	public void writeHourSourceDailyReport() throws UnsupportedEncodingException {
		String fileName = "source_hour_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = {"日期","时间","渠道","新注册","激活设备数","活跃","老用户","最高在线人数",
				"平均在线人数","平均在线时长","次日留存","付费人数",
				"付费额("+games.getCurrency()+")","付费次数","付费率","ARPU("+games.getCurrency()+")",
				"ARPPU("+games.getCurrency()+")","新付费用户数","新付费额("+games.getCurrency()+")",
				"注册付费人数","注册付费额("+games.getCurrency()+")","新注册付费率"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (HourSourceDailyReport sdr : hourSourceDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					sdr.getDay(),
					"0:00~"+sdr.getHour()+":"+(sdr.getMinute()>=10 ? sdr.getMinute() : "0"+sdr.getMinute()),
					sdr.getSource(),
					sdr.getDnu(),
					sdr.getNewEquip(),
					sdr.getDau(),
					sdr.getLoyalUser(),
					sdr.getPcu()==null?"0":sdr.getPcu(),
					sdr.getAcu()==null?"0":sdr.getAcu(),
					sdr.getAvgUserTime()==null?"0":sdr.getAvgUserTime(),
					Math.round(sdr.getD1()*100d*100d)/100d + "%",
					sdr.getPu(),
					Math.round((sdr.getPayment()/games.getRate())*100)/100,
					sdr.getPaymentTimes(),
					Math.round(sdr.getPayRate()*100d*100d)/100d + "%",
					Math.round((sdr.getArpu()/games.getRate())*100d)/100d,
					Math.round((sdr.getArppu()/games.getRate())*100d)/100d,
					sdr.getNewPu(),
					Math.round((sdr.getNewPayment()/games.getRate())*100d)/100d,
					sdr.getInstallPu(),
					Math.round((sdr.getInstallPayAmount()/games.getRate())*100d)/100d,
					Math.round(sdr.getInstallPayRate()*100d*100d)/100d + "%"
			};
			
			writeRow(row, data);
		}
		this.flushAndClose();
	}

	public void writeViewSource() throws UnsupportedEncodingException {
		String fileName = "source_view.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道","新注册","创角数","活跃","收入("+games.getCurrency()+")","付费用户数"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (SourceDailyReport sdr : sourceDailyReports) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					sdr.getDay(),
					sdr.getSource(),
					sdr.getDnu(),
					sdr.getRoleChoice(),
					sdr.getDau(),
					Math.round((sdr.getPaymentAmount()/games.getRate())*100d)/100d,
					sdr.getPu()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	public void initRealTimeSourceList(List<RealtimeSourceReport> sourceReports,List<Platforms> platformds) {
		this.sourceReports = sourceReports;
		this.platforms = platformds;
	}

	/**
	 * //实时分渠道
	 * @throws UnsupportedEncodingException
	 */
	public void writeRealTimeSource() throws UnsupportedEncodingException {
		String fileName = "realtime_source_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道","广告位","点击总数",
				"点击总数(去重)","ip总数（去重）","新注册","付费人数",
				"付费额("+games.getCurrency()+")","付费率（%）"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (RealtimeSourceReport sdr : sourceReports) {
			Row row=sheet.createRow(rowIdx++);
			
			String ads[] = sdr.getCreateive().split("_");
			if(ads.length == 2){
				sdr.setAdPlace(ads[1]);
				sdr.setCreateive(findPlatformName(ads[0], platforms));
			}
			
			Object data[] = {
					sdr.getDs(),
					sdr.getCreateive(),
					sdr.getAdPlace(),
					sdr.getIdfa(),
					sdr.getDistinctIdfa(),
					sdr.getDistinctIp(),
					sdr.getInstall(),
					sdr.getInstallPayer(),
					Math.round((sdr.getInstallPayment()/games.getRate())*100d)/100d,
					(Math.round((Double.valueOf(sdr.getInstallPayer())/Double.valueOf(sdr.getInstall()))*10000d)/100d)+"%",
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}
	
	public static String findPlatformName(String code,List<Platforms> platforms){
		for(Platforms platform:platforms){
			if(code.equals(platform.getCode())){
				return platform.getName();
			}
		}
		return code;
	}

	public void writeChannelCpa(List<ChannelCpa> channelCpas, String name) throws UnsupportedEncodingException {
		String fileName = name+"_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道代码","渠道名称","真实激活",
				"真实注册","激活","注册","day1","day3","day7"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (ChannelCpa sdr : channelCpas) {
			Row row=sheet.createRow(rowIdx++);
			
			Object data[] = {
					sdr.getDs(),
					sdr.getChannelId(),
					sdr.getChannelName(),
					sdr.getRealActive(),
					sdr.getRealRegist(),
					//sdr.getKouliangActive(),
					//sdr.getKouliangRegist(),
					sdr.getDay1(),
					sdr.getDay3(),
					sdr.getDay7()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	public void writeChannelCps(List<SourceDailyReport> channelCpss, List<CostPerSourceDimension> dimensions,boolean isOutSider,String name) throws UnsupportedEncodingException {
		String fileName = name+"_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header1 = {"日期","渠道代码","渠道名称","付费人数","付费次数",
				"付费额("+games.getCurrency()+")"};
		String[] header2 = {"日期","渠道代码","渠道名称","付费人数","付费次数",
				"付费额("+games.getCurrency()+")","扣量付费人数","扣量付费次数",
				"扣量付费额("+games.getCurrency()+")"};
		String[] header3 = {"渠道代码","渠道名称","付费人数","付费次数",
				"付费额("+games.getCurrency()+")"};
		String[] header4 = {"渠道代码","渠道名称","付费人数","付费次数",
				"付费额("+games.getCurrency()+")","扣量付费人数","扣量付费次数",
				"扣量付费额("+games.getCurrency()+")"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		String[] header = "cps_channel".equals(name) ? isOutSider ? header3 : header4 : isOutSider ? header1 :header2;
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (SourceDailyReport sdr : channelCpss) {
			Row row=sheet.createRow(rowIdx++);
			
			CostPerSourceDimension dimension = getDimension(dimensions,sdr.getSource());
			
			if(isOutSider){
				if("cps_channel".equals(name)){
					Object data1[] = {
							sdr.getSource(),
							dimension == null ? sdr.getSource() : dimension.getSourceName(),
							dimension == null ? sdr.getPu() : Math.round(sdr.getPu()*dimension.getPayUserRate()),
							dimension == null ? sdr.getPaymentCnt() : Math.round(sdr.getPaymentCnt()*dimension.getPayTimesRate()),
						    dimension == null ? sdr.getPaymentAmount() : Math.round((sdr.getPaymentAmount()/games.getRate())*dimension.getPayUserRate() * 100)/100
					};
					writeRow(row, data1);
				}else{
					Object data1[] = {
							sdr.getDay(),
							sdr.getSource(),
							dimension == null ? sdr.getSource() : dimension.getSourceName(),
							dimension == null ? sdr.getPu() : Math.round(sdr.getPu()*dimension.getPayUserRate()),
							dimension == null ? sdr.getPaymentCnt() : Math.round(sdr.getPaymentCnt()*dimension.getPayTimesRate()),
						    dimension == null ? sdr.getPaymentAmount() : Math.round((sdr.getPaymentAmount()/games.getRate())*dimension.getPayUserRate() * 100)/100
					};
					writeRow(row, data1);
				}
				
			}else{
				if("cps_channel".equals(name)){
					Object data2[] = {
							sdr.getSource(),
							dimension == null ? sdr.getSource() : dimension.getSourceName(),
							sdr.getPu(),
							sdr.getPaymentCnt(),
							Math.round(sdr.getPaymentAmount()/games.getRate()*100)/100,
							dimension == null ? sdr.getPu() : Math.round(sdr.getPu()*dimension.getPayUserRate()),
							dimension == null ? sdr.getPaymentCnt() : Math.round(sdr.getPaymentCnt()*dimension.getPayTimesRate()),
						    dimension == null ? sdr.getPaymentAmount() : Math.round((sdr.getPaymentAmount()/games.getRate())*dimension.getPayUserRate() * 100)/100
					};
					
					writeRow(row, data2);
				}else{
					Object data2[] = {
							sdr.getDay(),
							sdr.getSource(),
							dimension == null ? sdr.getSource() : dimension.getSourceName(),
							sdr.getPu(),
							sdr.getPaymentCnt(),
							Math.round(sdr.getPaymentAmount()/games.getRate()*100)/100,
							dimension == null ? sdr.getPu() : Math.round(sdr.getPu()*dimension.getPayUserRate()),
							dimension == null ? sdr.getPaymentCnt() : Math.round(sdr.getPaymentCnt()*dimension.getPayTimesRate()),
						    dimension == null ? sdr.getPaymentAmount() : Math.round((sdr.getPaymentAmount()/games.getRate())*dimension.getPayUserRate() * 100)/100
					};
					writeRow(row, data2);
				}
			}
		}
		flushAndClose();
	}

	public static CostPerSourceDimension getDimension(List<CostPerSourceDimension> dimensions, String source) {
		for(CostPerSourceDimension dimension:dimensions){
			if(source.equals(dimension.getSourceCode())){
				return dimension;
			}
		}
		return null;
	}

	public void writeRealTime(List<RealTimeNoClientResult> match) throws UnsupportedEncodingException {
		String fileName = "realTime.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] header = {"日期","新注册(含滚服)","活跃","收入("+games.getCurrency()+")",
				"ARPU("+games.getCurrency()+")","ARPPU("+games.getCurrency()+")","付费人数","付费次数"};
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (RealTimeNoClientResult rtr : match) {
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					rtr.getDs(),
					rtr.getInstall(),
					rtr.getDau(),
					(rtr.getAmount() == null || rtr.getAmount() == 0) ? 0 : Math.round(rtr.getAmount()/games.getRate()*100d)/100d,
					(rtr.getAmount() == null || rtr.getDau() == null || rtr.getDau() == 0) ? 0 : Math.round(rtr.getAmount()/rtr.getDau()/games.getRate()*100d)/100d,
					(rtr.getAmount() == null || rtr.getPayment() == null || rtr.getPayment() == 0) ? 0 : Math.round(rtr.getAmount()/rtr.getPayment()/games.getRate()*100d)/100d,
					rtr.getPayment(),
					rtr.getPaytimes()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	// 晕死，先这样吧
	public void writeHourRealTime(List<ReportingEachTimeNC> match) throws UnsupportedEncodingException {
		
		Calendar calendar = Calendar.getInstance();
		String today = DateUtils.getDateStr(calendar);
        
        // 昨天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
        String yesterday = DateUtils.getDateStr(calendar);
        
        // 前7天
        calendar.add(Calendar.DAY_OF_MONTH, -6);
        String last7Day = DateUtils.getDateStr(calendar);
        
        // 前30天
        calendar.add(Calendar.DAY_OF_MONTH, -23);
        String lastMonthSToday = DateUtils.getDateStr(calendar);
        
        // 依次为："今日","昨日","前7日","前30日","预计今日"
        double[][] countDatas = {{0,0,0,0,},{0,0,0,0,},{0,0,0,0,},{0,0,0,0,},{0,0,0,0,}};
        int[][] installDatas = {new int[24],new int[24],new int[24],new int[24]};
        int[][] dauDatas = {new int[24],new int[24],new int[24],new int[24]};
        double[][] amountDatas = {new double[24],new double[24],new double[24],new double[24]};
        double[][] arpuDatas = {new double[24],new double[24],new double[24],new double[24]};
        double[][] arppuDatas = {new double[24],new double[24],new double[24],new double[24]};
        
	    int[][] predictDataArr = new int[24][1];
        
		for(ReportingEachTimeNC eachTimeNC:match){
       	 if(eachTimeNC.getDs().equals(today)){
	   		 predictDataArr[Integer.valueOf(eachTimeNC.getTimes())][0] = eachTimeNC.getIspredict();
	   		 if(eachTimeNC.getIspredict() == 0){
	   			 processData(countDatas[4],null,null,null,null,null,eachTimeNC);
	   			 processData(countDatas[0],installDatas[0],dauDatas[0],amountDatas[0],arpuDatas[0],arppuDatas[0],eachTimeNC);
	   		 }else{
	   			 processData(countDatas[4],null,null,null,null,null,eachTimeNC);
	   			 processData(null,installDatas[0],dauDatas[0],amountDatas[0],arpuDatas[0],arppuDatas[0],eachTimeNC);
	   		 }
       	 }else if(eachTimeNC.getDs().equals(yesterday)){
       		 processData(countDatas[1],installDatas[1],dauDatas[1],amountDatas[1],arpuDatas[1],arppuDatas[1],eachTimeNC);
       	 }else if(eachTimeNC.getDs().equals(last7Day)){
       		 processData(countDatas[2],installDatas[2],dauDatas[2],amountDatas[2],arpuDatas[2],arppuDatas[2],eachTimeNC);
       	 }else if(eachTimeNC.getDs().equals(lastMonthSToday)){
       		 processData(countDatas[3],installDatas[3],dauDatas[3],amountDatas[3],arpuDatas[3],arppuDatas[3],eachTimeNC);
       	 }
        }
		
		String fileName = "hour_realTime.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		String[] trs= {"今日","昨日","前7日","前30日","预计今日"};
		String[] header = {"","累计新注册用户数（含滚服）","累计活跃用户数","累计付费("+games.getCurrency()+")",
				"平均ARPU("+games.getCurrency()+")","平均ARPPU("+games.getCurrency()+")"};
		Sheet sheet=wb.createSheet("count");
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for(int i=0;i<trs.length;i++){
			Row row=sheet.createRow(rowIdx++);
			Object data[] = {
					trs[i],
					new Double(countDatas[i][0]).intValue(),
					new Double(countDatas[i][1]).intValue(),
					Math.round(countDatas[i][2]*100d)/100d,
					Math.round(countDatas[i][2]/countDatas[i][1]*100d)/100d,
					Math.round(countDatas[i][2]/countDatas[i][3]*100d)/100d
			};
			writeRow(row, data);
		}
		
		writedatas(installDatas, predictDataArr, "install","小时/累计新注册用户数（含滚服）");
		writedatas(dauDatas, predictDataArr, "dau","小时/累计活跃用户数");
		writedatas(amountDatas, predictDataArr, "amount","小时/累计付费("+games.getCurrency()+")");
		writedatas(arpuDatas, predictDataArr, "arpu","小时/平均ARPU("+games.getCurrency()+")");
		writedatas(arppuDatas, predictDataArr, "arppu","小时/平均ARPPU("+games.getCurrency()+")");
		flushAndClose();
	}

	private void writedatas(double[][] amountDatas, int[][] predictDataArr,
			String name, String headName) {
		String[] header1 = {headName,"今日","昨日","前7日","前30日"};
		Sheet sheet1=wb.createSheet(name);
		Row headerRow1=sheet1.createRow(0);
		for (int i = 0; i < header1.length; i++) {
			Cell cell=headerRow1.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header1[i]);
		}
		
		for(int j=0;j<24;j++){
			Row row=sheet1.createRow(j+1);
			Object data[] = {
					j,
					predictDataArr[j][0] == 1 ? Math.round(amountDatas[0][j]*100d)/100d+"(预测)" : Math.round(amountDatas[0][j]*100d)/100d,
					Math.round(amountDatas[1][j]*100d)/100d,
					Math.round(amountDatas[2][j]*100d)/100d,
					Math.round(amountDatas[3][j]*100d)/100d
			};
			writeRow(row, data);
		}
	}

	private void writedatas(int[][] installDatas, int[][] predictDataArr,String name,String headName) {
		String[] header1 = {headName,"今日","昨日","前7日","前30日"};
		Sheet sheet1=wb.createSheet(name);
		Row headerRow1=sheet1.createRow(0);
		for (int i = 0; i < header1.length; i++) {
			Cell cell=headerRow1.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header1[i]);
		}
		
		for(int j=0;j<24;j++){
			Row row=sheet1.createRow(j+1);
			Object data[] = {
					j,
					predictDataArr[j][0] == 1 ? installDatas[0][j]+"(预测)" : installDatas[0][j],
					installDatas[0][j],
					installDatas[1][j],
					installDatas[2][j]
			};
			writeRow(row, data);
		}
	}
	
	private void processData(double[] yujiTodayCountArr, int[] installArr, int[] dauArr,
			double[] amountArr,double[] arpuArr, double[] arppuArr,ReportingEachTimeNC eachTimeNC){
		if(yujiTodayCountArr != null){
			yujiTodayCountArr[0] += eachTimeNC.getInstall();
	   		yujiTodayCountArr[1] += eachTimeNC.getDau();
	   		yujiTodayCountArr[2] += eachTimeNC.getAmount()/games.getRate();
	   		yujiTodayCountArr[3] += eachTimeNC.getPayUsers();
		}
		 
   		if(installArr != null){
   			int times = Integer.valueOf(eachTimeNC.getTimes());
   			installArr[times]=(eachTimeNC.getInstall());
      		dauArr[times]=(eachTimeNC.getDau());
      		amountArr[times]=eachTimeNC.getAmount()/games.getRate();
      		if(eachTimeNC.getDau() == 0){
      			arpuArr[times]=0;
      		}else{
      			arpuArr[times]=eachTimeNC.getAmount()/games.getRate()/eachTimeNC.getDau();
      		}
      		if(eachTimeNC.getPayUsers() == 0){
      			arppuArr[times] = 0;
      		}else{
      			arppuArr[times]=eachTimeNC.getAmount()/games.getRate()/eachTimeNC.getPayUsers();
      		}
   	    }
	}

	public void writeWhaleUser(List<WhaleUser> selectList) throws UnsupportedEncodingException {
		String fileName = "whale_user_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"用户ID","安装日期","渠道","首次付费日期","首次付费额("+games.getCurrency()+")",
				"总付费("+games.getCurrency()+")","最近付费日期","未充值天数",
				"最近登录日期","未登录天数","用户等级","首次登录服务器","最近登录服务器",
				"服务器ID","角色ID","角色名称","vip等级","更新日期"};
		
		
		Sheet sheet=wb.createSheet("whaleUser");
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		CellStyle cellStyle = wb.createCellStyle();
		//cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
		//cellStyle.setFillForegroundColor(HSSFColor.RED.index);
		Font font = wb.createFont();
		font.setBold(true);
		font.setColor(Font.COLOR_RED);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// //居中显示
//		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		CellStyle cellStyleBlue = wb.createCellStyle();
		Font fontBlue = wb.createFont();
		fontBlue.setBold(true);
		fontBlue.setColor(HSSFColor.BLUE.index);
		cellStyleBlue.setFont(fontBlue);
		cellStyleBlue.setAlignment(HSSFCellStyle.ALIGN_CENTER);// //居中显示
		
		for (WhaleUser sdr : selectList) {
			Row row=sheet.createRow(rowIdx++);
			
			long diffDau = DateUtils.getDateDiff(sdr.getDs(),sdr.getLastDauTime());
			long diffPay = DateUtils.getDateDiff(sdr.getDs(),sdr.getLastPayTime());
			
			
			Object data[] = {
					sdr.getUserid(),
					sdr.getInstallDate(),
					sdr.getCreative(),
					sdr.getFirstPayTime(),
					sdr.getFirstPayAmount(),
					sdr.getTotalPayAmount(),
					sdr.getLastPayTime(),
					diffPay,
					sdr.getLastDauTime(),
					diffDau,
					sdr.getLevel(),
					sdr.getFirstServer(),
					sdr.getLastServer(),
					sdr.getClientid(),
					sdr.getRoleid(),
					sdr.getRoleName(),
					sdr.getVipLevel(),
					sdr.getDs()
			};
			
			
			if(diffDau >= 3 && diffPay >= 14){
				writeRowHavaStyle(row, data,cellStyle);
			}else if(diffDau >= 3){
				writeRowHavaStyle(row, data,cellStyle);
			}else if(diffPay >= 14){
				writeRowHavaStyle(row, data,cellStyleBlue);
			}else{
				writeRow(row, data);
			}
		}
		flushAndClose();
	}
	
	private void writeRowHavaStyle(Row row, Object[] data,CellStyle cellStyle) {
		for (int i = 0; i < data.length; i++) {
			Cell cell=row.createCell(i);
			cell.setCellStyle(cellStyle);
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
	
	public void writeGameOpMonitorStandard(List<AdminOpMonitorStandard> selectList) throws UnsupportedEncodingException {
		String fileName = "op_monitor_report.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","服务器ID","操作类型代码","操作人名称","道具ID",
     	"道具名称","道具数量","被操作人名称","被操作人角色","操作日期","申请人名称","用途"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (AdminOpMonitorStandard sdr : selectList) {
			Row row=sheet.createRow(rowIdx++);
			
			Object data[] = {
					sdr.getMonth(),
					sdr.getClientid(),
					sdr.getOpCode(),
					sdr.getOpUserName(),
					sdr.getItemId(),
					sdr.getItemName(),
					sdr.getItemNum(),
					sdr.getBeOperatedUserName(),
					sdr.getBeOperatedUserRole(),
					sdr.getOpDate(),
					sdr.getApplyUserName(),
					sdr.getUseDesc()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	public void writeEquipVersionDau(List<EquipVersionDau> list) throws UnsupportedEncodingException {
		String fileName = "equip_version_dau.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","版本","dau"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (EquipVersionDau versionDau : list) {
			Row row=sheet.createRow(rowIdx++);
			
			Object data[] = {
					versionDau.getDs(),
					versionDau.getVersion(),
					versionDau.getDau()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	public void writeRetention(String type,List<EquipLtv> list) throws UnsupportedEncodingException {
		String fileName = "equip_version_dau.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header_all = {"日期","新增设备","d1","d2","d3","d4","d5","d6","d7"};
		String[] header_source = {"日期","新增设备","渠道","d1","d2","d3","d4","d5","d6","d7"};
		String[] header_model = {"日期","新增设备","机型","d1","d2","d3","d4","d5","d6","d7"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		
		String[] header = "source".equals(type) ? header_source : "model".equals(type) ? header_model : header_all;
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		if("all".equals(type)){
			for (EquipLtv equipLtv : list) {
				Row row=sheet.createRow(rowIdx++);
				
				Object data[] = {
						equipLtv.getInstallDate(),
						equipLtv.getNewEquip(),
						equipLtv.getD1() == null ? "-" : Math.round(equipLtv.getD1() * 100d * 100d)/100d + "%",
								equipLtv.getD2() == null ? "-" : Math.round(equipLtv.getD2() * 100d * 100d)/100d + "%",
								equipLtv.getD3() == null ? "-" : Math.round(equipLtv.getD3() * 100d * 100d)/100d + "%",
								equipLtv.getD4() == null ? "-" : Math.round(equipLtv.getD4() * 100d * 100d)/100d + "%",
								equipLtv.getD5() == null ? "-" : Math.round(equipLtv.getD5() * 100d * 100d)/100d + "%",
								equipLtv.getD6() == null ? "-" : Math.round(equipLtv.getD6() * 100d * 100d)/100d + "%",
								equipLtv.getD7() == null ? "-" : Math.round(equipLtv.getD7() * 100d * 100d)/100d + "%"
				};
				
				writeRow(row, data);
			}
		}else{
			for (EquipLtv equipLtv : list) {
				Row row=sheet.createRow(rowIdx++);
				
				Object data[] = {
						equipLtv.getInstallDate(),
						equipLtv.getNewEquip(),
						equipLtv.getSourceOrModel(),
						equipLtv.getD1() == null ? "-" : Math.round(equipLtv.getD1() * 100d * 100d)/100d + "%",
						equipLtv.getD2() == null ? "-" : Math.round(equipLtv.getD2() * 100d * 100d)/100d + "%",
						equipLtv.getD3() == null ? "-" : Math.round(equipLtv.getD3() * 100d * 100d)/100d + "%",
						equipLtv.getD4() == null ? "-" : Math.round(equipLtv.getD4() * 100d * 100d)/100d + "%",
						equipLtv.getD5() == null ? "-" : Math.round(equipLtv.getD5() * 100d * 100d)/100d + "%",
						equipLtv.getD6() == null ? "-" : Math.round(equipLtv.getD6() * 100d * 100d)/100d + "%",
						equipLtv.getD7() == null ? "-" : Math.round(equipLtv.getD7() * 100d * 100d)/100d + "%"
				};
				
				writeRow(row, data);
			}
		}
		
		flushAndClose();
	}

	public void writeEquipSourceDau(List<EquipSourceDay> list) throws UnsupportedEncodingException {
		String fileName = "equip_source_day.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","渠道","活跃设备","激活设备","激活且安装设备","活跃老设备","激活率","卸载设备"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (EquipSourceDay eq : list) {
			Row row=sheet.createRow(rowIdx++);
			
			Object data[] = {
					eq.getDs(),
					eq.getSource(),
					eq.getDau(),
					eq.getNewEquip(),
					eq.getInstall(),
					eq.getDau()-eq.getNewEquip(),
					Math.round(Double.valueOf(eq.getInstall())/Double.valueOf(eq.getNewEquip())*10000d)/100d+"%",
					eq.getUninstall()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	public void writeEquipModelDau(List<EquipModelDay> list) throws UnsupportedEncodingException {
		String fileName = "equip_model_day.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","机型","活跃设备","激活设备","激活且安装设备","活跃老设备","激活率","卸载设备"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (EquipModelDay eq : list) {
			Row row=sheet.createRow(rowIdx++);
			
			Object data[] = {
					eq.getDs(),
					eq.getModel(),
					eq.getDau(),
					eq.getNewEquip(),
					eq.getInstall(),
					eq.getDau()-eq.getNewEquip(),
					Math.round(Double.valueOf(eq.getInstall())/Double.valueOf(eq.getNewEquip())*10000d)/100d+"%",
					eq.getUninstall()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	public void writeEquipDau(List<EquipDay> list) throws UnsupportedEncodingException {
		String fileName = "equip_day.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","活跃设备","激活设备","激活且安装设备","活跃老设备","激活率","卸载设备"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (EquipDay eq : list) {
			Row row=sheet.createRow(rowIdx++);
			
			Object data[] = {
					eq.getDs(),
					eq.getDau(),
					eq.getNewEquip(),
					eq.getInstall(),
					eq.getDau()-eq.getNewEquip(),
					Math.round(Double.valueOf(eq.getInstall())/Double.valueOf(eq.getNewEquip())*10000d)/100d+"%",
					eq.getUninstall()
			};
			
			writeRow(row, data);
		}
		flushAndClose();
	}

	public void writeRetention(List<PayUserRetentionLtvVO> selectList) throws UnsupportedEncodingException {
		String fileName = "equip_day.xlsx";
		response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
		String[] header = {"日期","首付人数",
						"D1","D2","D3","D4","D5","D6","D7","D8","D9","D10","D11",
						"D12","D13","D14","D15","D16","D17","D18","D19","D20","D21",
						"D22","D23","D24","D25","D26","D27","D28","D29","D30","D31",
						"D32","D33","D34","D35","D36","D37","D38","D39","D40","D41",
						"D42","D43","D44","D45","D46","D47","D48","D49","D50","D51",
						"D52","D53","D54","D55","D56","D57","D58","D59","D60","D61",
						"D62","D63","D64","D65","D66","D67","D68","D69","D70","D71",
						"D72","D73","D74","D75","D76","D77","D78","D79","D80","D81",
						"D82","D83","D84","D85","D86","D87","D88","D89","D90"};
		
		
		Sheet sheet=wb.createSheet();
		int rowIdx=0;
		Row headerRow=sheet.createRow(rowIdx++);
		for (int i = 0; i < header.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(header[i]);
		}
		
		for (PayUserRetentionLtvVO eq : selectList) {
			Row row=sheet.createRow(rowIdx++);
			
			List<Object> data = new ArrayList<Object>();
			data.add(eq.getEntity().getFirstpayDate());
			data.add(eq.getEntity().getFirstpayUsers());
			for(Double d:eq.getDoubleDayDatas()){
				data.add(d);
			}
			
			writeRow(row, data.toArray());
		}
		flushAndClose();
		
	}

}
