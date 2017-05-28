package com.itheima.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrFirst {
	
	@Test
	public void addDocument() throws SolrServerException, IOException{
		//创建一个SolrService  与服务器连接
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		//向文档中添加域
		document.addField("id", "5");
		document.addField("title", "新文档1");
		//把文档对象写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	/**
	 * 删除文档    
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Test
	public void deleteAll() throws SolrServerException, IOException{
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		//根据id删除
		//solrServer.deleteById("5");
		//根据查询删除
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
		
	}
}
