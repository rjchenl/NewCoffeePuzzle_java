package com.rjchenl.member.model;

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
import java.util.LinkedHashSet;
import java.util.Set;

import com.rate_n_rev.model.Rate_n_revVO;
import com.report.model.ReportVO;
import com.orderlist.model.OrderlistVO;
import com.msg.model.MsgVO;
import com.reply.model.ReplyVO;
import com.activity.model.ActivityVO;
import com.participant.model.ParticipantVO;
import com.rept_activ.model.Rept_activVO;
import com.photo_store.model.Photo_storeVO;
import com.rept_store.model.Rept_storeVO;
import com.rjchenl.fav_store.model.Fav_storeVO;
import com.rjchenl.spndcoffeelist.model.SpndcoffeelistVO;

public class MemberJNDIDAO implements MemberDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO MEMBER (MEM_ID,MEM_ACCT,MEM_PWD,MEM_NAME,MEM_TEL,MEM_EMAIL,MEM_ADD,MEM_POINTS,MEM_IMG) VALUES ('MEM' || LPAD(to_char(MEM_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT MEM_ID,MEM_ACCT,MEM_PWD,MEM_NAME,MEM_TEL,MEM_EMAIL,MEM_ADD,MEM_POINTS,MEM_IMG FROM MEMBER ORDER BY MEM_ID";
	private static final String GET_ONE_STMT = "SELECT MEM_ID,MEM_ACCT,MEM_PWD,MEM_NAME,MEM_TEL,MEM_EMAIL,MEM_ADD,MEM_POINTS,MEM_IMG FROM MEMBER WHERE MEM_ID = ?";
	private static final String GET_Spndcoffeelists_ByMem_id_STMT = "SELECT LIST_ID,SPND_ID,MEM_ID,SPND_PROD,STORE_ID,LIST_AMT,LIST_LEFT,LIST_DATE FROM SPNDCOFFEELIST WHERE MEM_ID = ? ORDER BY LIST_ID";
	private static final String GET_Rate_n_revs_ByMem_id_STMT = "SELECT RNR_ID,MEM_ID,STORE_ID,RNR_RATE,RNR_REV,RNR_DATE FROM RATE_N_REV WHERE MEM_ID = ? ORDER BY RNR_ID";
	private static final String GET_Reports_ByMem_id_STMT = "SELECT MEM_ID,RNR_ID,REPT_VERF,REPT_RSN FROM REPORT WHERE MEM_ID = ? ORDER BY MEM_ID,RNR_ID";
	private static final String GET_Orderlists_ByMem_id_STMT = "SELECT ORD_ID,MEM_ID,STORE_ID,ORD_TOTAL,ORD_PICK,ORD_ADD,ORD_SHIPPING,ORD_TIME,SCORE_BUYER,SCORE_SELLER,REPT_BUYER,REPT_BUYER_RSN,REPT_BUYER_REV,REPT_SELLER,REPT_SELLER_RSN,REPT_SELLER_REV,ORD_ISRETURN,RETURN_RSN FROM ORDERLIST WHERE MEM_ID = ? ORDER BY ORD_ID";
	private static final String GET_Msgs_ByMem_id_STMT = "SELECT MSG_ID,MEM_ID,PROD_ID,MSG_CONTENT,MSG_DATE FROM MSG WHERE MEM_ID = ? ORDER BY MSG_ID";
	private static final String GET_Replys_ByMem_id_STMT = "SELECT REPLY_ID,MSG_ID,MEM_ID,STORE_ID,REPLY_CONTENT,REPLY_DATE FROM REPLY WHERE MEM_ID = ? ORDER BY REPLY_ID";
	private static final String GET_Activitys_ByMem_id_STMT = "SELECT ACTIV_ID,MEM_ID,STORE_ID,ACTIV_NAME,ACTIV_STARTTIME,ACTIV_ENDTIME,ACTIV_EXPIRE,ACTIV_IMG,ACTIV_SUMMARY,ACTIV_INTRO,ACTIV_NUM,ACTIV_STORE_CFM FROM ACTIVITY WHERE MEM_ID = ? ORDER BY ACTIV_ID";
	private static final String GET_Participants_ByMem_id_STMT = "SELECT ACTIV_ID,MEM_ID FROM PARTICIPANT WHERE MEM_ID = ? ORDER BY ACTIV_ID,MEM_ID";
	private static final String GET_Rept_activs_ByMem_id_STMT = "SELECT ACTIV_ID,MEM_ID,REPO_RSN,REPO_REV FROM REPT_ACTIV WHERE MEM_ID = ? ORDER BY ACTIV_ID,MEM_ID";
	private static final String GET_Fav_stores_ByMem_id_STMT = "SELECT MEM_ID,STORE_ID FROM FAV_STORE WHERE MEM_ID = ? ORDER BY MEM_ID,STORE_ID";
	private static final String GET_Photo_stores_ByMem_id_STMT = "SELECT PHOTO_ID,PHOTO,STORE_ID,MEM_ID,PHOTO_DESC FROM PHOTO_STORE WHERE MEM_ID = ? ORDER BY PHOTO_ID";
	private static final String GET_Rept_stores_ByMem_id_STMT = "SELECT STORE_ID,MEM_ID,REPT_RSN,REPT_REV FROM REPT_STORE WHERE MEM_ID = ? ORDER BY STORE_ID,MEM_ID";

	private static final String DELETE_SPNDCOFFEELISTs = "DELETE FROM SPNDCOFFEELIST WHERE MEM_ID = ?";
	private static final String DELETE_RATE_N_REVs = "DELETE FROM RATE_N_REV WHERE MEM_ID = ?";
	private static final String DELETE_REPORTs = "DELETE FROM REPORT WHERE MEM_ID = ?";
	private static final String DELETE_ORDERLISTs = "DELETE FROM ORDERLIST WHERE MEM_ID = ?";
	private static final String DELETE_MSGs = "DELETE FROM MSG WHERE MEM_ID = ?";
	private static final String DELETE_REPLYs = "DELETE FROM REPLY WHERE MEM_ID = ?";
	private static final String DELETE_ACTIVITYs = "DELETE FROM ACTIVITY WHERE MEM_ID = ?";
	private static final String DELETE_PARTICIPANTs = "DELETE FROM PARTICIPANT WHERE MEM_ID = ?";
	private static final String DELETE_REPT_ACTIVs = "DELETE FROM REPT_ACTIV WHERE MEM_ID = ?";
	private static final String DELETE_FAV_STOREs = "DELETE FROM FAV_STORE WHERE MEM_ID = ?";
	private static final String DELETE_PHOTO_STOREs = "DELETE FROM PHOTO_STORE WHERE MEM_ID = ?";
	private static final String DELETE_REPT_STOREs = "DELETE FROM REPT_STORE WHERE MEM_ID = ?";
	private static final String DELETE_MEMBER = "DELETE FROM MEMBER WHERE MEM_ID = ?";
	private static final String UPDATE = "UPDATE MEMBER SET MEM_ACCT=?, MEM_PWD=?, MEM_NAME=?, MEM_TEL=?, MEM_EMAIL=?, MEM_ADD=?, MEM_POINTS=?, MEM_IMG=? WHERE MEM_ID = ?";

	@Override
	public void insert(MemberVO memberVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_SPNDCOFFEELISTs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_RATE_N_REVs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPORTs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ORDERLISTs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_MSGs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPLYs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ACTIVITYs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_PARTICIPANTs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPT_ACTIVs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_FAV_STOREs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_PHOTO_STOREs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPT_STOREs);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_MEMBER);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

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
	public MemberVO findByPrimaryKey(String mem_id) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
	public Set<SpndcoffeelistVO> getSpndcoffeelistsByMem_id(String mem_id){
		Set<SpndcoffeelistVO> set = new LinkedHashSet<SpndcoffeelistVO>();
		SpndcoffeelistVO spndcoffeelistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Spndcoffeelists_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

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
				set.add(spndcoffeelistVO); // Store the row in the vector
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
		return set;
	}

	@Override
	public Set<Rate_n_revVO> getRate_n_revsByMem_id(String mem_id){
		Set<Rate_n_revVO> set = new LinkedHashSet<Rate_n_revVO>();
		Rate_n_revVO rate_n_revVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Rate_n_revs_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rate_n_revVO = new Rate_n_revVO();
				rate_n_revVO.setRnr_id(rs.getString("rnr_id"));
				rate_n_revVO.setMem_id(rs.getString("mem_id"));
				rate_n_revVO.setStore_id(rs.getString("store_id"));
				rate_n_revVO.setRnr_rate(rs.getInt("rnr_rate"));
				rate_n_revVO.setRnr_rev(readerToString(rs.getCharacterStream("rnr_rev")));
				rate_n_revVO.setRnr_date(rs.getTimestamp("rnr_date"));
				set.add(rate_n_revVO); // Store the row in the vector
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
		return set;
	}

	@Override
	public Set<ReportVO> getReportsByMem_id(String mem_id){
		Set<ReportVO> set = new LinkedHashSet<ReportVO>();
		ReportVO reportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Reports_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportVO = new ReportVO();
				reportVO.setMem_id(rs.getString("mem_id"));
				reportVO.setRnr_id(rs.getString("rnr_id"));
				reportVO.setRept_verf(rs.getInt("rept_verf"));
				reportVO.setRept_rsn(readerToString(rs.getCharacterStream("rept_rsn")));
				set.add(reportVO); // Store the row in the vector
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
		return set;
	}

	@Override
	public Set<OrderlistVO> getOrderlistsByMem_id(String mem_id){
		Set<OrderlistVO> set = new LinkedHashSet<OrderlistVO>();
		OrderlistVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Orderlists_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderlistVO = new OrderlistVO();
				orderlistVO.setOrd_id(rs.getString("ord_id"));
				orderlistVO.setMem_id(rs.getString("mem_id"));
				orderlistVO.setStore_id(rs.getString("store_id"));
				orderlistVO.setOrd_total(rs.getInt("ord_total"));
				orderlistVO.setOrd_pick(rs.getInt("ord_pick"));
				orderlistVO.setOrd_add(rs.getString("ord_add"));
				orderlistVO.setOrd_shipping(rs.getInt("ord_shipping"));
				orderlistVO.setOrd_time(rs.getTimestamp("ord_time"));
				orderlistVO.setScore_buyer(rs.getInt("score_buyer"));
				orderlistVO.setScore_seller(rs.getInt("score_seller"));
				orderlistVO.setRept_buyer(rs.getInt("rept_buyer"));
				orderlistVO.setRept_buyer_rsn(readerToString(rs.getCharacterStream("rept_buyer_rsn")));
				orderlistVO.setRept_buyer_rev(rs.getInt("rept_buyer_rev"));
				orderlistVO.setRept_seller(rs.getInt("rept_seller"));
				orderlistVO.setRept_seller_rsn(readerToString(rs.getCharacterStream("rept_seller_rsn")));
				orderlistVO.setRept_seller_rev(rs.getInt("rept_seller_rev"));
				orderlistVO.setOrd_isreturn(rs.getInt("ord_isreturn"));
				orderlistVO.setReturn_rsn(readerToString(rs.getCharacterStream("return_rsn")));
				set.add(orderlistVO); // Store the row in the vector
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
		return set;
	}

	@Override
	public Set<MsgVO> getMsgsByMem_id(String mem_id){
		Set<MsgVO> set = new LinkedHashSet<MsgVO>();
		MsgVO msgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Msgs_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				msgVO = new MsgVO();
				msgVO.setMsg_id(rs.getString("msg_id"));
				msgVO.setMem_id(rs.getString("mem_id"));
				msgVO.setProd_id(rs.getString("prod_id"));
				msgVO.setMsg_content(readerToString(rs.getCharacterStream("msg_content")));
				msgVO.setMsg_date(rs.getTimestamp("msg_date"));
				set.add(msgVO); // Store the row in the vector
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
		return set;
	}

	@Override
	public Set<ReplyVO> getReplysByMem_id(String mem_id){
		Set<ReplyVO> set = new LinkedHashSet<ReplyVO>();
		ReplyVO replyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Replys_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

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

	@Override
	public Set<ActivityVO> getActivitysByMem_id(String mem_id){
		Set<ActivityVO> set = new LinkedHashSet<ActivityVO>();
		ActivityVO activityVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Activitys_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				activityVO = new ActivityVO();
				activityVO.setActiv_id(rs.getString("activ_id"));
				activityVO.setMem_id(rs.getString("mem_id"));
				activityVO.setStore_id(rs.getString("store_id"));
				activityVO.setActiv_name(rs.getString("activ_name"));
				activityVO.setActiv_starttime(rs.getTimestamp("activ_starttime"));
				activityVO.setActiv_endtime(rs.getTimestamp("activ_endtime"));
				activityVO.setActiv_expire(rs.getTimestamp("activ_expire"));
				activityVO.setActiv_img(rs.getBytes("activ_img"));
				activityVO.setActiv_summary(rs.getString("activ_summary"));
				activityVO.setActiv_intro(readerToString(rs.getCharacterStream("activ_intro")));
				activityVO.setActiv_num(rs.getInt("activ_num"));
				activityVO.setActiv_store_cfm(rs.getInt("activ_store_cfm"));
				set.add(activityVO); // Store the row in the vector
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
		return set;
	}

	@Override
	public Set<ParticipantVO> getParticipantsByMem_id(String mem_id){
		Set<ParticipantVO> set = new LinkedHashSet<ParticipantVO>();
		ParticipantVO participantVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Participants_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				participantVO = new ParticipantVO();
				participantVO.setActiv_id(rs.getString("activ_id"));
				participantVO.setMem_id(rs.getString("mem_id"));
				set.add(participantVO); // Store the row in the vector
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
		return set;
	}

	@Override
	public Set<Rept_activVO> getRept_activsByMem_id(String mem_id){
		Set<Rept_activVO> set = new LinkedHashSet<Rept_activVO>();
		Rept_activVO rept_activVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Rept_activs_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_activVO = new Rept_activVO();
				rept_activVO.setActiv_id(rs.getString("activ_id"));
				rept_activVO.setMem_id(rs.getString("mem_id"));
				rept_activVO.setRepo_rsn(readerToString(rs.getCharacterStream("repo_rsn")));
				rept_activVO.setRepo_rev(rs.getInt("repo_rev"));
				set.add(rept_activVO); // Store the row in the vector
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
		return set;
	}

	@Override
	public Set<Fav_storeVO> getFav_storesByMem_id(String mem_id){
		Set<Fav_storeVO> set = new LinkedHashSet<Fav_storeVO>();
		Fav_storeVO fav_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Fav_stores_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				fav_storeVO = new Fav_storeVO();
				fav_storeVO.setMem_id(rs.getString("mem_id"));
				fav_storeVO.setStore_id(rs.getString("store_id"));
				set.add(fav_storeVO); // Store the row in the vector
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
		return set;
	}

	@Override
	public Set<Photo_storeVO> getPhoto_storesByMem_id(String mem_id){
		Set<Photo_storeVO> set = new LinkedHashSet<Photo_storeVO>();
		Photo_storeVO photo_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Photo_stores_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				photo_storeVO = new Photo_storeVO();
				photo_storeVO.setPhoto_id(rs.getString("photo_id"));
				photo_storeVO.setPhoto(rs.getBytes("photo"));
				photo_storeVO.setStore_id(rs.getString("store_id"));
				photo_storeVO.setMem_id(rs.getString("mem_id"));
				photo_storeVO.setPhoto_desc(readerToString(rs.getCharacterStream("photo_desc")));
				set.add(photo_storeVO); // Store the row in the vector
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
		return set;
	}

	@Override
	public Set<Rept_storeVO> getRept_storesByMem_id(String mem_id){
		Set<Rept_storeVO> set = new LinkedHashSet<Rept_storeVO>();
		Rept_storeVO rept_storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Rept_stores_ByMem_id_STMT);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_storeVO = new Rept_storeVO();
				rept_storeVO.setStore_id(rs.getString("store_id"));
				rept_storeVO.setMem_id(rs.getString("mem_id"));
				rept_storeVO.setRept_rsn(readerToString(rs.getCharacterStream("rept_rsn")));
				rept_storeVO.setRept_rev(rs.getInt("rept_rev"));
				set.add(rept_storeVO); // Store the row in the vector
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


}