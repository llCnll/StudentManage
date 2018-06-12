package service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import service.ClassesSerivce;
import dao.ClassesDao;
import dao.impl.ClassesDaoImpl;
import domain.Classes;
import domain.PageBean;

public class ClassesServiceImpl implements ClassesSerivce {

	private ClassesDao cd = new ClassesDaoImpl();
	
	public PageBean<Classes> classesList(Integer currentPage, Integer currentCount, Object... param) {
		//封装pageBean
		PageBean<Classes> pageBean = new PageBean<Classes>();
		//当前页数
		pageBean.setCurrentCount(currentCount);
		//当前条数
		pageBean.setCurrentPage(currentPage);
		//总条数
		int totalCount = cd.getCount(param);
		pageBean.setTotalCount(totalCount);
		//总页数
		int totalPage = (int)Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		//数据
		List<Classes> list = cd.list((currentPage-1)*currentCount, currentCount, param);
		pageBean.setList(list);
		
		//System.out.println(pageBean);
		
		return pageBean;
	}

	public List<Classes> classesList() {
		
		List<Classes> list = cd.list();
		
		return list;
	}

	public List<String> classesGradeSelect() {
		
		List<String> list = cd.classesGradeSelect();
		
		return list;
	}

	public List<String> classesMajorSelect() {
		
		List<String> list = cd.classesMajorSelect();
		
		return list;
	}

	public boolean classesAdd(Classes cl) {
		
		boolean flag = cd.classesAdd(cl);
		
		return flag;
	}

	public boolean classesDel(String id) {
		
		boolean flag = cd.classesDel(id);
		
		return flag;
	}

	public Classes classesEdit(String id) {
		
		Classes cl = cd.classesEdit(id);
		
		return cl;
	}

	public boolean classesEditSubmit(Classes cl) {
		
		boolean flag = cd.classesEditSubmit(cl);
		
		return flag;
	}

	public List<String> classesAddBatch(List<Classes> list) {
		
		List<String> successId = new ArrayList<String>();
		for(Classes cl : list){
			boolean flag = classesAdd(cl);
			if(flag){
				successId.add(cl.getName());
			}else{
				successId.add("0");
			}
		}
		
		return successId;
	}

}
