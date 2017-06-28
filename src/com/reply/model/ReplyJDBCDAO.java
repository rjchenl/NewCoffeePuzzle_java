package com.reply.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class ReplyJDBCDAO implements ReplyDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO REPLY (REPLY_ID,MSG_ID,MEM_ID,STORE_ID,REPLY_CONTENT,REPLY_DATE) VALUES ('REPLY' || LPAD(to_char(REPLY_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT REPLY_ID,MSG_ID,MEM_ID,STORE_ID,REPLY_CONTENT,REPLY_DATE FROM REPLY ORDER BY REPLY_ID";
	private static final String GET_ONE_STMT = "SELECT REPLY_ID,MSG_ID,MEM_ID,STORE_ID,REPLY_CONTENT,REPLY_DATE FROM REPLY WHERE REPLY_ID = ?";
	private static final String DELETE_REPLY = "DELETE FROM REPLY WHERE REPLY_ID = ?";
	private static final String UPDATE = "UPDATE REPLY SET MSG_ID=?, MEM_ID=?, STORE_ID=?, REPLY_CONTENT=?, REPLY_DATE=? WHERE REPLY_ID = ?";

	@Override
	public void insert(ReplyVO replyVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, replyVO.getMsg_id());
			pstmt.setString(2, replyVO.getMem_id());
			pstmt.setString(3, replyVO.getStore_id());
			pstmt.setCharacterStream(4, stringToReader(replyVO.getReply_content()));
			pstmt.setTimestamp(5, replyVO.getReply_date());

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
	public void update(ReplyVO replyVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, replyVO.getMsg_id());
			pstmt.setString(2, replyVO.getMem_id());
			pstmt.setString(3, replyVO.getStore_id());
			pstmt.setCharacterStream(4, stringToReader(replyVO.getReply_content()));
			pstmt.setTimestamp(5, replyVO.getReply_date());
			pstmt.setString(6, replyVO.getReply_id());

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
	public void delete(String reply_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_REPLY);

			pstmt.setString(1, reply_id);

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
	public ReplyVO findByPrimaryKey(String reply_id) {

		ReplyVO replyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, reply_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				replyVO = new ReplyVO();
				replyVO.setReply_id(rs.getString("reply_id"));
				replyVO.setMsg_id(rs.getString("msg_id"));
				replyVO.setMem_id(rs.getString("mem_id"));
				replyVO.setStore_id(rs.getString("store_id"));
				replyVO.setReply_content(readerToString(rs.getCharacterStream("reply_content")));
				replyVO.setReply_date(rs.getTimestamp("reply_date"));
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
		return replyVO;
	}

	@Override
	public List<ReplyVO> getAll() {

		List<ReplyVO> list = new ArrayList<ReplyVO>();
		ReplyVO replyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				replyVO = new ReplyVO();
				replyVO.setReply_id(rs.getString("reply_id"));
				replyVO.setMsg_id(rs.getString("msg_id"));
				replyVO.setMem_id(rs.getString("mem_id"));
				replyVO.setStore_id(rs.getString("store_id"));
				replyVO.setReply_content(readerToString(rs.getCharacterStream("reply_content")));
				replyVO.setReply_date(rs.getTimestamp("reply_date"));
				list.add(replyVO); // Store the row in the list
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

	public static void main(String[] args) {

		ReplyJDBCDAO dao = new ReplyJDBCDAO();

/*
		// insert()
		ReplyVO replyVO = new ReplyVO();
		replyVO.setReply_id("A");
		replyVO.setMsg_id("A");
		replyVO.setMem_id("A");
		replyVO.setStore_id("A");
		replyVO.setReply_content("A");
		replyVO.setReply_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		dao.insert(replyVO);

		// update()
		ReplyVO replyVO = new ReplyVO();
		replyVO.setReply_id("A");
		replyVO.setMsg_id("A");
		replyVO.setMem_id("A");
		replyVO.setStore_id("A");
		replyVO.setReply_content("A");
		replyVO.setReply_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		dao.update(replyVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		ReplyVO replyVO = dao.findByPrimaryKey("A");
		System.out.print(replyVO.getReply_id() + ", ");
		System.out.print(replyVO.getMsg_id() + ", ");
		System.out.print(replyVO.getMem_id() + ", ");
		System.out.print(replyVO.getStore_id() + ", ");
		System.out.print(replyVO.getReply_content() + ", ");
		System.out.print(replyVO.getReply_date() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<ReplyVO> list = dao.getAll();
		for (ReplyVO aReplyVO : list) {
			System.out.print(aReplyVO.getReply_id() + ", ");
			System.out.print(aReplyVO.getMsg_id() + ", ");
			System.out.print(aReplyVO.getMem_id() + ", ");
			System.out.print(aReplyVO.getStore_id() + ", ");
			System.out.print(aReplyVO.getReply_content() + ", ");
			System.out.print(aReplyVO.getReply_date() + ", ");
			System.out.println();
		}

*/
	}


}