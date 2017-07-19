package com.ming.member.model;

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
import com.ming.orderlist.model.OrderlistDAO_interface;
import com.ming.orderlist.model.OrderlistJDBCDAO;
import com.ming.orderlist.model.OrderlistVO;
import com.ming.store.model.StoreVO;

@SuppressWarnings("serial")
@WebServlet("/ming_Member_Servlet")
public class Member_Servlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDAO_interface Member_Dao = new MemberJDBCDAO();
		List<MemberVO> newsList = Member_Dao.getAll();
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
		MemberDAO_interface newsDao = new MemberJDBCDAO();
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);

		if (action.equals("getAll")) {
			List<MemberVO> newsList = newsDao.getAll();
			writeText(response, gson.toJson(newsList));
		}else
		if(action.equals("findByMem")){
			String mem_acct = jsonObject.get("mem_acct").getAsString();
			String mem_pwd = jsonObject.get("mem_pwd").getAsString();
			MemberVO memberVO = newsDao.findByMem(mem_acct);

			if(memberVO.getMem_pwd().trim().equals(mem_pwd.trim())){
				writeText(response,gson.toJson(memberVO));
				System.out.println("gson.toJson(storeVO) : "+gson.toJson(memberVO));
			}else{
				writeText(response,"");
			}
		}else{
			writeText(response,"");
		}
		if(action.equals("getMem_Insert")) {
			String inser_memid = jsonObject.get("inser_memid").getAsString();
			String inser_mem_psw = jsonObject.get("inser_mem_psw").getAsString();
			String inser_mem_name = jsonObject.get("inser_mem_name").getAsString();
			String inser_mem_nanber = jsonObject.get("inser_mem_nanber").getAsString();
			String inser_mem_mail = jsonObject.get("inser_mem_mail").getAsString();
			String mem_add = null;
			Integer mem_points = 0;
			byte[] mem_img = null;
			
			newsDao.getMem_Insert(inser_memid,inser_mem_psw,inser_mem_name,inser_mem_nanber,inser_mem_mail,mem_add,mem_points,mem_img);
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
