package vk.framework.spring.mapper.mybatis;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Proxy;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import org.slf4j.LoggerFactory;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.springframework.core.io.Resource;
import java.util.TimerTask;
import java.util.Timer;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.mybatis.spring.SqlSessionFactoryBean;

public class RefreshableSqlSessionFactoryBean extends SqlSessionFactoryBean implements DisposableBean {
	private final Logger log;
	private SqlSessionFactory proxy;
	private int interval;
	private Timer timer;
	private TimerTask task;
	private Resource[] mapperLocations;
	private boolean running;
	private final ReentrantReadWriteLock rwl;
	private final Lock r;
	private final Lock w;

	public RefreshableSqlSessionFactoryBean() {
		this.log = LoggerFactory.getLogger((Class) this.getClass());
		this.interval = 1500;
		this.running = false;
		this.rwl = new ReentrantReadWriteLock();
		this.r = this.rwl.readLock();
		this.w = this.rwl.writeLock();
	}

	public void setMapperLocations(final Resource[] mapperLocations) {
		super.setMapperLocations(mapperLocations);
		this.mapperLocations = mapperLocations;
	}

	public void setInterval(final int interval) {
		this.interval = interval;
	}

	public void refresh() throws Exception {
		if (this.log.isInfoEnabled()) {
			this.log.debug("refreshing sqlMapClient.");
		}
		this.w.lock();
		try {
			super.afterPropertiesSet();
		} finally {
			this.w.unlock();
		}
	}

	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		this.setRefreshable();
	}

	private void setRefreshable() {
		this.proxy = (SqlSessionFactory) Proxy.newProxyInstance(SqlSessionFactory.class.getClassLoader(),
				new Class[]{SqlSessionFactory.class}, new InvocationHandler() {
					@Override
					public Object invoke(final Object proxy, final Method method, final Object[] args)
							throws Throwable {
						return method.invoke(RefreshableSqlSessionFactoryBean.this.getParentObject(), args);
					}
				});
		this.task = new TimerTask() {
			private final Map<Resource, Long> map = new HashMap<Resource, Long>();

			@Override
			public void run() {
				if (this.isModified()) {
					try {
						RefreshableSqlSessionFactoryBean.this.refresh();
					} catch (Exception e) {
						RefreshableSqlSessionFactoryBean.this.log.error("caught exception", (Throwable) e);
					}
				}
			}

			private boolean isModified() {
				boolean retVal = false;
				if (RefreshableSqlSessionFactoryBean.this.mapperLocations != null) {
					for (final Resource mappingLocation : RefreshableSqlSessionFactoryBean.this.mapperLocations) {
						retVal |= this.findModifiedResource(mappingLocation);
					}
				}
				return retVal;
			}

			private boolean findModifiedResource(final Resource resource) {
				boolean retVal = false;
				final List<String> modifiedResources = new ArrayList<String>();
				try {
					final long modified = resource.lastModified();
					if (this.map.containsKey(resource)) {
						final long lastModified = this.map.get(resource);
						if (lastModified != modified) {
							this.map.put(resource, new Long(modified));
							modifiedResources.add(resource.getDescription());
							retVal = true;
						}
					} else {
						this.map.put(resource, new Long(modified));
					}
				} catch (IOException e) {
					RefreshableSqlSessionFactoryBean.this.log.error("caught exception", (Throwable) e);
				}
				if (retVal && RefreshableSqlSessionFactoryBean.this.log.isInfoEnabled()) {
					RefreshableSqlSessionFactoryBean.this.log.info("modified files : " + modifiedResources);
				}
				return retVal;
			}
		};
		this.timer = new Timer(true);
		this.resetInterval();
	}

	private Object getParentObject() throws Exception {
		this.r.lock();
		try {
			return super.getObject();
		} finally {
			this.r.unlock();
		}
	}

	public SqlSessionFactory getObject() {
		return this.proxy;
	}

	public Class<? extends SqlSessionFactory> getObjectType() {
		return (this.proxy != null) ? this.proxy.getClass() : SqlSessionFactory.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void setCheckInterval(final int ms) {
		this.interval = ms;
		if (this.timer != null) {
			this.resetInterval();
		}
	}

	private void resetInterval() {
		if (this.running) {
			this.timer.cancel();
			this.running = false;
		}
		if (this.interval > 0) {
			this.timer.schedule(this.task, 0L, this.interval);
			this.running = true;
		}
	}

	public void destroy() throws Exception {
		this.timer.cancel();
	}
}