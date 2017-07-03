package com.fav_store.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Fav_storeJDBCDAO implements Fav_storeDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO FAV_STORE (MEM_ID,STORE_ID) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT MEM_ID,STORE_ID FROM FAV_STORE ORDER BY MEM_ID,STORE_ID";
	private static final String GET_ONE_STMT = "SELECT MEM_ID,STORE_ID FROM FAV_STORE WHERE MEM_ID = ? AND STORE_ID = ?";
	private static final String DELETE_FAV_STORE = "DELETE FROM FAV_STORE WHERE MEM_ID = ? AND STORE_ID = ?";

	@Override
	public void insert(Fav_storeVO fav_storeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, fav_storeVO.getMem_id());
			pstmt.setString(2, fav_storeVO.getStore_id());

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
	public void delete(String mem_id, String store_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_FAV_STORE);

			pstmt.setString(1, mem_id);
			pstmt.setString(2, store_id);

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
	public Fav_storeVO findByPrimaryKey(String mem_id, String store_id) {

		Fav_storeVO fav_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_id);
			pstmt.setString(2, store_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				fav_storeVO = new Fav_storeVO();
				fav_storeVO.setMem_id(rs.getString("mem_id"));
				fav_storeVO.setStore_id(rs.getString("store_id"));
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
		return fav_storeVO;
	}

	@Override
	public List<Fav_storeVO> getAll() {

		List<Fav_storeVO> list = new ArrayList<Fav_storeVO>();
		Fav_storeVO fav_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				fav_storeVO = new Fav_storeVO();
				fav_storeVO.setMem_id(rs.getString("mem_id"));
				fav_storeVO.setStore_id(rs.getString("store_id"));
				list.add(fav_storeVO); // Store the row in the list
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

		Fav_storeJDBCDAO dao = new Fav_storeJDBCDAO();

/*
		// insert()
		Fav_storeVO fav_storeVO = new Fav_storeVO();
		fav_storeVO.setMem_id("A");
		fav_storeVO.setStore_id("A");
		dao.insert(fav_storeVO);

		// delete()
		dao.delete("A","A");

		// findByPrimaryKey()
		Fav_storeVO fav_storeVO = dao.findByPrimaryKey("A","A");
		System.out.print(fav_storeVO.getMem_id() + ", ");
		System.out.print(fav_storeVO.getStore_id() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<Fav_storeVO> list = dao.getAll();
		for (Fav_storeVO aFav_storeVO : list) {
			System.out.print(aFav_storeVO.getMem_id() + ", ");
			System.out.print(aFav_storeVO.getStore_id() + ", ");
			System.out.println();
		}

*/
	}


}