package com.rept_store.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Rept_storeJDBCDAO implements Rept_storeDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO REPT_STORE (STORE_ID,MEM_ID,REPT_RSN,REPT_REV) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT STORE_ID,MEM_ID,REPT_RSN,REPT_REV FROM REPT_STORE ORDER BY STORE_ID,MEM_ID";
	private static final String GET_ONE_STMT = "SELECT STORE_ID,MEM_ID,REPT_RSN,REPT_REV FROM REPT_STORE WHERE STORE_ID = ? AND MEM_ID = ?";
	private static final String DELETE_REPT_STORE = "DELETE FROM REPT_STORE WHERE STORE_ID = ? AND MEM_ID = ?";
	private static final String UPDATE = "UPDATE REPT_STORE SET REPT_RSN=?, REPT_REV=? WHERE STORE_ID = ? AND MEM_ID = ?";

	@Override
	public void insert(Rept_storeVO rept_storeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, rept_storeVO.getStore_id());
			pstmt.setString(2, rept_storeVO.getMem_id());
			pstmt.setCharacterStream(3, stringToReader(rept_storeVO.getRept_rsn()));
			pstmt.setInt(4, rept_storeVO.getRept_rev());

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
	public void update(Rept_storeVO rept_storeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setCharacterStream(1, stringToReader(rept_storeVO.getRept_rsn()));
			pstmt.setInt(2, rept_storeVO.getRept_rev());
			pstmt.setString(3, rept_storeVO.getStore_id());
			pstmt.setString(4, rept_storeVO.getMem_id());

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
	public void delete(String store_id, String mem_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_REPT_STORE);

			pstmt.setString(1, store_id);
			pstmt.setString(2, mem_id);

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
	public Rept_storeVO findByPrimaryKey(String store_id, String mem_id) {

		Rept_storeVO rept_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, store_id);
			pstmt.setString(2, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_storeVO = new Rept_storeVO();
				rept_storeVO.setStore_id(rs.getString("store_id"));
				rept_storeVO.setMem_id(rs.getString("mem_id"));
				rept_storeVO.setRept_rsn(readerToString(rs.getCharacterStream("rept_rsn")));
				rept_storeVO.setRept_rev(rs.getInt("rept_rev"));
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
		return rept_storeVO;
	}

	@Override
	public List<Rept_storeVO> getAll() {

		List<Rept_storeVO> list = new ArrayList<Rept_storeVO>();
		Rept_storeVO rept_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_storeVO = new Rept_storeVO();
				rept_storeVO.setStore_id(rs.getString("store_id"));
				rept_storeVO.setMem_id(rs.getString("mem_id"));
				rept_storeVO.setRept_rsn(readerToString(rs.getCharacterStream("rept_rsn")));
				rept_storeVO.setRept_rev(rs.getInt("rept_rev"));
				list.add(rept_storeVO); // Store the row in the list
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

	public static void main(String[] args) {

		Rept_storeJDBCDAO dao = new Rept_storeJDBCDAO();

/*
		// insert()
		Rept_storeVO rept_storeVO = new Rept_storeVO();
		rept_storeVO.setStore_id("A");
		rept_storeVO.setMem_id("A");
		rept_storeVO.setRept_rsn("A");
		rept_storeVO.setRept_rev(1);
		dao.insert(rept_storeVO);

		// update()
		Rept_storeVO rept_storeVO = new Rept_storeVO();
		rept_storeVO.setStore_id("A");
		rept_storeVO.setMem_id("A");
		rept_storeVO.setRept_rsn("A");
		rept_storeVO.setRept_rev(1);
		dao.update(rept_storeVO);

		// delete()
		dao.delete("A","A");

		// findByPrimaryKey()
		Rept_storeVO rept_storeVO = dao.findByPrimaryKey("A","A");
		System.out.print(rept_storeVO.getStore_id() + ", ");
		System.out.print(rept_storeVO.getMem_id() + ", ");
		System.out.print(rept_storeVO.getRept_rsn() + ", ");
		System.out.print(rept_storeVO.getRept_rev() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<Rept_storeVO> list = dao.getAll();
		for (Rept_storeVO aRept_storeVO : list) {
			System.out.print(aRept_storeVO.getStore_id() + ", ");
			System.out.print(aRept_storeVO.getMem_id() + ", ");
			System.out.print(aRept_storeVO.getRept_rsn() + ", ");
			System.out.print(aRept_storeVO.getRept_rev() + ", ");
			System.out.println();
		}

*/
	}


}