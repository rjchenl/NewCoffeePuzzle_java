package com.report.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class ReportJDBCDAO implements ReportDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO REPORT (MEM_ID,RNR_ID,REPT_VERF,REPT_RSN) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT MEM_ID,RNR_ID,REPT_VERF,REPT_RSN FROM REPORT ORDER BY MEM_ID,RNR_ID";
	private static final String GET_ONE_STMT = "SELECT MEM_ID,RNR_ID,REPT_VERF,REPT_RSN FROM REPORT WHERE MEM_ID = ? AND RNR_ID = ?";
	private static final String DELETE_REPORT = "DELETE FROM REPORT WHERE MEM_ID = ? AND RNR_ID = ?";
	private static final String UPDATE = "UPDATE REPORT SET REPT_VERF=?, REPT_RSN=? WHERE MEM_ID = ? AND RNR_ID = ?";

	@Override
	public void insert(ReportVO reportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, reportVO.getMem_id());
			pstmt.setString(2, reportVO.getRnr_id());
			pstmt.setInt(3, reportVO.getRept_verf());
			pstmt.setCharacterStream(4, stringToReader(reportVO.getRept_rsn()));

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
	public void update(ReportVO reportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, reportVO.getRept_verf());
			pstmt.setCharacterStream(2, stringToReader(reportVO.getRept_rsn()));
			pstmt.setString(3, reportVO.getMem_id());
			pstmt.setString(4, reportVO.getRnr_id());

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
	public void delete(String mem_id, String rnr_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_REPORT);

			pstmt.setString(1, mem_id);
			pstmt.setString(2, rnr_id);

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
	public ReportVO findByPrimaryKey(String mem_id, String rnr_id) {

		ReportVO reportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_id);
			pstmt.setString(2, rnr_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportVO = new ReportVO();
				reportVO.setMem_id(rs.getString("mem_id"));
				reportVO.setRnr_id(rs.getString("rnr_id"));
				reportVO.setRept_verf(rs.getInt("rept_verf"));
				reportVO.setRept_rsn(readerToString(rs.getCharacterStream("rept_rsn")));
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
		return reportVO;
	}

	@Override
	public List<ReportVO> getAll() {

		List<ReportVO> list = new ArrayList<ReportVO>();
		ReportVO reportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportVO = new ReportVO();
				reportVO.setMem_id(rs.getString("mem_id"));
				reportVO.setRnr_id(rs.getString("rnr_id"));
				reportVO.setRept_verf(rs.getInt("rept_verf"));
				reportVO.setRept_rsn(readerToString(rs.getCharacterStream("rept_rsn")));
				list.add(reportVO); // Store the row in the list
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

		ReportJDBCDAO dao = new ReportJDBCDAO();

/*
		// insert()
		ReportVO reportVO = new ReportVO();
		reportVO.setMem_id("A");
		reportVO.setRnr_id("A");
		reportVO.setRept_verf(1);
		reportVO.setRept_rsn("A");
		dao.insert(reportVO);

		// update()
		ReportVO reportVO = new ReportVO();
		reportVO.setMem_id("A");
		reportVO.setRnr_id("A");
		reportVO.setRept_verf(1);
		reportVO.setRept_rsn("A");
		dao.update(reportVO);

		// delete()
		dao.delete("A","A");

		// findByPrimaryKey()
		ReportVO reportVO = dao.findByPrimaryKey("A","A");
		System.out.print(reportVO.getMem_id() + ", ");
		System.out.print(reportVO.getRnr_id() + ", ");
		System.out.print(reportVO.getRept_verf() + ", ");
		System.out.print(reportVO.getRept_rsn() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<ReportVO> list = dao.getAll();
		for (ReportVO aReportVO : list) {
			System.out.print(aReportVO.getMem_id() + ", ");
			System.out.print(aReportVO.getRnr_id() + ", ");
			System.out.print(aReportVO.getRept_verf() + ", ");
			System.out.print(aReportVO.getRept_rsn() + ", ");
			System.out.println();
		}

*/
	}


}