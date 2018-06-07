package service.impl;

import java.util.List;

import service.LogService;
import dao.LogDao;
import dao.impl.LogDaoImpl;
import domain.Log;
import domain.PageBean;
import domain.Student;

public class LogServiceImpl implements LogService {
	
	private LogDao ld = new LogDaoImpl();

	public PageBean<Log> logList(Integer currentPage, Integer currentCount, Object... param) {
		//封装pageBean
		PageBean<Log> pageBean = new PageBean<Log>();
		//当前页数
		pageBean.setCurrentCount(currentCount);
		//当前条数
		pageBean.setCurrentPage(currentPage);
		//总条数
		int totalCount = ld.getCount(param);
		pageBean.setTotalCount(totalCount);
		//总页数
		int totalPage = (int)Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		//数据
		List<Log> list = ld.list((currentPage-1)*currentCount, currentCount, param);
		pageBean.setList(list);
		
		return pageBean;
	}

}
