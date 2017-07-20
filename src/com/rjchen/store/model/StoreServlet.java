package com.rjchen.store.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rjchenl.server.activies.ActivityDAO_interface;
import com.rjchenl.server.activies.ActivityJDBCDAO;
import com.rjchenl.server.activies.ActivityVO;
import com.rjehcnl.server.main.ImageUtil;


@SuppressWarnings("serial")
@WebServlet("/StoreServlet")
public class StoreServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StoreDAO_interface stroeDAO = new StoreJDBCDAO();
		List<StoreVO> storeList = stroeDAO.getAll();
		writeText(response, new Gson().toJson(storeList));
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
	System.out.println("jsonIn : "+jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
	System.out.println("jsonObject : "+jsonObject);
		StoreDAO_interface storeDAO = new StoreJDBCDAO();
		
		String action = jsonObject.get("action").getAsString();
	System.out.println("action : "+action);
		if (action.equals("getAll")) {
			
			List<StoreVO> storeList = storeDAO.getAll();
			writeText(response, gson.toJson(storeList));
		
		}else if(action.equals("getThisStore")){
			
			String store_id = jsonObject.get("store_id").getAsString();
			StoreVO storevo = storeDAO.findByPrimaryKey(store_id);
			writeText(response, gson.toJson(storevo));
		}
		
		//以下是取圖片
		else if(action.equals("getImage")){
			
			StoreJDBCDAO storedao = new StoreJDBCDAO();
			OutputStream os = response.getOutputStream();
			String store_id = jsonObject.get("store_id").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
		
			byte[] image = storedao.getImage(store_id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
				os.write(image);
			}
			
		}
		
	
	}

	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		
	}

}
