package com.zzh;

import com.zzh.domain.ResponseResult;
import com.zzh.domain.User;
import com.zzh.service.getPremService;
import com.zzh.service.userService;
import com.zzh.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private getPremService getPremService;
	@Autowired
	private RedisCache redisCache;
	@Autowired
	private userService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	void contextLoads() {
		String code = bCryptPasswordEncoder.encode("1234");
		System.out.println(code);
	}

	@Test
	void passwordBuilder()
	{

	}

	@Test
	void insertTest()
	{
		User user = new User();
		user.setUserName("futanari");
		user.setNickName("我是扶她巨娘");
		user.setUserType("1");
		user.setPassword(bCryptPasswordEncoder.encode("123456"));
		userService.insert(user);
	}

	@Test
	void redisTest()
	{
		redisTemplate.opsForValue().set("name","虎哥");
		String name = (String) redisTemplate.opsForValue().get("name");
		System.out.println(name);
	}

	@Test
	void redisTest1()
	{
		ResponseResult<String> stringResponseResult = new ResponseResult<>(4, "哈哈哈", "jjj");
		redisTemplate.opsForValue().set("result",stringResponseResult);
	}

	@Test
	void permTest()
	{
		System.out.println(getPremService.getPrems(3L));
	}
	@Test
	void redisHashTest()
	{
		Map<String,String> map = new HashMap<>();
		map.put("name","不知火");
		map.put("属性","gentle");
		redisCache.setCacheMap("user:1",map);
	}

}
