package domain;

public class Score {
	
	/*`id` bigint(20) NOT NULL COMMENT '序号',
	   `studentId` varchar(8) default NULL COMMENT '学生学号',
	   `courseId` varchar(10) default NULL COMMENT '课程编号',
	   `semester` varchar(15) default NULL COMMENT '开课学期',
	   `score1` float default NULL COMMENT '一考成绩',
	   `score2` float default NULL COMMENT '二考成绩',
	   `score3` float default NULL COMMENT '三考成绩',*/
	private Long id;
	private String semester;
	private Float score1;
	private Float score2;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public Float getScore1() {
		return score1;
	}
	public void setScore1(Float score1) {
		this.score1 = score1;
	}
	public Float getScore2() {
		return score2;
	}
	public void setScore2(Float score2) {
		this.score2 = score2;
	}
	@Override
	public String toString() {
		return "Score [id=" + id + ", semester=" + semester + ", score1="
				+ score1 + ", score2=" + score2 + "]";
	}
}
