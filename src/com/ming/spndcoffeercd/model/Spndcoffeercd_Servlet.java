package com.ming.spndcoffeercd.model;

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
import com.ming.orderlist.model.OrderlistDAO_interface;
import com.ming.orderlist.model.OrderlistJDBCDAO;
import com.ming.orderlist.model.OrderlistVO;
import com.ming.store.model.StoreVO;

@SuppressWarnings("serial")
@WebServlet("/ming_Spndcoffeercd_Servlet")
public class Spndcoffeercd_Servlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SpndcoffeercdDAO_interface Spndcoffeercd_Dao = new SpndcoffeercdJDBCDAO();
		List<SpndcoffeercdVO> newsList = Spndcoffeercd_Dao.getAll();
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
		SpndcoffeercdDAO_interface newsDao = new SpndcoffeercdJDBCDAO();
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);

		if (action.equals("getAll")) {
			List<SpndcoffeercdVO> newsList = newsDao.getAll();
			writeText(response, gson.toJson(newsList));
		}
		if (action.equals("getCDInser")) {
			String list_id = jsonObject.get("list_id").getAsString();
			Integer single_amt = 1;
			Timestamp rcd_date = new java.sql.Timestamp(System.currentTimeMillis());
			newsDao.getCDInser(list_id,single_amt,rcd_date);
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
