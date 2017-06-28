package com.spndcoffeercd.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class SpndcoffeercdJNDIDAO implements SpndcoffeercdDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO SPNDCOFFEERCD (RCD_ID,LIST_ID,SINGLE_AMT,RCD_DATE) VALUES ('RCD' || LPAD(to_char(RCD_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT RCD_ID,LIST_ID,SINGLE_AMT,RCD_DATE FROM SPNDCOFFEERCD ORDER BY RCD_ID";
	private static final String GET_ONE_STMT = "SELECT RCD_ID,LIST_ID,SINGLE_AMT,RCD_DATE FROM SPNDCOFFEERCD WHERE RCD_ID = ?";
	private static final String DELETE_SPNDCOFFEERCD = "DELETE FROM SPNDCOFFEERCD WHERE RCD_ID = ?";
	private static final String UPDATE = "UPDATE SPNDCOFFEERCD SET LIST_ID=?, SINGLE_AMT=?, RCD_DATE=? WHERE RCD_ID = ?";

	@Override
	public void insert(SpndcoffeercdVO spndcoffeercdVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, spndcoffeercdVO.getList_id());
			pstmt.setInt(2, spndcoffeercdVO.getSingle_amt());
			pstmt.setTimestamp(3, spndcoffeercdVO.getRcd_date());

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
	public void update(SpndcoffeercdVO spndcoffeercdVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, spndcoffeercdVO.getList_id());
			pstmt.setInt(2, spndcoffeercdVO.getSingle_amt());
			pstmt.setTimestamp(3, spndcoffeercdVO.getRcd_date());
			pstmt.setString(4, spndcoffeercdVO.getRcd_id());

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
	public void delete(String rcd_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_SPNDCOFFEERCD);

			pstmt.setString(1, rcd_id);

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
	public SpndcoffeercdVO findByPrimaryKey(String rcd_id) {

		SpndcoffeercdVO spndcoffeercdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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


}