package control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oreilly.servlet.MultipartRequest;

import exception.FindException;
import exception.ModifyException;
import service.DepartmentScheduleService;
import service.DepartmentScheduleServiceImpl;
import service.EmployeeScheduleService;
import service.EmployeeScheduleServiceImpl;
import service.MyInfoService;
import service.MyInfoServiceImpl;
import vo.Department;
import vo.DepartmentSchedule;
import vo.Employee;
import vo.EmployeeSchedule;

/**
 * Servlet implementation class UpdateEventServlet
 */
public class UpdateEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		mapper.setDateFormat(df);
		
		String emp_id = (String)session.getAttribute("login");
		
//		String saveDirectory = "C:\\SW\\apache-tomcat-9.0.41\\wtpwebapps\\myback\\upload"; // 절대경로, 이클립스경로가 절대아님!
		String saveDirectory = this.getServletContext().getRealPath("upload"); // 웹컨텍스트 실제 배포경로
		int maxPostSize = 5 * 1024 * 1024; // 5mb
		String encoding = "UTF-8";
		
		MultipartRequest mr = new MultipartRequest(request, saveDirectory, maxPostSize, encoding);
		
		String no = mr.getParameter("newScheduleNo");
		int scheduleNo = Integer.parseInt(no);
		String title = mr.getParameter("newScheduleTitle");
		String status = mr.getParameter("newScheduleStatus");
		int Istatus = Integer.parseInt(status);
		String sd = mr.getParameter("newScheduleStartDate");
		String st = mr.getParameter("newScheduleStartTime");
		String sdt = sd+" "+st;
		Date StartDate = null;
		String ed = mr.getParameter("newScheduleEndDate");
		String et = mr.getParameter("newScheduleEndTime");
		String edt = ed+" "+et;
		Date EndDate = null;
		try {
			StartDate = df.parse(sdt);
			EndDate = df.parse(edt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
        Date FirstDate = null;
        Date SecondDate = null;
		try {
			FirstDate = format.parse(sd);
			SecondDate = format.parse(ed);
		} catch (ParseException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
        
        long calDate = FirstDate.getTime() - SecondDate.getTime();
        
        long calDateDays = calDate / ( 24*60*60*1000);
        
        calDateDays = Math.abs(calDateDays);
        
		int use_day = (int)calDateDays + 1;
		
		String Content = mr.getParameter("newScheduleContent");
		String prevFileName = mr.getParameter("prevFileName");
		MyInfoService infoservice = new MyInfoServiceImpl();
		Employee emp_vo = null;
		try {
			emp_vo = infoservice.findById(emp_id);
		} catch (FindException e2) {
			Map<String, Object> map = new HashMap<>();
			map.put("status", -1);
			out.print(mapper.writeValueAsString(map));
			return;
		}
		Department dept_vo = new Department(emp_vo.getD().getDept_id(), emp_vo.getD().getDept_name());
		EmployeeSchedule es = new EmployeeSchedule(scheduleNo, title, Istatus, StartDate, EndDate, Content, emp_vo);
		DepartmentSchedule ds = new DepartmentSchedule(scheduleNo, title, StartDate, EndDate, Content, emp_vo, dept_vo);
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		EmployeeScheduleService service = new EmployeeScheduleServiceImpl();
		DepartmentScheduleService dservice = new DepartmentScheduleServiceImpl();
		try {
			if(Istatus != 4) {
				service.modify(es);
				if(Istatus == 3 && es.getEmp_task_status() != Istatus) {
					service.modifyAnnualLeave(emp_vo.getA(), use_day);
				}
			}else {
				dservice.modify(ds);
				map.put("dept_id", emp_vo.getD().getDept_id());
			}
			list.add(map);
			out.print(mapper.writeValueAsString(list));
		} catch (ModifyException e1) {
			e1.printStackTrace();
			map.put("status", -1);
			out.print(mapper.writeValueAsString(map));
		}
		
		File prevfile = null;
		if(Istatus != 4) {
			prevfile = new File(saveDirectory+"\\EMP_"+no+"_"+prevFileName);
		} else {
			prevfile = new File(saveDirectory+"\\DEPT_"+no+"_"+prevFileName);
		} 
		if( prevfile.exists() ){
			if(prevfile.delete()){ 
				System.out.println("파일삭제 성공"); 
			}else{ 
				System.out.println("파일삭제 실패"); 
			}
		}
		
		Enumeration<String> e = mr.getFileNames();
		while(e.hasMoreElements()) {
			String fileName = e.nextElement();
			File ofile = mr.getFile(fileName);
			if(ofile != null) {
				if(Istatus != 4) {
					File nfile = new File(saveDirectory + "\\EMP_" + scheduleNo + "_" +mr.getOriginalFileName(fileName));
					if( ofile.exists() ) {
						ofile.renameTo(nfile);
					}
				}else {
					File nfile = new File(saveDirectory + "\\DEPT_" + scheduleNo + "_" +mr.getOriginalFileName(fileName));
					if( ofile.exists() ) {
						ofile.renameTo(nfile);
					}
				}
			}
		}
	}
}
