package com.ming.orderlist.model;
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
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/ming_Orderlist_Servlet")
public class Orderlist_Servlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OrderlistDAO_interface Orderlist_Dao = new OrderlistJDBCDAO();
		List<OrderlistVO> newsList = Orderlist_Dao.getAll();
		writeText(response, new Gson().toJson(newsList));
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
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		OrderlistDAO_interface newsDao = new OrderlistJDBCDAO();
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);

		if (action.equals("getAll")) {
			List<OrderlistVO> newsList = newsDao.getAll();
			writeText(response, gson.toJson(newsList));
		}
		if (action.equals("getOrdelist_1")) {
			String store_id = jsonObject.get("store_id").getAsString();
			List<OrderlistVO> newsList = newsDao.getOrdelist_1(store_id);
			writeText(response, gson.toJson(newsList));
		}else
		if (action.equals("getOrdelist_2")) {
			String store_id = jsonObject.get("store_id").getAsString();
			List<OrderlistVO> newsList = newsDao.getOrdelist_2(store_id);
			writeText(response, gson.toJson(newsList));
		}else
		if (action.equals("getOrdelist_3")) {
			String store_id = jsonObject.get("store_id").getAsString();
			List<OrderlistVO> newsList = newsDao.getOrdelist_3(store_id);
			writeText(response, gson.toJson(newsList));
		}else
		if (action.equals("getOrdelist_4")) {
			String store_id = jsonObject.get("store_id").getAsString();
			List<OrderlistVO> newsList = newsDao.getOrdelist_4(store_id);
			writeText(response, gson.toJson(newsList));
		}else
		if (action.endsWith("getOrdelist_Update")) {
			String store_id = jsonObject.get("store_id").getAsString();
			String ord_id = jsonObject.get("ord_id").getAsString();
			newsDao.getOrdelist_Update(store_id,ord_id);
			writeText(response, gson.toJson(newsDao));
		}else
		if (action.endsWith("getOrdelist_NO_Update")) {
			String store_id = jsonObject.get("store_id").getAsString();
			String ord_id = jsonObject.get("ord_id").getAsString();
			newsDao.getOrdelist_NO_Update(store_id,ord_id);
			writeText(response, gson.toJson(newsDao));
		}else
		if (action.endsWith("getOrdelist_GO_Update")) {
			String store_id = jsonObject.get("store_id").getAsString();
			String ord_id_2 = jsonObject.get("ord_id_2").getAsString();
			newsDao.getOrdelist_GO_Update(store_id,ord_id_2);
			writeText(response, gson.toJson(newsDao));
		}
		if (action.endsWith("getDeliveryUpdate")) {
			String store_id = jsonObject.get("store_id").getAsString();
			String ord_id = jsonObject.get("ord_id").getAsString();
			newsDao.getDeliveryUpdate(store_id,ord_id);
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
