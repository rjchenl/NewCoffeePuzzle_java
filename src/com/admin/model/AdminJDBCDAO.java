package com.admin.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.util.LinkedHashSet;
import java.util.Set;
import com.auth.model.AuthVO;

public class AdminJDBCDAO implements AdminDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO ADMIN (ADMIN_ID,ADMIN_ACCT,ADMIN_PWD,ADMIN_NAME,ADMIN_EMAIL,ADMIN_EMPLOYED,ADMIN_IMG) VALUES ('ADMIN' || LPAD(to_char(ADMIN_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ADMIN_ID,ADMIN_ACCT,ADMIN_PWD,ADMIN_NAME,ADMIN_EMAIL,ADMIN_EMPLOYED,ADMIN_IMG FROM ADMIN ORDER BY ADMIN_ID";
	private static final String GET_ONE_STMT = "SELECT ADMIN_ID,ADMIN_ACCT,ADMIN_PWD,ADMIN_NAME,ADMIN_EMAIL,ADMIN_EMPLOYED,ADMIN_IMG FROM ADMIN WHERE ADMIN_ID = ?";
	private static final String GET_Auths_ByAdmin_id_STMT = "SELECT ADMIN_ID,FEATURE_ID FROM AUTH WHERE ADMIN_ID = ? ORDER BY ADMIN_ID,FEATURE_ID";

	private static final String DELETE_AUTHs = "DELETE FROM AUTH WHERE ADMIN_ID = ?";
	private static final String DELETE_ADMIN = "DELETE FROM ADMIN WHERE ADMIN_ID = ?";
	private static final String UPDATE = "UPDATE ADMIN SET ADMIN_ACCT=?, ADMIN_PWD=?, ADMIN_NAME=?, ADMIN_EMAIL=?, ADMIN_EMPLOYED=?, ADMIN_IMG=? WHERE ADMIN_ID = ?";

	@Override
	public void insert(AdminVO adminVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, adminVO.getAdmin_acct());
			pstmt.setString(2, adminVO.getAdmin_pwd());
			pstmt.setString(3, adminVO.getAdmin_name());
			pstmt.setString(4, adminVO.getAdmin_email());
			pstmt.setInt(5, adminVO.getAdmin_employed());
			pstmt.setBytes(6, adminVO.getAdmin_img());

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
	public void update(AdminVO adminVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, adminVO.getAdmin_acct());
			pstmt.setString(2, adminVO.getAdmin_pwd());
			pstmt.setString(3, adminVO.getAdmin_name());
			pstmt.setString(4, adminVO.getAdmin_email());
			pstmt.setInt(5, adminVO.getAdmin_employed());
			pstmt.setBytes(6, adminVO.getAdmin_img());
			pstmt.setString(7, adminVO.getAdmin_id());

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
	public void delete(String admin_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_AUTHs);

			pstmt.setString(1, admin_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ADMIN);

			pstmt.setString(1, admin_id);

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
	public AdminVO findByPrimaryKey(String admin_id) {

		AdminVO adminVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, admin_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				adminVO = new AdminVO();
				adminVO.setAdmin_id(rs.getString("admin_id"));
				adminVO.setAdmin_acct(rs.getString("admin_acct"));
				adminVO.setAdmin_pwd(rs.getString("admin_pwd"));
				adminVO.setAdmin_name(rs.getString("admin_name"));
				adminVO.setAdmin_email(rs.getString("admin_email"));
				adminVO.setAdmin_employed(rs.getInt("admin_employed"));
				adminVO.setAdmin_img(rs.getBytes("admin_img"));
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
		return adminVO;
	}

	@Override
	public List<AdminVO> getAll() {

		List<AdminVO> list = new ArrayList<AdminVO>();
		AdminVO adminVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				adminVO = new AdminVO();
				adminVO.setAdmin_id(rs.getString("admin_id"));
				adminVO.setAdmin_acct(rs.getString("admin_acct"));
				adminVO.setAdmin_pwd(rs.getString("admin_pwd"));
				adminVO.setAdmin_name(rs.getString("admin_name"));
				adminVO.setAdmin_email(rs.getString("admin_email"));
				adminVO.setAdmin_employed(rs.getInt("admin_employed"));
				adminVO.setAdmin_img(rs.getBytes("admin_img"));
				list.add(adminVO); // Store the row in the list
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
	public Set<AuthVO> getAuthsByAdmin_id(String admin_id){
		Set<AuthVO> set = new LinkedHashSet<AuthVO>();
		AuthVO authVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Auths_ByAdmin_id_STMT);
			pstmt.setString(1, admin_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				authVO = new AuthVO();
				authVO.setAdmin_id(rs.getString("admin_id"));
				authVO.setFeature_id(rs.getString("feature_id"));
				set.add(authVO); // Store the row in the vector
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

		AdminJDBCDAO dao = new AdminJDBCDAO();

/*
		// insert()
		AdminVO adminVO = new AdminVO();
		adminVO.setAdmin_id("A");
		adminVO.setAdmin_acct("A");
		adminVO.setAdmin_pwd("A");
		adminVO.setAdmin_name("A");
		adminVO.setAdmin_email("A");
		adminVO.setAdmin_employed(1);
		adminVO.setAdmin_img(getPictureByteArray("D:/temp/tomcat.gif"));
		dao.insert(adminVO);

		// update()
		AdminVO adminVO = new AdminVO();
		adminVO.setAdmin_id("A");
		adminVO.setAdmin_acct("A");
		adminVO.setAdmin_pwd("A");
		adminVO.setAdmin_name("A");
		adminVO.setAdmin_email("A");
		adminVO.setAdmin_employed(1);
		adminVO.setAdmin_img(getPictureByteArray("D:/temp/tomcat.gif"));
		dao.update(adminVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		AdminVO adminVO = dao.findByPrimaryKey("A");
		System.out.print(adminVO.getAdmin_id() + ", ");
		System.out.print(adminVO.getAdmin_acct() + ", ");
		System.out.print(adminVO.getAdmin_pwd() + ", ");
		System.out.print(adminVO.getAdmin_name() + ", ");
		System.out.print(adminVO.getAdmin_email() + ", ");
		System.out.print(adminVO.getAdmin_employed() + ", ");
		System.out.print(adminVO.getAdmin_img() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<AdminVO> list = dao.getAll();
		for (AdminVO aAdminVO : list) {
			System.out.print(aAdminVO.getAdmin_id() + ", ");
			System.out.print(aAdminVO.getAdmin_acct() + ", ");
			System.out.print(aAdminVO.getAdmin_pwd() + ", ");
			System.out.print(aAdminVO.getAdmin_name() + ", ");
			System.out.print(aAdminVO.getAdmin_email() + ", ");
			System.out.print(aAdminVO.getAdmin_employed() + ", ");
			System.out.print(aAdminVO.getAdmin_img() + ", ");
			System.out.println();
		}

		Set<AuthVO> set = dao.getAuthsByAdmin_id("A");
		for (AuthVO aAuth : set) {
			System.out.print(aAuth.getAdmin_id() + ", ");
			System.out.print(aAuth.getFeature_id() + ", ");
			System.out.println();
		}

*/
	}


}