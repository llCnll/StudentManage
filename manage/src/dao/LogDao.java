package dao;

import java.util.List;

import domain.Log;

public interface LogDao {

	int getCount(Object... param);

	List<Log> list(int i, Integer currentCount, Object... param);

}
