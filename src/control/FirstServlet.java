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
import service.MainPageService;
import service.MainPageServiceImpl;
import vo.Commute;
import vo.Employee;

/**
 * 	2021-02-28  조익호
 * 	출근시간만 존재할경우 출근시간,퇴근시간도 찍히게끔되어있어
 * 	조건변경함 (nullPointer Exception)
 */
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");//그냥암기 
		PrintWriter out = response.getWriter(); //그냥암기 
		
		//세션객체(wb별 객체)얻기
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("login");
		
//		String id = request.getParameter("id"); //data의 값 
		
		MainPageService service = new MainPageServiceImpl(); //객체생성 세팅 
		
		ObjectMapper mapper = new ObjectMapper(); //JSON형식으로 쓰기 위한 잭슨 라이브러리 세팅 
		
		Map<String, Object> map = new HashMap<>(); //Map 은 타입이다. Map은 인터페이스 HashMap 은 하위, 업캐스팅해서 사용  
		
		
		//index.html로 name값을 전달하는 코드 
		try {
			String name = service.findName(id); //service에 있는 findName
			map.put("name", name);
			//out.print(mapper.writeValueAsString(map));
		} catch (FindException e) {
			e.printStackTrace();
		}
		
		
		
		Date today = new Date(); //현재날짜 
		SimpleDateFormat dateTypeDay = new SimpleDateFormat("yyyy-MM-dd"); //날짜형식 1
		SimpleDateFormat dateTypeTime = new SimpleDateFormat("HH:mm:ss"); //날짜형식 2
		
		try {
			if(null==service.findStartTime(id)) { //로그인 시 출근시간 추가 및 출력 
				Commute c = new Commute();
				Timestamp t = new Timestamp(System.currentTimeMillis());
				c.setStart_time(t);
				//System.out.println(id);
				Employee e = new Employee();
				e.setEmp_id(id);
				c.setEmp_vo(e);
				service.addCommute(c);
				//System.out.println("세션타임 출근시간 테스트1"+dateTypeTime.format(t));
				map.put("starttime", dateTypeTime.format(t));
				
			}else if(null==service.findEndTime(id)){
				// 퇴근시간이 없을경우 출근시간만 출력 2021-02-28 조익호 
				Timestamp starttime = service.findStartTime(id);
				map.put("starttime", dateTypeTime.format(starttime));
			}else {
				Timestamp starttime = service.findStartTime(id);
				map.put("starttime", dateTypeTime.format(starttime));
				Timestamp endtime = service.findEndTime(id);
				map.put("endtime", dateTypeTime.format(endtime));
			}

			

			out.print(mapper.writeValueAsString(map));
		} catch (FindException e) {
			e.printStackTrace();
		} catch (AddException e) {
			e.printStackTrace();
		}
		
		
	}

}