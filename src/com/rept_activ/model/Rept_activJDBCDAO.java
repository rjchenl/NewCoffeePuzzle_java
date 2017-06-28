package com.rept_activ.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Rept_activJDBCDAO implements Rept_activDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO REPT_ACTIV (ACTIV_ID,MEM_ID,REPO_RSN,REPO_REV) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ACTIV_ID,MEM_ID,REPO_RSN,REPO_REV FROM REPT_ACTIV ORDER BY ACTIV_ID,MEM_ID";
	private static final String GET_ONE_STMT = "SELECT ACTIV_ID,MEM_ID,REPO_RSN,REPO_REV FROM REPT_ACTIV WHERE ACTIV_ID = ? AND MEM_ID = ?";
	private static final String DELETE_REPT_ACTIV = "DELETE FROM REPT_ACTIV WHERE ACTIV_ID = ? AND MEM_ID = ?";
	private static final String UPDATE = "UPDATE REPT_ACTIV SET REPO_RSN=?, REPO_REV=? WHERE ACTIV_ID = ? AND MEM_ID = ?";

	@Override
	public void insert(Rept_activVO rept_activVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, rept_activVO.getActiv_id());
			pstmt.setString(2, rept_activVO.getMem_id());
			pstmt.setCharacterStream(3, stringToReader(rept_activVO.getRepo_rsn()));
			pstmt.setInt(4, rept_activVO.getRepo_rev());

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
	public void update(Rept_activVO rept_activVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setCharacterStream(1, stringToReader(rept_activVO.getRepo_rsn()));
			pstmt.setInt(2, rept_activVO.getRepo_rev());
			pstmt.setString(3, rept_activVO.getActiv_id());
			pstmt.setString(4, rept_activVO.getMem_id());

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
	public void delete(String activ_id, String mem_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_REPT_ACTIV);

			pstmt.setString(1, activ_id);
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
	public Rept_activVO findByPrimaryKey(String activ_id, String mem_id) {

		Rept_activVO rept_activVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, activ_id);
			pstmt.setString(2, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_activVO = new Rept_activVO();
				rept_activVO.setActiv_id(rs.getString("activ_id"));
				rept_activVO.setMem_id(rs.getString("mem_id"));
				rept_activVO.setRepo_rsn(readerToString(rs.getCharacterStream("repo_rsn")));
				rept_activVO.setRepo_rev(rs.getInt("repo_rev"));
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
		return rept_activVO;
	}

	@Override
	public List<Rept_activVO> getAll() {

		List<Rept_activVO> list = new ArrayList<Rept_activVO>();
		Rept_activVO rept_activVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_activVO = new Rept_activVO();
				rept_activVO.setActiv_id(rs.getString("activ_id"));
				rept_activVO.setMem_id(rs.getString("mem_id"));
				rept_activVO.setRepo_rsn(readerToString(rs.getCharacterStream("repo_rsn")));
				rept_activVO.setRepo_rev(rs.getInt("repo_rev"));
				list.add(rept_activVO); // Store the row in the list
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

		Rept_activJDBCDAO dao = new Rept_activJDBCDAO();

/*
		// insert()
		Rept_activVO rept_activVO = new Rept_activVO();
		rept_activVO.setActiv_id("A");
		rept_activVO.setMem_id("A");
		rept_activVO.setRepo_rsn("A");
		rept_activVO.setRepo_rev(1);
		dao.insert(rept_activVO);

		// update()
		Rept_activVO rept_activVO = new Rept_activVO();
		rept_activVO.setActiv_id("A");
		rept_activVO.setMem_id("A");
		rept_activVO.setRepo_rsn("A");
		rept_activVO.setRepo_rev(1);
		dao.update(rept_activVO);

		// delete()
		dao.delete("A","A");

		// findByPrimaryKey()
		Rept_activVO rept_activVO = dao.findByPrimaryKey("A","A");
		System.out.print(rept_activVO.getActiv_id() + ", ");
		System.out.print(rept_activVO.getMem_id() + ", ");
		System.out.print(rept_activVO.getRepo_rsn() + ", ");
		System.out.print(rept_activVO.getRepo_rev() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<Rept_activVO> list = dao.getAll();
		for (Rept_activVO aRept_activVO : list) {
			System.out.print(aRept_activVO.getActiv_id() + ", ");
			System.out.print(aRept_activVO.getMem_id() + ", ");
			System.out.print(aRept_activVO.getRepo_rsn() + ", ");
			System.out.print(aRept_activVO.getRepo_rev() + ", ");
			System.out.println();
		}

*/
	}


}