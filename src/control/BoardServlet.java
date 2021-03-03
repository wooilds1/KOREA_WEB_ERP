package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.FindException;
import javafx.scene.layout.Border;
import service.BoardService;
import service.BoardServiceImpl;
import vo.Board;

public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//응답형식을 json으로
		response.setContentType("application/json;charset=UTF-8");
		//응답출력스트림얻기
		PrintWriter out = response.getWriter();
		//BoardService 타입의 service 객체얻기
		BoardService service = new BoardServiceImpl();
		
		
		//게시판리스트얻기
		List<Board> boardList; //게시글의 내용들
		
		
		try {

			int totalData; //게시글의 총 갯수
			int cnt_per_page = 10; //한페이지에 보여줄 목록수
			int startPage = 1; //시작페이지값
			int endPage = 0; //끝페이지값
			
			totalData = service.findCount();//게시글의 총 갯수 "select count(*) from board";
			
			int totalPage = (int)Math.ceil((double)totalData/cnt_per_page); //총페이지수 계산
			int thisPage=1; //현재 페이지
			
			
			String strThisPage = request.getParameter("thisPage"); //클라이언트에서 thisPage를 받아서 String타입의 strThisPage에 넣는다
			if(strThisPage != null) {
				thisPage = Integer.parseInt(strThisPage); //thisPage는 int이지만 getParameter로 받아올떈 String이니 int타입으로 형변환 시켜준다
			}
			
			
			int cnt_per_pagegroup = 3;//페이지그룹별 보여줄 페이지목록수
			startPage = ((thisPage-1)/cnt_per_pagegroup)*cnt_per_pagegroup+1;
			endPage = (startPage+cnt_per_pagegroup-1);
			if(totalPage < endPage) {
				endPage = totalPage;
			}
		
		//기본적으로 보여줄 페이지
			try {
				boardList = service.findBoardPage(thisPage, cnt_per_pagegroup);
				System.out.println(boardList);
				
				//jackson 라이브러리 사용해서 map의 내용을 json형태로 응답하기
				ObjectMapper mapper = new ObjectMapper(); //jackson 라이브러리 mapper 객체 생성
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
				
				
				
				for (Board b : boardList) {
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("board_no", b.getBoard_no());
					map1.put("board_title", b.getBoard_title());
				
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String dateFormatStringTime =dateFormat.format(b.getBoard_date());
					
					map1.put("emp_id", b.getEmp_vo().getEmp_id());
					map1.put("board_date", dateFormatStringTime ); //그냥 b.getboard_date를 하면 날짜가 제대로 출력안되니 simpleDateFormat로 포맷해줘야한다
					list.add(map1);
				}
				
				
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("startPage", startPage); //시작페이지
				map2.put("endPage", endPage); //끝페이지
				map2.put("totalPage",totalPage); //총페이지수
				map2.put("thisPage",  thisPage); //현재페이지 
				map2.put("list", list);
				
				out.print(mapper.writeValueAsString(map2));
				System.out.println(list);
		
			} catch (FindException e) {//boardList = service.findBoardPaging() 끝
	 
				e.printStackTrace();
			}
		} catch (FindException e1) {//total = service.getPageNum(); 끝
			
			e1.printStackTrace();
		} 
	}
}
