import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf

object App extends App {
  val sparkConf = new SparkConf()
      .setAppName("App")
      //    .setMaster("localhost:7077")
      .setMaster("local[*]")
      .set("spark.driver.allowMultipleContexts", "true")
      .set("spark.sql.streaming.checkpointLocation", "./checkpoints")

//      .set("spark.driver.memory", "500m")
//      .set("spark.executor.memory", "500m")
//      .set("spark.executor.memoryOverhead", "100m")
//      .set("spark.executor.cores", "4")
//      .set("spark.cores.max", "4")
//      .set("spark.executor.instances", "1")

    val spark = SparkSession
      .builder()
      .master("local")
      .config(sparkConf)
      .appName("App")
      .getOrCreate()

    val sc = spark.sparkContext



  val hdfs = FileSystem.get(new URI("hdfs://localhost:8020/"), new Configuration())
  val path = new Path("/txt/numbers-as-text")
  val stream = hdfs.open(path)
  def readLines = Stream.cons(stream.readLine, Stream.continually( stream.readLine))

  //This example checks line for null and prints every existing line consequentally
  readLines.takeWhile(_ != null).foreach(line => println(line))

}
