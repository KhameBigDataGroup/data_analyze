import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object App extends App {
  val sparkConf = new SparkConf()
    .setAppName("App")
//    .setMaster("localhost:7077")
    .setMaster("local[*]")
    .set("spark.driver.allowMultipleContexts", "true")
    .set("spark.sql.streaming.checkpointLocation", "./checkpoints")

  //      .set("spark.driver.memory","500m")
  //      .set("spark.executor.memory","500m")
  //      .set("spark.executor.memoryOverhead","100m")
  //      .set("spark.executor.cores","1")
  //      .set("spark.cores.max","1")
  //      .set("spark.executor.instances","1")

  val spark = SparkSession
    .builder()
    .master("local")
    .config(sparkConf)
    .appName("App")
    .getOrCreate()

  val sc = spark.sparkContext

      val df = spark
        .readStream
        .format("kafka")
        .option("kafka.bootstrap.servers", "172.17.0.1:9092")
        .option("subscribe", "first_topic")
        .option("startingOffsets", "earliest")
        .load()
  //
      import spark.implicits._
  //
  //    val cast = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
  //      .as[(String, String)]


  //    val query1 = df.collect.foreach(println)
  //      .writeStream
  //      .format("kafka")
  //      .start()
  //    val query2 = df.writeStream
  //      .format("kafka")
  //      .start()
  //
  //    df.printSchema()
  ////    query1.awaitTermination()
  //    query2.awaitTermination()

      val query = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)", "topic")
        .as[(String, String, String)]
        .writeStream
        .outputMode("append")
        .format("console")
        .start()
      query.awaitTermination()
  //
      df.write.parquet("hdfs://localhost:8020/test/kafka")
  //    df.write.save("hdfs://localhost:50070/test")

//  val numbersRdd = sc.parallelize((1 to 10000).toList)
//  numbersRdd.saveAsTextFile("hdfs://localhost:8020/test/numbers-as-text-2")

  sc.stop()
}
