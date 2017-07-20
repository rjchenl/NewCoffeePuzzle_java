package com.ming.spndcoffeelist.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
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
@WebServlet("/ming_Spndcoffelist_Servlet")
public class Spndcoffelist_Servlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	//123

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SpndcoffeelistDAO_interface Spnocoffelist_Dao = new SpndcoffeelistJDBCDAO();
		List<SpndcoffeelistVO> newsList = Spnocoffelist_Dao.getAll();
		writeText(response, new Gson().toJson(newsList));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		SpndcoffeelistDAO_interface newsDao = new SpndcoffeelistJDBCDAO();
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);

		if (action.equals("getAll")) {
			List<SpndcoffeelistVO> newsList = newsDao.getAll();
			writeText(response, gson.toJson(newsList));
		}
		if (action.equals("getStore")){
			String store_id = jsonObject.get("store_id").getAsString();
			List<SpndcoffeelistVO> newsList = newsDao.getStore(store_id);
			writeText(response, gson.toJson(newsList));
		}
		if (action.equals("getUpdate")){
			String store_id = jsonObject.get("store_id").getAsString();
			String list_id = jsonObject.get("list_id").getAsString();
			Integer list_left = jsonObject.get("list_left").getAsInt();
			list_left--;
			System.out.println("store_id :" + store_id);
			System.out.println("list_id :" + list_id);
			System.out.println("list_left :" + list_left);
			List<SpndcoffeelistVO> newsList = newsDao.getUpdate(store_id,list_id,list_left);
			writeText(response, gson.toJson(newsList));
		}
		if (action.equals("getInsert")){
			SpndcoffeelistVO spndcoffeelistVO = null;
			String store_id = jsonObject.get("store_id").getAsString();
			String mem_id = jsonObject.get("mem_id").getAsString();
			String spnd_id = jsonObject.get("spnd_id").getAsString();
			String spnd_prod = jsonObject.get("spnd_prod").getAsString();
			Integer list_amt = 5;
			Integer list_left = list_amt; 
			Timestamp list_date = new java.sql.Timestamp(System.currentTimeMillis());

			newsDao.getInsert(store_id,mem_id,spnd_id,spnd_prod,list_amt,list_left,list_date);
			writeText(response, gson.toJson(newsDao));
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