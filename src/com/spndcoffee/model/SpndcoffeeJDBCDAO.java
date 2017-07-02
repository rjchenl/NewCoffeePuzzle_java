package com.spndcoffee.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import com.rjchenl.spndcoffeelist.model.SpndcoffeelistVO;

public class SpndcoffeeJDBCDAO implements SpndcoffeeDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO SPNDCOFFEE (SPND_ID,STORE_ID,SPND_NAME,SPND_PROD,SPND_ENDDATE,SPND_AMT,SPND_IMG) VALUES ('SPND' || LPAD(to_char(SPND_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT SPND_ID,STORE_ID,SPND_NAME,SPND_PROD,to_char(SPND_ENDDATE,'yyyy-mm-dd') SPND_ENDDATE,SPND_AMT,SPND_IMG FROM SPNDCOFFEE ORDER BY SPND_ID";
	private static final String GET_ONE_STMT = "SELECT SPND_ID,STORE_ID,SPND_NAME,SPND_PROD,to_char(SPND_ENDDATE,'yyyy-mm-dd') SPND_ENDDATE,SPND_AMT,SPND_IMG FROM SPNDCOFFEE WHERE SPND_ID = ?";
	private static final String GET_Spndcoffeelists_BySpnd_id_STMT = "SELECT LIST_ID,SPND_ID,MEM_ID,SPND_PROD,STORE_ID,LIST_AMT,LIST_LEFT,LIST_DATE FROM SPNDCOFFEELIST WHERE SPND_ID = ? ORDER BY LIST_ID";

	private static final String DELETE_SPNDCOFFEELISTs = "DELETE FROM SPNDCOFFEELIST WHERE SPND_ID = ?";
	private static final String DELETE_SPNDCOFFEE = "DELETE FROM SPNDCOFFEE WHERE SPND_ID = ?";
	private static final String UPDATE = "UPDATE SPNDCOFFEE SET STORE_ID=?, SPND_NAME=?, SPND_PROD=?, SPND_ENDDATE=?, SPND_AMT=?, SPND_IMG=? WHERE SPND_ID = ?";

	@Override
	public void insert(SpndcoffeeVO spndcoffeeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, spndcoffeeVO.getStore_id());
			pstmt.setString(2, spndcoffeeVO.getSpnd_name());
			pstmt.setString(3, spndcoffeeVO.getSpnd_prod());
			pstmt.setDate(4, spndcoffeeVO.getSpnd_enddate());
			pstmt.setInt(5, spndcoffeeVO.getSpnd_amt());
			pstmt.setBytes(6, spndcoffeeVO.getSpnd_img());

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
	public void update(SpndcoffeeVO spndcoffeeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, spndcoffeeVO.getStore_id());
			pstmt.setString(2, spndcoffeeVO.getSpnd_name());
			pstmt.setString(3, spndcoffeeVO.getSpnd_prod());
			pstmt.setDate(4, spndcoffeeVO.getSpnd_enddate());
			pstmt.setInt(5, spndcoffeeVO.getSpnd_amt());
			pstmt.setBytes(6, spndcoffeeVO.getSpnd_img());
			pstmt.setString(7, spndcoffeeVO.getSpnd_id());

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
	public void delete(String spnd_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_SPNDCOFFEELISTs);

			pstmt.setString(1, spnd_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_SPNDCOFFEE);

			pstmt.setString(1, spnd_id);

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
	public SpndcoffeeVO findByPrimaryKey(String spnd_id) {

		SpndcoffeeVO spndcoffeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, spnd_id);

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
		return spndcoffeeVO;
	}

	@Override
	public List<SpndcoffeeVO> getAll() {

		List<SpndcoffeeVO> list = new ArrayList<SpndcoffeeVO>();
		SpndcoffeeVO spndcoffeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

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
				list.add(spndcoffeeVO); // Store the row in the list
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
	public Set<SpndcoffeelistVO> getSpndcoffeelistsBySpnd_id(String spnd_id){
		Set<SpndcoffeelistVO> set = new LinkedHashSet<SpndcoffeelistVO>();
		SpndcoffeelistVO spndcoffeelistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Spndcoffeelists_BySpnd_id_STMT);
			pstmt.setString(1, spnd_id);

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

		SpndcoffeeJDBCDAO dao = new SpndcoffeeJDBCDAO();

/*
		// insert()
		SpndcoffeeVO spndcoffeeVO = new SpndcoffeeVO();
		spndcoffeeVO.setSpnd_id("A");
		spndcoffeeVO.setStore_id("A");
		spndcoffeeVO.setSpnd_name("A");
		spndcoffeeVO.setSpnd_prod("A");
		spndcoffeeVO.setSpnd_enddate(java.sql.Date.valueOf("2007-12-03"));
		spndcoffeeVO.setSpnd_amt(1);
		spndcoffeeVO.setSpnd_img(getPictureByteArray("D:/temp/tomcat.gif"));
		dao.insert(spndcoffeeVO);

		// update()
		SpndcoffeeVO spndcoffeeVO = new SpndcoffeeVO();
		spndcoffeeVO.setSpnd_id("A");
		spndcoffeeVO.setStore_id("A");
		spndcoffeeVO.setSpnd_name("A");
		spndcoffeeVO.setSpnd_prod("A");
		spndcoffeeVO.setSpnd_enddate(java.sql.Date.valueOf("2007-12-03"));
		spndcoffeeVO.setSpnd_amt(1);
		spndcoffeeVO.setSpnd_img(getPictureByteArray("D:/temp/tomcat.gif"));
		dao.update(spndcoffeeVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		SpndcoffeeVO spndcoffeeVO = dao.findByPrimaryKey("A");
		System.out.print(spndcoffeeVO.getSpnd_id() + ", ");
		System.out.print(spndcoffeeVO.getStore_id() + ", ");
		System.out.print(spndcoffeeVO.getSpnd_name() + ", ");
		System.out.print(spndcoffeeVO.getSpnd_prod() + ", ");
		System.out.print(spndcoffeeVO.getSpnd_enddate() + ", ");
		System.out.print(spndcoffeeVO.getSpnd_amt() + ", ");
		System.out.print(spndcoffeeVO.getSpnd_img() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<SpndcoffeeVO> list = dao.getAll();
		for (SpndcoffeeVO aSpndcoffeeVO : list) {
			System.out.print(aSpndcoffeeVO.getSpnd_id() + ", ");
			System.out.print(aSpndcoffeeVO.getStore_id() + ", ");
			System.out.print(aSpndcoffeeVO.getSpnd_name() + ", ");
			System.out.print(aSpndcoffeeVO.getSpnd_prod() + ", ");
			System.out.print(aSpndcoffeeVO.getSpnd_enddate() + ", ");
			System.out.print(aSpndcoffeeVO.getSpnd_amt() + ", ");
			System.out.print(aSpndcoffeeVO.getSpnd_img() + ", ");
			System.out.println();
		}

		Set<SpndcoffeelistVO> set = dao.getSpndcoffeelistsBySpnd_id("A");
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

*/
	}


}