package com.advertisment.model;

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

public class AdvertismentJNDIDAO implements AdvertismentDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, advertismentVO.getAd_title());
			pstmt.setCharacterStream(2, stringToReader(advertismentVO.getAd_content()));
			pstmt.setBytes(3, advertismentVO.getAd_img());
			pstmt.setTimestamp(4, advertismentVO.getAd_date());
			pstmt.setInt(5, advertismentVO.getAd_status());

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
	public void update(AdvertismentVO advertismentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, advertismentVO.getAd_title());
			pstmt.setCharacterStream(2, stringToReader(advertismentVO.getAd_content()));
			pstmt.setBytes(3, advertismentVO.getAd_img());
			pstmt.setTimestamp(4, advertismentVO.getAd_date());
			pstmt.setInt(5, advertismentVO.getAd_status());
			pstmt.setString(6, advertismentVO.getAd_id());

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
	public void delete(String ad_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_ADVERTISMENT);

			pstmt.setString(1, ad_id);

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
	public AdvertismentVO findByPrimaryKey(String ad_id) {

		AdvertismentVO advertismentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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