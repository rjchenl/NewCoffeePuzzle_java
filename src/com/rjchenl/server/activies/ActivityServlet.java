package com.rjchenl.server.activies;

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
import com.rjehcnl.server.main.ImageUtil;

@SuppressWarnings("serial")
@WebServlet("/ActivityServlet")
public class ActivityServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	System.out.println("doGet startRR");
		ActivityDAO_interface actDAO = new ActivityJDBCDAO();
		List<ActivityVO> actList = actDAO.getAll();
		writeText(response, new Gson().toJson(actList));
	System.out.println("doGet ended");
		
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
	System.out.println("Receive json from app : "+jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		ActivityDAO_interface actDAO = new ActivityJDBCDAO();
		
		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {
			
		System.out.println("action(step2): " + action);
			List<ActivityVO> actList = actDAO.getAll();
			writeText(response, gson.toJson(actList));
		System.out.println("getAll response : "+response);
		
		}else if(action.equals("getImage")){
			
		System.out.println("action(step3): " + action);
		
			ActivityJDBCDAO actdao = new ActivityJDBCDAO();
			OutputStream os = response.getOutputStream();
			String activ_id = jsonObject.get("activ_id").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = actdao.getImage(activ_id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
			}
			os.write(image);
		}
		
	System.out.println("doPost ended");
	
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