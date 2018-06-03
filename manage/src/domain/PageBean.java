package domain;

import java.util.List;

public class PageBean<T> {
	
	//当前页数
	private Integer currentPage;
	//每页记录数
	private Integer currentCount;
	//总页数
	private Integer totalPage;
	//总条数
	private Integer totalCount;
	//数据
	private List<T> list;
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PageBean [currentPage=" + currentPage + ", currentCount=" + currentCount + ", totalPage=" + totalPage
				+ ", totalCount=" + totalCount + ", list=" + list + "]";
	}
	
}
