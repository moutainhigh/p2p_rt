package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.BorrowCollectionDao;
import com.rbao.east.entity.BorrowCollection;


@Repository
public class BorrowCollectionDaoImpl extends BaseDaoImpl<BorrowCollection>
		implements BorrowCollectionDao {
	private static Logger logger = LoggerFactory
			.getLogger(BorrowCollection.class);
}
