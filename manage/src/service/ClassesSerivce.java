package service;

import java.util.List;

import domain.Classes;
import domain.PageBean;

public interface ClassesSerivce {

	PageBean<Classes> classesList(Integer currentPage, Integer currentCount, Object... param);

	List<Classes> classesList();

	List<String> classesGradeSelect();

	List<String> classesMajorSelect();

	boolean classesAdd(Classes cl);

	boolean classesDel(String id);

	Classes classesEdit(String id);

	boolean classesEditSubmit(Classes cl);

	List<String> classesAddBatch(List<Classes> list);

}
