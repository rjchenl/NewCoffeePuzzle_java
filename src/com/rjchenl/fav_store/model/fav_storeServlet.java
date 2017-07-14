package com.rjchenl.fav_store.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rjchen.store.model.StoreDAO_interface;
import com.rjchen.store.model.StoreJDBCDAO;
import com.rjchen.store.model.StoreVO;
import com.rjehcnl.server.main.ImageUtil;



@SuppressWarnings("serial")
@WebServlet("/fav_storeServlet")
public class fav_storeServlet extends HttpServlet {
private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		Fav_storeDAO_interface fav_stroeDAO = new Fav_storeJDBCDAO();
		List<Fav_storeVO> fav_storeList = fav_stroeDAO.getAll();
		writeText(response, new Gson().toJson(fav_storeList));
	
		
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
		Fav_storeDAO_interface fav_storeDAO = new Fav_storeJDBCDAO();
		
		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("getAll")) {
			
			List<Fav_storeVO> fav_storeList = fav_storeDAO.getAll();
			writeText(response, gson.toJson(fav_storeList));
		
		}
		
		//以下是取圖片
		else if(action.equals("getImage")){
			
		
			StoreJDBCDAO storedao = new StoreJDBCDAO();
			OutputStream os = response.getOutputStream();
			
			String store_id = jsonObject.get("store_id").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = storedao.getImage(store_id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
			}
			os.write(image);
		}
		
	
		
		if(action.equals("fav_sotreInsert")){
			
			
			String fav_storeJsonObject = jsonObject.get("fav_storeJsonObject").getAsString();
			Fav_storeVO fav_storeVO = gson.fromJson(fav_storeJsonObject, Fav_storeVO.class);
			
			String mem_fromapp = fav_storeVO.getMem_id();
			String stroe_fromapp = fav_storeVO.getStore_id();
			String combination_fromapp = mem_fromapp+stroe_fromapp;
			
			
			List<Fav_storeVO> fav_storeVOList = fav_storeDAO.getCombinationString();
			
			boolean isExist = false;
			
			for(Fav_storeVO vo :fav_storeVOList ){
				
				String combination_fromservlet = vo.getMem_id()+vo.getStore_id();
				if(combination_fromapp.equals(combination_fromservlet)){					
					isExist = true;	
					System.out.println("已加過此店家");
				}
			}
			
			if(!isExist){
				//新增收藏店家
				fav_storeDAO.insert(fav_storeVO);
				System.out.println("未加過此店家");
			}
			
//			writeText(response,isExist.toString());
				
				
				
				
			
		
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
