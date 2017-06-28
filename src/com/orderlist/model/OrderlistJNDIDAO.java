package com.orderlist.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.LinkedHashSet;
import java.util.Set;
import com.orderdetail.model.OrderdetailVO;

public class OrderlistJNDIDAO implements OrderlistDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO ORDERLIST (ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_BUYER,SCORE_SELLER,REPT_BUYER,REPT_BUYER_RSN,REPT_BUYER_REV,REPT_SELLER,REPT_SELLER_RSN,REPT_SELLER_REV,ORD_ISRETURN,RETURN_RSN) VALUES ('ORD' || LPAD(to_char(ORD_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_BUYER,SCORE_SELLER,REPT_BUYER,REPT_BUYER_RSN,REPT_BUYER_REV,REPT_SELLER,REPT_SELLER_RSN,REPT_SELLER_REV,ORD_ISRETURN,RETURN_RSN FROM ORDERLIST ORDER BY ORD_ID";
	private static final String GET_ONE_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_BUYER,SCORE_SELLER,REPT_BUYER,REPT_BUYER_RSN,REPT_BUYER_REV,REPT_SELLER,REPT_SELLER_RSN,REPT_SELLER_REV,ORD_ISRETURN,RETURN_RSN FROM ORDERLIST WHERE ORD_ID = ?";
	private static final String GET_Orderdetails_ByOrd_id_STMT = "SELECT ORD_ID,PROD_ID,PROD_NAME,PROD_PRICE,DETAIL_AMT,DETAIL_RETURN FROM ORDERDETAIL WHERE ORD_ID = ? ORDER BY ORD_ID,PROD_ID";

	private static final String DELETE_ORDERDETAILs = "DELETE FROM ORDERDETAIL WHERE ORD_ID = ?";
	private static final String DELETE_ORDERLIST = "DELETE FROM ORDERLIST WHERE ORD_ID = ?";
	private static final String UPDATE = "UPDATE ORDERLIST SET MEM_ID=?, STORE_ID=?, ORD_TOTAL=?, ORD_PICK=?, ORD_ADD=?, ORD_SHIPPING=?, ORD_TIME=?, SCORE_BUYER=?, SCORE_SELLER=?, REPT_BUYER=?, REPT_BUYER_RSN=?, REPT_BUYER_REV=?, REPT_SELLER=?, REPT_SELLER_RSN=?, REPT_SELLER_REV=?, ORD_ISRETURN=?, RETURN_RSN=? WHERE ORD_ID = ?";

	@Override
	public void insert(OrderlistVO orderlistVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, orderlistVO.getMem_id());
			pstmt.setString(2, orderlistVO.getStore_id());
			pstmt.setInt(3, orderlistVO.getOrd_total());
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, orderlistVO.getMem_id());
			pstmt.setString(2, orderlistVO.getStore_id());
			pstmt.setInt(3, orderlistVO.getOrd_total());
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
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_ORDERDETAILs);

			pstmt.setString(1, ord_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ORDERLIST);

			pstmt.setString(1, ord_id);

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
	public OrderlistVO findByPrimaryKey(String ord_id) {

		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ord_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderlistVO = new OrderlistVO();
				orderlistVO.setOrd_id(rs.getString("ord_id"));
				orderlistVO.setMem_id(rs.getString("mem_id"));
				orderlistVO.setStore_id(rs.getString("store_id"));
				orderlistVO.setOrd_total(rs.getInt("ord_total"));
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
	public List<OrderlistVO> getAll() {

		List<OrderlistVO> list = new ArrayList<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderlistVO = new OrderlistVO();
				orderlistVO.setOrd_id(rs.getString("ord_id"));
				orderlistVO.setMem_id(rs.getString("mem_id"));
				orderlistVO.setStore_id(rs.getString("store_id"));
				orderlistVO.setOrd_total(rs.getInt("ord_total"));
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
	public Set<OrderdetailVO> getOrderdetailsByOrd_id(String ord_id){
		Set<OrderdetailVO> set = new LinkedHashSet<OrderdetailVO>();
		OrderdetailVO orderdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Orderdetails_ByOrd_id_STMT);
			pstmt.setString(1, ord_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderdetailVO = new OrderdetailVO();
				orderdetailVO.setOrd_id(rs.getString("ord_id"));
				orderdetailVO.setProd_id(rs.getString("prod_id"));
				orderdetailVO.setProd_name(rs.getString("prod_name"));
				orderdetailVO.setProd_price(rs.getInt("prod_price"));
				orderdetailVO.setDetail_amt(rs.getInt("detail_amt"));
				orderdetailVO.setDetail_return(rs.getInt("detail_return"));
				set.add(orderdetailVO); // Store the row in the vector
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