package com.photo_store.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;

public class Photo_storeJDBCDAO implements Photo_storeDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO PHOTO_STORE (PHOTO_ID,PHOTO,STORE_ID,MEM_ID,PHOTO_DESC) VALUES ('PHOTO' || LPAD(to_char(PHOTO_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT PHOTO_ID,PHOTO,STORE_ID,MEM_ID,PHOTO_DESC FROM PHOTO_STORE ORDER BY PHOTO_ID";
	private static final String GET_ONE_STMT = "SELECT PHOTO_ID,PHOTO,STORE_ID,MEM_ID,PHOTO_DESC FROM PHOTO_STORE WHERE PHOTO_ID = ?";
	private static final String DELETE_PHOTO_STORE = "DELETE FROM PHOTO_STORE WHERE PHOTO_ID = ?";
	private static final String UPDATE = "UPDATE PHOTO_STORE SET PHOTO=?, STORE_ID=?, MEM_ID=?, PHOTO_DESC=? WHERE PHOTO_ID = ?";

	@Override
	public void insert(Photo_storeVO photo_storeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setBytes(1, photo_storeVO.getPhoto());
			pstmt.setString(2, photo_storeVO.getStore_id());
			pstmt.setString(3, photo_storeVO.getMem_id());
			pstmt.setCharacterStream(4, stringToReader(photo_storeVO.getPhoto_desc()));

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
	public void update(Photo_storeVO photo_storeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setBytes(1, photo_storeVO.getPhoto());
			pstmt.setString(2, photo_storeVO.getStore_id());
			pstmt.setString(3, photo_storeVO.getMem_id());
			pstmt.setCharacterStream(4, stringToReader(photo_storeVO.getPhoto_desc()));
			pstmt.setString(5, photo_storeVO.getPhoto_id());

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
	public void delete(String photo_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_PHOTO_STORE);

			pstmt.setString(1, photo_id);

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
	public Photo_storeVO findByPrimaryKey(String photo_id) {

		Photo_storeVO photo_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, photo_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				photo_storeVO = new Photo_storeVO();
				photo_storeVO.setPhoto_id(rs.getString("photo_id"));
				photo_storeVO.setPhoto(rs.getBytes("photo"));
				photo_storeVO.setStore_id(rs.getString("store_id"));
				photo_storeVO.setMem_id(rs.getString("mem_id"));
				photo_storeVO.setPhoto_desc(readerToString(rs.getCharacterStream("photo_desc")));
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
		return photo_storeVO;
	}

	@Override
	public List<Photo_storeVO> getAll() {

		List<Photo_storeVO> list = new ArrayList<Photo_storeVO>();
		Photo_storeVO photo_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				photo_storeVO = new Photo_storeVO();
				photo_storeVO.setPhoto_id(rs.getString("photo_id"));
				photo_storeVO.setPhoto(rs.getBytes("photo"));
				photo_storeVO.setStore_id(rs.getString("store_id"));
				photo_storeVO.setMem_id(rs.getString("mem_id"));
				photo_storeVO.setPhoto_desc(readerToString(rs.getCharacterStream("photo_desc")));
				list.add(photo_storeVO); // Store the row in the list
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

	public static void main(String[] args) throws IOException {

		Photo_storeJDBCDAO dao = new Photo_storeJDBCDAO();

/*
		// insert()
		Photo_storeVO photo_storeVO = new Photo_storeVO();
		photo_storeVO.setPhoto_id("A");
		photo_storeVO.setPhoto(getPictureByteArray("D:/temp/tomcat.gif"));
		photo_storeVO.setStore_id("A");
		photo_storeVO.setMem_id("A");
		photo_storeVO.setPhoto_desc("A");
		dao.insert(photo_storeVO);

		// update()
		Photo_storeVO photo_storeVO = new Photo_storeVO();
		photo_storeVO.setPhoto_id("A");
		photo_storeVO.setPhoto(getPictureByteArray("D:/temp/tomcat.gif"));
		photo_storeVO.setStore_id("A");
		photo_storeVO.setMem_id("A");
		photo_storeVO.setPhoto_desc("A");
		dao.update(photo_storeVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		Photo_storeVO photo_storeVO = dao.findByPrimaryKey("A");
		System.out.print(photo_storeVO.getPhoto_id() + ", ");
		System.out.print(photo_storeVO.getPhoto() + ", ");
		System.out.print(photo_storeVO.getStore_id() + ", ");
		System.out.print(photo_storeVO.getMem_id() + ", ");
		System.out.print(photo_storeVO.getPhoto_desc() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<Photo_storeVO> list = dao.getAll();
		for (Photo_storeVO aPhoto_storeVO : list) {
			System.out.print(aPhoto_storeVO.getPhoto_id() + ", ");
			System.out.print(aPhoto_storeVO.getPhoto() + ", ");
			System.out.print(aPhoto_storeVO.getStore_id() + ", ");
			System.out.print(aPhoto_storeVO.getMem_id() + ", ");
			System.out.print(aPhoto_storeVO.getPhoto_desc() + ", ");
			System.out.println();
		}

*/
	}


}