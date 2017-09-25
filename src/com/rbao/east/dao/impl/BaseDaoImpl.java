package com.rbao.east.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.DataAccessException;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.rbao.east.common.Constants;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.dao.BaseDao;
import com.rbao.east.entity.BaseEntity;

@SuppressWarnings({ "unchecked", "unused" })
public class BaseDaoImpl<T extends BaseEntity> extends SqlSessionDaoSupport
		implements BaseDao<T> {

	private Class<T> entityClass;
	private String mapperNamespace;

	public BaseDaoImpl() {
		setEntityClass((Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	public List<T> selectAll() throws DataAccessException {
		List<T> result = new ArrayList<T>();
		try {
			result = this.getSqlSession().selectList(
					getMapperNamespace() + "." + "selectAll");
		} catch (DataAccessException e) {
			throw e;
		}
		return result;
	}

	public Integer getTotalCount(Object params) throws DataAccessException {
		return getSqlSession().selectOne(
				getMapperNamespace() + "." + "getTotalCount", params);
	}

	@Override
	public boolean insertSelective(T entity) throws DataAccessException {
		return insert("insertSelective", entity);
	}

	@Override
	public boolean deleteByPrimaryKey(Integer pk) throws DataAccessException {
		boolean flag = false;
		try {
			flag = this.getSqlSession().delete(
					getMapperNamespace() + "." + "deleteByPrimaryKey", pk) > 0 ? true
					: false;
		} catch (DataAccessException e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	@Override
	public T selectByPrimaryKey(Integer pk) throws DataAccessException {
		T result = null;
		try {
			result = (T) this.getSqlSession().selectOne(
					getMapperNamespace() + "." + "selectByPrimaryKey", pk);
		} catch (DataAccessException e) {
			throw e;
		}
		return result;
	}

	@Override
	public List<T> selectByEntity(T entity) throws DataAccessException {

		return select("selectByEntity", entity);
	}

	@Override
	public boolean delete(String id, T entity) throws DataAccessException {
		boolean flag = false;
		try {
			flag = this.getSqlSession().delete(getMapperNamespace() + "." + id,
					entity) > 0 ? true : false;
		} catch (DataAccessException e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	public Integer getTotalCount(String id, Object params)
			throws DataAccessException {
		return getSqlSession().selectOne(getMapperNamespace() + "." + id,
				params);
	}
	public PageModel getPage(String listId, Object params,PageModel pageModel) {

		List list = this.getSqlSession().selectList(getMapperNamespace() + "." + listId,
													params,
													new PageBounds(pageModel.getCurrentPage(), pageModel.getPageSize()));
		pageModel.setList(list);
		PageList pageList = (PageList)list;
		pageModel.setTotalRecord(pageList.getPaginator().getTotalCount());
		
		return pageModel;
	}

	@Deprecated
	@Override
	public PageModel getPage(String listId, String countId, Object params,
			PageModel pageModel) {

		// 得到数据记录总数
		Integer totalItem = (Integer) getSqlSession().selectOne(
				getMapperNamespace() + "." + countId, params);
		pageModel.setTotalRecord(totalItem);

		if (pageModel.getTotalPage() > 0) {
			pageModel.setList(this.getSqlSession().selectList(
					getMapperNamespace() + "." + listId,
					params,
					new PageBounds(pageModel.getCurrentPage(), pageModel
							.getPageSize())));
		}
		return pageModel;
	}
	

	@Override
	public boolean update(String id, T entity) throws DataAccessException {
		boolean flag = false;
		try {
			flag = this.getSqlSession().update(getMapperNamespace() + "." + id,
					entity) > 0 ? true : false;
			if(!flag){
				throw new RuntimeException("Concurrent error");
			}
		} catch (DataAccessException e) {
			flag = false;
			throw e;
		}
		return true;
	}

	@Override
	public List<T> select(String id, Object params) throws DataAccessException {
		List<T> result = new ArrayList<T>();
		try {
			result = this.getSqlSession().selectList(
					getMapperNamespace() + "." + id, params);
		} catch (DataAccessException e) {
			throw e;
		}
		return result;
	}

	@Override
	public boolean updateByPrimaryKeySelective(T entity)
			throws DataAccessException {

		return update("updateByPrimaryKeySelective", entity);
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
		setMapperNamespace(entityClass.getName().substring(
				entityClass.getName().lastIndexOf(".") + 1));
	}

	public String getMapperNamespace() {
		return mapperNamespace;
	}

	public void setMapperNamespace(String className) {
		this.mapperNamespace = "com.rbao.east.mapper." + className + "Mapper";
	}

	@Override
	public boolean saveOrUpdate(T entity) throws DataAccessException {
		return saveOrUpdate("insertSelective", "updateByPrimaryKeySelective",
				entity);
	}

	@Override
	public boolean saveOrUpdate(String insertId, String updateId, T entity)
			throws DataAccessException {
		BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
		Integer id = (Integer) beanWrapper.getPropertyValue("id");
		if (id == null || id <= 0) { // 添加
			return insert(insertId, entity);
		} else {
			return update(updateId, entity);
		}
	}

	@Override
	public boolean insert(String id, T entity) throws DataAccessException {
		return insertObj(id, entity);
	}

	@Override
	@Deprecated
	public PageModel getPage(String listId, String countId,
			Map paramsMap) {

		return getPage(listId,paramsMap);

	}
	@Override
	public PageModel getPage(String listId,Map paramsMap) {

		String curPage = StringUtils.defaultIfEmpty( // 当前页数
				(String)paramsMap.get(Constants.PAGED_CURPAGE), "1");

		String numPerPage = StringUtils.defaultIfEmpty( // 每页条数
				(String)paramsMap.get(Constants.PAGED_NUM_PERPAGE), "10");

		PageModel pageModel = new PageModel(Integer.parseInt(curPage));
		pageModel.setPageSize(Integer.parseInt(numPerPage));

		return getPage(listId, paramsMap, pageModel);

	}

	@Override
	public boolean insertObj(String id, Object params)
			throws DataAccessException {
		boolean flag = false;
		try {
			flag = this.getSqlSession().insert(getMapperNamespace() + "." + id,
					params) > 0 ? true : false;
		} catch (DataAccessException e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	@Override
	public T selectEntity(String id, Object params) throws DataAccessException {
		List<T> list = select(id, params);
		if (list != null && list.size() > 0)
			return (T) list.get(0);
		return null;
	}

	@Override
	public List selects(String id, Object params) throws DataAccessException {
		List result = new ArrayList();
		try {
			result = this.getSqlSession().selectList(
					getMapperNamespace() + "." + id, params);
		} catch (DataAccessException e) {
			throw e;
		}
		return result;
	}

	@Override
	public boolean deletes(String id, Object object) throws DataAccessException {
		boolean flag = false;
		try {
			flag = this.getSqlSession().delete(getMapperNamespace() + "." + id,
					object) > 0 ? true : false;
		} catch (DataAccessException e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	@Override
	public Object getObject(String id, Object object) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(getMapperNamespace() + "." + id, object);
	}

}
