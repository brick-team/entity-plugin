package com.github.huifer.entity.plugin.example.repo;

import com.github.huifer.entity.plugin.example.entity.TCompanyEntity;
import com.github.huifer.entity.plugin.example.entity.TUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<TUserEntity, Long> {

}
