<%@page import="com.hoolai.bi.tracking.GameHeader"%>
<%@page import="java.io.File"%>
<%@page import="com.hoolai.bi.tracking.clients.rollfile.RollFileEvent"%>
<%@page import="java.util.concurrent.BlockingQueue"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.hoolai.bi.tracking.clients.rollfile.RollFileEventSerializer"%>
<%@page import="java.util.concurrent.ConcurrentHashMap"%>
<%@page import="com.hoolai.bi.tracking.clients.rollfile.RollFileEventDispather"%>
<%@page import="com.hoolai.bi.tracking.clients.rollfile.RollFileClient"%>
<%@page import="com.hoolai.bi.tracking.service.impl.RollFileTrackingServiceImpl"%>
<%@page import="com.dw.services.TrackServices"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
<%@page import="java.util.ArrayList"%>
<%@page import="com.hoolai.core.Constants"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.hoolai.bi.tracking.web.AdTrackingCallbackManager"%>
<%@page import="com.hoolai.bi.tracking.log.StatsManager"%>

<%@page import="java.util.List"%>
<%@page import="java.lang.reflect.Field"%>
	
<%

	List<Map<String,Object>> allInfoList=new ArrayList<Map<String,Object>>();

	try{
		Field statsManagerField = StatsManager.class.getDeclaredField("ADTRACKING_CALLBACK_MANAGER");
		statsManagerField.setAccessible(true);
		AdTrackingCallbackManager adTrackingCallbackManager=(AdTrackingCallbackManager)statsManagerField.get(null);
		Map<String,Object> adTrackingInfoMap=new HashMap<String,Object>();
		adTrackingInfoMap.put("adTrackingOpen", Constants.AD_TRACKING_CALLBACK_ISOPEN);
		adTrackingInfoMap.put("adTrackingUrl", Constants.AD_TRACKING_CALLBACK_URL);
		adTrackingInfoMap.put("cacheSize", adTrackingCallbackManager.getCacheSize());
		allInfoList.add(adTrackingInfoMap);
	}catch(Exception e){
		
	}
	
	try{
		Field trackingServiceField = TrackServices.class.getDeclaredField("TRACKING_SERVICE");
		trackingServiceField.setAccessible(true);
		RollFileTrackingServiceImpl rollFileTrackingServiceImpl=(RollFileTrackingServiceImpl)trackingServiceField.get(null);
		
		Field rollFileClientField = RollFileTrackingServiceImpl.class.getDeclaredField("rollFileClient");
		rollFileClientField.setAccessible(true);
		RollFileClient rollFileClient=(RollFileClient)rollFileClientField.get(rollFileTrackingServiceImpl);
		
		Field rollFileEventDispatherField = RollFileClient.class.getDeclaredField("rollFileEventDispather");
		rollFileEventDispatherField.setAccessible(true);
		RollFileEventDispather rollFileEventDispather=(RollFileEventDispather)rollFileEventDispatherField.get(rollFileClient);
		Field gameEventSerializerField = RollFileEventDispather.class.getDeclaredField("gameEventSerializerMap");
		gameEventSerializerField.setAccessible(true);
		ConcurrentHashMap<String, RollFileEventSerializer> gameEventSerializerMap=(ConcurrentHashMap<String, RollFileEventSerializer>)gameEventSerializerField.get(rollFileEventDispather);
		for (Entry<String, RollFileEventSerializer>  entry: gameEventSerializerMap.entrySet()) {
			RollFileEventSerializer rollFileEventSerializer=entry.getValue();
			
			Field rollFileEventSerializerBlockingQueueField = RollFileEventSerializer.class.getDeclaredField("blockingQueue");
			rollFileEventSerializerBlockingQueueField.setAccessible(true);
			BlockingQueue<RollFileEvent> rollFileEventSerializerBlockingQueue=(BlockingQueue<RollFileEvent>)rollFileEventSerializerBlockingQueueField.get(rollFileEventSerializer);
			
			Field rollFileEventSerializerCurrentFileField = RollFileEventSerializer.class.getDeclaredField("currentFile");
			rollFileEventSerializerCurrentFileField.setAccessible(true);
			File rollFileEventSerializerCurrentFile=(File)rollFileEventSerializerCurrentFileField.get(rollFileEventSerializer);
			
			Field rollFileEventSerializerGameHeaderField = RollFileEventSerializer.class.getDeclaredField("gameHeader");
			rollFileEventSerializerGameHeaderField.setAccessible(true);
			GameHeader gameHeader=(GameHeader)rollFileEventSerializerGameHeaderField.get(rollFileEventSerializer);
			
			Map<String,Object> infoMapTmp=new HashMap<String,Object>();
			infoMapTmp.put("categoryName", gameHeader.getHeaderId()+"-rollFileEventSerializer");
			infoMapTmp.put("cacheSize", rollFileEventSerializerBlockingQueue.size());
			infoMapTmp.put("currentFile", rollFileEventSerializerCurrentFile.getAbsolutePath());
			infoMapTmp.put("currentFile_length", rollFileEventSerializerCurrentFile.length());
			
			allInfoList.add(infoMapTmp);
		}
	}catch(Exception e){
		
	}
	
	
	request.setAttribute("allInfoList", allInfoList);

%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<body>
		TrackingServer infos:<br/>
		<c:forEach var="infoMap" items="${allInfoList}">
			<table border="1">
				 <c:forEach items="${infoMap}" var="info">
				 	  <tr>
				 	  	<td>${info.key}:</td>
				 	  	<td>${info.value}</td>
				 	  </tr>
				 </c:forEach>
			</table>
			<br/>
		</c:forEach>
		
	</body>
</html>