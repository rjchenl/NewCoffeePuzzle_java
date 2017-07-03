package com.tools;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class DBImgReader
 */
public class DBImgReader extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	Connection con;
	
	private void nopic(ServletOutputStream out) throws IOException{
		
		InputStream in = getServletContext().getResourceAsStream("/img/nopic.jpg");
		byte[] buf = new byte[in.available()];
		in.read(buf);
		out.write(buf);
		in.close();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("Big5"); // 處理中文檔名
		response.setContentType("image/gif");
		ServletOutputStream out = response.getOutputStream();

		String action = request.getParameter("action");
		
		if(action != null){
			try {
				String id = request.getParameter("id");
				String pk = new String(id.toUpperCase().getBytes("ISO-8859-1"), "Big5");
				
				String sql;
				switch(action.toLowerCase()){
					case "member":
						sql = "SELECT mem_img FROM member WHERE mem_id = '" + pk + "'";
						break;
					case "store":
						sql = "SELECT store_img FROM store WHERE store_id = '" + pk + "'";
						break;
					case "spndcoffee":
						sql = "SELECT spnd_img FROM spndcoffee WHERE spnd_id = '" + pk + "'";
						break;
					case "news":
						sql = "SELECT news_img FROM news WHERE news_id = '" + pk + "'";
						break;
					case "advertisment":
						sql = "SELECT ad_img FROM advertisment WHERE ad_id = '" + pk + "'";
						break;
					case "product":
						sql = "SELECT prod_img FROM product WHERE prod_id = '" + pk + "'";
						break;
					case "activity":
						sql = "SELECT activ_img FROM activity WHERE activ_id = '" + pk + "'";
						break;
					case "photo_store":
						sql = "SELECT photo FROM photo_store WHERE photo_id = '" + pk + "'";
						break;
					case "admin":
						sql = "SELECT admin_img FROM admin WHERE admin_id = '" + pk + "'";
						break;
					default:
						sql = "";
						break;
				}
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				if (rs.next()) {
					BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream(1));
					byte[] buf = new byte[4 * 1024]; // 4K buffer
					int len;
					while ((len = in.read(buf)) != -1) {
						out.write(buf, 0, len);
					}
					in.close();
				} else {
					nopic(out);
				}
				rs.close();
				stmt.close();
			} catch (Exception e) {
				nopic(out);
			}
		}
		else {
			nopic(out);
		}

	}

	public void init() throws ServletException {
		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ba101g4DB");
			con = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void destroy() {
		try {
			if (con != null) con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
}
