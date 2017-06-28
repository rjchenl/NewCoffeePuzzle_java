package com.news.model;

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

public class NewsJNDIDAO implements NewsDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_NEWS);

			pstmt.setString(1, news_id);

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
	public NewsVO findByPrimaryKey(String news_id) {

		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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


}