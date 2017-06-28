package com.activity.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.util.LinkedHashSet;
import java.util.Set;
import com.participant.model.ParticipantVO;
import com.rept_activ.model.Rept_activVO;

public class ActivityJDBCDAO implements ActivityDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO ACTIVITY (ACTIV_ID,MEM_ID,STORE_ID,ACTIV_NAME,ACTIV_STARTTIME,ACTIV_ENDTIME,ACTIV_EXPIRE,ACTIV_IMG,ACTIV_SUMMARY,ACTIV_INTRO,ACTIV_NUM,ACTIV_STORE_CFM) VALUES ('ACTIV' || LPAD(to_char(ACTIV_ID_SQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ACTIV_ID,MEM_ID,STORE_ID,ACTIV_NAME,ACTIV_STARTTIME,ACTIV_ENDTIME,ACTIV_EXPIRE,ACTIV_IMG,ACTIV_SUMMARY,ACTIV_INTRO,ACTIV_NUM,ACTIV_STORE_CFM FROM ACTIVITY ORDER BY ACTIV_ID";
	private static final String GET_ONE_STMT = "SELECT ACTIV_ID,MEM_ID,STORE_ID,ACTIV_NAME,ACTIV_STARTTIME,ACTIV_ENDTIME,ACTIV_EXPIRE,ACTIV_IMG,ACTIV_SUMMARY,ACTIV_INTRO,ACTIV_NUM,ACTIV_STORE_CFM FROM ACTIVITY WHERE ACTIV_ID = ?";
	private static final String GET_Participants_ByActiv_id_STMT = "SELECT ACTIV_ID,MEM_ID FROM PARTICIPANT WHERE ACTIV_ID = ? ORDER BY ACTIV_ID,MEM_ID";
	private static final String GET_Rept_activs_ByActiv_id_STMT = "SELECT ACTIV_ID,MEM_ID,REPO_RSN,REPO_REV FROM REPT_ACTIV WHERE ACTIV_ID = ? ORDER BY ACTIV_ID,MEM_ID";

	private static final String DELETE_PARTICIPANTs = "DELETE FROM PARTICIPANT WHERE ACTIV_ID = ?";
	private static final String DELETE_REPT_ACTIVs = "DELETE FROM REPT_ACTIV WHERE ACTIV_ID = ?";
	private static final String DELETE_ACTIVITY = "DELETE FROM ACTIVITY WHERE ACTIV_ID = ?";
	private static final String UPDATE = "UPDATE ACTIVITY SET MEM_ID=?, STORE_ID=?, ACTIV_NAME=?, ACTIV_STARTTIME=?, ACTIV_ENDTIME=?, ACTIV_EXPIRE=?, ACTIV_IMG=?, ACTIV_SUMMARY=?, ACTIV_INTRO=?, ACTIV_NUM=?, ACTIV_STORE_CFM=? WHERE ACTIV_ID = ?";

	@Override
	public void insert(ActivityVO activityVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, activityVO.getMem_id());
			pstmt.setString(2, activityVO.getStore_id());
			pstmt.setString(3, activityVO.getActiv_name());
			pstmt.setTimestamp(4, activityVO.getActiv_starttime());
			pstmt.setTimestamp(5, activityVO.getActiv_endtime());
			pstmt.setTimestamp(6, activityVO.getActiv_expire());
			pstmt.setBytes(7, activityVO.getActiv_img());
			pstmt.setString(8, activityVO.getActiv_summary());
			pstmt.setCharacterStream(9, stringToReader(activityVO.getActiv_intro()));
			pstmt.setInt(10, activityVO.getActiv_num());
			pstmt.setInt(11, activityVO.getActiv_store_cfm());

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
	public void update(ActivityVO activityVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, activityVO.getMem_id());
			pstmt.setString(2, activityVO.getStore_id());
			pstmt.setString(3, activityVO.getActiv_name());
			pstmt.setTimestamp(4, activityVO.getActiv_starttime());
			pstmt.setTimestamp(5, activityVO.getActiv_endtime());
			pstmt.setTimestamp(6, activityVO.getActiv_expire());
			pstmt.setBytes(7, activityVO.getActiv_img());
			pstmt.setString(8, activityVO.getActiv_summary());
			pstmt.setCharacterStream(9, stringToReader(activityVO.getActiv_intro()));
			pstmt.setInt(10, activityVO.getActiv_num());
			pstmt.setInt(11, activityVO.getActiv_store_cfm());
			pstmt.setString(12, activityVO.getActiv_id());

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
	public void delete(String activ_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_PARTICIPANTs);

			pstmt.setString(1, activ_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_REPT_ACTIVs);

			pstmt.setString(1, activ_id);

			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE_ACTIVITY);

			pstmt.setString(1, activ_id);

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
	public ActivityVO findByPrimaryKey(String activ_id) {

		ActivityVO activityVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, activ_id);

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
		return activityVO;
	}

	@Override
	public List<ActivityVO> getAll() {

		List<ActivityVO> list = new ArrayList<ActivityVO>();
		ActivityVO activityVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

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
				list.add(activityVO); // Store the row in the list
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
	public Set<ParticipantVO> getParticipantsByActiv_id(String activ_id){
		Set<ParticipantVO> set = new LinkedHashSet<ParticipantVO>();
		ParticipantVO participantVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Participants_ByActiv_id_STMT);
			pstmt.setString(1, activ_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				participantVO = new ParticipantVO();
				participantVO.setActiv_id(rs.getString("activ_id"));
				participantVO.setMem_id(rs.getString("mem_id"));
				set.add(participantVO); // Store the row in the vector
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

	@Override
	public Set<Rept_activVO> getRept_activsByActiv_id(String activ_id){
		Set<Rept_activVO> set = new LinkedHashSet<Rept_activVO>();
		Rept_activVO rept_activVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Rept_activs_ByActiv_id_STMT);
			pstmt.setString(1, activ_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				rept_activVO = new Rept_activVO();
				rept_activVO.setActiv_id(rs.getString("activ_id"));
				rept_activVO.setMem_id(rs.getString("mem_id"));
				rept_activVO.setRepo_rsn(readerToString(rs.getCharacterStream("repo_rsn")));
				rept_activVO.setRepo_rev(rs.getInt("repo_rev"));
				set.add(rept_activVO); // Store the row in the vector
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

		ActivityJDBCDAO dao = new ActivityJDBCDAO();

/*
		// insert()
		ActivityVO activityVO = new ActivityVO();
		activityVO.setActiv_id("A");
		activityVO.setMem_id("A");
		activityVO.setStore_id("A");
		activityVO.setActiv_name("A");
		activityVO.setActiv_starttime(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		activityVO.setActiv_endtime(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		activityVO.setActiv_expire(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		activityVO.setActiv_img(getPictureByteArray("D:/temp/tomcat.gif"));
		activityVO.setActiv_summary("A");
		activityVO.setActiv_intro("A");
		activityVO.setActiv_num(1);
		activityVO.setActiv_store_cfm(1);
		dao.insert(activityVO);

		// update()
		ActivityVO activityVO = new ActivityVO();
		activityVO.setActiv_id("A");
		activityVO.setMem_id("A");
		activityVO.setStore_id("A");
		activityVO.setActiv_name("A");
		activityVO.setActiv_starttime(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		activityVO.setActiv_endtime(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		activityVO.setActiv_expire(java.sql.Timestamp.valueOf("2007-12-03 10:15:30"));
		activityVO.setActiv_img(getPictureByteArray("D:/temp/tomcat.gif"));
		activityVO.setActiv_summary("A");
		activityVO.setActiv_intro("A");
		activityVO.setActiv_num(1);
		activityVO.setActiv_store_cfm(1);
		dao.update(activityVO);

		// delete()
		dao.delete("A");

		// findByPrimaryKey()
		ActivityVO activityVO = dao.findByPrimaryKey("A");
		System.out.print(activityVO.getActiv_id() + ", ");
		System.out.print(activityVO.getMem_id() + ", ");
		System.out.print(activityVO.getStore_id() + ", ");
		System.out.print(activityVO.getActiv_name() + ", ");
		System.out.print(activityVO.getActiv_starttime() + ", ");
		System.out.print(activityVO.getActiv_endtime() + ", ");
		System.out.print(activityVO.getActiv_expire() + ", ");
		System.out.print(activityVO.getActiv_img() + ", ");
		System.out.print(activityVO.getActiv_summary() + ", ");
		System.out.print(activityVO.getActiv_intro() + ", ");
		System.out.print(activityVO.getActiv_num() + ", ");
		System.out.print(activityVO.getActiv_store_cfm() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<ActivityVO> list = dao.getAll();
		for (ActivityVO aActivityVO : list) {
			System.out.print(aActivityVO.getActiv_id() + ", ");
			System.out.print(aActivityVO.getMem_id() + ", ");
			System.out.print(aActivityVO.getStore_id() + ", ");
			System.out.print(aActivityVO.getActiv_name() + ", ");
			System.out.print(aActivityVO.getActiv_starttime() + ", ");
			System.out.print(aActivityVO.getActiv_endtime() + ", ");
			System.out.print(aActivityVO.getActiv_expire() + ", ");
			System.out.print(aActivityVO.getActiv_img() + ", ");
			System.out.print(aActivityVO.getActiv_summary() + ", ");
			System.out.print(aActivityVO.getActiv_intro() + ", ");
			System.out.print(aActivityVO.getActiv_num() + ", ");
			System.out.print(aActivityVO.getActiv_store_cfm() + ", ");
			System.out.println();
		}

		Set<ParticipantVO> set = dao.getParticipantsByActiv_id("A");
		for (ParticipantVO aParticipant : set) {
			System.out.print(aParticipant.getActiv_id() + ", ");
			System.out.print(aParticipant.getMem_id() + ", ");
			System.out.println();
		}

		Set<Rept_activVO> set = dao.getRept_activsByActiv_id("A");
		for (Rept_activVO aRept_activ : set) {
			System.out.print(aRept_activ.getActiv_id() + ", ");
			System.out.print(aRept_activ.getMem_id() + ", ");
			System.out.print(aRept_activ.getRepo_rsn() + ", ");
			System.out.print(aRept_activ.getRepo_rev() + ", ");
			System.out.println();
		}

*/
	}


}