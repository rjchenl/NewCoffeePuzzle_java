package com.rjchenl.orderdetail.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.rjchenl.orderlist.model.OrderlistDAO_interface;
import com.rjchenl.orderlist.model.OrderlistJDBCDAO;
import com.rjchenl.orderlist.model.OrderlistVO;

@SuppressWarnings("serial")
@WebServlet("/OrderdetailServlet_RJ")
public class OrderdetailServlet extends HttpServlet  {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		OrderdetailDAO_interface orderdetaildao = new OrderdetailJDBCDAO();
		List<OrderdetailVO> orderdetailVO_List = orderdetaildao.getAll();
		writeText(response, new Gson().toJson(orderdetailVO_List));

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		OrderdetailDAO_interface orderdetaildao = new OrderdetailJDBCDAO();

		String action = jsonObject.get("action").getAsString();

		if (action.equals("getAll")) {

			List<OrderdetailVO> orderdetail_List = orderdetaildao.getAll();
			writeText(response, gson.toJson(orderdetail_List));

		} else if (action.equals("getMyOrderDetailByOrdid")) {

			String ord_id = jsonObject.get("ord_id").getAsString();

			List<OrderdetailVO> orderdetail_List = orderdetaildao.findOderdetailVOByOrdid(ord_id);

			writeText(response, gson.toJson(orderdetail_List));
		}
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		System.out.println("writeText start");
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		System.out.println("outText:(isthatme) " + outText);
		out.print(outText);
		System.out.println("writeText ended");
	}

}
