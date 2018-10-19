package me.whiteship.natual.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("test")
@Ignore
public class BaseControllerTests {

    @TestConfiguration
    static class TestConfig implements RestDocsMockMvcConfigurationCustomizer {
        @Override
        public void customize(MockMvcRestDocumentationConfigurer configurer) {
            configurer.operationPreprocessors()
                    .withResponseDefaults(prettyPrint())
                    .withRequestDefaults(prettyPrint());
        }
    }

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;

}
