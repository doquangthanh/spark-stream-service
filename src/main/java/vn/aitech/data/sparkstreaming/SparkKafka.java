package vn.aitech.data.sparkstreaming;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.rdd.RDD;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import com.google.gson.Gson;

import kafka.utils.Time;
import scala.Tuple2;
import vn.aitech.data.entities.Pageview;

/**
 * @author minhnv
 *
 */
public class SparkKafka {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		if (args.length < 4) {
			System.err.println("Usage: JavaKafkaWordCount <zkQuorum> <group> <topics> <numThreads>");
			// System.exit(1);
		}

		// StreamingExamples.setStreamingLogLevels();
		SparkConf sparkConf = new SparkConf().setAppName("JavaKafkaWordCount");
		sparkConf.setMaster("local[2]");
		// Create the context with 2 seconds batch size
		JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new Duration(5000));

		int numThreads = 2;// Integer.parseInt(args[3]);
		Map<String, Integer> topicMap = new HashMap<>();
		topicMap.put("streaming", numThreads);
		/*
		 * String[] topics = args[2].split(","); for (String topic: topics) {
		 * topicMap.put(topic, numThreads); }
		 */

	/*	JavaPairReceiverInputDStream<String, String> messages = KafkaUtils.createStream(jssc, "localhost:2181",
				"g_reporting", topicMap);*/

		JavaDStream<String>lines=SparkKafka.kafkaStream(jssc, "localhost:2181", "g_reporting", "streaming", numThreads);		
		lines.print();
		jssc.start();
		jssc.awaitTermination();
	}

	public static JavaDStream<String> kafkaStream(JavaStreamingContext ctx, String zookeeper,
			String ConsumerGroup, String kafkaTopics, Integer numThreads) {
		Map<String, Integer> topicMap = new HashMap<String, Integer>();
		topicMap.put(kafkaTopics, numThreads);
		JavaPairReceiverInputDStream<String, String> inputDStream = null;
		inputDStream = KafkaUtils.createStream(ctx, zookeeper, ConsumerGroup, topicMap);
		return inputDStream.map(new Function<Tuple2<String, String>,String>() {
			private static final long serialVersionUID = 1L;

			public String call(Tuple2<String, String> tuple2) {				
				return tuple2._2();
			}
		});
	}

}
