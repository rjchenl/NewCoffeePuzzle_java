package com.ming.spndcoffeercd.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class SpndcoffeercdJDBCDAO implements SpndcoffeercdDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO SPNDCOFFEERCD (RCD_ID,LIST_ID,SINGLE_AMT,RCD_DATE) VALUES ('RCD' || LPAD(to_char(RCD_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?)";
	private static final String INSERT_CD = "INSERT INTO SPNDCOFFEERCD (RCD_ID,LIST_ID,SINGLE_AMT,RCD_DATE) VALUES ('RCD' || LPAD(to_char(RCD_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT RCD_ID,LIST_ID,SINGLE_AMT,RCD_DATE FROM SPNDCOFFEERCD ORDER BY RCD_ID";
	private static final String GET_ONE_STMT = "SELECT RCD_ID,LIST_ID,SINGLE_AMT,RCD_DATE FROM SPNDCOFFEERCD WHERE RCD_ID = ?";
	private static final String DELETE_SPNDCOFFEERCD = "DELETE FROM SPNDCOFFEERCD WHERE RCD_ID = ?";
	private static final String UPDATE = "UPDATE SPNDCOFFEERCD SET LIST_ID=?, SINGLE_AMT=?, RCD_DATE=? WHERE RCD_ID = ?";

	@Override
	public void insert(SpndcoffeercdVO spndcoffeercdVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, spndcoffeercdVO.getList_id());
			pstmt.setInt(2, spndcoffeercdVO.getSingle_amt());
			pstmt.setTimestamp(3, spndcoffeercdVO.getRcd_date());

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
	public void getCDInser(String list_id, Integer single_amt, Timestamp rcd_date) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_CD);

			pstmt.setString(1, list_id);
			pstmt.setInt(2, single_amt);
			pstmt.setTimestamp(3,rcd_date);

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
	public void update(SpndcoffeercdVO spndcoffeercdVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, spndcoffeercdVO.getList_id());
			pstmt.setInt(2, spndcoffeercdVO.getSingle_amt());
			pstmt.setTimestamp(3, spndcoffeercdVO.getRcd_date());
			pstmt.setString(4, spndcoffeercdVO.getRcd_id());

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
	public void delete(String rcd_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_SPNDCOFFEERCD);

			pstmt.setString(1, rcd_id);

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
	public SpndcoffeercdVO findByPrimaryKey(String rcd_id) {

		SpndcoffeercdVO spndcoffeercdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, rcd_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				spndcoffeercdVO = new SpndcoffeercdVO();
				spndcoffeercdVO.setRcd_id(rs.getString("rcd_id"));
				spndcoffeercdVO.setList_id(rs.getString("list_id"));
				spndcoffeercdVO.setSingle_amt(rs.getInt("single_amt"));
				spndcoffeercdVO.setRcd_date(rs.getTimestamp("rcd_date"));
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
		return spndcoffeercdVO;
	}

	@Override
	public List<SpndcoffeercdVO> getAll() {

		List<SpndcoffeercdVO> list = new ArrayList<SpndcoffeercdVO>();
		SpndcoffeercdVO spndcoffeercdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				spndcoffeercdVO = new SpndcoffeercdVO();
				spndcoffeercdVO.setRcd_id(rs.getString("rcd_id"));
				spndcoffeercdVO.setList_id(rs.getString("list_id"));
				spndcoffeercdVO.setSingle_amt(rs.getInt("single_amt"));
				spndcoffeercdVO.setRcd_date(rs.getTimestamp("rcd_date"));
				list.add(spndcoffeercdVO); // Store the row in the list
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

		SpndcoffeercdJDBCDAO dao = new SpndcoffeercdJDBCDAO();

/*
		// insert()
		SpndcoffeercdVO spndcoffeercdVO = new SpndcoffeercdVO();
		spndcoffeercdVO.setRcd_id("A");
		spndcoffeercdVO.setList_id("A");
		spndcoffeercdVO.setSingle_amt(1);
		spndcoffeercdVO.setRcd_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		dao.insert(spndcoffeercdVO);

		// update()
		SpndcoffeercdVO spndcoffeercdVO = new SpndcoffeercdVO();
		spndcoffeercdVO.setRcd_id("A");
		spndcoffeercdVO.setList_id("A");
		spndcoffeercdVO.setSingle_amt(1);
		spndcoffeercdVO.setRcd_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		dao.update(spndcoffeercdVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		SpndcoffeercdVO spndcoffeercdVO = dao.findByPrimaryKey("A");
		System.out.print(spndcoffeercdVO.getRcd_id() + ", ");
		System.out.print(spndcoffeercdVO.getList_id() + ", ");
		System.out.print(spndcoffeercdVO.getSingle_amt() + ", ");
		System.out.print(spndcoffeercdVO.getRcd_date() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<SpndcoffeercdVO> list = dao.getAll();
		for (SpndcoffeercdVO aSpndcoffeercdVO : list) {
			System.out.print(aSpndcoffeercdVO.getRcd_id() + ", ");
			System.out.print(aSpndcoffeercdVO.getList_id() + ", ");
			System.out.print(aSpndcoffeercdVO.getSingle_amt() + ", ");
			System.out.print(aSpndcoffeercdVO.getRcd_date() + ", ");
			System.out.println();
		}

*/
	}


}