package com.rjchnel.spndcoffee.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.rjchenl.spndcoffeelist.model.SpndcoffeelistVO;

import java.util.LinkedHashSet;
import java.util.Set;

public class SpndcoffeeJNDIDAO implements SpndcoffeeDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, spndcoffeeVO.getStore_id());
			pstmt.setString(2, spndcoffeeVO.getSpnd_name());
			pstmt.setString(3, spndcoffeeVO.getSpnd_prod());
			pstmt.setDate(4, spndcoffeeVO.getSpnd_enddate());
			pstmt.setInt(5, spndcoffeeVO.getSpnd_amt());
			pstmt.setBytes(6, spndcoffeeVO.getSpnd_img());

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
	public void update(SpndcoffeeVO spndcoffeeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, spndcoffeeVO.getStore_id());
			pstmt.setString(2, spndcoffeeVO.getSpnd_name());
			pstmt.setString(3, spndcoffeeVO.getSpnd_prod());
			pstmt.setDate(4, spndcoffeeVO.getSpnd_enddate());
			pstmt.setInt(5, spndcoffeeVO.getSpnd_amt());
			pstmt.setBytes(6, spndcoffeeVO.getSpnd_img());
			pstmt.setString(7, spndcoffeeVO.getSpnd_id());

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
	public void delete(String spnd_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_SPNDCOFFEELISTs);

			pstmt.setString(1, spnd_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_SPNDCOFFEE);

			pstmt.setString(1, spnd_id);

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
	public SpndcoffeeVO findByPrimaryKey(String spnd_id) {

		SpndcoffeeVO spndcoffeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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