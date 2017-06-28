package com.auth.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class AuthJDBCDAO implements AuthDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO AUTH (ADMIN_ID,FEATURE_ID) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT ADMIN_ID,FEATURE_ID FROM AUTH ORDER BY ADMIN_ID,FEATURE_ID";
	private static final String GET_ONE_STMT = "SELECT ADMIN_ID,FEATURE_ID FROM AUTH WHERE ADMIN_ID = ? AND FEATURE_ID = ?";
	private static final String DELETE_AUTH = "DELETE FROM AUTH WHERE ADMIN_ID = ? AND FEATURE_ID = ?";

	@Override
	public void insert(AuthVO authVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, authVO.getAdmin_id());
			pstmt.setString(2, authVO.getFeature_id());

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
	public void delete(String admin_id, String feature_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_AUTH);

			pstmt.setString(1, admin_id);
			pstmt.setString(2, feature_id);

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
	public AuthVO findByPrimaryKey(String admin_id, String feature_id) {

		AuthVO authVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, admin_id);
			pstmt.setString(2, feature_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				authVO = new AuthVO();
				authVO.setAdmin_id(rs.getString("admin_id"));
				authVO.setFeature_id(rs.getString("feature_id"));
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
		return authVO;
	}

	@Override
	public List<AuthVO> getAll() {

		List<AuthVO> list = new ArrayList<AuthVO>();
		AuthVO authVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				authVO = new AuthVO();
				authVO.setAdmin_id(rs.getString("admin_id"));
				authVO.setFeature_id(rs.getString("feature_id"));
				list.add(authVO); // Store the row in the list
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

		AuthJDBCDAO dao = new AuthJDBCDAO();

/*
		// insert()
		AuthVO authVO = new AuthVO();
		authVO.setAdmin_id("A");
		authVO.setFeature_id("A");
		dao.insert(authVO);

		// delete()
		dao.delete("A","A");

		// findByPrimaryKey()
		AuthVO authVO = dao.findByPrimaryKey("A","A");
		System.out.print(authVO.getAdmin_id() + ", ");
		System.out.print(authVO.getFeature_id() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<AuthVO> list = dao.getAll();
		for (AuthVO aAuthVO : list) {
			System.out.print(aAuthVO.getAdmin_id() + ", ");
			System.out.print(aAuthVO.getFeature_id() + ", ");
			System.out.println();
		}

*/
	}


}