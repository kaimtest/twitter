package com.example.twitter;

import com.example.twitter.controllers.HaupController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("eee")//Указываем под каким пользователем мы должны быть авторизированы
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-bevor.sql", "/messages-list-bevor.sql"},  executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    HaupController controller;

    @Test
    public void mainPageTest() throws Exception{// Проверяем корректную аунтификацию(веб ссесия должна быть запущена). Мы должны зайти под пользователем узазаным в @WithUserDetails
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/nav/div/div").string("eee"));// Получаем xpath и ожидаем что получим указанное имя ("eee")
    }

    @Test
    public void messageListTest()throws Exception{// Проверяем корректное отображение сообщений
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*div[@id='collapseExample']/div").nodeCount(4));//ожидаем определенное колличество сообщений на странице
        //для выполнения этой задачи нужно создать тестовую базу данных
        //прописываем  в проперти имя тестовой БД
        //прописываем анатацию с именем проперти @TestPropertySource("application-test.properties")
        //Добавляем анатацию @Sql и указываем скрипты, которые нужно выполнить перед
        //В скрипты мы прописываем пользователей и мессаджи
        //затем получаем xpath сообщений

    }

    @Test
    public void filterMessageTest() throws Exception{//Проверяем что работает фильтр по тегу
        this.mockMvc.perform(get("/main").param("filter", "my-tag"))//передаем параметр filter со значением my-tag
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*div[@id='collapseExample']/div").nodeCount(2))
                .andExpect(xpath("//*div[@id='collapseExample']/div[data-id=1]").exists())
                .andExpect(xpath("//*div[@id='collapseExample']/div[data-id=3]").exists());
    }
    @Test
    public void addMessageTolist() throws Exception{

        MockHttpServletRequestBuilder multipart = multipart("/main")
                .file("file", "123".getBytes())
                .param("text", "fifth")
                .param("tag", "new one");
        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated());//дальше не дописано, смотреть https://www.youtube.com/watch?v=Lnc3o8cCwZY с 27 минуты.
    }
}
