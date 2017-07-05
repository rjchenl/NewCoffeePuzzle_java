package com.store.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.util.LinkedHashSet;
import java.util.Set;
import com.spndcoffee.model.SpndcoffeeVO;
import com.spndcoffeelist.model.SpndcoffeelistVO;
import com.rate_n_rev.model.Rate_n_revVO;
import com.news.model.NewsVO;
import com.store_tag.model.Store_tagVO;
import com.orderlist.model.OrderlistVO;
import com.reply.model.ReplyVO;
import com.activity.model.ActivityVO;
import com.fav_store.model.Fav_storeVO;
import com.photo_store.model.Photo_storeVO;
import com.rept_store.model.Rept_storeVO;
import com.rjchenl.product.model.ProductVO;

public class StoreJDBCDAO implements StoreDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO STORE (STORE_ID,STORE_ACCT,STORE_PWD,STORE_NAME,STORE_TEL,STORE_ADD,STORE_EMAIL,LONGITUDE,LATITUDE,STORE_POINTS,STORE_CPSE,MIN_ORDER,IS_MIN_ORDER,IS_WIFI,IS_PLUG,IS_SINGLE_ORGN,IS_DESSERT,IS_MEAL,IS_TIME_LIMIT,MON_ISOPEN,MON_OPEN,MON_CLOSE,TUE_ISOPEN,TUE_OPEN,TUE_CLOSE,WED_ISOPEN,WED_OPEN,WED_CLOSE,THU_ISOPEN,THU_OPEN,THU_CLOSE,FRI_ISOPEN,FRI_OPEN,FRI_CLOSE,SAT_ISOPEN,SAT_OPEN,SAT_CLOSE,SUN_ISOPEN,SUN_OPEN,SUN_CLOSE,STORE_IMG,STORE_PASS,STORE_AUTHENTICATION,STORE_VALIDATECODE) VALUES ('STORE' || LPAD(to_char(STORE_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT STORE_ID,STORE_ACCT,STORE_PWD,STORE_NAME,STORE_TEL,STORE_ADD,STORE_EMAIL,LONGITUDE,LATITUDE,STORE_POINTS,STORE_CPSE,MIN_ORDER,IS_MIN_ORDER,IS_WIFI,IS_PLUG,IS_SINGLE_ORGN,IS_DESSERT,IS_MEAL,IS_TIME_LIMIT,MON_ISOPEN,MON_OPEN,MON_CLOSE,TUE_ISOPEN,TUE_OPEN,TUE_CLOSE,WED_ISOPEN,WED_OPEN,WED_CLOSE,THU_ISOPEN,THU_OPEN,THU_CLOSE,FRI_ISOPEN,FRI_OPEN,FRI_CLOSE,SAT_ISOPEN,SAT_OPEN,SAT_CLOSE,SUN_ISOPEN,SUN_OPEN,SUN_CLOSE,STORE_IMG,STORE_PASS,STORE_AUTHENTICATION,STORE_VALIDATECODE FROM STORE ORDER BY STORE_ID";
	private static final String GET_ONE_STMT = "SELECT STORE_ID,STORE_ACCT,STORE_PWD,STORE_NAME,STORE_TEL,STORE_ADD,STORE_EMAIL,LONGITUDE,LATITUDE,STORE_POINTS,STORE_CPSE,MIN_ORDER,IS_MIN_ORDER,IS_WIFI,IS_PLUG,IS_SINGLE_ORGN,IS_DESSERT,IS_MEAL,IS_TIME_LIMIT,MON_ISOPEN,MON_OPEN,MON_CLOSE,TUE_ISOPEN,TUE_OPEN,TUE_CLOSE,WED_ISOPEN,WED_OPEN,WED_CLOSE,THU_ISOPEN,THU_OPEN,THU_CLOSE,FRI_ISOPEN,FRI_OPEN,FRI_CLOSE,SAT_ISOPEN,SAT_OPEN,SAT_CLOSE,SUN_ISOPEN,SUN_OPEN,SUN_CLOSE,STORE_IMG,STORE_PASS,STORE_AUTHENTICATION,STORE_VALIDATECODE FROM STORE WHERE STORE_ID = ?";
	private static final String GET_Spndcoffees_ByStore_id_STMT = "SELECT SPND_ID,STORE_ID,SPND_NAME,SPND_PROD,to_char(SPND_ENDDATE,'yyyy-mm-dd') SPND_ENDDATE,SPND_AMT,SPND_IMG FROM SPNDCOFFEE WHERE STORE_ID = ? ORDER BY SPND_ID";
	private static final String GET_Spndcoffeelists_ByStore_id_STMT = "SELECT LIST_ID,SPND_ID,MEM_ID,SPND_PROD,STORE_ID,LIST_AMT,LIST_LEFT,LIST_DATE FROM SPNDCOFFEELIST WHERE STORE_ID = ? ORDER BY LIST_ID";
	private static final String GET_Rate_n_revs_ByStore_id_STMT = "SELECT RNR_ID,MEM_ID,STORE_ID,RNR_RATE,RNR_REV,RNR_DATE FROM RATE_N_REV WHERE STORE_ID = ? ORDER BY RNR_ID";
	private static final String GET_Newss_ByStore_id_STMT = "SELECT NEWS_ID,STORE_ID,NEWS_TITLE,NEWS_CONTENT,NEWS_IMG,NEWS_DATE,NEWS_CLASS,NEWS_TOP,NEWS_PASS FROM NEWS WHERE STORE_ID = ? ORDER BY NEWS_ID";
	private static final String GET_Store_tags_ByStore_id_STMT = "SELECT STORE_ID,TAG_ID,TAG_NUM FROM STORE_TAG WHERE STORE_ID = ? ORDER BY STORE_ID,TAG_ID";
	private static final String GET_Products_ByStore_id_STMT = "SELECT PROD_ID,STORE_ID,PROD_NAME,CATE_ID,PROD_PRICE,PROD_CATEGORY,PROD_IMG,PROD_AMT,PROD_LAUNCH FROM PRODUCT WHERE STORE_ID = ? ORDER BY PROD_ID";
	private static final String GET_Orderlists_ByStore_id_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER FROM ORDERLIST WHERE STORE_ID = ? ORDER BY ORD_ID";
	private static final String GET_Replys_ByStore_id_STMT = "SELECT REPLY_ID,MSG_ID,MEM_ID,STORE_ID,REPLY_CONTENT,REPLY_DATE FROM REPLY WHERE STORE_ID = ? ORDER BY REPLY_ID";
	private static final String GET_Activitys_ByStore_id_STMT = "SELECT ACTIV_ID,MEM_ID,STORE_ID,ACTIV_NAME,ACTIV_STARTTIME,ACTIV_ENDTIME,ACTIV_EXPIRE,ACTIV_IMG,ACTIV_SUMMARY,ACTIV_INTRO,ACTIV_NUM,ACTIV_STORE_CFM FROM ACTIVITY WHERE STORE_ID = ? ORDER BY ACTIV_ID";
	private static final String GET_Fav_stores_ByStore_id_STMT = "SELECT MEM_ID,STORE_ID FROM FAV_STORE WHERE STORE_ID = ? ORDER BY MEM_ID,STORE_ID";
	private static final String GET_Photo_stores_ByStore_id_STMT = "SELECT PHOTO_ID,PHOTO,STORE_ID,MEM_ID,PHOTO_DESC FROM PHOTO_STORE WHERE STORE_ID = ? ORDER BY PHOTO_ID";
	private static final String GET_Rept_stores_ByStore_id_STMT = "SELECT STORE_ID,MEM_ID,REPT_RSN,REPT_REV FROM REPT_STORE WHERE STORE_ID = ? ORDER BY STORE_ID,MEM_ID";

	private static final String DELETE_SPNDCOFFEEs = "DELETE FROM SPNDCOFFEE WHERE STORE_ID = ?";
	private static final String DELETE_SPNDCOFFEELISTs = "DELETE FROM SPNDCOFFEELIST WHERE STORE_ID = ?";
	private static final String DELETE_RATE_N_REVs = "DELETE FROM RATE_N_REV WHERE STORE_ID = ?";
	private static final String DELETE_NEWSs = "DELETE FROM NEWS WHERE STORE_ID = ?";
	private static final String DELETE_STORE_TAGs = "DELETE FROM STORE_TAG WHERE STORE_ID = ?";
	private static final String DELETE_PRODUCTs = "DELETE FROM PRODUCT WHERE STORE_ID = ?";
	private static final String DELETE_ORDERLISTs = "DELETE FROM ORDERLIST WHERE STORE_ID = ?";
	private static final String DELETE_REPLYs = "DELETE FROM REPLY WHERE STORE_ID = ?";
	private static final String DELETE_ACTIVITYs = "DELETE FROM ACTIVITY WHERE STORE_ID = ?";
	private static final String DELETE_FAV_STOREs = "DELETE FROM FAV_STORE WHERE STORE_ID = ?";
	private static final String DELETE_PHOTO_STOREs = "DELETE FROM PHOTO_STORE WHERE STORE_ID = ?";
	private static final String DELETE_REPT_STOREs = "DELETE FROM REPT_STORE WHERE STORE_ID = ?";
	private static final String DELETE_STORE = "DELETE FROM STORE WHERE STORE_ID = ?";
	private static final String UPDATE = "UPDATE STORE SET STORE_ACCT=?, STORE_PWD=?, STORE_NAME=?, STORE_TEL=?, STORE_ADD=?, STORE_EMAIL=?, LONGITUDE=?, LATITUDE=?, STORE_POINTS=?, STORE_CPSE=?, MIN_ORDER=?, IS_MIN_ORDER=?, IS_WIFI=?, IS_PLUG=?, IS_SINGLE_ORGN=?, IS_DESSERT=?, IS_MEAL=?, IS_TIME_LIMIT=?, MON_ISOPEN=?, MON_OPEN=?, MON_CLOSE=?, TUE_ISOPEN=?, TUE_OPEN=?, TUE_CLOSE=?, WED_ISOPEN=?, WED_OPEN=?, WED_CLOSE=?, THU_ISOPEN=?, THU_OPEN=?, THU_CLOSE=?, FRI_ISOPEN=?, FRI_OPEN=?, FRI_CLOSE=?, SAT_ISOPEN=?, SAT_OPEN=?, SAT_CLOSE=?, SUN_ISOPEN=?, SUN_OPEN=?, SUN_CLOSE=?, STORE_IMG=?, STORE_PASS=?, STORE_AUTHENTICATION=?, STORE_VALIDATECODE=? WHERE STORE_ID = ?";

	@Override
	public void insert(StoreVO storeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, storeVO.getStore_acct());
			pstmt.setString(2, storeVO.getStore_pwd());
			pstmt.setString(3, storeVO.getStore_name());
			pstmt.setString(4, storeVO.getStore_tel());
			pstmt.setString(5, storeVO.getStore_add());
			pstmt.setString(6, storeVO.getStore_email());
			pstmt.setDouble(7, storeVO.getLongitude());
			pstmt.setDouble(8, storeVO.getLatitude());
			pstmt.setInt(9, storeVO.getStore_points());
			pstmt.setString(10, storeVO.getStore_cpse());
			pstmt.setInt(11, storeVO.getMin_order());
			pstmt.setInt(12, storeVO.getIs_min_order());
			pstmt.setInt(13, storeVO.getIs_wifi());
			pstmt.setInt(14, storeVO.getIs_plug());
			pstmt.setInt(15, storeVO.getIs_single_orgn());
			pstmt.setInt(16, storeVO.getIs_dessert());
			pstmt.setInt(17, storeVO.getIs_meal());
			pstmt.setInt(18, storeVO.getIs_time_limit());
			pstmt.setInt(19, storeVO.getMon_isopen());
			pstmt.setTimestamp(20, storeVO.getMon_open());
			pstmt.setTimestamp(21, storeVO.getMon_close());
			pstmt.setInt(22, storeVO.getTue_isopen());
			pstmt.setTimestamp(23, storeVO.getTue_open());
			pstmt.setTimestamp(24, storeVO.getTue_close());
			pstmt.setInt(25, storeVO.getWed_isopen());
			pstmt.setTimestamp(26, storeVO.getWed_open());
			pstmt.setTimestamp(27, storeVO.getWed_close());
			pstmt.setInt(28, storeVO.getThu_isopen());
			pstmt.setTimestamp(29, storeVO.getThu_open());
			pstmt.setTimestamp(30, storeVO.getThu_close());
			pstmt.setInt(31, storeVO.getFri_isopen());
			pstmt.setTimestamp(32, storeVO.getFri_open());
			pstmt.setTimestamp(33, storeVO.getFri_close());
			pstmt.setInt(34, storeVO.getSat_isopen());
			pstmt.setTimestamp(35, storeVO.getSat_open());
			pstmt.setTimestamp(36, storeVO.getSat_close());
			pstmt.setInt(37, storeVO.getSun_isopen());
			pstmt.setTimestamp(38, storeVO.getSun_open());
			pstmt.setTimestamp(39, storeVO.getSun_close());
			pstmt.setBytes(40, storeVO.getStore_img());
			pstmt.setInt(41, storeVO.getStore_pass());
			pstmt.setInt(42, storeVO.getStore_authentication());
			pstmt.setString(43, storeVO.getStore_validatecode());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(StoreVO storeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, storeVO.getStore_acct());
			pstmt.setString(2, storeVO.getStore_pwd());
			pstmt.setString(3, storeVO.getStore_name());
			pstmt.setString(4, storeVO.getStore_tel());
			pstmt.setString(5, storeVO.getStore_add());
			pstmt.setString(6, storeVO.getStore_email());
			pstmt.setDouble(7, storeVO.getLongitude());
			pstmt.setDouble(8, storeVO.getLatitude());
			pstmt.setInt(9, storeVO.getStore_points());
			pstmt.setString(10, storeVO.getStore_cpse());
			pstmt.setInt(11, storeVO.getMin_order());
			pstmt.setInt(12, storeVO.getIs_min_order());
			pstmt.setInt(13, storeVO.getIs_wifi());
			pstmt.setInt(14, storeVO.getIs_plug());
			pstmt.setInt(15, storeVO.getIs_single_orgn());
			pstmt.setInt(16, storeVO.getIs_dessert());
			pstmt.setInt(17, storeVO.getIs_meal());
			pstmt.setInt(18, storeVO.getIs_time_limit());
			pstmt.setInt(19, storeVO.getMon_isopen());
			pstmt.setTimestamp(20, storeVO.getMon_open());
			pstmt.setTimestamp(21, storeVO.getMon_close());
			pstmt.setInt(22, storeVO.getTue_isopen());
			pstmt.setTimestamp(23, storeVO.getTue_open());
			pstmt.setTimestamp(24, storeVO.getTue_close());
			pstmt.setInt(25, storeVO.getWed_isopen());
			pstmt.setTimestamp(26, storeVO.getWed_open());
			pstmt.setTimestamp(27, storeVO.getWed_close());
			pstmt.setInt(28, storeVO.getThu_isopen());
			pstmt.setTimestamp(29, storeVO.getThu_open());
			pstmt.setTimestamp(30, storeVO.getThu_close());
			pstmt.setInt(31, storeVO.getFri_isopen());
			pstmt.setTimestamp(32, storeVO.getFri_open());
			pstmt.setTimestamp(33, storeVO.getFri_close());
			pstmt.setInt(34, storeVO.getSat_isopen());
			pstmt.setTimestamp(35, storeVO.getSat_open());
			pstmt.setTimestamp(36, storeVO.getSat_close());
			pstmt.setInt(37, storeVO.getSun_isopen());
			pstmt.setTimestamp(38, storeVO.getSun_open());
			pstmt.setTimestamp(39, storeVO.getSun_close());
			pstmt.setBytes(40, storeVO.getStore_img());
			pstmt.setInt(41, storeVO.getStore_pass());
			pstmt.setInt(42, storeVO.getStore_authentication());
			pstmt.setString(43, storeVO.getStore_validatecode());
			pstmt.setString(44, storeVO.getStore_id());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String store_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_SPNDCOFFEEs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_SPNDCOFFEELISTs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_RATE_N_REVs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_NEWSs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_STORE_TAGs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_PRODUCTs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ORDERLISTs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPLYs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ACTIVITYs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_FAV_STOREs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_PHOTO_STOREs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPT_STOREs);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_STORE);

			pstmt.setString(1, store_id);

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public StoreVO findByPrimaryKey(String store_id) {

		StoreVO storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeVO = new StoreVO();
				storeVO.setStore_id(rs.getString("store_id"));
				storeVO.setStore_acct(rs.getString("store_acct"));
				storeVO.setStore_pwd(rs.getString("store_pwd"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_tel(rs.getString("store_tel"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_email(rs.getString("store_email"));
				storeVO.setLongitude(rs.getDouble("longitude"));
				storeVO.setLatitude(rs.getDouble("latitude"));
				storeVO.setStore_points(rs.getInt("store_points"));
				storeVO.setStore_cpse(rs.getString("store_cpse"));
				storeVO.setMin_order(rs.getInt("min_order"));
				storeVO.setIs_min_order(rs.getInt("is_min_order"));
				storeVO.setIs_wifi(rs.getInt("is_wifi"));
				storeVO.setIs_plug(rs.getInt("is_plug"));
				storeVO.setIs_single_orgn(rs.getInt("is_single_orgn"));
				storeVO.setIs_dessert(rs.getInt("is_dessert"));
				storeVO.setIs_meal(rs.getInt("is_meal"));
				storeVO.setIs_time_limit(rs.getInt("is_time_limit"));
				storeVO.setMon_isopen(rs.getInt("mon_isopen"));
				storeVO.setMon_open(rs.getTimestamp("mon_open"));
				storeVO.setMon_close(rs.getTimestamp("mon_close"));
				storeVO.setTue_isopen(rs.getInt("tue_isopen"));
				storeVO.setTue_open(rs.getTimestamp("tue_open"));
				storeVO.setTue_close(rs.getTimestamp("tue_close"));
				storeVO.setWed_isopen(rs.getInt("wed_isopen"));
				storeVO.setWed_open(rs.getTimestamp("wed_open"));
				storeVO.setWed_close(rs.getTimestamp("wed_close"));
				storeVO.setThu_isopen(rs.getInt("thu_isopen"));
				storeVO.setThu_open(rs.getTimestamp("thu_open"));
				storeVO.setThu_close(rs.getTimestamp("thu_close"));
				storeVO.setFri_isopen(rs.getInt("fri_isopen"));
				storeVO.setFri_open(rs.getTimestamp("fri_open"));
				storeVO.setFri_close(rs.getTimestamp("fri_close"));
				storeVO.setSat_isopen(rs.getInt("sat_isopen"));
				storeVO.setSat_open(rs.getTimestamp("sat_open"));
				storeVO.setSat_close(rs.getTimestamp("sat_close"));
				storeVO.setSun_isopen(rs.getInt("sun_isopen"));
				storeVO.setSun_open(rs.getTimestamp("sun_open"));
				storeVO.setSun_close(rs.getTimestamp("sun_close"));
				storeVO.setStore_img(rs.getBytes("store_img"));
				storeVO.setStore_pass(rs.getInt("store_pass"));
				storeVO.setStore_authentication(rs.getInt("store_authentication"));
				storeVO.setStore_validatecode(rs.getString("store_validatecode"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return storeVO;
	}

	@Override
	public List<StoreVO> getAll() {

		List<StoreVO> list = new ArrayList<StoreVO>();
		StoreVO storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeVO = new StoreVO();
				storeVO.setStore_id(rs.getString("store_id"));
				storeVO.setStore_acct(rs.getString("store_acct"));
				storeVO.setStore_pwd(rs.getString("store_pwd"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_tel(rs.getString("store_tel"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_email(rs.getString("store_email"));
				storeVO.setLongitude(rs.getDouble("longitude"));
				storeVO.setLatitude(rs.getDouble("latitude"));
				storeVO.setStore_points(rs.getInt("store_points"));
				storeVO.setStore_cpse(rs.getString("store_cpse"));
				storeVO.setMin_order(rs.getInt("min_order"));
				storeVO.setIs_min_order(rs.getInt("is_min_order"));
				storeVO.setIs_wifi(rs.getInt("is_wifi"));
				storeVO.setIs_plug(rs.getInt("is_plug"));
				storeVO.setIs_single_orgn(rs.getInt("is_single_orgn"));
				storeVO.setIs_dessert(rs.getInt("is_dessert"));
				storeVO.setIs_meal(rs.getInt("is_meal"));
				storeVO.setIs_time_limit(rs.getInt("is_time_limit"));
				storeVO.setMon_isopen(rs.getInt("mon_isopen"));
				storeVO.setMon_open(rs.getTimestamp("mon_open"));
				storeVO.setMon_close(rs.getTimestamp("mon_close"));
				storeVO.setTue_isopen(rs.getInt("tue_isopen"));
				storeVO.setTue_open(rs.getTimestamp("tue_open"));
				storeVO.setTue_close(rs.getTimestamp("tue_close"));
				storeVO.setWed_isopen(rs.getInt("wed_isopen"));
				storeVO.setWed_open(rs.getTimestamp("wed_open"));
				storeVO.setWed_close(rs.getTimestamp("wed_close"));
				storeVO.setThu_isopen(rs.getInt("thu_isopen"));
				storeVO.setThu_open(rs.getTimestamp("thu_open"));
				storeVO.setThu_close(rs.getTimestamp("thu_close"));
				storeVO.setFri_isopen(rs.getInt("fri_isopen"));
				storeVO.setFri_open(rs.getTimestamp("fri_open"));
				storeVO.setFri_close(rs.getTimestamp("fri_close"));
				storeVO.setSat_isopen(rs.getInt("sat_isopen"));
				storeVO.setSat_open(rs.getTimestamp("sat_open"));
				storeVO.setSat_close(rs.getTimestamp("sat_close"));
				storeVO.setSun_isopen(rs.getInt("sun_isopen"));
				storeVO.setSun_open(rs.getTimestamp("sun_open"));
				storeVO.setSun_close(rs.getTimestamp("sun_close"));
				storeVO.setStore_img(rs.getBytes("store_img"));
				storeVO.setStore_pass(rs.getInt("store_pass"));
				storeVO.setStore_authentication(rs.getInt("store_authentication"));
				storeVO.setStore_validatecode(rs.getString("store_validatecode"));
				list.add(storeVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public Set<SpndcoffeeVO> getSpndcoffeesByStore_id(String store_id){
		Set<SpndcoffeeVO> set = new LinkedHashSet<SpndcoffeeVO>();
		SpndcoffeeVO spndcoffeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Spndcoffees_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				spndcoffeeVO = new SpndcoffeeVO();
				spndcoffeeVO.setSpnd_id(rs.getString("spnd_id"));
				spndcoffeeVO.setStore_id(rs.getString("store_id"));
				spndcoffeeVO.setSpnd_name(rs.getString("spnd_name"));
				spndcoffeeVO.setSpnd_prod(rs.getString("spnd_prod"));
				spndcoffeeVO.setSpnd_enddate(rs.getDate("spnd_enddate"));
				spndcoffeeVO.setSpnd_amt(rs.getInt("spnd_amt"));
				spndcoffeeVO.setSpnd_img(rs.getBytes("spnd_img"));
				set.add(spndcoffeeVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<SpndcoffeelistVO> getSpndcoffeelistsByStore_id(String store_id){
		Set<SpndcoffeelistVO> set = new LinkedHashSet<SpndcoffeelistVO>();
		SpndcoffeelistVO spndcoffeelistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Spndcoffeelists_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				spndcoffeelistVO = new SpndcoffeelistVO();
				spndcoffeelistVO.setList_id(rs.getString("list_id"));
				spndcoffeelistVO.setSpnd_id(rs.getString("spnd_id"));
				spndcoffeelistVO.setMem_id(rs.getString("mem_id"));
				spndcoffeelistVO.setSpnd_prod(rs.getString("spnd_prod"));
				spndcoffeelistVO.setStore_id(rs.getString("store_id"));
				spndcoffeelistVO.setList_amt(rs.getInt("list_amt"));
				spndcoffeelistVO.setList_left(rs.getInt("list_left"));
				spndcoffeelistVO.setList_date(rs.getTimestamp("list_date"));
				set.add(spndcoffeelistVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<Rate_n_revVO> getRate_n_revsByStore_id(String store_id){
		Set<Rate_n_revVO> set = new LinkedHashSet<Rate_n_revVO>();
		Rate_n_revVO rate_n_revVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Rate_n_revs_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rate_n_revVO = new Rate_n_revVO();
				rate_n_revVO.setRnr_id(rs.getString("rnr_id"));
				rate_n_revVO.setMem_id(rs.getString("mem_id"));
				rate_n_revVO.setStore_id(rs.getString("store_id"));
				rate_n_revVO.setRnr_rate(rs.getInt("rnr_rate"));
				rate_n_revVO.setRnr_rev(readerToString(rs.getCharacterStream("rnr_rev")));
				rate_n_revVO.setRnr_date(rs.getTimestamp("rnr_date"));
				set.add(rate_n_revVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<NewsVO> getNewssByStore_id(String store_id){
		Set<NewsVO> set = new LinkedHashSet<NewsVO>();
		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Newss_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				newsVO = new NewsVO();
				newsVO.setNews_id(rs.getString("news_id"));
				newsVO.setStore_id(rs.getString("store_id"));
				newsVO.setNews_title(rs.getString("news_title"));
				newsVO.setNews_content(readerToString(rs.getCharacterStream("news_content")));
				newsVO.setNews_img(rs.getBytes("news_img"));
				newsVO.setNews_date(rs.getTimestamp("news_date"));
				newsVO.setNews_class(rs.getString("news_class"));
				newsVO.setNews_top(rs.getInt("news_top"));
				newsVO.setNews_pass(rs.getInt("news_pass"));
				set.add(newsVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<Store_tagVO> getStore_tagsByStore_id(String store_id){
		Set<Store_tagVO> set = new LinkedHashSet<Store_tagVO>();
		Store_tagVO store_tagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Store_tags_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				store_tagVO = new Store_tagVO();
				store_tagVO.setStore_id(rs.getString("store_id"));
				store_tagVO.setTag_id(rs.getString("tag_id"));
				store_tagVO.setTag_num(rs.getInt("tag_num"));
				set.add(store_tagVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<ProductVO> getProductsByStore_id(String store_id){
		Set<ProductVO> set = new LinkedHashSet<ProductVO>();
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Products_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				productVO = new ProductVO();
				productVO.setProd_id(rs.getString("prod_id"));
				productVO.setStore_id(rs.getString("store_id"));
				productVO.setProd_name(rs.getString("prod_name"));
				productVO.setCate_id(rs.getString("cate_id"));
				productVO.setProd_price(rs.getInt("prod_price"));
				productVO.setProd_category(rs.getInt("prod_category"));
				productVO.setProd_img(rs.getBytes("prod_img"));
				productVO.setProd_amt(rs.getInt("prod_amt"));
				productVO.setProd_launch(rs.getInt("prod_launch"));
				set.add(productVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<OrderlistVO> getOrderlistsByStore_id(String store_id){
		Set<OrderlistVO> set = new LinkedHashSet<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Orderlists_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderlistVO = new OrderlistVO();
				orderlistVO.setOrd_id(rs.getString("ord_id"));
				orderlistVO.setMem_id(rs.getString("mem_id"));
				orderlistVO.setStore_id(rs.getString("store_id"));
				orderlistVO.setOrd_total(rs.getInt("ord_total"));
				orderlistVO.setOrd_pick(rs.getInt("ord_pick"));
				orderlistVO.setOrd_add(rs.getString("ord_add"));
				orderlistVO.setOrd_shipping(rs.getInt("ord_shipping"));
				orderlistVO.setOrd_time(rs.getTimestamp("ord_time"));
				orderlistVO.setScore_seller(rs.getInt("score_seller"));
				set.add(orderlistVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<ReplyVO> getReplysByStore_id(String store_id){
		Set<ReplyVO> set = new LinkedHashSet<ReplyVO>();
		ReplyVO replyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Replys_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				replyVO = new ReplyVO();
				replyVO.setReply_id(rs.getString("reply_id"));
				replyVO.setMsg_id(rs.getString("msg_id"));
				replyVO.setMem_id(rs.getString("mem_id"));
				replyVO.setStore_id(rs.getString("store_id"));
				replyVO.setReply_content(readerToString(rs.getCharacterStream("reply_content")));
				replyVO.setReply_date(rs.getTimestamp("reply_date"));
				set.add(replyVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<ActivityVO> getActivitysByStore_id(String store_id){
		Set<ActivityVO> set = new LinkedHashSet<ActivityVO>();
		ActivityVO activityVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Activitys_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				activityVO = new ActivityVO();
				activityVO.setActiv_id(rs.getString("activ_id"));
				activityVO.setMem_id(rs.getString("mem_id"));
				activityVO.setStore_id(rs.getString("store_id"));
				activityVO.setActiv_name(rs.getString("activ_name"));
				activityVO.setActiv_starttime(rs.getTimestamp("activ_starttime"));
				activityVO.setActiv_endtime(rs.getTimestamp("activ_endtime"));
				activityVO.setActiv_expire(rs.getTimestamp("activ_expire"));
				activityVO.setActiv_img(rs.getBytes("activ_img"));
				activityVO.setActiv_summary(rs.getString("activ_summary"));
				activityVO.setActiv_intro(readerToString(rs.getCharacterStream("activ_intro")));
				activityVO.setActiv_num(rs.getInt("activ_num"));
				activityVO.setActiv_store_cfm(rs.getInt("activ_store_cfm"));
				set.add(activityVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<Fav_storeVO> getFav_storesByStore_id(String store_id){
		Set<Fav_storeVO> set = new LinkedHashSet<Fav_storeVO>();
		Fav_storeVO fav_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Fav_stores_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				fav_storeVO = new Fav_storeVO();
				fav_storeVO.setMem_id(rs.getString("mem_id"));
				fav_storeVO.setStore_id(rs.getString("store_id"));
				set.add(fav_storeVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<Photo_storeVO> getPhoto_storesByStore_id(String store_id){
		Set<Photo_storeVO> set = new LinkedHashSet<Photo_storeVO>();
		Photo_storeVO photo_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Photo_stores_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				photo_storeVO = new Photo_storeVO();
				photo_storeVO.setPhoto_id(rs.getString("photo_id"));
				photo_storeVO.setPhoto(rs.getBytes("photo"));
				photo_storeVO.setStore_id(rs.getString("store_id"));
				photo_storeVO.setMem_id(rs.getString("mem_id"));
				photo_storeVO.setPhoto_desc(readerToString(rs.getCharacterStream("photo_desc")));
				set.add(photo_storeVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	@Override
	public Set<Rept_storeVO> getRept_storesByStore_id(String store_id){
		Set<Rept_storeVO> set = new LinkedHashSet<Rept_storeVO>();
		Rept_storeVO rept_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Rept_stores_ByStore_id_STMT);
			pstmt.setString(1, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_storeVO = new Rept_storeVO();
				rept_storeVO.setStore_id(rs.getString("store_id"));
				rept_storeVO.setMem_id(rs.getString("mem_id"));
				rept_storeVO.setRept_rsn(readerToString(rs.getCharacterStream("rept_rsn")));
				rept_storeVO.setRept_rev(rs.getInt("rept_rev"));
				set.add(rept_storeVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

	public Reader stringToReader(String text) {
		if(text != null){
			return new StringReader(text);
		}
		else{
			return null;
		}
	}

	public String readerToString(Reader reader) {
		if(reader != null){
			int i;
			StringBuilder sb = new StringBuilder();
			try {
				while ((i = reader.read()) != -1) {
					sb.append((char)i);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return sb.toString();
		}
		else{
			return null;
		}
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

	public static void main(String[] args) throws IOException {

		StoreJDBCDAO dao = new StoreJDBCDAO();

/*
		// insert()
		StoreVO storeVO = new StoreVO();
		storeVO.setStore_id("A");
		storeVO.setStore_acct("A");
		storeVO.setStore_pwd("A");
		storeVO.setStore_name("A");
		storeVO.setStore_tel("A");
		storeVO.setStore_add("A");
		storeVO.setStore_email("A");
		storeVO.setLongitude(1.0);
		storeVO.setLatitude(1.0);
		storeVO.setStore_points(1);
		storeVO.setStore_cpse("A");
		storeVO.setMin_order(1);
		storeVO.setIs_min_order(1);
		storeVO.setIs_wifi(1);
		storeVO.setIs_plug(1);
		storeVO.setIs_single_orgn(1);
		storeVO.setIs_dessert(1);
		storeVO.setIs_meal(1);
		storeVO.setIs_time_limit(1);
		storeVO.setMon_isopen(1);
		storeVO.setMon_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setMon_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setTue_isopen(1);
		storeVO.setTue_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setTue_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setWed_isopen(1);
		storeVO.setWed_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setWed_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setThu_isopen(1);
		storeVO.setThu_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setThu_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setFri_isopen(1);
		storeVO.setFri_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setFri_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setSat_isopen(1);
		storeVO.setSat_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setSat_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setSun_isopen(1);
		storeVO.setSun_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setSun_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setStore_img(getPictureByteArray("D:/temp/tomcat.gif"));
		storeVO.setStore_pass(1);
		storeVO.setStore_authentication(1);
		storeVO.setStore_validatecode("A");
		dao.insert(storeVO);

		// update()
		StoreVO storeVO = new StoreVO();
		storeVO.setStore_id("A");
		storeVO.setStore_acct("A");
		storeVO.setStore_pwd("A");
		storeVO.setStore_name("A");
		storeVO.setStore_tel("A");
		storeVO.setStore_add("A");
		storeVO.setStore_email("A");
		storeVO.setLongitude(1.0);
		storeVO.setLatitude(1.0);
		storeVO.setStore_points(1);
		storeVO.setStore_cpse("A");
		storeVO.setMin_order(1);
		storeVO.setIs_min_order(1);
		storeVO.setIs_wifi(1);
		storeVO.setIs_plug(1);
		storeVO.setIs_single_orgn(1);
		storeVO.setIs_dessert(1);
		storeVO.setIs_meal(1);
		storeVO.setIs_time_limit(1);
		storeVO.setMon_isopen(1);
		storeVO.setMon_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setMon_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setTue_isopen(1);
		storeVO.setTue_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setTue_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setWed_isopen(1);
		storeVO.setWed_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setWed_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setThu_isopen(1);
		storeVO.setThu_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setThu_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setFri_isopen(1);
		storeVO.setFri_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setFri_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setSat_isopen(1);
		storeVO.setSat_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setSat_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setSun_isopen(1);
		storeVO.setSun_open(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setSun_close(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		storeVO.setStore_img(getPictureByteArray("D:/temp/tomcat.gif"));
		storeVO.setStore_pass(1);
		storeVO.setStore_authentication(1);
		storeVO.setStore_validatecode("A");
		dao.update(storeVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		StoreVO storeVO = dao.findByPrimaryKey("A");
		System.out.print(storeVO.getStore_id() + ", ");
		System.out.print(storeVO.getStore_acct() + ", ");
		System.out.print(storeVO.getStore_pwd() + ", ");
		System.out.print(storeVO.getStore_name() + ", ");
		System.out.print(storeVO.getStore_tel() + ", ");
		System.out.print(storeVO.getStore_add() + ", ");
		System.out.print(storeVO.getStore_email() + ", ");
		System.out.print(storeVO.getLongitude() + ", ");
		System.out.print(storeVO.getLatitude() + ", ");
		System.out.print(storeVO.getStore_points() + ", ");
		System.out.print(storeVO.getStore_cpse() + ", ");
		System.out.print(storeVO.getMin_order() + ", ");
		System.out.print(storeVO.getIs_min_order() + ", ");
		System.out.print(storeVO.getIs_wifi() + ", ");
		System.out.print(storeVO.getIs_plug() + ", ");
		System.out.print(storeVO.getIs_single_orgn() + ", ");
		System.out.print(storeVO.getIs_dessert() + ", ");
		System.out.print(storeVO.getIs_meal() + ", ");
		System.out.print(storeVO.getIs_time_limit() + ", ");
		System.out.print(storeVO.getMon_isopen() + ", ");
		System.out.print(storeVO.getMon_open() + ", ");
		System.out.print(storeVO.getMon_close() + ", ");
		System.out.print(storeVO.getTue_isopen() + ", ");
		System.out.print(storeVO.getTue_open() + ", ");
		System.out.print(storeVO.getTue_close() + ", ");
		System.out.print(storeVO.getWed_isopen() + ", ");
		System.out.print(storeVO.getWed_open() + ", ");
		System.out.print(storeVO.getWed_close() + ", ");
		System.out.print(storeVO.getThu_isopen() + ", ");
		System.out.print(storeVO.getThu_open() + ", ");
		System.out.print(storeVO.getThu_close() + ", ");
		System.out.print(storeVO.getFri_isopen() + ", ");
		System.out.print(storeVO.getFri_open() + ", ");
		System.out.print(storeVO.getFri_close() + ", ");
		System.out.print(storeVO.getSat_isopen() + ", ");
		System.out.print(storeVO.getSat_open() + ", ");
		System.out.print(storeVO.getSat_close() + ", ");
		System.out.print(storeVO.getSun_isopen() + ", ");
		System.out.print(storeVO.getSun_open() + ", ");
		System.out.print(storeVO.getSun_close() + ", ");
		System.out.print(storeVO.getStore_img() + ", ");
		System.out.print(storeVO.getStore_pass() + ", ");
		System.out.print(storeVO.getStore_authentication() + ", ");
		System.out.print(storeVO.getStore_validatecode() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<StoreVO> list = dao.getAll();
		for (StoreVO aStoreVO : list) {
			System.out.print(aStoreVO.getStore_id() + ", ");
			System.out.print(aStoreVO.getStore_acct() + ", ");
			System.out.print(aStoreVO.getStore_pwd() + ", ");
			System.out.print(aStoreVO.getStore_name() + ", ");
			System.out.print(aStoreVO.getStore_tel() + ", ");
			System.out.print(aStoreVO.getStore_add() + ", ");
			System.out.print(aStoreVO.getStore_email() + ", ");
			System.out.print(aStoreVO.getLongitude() + ", ");
			System.out.print(aStoreVO.getLatitude() + ", ");
			System.out.print(aStoreVO.getStore_points() + ", ");
			System.out.print(aStoreVO.getStore_cpse() + ", ");
			System.out.print(aStoreVO.getMin_order() + ", ");
			System.out.print(aStoreVO.getIs_min_order() + ", ");
			System.out.print(aStoreVO.getIs_wifi() + ", ");
			System.out.print(aStoreVO.getIs_plug() + ", ");
			System.out.print(aStoreVO.getIs_single_orgn() + ", ");
			System.out.print(aStoreVO.getIs_dessert() + ", ");
			System.out.print(aStoreVO.getIs_meal() + ", ");
			System.out.print(aStoreVO.getIs_time_limit() + ", ");
			System.out.print(aStoreVO.getMon_isopen() + ", ");
			System.out.print(aStoreVO.getMon_open() + ", ");
			System.out.print(aStoreVO.getMon_close() + ", ");
			System.out.print(aStoreVO.getTue_isopen() + ", ");
			System.out.print(aStoreVO.getTue_open() + ", ");
			System.out.print(aStoreVO.getTue_close() + ", ");
			System.out.print(aStoreVO.getWed_isopen() + ", ");
			System.out.print(aStoreVO.getWed_open() + ", ");
			System.out.print(aStoreVO.getWed_close() + ", ");
			System.out.print(aStoreVO.getThu_isopen() + ", ");
			System.out.print(aStoreVO.getThu_open() + ", ");
			System.out.print(aStoreVO.getThu_close() + ", ");
			System.out.print(aStoreVO.getFri_isopen() + ", ");
			System.out.print(aStoreVO.getFri_open() + ", ");
			System.out.print(aStoreVO.getFri_close() + ", ");
			System.out.print(aStoreVO.getSat_isopen() + ", ");
			System.out.print(aStoreVO.getSat_open() + ", ");
			System.out.print(aStoreVO.getSat_close() + ", ");
			System.out.print(aStoreVO.getSun_isopen() + ", ");
			System.out.print(aStoreVO.getSun_open() + ", ");
			System.out.print(aStoreVO.getSun_close() + ", ");
			System.out.print(aStoreVO.getStore_img() + ", ");
			System.out.print(aStoreVO.getStore_pass() + ", ");
			System.out.print(aStoreVO.getStore_authentication() + ", ");
			System.out.print(aStoreVO.getStore_validatecode() + ", ");
			System.out.println();
		}

		Set<SpndcoffeeVO> set = dao.getSpndcoffeesByStore_id("A");
		for (SpndcoffeeVO aSpndcoffee : set) {
			System.out.print(aSpndcoffee.getSpnd_id() + ", ");
			System.out.print(aSpndcoffee.getStore_id() + ", ");
			System.out.print(aSpndcoffee.getSpnd_name() + ", ");
			System.out.print(aSpndcoffee.getSpnd_prod() + ", ");
			System.out.print(aSpndcoffee.getSpnd_enddate() + ", ");
			System.out.print(aSpndcoffee.getSpnd_amt() + ", ");
			System.out.print(aSpndcoffee.getSpnd_img() + ", ");
			System.out.println();
		}

		Set<SpndcoffeelistVO> set = dao.getSpndcoffeelistsByStore_id("A");
		for (SpndcoffeelistVO aSpndcoffeelist : set) {
			System.out.print(aSpndcoffeelist.getList_id() + ", ");
			System.out.print(aSpndcoffeelist.getSpnd_id() + ", ");
			System.out.print(aSpndcoffeelist.getMem_id() + ", ");
			System.out.print(aSpndcoffeelist.getSpnd_prod() + ", ");
			System.out.print(aSpndcoffeelist.getStore_id() + ", ");
			System.out.print(aSpndcoffeelist.getList_amt() + ", ");
			System.out.print(aSpndcoffeelist.getList_left() + ", ");
			System.out.print(aSpndcoffeelist.getList_date() + ", ");
			System.out.println();
		}

		Set<Rate_n_revVO> set = dao.getRate_n_revsByStore_id("A");
		for (Rate_n_revVO aRate_n_rev : set) {
			System.out.print(aRate_n_rev.getRnr_id() + ", ");
			System.out.print(aRate_n_rev.getMem_id() + ", ");
			System.out.print(aRate_n_rev.getStore_id() + ", ");
			System.out.print(aRate_n_rev.getRnr_rate() + ", ");
			System.out.print(aRate_n_rev.getRnr_rev() + ", ");
			System.out.print(aRate_n_rev.getRnr_date() + ", ");
			System.out.println();
		}

		Set<NewsVO> set = dao.getNewssByStore_id("A");
		for (NewsVO aNews : set) {
			System.out.print(aNews.getNews_id() + ", ");
			System.out.print(aNews.getStore_id() + ", ");
			System.out.print(aNews.getNews_title() + ", ");
			System.out.print(aNews.getNews_content() + ", ");
			System.out.print(aNews.getNews_img() + ", ");
			System.out.print(aNews.getNews_date() + ", ");
			System.out.print(aNews.getNews_class() + ", ");
			System.out.print(aNews.getNews_top() + ", ");
			System.out.print(aNews.getNews_pass() + ", ");
			System.out.println();
		}

		Set<Store_tagVO> set = dao.getStore_tagsByStore_id("A");
		for (Store_tagVO aStore_tag : set) {
			System.out.print(aStore_tag.getStore_id() + ", ");
			System.out.print(aStore_tag.getTag_id() + ", ");
			System.out.print(aStore_tag.getTag_num() + ", ");
			System.out.println();
		}

		Set<ProductVO> set = dao.getProductsByStore_id("A");
		for (ProductVO aProduct : set) {
			System.out.print(aProduct.getProd_id() + ", ");
			System.out.print(aProduct.getStore_id() + ", ");
			System.out.print(aProduct.getProd_name() + ", ");
			System.out.print(aProduct.getCate_id() + ", ");
			System.out.print(aProduct.getProd_price() + ", ");
			System.out.print(aProduct.getProd_category() + ", ");
			System.out.print(aProduct.getProd_img() + ", ");
			System.out.print(aProduct.getProd_amt() + ", ");
			System.out.print(aProduct.getProd_launch() + ", ");
			System.out.println();
		}

		Set<OrderlistVO> set = dao.getOrderlistsByStore_id("A");
		for (OrderlistVO aOrderlist : set) {
			System.out.print(aOrderlist.getOrd_id() + ", ");
			System.out.print(aOrderlist.getMem_id() + ", ");
			System.out.print(aOrderlist.getStore_id() + ", ");
			System.out.print(aOrderlist.getOrd_total() + ", ");
			System.out.print(aOrderlist.getOrd_pick() + ", ");
			System.out.print(aOrderlist.getOrd_add() + ", ");
			System.out.print(aOrderlist.getOrd_shipping() + ", ");
			System.out.print(aOrderlist.getOrd_time() + ", ");
			System.out.print(aOrderlist.getScore_seller() + ", ");
			System.out.println();
		}

		Set<ReplyVO> set = dao.getReplysByStore_id("A");
		for (ReplyVO aReply : set) {
			System.out.print(aReply.getReply_id() + ", ");
			System.out.print(aReply.getMsg_id() + ", ");
			System.out.print(aReply.getMem_id() + ", ");
			System.out.print(aReply.getStore_id() + ", ");
			System.out.print(aReply.getReply_content() + ", ");
			System.out.print(aReply.getReply_date() + ", ");
			System.out.println();
		}

		Set<ActivityVO> set = dao.getActivitysByStore_id("A");
		for (ActivityVO aActivity : set) {
			System.out.print(aActivity.getActiv_id() + ", ");
			System.out.print(aActivity.getMem_id() + ", ");
			System.out.print(aActivity.getStore_id() + ", ");
			System.out.print(aActivity.getActiv_name() + ", ");
			System.out.print(aActivity.getActiv_starttime() + ", ");
			System.out.print(aActivity.getActiv_endtime() + ", ");
			System.out.print(aActivity.getActiv_expire() + ", ");
			System.out.print(aActivity.getActiv_img() + ", ");
			System.out.print(aActivity.getActiv_summary() + ", ");
			System.out.print(aActivity.getActiv_intro() + ", ");
			System.out.print(aActivity.getActiv_num() + ", ");
			System.out.print(aActivity.getActiv_store_cfm() + ", ");
			System.out.println();
		}

		Set<Fav_storeVO> set = dao.getFav_storesByStore_id("A");
		for (Fav_storeVO aFav_store : set) {
			System.out.print(aFav_store.getMem_id() + ", ");
			System.out.print(aFav_store.getStore_id() + ", ");
			System.out.println();
		}

		Set<Photo_storeVO> set = dao.getPhoto_storesByStore_id("A");
		for (Photo_storeVO aPhoto_store : set) {
			System.out.print(aPhoto_store.getPhoto_id() + ", ");
			System.out.print(aPhoto_store.getPhoto() + ", ");
			System.out.print(aPhoto_store.getStore_id() + ", ");
			System.out.print(aPhoto_store.getMem_id() + ", ");
			System.out.print(aPhoto_store.getPhoto_desc() + ", ");
			System.out.println();
		}

		Set<Rept_storeVO> set = dao.getRept_storesByStore_id("A");
		for (Rept_storeVO aRept_store : set) {
			System.out.print(aRept_store.getStore_id() + ", ");
			System.out.print(aRept_store.getMem_id() + ", ");
			System.out.print(aRept_store.getRept_rsn() + ", ");
			System.out.print(aRept_store.getRept_rev() + ", ");
			System.out.println();
		}

*/
	}


}