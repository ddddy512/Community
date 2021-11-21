package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.AlphaDaoHibernateImpl;
import com.nowcoder.community.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)  //在测试代码中启用CommunityApplication作为配置类
public class CommunityApplicationTests implements ApplicationContextAware {
	private ApplicationContext applicationContext;  //记录Spring容器

	@Override
	public  void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
		this.applicationContext = applicationContext;
	}
	@Test
	public void testApplicationContext(){
		System.out.println(applicationContext);

		AlphaDao alphadao = applicationContext.getBean(AlphaDao.class); //从容器中获取AlphaDao接口类型的bean
		// 依赖实现类的接口，若实现类发生改变，此处代码无需修改，降低了bean之间的耦合度
		System.out.println(alphadao.select());

		alphadao = applicationContext.getBean("alphadaohibernate",  AlphaDao.class);
		System.out.println(alphadao.select());
	}

	@Test
	public void testBeanManament(){
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);

		alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
	}

	@Test
	public void testBeanConfig(){
		SimpleDateFormat simpleDateFormat = applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}

	//依赖注入
	@Autowired     //@Autowired 注解除了写在成员变量之前，还能写在set方法和构造器之前
	@Qualifier("alphadaohibernate" ) //希望注入hibernate
	private AlphaDao alphaDao;   //Spring容器将AlphaDao注入给当前bean

	@Autowired
	private SimpleDateFormat simpleDateFormat;

	@Autowired
	private AlphaService alphaService;

	@Test
	public void testDI(){   //DI:dependency injection
		System.out.println(alphaDao);
		System.out.println(simpleDateFormat);
		System.out.println(alphaService);

	}

}
