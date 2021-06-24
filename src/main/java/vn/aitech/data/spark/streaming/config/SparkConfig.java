/**
 * 
 */
package vn.aitech.data.spark.streaming.config;

import java.io.Serializable;
import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;

import vn.aitech.data.sparkstreaming.utils.StreamingUtils;



public class SparkConfig implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private final String APP_NAME = "spark.app.name";
	private final String MASTER = "spark.master.name";
	private final String CASSANDRA_HOST = "spark.cassandra.connection.host";	
	private static final Properties env=StreamingUtils.loadConfig("spark-streaming-kafka.properties");
	
	
	

	private String getAppName() {
		return env.getProperty(APP_NAME);
	}

	private String getMaster() {
		return env.getProperty(MASTER);
	}

	private String getCassandraHost() {
		return env.getProperty(CASSANDRA_HOST);
	}
	public Duration getDuration(){
		return new Duration(Long.parseLong(env.get("streaming.duration").toString()));
	}
	
	public SparkConf getSparkConfig() {
		SparkConf conf = new SparkConf();
		conf.setAppName(getAppName());
		conf.setMaster(getMaster());
		conf.set("spark.cassandra.connection.host", getCassandraHost());
		return conf;
	}	

}
