package com.itheima.solr;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

/**
 * @author Administrator
 *    使用solrj查询
 *
 */
public class SlorSecond {
	/**
	 * 
	 * @throws SolrServerException
	 * 简单查询
	 */
	@Test
	public void searchIndex() throws SolrServerException{
		//创建一个SolrServer对象和服务器连接
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		//创建SolrQuery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.set("q", "*:*");
		//执行查询
		QueryResponse response = solrServer.query(query);
		//显示  查询结果总记录  结果列表  高亮显示
		SolrDocumentList documentList = response.getResults();
		System.out.println("结果记录："+documentList.getNumFound());
		//获取结果 并打印
		for (SolrDocument solrDocument : documentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("product_name"));
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_catalog_name"));
			System.out.println(solrDocument.get("product_picture"));
		}
	}
	/**
	 * 复杂查询
	 * @throws SolrServerException 
	 */
	@Test
	public void searchIndex2() throws SolrServerException{
		
		//创建SolrServer
		
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		//创建SolrQuery
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery("小黄人");
		query.addFilterQuery("product_price:[0 TO 10]");
		query.setSort("product_price",ORDER.asc);
		query.setStart(0);
		query.setRows(8);
		query.set("df", "product_keywords");
		//设置高亮
		query.setHighlight(true);
		query.addHighlightField("product_name");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		QueryResponse response = solrServer.query(query);
		SolrDocumentList documentList = response.getResults();
		//获取结果   结果列表  高亮
		System.out.println("结果记录："+documentList.getNumFound());
		
		//获取高亮结果
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		for (SolrDocument solrDocument : documentList) {
			System.out.println(solrDocument.get("id"));
			//取高亮结果
			String name="";
			List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
			if(list!=null&&list.size()>0){
				name=list.get(0);
			}else{
				name = (String) solrDocument.get("product_name");
			}
			System.out.println(name);
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_catalog_name"));
			System.out.println(solrDocument.get("product_picture"));
			
		}
		
	}
	public static void main(String[] args) {
		System.out.println("hello git !");
	}
	

}
