package com.github.huifer.entity.plugin.example;

import com.github.huifer.entity.plugin.core.beans.EntityPluginCoreBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(EntityPluginCoreBean.class)
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class);
  }

}
