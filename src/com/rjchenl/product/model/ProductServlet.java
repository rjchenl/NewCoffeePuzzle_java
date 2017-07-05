package com.rjchenl.product.model;

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
import com.rjchenl.spndcoffeelist.model.SpndcoffeelistDAO_interface;
import com.rjchenl.spndcoffeelist.model.SpndcoffeelistJDBCDAO;
import com.rjchenl.spndcoffeelist.model.SpndcoffeelistVO;

@SuppressWarnings("serial")
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductDAO_interface productdao = new ProductJDBCDAO();
		List<ProductVO> productList = productdao.getAll();
		writeText(response, new Gson().toJson(productList));

		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("step1");
	
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println("step2");
			jsonIn.append(line);
		}
	System.out.println("Receive json from app : "+jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		ProductDAO_interface productDAO = new ProductJDBCDAO();
		System.out.println("step3");
		String action = jsonObject.get("action").getAsString();
		
		
		
		

		if (action.equals("getAll")) {
			
			List<ProductVO> productList = productDAO.getAll();
			writeText(response, gson.toJson(productList));
		
		
		}else if(action.equals("getStoreProductByStoreName")){
			System.out.println("step4_1");
			
			String store_name = jsonObject.get("store_name").getAsString();
			System.out.println("step4_2");
			System.out.println("store_name : "+store_name);
			List<ProductVO> productList = productDAO.getStoreProductByStoreName(store_name);
			System.out.println("step4_3");
			writeText(response, gson.toJson(productList));
			System.out.println("step5");
		}
		System.out.println("step6");
	
	
	}
	
	
	
	
	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
	
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		System.out.println("outText:(isthatme) " + outText);
		out.print(outText);
	
	}
	
	
	
	

}
