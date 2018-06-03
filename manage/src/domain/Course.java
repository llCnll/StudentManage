package domain;

/*CREATE TABLE `course` (
   `id` varchar(10) NOT NULL COMMENT '课程id',
   `name` varchar(30) default NULL COMMENT '课程名',
   `credithour` float default NULL COMMENT '学分',
   `classhour` int(11) default NULL COMMENT '讲授学时',
   `practicehour` int(11) default NULL COMMENT '实验学时',
   `remark` text COMMENT '备注',
   PRIMARY KEY  (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8*/

public class Course {
	
	private Integer id;
	private String name;
	private Float credithour;
	private Integer classhour;
	private Integer practicehour;
	private String remark;
	
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
	public Float getCredithour() {
		return credithour;
	}
	public void setCredithour(Float credithour) {
		this.credithour = credithour;
	}
	public Integer getClasshour() {
		return classhour;
	}
	public void setClasshour(Integer classhour) {
		this.classhour = classhour;
	}
	public Integer getPracticehour() {
		return practicehour;
	}
	public void setPracticehour(Integer practicehour) {
		this.practicehour = practicehour;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", credithour=" + credithour + ", classhour=" + classhour
				+ ", practicehour=" + practicehour + ", remark=" + remark + "]";
	}
}
