/**
 * 
 */
package vn.aitech.data.sparkstreaming.utils;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import vn.aitech.data.spark.streaming.config.SparkConfig;

/**
 * @author thanhdq
 *
 */
public class CassandraConnector {
	
	private static final SparkConfig SPARK_CONFIG=new SparkConfig();
	private static JavaSparkContext instanceCass=new JavaSparkContext(SPARK_CONFIG.getSparkConfig());
	private static JavaStreamingContext instanceStreaming=new JavaStreamingContext(SPARK_CONFIG.getSparkConfig(),SPARK_CONFIG.getDuration());
	private CassandraConnector(){		
	}
	public static JavaSparkContext getInstanceCassandra() {
		return instanceCass;
	}
	public static JavaStreamingContext getInstanceStreaming(){
		return instanceStreaming;
	}
}
