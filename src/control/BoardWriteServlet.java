package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.AddException;
import exception.FindException;
import service.BoardService;
import service.BoardServiceImpl;
import vo.Board;
import vo.Employee;

public class BoardWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BoardService service=new BoardServiceImpl();
		PrintWriter out = response.getWriter();
		Board b = new Board();

		HttpSession session=request.getSession();
		String loginedId=(String)session.getAttribute("login");
		
		String board_title=request.getParameter("title");
		String board_content=request.getParameter("content");
		String emp_id = loginedId;
		String boardNum=request.getParameter("pord_no");
		

		
		try {
			service.addBoard(board_title,board_content,emp_id);
			String path = "./board.html";//나중에는 게시글목록으로 넘어가자
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
			
		} catch (AddException e) {
			e.printStackTrace();
//			out.print("{\"status\": -1, \"msg\": " + e.getMessage() + "}");
		}
		}

	}
