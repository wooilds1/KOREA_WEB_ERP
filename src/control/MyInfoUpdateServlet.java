package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.FindException;
import exception.ModifyException;
import service.MyInfoServiceDAOImpl;
import vo.Employee;


public class MyInfoUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//날짜데이터 형식 맞추기
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				//JAVA객체를 JSON문자열로 쓰기
				ObjectMapper mapper = new ObjectMapper();
				//응답형식지정하기
				response.setContentType("application/json;charset=UTF-8");
				//응답출력스트림 얻기
				PrintWriter out = response.getWriter();

				//세션객체(wb별 객체)얻기
				HttpSession session = request.getSession();
				//속성추가("속성명: login, 값:id");
				String id= (String) session.getAttribute("login");
				//요청전달데이터 얻기
				String email = request.getParameter("email");
				String hpphone = request.getParameter("hpPhone");
				String address = request.getParameter("address");
				MyInfoServiceDAOImpl service = new MyInfoServiceDAOImpl();
				try {
					Employee e = new Employee();
					String  jsonStr = mapper.writeValueAsString(e);
					Map<String, Object> map = new HashMap<String, Object>();
					e.setEmp_id(id);
					e.setEmail(email);
					e.setH_phone(hpphone);
					e.setAddress(address);
					service.modify(e);
					map.put("email",e.getEmail());
					map.put("hpPhone",e.getH_phone());
					map.put("address",e.getAddress());
					jsonStr = mapper.writeValueAsString(map);
					out.print(mapper.writeValueAsString(map));
				}catch(ModifyException e){
					e.printStackTrace();
					out.print("{\"status\":-1}");
				}
	}
}
