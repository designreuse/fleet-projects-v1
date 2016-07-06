package test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;


@ContextConfiguration("classpath:applicationContext-test.xml")
public class BaseTest extends AbstractTransactionalTestNGSpringContextTests { //回滚事务
//public class BaseTest extends AbstractTestNGSpringContextTests { //不回滚

}
