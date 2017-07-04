package com.ming.orderlist.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class OrderlistJDBCDAO implements OrderlistDAO_interface {

	// JDBC
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";
	// JDBC

	private static final String INSERT_STMT = "INSERT INTO ORDERLIST (ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_BUYER,SCORE_SELLER,REPT_BUYER,REPT_BUYER_RSN,REPT_BUYER_REV,REPT_SELLER,REPT_SELLER_RSN,REPT_SELLER_REV,ORD_ISRETURN,RETURN_RSN) VALUES ('ORD' || LPAD(to_char(ORD_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_BUYER,SCORE_SELLER,REPT_BUYER,REPT_BUYER_RSN,REPT_BUYER_REV,REPT_SELLER,REPT_SELLER_RSN,REPT_SELLER_REV,ORD_ISRETURN,RETURN_RSN FROM ORDERLIST ORDER BY ORD_ID";
	private static final String GET_ONE_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_BUYER,SCORE_SELLER,REPT_BUYER,REPT_BUYER_RSN,REPT_BUYER_REV,REPT_SELLER,REPT_SELLER_RSN,REPT_SELLER_REV,ORD_ISRETURN,RETURN_RSN FROM ORDERLIST WHERE ORD_ID = ?";
	private static final String DELETE = "DELETE FROM ORDERLIST WHERE ORD_ID = ?";
	private static final String UPDATE = "UPDATE ORDERLIST SET MEM_ID=?, STORE_ID=?, ORD_TOTAL=?, ORD_PICK=?, ORD_ADD=?, ORD_SHIPPING=?, ORD_TIME=?, SCORE_BUYER=?, SCORE_SELLER=?, REPT_BUYER=?, REPT_BUYER_RSN=?, REPT_BUYER_REV=?, REPT_SELLER=?, REPT_SELLER_RSN=?, REPT_SELLER_REV=?, ORD_ISRETURN=?, RETURN_RSN=? WHERE ORD_ID = ?";

	@Override
	public void insert(OrderlistVO orderlistVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, orderlistVO.getMem_id());
			pstmt.setString(2, orderlistVO.getStore_id());
			pstmt.setString(3, orderlistVO.getOrd_total());
			pstmt.setInt(4, orderlistVO.getOrd_pick());
			pstmt.setString(5, orderlistVO.getOrd_add());
			pstmt.setInt(6, orderlistVO.getOrd_shipping());
			pstmt.setTimestamp(7, orderlistVO.getOrd_time());
			pstmt.setInt(8, orderlistVO.getScore_buyer());
			pstmt.setInt(9, orderlistVO.getScore_seller());
			pstmt.setInt(10, orderlistVO.getRept_buyer());
			pstmt.setCharacterStream(11, stringToReader(orderlistVO.getRept_buyer_rsn()));
			pstmt.setInt(12, orderlistVO.getRept_buyer_rev());
			pstmt.setInt(13, orderlistVO.getRept_seller());
			pstmt.setCharacterStream(14, stringToReader(orderlistVO.getRept_seller_rsn()));
			pstmt.setInt(15, orderlistVO.getRept_seller_rev());
			pstmt.setInt(16, orderlistVO.getOrd_isreturn());
			pstmt.setCharacterStream(17, stringToReader(orderlistVO.getReturn_rsn()));

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
	public void update(OrderlistVO orderlistVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, orderlistVO.getMem_id());
			pstmt.setString(2, orderlistVO.getStore_id());
			pstmt.setString(3, orderlistVO.getOrd_total());
			pstmt.setInt(4, orderlistVO.getOrd_pick());
			pstmt.setString(5, orderlistVO.getOrd_add());
			pstmt.setInt(6, orderlistVO.getOrd_shipping());
			pstmt.setTimestamp(7, orderlistVO.getOrd_time());
			pstmt.setInt(8, orderlistVO.getScore_buyer());
			pstmt.setInt(9, orderlistVO.getScore_seller());
			pstmt.setInt(10, orderlistVO.getRept_buyer());
			pstmt.setCharacterStream(11, stringToReader(orderlistVO.getRept_buyer_rsn()));
			pstmt.setInt(12, orderlistVO.getRept_buyer_rev());
			pstmt.setInt(13, orderlistVO.getRept_seller());
			pstmt.setCharacterStream(14, stringToReader(orderlistVO.getRept_seller_rsn()));
			pstmt.setInt(15, orderlistVO.getRept_seller_rev());
			pstmt.setInt(16, orderlistVO.getOrd_isreturn());
			pstmt.setCharacterStream(17, stringToReader(orderlistVO.getReturn_rsn()));
			pstmt.setString(18, orderlistVO.getOrd_id());

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
	public void delete(String ord_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ord_id);

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
	public OrderlistVO findByPrimaryKey(String ord_id) throws IOException {

		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ord_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderlistVO = new OrderlistVO();
				orderlistVO.setOrd_id(rs.getString("ord_id"));
				orderlistVO.setMem_id(rs.getString("mem_id"));
				orderlistVO.setStore_id(rs.getString("store_id"));
				orderlistVO.setOrd_total(rs.getString("ord_total"));
				orderlistVO.setOrd_pick(rs.getInt("ord_pick"));
				orderlistVO.setOrd_add(rs.getString("ord_add"));
				orderlistVO.setOrd_shipping(rs.getInt("ord_shipping"));
				orderlistVO.setOrd_time(rs.getTimestamp("ord_time"));
				orderlistVO.setScore_buyer(rs.getInt("score_buyer"));
				orderlistVO.setScore_seller(rs.getInt("score_seller"));
				orderlistVO.setRept_buyer(rs.getInt("rept_buyer"));
				orderlistVO.setRept_buyer_rsn(readerToString(rs.getCharacterStream("rept_buyer_rsn")));
				orderlistVO.setRept_buyer_rev(rs.getInt("rept_buyer_rev"));
				orderlistVO.setRept_seller(rs.getInt("rept_seller"));
				orderlistVO.setRept_seller_rsn(readerToString(rs.getCharacterStream("rept_seller_rsn")));
				orderlistVO.setRept_seller_rev(rs.getInt("rept_seller_rev"));
				orderlistVO.setOrd_isreturn(rs.getInt("ord_isreturn"));
				orderlistVO.setReturn_rsn(readerToString(rs.getCharacterStream("return_rsn")));
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
		return orderlistVO;
	}

	@Override
	public List<OrderlistVO> getAll() throws IOException {

		List<OrderlistVO> list = new ArrayList<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderlistVO = new OrderlistVO();
				orderlistVO.setOrd_id(rs.getString("ord_id"));
				orderlistVO.setMem_id(rs.getString("mem_id"));
				orderlistVO.setStore_id(rs.getString("store_id"));
				orderlistVO.setOrd_total(rs.getString("ord_total"));
				orderlistVO.setOrd_pick(rs.getInt("ord_pick"));
				orderlistVO.setOrd_add(rs.getString("ord_add"));
				orderlistVO.setOrd_shipping(rs.getInt("ord_shipping"));
				orderlistVO.setOrd_time(rs.getTimestamp("ord_time"));
				orderlistVO.setScore_buyer(rs.getInt("score_buyer"));
				orderlistVO.setScore_seller(rs.getInt("score_seller"));
				orderlistVO.setRept_buyer(rs.getInt("rept_buyer"));
				orderlistVO.setRept_buyer_rsn(readerToString(rs.getCharacterStream("rept_buyer_rsn")));
				orderlistVO.setRept_buyer_rev(rs.getInt("rept_buyer_rev"));
				orderlistVO.setRept_seller(rs.getInt("rept_seller"));
				orderlistVO.setRept_seller_rsn(readerToString(rs.getCharacterStream("rept_seller_rsn")));
				orderlistVO.setRept_seller_rev(rs.getInt("rept_seller_rev"));
				orderlistVO.setOrd_isreturn(rs.getInt("ord_isreturn"));
				orderlistVO.setReturn_rsn(readerToString(rs.getCharacterStream("return_rsn")));
				list.add(orderlistVO); // Store the row in the list
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

	public static void main(String[] args) throws IOException {

		OrderlistJDBCDAO dao = new OrderlistJDBCDAO();

/*
		// insert()
		OrderlistVO orderlistVO = new OrderlistVO();
		orderlistVO.setOrd_id("A");
		orderlistVO.setMem_id("A");
		orderlistVO.setStore_id("A");
		orderlistVO.setOrd_total("A");
		orderlistVO.setOrd_pick(1);
		orderlistVO.setOrd_add("A");
		orderlistVO.setOrd_shipping(1);
		orderlistVO.setOrd_time(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		orderlistVO.setScore_buyer(1);
		orderlistVO.setScore_seller(1);
		orderlistVO.setRept_buyer(1);
		orderlistVO.setRept_buyer_rsn("A");
		orderlistVO.setRept_buyer_rev(1);
		orderlistVO.setRept_seller(1);
		orderlistVO.setRept_seller_rsn("A");
		orderlistVO.setRept_seller_rev(1);
		orderlistVO.setOrd_isreturn(1);
		orderlistVO.setReturn_rsn("A");
		dao.insert(orderlistVO);

		// update()
		OrderlistVO orderlistVO = new OrderlistVO();
		orderlistVO.setOrd_id("A");
		orderlistVO.setMem_id("A");
		orderlistVO.setStore_id("A");
		orderlistVO.setOrd_total("A");
		orderlistVO.setOrd_pick(1);
		orderlistVO.setOrd_add("A");
		orderlistVO.setOrd_shipping(1);
		orderlistVO.setOrd_time(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		orderlistVO.setScore_buyer(1);
		orderlistVO.setScore_seller(1);
		orderlistVO.setRept_buyer(1);
		orderlistVO.setRept_buyer_rsn("A");
		orderlistVO.setRept_buyer_rev(1);
		orderlistVO.setRept_seller(1);
		orderlistVO.setRept_seller_rsn("A");
		orderlistVO.setRept_seller_rev(1);
		orderlistVO.setOrd_isreturn(1);
		orderlistVO.setReturn_rsn("A");
		dao.update(orderlistVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		OrderlistVO orderlistVO = dao.findByPrimaryKey("A");
		System.out.print(orderlistVO.getOrd_id() + ", ");
		System.out.print(orderlistVO.getMem_id() + ", ");
		System.out.print(orderlistVO.getStore_id() + ", ");
		System.out.print(orderlistVO.getOrd_total() + ", ");
		System.out.print(orderlistVO.getOrd_pick() + ", ");
		System.out.print(orderlistVO.getOrd_add() + ", ");
		System.out.print(orderlistVO.getOrd_shipping() + ", ");
		System.out.print(orderlistVO.getOrd_time() + ", ");
		System.out.print(orderlistVO.getScore_buyer() + ", ");
		System.out.print(orderlistVO.getScore_seller() + ", ");
		System.out.print(orderlistVO.getRept_buyer() + ", ");
		System.out.print(orderlistVO.getRept_buyer_rsn() + ", ");
		System.out.print(orderlistVO.getRept_buyer_rev() + ", ");
		System.out.print(orderlistVO.getRept_seller() + ", ");
		System.out.print(orderlistVO.getRept_seller_rsn() + ", ");
		System.out.print(orderlistVO.getRept_seller_rev() + ", ");
		System.out.print(orderlistVO.getOrd_isreturn() + ", ");
		System.out.print(orderlistVO.getReturn_rsn() + ", ");
		System.out.println("---------------------");
*/
		// getAll()
		List<OrderlistVO> list = dao.getAll();
		for (OrderlistVO aOrderlistVO : list) {
			System.out.print(aOrderlistVO.getOrd_id() + ", ");
			System.out.print(aOrderlistVO.getMem_id() + ", ");
			System.out.print(aOrderlistVO.getStore_id() + ", ");
			System.out.print(aOrderlistVO.getOrd_total() + ", ");
			System.out.print(aOrderlistVO.getOrd_pick() + ", ");
			System.out.print(aOrderlistVO.getOrd_add() + ", ");
			System.out.print(aOrderlistVO.getOrd_shipping() + ", ");
			System.out.print(aOrderlistVO.getOrd_time() + ", ");
			System.out.print(aOrderlistVO.getScore_buyer() + ", ");
			System.out.print(aOrderlistVO.getScore_seller() + ", ");
			System.out.print(aOrderlistVO.getRept_buyer() + ", ");
			System.out.print(aOrderlistVO.getRept_buyer_rsn() + ", ");
			System.out.print(aOrderlistVO.getRept_buyer_rev() + ", ");
			System.out.print(aOrderlistVO.getRept_seller() + ", ");
			System.out.print(aOrderlistVO.getRept_seller_rsn() + ", ");
			System.out.print(aOrderlistVO.getRept_seller_rev() + ", ");
			System.out.print(aOrderlistVO.getOrd_isreturn() + ", ");
			System.out.print(aOrderlistVO.getReturn_rsn() + ", ");
			System.out.println();
		}


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


}