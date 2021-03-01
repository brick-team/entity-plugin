package com.github.huifer.entity.plugin.core.api;

public interface EntityConvert<InsType, UpType, ResType, EntityType> {

  /**
   * convert data from insert param db entity
   *
   * @param insType insert param
   * @return db entity
   */
  EntityType fromInsType(InsType insType);

  /**
   * convert data from update param to db entity
   *
   * @param upType update param
   * @return db entity
   */
  EntityType fromUpType(UpType upType);

  /**
   * convert data from db entity to response entity
   *
   * @param entityType db entity
   * @return response entity
   */
  ResType fromEntity(EntityType entityType);

}
