package control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.RemoveException;
import service.DepartmentScheduleService;
import service.DepartmentScheduleServiceImpl;
import service.EmployeeScheduleService;
import service.EmployeeScheduleServiceImpl;

/**
 * Servlet implementation class DeleteEventServlet
 */
public class DeleteEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
//		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
		
		String no = request.getParameter("scheduleNo");
		String status = request.getParameter("status");
		String filepath = request.getParameter("filepath");
		String filename = request.getParameter("filename");
		int Ino = Integer.parseInt(no);
		
		EmployeeScheduleService service = new EmployeeScheduleServiceImpl();
		DepartmentScheduleService dservice = new DepartmentScheduleServiceImpl();
		Map<String, Integer> map = new HashMap<>();
		try {
			if(!status.equals("4")) {
				service.remove(Ino);
			}else {
				dservice.remove(Ino);
			}
			map.put("status", 0);
			out.print(mapper.writeValueAsString(map));
		} catch (RemoveException e) {
			e.printStackTrace();
			map.put("status", -1);
			out.print(mapper.writeValueAsString(map));
		}
		File file = new File(filepath+"\\"+filename);
		if( file.exists() ){
			if(file.delete()){ 
				System.out.println("파일삭제 성공"); 
			}else{ 
				System.out.println("파일삭제 실패"); 
			}
		}
	}
}
