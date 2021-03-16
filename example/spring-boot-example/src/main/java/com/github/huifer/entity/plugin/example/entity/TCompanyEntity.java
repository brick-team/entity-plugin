package com.github.huifer.entity.plugin.example.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "t_company", schema = "shands_uc_3_back", catalog = "")
public class TCompanyEntity {
    
    private Long id;
    
    private String name;
    
    @Id
    @GeneratedValue
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
        TCompanyEntity that = (TCompanyEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
