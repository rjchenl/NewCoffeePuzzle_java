package com.ming.member.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;

public class MemberJDBCDAO implements MemberDAO_interface {

	// JDBC
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";
	// JDBC

	private static final String INSERT_STMT = "INSERT INTO MEMBER (MEM_ID,MEM_ACCT,MEM_PWD,MEM_NAME,MEM_TEL,MEM_EMAIL,MEM_ADD,MEM_POINTS,MEM_IMG) VALUES ('MEM' || LPAD(to_char(MEM_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT MEM_ID,MEM_ACCT,MEM_PWD,MEM_NAME,MEM_TEL,MEM_EMAIL,MEM_ADD,MEM_POINTS,MEM_IMG FROM MEMBER ORDER BY MEM_ID";
	private static final String GET_ONE_STMT = "SELECT MEM_ID,MEM_ACCT,MEM_PWD,MEM_NAME,MEM_TEL,MEM_EMAIL,MEM_ADD,MEM_POINTS,MEM_IMG FROM MEMBER WHERE MEM_ID = ?";
	private static final String DELETE = "DELETE FROM MEMBER WHERE MEM_ID = ?";
	private static final String UPDATE = "UPDATE MEMBER SET MEM_ACCT=?, MEM_PWD=?, MEM_NAME=?, MEM_TEL=?, MEM_EMAIL=?, MEM_ADD=?, MEM_POINTS=?, MEM_IMG=? WHERE MEM_ID = ?";
	private static final String GET_ONE_MEM = "SELECT MEM_ID,MEM_ACCT,MEM_PWD,MEM_NAME,MEM_TEL,MEM_EMAIL,MEM_ADD,MEM_POINTS,MEM_IMG FROM MEMBER WHERE MEM_ACCT = ?" ;
	
	private static final String GET_INSERT_MEM = "INSERT INTO MEMBER (MEM_ID,MEM_ACCT,MEM_PWD,MEM_NAME,MEM_TEL,MEM_EMAIL,MEM_ADD,MEM_POINTS,MEM_IMG) VALUES ('MEM' || LPAD(to_char(MEM_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?)";
	
	@Override
	public void insert(MemberVO memberVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memberVO.getMem_acct());
			pstmt.setString(2, memberVO.getMem_pwd());
			pstmt.setString(3, memberVO.getMem_name());
			pstmt.setString(4, memberVO.getMem_tel());
			pstmt.setString(5, memberVO.getMem_email());
			pstmt.setString(6, memberVO.getMem_add());
			pstmt.setInt(7, memberVO.getMem_points());
			pstmt.setBytes(8, memberVO.getMem_img());

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
	public void getMem_Insert(String inser_memid, String inser_mem_psw, String inser_mem_name, String inser_mem_nanber,
			String inser_mem_mail, String mem_add, Integer mem_points, byte[] mem_img) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_INSERT_MEM);

			pstmt.setString(1, inser_memid);
			pstmt.setString(2, inser_mem_psw);
			pstmt.setString(3, inser_mem_name);
			pstmt.setString(4, inser_mem_nanber);
			pstmt.setString(5, inser_mem_mail);
			pstmt.setString(6, mem_add);
			pstmt.setInt(7, mem_points);
			pstmt.setBytes(8, mem_img);

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
	public void update(MemberVO memberVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, memberVO.getMem_acct());
			pstmt.setString(2, memberVO.getMem_pwd());
			pstmt.setString(3, memberVO.getMem_name());
			pstmt.setString(4, memberVO.getMem_tel());
			pstmt.setString(5, memberVO.getMem_email());
			pstmt.setString(6, memberVO.getMem_add());
			pstmt.setInt(7, memberVO.getMem_points());
			pstmt.setBytes(8, memberVO.getMem_img());
			pstmt.setString(9, memberVO.getMem_id());

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
	public void delete(String mem_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mem_id);

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
	public MemberVO findByPrimaryKey(String mem_id) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMem_id(rs.getString("mem_id"));
				memberVO.setMem_acct(rs.getString("mem_acct"));
				memberVO.setMem_pwd(rs.getString("mem_pwd"));
				memberVO.setMem_name(rs.getString("mem_name"));
				memberVO.setMem_tel(rs.getString("mem_tel"));
				memberVO.setMem_email(rs.getString("mem_email"));
				memberVO.setMem_add(rs.getString("mem_add"));
				memberVO.setMem_points(rs.getInt("mem_points"));
				memberVO.setMem_img(rs.getBytes("mem_img"));
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
		return memberVO;
	}

	@Override
	public MemberVO findByMem(String mem_acct) {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_MEM);

			pstmt.setString(1, mem_acct);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMem_id(rs.getString("mem_id"));
				memberVO.setMem_acct(rs.getString("mem_acct"));
				memberVO.setMem_pwd(rs.getString("mem_pwd"));
				memberVO.setMem_name(rs.getString("mem_name"));
				memberVO.setMem_tel(rs.getString("mem_tel"));
				memberVO.setMem_email(rs.getString("mem_email"));
				memberVO.setMem_add(rs.getString("mem_add"));
				memberVO.setMem_points(rs.getInt("mem_points"));
				memberVO.setMem_img(rs.getBytes("mem_img"));
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
		return memberVO;
	}

	@Override
	public List<MemberVO> getAll() {

		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMem_id(rs.getString("mem_id"));
				memberVO.setMem_acct(rs.getString("mem_acct"));
				memberVO.setMem_pwd(rs.getString("mem_pwd"));
				memberVO.setMem_name(rs.getString("mem_name"));
				memberVO.setMem_tel(rs.getString("mem_tel"));
				memberVO.setMem_email(rs.getString("mem_email"));
				memberVO.setMem_add(rs.getString("mem_add"));
				memberVO.setMem_points(rs.getInt("mem_points"));
				memberVO.setMem_img(rs.getBytes("mem_img"));
				list.add(memberVO); // Store the row in the list
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

		MemberJDBCDAO dao = new MemberJDBCDAO();

/*
		// insert()
		MemberVO memberVO = new MemberVO();
		memberVO.setMem_id("A");
		memberVO.setMem_acct("A");
		memberVO.setMem_pwd("A");
		memberVO.setMem_name("A");
		memberVO.setMem_tel("A");
		memberVO.setMem_email("A");
		memberVO.setMem_add("A");
		memberVO.setMem_points(1);
		memberVO.setMem_img(getPictureByteArray("D:/temp/tomcat.gif"));
		dao.insert(memberVO);

		// update()
		MemberVO memberVO = new MemberVO();
		memberVO.setMem_id("A");
		memberVO.setMem_acct("A");
		memberVO.setMem_pwd("A");
		memberVO.setMem_name("A");
		memberVO.setMem_tel("A");
		memberVO.setMem_email("A");
		memberVO.setMem_add("A");
		memberVO.setMem_points(1);
		memberVO.setMem_img(getPictureByteArray("D:/temp/tomcat.gif"));
		dao.update(memberVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		MemberVO memberVO = dao.findByPrimaryKey("A");
		System.out.print(memberVO.getMem_id() + ", ");
		System.out.print(memberVO.getMem_acct() + ", ");
		System.out.print(memberVO.getMem_pwd() + ", ");
		System.out.print(memberVO.getMem_name() + ", ");
		System.out.print(memberVO.getMem_tel() + ", ");
		System.out.print(memberVO.getMem_email() + ", ");
		System.out.print(memberVO.getMem_add() + ", ");
		System.out.print(memberVO.getMem_points() + ", ");
		System.out.print(memberVO.getMem_img() + ", ");
		System.out.println("---------------------");
*/
		// getAll()
		List<MemberVO> list = dao.getAll();
		for (MemberVO aMemberVO : list) {
			System.out.print(aMemberVO.getMem_id() + ", ");
			System.out.print(aMemberVO.getMem_acct() + ", ");
			System.out.print(aMemberVO.getMem_pwd() + ", ");
			System.out.print(aMemberVO.getMem_name() + ", ");
			System.out.print(aMemberVO.getMem_tel() + ", ");
			System.out.print(aMemberVO.getMem_email() + ", ");
			System.out.print(aMemberVO.getMem_add() + ", ");
			System.out.print(aMemberVO.getMem_points() + ", ");
			System.out.print(aMemberVO.getMem_img() + ", ");
			System.out.println();
		}


	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}


}