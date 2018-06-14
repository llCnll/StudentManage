package domain;

import java.util.List;

/*
create table
CREATE TABLE `course` (
   `id` varchar(10) NOT NULL COMMENT '课程id',
   `name` varchar(30) default NULL COMMENT '课程名',
   `credithour` float default NULL COMMENT '学分',
   `classhour` int(11) default NULL COMMENT '讲授学时',
   `practicehour` int(11) default NULL COMMENT '实验学时',
   `remark` text COMMENT '备注',
   PRIMARY KEY  (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8*/
public class Student {
	
	private String id;
	private String name;
	private String pwd;
	private Classes classes;
	private Integer roleId;
	
	private Float gpa;
	
	private Integer flag;
	
	private List<Course> courses;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public Float getGpa() {
		return gpa;
	}
	public void setGpa(Float gpa) {
		this.gpa = gpa;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", pwd=" + pwd + ", classes=" + classes + ", roleId=" + roleId
				+ ", gpa=" + gpa + ", courses=" + courses + "]";
	}
}
