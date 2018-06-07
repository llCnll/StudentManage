package web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import service.ScoreService;
import service.impl.ScoreServiceImpl;

import com.google.gson.Gson;

import domain.Course;
import domain.PageBean;
import domain.Score;
import domain.Student;

public class ScoreServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
       
    public ScoreServlet() {
        super();
    }
    
    private ScoreService scs = new ScoreServiceImpl();
    private Logger logger = Logger.getLogger(ScoreServlet.class);

    private HSSFWorkbook exportExcel(String[] title, List<Student> list) throws Exception{  
    	//创建excel工作簿
    	HSSFWorkbook workbook=new HSSFWorkbook();
    	//创建工作表sheet
    	HSSFSheet sheet=workbook.createSheet();
    	//创建第一行
    	HSSFRow row=sheet.createRow(0);
    	HSSFCell cell=null;
    	//插入第一行数据的表头
    	for(int i=0;i<title.length;i++){
    	    cell=row.createCell(i);
    	    cell.setCellValue(title[i]);
    	}
    	//写入数据
    	for (int i=1;i<=list.size();i++){
    		Student st = list.get(i-1);
    	    HSSFRow nrow=sheet.createRow(i);
    	    HSSFCell ncell=nrow.createCell(0);
    	    ncell.setCellValue(st.getId());
    	    ncell=nrow.createCell(1);
    	    ncell.setCellValue(st.getName());
    	    ncell=nrow.createCell(2);
    	    ncell.setCellValue(st.getClasses().getName());
    	    ncell=nrow.createCell(3);
    	    Float gpa = st.getGpa();
    	    ncell.setCellValue(gpa!=null?(gpa+""):"未选课");
    	}
		
		return workbook;
    }  
    
    //打印绩点
    protected void exportGpa(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	
    	request.setCharacterEncoding("UTF-8");
    	//获得要下载的文件名
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	String fileName = "gpa"+format.format(new Date())+".csv";
    	String resource = request.getSession().getServletContext().getRealPath("/temp");
    	
    	//定义表头
    	String[] title={"用户ID","用户姓名","用户班级", "绩点"};
    	
    	//获得学生信息
    	List<Object> paramList = new ArrayList<Object>();
    	
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String classes = request.getParameter("classes");	
		paramList.add(id);
		paramList.add(name);
		paramList.add(classes);
		
		Object[] param = paramList.toArray();
    	
		logger.info("-----正在获得学生绩点列表中----");
		List<Student> list = scs.getGpa(param);
		logger.info("-----获取学生绩点列表成功----\n");
    	
		HSSFWorkbook workbook = exportExcel(title, list);
		FileOutputStream out =  new FileOutputStream(resource+"/"+fileName);
		workbook.write(out);
		out.close();
    	//准备excel完毕
		
    	FileInputStream in = new FileInputStream(resource+"/"+fileName);
    	//获得excel
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
		
		//删除文件
		File file = new File(resource+"/"+fileName);
		file.delete();
		
	}
    //获取绩点
    protected void getGpa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	List<Object> paramList = new ArrayList<Object>();
		
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String classes = request.getParameter("classes");
		
		String currentPageStr = request.getParameter("page");
		String currentCountStr = request.getParameter("currentCount");
		if(currentPageStr == null || "".equals(currentPageStr)) {
			currentPageStr = "1";
		}
		if(currentCountStr == null || "".equals(currentCountStr)) {
			currentCountStr = "5";//默认值 每页显示5个
		}
		Integer currentPage = Integer.parseInt(currentPageStr);
		Integer currentCount = Integer.parseInt(currentCountStr);
		System.out.println("currentPage:"+currentPage+" currentCount:"+ currentCount);
		
		paramList.add(id);
		paramList.add(name);
		paramList.add(classes);
		
		Object[] param = paramList.toArray();
		logger.info("-----正在获得学生绩点列表中----");
		PageBean<Student> pageBean = scs.getGpa(currentPage, currentCount, param);
		logger.info("-----获取学生绩点列表成功----\n");
		Gson gson = new Gson();
		String json = gson.toJson(pageBean);
		/*response.setCharacterEncoding("UTF-8");*/
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(json);
    }	
    //批量添加
  	protected void addBactch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  			
  		String[] ids = request.getParameterValues("id");
  		String[] courseNames = request.getParameterValues("courseName");
  		String[] semesters = request.getParameterValues("semester");
  		String[] score1s = request.getParameterValues("score1");
  		String[] score2s = request.getParameterValues("score2");
  		String[] score3s = request.getParameterValues("score3");
  		
  		List<Student> list = new ArrayList<Student>();
  		logger.info("-----正在批量录入成绩中----");
  		for(int i = 0; ids != null &&  i < ids.length; ++i){
  			
  			Student st = new Student();
  			Course c = new Course();
  			Score sc = new Score();
  			
  			st.setId(ids[i]);
  			c.setName(courseNames[i]);
  			String score1 = score1s[i];
  			String score2 = score2s[i];
  			String score3 = score3s[i];
  			sc.setScore1((score1 != null&&!"".equals(score1))?Float.parseFloat(score1):null);
  			sc.setScore2((score2 != null&&!"".equals(score2))?Float.parseFloat(score2):null);
  			sc.setScore3((score3 != null&&!"".equals(score3))?Float.parseFloat(score3):null);
  			sc.setSemester(semesters[i]);
  			c.setScore(sc);
  			st.setCourses(new ArrayList<Course>());
  			st.getCourses().add(c);
  			
  			list.add(st);
  		}
  		
  		List<String> successId = scs.scoreAddBatch(list);
  		logger.info("-----批量录入课程成功----\n");
  	}	
    
    //保存一个学生的成绩
	protected void saveScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Student st = new Student();
		Course c = new Course();
		Score sc = new Score();
		
		st.setId(request.getParameter("id"));
		c.setId(Integer.parseInt(request.getParameter("cid")));
		String score1 = request.getParameter("score1");
		String score2 = request.getParameter("score2");
		String score3 = request.getParameter("score3");
		sc.setScore1((score1 != null&&!"".equals(score1))?Float.parseFloat(score1):null);
		sc.setScore2((score2 != null&&!"".equals(score2))?Float.parseFloat(score2):null);
		sc.setScore3((score3 != null&&!"".equals(score3))?Float.parseFloat(score3):null);
		sc.setSemester(request.getParameter("semester"));
		c.setScore(sc);
		st.setCourses(new ArrayList<Course>());
		st.getCourses().add(c);
		logger.info("-----正在录入学生成绩中----");
		boolean flag = scs.saveScore(st);
		
		if(flag){
			logger.info("-----录入学生成绩成功----\n");
			request.setAttribute("message", "录入成绩成功!");
			request.getRequestDispatcher("/jsp/score/list.jsp").forward(request, response);;
		}else{
			request.setAttribute("error", "录入成绩失败!");
			logger.info("-----录入学生成绩失败----\n");
			request.getRequestDispatcher("/jsp/score/edit.jsp").forward(request, response);
		}
	}

}
