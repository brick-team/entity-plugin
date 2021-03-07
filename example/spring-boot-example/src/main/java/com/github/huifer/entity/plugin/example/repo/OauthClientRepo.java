package com.github.huifer.entity.plugin.example.repo;

import com.github.huifer.entity.plugin.example.entity.OauthClientEntity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthClientRepo extends B, JpaRepository<OauthClientEntity, Long> {

}
