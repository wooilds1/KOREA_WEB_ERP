package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ParseConversionEvent;

import exception.AddException;
import exception.FindException;
import exception.RemoveException;
import service.BoardService;
import service.BoardServiceImpl;
import vo.Board;

/**
 * Servlet implementation class BoardRemoveServlet
 */
public class BoardRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("remove servlet-0");
		response.setContentType("application/json;charset=UTF-8");
		
		HttpSession session = request.getSession();
		String loginedId=(String)session.getAttribute("login");
		
		BoardService service=new BoardServiceImpl();
		PrintWriter out = response.getWriter();
		Board b=new Board();
		
		String boardNum=request.getParameter("board_no");
		int board_no=Integer.parseInt(boardNum);

		try {

			service.removeBoard(board_no, loginedId);
			//System.out.println("remove servlet-1 : boardNum=" + boardNum);
//			String path = "./board.html";
//			RequestDispatcher rd = request.getRequestDispatcher(path);
//			rd.forward(request, response);
			out.print("{\"status\": 1}");
		} catch (RemoveException e) {
			e.printStackTrace();
			out.print("{\"status\": -1, \"msg\": " + e.getMessage() + "}");
		}

		
		
	}


}
