package com.hoolai.loader.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.hoolai.loader.exception.PersistenceException;
import com.hoolai.loader.util.DBUtil;
import com.hoolai.loader.util.StringUtil;

/**
 * 
 * @author ruijie
 * @date 2014-4-2
 * @version V1.0
 */
public class RelationDao {
	
	private static Logger log = Logger.getLogger(RelationDao.class);
	
	private static RelationDao dao = new RelationDao();
	
	private DBUtil dbutil = DBUtil.newInstance();
	
	private RelationDao() {	}

	public static RelationDao newInstance() {
		return dao;
	}
	
	/**
	 * @category  如果是改版之前，
	 * 即：1  不存在 message_click，根据_ 分组后，数组长度要  <5
	 *    2  存在 message_click时，去掉这个后， 数组长度要 <4 
	 *     那么在路径中存在着
	 */
	public void addRelation(String path) {
		if(!StringUtil.isRevision(path)){
			String param[] = StringUtil.getParams(path, false);
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			try {
				conn = dbutil.getConn();
				
				pstmt = dbutil.getStatement(conn, "select sn_id from d_Relationship where sn_id = ? and client_id = ? and game_id = ?");
				pstmt.setInt(1, Integer.parseInt(param[1]));
				pstmt.setInt(2, Integer.parseInt(param[2]));
				pstmt.setInt(3, Integer.parseInt(param[3]));
				
				if(!pstmt.executeQuery().next()){
					pstmt = dbutil.getStatement(conn, "insert into d_Relationship (sn_id, client_id, game_id, ds) values (?, ?, ?, now())");
					pstmt.setInt(1, Integer.parseInt(param[1]));
					pstmt.setInt(2, Integer.parseInt(param[2]));
					pstmt.setInt(3, Integer.parseInt(param[3]));
					
					pstmt.executeUpdate();
					
					log.info("relation : [ snid = " + param[1] + ", clientid = " + param[2] + ", gameid = " + param[3] + " ]");
				}
			} catch (Exception e) {
				throw new PersistenceException(e);
			} finally {
				dbutil.release(null, pstmt, conn);
			}
		}
	}
}