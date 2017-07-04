package com.ming.store.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class StoreJDBCDAO implements StoreDAO_interface {

	// JDBC
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";
	// JDBC

	private static final String INSERT_STMT = "INSERT INTO STORE (STORE_ID,STORE_ACCT,STORE_PWD,STORE_NAME,STORE_TEL,STORE_ADD,STORE_EMAIL,LONGITUDE,LATITUDE,STORE_POINTS,STORE_CPSE,MIN_ORDER,IS_MIN_ORDER,IS_WIFI,IS_PLUG,IS_SINGLE_ORGN,IS_DESSERT,IS_MEAL,IS_TIME_LIMIT,MON_ISOPEN,MON_OPEN,MON_CLOSE,TUE_ISOPEN,TUE_OPEN,TUE_CLOSE,WED_ISOPEN,WED_OPEN,WED_CLOSE,THU_ISOPEN,THU_OPEN,THU_CLOSE,FRI_ISOPEN,FRI_OPEN,FRI_CLOSE,SAT_ISOPEN,SAT_OPEN,SAT_CLOSE,SUN_ISOPEN,SUN_OPEN,SUN_CLOSE) VALUES ('STORE' || LPAD(to_char(STORE_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT STORE_ID,STORE_ACCT,STORE_PWD,STORE_NAME,STORE_TEL,STORE_ADD,STORE_EMAIL,LONGITUDE,LATITUDE,STORE_POINTS,STORE_CPSE,MIN_ORDER,IS_MIN_ORDER,IS_WIFI,IS_PLUG,IS_SINGLE_ORGN,IS_DESSERT,IS_MEAL,IS_TIME_LIMIT,MON_ISOPEN,MON_OPEN,MON_CLOSE,TUE_ISOPEN,TUE_OPEN,TUE_CLOSE,WED_ISOPEN,WED_OPEN,WED_CLOSE,THU_ISOPEN,THU_OPEN,THU_CLOSE,FRI_ISOPEN,FRI_OPEN,FRI_CLOSE,SAT_ISOPEN,SAT_OPEN,SAT_CLOSE,SUN_ISOPEN,SUN_OPEN,SUN_CLOSE FROM STORE ORDER BY STORE_ID";
	private static final String GET_ONE_STMT = "SELECT STORE_ID,STORE_ACCT,STORE_PWD,STORE_NAME,STORE_TEL,STORE_ADD,STORE_EMAIL,LONGITUDE,LATITUDE,STORE_POINTS,STORE_CPSE,MIN_ORDER,IS_MIN_ORDER,IS_WIFI,IS_PLUG,IS_SINGLE_ORGN,IS_DESSERT,IS_MEAL,IS_TIME_LIMIT,MON_ISOPEN,MON_OPEN,MON_CLOSE,TUE_ISOPEN,TUE_OPEN,TUE_CLOSE,WED_ISOPEN,WED_OPEN,WED_CLOSE,THU_ISOPEN,THU_OPEN,THU_CLOSE,FRI_ISOPEN,FRI_OPEN,FRI_CLOSE,SAT_ISOPEN,SAT_OPEN,SAT_CLOSE,SUN_ISOPEN,SUN_OPEN,SUN_CLOSE FROM STORE WHERE STORE_ID = ?";
	private static final String DELETE = "DELETE FROM STORE WHERE STORE_ID = ?";
	private static final String UPDATE = "UPDATE STORE SET STORE_ACCT=?, STORE_PWD=?, STORE_NAME=?, STORE_TEL=?, STORE_ADD=?, STORE_EMAIL=?, LONGITUDE=?, LATITUDE=?, STORE_POINTS=?, STORE_CPSE=?, MIN_ORDER=?, IS_MIN_ORDER=?, IS_WIFI=?, IS_PLUG=?, IS_SINGLE_ORGN=?, IS_DESSERT=?, IS_MEAL=?, IS_TIME_LIMIT=?, MON_ISOPEN=?, MON_OPEN=?, MON_CLOSE=?, TUE_ISOPEN=?, TUE_OPEN=?, TUE_CLOSE=?, WED_ISOPEN=?, WED_OPEN=?, WED_CLOSE=?, THU_ISOPEN=?, THU_OPEN=?, THU_CLOSE=?, FRI_ISOPEN=?, FRI_OPEN=?, FRI_CLOSE=?, SAT_ISOPEN=?, SAT_OPEN=?, SAT_CLOSE=?, SUN_ISOPEN=?, SUN_OPEN=?, SUN_CLOSE=? WHERE STORE_ID = ?";
	private static final String GET_ONE_STORE = "SELECT STORE_ID,STORE_ACCT,STORE_PWD,STORE_NAME,STORE_TEL,STORE_ADD,STORE_EMAIL,LONGITUDE,LATITUDE,STORE_POINTS,STORE_CPSE,MIN_ORDER,IS_MIN_ORDER,IS_WIFI,IS_PLUG,IS_SINGLE_ORGN,IS_DESSERT,IS_MEAL,IS_TIME_LIMIT,MON_ISOPEN,MON_OPEN,MON_CLOSE,TUE_ISOPEN,TUE_OPEN,TUE_CLOSE,WED_ISOPEN,WED_OPEN,WED_CLOSE,THU_ISOPEN,THU_OPEN,THU_CLOSE,FRI_ISOPEN,FRI_OPEN,FRI_CLOSE,SAT_ISOPEN,SAT_OPEN,SAT_CLOSE,SUN_ISOPEN,SUN_OPEN,SUN_CLOSE FROM STORE WHERE STORE_ACCT = ?";
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
			pstmt.setInt(7, storeVO.getLongitude());
			pstmt.setInt(8, storeVO.getLatitude());
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
			pstmt.setInt(7, storeVO.getLongitude());
			pstmt.setInt(8, storeVO.getLatitude());
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
			pstmt.setString(40, storeVO.getStore_id());

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
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, store_id);

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
				storeVO.setLongitude(rs.getInt("longitude"));
				storeVO.setLatitude(rs.getInt("latitude"));
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
	public StoreVO findByStore(String store_acct) {
		StoreVO storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STORE);

			pstmt.setString(1, store_acct);
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
				storeVO.setLongitude(rs.getInt("longitude"));
				storeVO.setLatitude(rs.getInt("latitude"));
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
				storeVO.setLongitude(rs.getInt("longitude"));
				storeVO.setLatitude(rs.getInt("latitude"));
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

	public static void main(String[] args) {

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
		storeVO.setLongitude(1);
		storeVO.setLatitude(1);
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
		storeVO.setLongitude(1);
		storeVO.setLatitude(1);
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
			System.out.println();
		}

*/
	}


}