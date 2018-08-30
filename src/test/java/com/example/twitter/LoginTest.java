package com.example.twitter;

import com.example.twitter.controllers.HaupController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    HaupController controller;

    @Test
    public void contextLoads() throws Exception {
        this.mockMvc.perform(get("/"))//get запрос на главную страницу
                .andDo(print())//выводит полученный результат в консоль
                .andExpect(status().isOk())//позволяет сравинить результат с ожиданием(вернет http 200)
                .andExpect(content().string(containsString("Hello, Guest")))//вернет контент, в виде подстроки  и сравниваем.
                .andExpect(content().string(containsString("Messages")));
    }

    @Test
    public void accessDinedTest() throws Exception {// тест, что будет вызвана форма авторизации
        this.mockMvc.perform(get("/main"))// переходим на страницу, которая требует авторизации
                .andDo(print())
                .andExpect(status().is3xxRedirection())//проверяем по Http коду
                .andExpect(redirectedUrl("http://localhost/login"));//проверяет что система перекинет нас на этот адрес

    }

    @Test
    public void correctLogin() throws Exception {
        this.mockMvc.perform(formLogin().user("eee").password("ddd"))//проверяем, что будет вызвана форма логин и входим под определенным пользователем
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void badCredetials()throws Exception{//проверяем на правильность введенных регистрационных данных
        this.mockMvc.perform(post("/login").param("user", "Kira"))//можно использовать форму лога(как в предыдущем примере) здесь истользуем ручной ввод
                .andDo(print())
                .andExpect(status().isForbidden());//проверяем на 403 статус.
    }

}
