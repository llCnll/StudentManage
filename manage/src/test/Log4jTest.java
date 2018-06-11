package test;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.junit.Test;

public class Log4jTest {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Test
	public void fun1(){
		MDC.put("stid", "16422010");
		//logger.debug("测试");
		logger.error("测试");
		//logger.info("测试");
	}

}
