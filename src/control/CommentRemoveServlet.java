package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.FindException;
import exception.RemoveException;
import service.BoardService;
import service.BoardServiceImpl;
import vo.Board;
import vo.BoardComment;

public class CommentRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String loginedId=(String)session.getAttribute("login");
		BoardComment bc=new BoardComment();
		
		BoardService service=new BoardServiceImpl();
		PrintWriter out = response.getWriter();
		
		String commentNum=request.getParameter("comment_no");
		int cmt_no=Integer.parseInt(commentNum);

		try {
			bc=service.findBoardCommentByNo(cmt_no);
			String writerId=bc.getEmp_vo().getEmp_id();
			
			if(writerId.equals(loginedId)) {
				
				service.removeBoardComment(cmt_no, loginedId);
				String path = "./board.html";
				RequestDispatcher rd = request.getRequestDispatcher(path);
				rd.forward(request, response);
			}

		} catch (RemoveException | FindException e) {
			e.printStackTrace();
			out.print("{\"status\": -1, \"msg\": " + e.getMessage() + "}");
		}

	}

}
