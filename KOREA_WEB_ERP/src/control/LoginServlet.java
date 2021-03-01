package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.FindException;
import service.LogInService;
import service.LogInServiceImpl;
import vo.Employee;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 1. 응답형식을 제이슨으로
		 * 2. 응답출력스트림얻기
		 * 3. 요청전달데이터얻기
		 * 4. 핵심로직얻기(loginServicelmpl)
		 */
		//1 
		response.setContentType("application/json;charset=UTF-8"); //응답형식을 json으로
		//2
		PrintWriter out = response.getWriter();//응답출력스트립얻기
		
		//3
		//클라이언트에서 입력된 id값을 String타입의 변수 id에 넣는다
		String id = request.getParameter("id");
		//클라이언트에서 입력된 pwd값을 String타입의 벼수 pwd에 넣는다
		String pwd = request.getParameter("pwd");
		//4
		LogInService service = new LogInServiceImpl(); //ㅣloginServiceImpl타입의 service객체 생성
		
		try {
			Employee emp = service.logIn(id, pwd); //클라이언트에서 입력한 id값과 pwd값이 service.login의 인자값으로 들어간다음 service.login메소드를 실행시킨다
			
			//세션객체(wb별 객체)얻기
			HttpSession session = request.getSession();
			
			//속성추가("속성명: login, 값:id");
			session.setAttribute("login", id);
			
			//json을 통해 성공했다는 결과를 출력한다
			out.print("{\"status\":1}");
		} catch (FindException e) {
			//로그인실패시 
			e.printStackTrace();
			out.print("{\"status\":-1}");
		} 
	}
}
