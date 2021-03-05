package com.github.huifer.entity.plugin.example.impl;

import com.github.huifer.entity.plugin.core.api.EntityConvert;
import com.github.huifer.entity.plugin.example.entity.OauthClientEntity;
import com.github.huifer.entity.plugin.example.req.UserAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class OauthUserEntityConvertInSpring implements
    EntityConvert<UserAdd, UserAdd, UserAdd, OauthClientEntity> {

  @Autowired
  private ApplicationContext context;

  @Override
  public OauthClientEntity fromInsType(UserAdd userAdd) {
    ServerProperties bean = context.getBean(ServerProperties.class);

    OauthClientEntity oauthClientEntity = new OauthClientEntity();
    oauthClientEntity.setClientId(userAdd.getClientId());
    oauthClientEntity.setRedirectUri(userAdd.getClientId());
    oauthClientEntity.setClientSecurity(String.valueOf(bean.getPort()));
    return oauthClientEntity;
  }

  @Override
  public OauthClientEntity fromUpType(UserAdd userAdd) {
    OauthClientEntity oauthClientEntity = new OauthClientEntity();
    oauthClientEntity.setId(Long.valueOf(userAdd.getId()));
    oauthClientEntity.setClientId(userAdd.getClientId());
    oauthClientEntity.setRedirectUri(userAdd.getClientId());
    return oauthClientEntity;
  }

  @Override
  public UserAdd fromEntity(OauthClientEntity oauthClientEntity) {
    UserAdd userAdd = new UserAdd();
    userAdd.setClientId(oauthClientEntity.getClientId());
    return userAdd;
  }
}
