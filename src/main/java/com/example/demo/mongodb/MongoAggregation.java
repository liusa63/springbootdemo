package com.example.demo.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson.JSONObject;

@Repository
public class MongoAggregation {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 查询姓 相同的朋友
	 * @author liusa
	 * @date 2019/06/06 16:49
	 * @param id
	 * @param lastName
	 * @return
	 */
	public List<Friend> getAllFriendsWithSameLastName(String id, String lastName) {
		Pattern pattern = Pattern.compile(lastName + ".*$", Pattern.CASE_INSENSITIVE);
		String[] fields = { "friends.name", "friends.age", "friends.sex" };
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.unwind("friends"),
				Aggregation.match(Criteria.where("id").is(id).and("friends.name").regex(pattern)),
				Aggregation.project(fields));

		AggregationResults<Friend> groupResults = mongoTemplate.aggregate(aggregation, MongoTestUser.class,
				Friend.class);
		List<Friend> list = groupResults.getMappedResults();

		return list;
	}

	
	/**
	 * 查询年林相同的，按性别升序（女-男），分页
	 * @author liusa
	 * @date 2019/06/06 16:50
	 * @param id
	 * @param age
	 * @param limit
	 * @param offset
	 * @return
	 */
	public List<Friend> getFriendsWithSameAge(String id, Integer age, Integer limit, Integer offset){
		String[] fields = { "friends.name", "friends.age", "friends.sex" };
		Criteria criteria = new Criteria();
		criteria.and("id").is(id);
		criteria.and("friends.age").is(age);

		List<AggregationOperation> operations = new ArrayList<>();
		operations.add(Aggregation.unwind("friends"));
		operations.add(Aggregation.match(criteria));
		operations.add(Aggregation.project(fields));
		// 排序
		SortOperation sort = Aggregation.sort(Sort.Direction.ASC, "sex");
		operations.add(sort);

		// 分页
		operations.add(Aggregation.skip((long) offset));
		operations.add(Aggregation.limit(limit));

		Aggregation aggregation = Aggregation.newAggregation(operations);
		AggregationResults<Friend> results = mongoTemplate.aggregate(aggregation, MongoTestUser.class, Friend.class);
		List<Friend> list = results.getMappedResults();

		return list.size() > 0 ? list : null;
	}
	
	/**
	 * 查询姓相同的 男/女 的年龄和
	 * @author liusa
	 * @date 2019/06/06 16:51
	 * @param id
	 * @param lastName
	 * @return
	 */
    public Long getSumFemaleAgeWithSameLastName(String id, String lastName, Integer sex) {
        Long sumAge = 0L;
        Pattern pattern = Pattern.compile(lastName + ".*$", Pattern.CASE_INSENSITIVE);
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.unwind("friends"),
                Aggregation.match(
                        Criteria.where("id").is(id).and("friends.name").regex(pattern).and("friends.sex").is(sex)),
                Aggregation.group("friends.sex").sum("friends.age").as("totalAge"));

        AggregationResults<JSONObject> groupResults = mongoTemplate.aggregate(aggregation, MongoTestUser.class,
                JSONObject.class);
        List<JSONObject> list = groupResults.getMappedResults();
        if (list != null && list.size() > 0) {
        	sumAge = list.get(0).getLong("totalAge");
        }
        return sumAge;
    }

}
