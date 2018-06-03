package domain;

/*CREATE TABLE `classes` (
   `id` int(11) unsigned NOT NULL auto_increment COMMENT '班级Id',
   `name` varchar(20) NOT NULL COMMENT '名称',
   `grade` int(11) default NULL COMMENT '年级',
   `majorId` varchar(20) default NULL COMMENT '所属专业',
   PRIMARY KEY  (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8*/
public class Classes {
	
	private Integer id;
	private String name;
	private Integer grade;
	private String major;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	@Override
	public String toString() {
		return "Classes [id=" + id + ", name=" + name + ", grade=" + grade + ", major=" + major + "]";
	}
}
