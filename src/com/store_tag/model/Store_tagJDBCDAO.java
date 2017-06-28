package com.store_tag.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Store_tagJDBCDAO implements Store_tagDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO STORE_TAG (STORE_ID,TAG_ID,TAG_NUM) VALUES (?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT STORE_ID,TAG_ID,TAG_NUM FROM STORE_TAG ORDER BY STORE_ID,TAG_ID";
	private static final String GET_ONE_STMT = "SELECT STORE_ID,TAG_ID,TAG_NUM FROM STORE_TAG WHERE STORE_ID = ? AND TAG_ID = ?";
	private static final String DELETE_STORE_TAG = "DELETE FROM STORE_TAG WHERE STORE_ID = ? AND TAG_ID = ?";
	private static final String UPDATE = "UPDATE STORE_TAG SET TAG_NUM=? WHERE STORE_ID = ? AND TAG_ID = ?";

	@Override
	public void insert(Store_tagVO store_tagVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, store_tagVO.getStore_id());
			pstmt.setString(2, store_tagVO.getTag_id());
			pstmt.setInt(3, store_tagVO.getTag_num());

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
	public void update(Store_tagVO store_tagVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, store_tagVO.getTag_num());
			pstmt.setString(2, store_tagVO.getStore_id());
			pstmt.setString(3, store_tagVO.getTag_id());

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
	public void delete(String store_id, String tag_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_STORE_TAG);

			pstmt.setString(1, store_id);
			pstmt.setString(2, tag_id);

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
	public Store_tagVO findByPrimaryKey(String store_id, String tag_id) {

		Store_tagVO store_tagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, store_id);
			pstmt.setString(2, tag_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				store_tagVO = new Store_tagVO();
				store_tagVO.setStore_id(rs.getString("store_id"));
				store_tagVO.setTag_id(rs.getString("tag_id"));
				store_tagVO.setTag_num(rs.getInt("tag_num"));
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
		return store_tagVO;
	}

	@Override
	public List<Store_tagVO> getAll() {

		List<Store_tagVO> list = new ArrayList<Store_tagVO>();
		Store_tagVO store_tagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				store_tagVO = new Store_tagVO();
				store_tagVO.setStore_id(rs.getString("store_id"));
				store_tagVO.setTag_id(rs.getString("tag_id"));
				store_tagVO.setTag_num(rs.getInt("tag_num"));
				list.add(store_tagVO); // Store the row in the list
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

		Store_tagJDBCDAO dao = new Store_tagJDBCDAO();

/*
		// insert()
		Store_tagVO store_tagVO = new Store_tagVO();
		store_tagVO.setStore_id("A");
		store_tagVO.setTag_id("A");
		store_tagVO.setTag_num(1);
		dao.insert(store_tagVO);

		// update()
		Store_tagVO store_tagVO = new Store_tagVO();
		store_tagVO.setStore_id("A");
		store_tagVO.setTag_id("A");
		store_tagVO.setTag_num(1);
		dao.update(store_tagVO);

		// delete()
		dao.delete("A","A");

		// findByPrimaryKey()
		Store_tagVO store_tagVO = dao.findByPrimaryKey("A","A");
		System.out.print(store_tagVO.getStore_id() + ", ");
		System.out.print(store_tagVO.getTag_id() + ", ");
		System.out.print(store_tagVO.getTag_num() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<Store_tagVO> list = dao.getAll();
		for (Store_tagVO aStore_tagVO : list) {
			System.out.print(aStore_tagVO.getStore_id() + ", ");
			System.out.print(aStore_tagVO.getTag_id() + ", ");
			System.out.print(aStore_tagVO.getTag_num() + ", ");
			System.out.println();
		}

*/
	}


}