package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import service.SalaryService;
import service.SalaryServiceImpl;
import vo.Salary;


public class SalaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
			HttpSession session = request.getSession();
			String logindId = (String)session.getAttribute("login");
			PrintWriter out = response.getWriter();
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			SalaryService service = new SalaryServiceImpl();
			List<Salary> list2;

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				Date startDt = sdf.parse(start);
				Date endDt = sdf.parse(end);
				//System.out.println("요청전달데이터를 날짜형식으로 변환:" + startDt + ":" + endDt);
				list2 = service.findByTerm(startDt, endDt, logindId);
				
				//--목록
				int cnt_per_page = 2;
				int currentPage = 1;
				String strCurrentPage = request.getParameter("currentPage");
				if(strCurrentPage != null) { //페이지를 요청전달데이터로 전달한경우
					currentPage = Integer.parseInt(request.getParameter("currentPage"));
				}
				list2 = service.findByTerm(startDt, endDt, currentPage, cnt_per_page, logindId);
				//총건수에해당하는 총페이지수를 계산한다
				int cnt = service.findCntByTerm(startDt, endDt, logindId);
				//총페이지수 
				int totalPage = (int)Math.ceil((double)cnt/cnt_per_page);
				
				System.out.println("총건수:" + cnt + ", 총페이지수: " + totalPage);
				
				//페이지계산
				int cnt_per_page_group =3;
				int start_page = ((currentPage-1)/cnt_per_page_group)*cnt_per_page_group+1;
				int end_page = (start_page+cnt_per_page_group-1);
				if(totalPage<end_page) {
					end_page=totalPage;
				}
			
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonStr = mapper.writeValueAsString(list2);
				List<Map<String,Object>> maplist = new ArrayList<>();
				
				for(Salary s: list2) {
					Map<String,Object> map1 = new HashMap<>();
					String dateFormatStringTime =sdf.format(s.getSalary_date());
					map1.put("salary_date", dateFormatStringTime);
				    map1.put("after_tax_salary", s.getAfter_tax_salary());
				    map1.put("income_tax", s.getIncome_tax());
				    map1.put("hire_insurance", s.getHire_insurance());
				    map1.put("total_deduction",   s.getTotal_deduction());
				    map1.put("extra_pay", s.getExtra_pay());
				    map1.put("position_pay", s.getPosition_pay());
				    map1.put("before_tax_salary", s.getBefore_tax_salary());
				    map1.put("local_income_tax", s.getLocal_income_tax());
				    map1.put("health_insurance", s.getHealth_insurance());
				    map1.put("national_pension", s.getNational_pension());
				maplist.add(map1);
				}
				Map<String, Object> map2 = new HashMap<>();
				
				  map2.put("totalPage",totalPage);
				  map2.put("start_page",start_page);
				  map2.put("end_page",end_page);
				  map2.put("list", maplist);  
//				out.print(mapper.writeValueAsString(maplist));
				out.print(mapper.writeValueAsString(map2));
			
				System.out.println("map for:" + mapper.writeValueAsString(map2));
				
				
			} catch (FindException | ParseException e) {
				e.printStackTrace();
				Map<String, Integer> map = new HashMap<>();
				map.put("status", -1);
				out.print(map);
			}
	}

}
