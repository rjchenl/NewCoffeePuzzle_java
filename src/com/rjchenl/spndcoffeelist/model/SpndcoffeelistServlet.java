package com.rjchenl.spndcoffeelist.model;

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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.rjchenl.fav_store.model.Fav_storeVO;
import com.rjchenl.server.activies.ActivityDAO_interface;
import com.rjchenl.server.activies.ActivityJDBCDAO;
import com.rjchenl.server.activies.ActivityVO;
import com.rjehcnl.server.main.ImageUtil;

@SuppressWarnings("serial")
@WebServlet("/SpndcoffeelistServlet")
public class SpndcoffeelistServlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SpndcoffeelistDAO_interface spndDAO = new SpndcoffeelistJDBCDAO();
		List<SpndcoffeelistVO> spndList = spndDAO.getAll();
		writeText(response, new Gson().toJson(spndList));

		
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
//		Gson gson = new Gson();
		Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
	System.out.println("Receive json from app : "+jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		SpndcoffeelistDAO_interface spndDAO = new SpndcoffeelistJDBCDAO();
		
		String action = jsonObject.get("action").getAsString();
		
		
		
		

		if (action.equals("getAll")) {
			
			List<SpndcoffeelistVO> spndList = spndDAO.getAll();
			writeText(response, gson.toJson(spndList));
		
		
		}else if(action.equals("getMySpndCoffeeList")){
			
			String mem_id = jsonObject.get("mem_id").getAsString();
			
			List<SpndcoffeelistVO> spndList = spndDAO.getMySpndCoffeeList(mem_id);
			
			writeText(response, gson.toJson(spndList));
		}
		
		
		
		
		
		
		
//		else if(action.equals("getImage")){
//			
//		
//		
//			SpndcoffeelistJDBCDAO spnddao = new SpndcoffeelistJDBCDAO();
//			OutputStream os = response.getOutputStream();
//			String spnd_id = jsonObject.get("spnd_id").getAsString();
//			int imageSize = jsonObject.get("imageSize").getAsInt();
//			byte[] image = spndDAO.getImage(spnd_id);
//			if (image != null) {
//				image = ImageUtil.shrink(image, imageSize);
//				response.setContentType("image/jpeg");
//				response.setContentLength(image.length);
//			}
//			os.write(image);
//		}
		
	
	
	}
	
	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
	
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		System.out.println("outText:(isthatme) " + outText);
		out.print(outText);
	
	}

}
