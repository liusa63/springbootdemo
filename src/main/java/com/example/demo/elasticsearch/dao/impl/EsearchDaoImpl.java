package com.example.demo.elasticsearch.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.elasticsearch.config.ElasticSearchPoolUtil;
import com.example.demo.elasticsearch.entity.Metadata;
import com.example.demo.elasticsearch.dao.EsearchDao;
import org.elasticsearch.ElasticsearchGenerationException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EsearchDaoImpl implements EsearchDao {

    private static Logger logger = LoggerFactory.getLogger(EsearchDaoImpl.class);

    public final static String typeName = "meta";

    @Override
    public Metadata findByNeid(String indexName, Long documentId, Long accountId) {
        SearchResponse response = null;
        RestHighLevelClient client = null;
        try {
            client = ElasticSearchPoolUtil.getClient();

            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.termQuery("neid", documentId));

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(indexName).types(Metadata.typeName).routing(accountId + "");

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(queryBuilder);
            searchSourceBuilder.version(true);

            searchRequest.source(searchSourceBuilder);

            searchSourceBuilder.fetchSource(true);

            response = client.search(searchRequest, RequestOptions.DEFAULT);

            if (response.status() == RestStatus.OK) {
                SearchHits hits = response.getHits();
                logger.info("es查询时间:{},es返回数量:{}",response.getTook(),hits.totalHits);
                for (SearchHit hit : hits) {
                    //每个文档都有一个版本号，更新的时候需要使用版本号防止冲突
                    long version = hit.getVersion();
                    String jsonString = hit.getSourceAsString();
                    Metadata metadata = JSONObject.parseObject(jsonString, Metadata.class);
                    metadata.setVersion(version);
                    metadata.setDocumentId(hit.getId());//删除的时候根据文档id

                    return metadata;
                }
            }
            return null;
        } catch (Exception e) {
            throw new ElasticsearchGenerationException("es error");
        } finally {
            ElasticSearchPoolUtil.returnClient(client);
        }
    }

    @Override
    public void updateMeta(Metadata meta, String indexName, String routing, Long version) {
        if (version == null) {
            return ;
        }
        UpdateRequest request = null;

        request = new UpdateRequest(indexName, Metadata.typeName, meta.getNeid() + "").routing(routing).version(version).doc(JSONObject.toJSONString(meta), XContentType.JSON);

        UpdateResponse updateResponse = null;
        RestHighLevelClient client = null;
        try {
            client = ElasticSearchPoolUtil.getClient();
            updateResponse = client.update(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new ElasticsearchGenerationException("es error");
        } finally {
            ElasticSearchPoolUtil.returnClient(client);
        }
        DocWriteResponse.Result result = updateResponse.getResult();

        if (Result.CREATED.equals(result) || Result.UPDATED.equals(result) || Result.NOOP.equals(result)) {
            logger.info("更新es的数据 neid:{},nsid:{},status:{},hash:{},documentId:{}", meta.getNeid(),meta.getNsid(),meta.getStatus(),meta.getHash(),meta.getDocumentId());
        } else {
            logger.info("save 失败" + result.name());
        }
    }

    @Override
    public Boolean deleteByNeid(long neid, String indexName, Long account_id) {
        RestHighLevelClient client = null;
        DeleteResponse deleteResponse = null;

        try {
            client = ElasticSearchPoolUtil.getClient();

            DeleteRequest request = new DeleteRequest(indexName, Metadata.typeName, neid+"").routing(account_id+"");
            deleteResponse = client.delete(request, RequestOptions.DEFAULT);

            Result result = deleteResponse.getResult();
            if (!Result.DELETED.equals(result)) {
                logger.info("es删除失败 neid:{},accountId:{},indexName:{}",neid,account_id,indexName);
                return false;
            }
            logger.info("es删除成功 neid:{},accountId:{},indexName:{}",neid,account_id,indexName);
            return true;
        } catch (Exception e) {
            logger.error("es删除异常 neid:{},accountId:{},indexName:{},e:{}",neid,account_id,indexName,e);
            return false;
        } finally {
            ElasticSearchPoolUtil.returnClient(client);
        }
    }
}

