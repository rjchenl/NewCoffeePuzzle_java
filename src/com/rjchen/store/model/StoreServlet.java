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
	System.out.println("doGet start(store)");
		StoreDAO_interface stroeDAO = new StoreJDBCDAO();
		List<StoreVO> storeList = stroeDAO.getAll();
	System.out.println("storeList : "+storeList);
		writeText(response, new Gson().toJson(storeList));
	System.out.println("doGet End(store)");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	System.out.println("doPost start(store)");
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
	System.out.println("Receive json from app : "+jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		StoreDAO_interface storeDAO = new StoreJDBCDAO();
		
		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			
		System.out.println("action(step2): " + action);
			List<StoreVO> storeList = storeDAO.getAll();
			writeText(response, gson.toJson(storeList));
		System.out.println("getAll response : "+response);
		
		}
		
		//以下是取圖片
//		else if(action.equals("getImage")){
//			
//		System.out.println("action(step3): " + action);
//		
//			StoreJDBCDAO storedao = new StoreJDBCDAO();
//			OutputStream os = response.getOutputStream();
//			
//			String activ_id = jsonObject.get("activ_id").getAsString();
//			int imageSize = jsonObject.get("imageSize").getAsInt();
//			byte[] image = actdao.getImage(activ_id);
//			if (image != null) {
//				image = ImageUtil.shrink(image, imageSize);
//				response.setContentType("image/jpeg");
//				response.setContentLength(image.length);
//			}
//			os.write(image);
//		}
//		
//	System.out.println("doPost ended");
	
	}

	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
	System.out.println("writeText start");
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
	System.out.println("outText:(isthatme) " + outText);
		out.print(outText);
	System.out.println("writeText ended");
	}

}
