package com.participant.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ParticipantJDBCDAO implements ParticipantDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";

	private static final String INSERT_STMT = "INSERT INTO PARTICIPANT (ACTIV_ID,MEM_ID) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT ACTIV_ID,MEM_ID FROM PARTICIPANT ORDER BY ACTIV_ID,MEM_ID";
	private static final String GET_ONE_STMT = "SELECT ACTIV_ID,MEM_ID FROM PARTICIPANT WHERE ACTIV_ID = ? AND MEM_ID = ?";
	private static final String DELETE_PARTICIPANT = "DELETE FROM PARTICIPANT WHERE ACTIV_ID = ? AND MEM_ID = ?";

	@Override
	public void insert(ParticipantVO participantVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, participantVO.getActiv_id());
			pstmt.setString(2, participantVO.getMem_id());

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
	public void delete(String activ_id, String mem_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_PARTICIPANT);

			pstmt.setString(1, activ_id);
			pstmt.setString(2, mem_id);

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
	public ParticipantVO findByPrimaryKey(String activ_id, String mem_id) {

		ParticipantVO participantVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, activ_id);
			pstmt.setString(2, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				participantVO = new ParticipantVO();
				participantVO.setActiv_id(rs.getString("activ_id"));
				participantVO.setMem_id(rs.getString("mem_id"));
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
		return participantVO;
	}

	@Override
	public List<ParticipantVO> getAll() {

		List<ParticipantVO> list = new ArrayList<ParticipantVO>();
		ParticipantVO participantVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				participantVO = new ParticipantVO();
				participantVO.setActiv_id(rs.getString("activ_id"));
				participantVO.setMem_id(rs.getString("mem_id"));
				list.add(participantVO); // Store the row in the list
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

	public static void main(String[] args) {

		ParticipantJDBCDAO dao = new ParticipantJDBCDAO();

/*
		// insert()
		ParticipantVO participantVO = new ParticipantVO();
		participantVO.setActiv_id("A");
		participantVO.setMem_id("A");
		dao.insert(participantVO);

		// delete()
		dao.delete("A","A");

		// findByPrimaryKey()
		ParticipantVO participantVO = dao.findByPrimaryKey("A","A");
		System.out.print(participantVO.getActiv_id() + ", ");
		System.out.print(participantVO.getMem_id() + ", ");
		System.out.println("---------------------");

		// getAll()
		List<ParticipantVO> list = dao.getAll();
		for (ParticipantVO aParticipantVO : list) {
			System.out.print(aParticipantVO.getActiv_id() + ", ");
			System.out.print(aParticipantVO.getMem_id() + ", ");
			System.out.println();
		}

*/
	}


}