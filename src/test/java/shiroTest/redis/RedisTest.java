package shiroTest.redis;

import com.jk51.shiroTest.Bootstrap;
import com.jk51.shiroTest.redisDemo.Address;
import com.jk51.shiroTest.redisDemo.HashMapping;
import com.jk51.shiroTest.redisDemo.Person;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018/7/6
 * 修改记录:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Bootstrap.class)
@ActiveProfiles("dev")
public class RedisTest {

    @Autowired
    private JedisConnectionFactory factory;

    @Autowired
    private RedisTemplate<String,String> template;

    @Autowired
    private RedisTemplate<String,Session> sessionTemplate;


    @Resource(name="redisTemplate")
    private ListOperations<String,String> listOps;
    @Autowired
    private HashMapping hashMapping;

    @Test
    public void TransactionsTest(){
        List<Object> txResuls = template.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {

               operations.multi();

               operations.opsForSet().add("key", "value1");
               operations.opsForSet().add("key1", "value11");
               return operations.exec();
            }
        });

        System.out.println("number of items added to set:"+txResuls);
    }

    @Test
    public void testHashMap(){

        Address address = new Address();
        address.setAddress("上海");

        Person person = new Person();
        person.setFirstNamne("gao");
        person.setLastName("Jay");
        person.setAddress(address);

        hashMapping.writerHash("jay",person);

        Person person1 = hashMapping.loadHash("jay");
    }

    @Test
    public void testFactory(){

        RedisConnection connection =  factory.getConnection();
        connection.set("aaa".getBytes(),"bbbb".getBytes());
        byte[] value = connection.get("aaa".getBytes());
        String result = new String(value);

        template.opsForValue().set("ccc","ddd");

        listOps.leftPush("list1","a1");

        SimpleSession session = new SimpleSession();
        session.setId("aa");
        sessionTemplate.opsForValue().set("123",session);

        Session session1 = sessionTemplate.opsForValue().get("123");

    }
}
