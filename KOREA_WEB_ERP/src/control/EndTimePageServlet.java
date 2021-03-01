package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.AddException;
import exception.FindException;
import exception.ModifyException;
import service.MainPageService;
import service.MainPageServiceImpl;
import vo.Commute;
import vo.Employee;

/**
 * Servlet implementation class EndTimePageServlet
 */
public class EndTimePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");//그냥암기 
		PrintWriter out = response.getWriter(); //그냥암기 

		MainPageService service = new MainPageServiceImpl();
		
		//세션객체(wb별 객체)얻기
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("login");
//		String id = request.getParameter("id"); //세션으로 대체 
		
		ObjectMapper mapper = new ObjectMapper(); 
		Map<String, Object> map = new HashMap<>();
		
		Date today = new Date(); //현재날짜 
		SimpleDateFormat dateTypeDay = new SimpleDateFormat("yyyy-MM-dd"); //날짜형식 1
		SimpleDateFormat dateTypeTime = new SimpleDateFormat("HH:mm:ss"); //날짜형식 2
		System.out.println("퇴근 테스트 1");
		try {
			if(null==service.findEndTime(id)) { 
				Commute c = new Commute();
				Timestamp t = new Timestamp(System.currentTimeMillis());
				c.setEnd_time(t);
				System.out.println(id);
				Employee e = new Employee();
				e.setEmp_id(id);
				c.setEmp_vo(e);
				service.modifyCommute(c);
				System.out.println("세션타임 퇴근시간 테스트1"+dateTypeTime.format(t));
				map.put("endtime", dateTypeTime.format(t));
			}else {
				Timestamp endtime = service.findEndTime(id);
				map.put("endtime", dateTypeTime.format(endtime));
				System.out.println("세션타임 퇴근시간 테스트2"+dateTypeTime.format(endtime));
			}
			System.out.println("퇴근 테스트 4");
			out.print(mapper.writeValueAsString(map));
		} catch (FindException e) {
			e.printStackTrace();
		} catch (ModifyException e) {
			e.printStackTrace();
		}
		
	}
}
