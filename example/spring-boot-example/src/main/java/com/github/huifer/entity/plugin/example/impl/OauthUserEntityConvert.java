package com.github.huifer.entity.plugin.example.impl;

import com.github.huifer.entity.plugin.core.api.EntityConvert;
import com.github.huifer.entity.plugin.example.entity.OauthUserEntity;
import com.google.gson.Gson;

public class OauthUserEntityConvert implements
    EntityConvert<Object, Object, Object, OauthUserEntity> {

    Gson gson = new Gson();

  @Override
  public OauthUserEntity fromInsType(Object o) {
    return null;
  }

  @Override
  public OauthUserEntity fromUpType(Object o) {
    return null;
  }

  @Override
  public Object fromEntity(OauthUserEntity oauthUserEntity) {
    return null;
  }
}
