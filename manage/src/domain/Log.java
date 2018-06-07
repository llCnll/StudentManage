package domain;

public class Log {
	/*
	`id` int(11) NOT NULL auto_increment,
	   `stid` varchar(8) default NULL,
	   `class` varchar(255) default NULL,
	   `method` varchar(50) default NULL,
	   `createtime` varchar(50) default NULL,
	   `loglevel` varchar(50) default NULL,
	   `msg` text,*/
	
	private Integer id;
	private Student student;
	private String clazz;
	private String method;
	private String createtime;
	private String loglevel;
	private String msg;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLoglevel() {
		return loglevel;
	}
	public void setLoglevel(String loglevel) {
		this.loglevel = loglevel;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "Log [id=" + id + ", student=" + student + ", clazz=" + clazz
				+ ", method=" + method + ", createtime=" + createtime
				+ ", loglevel=" + loglevel + ", msg=" + msg + "]";
	}
}
