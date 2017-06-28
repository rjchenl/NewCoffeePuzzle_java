package com.tag.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;
import com.store_tag.model.Store_tagVO;

public class TagJDBCDAO implements TagDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO TAG (TAG_ID,TAG_CONTENT) VALUES ('TAG' || LPAD(to_char(TAG_ID_SQ.NEXTVAL), 8, '0'), ?)";
	private static final String GET_ALL_STMT = "SELECT TAG_ID,TAG_CONTENT FROM TAG ORDER BY TAG_ID";
	private static final String GET_ONE_STMT = "SELECT TAG_ID,TAG_CONTENT FROM TAG WHERE TAG_ID = ?";
	private static final String GET_Store_tags_ByTag_id_STMT = "SELECT STORE_ID,TAG_ID,TAG_NUM FROM STORE_TAG WHERE TAG_ID = ? ORDER BY STORE_ID,TAG_ID";

	private static final String DELETE_STORE_TAGs = "DELETE FROM STORE_TAG WHERE TAG_ID = ?";
	private static final String DELETE_TAG = "DELETE FROM TAG WHERE TAG_ID = ?";
	private static final String UPDATE = "UPDATE TAG SET TAG_CONTENT=? WHERE TAG_ID = ?";

	@Override
	public void insert(TagVO tagVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, tagVO.getTag_content());

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
	public void update(TagVO tagVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, tagVO.getTag_content());
			pstmt.setString(2, tagVO.getTag_id());

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
	public void delete(String tag_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_STORE_TAGs);

			pstmt.setString(1, tag_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_TAG);

			pstmt.setString(1, tag_id);

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
	public TagVO findByPrimaryKey(String tag_id) {

		TagVO tagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, tag_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				tagVO = new TagVO();
				tagVO.setTag_id(rs.getString("tag_id"));
				tagVO.setTag_content(rs.getString("tag_content"));
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
		return tagVO;
	}

	@Override
	public List<TagVO> getAll() {

		List<TagVO> list = new ArrayList<TagVO>();
		TagVO tagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				tagVO = new TagVO();
				tagVO.setTag_id(rs.getString("tag_id"));
				tagVO.setTag_content(rs.getString("tag_content"));
				list.add(tagVO); // Store the row in the list
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
	public Set<Store_tagVO> getStore_tagsByTag_id(String tag_id){
		Set<Store_tagVO> set = new LinkedHashSet<Store_tagVO>();
		Store_tagVO store_tagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Store_tags_ByTag_id_STMT);
			pstmt.setString(1, tag_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				store_tagVO = new Store_tagVO();
				store_tagVO.setStore_id(rs.getString("store_id"));
				store_tagVO.setTag_id(rs.getString("tag_id"));
				store_tagVO.setTag_num(rs.getInt("tag_num"));
				set.add(store_tagVO); // Store the row in the vector
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

	public static void main(String[] args) {

		TagJDBCDAO dao = new TagJDBCDAO();

/*
		// insert()
		TagVO tagVO = new TagVO();
		tagVO.setTag_id("A");
		tagVO.setTag_content("A");
		dao.insert(tagVO);

		// update()
		TagVO tagVO = new TagVO();
		tagVO.setTag_id("A");
		tagVO.setTag_content("A");
		dao.update(tagVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		TagVO tagVO = dao.findByPrimaryKey("A");
		System.out.print(tagVO.getTag_id() + ", ");
		System.out.print(tagVO.getTag_content() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<TagVO> list = dao.getAll();
		for (TagVO aTagVO : list) {
			System.out.print(aTagVO.getTag_id() + ", ");
			System.out.print(aTagVO.getTag_content() + ", ");
			System.out.println();
		}

		Set<Store_tagVO> set = dao.getStore_tagsByTag_id("A");
		for (Store_tagVO aStore_tag : set) {
			System.out.print(aStore_tag.getStore_id() + ", ");
			System.out.print(aStore_tag.getTag_id() + ", ");
			System.out.print(aStore_tag.getTag_num() + ", ");
			System.out.println();
		}

*/
	}


}