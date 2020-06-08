package com.example.demo.elasticsearch.config;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class EsClientPoolFactory implements PooledObjectFactory<RestHighLevelClient>{

	private static String serverHost;

    private static int serverPort;

	private static String clusterName;
	private static String esUserName;
	private static String esPassword;
	private static int connectTimeout;
	private static int cocketTimeout;
	private static int connectionRequestTimeout;
	private static String protocol;

    @Autowired
    private Environment env;

    @PostConstruct
    public void readConfig() {
        serverHost = env.getProperty("elas.server.hostName");
        serverPort = Integer.parseInt(env.getProperty("elas.server.port"));
        clusterName = env.getProperty("elas.cluster.name");
        esUserName = env.getProperty("elas.server.esusername");
        esPassword = env.getProperty("elas.server.espassword");
        connectTimeout = Integer.parseInt(env.getProperty("elas.server.connectTimeout"));
        cocketTimeout = Integer.parseInt(env.getProperty("elas.server.cocketTimeout"));
        connectionRequestTimeout = Integer.parseInt(env.getProperty("elas.server.connectionRequestTimeout"));
        protocol = env.getProperty("elas.server.protocol");
        makeObject();
    }

    @Override
    public void activateObject(PooledObject<RestHighLevelClient> arg0) throws Exception {
//        System.out.println("activateObject");

    }

    /**
	 * 销毁对象
	 */
	@Override
	public void destroyObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
		RestHighLevelClient highLevelClient = pooledObject.getObject();
		highLevelClient.close();
	}
	
	/**
	 * 生产对象
	 */
//	@SuppressWarnings({ "resource" })
	@Override
	public PooledObject<RestHighLevelClient> makeObject() {
		//Settings settings = Settings.builder().put("cluster.name","elasticsearch").build();
		RestHighLevelClient client = null;
		try {
			/*client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));*/
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	        credentialsProvider.setCredentials(AuthScope.ANY,
	                new UsernamePasswordCredentials(esUserName, esPassword));
			 client = new RestHighLevelClient(RestClient.builder(new HttpHost (serverHost, serverPort, protocol))
					.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
			            @Override
			            public Builder customizeRequestConfig(Builder requestConfigBuilder) {
			                requestConfigBuilder.setConnectTimeout(connectTimeout);//5000
			                requestConfigBuilder.setSocketTimeout(cocketTimeout);//40000
			                requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeout);//1000
			                return requestConfigBuilder;
			            }
			        }).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
						
						@Override
						public HttpAsyncClientBuilder customizeHttpClient(
								HttpAsyncClientBuilder httpClientBuilder) {
//							httpClientBuilder.disableAuthCaching();
					        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
						}
					}));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DefaultPooledObject<RestHighLevelClient>(client);
	}
 
	@Override
	public void passivateObject(PooledObject<RestHighLevelClient> arg0) throws Exception {
	}
 
	@Override
	public boolean validateObject(PooledObject<RestHighLevelClient> arg0) {
		return true;
	}

}
