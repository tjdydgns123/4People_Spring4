package com.board;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vo.boardVO;



public class BoardDao {
	SqlSessionFactory  sqlMap = null;
	//
	SqlSession session = null;
	Logger logger = Logger.getLogger(BoardDao.class);
	public void BoardIns(Map<String, Object> pMap) {
		try {
			String resource = "com/mybatis/Configuration.xml";
			Reader reader =null;
			reader = Resources.getResourceAsReader(resource);
			sqlMap = new SqlSessionFactoryBuilder().build(reader);
			session = sqlMap.openSession(); 
			session.insert("BoardIns", pMap);
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<Map<String,Object>> BoardSel(Map<String, Object> pMap) {
		
		List<Map<String,Object>> BoardList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> TeamList = null;
		try {
			String resource = "com/mybatis/Configuration.xml";
			Reader reader =null;
			reader = Resources.getResourceAsReader(resource);
			sqlMap = new SqlSessionFactoryBuilder().build(reader);
			session = sqlMap.openSession();
//			boardVO BoardVO = new boardVO();
//				BoardVO.setMem_id(pMap.get("mem_id").toString());
//				logger.info(BoardVO.getMem_id());
//			session.sele
//			List<boardVO> test = session.selectList("BoardSel",BoardVO);
//			logger.info(test);
//			session.select
//			logger.info(test.size());
//			Map<String,Object> hMap = new HashMap<String,Object>();
//			hMap.put("BoardSel", session.selectList("BoardSel",pMap));
////			hMap.put("TeamSel", session.selectList("TeamSel",pMap));
//			Map<String,Object> hMap =  new HashMap<String,Object>();
			Gson g = new Gson();
			JsonParser jsonParser = new JsonParser();
			
			session.selectList("BoardSel", pMap);
//			logger.info(pMap.get("boardMap"));
//		  jsonParser.parse(g.toJson(pMap.get("boardMap")));
//			JsonElement rootObejct = jsonParser.parse(g.toJson(pMap.get("boardMap")));
//			logger.info(rootObejct.getAsJsonObject().get("team_code"));
//			logger.info(rootObejct.getAsJsonObject().get("board_title"));

			
//			JsonObject jsonObject = (JsonObject) obj;
//			JsonObject dataObject = (JsonObject) jsonObject.get("data");
			 BoardList.add(pMap);
//			logger.info(g.toJson(pMap));
//					logger.info(BoardList.size());
//					("BoardSel",pMap);
//					logger.info(BoardVO.getBoard_title());
					//logger.info(pMap.get("boardMap"));
					//logger.info(BoardList.size());
//					Iterator<String> keys =((HashMap)pMap.get("teamMap")).keySet().iterator();
//					while(keys.hasNext()) {
//						String key = keys.next();
//						logger.info("key :"+key+ "value : "+ ((HashMap)pMap.get("teamMap")).get(0));
//					}
//			logger.info("mem_id=: "+pMap.get("mem_id").);
			
//			logger.info(((Map<String,Object>)pMap.get("boardMap")).get("team_code"));
			
//			logger.info(BoardList.size());
//			BoardList.add(hMap);
//			logger.info(BoardList);
//			for(int i=0;i<pMap.size();i++) {
//				
//				logger.info(pMap.get(i));
//				logger.info(pMap.get("TEAM_CODE"));
//			}
//			logger.info(BoardList.size());
//			for(Map<String,Object> lMap:BoardList) {
//				for(String key:lMap.keySet()) {
//					logger.info("key=="+key+"  value=="+lMap.get(key));
//					( (Map<String, Object>) lMap.get( key )).get( "TEAM_CODE" );
//				}
//			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		return BoardList;
	}
	public void BoardUpd(Map<String, Object> pMap) {
		try {
			String resource = "com/mybatis/Configuration.xml";
			Reader reader =null;
			reader = Resources.getResourceAsReader(resource);
			sqlMap = new SqlSessionFactoryBuilder().build(reader);
			session = sqlMap.openSession(); 
			session.update("BoardUpd", pMap);
			logger.info("성공?");
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
