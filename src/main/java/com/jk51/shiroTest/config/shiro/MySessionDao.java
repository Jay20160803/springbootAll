package com.jk51.shiroTest.config.shiro;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018/7/4
 * 修改记录:
 */
public class MySessionDao extends AbstractSessionDAO {


    private ShiroRedisCache<Serializable,Session> shiroRedisCache;

    public MySessionDao(ShiroRedisCache shiroRedisCache) {
        this.shiroRedisCache = shiroRedisCache;
    }

    @Override
    protected Serializable doCreate(Session session) {

        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        storeSession(sessionId, session);
        return sessionId;
    }

    protected Session storeSession(Serializable id, Session session) {
        if (id == null) {
            throw new NullPointerException("id argument cannot be null.");
        }
        return shiroRedisCache.put(id, session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return  shiroRedisCache.get(sessionId);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {

        storeSession(session.getId(),session);
    }

    @Override
    public void delete(Session session) {

        shiroRedisCache.remove(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        throw new UnsupportedOperationException();
    }

}
