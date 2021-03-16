package com.github.huifer.entity.plugin.example.repo;

import com.github.huifer.entity.plugin.example.entity.TDeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepo extends JpaRepository<TDeptEntity, Long> {

}
