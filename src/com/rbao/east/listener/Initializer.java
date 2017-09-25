package com.rbao.east.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoader;

/**
 * 缓存调度类
 * @author liutq
 *
 */
public final class Initializer implements ServletContextListener {

	private List<Object> starters = new ArrayList<Object>();

	public Initializer() {
		addStarter(new SystemConfigStarter());
	}

	void addStarter(Starter startup) {
		starters.add(startup);
	}
	
	/**
	 * 更新所有缓存
	 */
	public void updateCache(){
		ServletContext ctx=ContextLoader.getCurrentWebApplicationContext().getServletContext();
		Iterator<Object> it = starters.iterator();
		while (it.hasNext()) {
			Starter s = (Starter) it.next();
			s.init(ctx);
		}
	}

	/**
	 * 系统启动时初始化缓存
	 */
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ctx = sce.getServletContext();
		Iterator<Object> it = starters.iterator();
		while (it.hasNext()) {
			Starter s = (Starter) it.next();
			s.init(ctx);
		}
	}
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
