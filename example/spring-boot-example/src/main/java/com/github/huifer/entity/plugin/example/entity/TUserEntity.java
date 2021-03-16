package com.github.huifer.entity.plugin.example.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "t_user", schema = "shands_uc_3_back", catalog = "")
public class TUserEntity {
    
    private Long id;
    
    private String name;
    
    private TDeptEntity tDeptByDeptId;
    
    private TCompanyEntity tCompanyByCompanyId;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TUserEntity that = (TUserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
    
    @ManyToOne
    @JoinColumn(name = "dept_id", referencedColumnName = "id")
    public TDeptEntity gettDeptByDeptId() {
        return tDeptByDeptId;
    }
    
    public void settDeptByDeptId(TDeptEntity tDeptByDeptId) {
        this.tDeptByDeptId = tDeptByDeptId;
    }
    
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    public TCompanyEntity gettCompanyByCompanyId() {
        return tCompanyByCompanyId;
    }
    
    public void settCompanyByCompanyId(TCompanyEntity tCompanyByCompanyId) {
        this.tCompanyByCompanyId = tCompanyByCompanyId;
    }
}
