package service;

import domain.Log;
import domain.PageBean;

public interface LogService {

	PageBean<Log> logList(Integer currentPage, Integer currentCount, Object... param);

}
