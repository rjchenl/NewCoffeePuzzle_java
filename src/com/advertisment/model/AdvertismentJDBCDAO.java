package com.advertisment.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;

public class AdvertismentJDBCDAO implements AdvertismentDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO ADVERTISMENT (AD_ID,AD_TITLE,AD_CONTENT,AD_IMG,AD_DATE,AD_STATUS) VALUES ('AD' || LPAD(to_char(AD_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT AD_ID,AD_TITLE,AD_CONTENT,AD_IMG,AD_DATE,AD_STATUS FROM ADVERTISMENT ORDER BY AD_ID";
	private static final String GET_ONE_STMT = "SELECT AD_ID,AD_TITLE,AD_CONTENT,AD_IMG,AD_DATE,AD_STATUS FROM ADVERTISMENT WHERE AD_ID = ?";
	private static final String DELETE_ADVERTISMENT = "DELETE FROM ADVERTISMENT WHERE AD_ID = ?";
	private static final String UPDATE = "UPDATE ADVERTISMENT SET AD_TITLE=?, AD_CONTENT=?, AD_IMG=?, AD_DATE=?, AD_STATUS=? WHERE AD_ID = ?";

	@Override
	public void insert(AdvertismentVO advertismentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, advertismentVO.getAd_title());
			pstmt.setCharacterStream(2, stringToReader(advertismentVO.getAd_content()));
			pstmt.setBytes(3, advertismentVO.getAd_img());
			pstmt.setTimestamp(4, advertismentVO.getAd_date());
			pstmt.setInt(5, advertismentVO.getAd_status());

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
	public void update(AdvertismentVO advertismentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, advertismentVO.getAd_title());
			pstmt.setCharacterStream(2, stringToReader(advertismentVO.getAd_content()));
			pstmt.setBytes(3, advertismentVO.getAd_img());
			pstmt.setTimestamp(4, advertismentVO.getAd_date());
			pstmt.setInt(5, advertismentVO.getAd_status());
			pstmt.setString(6, advertismentVO.getAd_id());

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
	public void delete(String ad_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_ADVERTISMENT);

			pstmt.setString(1, ad_id);

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
	public AdvertismentVO findByPrimaryKey(String ad_id) {

		AdvertismentVO advertismentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ad_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				advertismentVO = new AdvertismentVO();
				advertismentVO.setAd_id(rs.getString("ad_id"));
				advertismentVO.setAd_title(rs.getString("ad_title"));
				advertismentVO.setAd_content(readerToString(rs.getCharacterStream("ad_content")));
				advertismentVO.setAd_img(rs.getBytes("ad_img"));
				advertismentVO.setAd_date(rs.getTimestamp("ad_date"));
				advertismentVO.setAd_status(rs.getInt("ad_status"));
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
		return advertismentVO;
	}

	@Override
	public List<AdvertismentVO> getAll() {

		List<AdvertismentVO> list = new ArrayList<AdvertismentVO>();
		AdvertismentVO advertismentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				advertismentVO = new AdvertismentVO();
				advertismentVO.setAd_id(rs.getString("ad_id"));
				advertismentVO.setAd_title(rs.getString("ad_title"));
				advertismentVO.setAd_content(readerToString(rs.getCharacterStream("ad_content")));
				advertismentVO.setAd_img(rs.getBytes("ad_img"));
				advertismentVO.setAd_date(rs.getTimestamp("ad_date"));
				advertismentVO.setAd_status(rs.getInt("ad_status"));
				list.add(advertismentVO); // Store the row in the list
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

		AdvertismentJDBCDAO dao = new AdvertismentJDBCDAO();

/*
		// insert()
		AdvertismentVO advertismentVO = new AdvertismentVO();
		advertismentVO.setAd_id("A");
		advertismentVO.setAd_title("A");
		advertismentVO.setAd_content("A");
		advertismentVO.setAd_img(getPictureByteArray("D:/temp/tomcat.gif"));
		advertismentVO.setAd_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		advertismentVO.setAd_status(1);
		dao.insert(advertismentVO);

		// update()
		AdvertismentVO advertismentVO = new AdvertismentVO();
		advertismentVO.setAd_id("A");
		advertismentVO.setAd_title("A");
		advertismentVO.setAd_content("A");
		advertismentVO.setAd_img(getPictureByteArray("D:/temp/tomcat.gif"));
		advertismentVO.setAd_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		advertismentVO.setAd_status(1);
		dao.update(advertismentVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		AdvertismentVO advertismentVO = dao.findByPrimaryKey("A");
		System.out.print(advertismentVO.getAd_id() + ", ");
		System.out.print(advertismentVO.getAd_title() + ", ");
		System.out.print(advertismentVO.getAd_content() + ", ");
		System.out.print(advertismentVO.getAd_img() + ", ");
		System.out.print(advertismentVO.getAd_date() + ", ");
		System.out.print(advertismentVO.getAd_status() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<AdvertismentVO> list = dao.getAll();
		for (AdvertismentVO aAdvertismentVO : list) {
			System.out.print(aAdvertismentVO.getAd_id() + ", ");
			System.out.print(aAdvertismentVO.getAd_title() + ", ");
			System.out.print(aAdvertismentVO.getAd_content() + ", ");
			System.out.print(aAdvertismentVO.getAd_img() + ", ");
			System.out.print(aAdvertismentVO.getAd_date() + ", ");
			System.out.print(aAdvertismentVO.getAd_status() + ", ");
			System.out.println();
		}

*/
	}


}