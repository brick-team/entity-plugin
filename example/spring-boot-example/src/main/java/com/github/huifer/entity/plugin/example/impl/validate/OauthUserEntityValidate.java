package com.github.huifer.entity.plugin.example.impl.validate;

import com.github.huifer.entity.plugin.core.api.ValidateApi;
import com.github.huifer.entity.plugin.example.req.UserAdd;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OauthUserEntityValidate implements ValidateApi<UserAdd, UserAdd> {

  private static final Logger log = LoggerFactory.getLogger(OauthUserEntityValidate.class);
  Gson gson = new Gson();

  @Override
  public void validateInsType(UserAdd userAdd) {
    if (log.isInfoEnabled()) {
      log.info("validateInsType,userAdd = {}", gson.toJson(userAdd));
    }
    throw new RuntimeException("validate insert error ");

  }

  @Override
  public void validateUpType(UserAdd userAdd) {
    if (log.isInfoEnabled()) {
      log.info("validateUpType,userAdd = {}", gson.toJson(userAdd));
    }
    throw new RuntimeException("validate update error ");
  }
}
