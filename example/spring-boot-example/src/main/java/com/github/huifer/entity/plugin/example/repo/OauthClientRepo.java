package com.github.huifer.entity.plugin.example.repo;

import com.github.huifer.entity.plugin.example.entity.OauthClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthClientRepo extends CrudRepository<OauthClientEntity ,Long> {

}
