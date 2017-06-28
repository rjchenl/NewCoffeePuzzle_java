package com.feature.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;
import com.auth.model.AuthVO;

public class FeatureJDBCDAO implements FeatureDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO FEATURE (FEATURE_ID,FEATURE_NAME) VALUES ('FEATURE' || LPAD(to_char(FEATURE_ID_SQ.NEXTVAL), 8, '0'), ?)";
	private static final String GET_ALL_STMT = "SELECT FEATURE_ID,FEATURE_NAME FROM FEATURE ORDER BY FEATURE_ID";
	private static final String GET_ONE_STMT = "SELECT FEATURE_ID,FEATURE_NAME FROM FEATURE WHERE FEATURE_ID = ?";
	private static final String GET_Auths_ByFeature_id_STMT = "SELECT ADMIN_ID,FEATURE_ID FROM AUTH WHERE FEATURE_ID = ? ORDER BY ADMIN_ID,FEATURE_ID";

	private static final String DELETE_AUTHs = "DELETE FROM AUTH WHERE FEATURE_ID = ?";
	private static final String DELETE_FEATURE = "DELETE FROM FEATURE WHERE FEATURE_ID = ?";
	private static final String UPDATE = "UPDATE FEATURE SET FEATURE_NAME=? WHERE FEATURE_ID = ?";

	@Override
	public void insert(FeatureVO featureVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, featureVO.getFeature_name());

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
	public void update(FeatureVO featureVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, featureVO.getFeature_name());
			pstmt.setString(2, featureVO.getFeature_id());

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
	public void delete(String feature_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_AUTHs);

			pstmt.setString(1, feature_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_FEATURE);

			pstmt.setString(1, feature_id);

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
	public FeatureVO findByPrimaryKey(String feature_id) {

		FeatureVO featureVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, feature_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				featureVO = new FeatureVO();
				featureVO.setFeature_id(rs.getString("feature_id"));
				featureVO.setFeature_name(rs.getString("feature_name"));
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
		return featureVO;
	}

	@Override
	public List<FeatureVO> getAll() {

		List<FeatureVO> list = new ArrayList<FeatureVO>();
		FeatureVO featureVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				featureVO = new FeatureVO();
				featureVO.setFeature_id(rs.getString("feature_id"));
				featureVO.setFeature_name(rs.getString("feature_name"));
				list.add(featureVO); // Store the row in the list
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
	public Set<AuthVO> getAuthsByFeature_id(String feature_id){
		Set<AuthVO> set = new LinkedHashSet<AuthVO>();
		AuthVO authVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Auths_ByFeature_id_STMT);
			pstmt.setString(1, feature_id);

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

	public static void main(String[] args) {

		FeatureJDBCDAO dao = new FeatureJDBCDAO();

/*
		// insert()
		FeatureVO featureVO = new FeatureVO();
		featureVO.setFeature_id("A");
		featureVO.setFeature_name("A");
		dao.insert(featureVO);

		// update()
		FeatureVO featureVO = new FeatureVO();
		featureVO.setFeature_id("A");
		featureVO.setFeature_name("A");
		dao.update(featureVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		FeatureVO featureVO = dao.findByPrimaryKey("A");
		System.out.print(featureVO.getFeature_id() + ", ");
		System.out.print(featureVO.getFeature_name() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<FeatureVO> list = dao.getAll();
		for (FeatureVO aFeatureVO : list) {
			System.out.print(aFeatureVO.getFeature_id() + ", ");
			System.out.print(aFeatureVO.getFeature_name() + ", ");
			System.out.println();
		}

		Set<AuthVO> set = dao.getAuthsByFeature_id("A");
		for (AuthVO aAuth : set) {
			System.out.print(aAuth.getAdmin_id() + ", ");
			System.out.print(aAuth.getFeature_id() + ", ");
			System.out.println();
		}

*/
	}


}