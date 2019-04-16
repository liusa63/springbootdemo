package com.example.demo.mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * 各个方法的详细注释在test目录下
 * @author liusa
 * @date 2019/04/16
 */
public class MongoTemplateOptions {
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	/**
	 * 查询
	 * @author liusa
	 * @date 2019/04/16 18:35
	 */
	public void query() {
		
		  Query queryOne = Query.query(Criteria.where("id").is("5cb589b40a158532dc8e2998")); 
		  //mongoTemplate.findById(new ObjectId("5cb589b40a158532dc8e2998"),MongoTesUser.class); 
		  MongoTesUser user = mongoTemplate.findOne(queryOne,MongoTesUser.class); 
		  System.out.println("----- findOne -----");
		  System.out.println(user.toString());
		  
		  Query queryMany = Query.query(Criteria.where("name").is("刘飒many"));
		  List<MongoTesUser> users = mongoTemplate.find(queryMany, MongoTesUser.class);
		  System.out.println("----- findMany -----"); System.out.println(users.size());
		  
		  
		  List<MongoTesUser> userList = mongoTemplate.findAll(MongoTesUser.class);
		  System.out.println("----- findAll -----");
		  System.out.println(userList.size());
		  
		  Query queryLt = Query.query(Criteria.where("age").lt(27)); List<MongoTesUser>
		  usersLt = mongoTemplate.find(queryLt, MongoTesUser.class);
		  System.out.println("----- queryLt -----");
		  System.out.println(usersLt.size());
		 

		  Query queryRegex = Query.query(Criteria.where("name").regex("liusa"));
		  List<MongoTesUser> usersRegex = mongoTemplate.find(queryRegex, MongoTesUser.class);
		  System.out.println("----- queryRegex -----");
		  System.out.println(usersRegex.size());
		  
		  
		  Query queryOr = Query.query(Criteria.where("").orOperator(
				    Criteria.where("name").regex("刘飒"),
				    Criteria.where("age").lt(17)));
		  List<MongoTesUser> usersOr = mongoTemplate.find(queryOr, MongoTesUser.class);
		  System.out.println("----- queryOr -----");
		  System.out.println(usersOr.size());
		
	}
	
	
	/**
	 * 添加
	 * @author liusa
	 * @date 2019/04/16 18:40
	 */
	public void insert() {
		
		//添加数据
	    for (int i = 0; i < 10; i++) {
	    	MongoTesUser user = new MongoTesUser();
	    	user.setName("liusa-"+i);
	    	user.setAge(18+i);
	    	user.setTags(Arrays.asList("java", "mongodb", "spring"));
	    	user.setCreateTime(new Date());
		    mongoTemplate.save(user);
	    }
	    
	    
	    //批量添加
	    List<MongoTesUser> userList = new ArrayList<>(10);
	    for (int i = 10; i < 20; i++) {
	    	MongoTesUser user = new MongoTesUser();
	    	user.setName("liusa-"+i);
	    	user.setAge(18+i);
	    	user.setTags(Arrays.asList("java", "mongodb", "spring"));
	    	userList.add(user);
	    }
	    mongoTemplate.insert(userList, MongoTesUser.class);
	    
	}
	
	/**
	 * 单个删除
	 * @author liusa
	 * @date 2019/04/16 18:40
	 * @param name
	 */
	public void deleteOne(String name) {
		Query query = Query.query(Criteria.where("name").is(name));
		MongoTesUser user = mongoTemplate.findAndRemove(query, MongoTesUser.class);
		System.out.println(user.getName());
	}
	
	
	/**
	 * 多个删除
	 * @author liusa
	 * @date 2019/04/16 18:40
	 * @param age
	 */
	public void deleteMany(int age) {
		Query query = Query.query(Criteria.where("age").lt(age));
		List<MongoTesUser>  list = mongoTemplate.findAllAndRemove(query, MongoTesUser.class);
		System.out.println(list.size());
	}
	
	
	/**
	 * 单个更新
	 * @author liusa
	 * @date 2019/04/16 18:40
	 * @param name
	 */
	public void updateOne(String name) {
		
		Query query = Query.query(Criteria.where("name").is(name));
//		Query query = Query.query(Criteria.where("id").is("5cb589b40a158532dc8e2995"));
		Update update = Update.update("name", "刘飒").set("age", 33).set("tags", Arrays.asList("mongodb"));
		mongoTemplate.updateFirst(query, update, MongoTesUser.class);
		
	}
	
	/**
	 * 多个更新
	 * @author liusa
	 * @date 2019/04/16 18:40
	 */
	public void updateMany() {
		Query query = Query.query(Criteria.where("name").is("刘飒many"));
		Update update = Update.update("name", "刘飒many").set("age", 22).set("tags", Arrays.asList("java","python","mongodb"));
		mongoTemplate.updateMulti(query, update, MongoTesUser.class);
	}
	
	
	/**
	 * 累加
	 * @author liusa
	 * @date 2019/04/16 18:40
	 */
	public void inc() {
		Query query = Query.query(Criteria.where("name").is("刘飒many"));
		Update update = new Update();
		update.inc("age", 4);
		mongoTemplate.updateMulti(query, update, MongoTesUser.class);
		
	}
	
	
	

}
