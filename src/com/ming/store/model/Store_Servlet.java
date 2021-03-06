package com.ming.store.model;

import java.io.BufferedReader;
import java.io.IOException;
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

@SuppressWarnings("serial")
@WebServlet("/ming_Store_Servlet")
public class Store_Servlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StoreDAO_interface StoreDAO = new StoreJDBCDAO();
		List<StoreVO> newsList = StoreDAO.getAll();
		writeText(response, new Gson().toJson(newsList));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
	
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		System.out.println("jsonObject : "+jsonObject);
		StoreDAO_interface newsDao = new StoreJDBCDAO();
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);

		if (action.equals("getAll")) {
			List<StoreVO> newsList = newsDao.getAll();
			writeText(response, gson.toJson(newsList));
		}
		if (action.equals("findByStore")){
			
			System.out.println("eter findbystore");
			String store_acct = jsonObject.get("store_acct").getAsString();
			String store_pwd = jsonObject.get("store_pwd").getAsString();
			StoreVO storeVO = newsDao.findByStore(store_acct);
			
			if(storeVO.getStore_pwd().trim().equals(store_pwd.trim())){
				writeText(response,gson.toJson(storeVO));
				System.out.println("gson.toJson(storeVO) : "+gson.toJson(storeVO));
			}else{
				writeText(response,"");
			}
		}else{
			writeText(response,"");
		}
	}

	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		System.out.println("outText: " + outText);
		out.print(outText);
	}

}
