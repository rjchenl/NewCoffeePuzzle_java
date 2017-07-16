package com.ming.orderlist.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.LinkedHashSet;
import java.util.Set;
import com.ming.orderdetail_model.OrderdetailVO;

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

	private static final String INSERT_STMT = "INSERT INTO ORDERLIST (ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER) VALUES ('ORD' || LPAD(to_char(ORD_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER FROM ORDERLIST ORDER BY ORD_ID";
	private static final String GET_ONE_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_SELLER FROM ORDERLIST WHERE ORD_ID = ?";
	private static final String GET_Orderdetails_ByOrd_id_STMT = "SELECT ORD_ID,PROD_ID,PROD_NAME,PROD_PRICE,DETAIL_AMT FROM ORDERDETAIL WHERE ORD_ID = ? ORDER BY ORD_ID,PROD_ID";

	private static final String DELETE_ORDERDETAILs = "DELETE FROM ORDERDETAIL WHERE ORD_ID = ?";
	private static final String DELETE_ORDERLIST = "DELETE FROM ORDERLIST WHERE ORD_ID = ?";
	private static final String UPDATE = "UPDATE ORDERLIST SET MEM_ID=?, STORE_ID=?, ORD_TOTAL=?, ORD_PICK=?, ORD_ADD=?, ORD_SHIPPING=?, ORD_TIME=?, SCORE_SELLER=? WHERE ORD_ID = ?";

	private static final String GET_ONE_ORDERLIST = "SELECT O.ORD_ID,O.NEN_ID,O.STORE_ID,O.ORD_TOTAL,O.SHIPPING,O.ORD_TIME,D.DRD_ID,D.PROD_ID,D.PROD_NAME,D.PROD_PRICE,D.DETAIL_AMT FROM O.DRD_ID = D.DRD_ID WHERE STORE_ID=?";

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
			pstmt.setInt(8, orderlistVO.getScore_seller());

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
			pstmt.setInt(8, orderlistVO.getScore_seller());
			pstmt.setString(9, orderlistVO.getOrd_id());

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
				orderlistVO.setScore_seller(rs.getInt("score_seller"));
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
				orderlistVO.setScore_seller(rs.getInt("score_seller"));
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
	public List<OrderlistVO> getOrdelist_1(String store_id) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public List<OrderlistVO> getOrdelist_2(String store_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderlistVO> getOrdelist_3(String store_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderlistVO> getOrdelist_4(String store_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getOrdelist_Update(String store_id, String ord_id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void getOrdelist_NO_Update(String store_id, String ord_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getOrdelist_GO_Update(String store_id, String ord_id_2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getDeliveryUpdate(String store_id, String ord_id) {
		// TODO Auto-generated method stub
		
	}


}