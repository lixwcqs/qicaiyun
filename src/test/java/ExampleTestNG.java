import com.cqs.configuration.BaseConfigurationTestNG;
import com.cqs.qicaiyun.web.HelloController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by cqs on 2017/9/9.
 */
public class ExampleTestNG extends BaseConfigurationTestNG {

    private MockMvc mvc;
    private MockMvc mvc2;

    @Autowired
    private WebApplicationContext wac;
    @BeforeClass
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
        mvc2 = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void home() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello World")));
        MvcResult mvcResult =
                mvc2.perform(MockMvcRequestBuilders.get("/topic/hot/list").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(request().asyncNotStarted()).andReturn();
    }

}