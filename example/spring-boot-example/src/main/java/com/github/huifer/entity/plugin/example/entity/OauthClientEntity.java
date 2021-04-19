package com.github.huifer.entity.plugin.example.entity;

import com.github.huifer.entity.plugin.core.annotation.EntityPlugin;
import com.github.huifer.entity.plugin.core.api.ID;
import com.github.huifer.entity.plugin.example.impl.convert.OauthUserEntityConvertInSpring;
import com.github.huifer.entity.plugin.example.impl.validate.OauthUserEntityValidate;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@EntityPlugin(name = "abc",
    convertClass = OauthUserEntityConvertInSpring.class,
    validateApiClass = OauthUserEntityValidate.class,
    cacheKey = "oauth_client"
)
@Entity
@Table(name = "oauth_client",  catalog = "")
public class OauthClientEntity implements ID {

  private Long id;
  private String clientId;
  private String clientSecurity;
  private String redirectUri;
  private Long version;

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
  @Column(name = "client_Id", nullable = true, length = 50)
  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  @Basic
  @Column(name = "client_security", nullable = true, length = 50)
  public String getClientSecurity() {
    return clientSecurity;
  }

  public void setClientSecurity(String clientSecurity) {
    this.clientSecurity = clientSecurity;
  }

  @Basic
  @Column(name = "redirect_uri", nullable = true, length = 255)
  public String getRedirectUri() {
    return redirectUri;
  }

  public void setRedirectUri(String redirectUri) {
    this.redirectUri = redirectUri;
  }

  @Basic
  @Column(name = "version", nullable = true)
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OauthClientEntity that = (OauthClientEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(clientId, that.clientId)
        && Objects.equals(clientSecurity, that.clientSecurity) && Objects
        .equals(redirectUri, that.redirectUri) && Objects.equals(version, that.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, clientId, clientSecurity, redirectUri, version);
  }
}
