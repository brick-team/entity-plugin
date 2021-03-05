package com.github.huifer.entity.plugin.example.impl.convert;

import com.github.huifer.entity.plugin.core.api.EntityConvert;
import com.github.huifer.entity.plugin.example.entity.OauthClientEntity;
import com.github.huifer.entity.plugin.example.req.UserAdd;

public class OauthUserEntityConvert implements
    EntityConvert<UserAdd, UserAdd, OauthClientEntity, OauthClientEntity> {


  @Override
  public OauthClientEntity fromInsType(UserAdd userAdd) {
    OauthClientEntity oauthClientEntity = new OauthClientEntity();
    oauthClientEntity.setClientId(userAdd.getClientId());
    oauthClientEntity.setRedirectUri(userAdd.getClientId());
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
  public OauthClientEntity fromEntity(OauthClientEntity oauthClientEntity) {
    return oauthClientEntity;
  }
}
