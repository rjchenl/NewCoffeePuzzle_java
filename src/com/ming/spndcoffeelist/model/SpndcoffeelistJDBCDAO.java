package com.ming.spndcoffeelist.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class SpndcoffeelistJDBCDAO implements SpndcoffeelistDAO_interface {

	// JDBC
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";
	private static final String INSERT_STMT = "INSERT INTO SPNDCOFFEELIST (LIST_ID,SPND_ID,MEM_ID,SPND_PROD,STORE_ID,LIST_AMT,LIST_LEFT,LIST_DATE) VALUES ('LIST' || LPAD(to_char(LIST_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT LIST_ID,SPND_ID,MEM_ID,SPND_PROD,STORE_ID,LIST_AMT,LIST_LEFT,LIST_DATE FROM SPNDCOFFEELIST ORDER BY LIST_ID";
	private static final String GET_ONE_STMT = "SELECT LIST_ID,SPND_ID,MEM_ID,SPND_PROD,STORE_ID,LIST_AMT,LIST_LEFT,LIST_DATE FROM SPNDCOFFEELIST WHERE LIST_ID = ?";
	private static final String DELETE = "DELETE FROM SPNDCOFFEELIST WHERE LIST_ID = ?";
	private static final String UPDATE = "UPDATE SPNDCOFFEELIST SET SPND_ID=?, MEM_ID=?, SPND_PROD=?, STORE_ID=?, LIST_AMT=?, LIST_LEFT=?, LIST_DATE=? WHERE LIST_ID = ?";

	private static final String Get_All_STORE = "SELECT P.LIST_ID,P.SPND_ID,M.MEM_ID,P.STORE_ID,P.LIST_AMT,P.LIST_DATE,P.LIST_LEFT,P.SPND_PROD,M.MEM_NAME FROM SPNDCOFFEELIST P JOIN MEMBER M ON P.MEM_ID = M.MEM_ID WHERE P.STORE_ID = ?";
	// JDBC
	private static final String Get_Update = "UPDATE SPNDCOFFEELIST SET LIST_LEFT=? WHERE LIST_ID =? AND STORE_ID=?";
	private static final String Get_INSERT = "INSERT INTO SPNDCOFFEELIST (LIST_ID,SPND_ID,MEM_ID,SPND_PROD,STORE_ID,LIST_AMT,LIST_LEFT,LIST_DATE) VALUES ('LIST' || LPAD(to_char(LIST_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?)";

	@Override
	public void insert(SpndcoffeelistVO spndcoffeelistVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, spndcoffeelistVO.getSpnd_id());
			pstmt.setString(2, spndcoffeelistVO.getMem_id());
			pstmt.setString(3, spndcoffeelistVO.getSpnd_prod());
			pstmt.setString(4, spndcoffeelistVO.getStore_id());
			pstmt.setInt(5, spndcoffeelistVO.getList_amt());
			pstmt.setInt(6, spndcoffeelistVO.getList_left());
			pstmt.setTimestamp(7, spndcoffeelistVO.getList_date());

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
	public void getInsert(String store_id, String mem_id, String spnd_id, String spnd_prod, Integer list_amt,
			Integer list_left, Timestamp list_date) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(Get_INSERT);

			pstmt.setString(1, spnd_id);
			pstmt.setString(2, mem_id);
			pstmt.setString(3, spnd_prod);
			pstmt.setString(4, store_id);
			pstmt.setInt(5, list_amt);
			pstmt.setInt(6, list_left);
			pstmt.setTimestamp(7, list_date);

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
	public void update(SpndcoffeelistVO spndcoffeelistVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, spndcoffeelistVO.getSpnd_id());
			pstmt.setString(2, spndcoffeelistVO.getMem_id());
			pstmt.setString(3, spndcoffeelistVO.getSpnd_prod());
			pstmt.setString(4, spndcoffeelistVO.getStore_id());
			pstmt.setInt(5, spndcoffeelistVO.getList_amt());
			pstmt.setInt(6, spndcoffeelistVO.getList_left());
			pstmt.setTimestamp(7, spndcoffeelistVO.getList_date());
			pstmt.setString(8, spndcoffeelistVO.getList_id());

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
	public void delete(String list_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, list_id);

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
	public SpndcoffeelistVO findByPrimaryKey(String list_id) {

		SpndcoffeelistVO spndcoffeelistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, list_id);

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
		return spndcoffeelistVO;
	}

	@Override
	public List<SpndcoffeelistVO> getAll() {

		List<SpndcoffeelistVO> list = new ArrayList<SpndcoffeelistVO>();
		SpndcoffeelistVO spndcoffeelistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

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
				list.add(spndcoffeelistVO); // Store the row in the list
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
	public List<SpndcoffeelistVO> getStore(String store_id) {
		List<SpndcoffeelistVO> list = new ArrayList<SpndcoffeelistVO>();
		SpndcoffeelistVO spndcoffeelistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(Get_All_STORE);
	
			pstmt.setString(1, store_id);
	
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
				spndcoffeelistVO.setMem_name(rs.getString("mem_name"));
				list.add(spndcoffeelistVO);
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
		return  list;
	}

	@Override
	public List<SpndcoffeelistVO> getUpdate(String store_id,String list_id,Integer list_left) {
		List<SpndcoffeelistVO> list = new ArrayList<SpndcoffeelistVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(Get_Update);
	
			pstmt.setInt(1, list_left);
			pstmt.setString(2, list_id);
			pstmt.setString(3, store_id);
			
			System.out.println("-------------------store_id="+store_id);
			System.out.println("-------------------list_id="+list_id);
			System.out.println("-------------------list_left="+list_left);
	
			int num = pstmt.executeUpdate();
			System.out.println("2");
			
			System.out.println("num: " + num);
			
	
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			se.printStackTrace();
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

		SpndcoffeelistJDBCDAO dao = new SpndcoffeelistJDBCDAO();

/*
		// insert()
		SpndcoffeelistVO spndcoffeelistVO = new SpndcoffeelistVO();
		spndcoffeelistVO.setList_id("A");
		spndcoffeelistVO.setSpnd_id("A");
		spndcoffeelistVO.setMem_id("A");
		spndcoffeelistVO.setSpnd_prod("A");
		spndcoffeelistVO.setStore_id("A");
		spndcoffeelistVO.setList_amt(1);
		spndcoffeelistVO.setList_left(1);
		spndcoffeelistVO.setList_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		dao.insert(spndcoffeelistVO);

		// update()
		SpndcoffeelistVO spndcoffeelistVO = new SpndcoffeelistVO();
		spndcoffeelistVO.setList_id("A");
		spndcoffeelistVO.setSpnd_id("A");
		spndcoffeelistVO.setMem_id("A");
		spndcoffeelistVO.setSpnd_prod("A");
		spndcoffeelistVO.setStore_id("A");
		spndcoffeelistVO.setList_amt(1);
		spndcoffeelistVO.setList_left(1);
		spndcoffeelistVO.setList_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		dao.update(spndcoffeelistVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		SpndcoffeelistVO spndcoffeelistVO = dao.findByPrimaryKey("A");
		System.out.print(spndcoffeelistVO.getList_id() + ", ");
		System.out.print(spndcoffeelistVO.getSpnd_id() + ", ");
		System.out.print(spndcoffeelistVO.getMem_id() + ", ");
		System.out.print(spndcoffeelistVO.getSpnd_prod() + ", ");
		System.out.print(spndcoffeelistVO.getStore_id() + ", ");
		System.out.print(spndcoffeelistVO.getList_amt() + ", ");
		System.out.print(spndcoffeelistVO.getList_left() + ", ");
		System.out.print(spndcoffeelistVO.getList_date() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<SpndcoffeelistVO> list = dao.getAll();
		for (SpndcoffeelistVO aSpndcoffeelistVO : list) {
			System.out.print(aSpndcoffeelistVO.getList_id() + ", ");
			System.out.print(aSpndcoffeelistVO.getSpnd_id() + ", ");
			System.out.print(aSpndcoffeelistVO.getMem_id() + ", ");
			System.out.print(aSpndcoffeelistVO.getSpnd_prod() + ", ");
			System.out.print(aSpndcoffeelistVO.getStore_id() + ", ");
			System.out.print(aSpndcoffeelistVO.getList_amt() + ", ");
			System.out.print(aSpndcoffeelistVO.getList_left() + ", ");
			System.out.print(aSpndcoffeelistVO.getList_date() + ", ");
			System.out.println();
		}

*/
	}


}