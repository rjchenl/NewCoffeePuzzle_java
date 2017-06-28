package com.msg.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedHashSet;
import java.util.Set;
import com.reply.model.ReplyVO;

public class MsgJDBCDAO implements MsgDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO MSG (MSG_ID,MEM_ID,PROD_ID,MSG_CONTENT,MSG_DATE) VALUES ('MSG' || LPAD(to_char(MSG_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT MSG_ID,MEM_ID,PROD_ID,MSG_CONTENT,MSG_DATE FROM MSG ORDER BY MSG_ID";
	private static final String GET_ONE_STMT = "SELECT MSG_ID,MEM_ID,PROD_ID,MSG_CONTENT,MSG_DATE FROM MSG WHERE MSG_ID = ?";
	private static final String GET_Replys_ByMsg_id_STMT = "SELECT REPLY_ID,MSG_ID,MEM_ID,STORE_ID,REPLY_CONTENT,REPLY_DATE FROM REPLY WHERE MSG_ID = ? ORDER BY REPLY_ID";

	private static final String DELETE_REPLYs = "DELETE FROM REPLY WHERE MSG_ID = ?";
	private static final String DELETE_MSG = "DELETE FROM MSG WHERE MSG_ID = ?";
	private static final String UPDATE = "UPDATE MSG SET MEM_ID=?, PROD_ID=?, MSG_CONTENT=?, MSG_DATE=? WHERE MSG_ID = ?";

	@Override
	public void insert(MsgVO msgVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, msgVO.getMem_id());
			pstmt.setString(2, msgVO.getProd_id());
			pstmt.setCharacterStream(3, stringToReader(msgVO.getMsg_content()));
			pstmt.setTimestamp(4, msgVO.getMsg_date());

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
	public void update(MsgVO msgVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, msgVO.getMem_id());
			pstmt.setString(2, msgVO.getProd_id());
			pstmt.setCharacterStream(3, stringToReader(msgVO.getMsg_content()));
			pstmt.setTimestamp(4, msgVO.getMsg_date());
			pstmt.setString(5, msgVO.getMsg_id());

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
	public void delete(String msg_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_REPLYs);

			pstmt.setString(1, msg_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_MSG);

			pstmt.setString(1, msg_id);

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
	public MsgVO findByPrimaryKey(String msg_id) {

		MsgVO msgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, msg_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				msgVO = new MsgVO();
				msgVO.setMsg_id(rs.getString("msg_id"));
				msgVO.setMem_id(rs.getString("mem_id"));
				msgVO.setProd_id(rs.getString("prod_id"));
				msgVO.setMsg_content(readerToString(rs.getCharacterStream("msg_content")));
				msgVO.setMsg_date(rs.getTimestamp("msg_date"));
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
		return msgVO;
	}

	@Override
	public List<MsgVO> getAll() {

		List<MsgVO> list = new ArrayList<MsgVO>();
		MsgVO msgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				msgVO = new MsgVO();
				msgVO.setMsg_id(rs.getString("msg_id"));
				msgVO.setMem_id(rs.getString("mem_id"));
				msgVO.setProd_id(rs.getString("prod_id"));
				msgVO.setMsg_content(readerToString(rs.getCharacterStream("msg_content")));
				msgVO.setMsg_date(rs.getTimestamp("msg_date"));
				list.add(msgVO); // Store the row in the list
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
	public Set<ReplyVO> getReplysByMsg_id(String msg_id){
		Set<ReplyVO> set = new LinkedHashSet<ReplyVO>();
		ReplyVO replyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Replys_ByMsg_id_STMT);
			pstmt.setString(1, msg_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				replyVO = new ReplyVO();
				replyVO.setReply_id(rs.getString("reply_id"));
				replyVO.setMsg_id(rs.getString("msg_id"));
				replyVO.setMem_id(rs.getString("mem_id"));
				replyVO.setStore_id(rs.getString("store_id"));
				replyVO.setReply_content(readerToString(rs.getCharacterStream("reply_content")));
				replyVO.setReply_date(rs.getTimestamp("reply_date"));
				set.add(replyVO); // Store the row in the vector
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

		MsgJDBCDAO dao = new MsgJDBCDAO();

/*
		// insert()
		MsgVO msgVO = new MsgVO();
		msgVO.setMsg_id("A");
		msgVO.setMem_id("A");
		msgVO.setProd_id("A");
		msgVO.setMsg_content("A");
		msgVO.setMsg_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		dao.insert(msgVO);

		// update()
		MsgVO msgVO = new MsgVO();
		msgVO.setMsg_id("A");
		msgVO.setMem_id("A");
		msgVO.setProd_id("A");
		msgVO.setMsg_content("A");
		msgVO.setMsg_date(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		dao.update(msgVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		MsgVO msgVO = dao.findByPrimaryKey("A");
		System.out.print(msgVO.getMsg_id() + ", ");
		System.out.print(msgVO.getMem_id() + ", ");
		System.out.print(msgVO.getProd_id() + ", ");
		System.out.print(msgVO.getMsg_content() + ", ");
		System.out.print(msgVO.getMsg_date() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<MsgVO> list = dao.getAll();
		for (MsgVO aMsgVO : list) {
			System.out.print(aMsgVO.getMsg_id() + ", ");
			System.out.print(aMsgVO.getMem_id() + ", ");
			System.out.print(aMsgVO.getProd_id() + ", ");
			System.out.print(aMsgVO.getMsg_content() + ", ");
			System.out.print(aMsgVO.getMsg_date() + ", ");
			System.out.println();
		}

		Set<ReplyVO> set = dao.getReplysByMsg_id("A");
		for (ReplyVO aReply : set) {
			System.out.print(aReply.getReply_id() + ", ");
			System.out.print(aReply.getMsg_id() + ", ");
			System.out.print(aReply.getMem_id() + ", ");
			System.out.print(aReply.getStore_id() + ", ");
			System.out.print(aReply.getReply_content() + ", ");
			System.out.print(aReply.getReply_date() + ", ");
			System.out.println();
		}

*/
	}


}