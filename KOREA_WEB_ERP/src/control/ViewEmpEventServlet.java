package control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import vo.EmployeeSchedule;


public class ViewEmpEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		mapper.setDateFormat(df);
		
//		String loginId = request.getParameter("loginId");
		String loginId = (String)session.getAttribute("login");
		String checked = request.getParameter("checked");
//		String[] statusArr = request.getParameterValues("status");
//		if(statusArr == null) {
//			List<Map<String, Object>> mapList = new ArrayList<>();
//			out.print(mapper.writeValueAsString(mapList));
//			return;
//		}
//		int[] status = Arrays.stream(statusArr).mapToInt(Integer::parseInt).toArray();
		
		String stringstatus = request.getParameter("status");
		int status = Integer.parseInt(stringstatus);
		String saveDirectory = this.getServletContext().getRealPath("upload");		
		List<Map<String, Object>> mapList = new ArrayList<>();
		EmployeeScheduleService service = new EmployeeScheduleServiceImpl();
		File dir = new File(saveDirectory);
		String[] filenames = dir.list();
		try {
			List<EmployeeSchedule> list = new ArrayList<>();
			list = service.findAllByIdAndStatus(loginId, status);
			for(EmployeeSchedule es : list) {
				if(checked.equals("1")) {
//				for(int value : status) {
//					if(es.getEmp_task_status() == value) {
					Map<String, Object> map = new HashMap<>();
					map.put("title", es.getEmp_title());
					map.put("start", es.getEmp_task_start());
					map.put("end", es.getEmp_task_end());
					map.put("content", es.getEmp_content());
					map.put("id", es.getEmp_schedule_no());
					map.put("emp_id", es.getEmp_vo().getEmp_id());
					map.put("emp_name", es.getEmp_vo().getName());
					map.put("schedule_status", es.getEmp_task_status());
					if(es.getEmp_task_status() == 1) {
						map.put("status_name", "업무");
					}else if(es.getEmp_task_status() == 2) {
						map.put("status_name", "출장");
					}else if(es.getEmp_task_status() == 3) {
						map.put("status_name", "연차");
					}
					if(filenames != null) {
						for(String s : filenames) {
							String[] name = s.split("_");
							if(name[0].equals("EMP") && name[1].equals(Integer.toString(es.getEmp_schedule_no()))) {
								map.put("filename", s);
								map.put("filepath", saveDirectory);
							}
						}
					}
					mapList.add(map);
//					}
//				}
				}
			}
			out.print(mapper.writeValueAsString(mapList));
		} catch (FindException e) {
			Map<String, Object> map = new HashMap<>();
			map.put("status", -1);
			out.print(mapper.writeValueAsString(map));
		}	
	}

}
