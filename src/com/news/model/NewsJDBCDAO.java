package com.news.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;

public class NewsJDBCDAO implements NewsDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO NEWS (NEWS_ID,STORE_ID,NEWS_TITLE,NEWS_CONTENT,NEWS_IMG,NEWS_DATE,NEWS_CLASS,NEWS_TOP,NEWS_PASS) VALUES ('NEWS' || LPAD(to_char(NEWS_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT NEWS_ID,STORE_ID,NEWS_TITLE,NEWS_CONTENT,NEWS_IMG,NEWS_DATE,NEWS_CLASS,NEWS_TOP,NEWS_PASS FROM NEWS ORDER BY NEWS_ID";
	private static final String GET_ONE_STMT = "SELECT NEWS_ID,STORE_ID,NEWS_TITLE,NEWS_CONTENT,NEWS_IMG,NEWS_DATE,NEWS_CLASS,NEWS_TOP,NEWS_PASS FROM NEWS WHERE NEWS_ID = ?";
	private static final String DELETE_NEWS = "DELETE FROM NEWS WHERE NEWS_ID = ?";
	private static final String UPDATE = "UPDATE NEWS SET STORE_ID=?, NEWS_TITLE=?, NEWS_CONTENT=?, NEWS_IMG=?, NEWS_DATE=?, NEWS_CLASS=?, NEWS_TOP=?, NEWS_PASS=? WHERE NEWS_ID = ?";

	@Override
	public void insert(NewsVO newsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, newsVO.getStore_id());
			pstmt.setString(2, newsVO.getNews_title());
			pstmt.setCharacterStream(3, stringToReader(newsVO.getNews_content()));
			pstmt.setBytes(4, newsVO.getNews_img());
			pstmt.setTimestamp(5, newsVO.getNews_date());
			pstmt.setString(6, newsVO.getNews_class());
			pstmt.setInt(7, newsVO.getNews_top());
			pstmt.setInt(8, newsVO.getNews_pass());

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
	public void update(NewsVO newsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, newsVO.getStore_id());
			pstmt.setString(2, newsVO.getNews_title());
			pstmt.setCharacterStream(3, stringToReader(newsVO.getNews_content()));
			pstmt.setBytes(4, newsVO.getNews_img());
			pstmt.setTimestamp(5, newsVO.getNews_date());
			pstmt.setString(6, newsVO.getNews_class());
			pstmt.setInt(7, newsVO.getNews_top());
			pstmt.setInt(8, newsVO.getNews_pass());
			pstmt.setString(9, newsVO.getNews_id());

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
	public void delete(String news_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_NEWS);

			pstmt.setString(1, news_id);

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
	public NewsVO findByPrimaryKey(String news_id) {

		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, news_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				newsVO = new NewsVO();
				newsVO.setNews_id(rs.getString("news_id"));
				newsVO.setStore_id(rs.getString("store_id"));
				newsVO.setNews_title(rs.getString("news_title"));
				newsVO.setNews_content(readerToString(rs.getCharacterStream("news_content")));
				newsVO.setNews_img(rs.getBytes("news_img"));
				newsVO.setNews_date(rs.getTimestamp("news_date"));
				newsVO.setNews_class(rs.getString("news_class"));
				newsVO.setNews_top(rs.getInt("news_top"));
				newsVO.setNews_pass(rs.getInt("news_pass"));
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
		return newsVO;
	}

	@Override
	public List<NewsVO> getAll() {

		List<NewsVO> list = new ArrayList<NewsVO>();
		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				newsVO = new NewsVO();
				newsVO.setNews_id(rs.getString("news_id"));
				newsVO.setStore_id(rs.getString("store_id"));
				newsVO.setNews_title(rs.getString("news_title"));
				newsVO.setNews_content(readerToString(rs.getCharacterStream("news_content")));
				newsVO.setNews_img(rs.getBytes("news_img"));
				newsVO.setNews_date(rs.getTimestamp("news_date"));
				newsVO.setNews_class(rs.getString("news_class"));
				newsVO.setNews_top(rs.getInt("news_top"));
				newsVO.setNews_pass(rs.getInt("news_pass"));
				list.add(newsVO); // Store the row in the list
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

		NewsJDBCDAO dao = new NewsJDBCDAO();

/*
		// insert()
		NewsVO newsVO = new NewsVO();
		newsVO.setNews_id("A");
		newsVO.setStore_id("A");
		newsVO.setNews_title("A");
		newsVO.setNews_content("A");
		newsVO.setNews_img(getPictureByteArray("D:/temp/tomcat.gif"));
		newsVO.setNews_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		newsVO.setNews_class("A");
		newsVO.setNews_top(1);
		newsVO.setNews_pass(1);
		dao.insert(newsVO);

		// update()
		NewsVO newsVO = new NewsVO();
		newsVO.setNews_id("A");
		newsVO.setStore_id("A");
		newsVO.setNews_title("A");
		newsVO.setNews_content("A");
		newsVO.setNews_img(getPictureByteArray("D:/temp/tomcat.gif"));
		newsVO.setNews_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		newsVO.setNews_class("A");
		newsVO.setNews_top(1);
		newsVO.setNews_pass(1);
		dao.update(newsVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		NewsVO newsVO = dao.findByPrimaryKey("A");
		System.out.print(newsVO.getNews_id() + ", ");
		System.out.print(newsVO.getStore_id() + ", ");
		System.out.print(newsVO.getNews_title() + ", ");
		System.out.print(newsVO.getNews_content() + ", ");
		System.out.print(newsVO.getNews_img() + ", ");
		System.out.print(newsVO.getNews_date() + ", ");
		System.out.print(newsVO.getNews_class() + ", ");
		System.out.print(newsVO.getNews_top() + ", ");
		System.out.print(newsVO.getNews_pass() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<NewsVO> list = dao.getAll();
		for (NewsVO aNewsVO : list) {
			System.out.print(aNewsVO.getNews_id() + ", ");
			System.out.print(aNewsVO.getStore_id() + ", ");
			System.out.print(aNewsVO.getNews_title() + ", ");
			System.out.print(aNewsVO.getNews_content() + ", ");
			System.out.print(aNewsVO.getNews_img() + ", ");
			System.out.print(aNewsVO.getNews_date() + ", ");
			System.out.print(aNewsVO.getNews_class() + ", ");
			System.out.print(aNewsVO.getNews_top() + ", ");
			System.out.print(aNewsVO.getNews_pass() + ", ");
			System.out.println();
		}

*/
	}


}