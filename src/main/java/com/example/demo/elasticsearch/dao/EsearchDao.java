package com.example.demo.elasticsearch.dao;

import com.example.demo.elasticsearch.entity.Metadata;

public interface EsearchDao {
    Metadata findByNeid(String indexName, Long neid, Long accountId);

    void updateMeta(Metadata meta, String indexName, String routing, Long version);

    Boolean deleteByNeid(long neid, String indexName, Long account_id);
}
