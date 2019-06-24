import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringBootJwtTestApplication.class)
@RunWith(SpringRunner.class)
public class SpringBootJwtTestApplication {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Environment env;

    @Test
    public void onPropertiesLoad() {
        String salt = env.getProperty("jwt.salt");
        System.out.println(salt);
        logger.debug("----------------->"+salt);
    }
}
