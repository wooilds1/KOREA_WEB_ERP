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
import service.BoardService;
import service.BoardServiceImpl;

/**
 * Servlet implementation class CommentAddServlet
 */
public class CommentAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("CommentAddServlet-0");
		BoardService service=new BoardServiceImpl();
		PrintWriter out = response.getWriter();

		HttpSession session=request.getSession();
		String loginedId=(String)session.getAttribute("login");
		
		String cmt_content=request.getParameter("comment");
		String emp_id = loginedId;
		
		String boardNum=request.getParameter("board_no");
		int board_no=Integer.parseInt(boardNum);
		
		try {
			service.addBoardComment(cmt_content,emp_id,board_no);
			//System.out.println("CommentAddServlet-1");
			out.print("{\"status\": 1}");
//			String path = "./board_content.html";
//			RequestDispatcher rd = request.getRequestDispatcher(path);
//			rd.forward(request, response);
		} catch (AddException e) {
			e.printStackTrace();
			out.print("{\"status\": -1, \"msg\": " + e.getMessage() + "}");
		}

	}


}
