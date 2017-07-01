package com.rjchenl.server.activies;

import java.io.*;
import java.sql.*;
import java.util.*;

import com.rjehcnl.server.main.Common;

public class ActivityJDBCDAO implements ActivityDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g4";
	String passwd = "ba101g4";
	

	
	private static final String INSERT_STMT=
			"INSERT INTO ACTIVITY (ACTIV_ID,MEM_ID,STORE_ID,ACTIV_NAME,ACTIV_STARTTIME,ACTIV_ENDTIME,ACTIV_EXPIRE,ACTIV_IMG,ACTIV_SUMMARY,ACTIV_INTRO,ACTIV_NUM,ACTIV_STORE_CFM)"
			+ "VALUES ('ACTIV'||LPAD(ACTIV_SQ.NEXTVAL,8,0),?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT=
			"SELECT ACTIV_ID,MEM_ID,STORE_ID,ACTIV_NAME,ACTIV_STARTTIME,ACTIV_ENDTIME,ACTIV_EXPIRE,ACTIV_IMG,ACTIV_SUMMARY,ACTIV_INTRO,ACTIV_NUM,ACTIV_STORE_CFM "
			+ "FROM ACTIVITY";
	private static final String GET_ONE_STMT=
			"SELECT ACTIV_ID,MEM_ID,STORE_ID,ACTIV_NAME,ACTIV_STARTTIME,ACTIV_ENDTIME,ACTIV_EXPIRE,ACTIV_IMG,ACTIV_SUMMARY,ACTIV_INTRO,ACTIV_NUM,ACTIV_STORE_CFM "
			+ "FROM ACTIVITY WHERE ACTIV_ID=?";
	private static final String DELETE=
			"DELETE FROM ACTIVITY WHERE ACTIV_ID=?";
	private static final String UPDATE=
			"UPDATE ACTIVITY SET MEM_ID=?,STORE_ID=?,ACTIV_NAME=?,ACTIV_STARTTIME=?,ACTIV_ENDTIME=?,ACTIV_EXPIRE=?,ACTIV_IMG=?,ACTIV_SUMMARY=?,ACTIV_INTRO=?,ACTIV_NUM=?,ACTIV_STORE_CFM=?"
			+ "WHERE ACTIV_ID=?";
	
	

	
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

	@Override
	public void insert(ActivityVO actVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
	
			
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url,userid,passwd);
				pstmt = con.prepareStatement(INSERT_STMT);
//SQ				pstmt.setString(1,actVO.getActiv_id());
				pstmt.setString(1,actVO.getMem_id());
				pstmt.setString(2,actVO.getStore_id());
				pstmt.setString(3,actVO.getActiv_name());
				pstmt.setTimestamp(4,actVO.getActiv_starttime());
				pstmt.setTimestamp(5,actVO.getActiv_endtime());
				pstmt.setTimestamp(6,actVO.getActiv_expire());
				pstmt.setBytes(7,actVO.getActiv_img());
				pstmt.setString(8,actVO.getActiv_summary());
				pstmt.setString(9,actVO.getActiv_intro());
				pstmt.setInt(10,actVO.getActiv_num());
				pstmt.setInt(11,actVO.getActiv_store_cfm());
				
				pstmt.executeUpdate();
				
			
			
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver."+e.getMessage());
		
			} catch (SQLException se) {
				
				throw new RuntimeException("A database error occured."+se.getMessage());
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
	public void update(ActivityVO actVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			
			pstmt.setString(1,actVO.getMem_id());
			pstmt.setString(2,actVO.getStore_id());
			pstmt.setString(3,actVO.getActiv_name());
			pstmt.setTimestamp(4,actVO.getActiv_starttime());
			pstmt.setTimestamp(5,actVO.getActiv_endtime());
			pstmt.setTimestamp(6,actVO.getActiv_expire());
			pstmt.setBytes(7,actVO.getActiv_img());
			pstmt.setString(8,actVO.getActiv_summary());
			pstmt.setString(9,actVO.getActiv_intro());
			pstmt.setInt(10,actVO.getActiv_num());
			pstmt.setInt(11,actVO.getActiv_store_cfm());
			pstmt.setString(12,actVO.getActiv_id());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver."+e.getMessage());
	
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured."+se.getMessage());
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
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, activ_id);
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver."+e.getMessage());
	
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured."+se.getMessage());
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
		
		ActivityVO actVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1,activ_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				actVO = new ActivityVO();
				actVO.setActiv_id(rs.getString("activ_id"));
				actVO.setMem_id(rs.getString("mem_id"));
				actVO.setStore_id(rs.getString("store_id"));
				actVO.setActiv_name(rs.getString("activ_name"));
				actVO.setActiv_starttime(rs.getTimestamp("activ_starttime"));
				actVO.setActiv_endtime(rs.getTimestamp("activ_endtime"));
				actVO.setActiv_expire(rs.getTimestamp("activ_expire"));
				actVO.setActiv_img(rs.getBytes("activ_img"));
				actVO.setActiv_summary(rs.getString("activ_summary"));
				actVO.setActiv_intro(rs.getString("activ_intro"));
				actVO.setActiv_num(rs.getInt("activ_num"));
				actVO.setActiv_store_cfm(rs.getInt("activ_store_cfm"));
				
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver."+e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured."+se.getMessage());
		}finally {
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
		

		return actVO;
	}

	@Override
	public List<ActivityVO> getAll() {
		List<ActivityVO> list = new ArrayList<ActivityVO>();
		ActivityVO actVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				actVO = new ActivityVO();
				actVO.setActiv_id(rs.getString("activ_id"));
				actVO.setMem_id(rs.getString("mem_id"));
				actVO.setStore_id(rs.getString("store_id"));
				actVO.setActiv_name(rs.getString("activ_name"));
				actVO.setActiv_starttime(rs.getTimestamp("activ_endtime"));
				actVO.setActiv_expire(rs.getTimestamp("activ_expire"));
				actVO.setActiv_starttime(rs.getTimestamp("activ_starttime"));
				actVO.setActiv_img(rs.getBytes("activ_img"));
				actVO.setActiv_summary(rs.getString("activ_summary"));
				actVO.setActiv_intro(rs.getString("activ_intro"));
				actVO.setActiv_num(rs.getInt("activ_num"));
				actVO.setActiv_store_cfm(rs.getInt("activ_store_cfm"));
				list.add(actVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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

		return list;
	}
	
	public static void main(String[] args){
		ActivityJDBCDAO dao = new ActivityJDBCDAO();
		
		//�s�W
//		ActivityVO actVO = new ActivityVO();
//		actVO.setActiv_id("5566");
//		actVO.setMem_id("22222");
//		actVO.setStore_id("33333");
//		actVO.setActiv_name("���a����");
//		actVO.setActiv_starttime(new Timestamp(2016,06,03,12,30,44,00));
//		actVO.setActiv_endtime(new Timestamp(2016,06,03,13,30,44,00));
//		actVO.setActiv_expire(new Timestamp(2016,06,02,13,30,44,00));
//		try {
//			actVO.setActiv_img(getPictureByteArray("images/coffee.jpg"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		actVO.setActiv_summary("���ʪ����e�O...");
//		actVO.setActiv_intro("���ʪ����ЬO");
//		actVO.setActiv_num(20);
//		actVO.setActiv_store_cfm(1);
//		dao.insert(actVO);
		
		
		
		//�ק�
//		ActivityVO actVO2 = new ActivityVO();
//		actVO2.setActiv_id("5566");
//		actVO2.setMem_id("7788");  //�u��o��
//		actVO2.setStore_id("33333");
//		actVO2.setActiv_name("�ڦ���");
//		actVO2.setActiv_starttime(new Timestamp(2016,06,04,12,30,44,00));
//		actVO2.setActiv_endtime(new Timestamp(2016,06,05,13,30,44,00));
//		actVO2.setActiv_expire(new Timestamp(2016,06,01,13,30,44,00));
//		try {
//			actVO2.setActiv_img(getPictureByteArray("images/gener_member.jpg"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		actVO2.setActiv_summary("�קﬡ�ʪ����e�O...");
//		actVO2.setActiv_intro("�קﬡ�ʪ����ЬO");
//		actVO2.setActiv_num(20);
//		actVO2.setActiv_store_cfm(1);
//		dao.update(actVO2);
		
		//�R��
//		dao.delete("5566");
		
		
		//�d��
//		ActivityVO actVO33 = dao.findByPrimaryKey("5566");
//		System.out.println(actVO33.getActiv_id()+",");
//		System.out.println(actVO33.getMem_id()+",");
//		System.out.println(actVO33.getStore_id()+",");
//		System.out.println(actVO33.getActiv_name()+",");
//		System.out.println(actVO33.getActiv_starttime()+",");
//		System.out.println(actVO33.getActiv_endtime()+",");
//		System.out.println(actVO33.getActiv_expire()+",");
//		System.out.println(actVO33.getActiv_img()+",");
//		System.out.println(actVO33.getActiv_summary()+",");
//		System.out.println(actVO33.getActiv_intro()+",");
//		System.out.println(actVO33.getActiv_num()+",");
//		System.out.println(actVO33.getActiv_store_cfm()+",");
//		System.out.println("--------------------------------");
		
		//�d��
		List<ActivityVO> list = dao.getAll();
		for(ActivityVO actVO: list){
			System.out.println(actVO.getActiv_id());
			System.out.println(actVO.getMem_id());
			System.out.println(actVO.getStore_id());
			System.out.println(actVO.getStore_id());
			System.out.println(actVO.getActiv_starttime());
			System.out.println(actVO.getActiv_endtime());
			System.out.println(actVO.getActiv_expire());
			System.out.println(actVO.getActiv_img());
			System.out.println(actVO.getActiv_summary());
			System.out.println(actVO.getActiv_intro());
			System.out.println(actVO.getActiv_num());
			System.out.println(actVO.getActiv_store_cfm());
			System.out.println();
			
			
		}
		
		
		
		
		
		
	}

	@Override
	public byte[] getImage(String activ_id) {
		
		String sql = "SELECT activ_img FROM activity WHERE activ_id = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		byte[] image = null;
		try {
			connection = DriverManager.getConnection(Common.URL, Common.USER,
					Common.PASSWORD);
			ps = connection.prepareStatement(sql);
			ps.setString(1, activ_id);
			ResultSet rs = ps.executeQuery();
		

			if (rs.next()) {
				image = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		}
		return image;
		
	}
	
	
	
	
	
	
	
	
}
