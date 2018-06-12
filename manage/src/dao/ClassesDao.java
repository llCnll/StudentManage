package dao;

import java.util.List;

import domain.Classes;

public interface ClassesDao {

	Classes getClassesByCid(String string);

	List<Classes> list();

	Classes getClassesByName(String name);

	int getCount();
	
	int getCount(Object... param);

	List<Classes> list(int i, Integer currentCount, Object[] param);

	List<String> classesGradeSelect();

	List<String> classesMajorSelect();

	boolean classesAdd(Classes cl);

	boolean classesDel(String id);

	Classes classesEdit(String id);

	boolean classesEditSubmit(Classes cl);

}
