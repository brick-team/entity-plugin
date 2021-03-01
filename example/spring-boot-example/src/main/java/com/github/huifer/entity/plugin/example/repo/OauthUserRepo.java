package com.github.huifer.entity.plugin.example.repo;

import com.github.huifer.entity.plugin.example.entity.OauthUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthUserRepo extends CrudRepository<OauthUserEntity, Long> {

}
