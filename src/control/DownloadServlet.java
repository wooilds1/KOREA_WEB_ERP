package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("filename");
		String path = this.getServletContext().getRealPath("upload");
//		ObjectMapper mapper = new ObjectMapper();
//		PrintWriter out = response.getWriter();
		
		response.setContentType("application/octet-stream;charset=UTF-8");
		
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
		
		ServletOutputStream sos = response.getOutputStream();
		
		
		FileInputStream fis = null;
		File f = new File(path +"\\"+ name);
		
//		if(!f.exists()) {
//			Map<String, Integer> map = new HashMap<>();
//			map.put("status", -1);
//			out.print(mapper.writeValueAsString(map));
//			return;
//		}
		
		fis = new FileInputStream(f);
		byte[] buf = new byte[1024];
		int readByteCnt = -1;
		while((readByteCnt = fis.read(buf)) != -1) {
			sos.write(buf, 0, readByteCnt);
		}
		
		fis.close();
	}

}
