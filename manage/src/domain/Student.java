package domain;

import java.util.List;

/*create table
CREATE TABLE `student` (
   `id` varchar(8) NOT NULL COMMENT '学号',
   `name` varchar(20) NOT NULL COMMENT '姓名',
   `pwd` varchar(20) NOT NULL COMMENT '密码',
   `classesId` int(11) unsigned NOT NULL COMMENT '班级Id',
   `roleId` int(4) unsigned NOT NULL default '0' COMMENT '0-一般用户 1-管理员',
   PRIMARY KEY  (`id`),
   KEY `FK_student` (`classesId`),
   CONSTRAINT `FK_student` FOREIGN KEY (`classesId`) REFERENCES `classes` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8*/
public class Student {
	
	private String id;
	private String name;
	private String pwd;
	private Classes classes;
	private int roleId;
	
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
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", pwd=" + pwd
				+ ", classes=" + classes + ", roleId=" + roleId + ", courses="
				+ courses + "]";
	}
}
