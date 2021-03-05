package com.github.hufier.entity.plugin.example.repo;

import com.github.huifer.entity.plugin.core.api.EntityUseService;
import com.github.huifer.entity.plugin.example.App;
import com.github.huifer.entity.plugin.example.entity.OauthClientEntity;
import com.github.huifer.entity.plugin.example.req.UserAdd;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {App.class})
public class EntityUseServiceTest {

  @Autowired
  private EntityUseService entityUseService;

  @Test
  void testSave() throws Exception {
    UserAdd add = new UserAdd();
    add.setClientId("dada");
    OauthClientEntity save = entityUseService.save(add, OauthClientEntity.class);
    System.out.println();
  }

  @Test
  void testFindById() {
    // 由于存在转换方法需要使用转换后的对象
//    UserAdd byId = entityUseService.findById("23", UserAdd.class);
    OauthClientEntity byId2 = entityUseService.findById("21", OauthClientEntity.class);
    System.out.println();
  }

  @Test
  void testUpdate() {
    OauthClientEntity byId2 = entityUseService.findById("21", OauthClientEntity.class);
    byId2.setClientId("bac");
    entityUseService.update(byId2, byId2.getClass());
  }

}
