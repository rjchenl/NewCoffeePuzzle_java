package com.rjchenl.orderlist.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.rjchen.store.model.StoreJDBCDAO;
import com.rjchenl.fav_store.model.Fav_storeDAO_interface;
import com.rjchenl.fav_store.model.Fav_storeJDBCDAO;
import com.rjchenl.fav_store.model.Fav_storeVO;
import com.rjchenl.orderdetail.model.OrderdetailVO;
import com.rjehcnl.server.main.ImageUtil;

@SuppressWarnings("serial")
@WebServlet("/OrderlistServlet")
public class OrderlistServlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		OrderlistDAO_interface orderlistdao = new OrderlistJDBCDAO();
		List<OrderlistVO> orderlistVO_List = orderlistdao.getAll();
		writeText(response, new Gson().toJson(orderlistVO_List));

		
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
		System.out.println("jsonin0711"+jsonIn.toString());
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		OrderlistDAO_interface orderlistdao = new OrderlistJDBCDAO();
		
		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("getAll")) {
			
			List<OrderlistVO> orderlistVO_List = orderlistdao.getAll();
			writeText(response, gson.toJson(orderlistVO_List));
		
		}else if(action.equals("getMyOrderListByMemID")){
			
			System.out.println("getMyOrderListByMemID step1");
			
			String mem_id = jsonObject.get("mem_id").getAsString();
			
			List<OrderlistVO> orderlistVOlist = orderlistdao.getMyOrderList(mem_id);
			System.out.println("getMyOrderListByMemID step2");
			writeText(response, gson.toJson(orderlistVOlist));
			System.out.println("getMyOrderListByMemID step3");
		}
		
		//以下是取圖片
		else if(action.equals("getImage")){
			
		
//			StoreJDBCDAO storedao = new StoreJDBCDAO();
//			OutputStream os = response.getOutputStream();
//			
//			String store_id = jsonObject.get("store_id").getAsString();
//			int imageSize = jsonObject.get("imageSize").getAsInt();
//			byte[] image = storedao.getImage(store_id);
//			if (image != null) {
//				image = ImageUtil.shrink(image, imageSize);
//				response.setContentType("image/jpeg");
//				response.setContentLength(image.length);
//			}
//			os.write(image);
		}
		
	
		
		if(action.equals("orderListInsert")){
			
			
			String OrderListVO = jsonObject.get("OrderListVO").getAsString();
			OrderlistVO orderlistVO = gson.fromJson(OrderListVO, OrderlistVO.class);

			String OrderdtailVOList = jsonObject.get("OrderdtailVOList").getAsString();
		

			Type listType = new TypeToken<ArrayList<OrderdetailVO>>(){}.getType();
			List<OrderdetailVO> orderdetailVO = new Gson().fromJson(OrderdtailVOList, listType);
			
			
			
			orderlistdao.insertWithOrderDetail(orderlistVO, orderdetailVO);

		
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
