package control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import service.DepartmentScheduleService;
import service.DepartmentScheduleServiceImpl;
import vo.DepartmentSchedule;

/**
 * Servlet implementation class ViewDeptEventServlet
 */
public class ViewDeptEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
//		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		mapper.setDateFormat(df);
		
//		String loginId = (String)session.getAttribute("login"); // 나중에 필요하면 쓰기
		String dept_id = request.getParameter("dept_id");
		String checked = request.getParameter("checked");
		if(dept_id == null) {
			List<Map<String, Object>> mapList = new ArrayList<>();
			out.print(mapper.writeValueAsString(mapList));
			return;
		}
		
		List<Map<String, Object>> mapList = new ArrayList<>();
		DepartmentScheduleService service = new DepartmentScheduleServiceImpl();
		String saveDirectory = this.getServletContext().getRealPath("upload");
		List<DepartmentSchedule> list = new ArrayList<>();
		File dir = new File(saveDirectory);
		String[] filenames = dir.list();
		try {
			list = service.findById(dept_id);
			for(DepartmentSchedule ds : list) {
				if(checked.equals("1")) {
					Map<String, Object> map = new HashMap<>();
					map.put("title", ds.getDept_title());
					map.put("start", ds.getDept_task_start());
					map.put("end", ds.getDept_task_end());
					map.put("content", ds.getDept_content());
					map.put("id", ds.getDept_schedule_no());
					map.put("dept_id", ds.getDept_vo().getDept_id());
					map.put("dept_name", ds.getDept_vo().getDept_name());
					map.put("emp_id", ds.getEmp_vo().getEmp_id());
					map.put("emp_name", ds.getEmp_vo().getName());
					map.put("schedule_status", 4);
					if(filenames != null) {
						for(String s : filenames) {
							String[] name = s.split("_");
							if(name[0].equals("DEPT") && name[1].equals(Integer.toString(ds.getDept_schedule_no()))) {
								map.put("filename", s);
								map.put("filepath", saveDirectory);	
							}
						}
					}
					mapList.add(map);
				}else {
					Map<String, Object> map = new HashMap<>();
					mapList.add(map);
				}
			}
			out.print(mapper.writeValueAsString(mapList));
		} catch (FindException e) {
			Map<String, Integer> map = new HashMap<>();
			map.put("status", -1);
			out.print(mapper.writeValueAsString(map));
		}
	}
}
