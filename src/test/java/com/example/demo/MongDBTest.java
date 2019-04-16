package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.mongodb.MongoTestUser;

/**
 * @author liusa
 * @date 2019/04/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongDBTest {

	/**
	 * --------------- update ------------------------ 
	 * 没有符合条件的就创建该数据
	 * mongoTemplate.upsert(query, update, CLAZZ.class);
	 * 
	 * 累加         update.inc() 
	 * 删除key  update.unset("tags") 
	 * 删除数组中的某个数据      update.pull("tags","java") 
	 * 修改key的名称       update.rename("tags","tagArray") 
	 * -------------------- query----------------------------- 
	 * in(); 
	 * 小于 lt(); 
	 * 大于 gt(); 
	 * 不等于 ne(); 
	 * 模糊查询 regex();
	 * or();
	 * 
	 */

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void testDemo() {

//		insert();

		// 删除liusa-0
//		deleteOne("liusa-0");

		// 删除年龄小于21的
//		deleteMany(21);

//		updateOne("liusa-5");

//		updateMany();

		// 增加
//		inc();

		query();
	}

	public void query() {

		//匹配查询一条
		Query queryOne = Query.query(Criteria.where("id").is("5cb589b40a158532dc8e2998"));
		// mongoTemplate.findById(new
		// ObjectId("5cb589b40a158532dc8e2998"),MongoTestUser.class);
		MongoTestUser user = mongoTemplate.findOne(queryOne, MongoTestUser.class);
		System.out.println("----- findOne -----");
		System.out.println(user.toString());

		//查询所有符合条件的数据
		Query queryMany = Query.query(Criteria.where("name").is("刘飒many"));
		List<MongoTestUser> users = mongoTemplate.find(queryMany, MongoTestUser.class);
		System.out.println("----- findMany -----");
		System.out.println(users.size());

		//查询所有
		List<MongoTestUser> userList = mongoTemplate.findAll(MongoTestUser.class);
		System.out.println("----- findAll -----");
		System.out.println(userList.size());

		//小于的使用
		Query queryLt = Query.query(Criteria.where("age").lt(27));
		List<MongoTestUser> usersLt = mongoTemplate.find(queryLt, MongoTestUser.class);
		System.out.println("----- queryLt -----");
		System.out.println(usersLt.size());

		//模糊查询
		Query queryRegex = Query.query(Criteria.where("name").regex("liusa"));
		List<MongoTestUser> usersRegex = mongoTemplate.find(queryRegex, MongoTestUser.class);
		System.out.println("----- queryRegex -----");
		System.out.println(usersRegex.size());

		//or查询
		Query queryOr = Query
				.query(Criteria.where("").orOperator(Criteria.where("name").regex("刘飒"), Criteria.where("age").lt(17)));
		List<MongoTestUser> usersOr = mongoTemplate.find(queryOr, MongoTestUser.class);
		System.out.println("----- queryOr -----");
		System.out.println(usersOr.size());

	}

	public void insert() {

		// 添加数据
		for (int i = 0; i < 10; i++) {
			MongoTestUser user = new MongoTestUser();
			user.setName("liusa-" + i);
			user.setAge(18 + i);
			user.setTags(Arrays.asList("java", "mongodb", "spring"));
			user.setCreateTime(new Date());
			mongoTemplate.save(user);
		}

		// 批量添加
		List<MongoTestUser> userList = new ArrayList<>(10);
		for (int i = 10; i < 20; i++) {
			MongoTestUser user = new MongoTestUser();
			user.setName("liusa-" + i);
			user.setAge(18 + i);
			user.setTags(Arrays.asList("java", "mongodb", "spring"));
			userList.add(user);
		}
		mongoTemplate.insert(userList, MongoTestUser.class);

		/*
		 * 添加后 mongoDB 中的数据
		 * 
		 * 
		 * { 
		 *    "_id" : ObjectId("5cb589b40a158532dc8e2991"), 
		 *    "name" : "liusa-0", 
		 *    "age" : 18, 
		 *    "tags" : ["java", "mongodb", "spring"], 
		 *    "create_time" :ISODate("2019-04-16T07:52:20.075Z"),
		 *    "_class" :"com.example.demo.mongodb.MongoTestUser" 
		 *  }
		 * 
		 * 
		 * {
		 *   "_id" : ObjectId("5cb589b40a158532dc8e2992"), 
		 *   "name" : "liusa-1", 
		 *   "age" :19, 
		 *   "tags" : ["java", "mongodb", "spring"], 
		 *   "create_time" : ISODate("2019-04-16T07:52:20.326Z"), 
		 *   "_class" :"com.example.demo.mongodb.MongoTestUser" 
		 * }
		 * 
		 */

	}

	public void deleteOne(String name) {
		Query query = Query.query(Criteria.where("name").is(name));
		MongoTestUser user = mongoTemplate.findAndRemove(query, MongoTestUser.class);
		System.out.println(user.getName());
	}

	public void deleteMany(int age) {
		Query query = Query.query(Criteria.where("age").lt(age));
		List<MongoTestUser> list = mongoTemplate.findAllAndRemove(query, MongoTestUser.class);
		System.out.println(list.size());
	}

	public void updateOne(String name) {

		Query query = Query.query(Criteria.where("name").is(name));
//		Query query = Query.query(Criteria.where("id").is("5cb589b40a158532dc8e2995"));
		Update update = Update.update("name", "刘飒").set("age", 33).set("tags", Arrays.asList("mongodb"));
		mongoTemplate.updateFirst(query, update, MongoTestUser.class);

		/*
		 * mongoDB 中的数据 
		 * {
		 *   "_id" : ObjectId("5cb589b40a158532dc8e2995"), 
		 *   "name" : "刘飒",
		 *   "age" : 33, 
		 *   "tags" : ["mongodb"], 
		 *   "create_time" : ISODate("2019-04-16T07:52:20.398Z"), 
		 *   "_class" :"com.example.demo.mongodb.MongoTestUser" 
		 * }
		 */
	}

	public void updateMany() {
		Query query = Query.query(Criteria.where("name").is("刘飒many"));
		Update update = Update.update("name", "刘飒many").set("age", 22).set("tags",
				Arrays.asList("java", "python", "mongodb"));
		mongoTemplate.updateMulti(query, update, MongoTestUser.class);

		/*
		 * mongoDB 中的数据 
		 * { 
		 *    "_id" : ObjectId("5cb589b40a158532dc8e2999"), 
		 *    "name" : "刘飒many",
		 *    "age" : 22,
		 *    "tags" : ["java", "python", "mongodb"], 
		 *    "create_time" :ISODate("2019-04-16T07:52:20.496Z"),
		 *    "_class" : "com.example.demo.mongodb.MongoTestUser" 
		 * }
		 */
	}

	public void inc() {
		Query query = Query.query(Criteria.where("name").is("刘飒many"));
		Update update = new Update();
		update.inc("age", 4);
		mongoTemplate.updateMulti(query, update, MongoTestUser.class);

		/*
		 * mongoDB 中的数据 
		 * { 
		 *   "_id" : ObjectId("5cb589b40a158532dc8e2998"), 
		 *   "name" :"刘飒many", 
		 *   "age" : 26,
		 *   "tags" : ["java", "python", "mongodb"], 
		 *   "create_time" : ISODate("2019-04-16T07:52:20.469Z"), 
		 *   "_class" :"com.example.demo.mongodb.MongoTestUser"
		 *  }
		 *  
		 */

	}

}
