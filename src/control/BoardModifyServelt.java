package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.FindException;
import exception.ModifyException;
import service.BoardService;
import service.BoardServiceImpl;
import vo.Board;
import vo.BoardComment;
import vo.Employee;

public class BoardModifyServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BoardService service=new BoardServiceImpl();
		PrintWriter out = response.getWriter();
		Board b=new Board();
		
		String modifytitle=request.getParameter("title");
		String modifycontent=request.getParameter("content");
		
		String StrPord_no = request.getParameter("pord_no");
		int pord_no = Integer.parseInt(StrPord_no);

			try {
				b=service.findBoard(pord_no);

				if(!b.getBoard_content().equals(modifycontent) && !b.getBoard_title().equals(modifytitle)) {
					service.modifyBoard(modifytitle, modifycontent, pord_no);
//					String path = "./board.html";
//					RequestDispatcher rd = request.getRequestDispatcher(path);
//					rd.forward(request, response);
				}
			} catch (ModifyException | FindException e) {
				e.printStackTrace();
			}

	}


}

