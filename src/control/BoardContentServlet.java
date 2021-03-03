package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.FindException;
import service.BoardService;
import service.BoardServiceImpl;
import vo.Board;
import vo.BoardComment;
import vo.Employee;

/**
 * Servlet implementation class BoardContentServlet
 */
public class BoardContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Board b;
		Employee e=new Employee();
		
		HttpSession session=request.getSession();
		String loginedId=(String)session.getAttribute("login");

		ObjectMapper mapper=new ObjectMapper();
		
		BoardService service=new BoardServiceImpl();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    mapper.setDateFormat(df);

		List<BoardComment> list;
		
		String StrPord_no = request.getParameter("pord_no");
		int pord_no = Integer.parseInt(StrPord_no);
		
		try {
			b = service.findBoard(pord_no);
			String jsonStr = mapper.writeValueAsString(b);
			
			list = service.findBoardComment(pord_no);
			
			List<Map<String,Object>> maplist = new ArrayList<>();

			Map<String, Object> boardmap = new HashMap<>();
			boardmap.put("board_no", b.getBoard_no());
			boardmap.put("board_title", b.getBoard_title());
			boardmap.put("board_content", b.getBoard_content());
			boardmap.put("board_date", b.getBoard_date());
			boardmap.put("emp_name",b.getEmp_vo().getName());
			
			for (BoardComment bc : list) {
				Map<String,Object>commentmap=new HashMap<>();
				
				commentmap.put("cmt_no", bc.getCmt_no());
				commentmap.put("board_no", bc.getBoard_no());
				commentmap.put("cmt_content", bc.getCmt_content());
				commentmap.put("cmt_date", bc.getCmt_date());
				commentmap.put("emp_name",bc.getEmp_vo().getName());
				commentmap.put("emp_id",bc.getEmp_vo().getEmp_id());
				commentmap.put("cemp_id", bc.getEmp_vo().getEmp_id());
				
				maplist.add(commentmap);
			}
			boardmap.put("commentList", maplist);
			out.print(mapper.writeValueAsString(boardmap));
			
		// e오류 떠서 e1으로 변수명 변경
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (FindException e1) {
			e1.printStackTrace();
		}
		
	}

}

