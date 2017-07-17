package com.ming.orderdetail_model;
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
@WebServlet("/ming_Orderdetail_Servlet")
public class Orderdetail_Servlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OrderdetailDAO_interface Orderlist_Dao = new OrderdetailJDBCDAO();
		List<OrderdetailVO> newsList = Orderlist_Dao.getAll();
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
		OrderdetailDAO_interface newsDao = new OrderdetailJDBCDAO();
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);

		if (action.equals("getAll")) {
			List<OrderdetailVO> newsList = newsDao.getAll();
			writeText(response, gson.toJson(newsList));
		}
		if (action.equals("getOrderdetail")) {
			String store_id = jsonObject.get("store_id").getAsString();
			String ord_id = jsonObject.get("ord_id").getAsString();
			List<OrderdetailVO> newsList = newsDao.getOrderdetail(store_id,ord_id);
			writeText(response, gson.toJson(newsList));
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
