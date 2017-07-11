package com.rjchenl.orderdetail.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OrderdetailJNDIDAO implements OrderdetailDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO ORDERDETAIL (ORD_ID,PROD_ID,PROD_NAME,PROD_PRICE,DETAIL_AMT) VALUES (?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ORD_ID,PROD_ID,PROD_NAME,PROD_PRICE,DETAIL_AMT FROM ORDERDETAIL ORDER BY ORD_ID,PROD_ID";
	private static final String GET_ONE_STMT = "SELECT ORD_ID,PROD_ID,PROD_NAME,PROD_PRICE,DETAIL_AMT FROM ORDERDETAIL WHERE ORD_ID = ? AND PROD_ID = ?";
	private static final String DELETE_ORDERDETAIL = "DELETE FROM ORDERDETAIL WHERE ORD_ID = ? AND PROD_ID = ?";
	private static final String UPDATE = "UPDATE ORDERDETAIL SET PROD_NAME=?, PROD_PRICE=?, DETAIL_AMT=? WHERE ORD_ID = ? AND PROD_ID = ?";

	@Override
	public void insert(OrderdetailVO orderdetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, orderdetailVO.getOrd_id());
			pstmt.setString(2, orderdetailVO.getProd_id());
			pstmt.setString(3, orderdetailVO.getProd_name());
			pstmt.setInt(4, orderdetailVO.getProd_price());
			pstmt.setInt(5, orderdetailVO.getDetail_amt());

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
	public void update(OrderdetailVO orderdetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, orderdetailVO.getProd_name());
			pstmt.setInt(2, orderdetailVO.getProd_price());
			pstmt.setInt(3, orderdetailVO.getDetail_amt());
			pstmt.setString(4, orderdetailVO.getOrd_id());
			pstmt.setString(5, orderdetailVO.getProd_id());

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
	public void delete(String ord_id, String prod_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_ORDERDETAIL);

			pstmt.setString(1, ord_id);
			pstmt.setString(2, prod_id);

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
	public OrderdetailVO findByPrimaryKey(String ord_id, String prod_id) {

		OrderdetailVO orderdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ord_id);
			pstmt.setString(2, prod_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderdetailVO = new OrderdetailVO();
				orderdetailVO.setOrd_id(rs.getString("ord_id"));
				orderdetailVO.setProd_id(rs.getString("prod_id"));
				orderdetailVO.setProd_name(rs.getString("prod_name"));
				orderdetailVO.setProd_price(rs.getInt("prod_price"));
				orderdetailVO.setDetail_amt(rs.getInt("detail_amt"));
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
		return orderdetailVO;
	}

	@Override
	public List<OrderdetailVO> getAll() {

		List<OrderdetailVO> list = new ArrayList<OrderdetailVO>();
		OrderdetailVO orderdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderdetailVO = new OrderdetailVO();
				orderdetailVO.setOrd_id(rs.getString("ord_id"));
				orderdetailVO.setProd_id(rs.getString("prod_id"));
				orderdetailVO.setProd_name(rs.getString("prod_name"));
				orderdetailVO.setProd_price(rs.getInt("prod_price"));
				orderdetailVO.setDetail_amt(rs.getInt("detail_amt"));
				list.add(orderdetailVO); // Store the row in the list
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
	public void insert2(OrderdetailVO orderdetailVO, Connection con) {
		// TODO Auto-generated method stub
		
	}


}