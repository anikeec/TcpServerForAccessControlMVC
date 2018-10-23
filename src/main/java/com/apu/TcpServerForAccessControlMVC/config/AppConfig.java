package com.apu.TcpServerForAccessControlMVC.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import com.apu.TcpServerForAccessControlDB.entity.AccessMessage;
import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlDB.entity.Device;
import com.apu.TcpServerForAccessControlDB.entity.EventMessage;
import com.apu.TcpServerForAccessControlMVC.redis.MessagePublisher;
import com.apu.TcpServerForAccessControlMVC.redis.RedisMessagePublisher;
import com.apu.TcpServerForAccessControlMVC.redis.RedisMessageSubscriber;
import com.apu.TcpServerForAccessControlMVC.service.AccessMessageService;
import com.apu.TcpServerForAccessControlMVC.service.CardService;
import com.apu.TcpServerForAccessControlMVC.service.DeviceService;
import com.apu.TcpServerForAccessControlMVC.service.EventMessageService;
import com.apu.TcpServerForAccessControlMVC.service.utils.ActivatableServiceUtils;
import com.apu.TcpServerForAccessControlMVC.service.utils.PageableServiceUtils;
import com.apu.TcpServerForAccessControlMVC.service.utils.ServiceUtils; 

@Configuration
@ComponentScan({"com.apu.TcpServerForAccessControlDB","com.apu.TcpServerForAccessControlMVC"})
@EnableRedisRepositories(basePackages= {"com.apu.TcpServerForAccessControlDB.repository"})
public class AppConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisMessageSubscriber());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    MessagePublisher redisPublisher() {
        return new RedisMessagePublisher(redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue");
    }
    
    @Bean(name = "accessMessageServiceUtils")
    PageableServiceUtils<AccessMessage> accessMessageServiceUtils(AccessMessageService amService) {
        return new PageableServiceUtils<AccessMessage>(amService);
    }
    
    @Bean(name = "eventMessageServiceUtils")
    PageableServiceUtils<EventMessage> eventMessageServiceUtils(EventMessageService emService) {
        return new PageableServiceUtils<EventMessage>(emService);
    }
    
    @Bean(name = "cardServiceUtils")
    ActivatableServiceUtils<Card> cardServiceUtils(CardService service) {
        return new ActivatableServiceUtils<Card>(service);
    }
    
    @Bean(name = "deviceServiceUtils")
    ActivatableServiceUtils<Device> deviceServiceUtils(DeviceService service) {
        return new ActivatableServiceUtils<Device>(service);
    }
    
}
