package com.orderdetail.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class OrderdetailJDBCDAO implements OrderdetailDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, orderdetailVO.getOrd_id());
			pstmt.setString(2, orderdetailVO.getProd_id());
			pstmt.setString(3, orderdetailVO.getProd_name());
			pstmt.setInt(4, orderdetailVO.getProd_price());
			pstmt.setInt(5, orderdetailVO.getDetail_amt());

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
	public void update(OrderdetailVO orderdetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, orderdetailVO.getProd_name());
			pstmt.setInt(2, orderdetailVO.getProd_price());
			pstmt.setInt(3, orderdetailVO.getDetail_amt());
			pstmt.setString(4, orderdetailVO.getOrd_id());
			pstmt.setString(5, orderdetailVO.getProd_id());

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
	public void delete(String ord_id, String prod_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_ORDERDETAIL);

			pstmt.setString(1, ord_id);
			pstmt.setString(2, prod_id);

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
	public OrderdetailVO findByPrimaryKey(String ord_id, String prod_id) {

		OrderdetailVO orderdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		OrderdetailJDBCDAO dao = new OrderdetailJDBCDAO();

/*
		// insert()
		OrderdetailVO orderdetailVO = new OrderdetailVO();
		orderdetailVO.setOrd_id("A");
		orderdetailVO.setProd_id("A");
		orderdetailVO.setProd_name("A");
		orderdetailVO.setProd_price(1);
		orderdetailVO.setDetail_amt(1);
		dao.insert(orderdetailVO);

		// update()
		OrderdetailVO orderdetailVO = new OrderdetailVO();
		orderdetailVO.setOrd_id("A");
		orderdetailVO.setProd_id("A");
		orderdetailVO.setProd_name("A");
		orderdetailVO.setProd_price(1);
		orderdetailVO.setDetail_amt(1);
		dao.update(orderdetailVO);

		// delete()
		dao.delete("A","A");

		// findByPrimaryKey()
		OrderdetailVO orderdetailVO = dao.findByPrimaryKey("A","A");
		System.out.print(orderdetailVO.getOrd_id() + ", ");
		System.out.print(orderdetailVO.getProd_id() + ", ");
		System.out.print(orderdetailVO.getProd_name() + ", ");
		System.out.print(orderdetailVO.getProd_price() + ", ");
		System.out.print(orderdetailVO.getDetail_amt() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<OrderdetailVO> list = dao.getAll();
		for (OrderdetailVO aOrderdetailVO : list) {
			System.out.print(aOrderdetailVO.getOrd_id() + ", ");
			System.out.print(aOrderdetailVO.getProd_id() + ", ");
			System.out.print(aOrderdetailVO.getProd_name() + ", ");
			System.out.print(aOrderdetailVO.getProd_price() + ", ");
			System.out.print(aOrderdetailVO.getDetail_amt() + ", ");
			System.out.println();
		}

*/
	}


}