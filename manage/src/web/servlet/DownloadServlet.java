package web.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DownloadServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(DownloadServlet.class);
    public DownloadServlet() {
        super();
    }
	protected void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fileName = request.getParameter("fileName");
		String resource = request.getSession().getServletContext().getRealPath("/temp");
		logger.info("-----正在下载"+fileName+"模板中----");
		FileInputStream in = null;
		try {
			in = new FileInputStream(resource+"/"+fileName);
		} catch (Exception e) {
			logger.error("-----下载失败!没有"+fileName+"文件-----\n");
			request.setAttribute("error", "下载失败!没有"+fileName+"文件!");
			request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
			return ;
		}
		//设置客户端名称
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		//读取要下载的文件，保存到文件输入流
		OutputStream output = response.getOutputStream();
		//创建缓冲区
		byte buffer[] = new byte[1024];
		int len = 0;
		//循环将输入流中的内容读取到缓冲区当中
		while((len=in.read(buffer))>0){
		//输出缓冲区的内容到浏览器，实现文件下载
			output.write(buffer, 0, len);
		}                  
		//关闭文件输入流
		in.close();
		//关闭输出流
		output.close();
		logger.info("-----下载"+fileName+"模板完成----\n");
	
	}
}
