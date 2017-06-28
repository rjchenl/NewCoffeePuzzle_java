package com.admin.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.LinkedHashSet;
import java.util.Set;
import com.auth.model.AuthVO;

public class AdminJNDIDAO implements AdminDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, adminVO.getAdmin_acct());
			pstmt.setString(2, adminVO.getAdmin_pwd());
			pstmt.setString(3, adminVO.getAdmin_name());
			pstmt.setString(4, adminVO.getAdmin_email());
			pstmt.setInt(5, adminVO.getAdmin_employed());
			pstmt.setBytes(6, adminVO.getAdmin_img());

			pstmt.executeUpdate();

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, adminVO.getAdmin_acct());
			pstmt.setString(2, adminVO.getAdmin_pwd());
			pstmt.setString(3, adminVO.getAdmin_name());
			pstmt.setString(4, adminVO.getAdmin_email());
			pstmt.setInt(5, adminVO.getAdmin_employed());
			pstmt.setBytes(6, adminVO.getAdmin_img());
			pstmt.setString(7, adminVO.getAdmin_id());

			pstmt.executeUpdate();

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
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_AUTHs);

			pstmt.setString(1, admin_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ADMIN);

			pstmt.setString(1, admin_id);

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Auths_ByAdmin_id_STMT);
			pstmt.setString(1, admin_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				authVO = new AuthVO();
				authVO.setAdmin_id(rs.getString("admin_id"));
				authVO.setFeature_id(rs.getString("feature_id"));
				set.add(authVO); // Store the row in the vector
			}

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


}