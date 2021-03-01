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
import service.MyInfoServiceDAOImpl;
import vo.Employee;

public class MyInfoServlet extends HttpServlet {
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
		//요청전달데이터 얻기
		//세션객체(wb별 객체)얻기
		HttpSession session = request.getSession();
		//속성추가("속성명: login, 값:id");
		String id= (String) session.getAttribute("login");
		MyInfoServiceDAOImpl service = new MyInfoServiceDAOImpl();
		try {
			Employee e =service.findById(id);
			String  jsonStr = mapper.writeValueAsString(e);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("email",e.getEmail());
			map.put("hpPhone",e.getH_phone());
			String birthday =dateFormat.format(e.getBirth_date());
			map.put("birthday",birthday);
			map.put("address",e.getAddress());
			map.put("employeeId",e.getEmp_id());
			map.put("position",e.getPosition());
			map.put("applyDays",e.getA().used_day+" / "+e.getA().max_day);
			map.put("departmentName",e.getD().getDept_name());
			String hireDate =dateFormat.format(e.getHire_date());
			map.put("hireDate",hireDate);
			map.put("officeTel",e.getOffice_tel());
			jsonStr = mapper.writeValueAsString(map);
			System.out.println("myinfoServlet json");
			out.print(mapper.writeValueAsString(map));
		}catch(FindException e){
			e.printStackTrace();
			out.print("{\"status\":-1}");
		}
	}

}
