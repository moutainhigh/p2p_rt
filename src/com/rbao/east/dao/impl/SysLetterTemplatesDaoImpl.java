package com.rbao.east.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rbao.east.dao.SysLetterTemplatesDao;
import com.rbao.east.entity.SysLetterTemplates;

@Repository
public class SysLetterTemplatesDaoImpl extends BaseDaoImpl<SysLetterTemplates> implements SysLetterTemplatesDao{
	private static Logger logger = LoggerFactory.getLogger(SysLetterTemplatesDaoImpl.class);
}
