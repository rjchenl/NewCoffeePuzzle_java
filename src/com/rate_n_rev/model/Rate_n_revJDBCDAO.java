package com.rate_n_rev.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedHashSet;
import java.util.Set;
import com.report.model.ReportVO;

public class Rate_n_revJDBCDAO implements Rate_n_revDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO RATE_N_REV (RNR_ID,MEM_ID,STORE_ID,RNR_RATE,RNR_REV,RNR_DATE) VALUES ('RNR' || LPAD(to_char(RNR_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT RNR_ID,MEM_ID,STORE_ID,RNR_RATE,RNR_REV,RNR_DATE FROM RATE_N_REV ORDER BY RNR_ID";
	private static final String GET_ONE_STMT = "SELECT RNR_ID,MEM_ID,STORE_ID,RNR_RATE,RNR_REV,RNR_DATE FROM RATE_N_REV WHERE RNR_ID = ?";
	private static final String GET_Reports_ByRnr_id_STMT = "SELECT MEM_ID,RNR_ID,REPT_VERF,REPT_RSN FROM REPORT WHERE RNR_ID = ? ORDER BY MEM_ID,RNR_ID";

	private static final String DELETE_REPORTs = "DELETE FROM REPORT WHERE RNR_ID = ?";
	private static final String DELETE_RATE_N_REV = "DELETE FROM RATE_N_REV WHERE RNR_ID = ?";
	private static final String UPDATE = "UPDATE RATE_N_REV SET MEM_ID=?, STORE_ID=?, RNR_RATE=?, RNR_REV=?, RNR_DATE=? WHERE RNR_ID = ?";

	@Override
	public void insert(Rate_n_revVO rate_n_revVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, rate_n_revVO.getMem_id());
			pstmt.setString(2, rate_n_revVO.getStore_id());
			pstmt.setInt(3, rate_n_revVO.getRnr_rate());
			pstmt.setCharacterStream(4, stringToReader(rate_n_revVO.getRnr_rev()));
			pstmt.setTimestamp(5, rate_n_revVO.getRnr_date());

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
	public void update(Rate_n_revVO rate_n_revVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, rate_n_revVO.getMem_id());
			pstmt.setString(2, rate_n_revVO.getStore_id());
			pstmt.setInt(3, rate_n_revVO.getRnr_rate());
			pstmt.setCharacterStream(4, stringToReader(rate_n_revVO.getRnr_rev()));
			pstmt.setTimestamp(5, rate_n_revVO.getRnr_date());
			pstmt.setString(6, rate_n_revVO.getRnr_id());

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
	public void delete(String rnr_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_REPORTs);

			pstmt.setString(1, rnr_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_RATE_N_REV);

			pstmt.setString(1, rnr_id);

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
	public Rate_n_revVO findByPrimaryKey(String rnr_id) {

		Rate_n_revVO rate_n_revVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, rnr_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rate_n_revVO = new Rate_n_revVO();
				rate_n_revVO.setRnr_id(rs.getString("rnr_id"));
				rate_n_revVO.setMem_id(rs.getString("mem_id"));
				rate_n_revVO.setStore_id(rs.getString("store_id"));
				rate_n_revVO.setRnr_rate(rs.getInt("rnr_rate"));
				rate_n_revVO.setRnr_rev(readerToString(rs.getCharacterStream("rnr_rev")));
				rate_n_revVO.setRnr_date(rs.getTimestamp("rnr_date"));
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
		return rate_n_revVO;
	}

	@Override
	public List<Rate_n_revVO> getAll() {

		List<Rate_n_revVO> list = new ArrayList<Rate_n_revVO>();
		Rate_n_revVO rate_n_revVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rate_n_revVO = new Rate_n_revVO();
				rate_n_revVO.setRnr_id(rs.getString("rnr_id"));
				rate_n_revVO.setMem_id(rs.getString("mem_id"));
				rate_n_revVO.setStore_id(rs.getString("store_id"));
				rate_n_revVO.setRnr_rate(rs.getInt("rnr_rate"));
				rate_n_revVO.setRnr_rev(readerToString(rs.getCharacterStream("rnr_rev")));
				rate_n_revVO.setRnr_date(rs.getTimestamp("rnr_date"));
				list.add(rate_n_revVO); // Store the row in the list
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
	public Set<ReportVO> getReportsByRnr_id(String rnr_id){
		Set<ReportVO> set = new LinkedHashSet<ReportVO>();
		ReportVO reportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Reports_ByRnr_id_STMT);
			pstmt.setString(1, rnr_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportVO = new ReportVO();
				reportVO.setMem_id(rs.getString("mem_id"));
				reportVO.setRnr_id(rs.getString("rnr_id"));
				reportVO.setRept_verf(rs.getInt("rept_verf"));
				reportVO.setRept_rsn(readerToString(rs.getCharacterStream("rept_rsn")));
				set.add(reportVO); // Store the row in the vector
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

		Rate_n_revJDBCDAO dao = new Rate_n_revJDBCDAO();

/*
		// insert()
		Rate_n_revVO rate_n_revVO = new Rate_n_revVO();
		rate_n_revVO.setRnr_id("A");
		rate_n_revVO.setMem_id("A");
		rate_n_revVO.setStore_id("A");
		rate_n_revVO.setRnr_rate(1);
		rate_n_revVO.setRnr_rev("A");
		rate_n_revVO.setRnr_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		dao.insert(rate_n_revVO);

		// update()
		Rate_n_revVO rate_n_revVO = new Rate_n_revVO();
		rate_n_revVO.setRnr_id("A");
		rate_n_revVO.setMem_id("A");
		rate_n_revVO.setStore_id("A");
		rate_n_revVO.setRnr_rate(1);
		rate_n_revVO.setRnr_rev("A");
		rate_n_revVO.setRnr_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		dao.update(rate_n_revVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		Rate_n_revVO rate_n_revVO = dao.findByPrimaryKey("A");
		System.out.print(rate_n_revVO.getRnr_id() + ", ");
		System.out.print(rate_n_revVO.getMem_id() + ", ");
		System.out.print(rate_n_revVO.getStore_id() + ", ");
		System.out.print(rate_n_revVO.getRnr_rate() + ", ");
		System.out.print(rate_n_revVO.getRnr_rev() + ", ");
		System.out.print(rate_n_revVO.getRnr_date() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<Rate_n_revVO> list = dao.getAll();
		for (Rate_n_revVO aRate_n_revVO : list) {
			System.out.print(aRate_n_revVO.getRnr_id() + ", ");
			System.out.print(aRate_n_revVO.getMem_id() + ", ");
			System.out.print(aRate_n_revVO.getStore_id() + ", ");
			System.out.print(aRate_n_revVO.getRnr_rate() + ", ");
			System.out.print(aRate_n_revVO.getRnr_rev() + ", ");
			System.out.print(aRate_n_revVO.getRnr_date() + ", ");
			System.out.println();
		}

		Set<ReportVO> set = dao.getReportsByRnr_id("A");
		for (ReportVO aReport : set) {
			System.out.print(aReport.getMem_id() + ", ");
			System.out.print(aReport.getRnr_id() + ", ");
			System.out.print(aReport.getRept_verf() + ", ");
			System.out.print(aReport.getRept_rsn() + ", ");
			System.out.println();
		}

*/
	}


}