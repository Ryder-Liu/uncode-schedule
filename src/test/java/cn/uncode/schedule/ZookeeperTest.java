package cn.uncode.schedule;

import java.io.StringWriter;
import java.util.*;

import com.google.gson.Gson;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.uncode.schedule.core.TaskDefine;
import cn.uncode.schedule.zk.ZKTools;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author juny.ye
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:/applicationContext1.xml"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZookeeperTest {
	@Test
	public void testCloseStatus() throws Exception {
		ZooKeeper zk = new ZooKeeper("localhost:2181", 3000, null);
		int i = 1;
		while (true) {
			try {
				StringWriter writer = new StringWriter();
				ZKTools.printTree(zk, "/uncode/schedule", writer, "");
				System.out.println(i++ + "----" + writer.toString());
				Thread.sleep(2000);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Test
	public void testPrint() throws Exception {
		ZooKeeper zk = new ZooKeeper("localhost:2181", 3000, null);
		StringWriter writer = new StringWriter();
		ZKTools.printTree(zk, "/", writer, "\n");
		System.out.println(writer.toString());
	}

	@Test
	public void deletePath() throws Exception {
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 3000, null);
		zk.addAuthInfo("digest", "ScheduleAdmin:password".getBytes());
		ZKTools.deleteTree(zk, "/uncode/schedule/task/taskObj#print");
		//ZKTools.deleteTree(zk, "/uncode/schedule");
		StringWriter writer = new StringWriter();
		ZKTools.printTree(zk, "/", writer, "\n");
		System.out.println(writer.getBuffer().toString());
	}

	@Test
	public void testCreateTask() throws Exception {
		ZooKeeper zk = new ZooKeeper("localhost:2181", 3000, null);
		List<ACL> acls = new ArrayList<ACL>();
		zk.addAuthInfo("digest", "ScheduleAdmin:password".getBytes());
		acls.add(new ACL(ZooDefs.Perms.ALL, new Id("digest",
				DigestAuthenticationProvider.generateDigest("ScheduleAdmin:password"))));
		acls.add(new ACL(ZooDefs.Perms.READ, Ids.ANYONE_ID_UNSAFE));
		zk.create("/uncode/schedule/task/taskObj#print", new byte[0], acls, CreateMode.PERSISTENT);
		zk.getData("/uncode/schedule/task/taskObj#print", false, null);
	}

	@Test
	public void testCreateLocalTask() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
		Thread.sleep(1000);
		//print1
		TaskDefine taskDefine1 = new TaskDefine();
		taskDefine1.setTargetBean("taskObj");
		taskDefine1.setTargetMethod("print1");
		taskDefine1.setCronExpression("0/3 * * * * ?");
		ConsoleManager.addScheduleTask(taskDefine1);
		//print2
		TaskDefine taskDefine2 = new TaskDefine();
		taskDefine2.setTargetBean("taskObj");
		taskDefine2.setStartTime(new Date(System.currentTimeMillis()+1000));
		taskDefine2.setTargetMethod("print2");
		taskDefine2.setTaskDefineName("task2");
		ConsoleManager.addScheduleTask(taskDefine2);
		//print3
		TaskDefine taskDefine3 = new TaskDefine();
		taskDefine3.setTargetBean("taskObj");
		taskDefine3.setTargetMethod("print3");
		taskDefine3.setTaskDefineName("task3");
		taskDefine3.setStartTime(new Date(System.currentTimeMillis()+1000*2));
		taskDefine3.setPeriod(1000);
		ConsoleManager.addScheduleTask(taskDefine3);
		//print4
		TaskDefine taskDefine4 = new TaskDefine();
		taskDefine4.setTargetBean("taskObj");
		taskDefine4.setTargetMethod("print4");
		taskDefine4.setTaskDefineName("task4");
		taskDefine4.setPeriod(1000);
		ConsoleManager.addScheduleTask(taskDefine4);

	}

	@Test
	public void testCreateLocalTaskHiveParam() throws Exception {
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
		Thread.sleep(1000);
		Map<String, String> params = new HashMap();
		params.put("name", "chenghongchao");
		params.put("age", "28");
		//print5
		TaskDefine taskDefine5 = new TaskDefine();
		taskDefine5.setTargetBean("simpleTask");
		taskDefine5.setTargetMethod("print3");
		taskDefine5.setTaskDefineName("task3");
		taskDefine5.setPeriod(10000);
//		taskDefine5.setParams(new Gson().toJson(params));
		ConsoleManager.addScheduleTask(taskDefine5);

	}

	@Test
	public void testInterface () throws Exception {
		TaskDefine taskDefine5 = new TaskDefine();
		taskDefine5.setTargetBean("JDBCToESTask");
		taskDefine5.setTargetMethod("JDBCSinkES");
		taskDefine5.setTaskDefineName("testInterface");
		taskDefine5.setPeriod(10000);
//		taskDefine5.setParams(new Gson().toJson(params));
		ConsoleManager.addScheduleTask(taskDefine5);
	}
}