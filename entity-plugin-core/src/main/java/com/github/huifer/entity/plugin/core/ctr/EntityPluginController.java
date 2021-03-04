package com.github.huifer.entity.plugin.core.ctr;

import com.github.huifer.entity.plugin.core.api.EntityConvert;
import com.github.huifer.entity.plugin.core.api.EntityPluginCoreService;
import com.github.huifer.entity.plugin.core.model.EntityPluginCache;
import com.github.huifer.entity.plugin.core.model.EntityPluginCacheBean;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EntityPluginController {

  Gson gson = new Gson();
  @Autowired
  private EntityPluginCoreService coreService;
  @Autowired
  private EntityPluginCacheBean entityPluginCacheBean;


  @GetMapping("/{entityPluginName}/{id}")
  public ResponseEntity<Object> findById(
      @PathVariable("entityPluginName") String entityPluginName,
      @PathVariable("id") String id
  ) throws Exception {
    return ResponseEntity.ok(coreService.findById(entityPluginName, id));
  }


  @PostMapping("/{entityPluginName}")
  public ResponseEntity<Object> save(
      @PathVariable("entityPluginName") String entityPluginName,
      @RequestBody Object insertParam
  ) throws Exception {
    EntityPluginCache entityPluginCache = entityPluginCacheBean.getCacheMap().get(entityPluginName);
    Class<? extends EntityConvert> convertClass = entityPluginCache.getConvertClass();
    if (convertClass != EntityConvert.class) {
      Object save = coreService.save(entityPluginName, insertParam);
      return ResponseEntity.ok(save);
    } else {
      Object o = gson.fromJson(gson.toJson(insertParam), entityPluginCache.getSelf());
      Object save = coreService.save(entityPluginName, o);
      return ResponseEntity.ok(save);

    }
  }

  @PutMapping("/{entityPluginName}")
  public ResponseEntity<Object> update(
      @PathVariable("entityPluginName") String entityPluginName,
      @RequestBody Object updateParam
  ) throws Exception {
    EntityPluginCache entityPluginCache = entityPluginCacheBean.getCacheMap().get(entityPluginName);
    Class<? extends EntityConvert> convertClass = entityPluginCache.getConvertClass();
    if (convertClass != EntityConvert.class) {
      Object save = coreService.update(entityPluginName, updateParam);
      return ResponseEntity.ok(save);
    } else {
      Object o = gson.fromJson(gson.toJson(updateParam), entityPluginCache.getSelf());
      Object save = coreService.update(entityPluginName, o);
      return ResponseEntity.ok(save);

    }
  }

  @DeleteMapping("/{entityPluginName}/{id}")
  public ResponseEntity<Object> deleteById(
      @PathVariable("entityPluginName") String entityPluginName,
      @PathVariable("id") String id
  ) {
    Boolean aBoolean = this.coreService.deleteById(entityPluginName, id);
    return ResponseEntity.ok(aBoolean);
  }
}
