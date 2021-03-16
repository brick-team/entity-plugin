package com.github.hufier.entity.plugin.example.repo;

import com.github.huifer.entity.plugin.example.App;
import com.github.huifer.entity.plugin.example.entity.TCompanyEntity;
import com.github.huifer.entity.plugin.example.entity.TDeptEntity;
import com.github.huifer.entity.plugin.example.entity.TUserEntity;
import com.github.huifer.entity.plugin.example.repo.CompanyRepo;
import com.github.huifer.entity.plugin.example.repo.DeptRepo;
import com.github.huifer.entity.plugin.example.repo.UserRepo;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Optional;

@SpringBootTest(classes = {App.class})
//@ContextConfiguration(classes = App.class, loader = AnnotationConfigContextLoader.class)
public class FKServiceTest {
    
    @Autowired
    Gson gson;
    
    @Autowired
    private CompanyRepo companyRepo;
    
    @Autowired
    private DeptRepo deptRepo;
    
    @Autowired
    private UserRepo userRepo;
    
    @Test
    void saveUser() {
        TCompanyEntity tCompanyEntity = new TCompanyEntity();
        tCompanyEntity.setName("测试企业1");
        TCompanyEntity companySave = companyRepo.save(tCompanyEntity);
        
        TDeptEntity tDeptEntity = new TDeptEntity();
        tDeptEntity.setName("测试部门1");
        TDeptEntity deptSave = deptRepo.save(tDeptEntity);
        
        TUserEntity tUserEntity = new TUserEntity();
        tUserEntity.setName("测试用户1");
        tUserEntity.settCompanyByCompanyId(companySave);
        tUserEntity.settDeptByDeptId(deptSave);
        TUserEntity save = userRepo.save(tUserEntity);
    }
    @Test
    void findByUserId(){
        Optional<TUserEntity> byId = userRepo.findById(1L);
        if (byId.isPresent()) {
            TUserEntity db = byId.get();
            System.out.println(gson.toJson(db));
        }
    }
    
}
