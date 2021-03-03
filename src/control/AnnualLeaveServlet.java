package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.FindException;
import service.EmployeeScheduleService;
import service.EmployeeScheduleServiceImpl;
import vo.AnnualLeave;


public class AnnualLeaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
		
		String loginedId = (String)session.getAttribute("login");
		
		EmployeeScheduleService service = new EmployeeScheduleServiceImpl();
		AnnualLeave a = new AnnualLeave();
		try {
			a = service.findAnnualLeaveById(loginedId);
			Map<String, Integer> map = new HashMap<>();
			map.put("used_day", a.getUsed_day());
			System.out.println(a.getUsed_day());
			out.print(mapper.writeValueAsString(map));
		} catch (FindException e) {
			e.printStackTrace();
			Map<String, Integer> map = new HashMap<>();
			map.put("status", -1);
			out.print(mapper.writeValueAsString(map));
		}
		
	}
}
