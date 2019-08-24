import org.apache.log4j.{Level, Logger}

import scala.collection.mutable


object App {
  //https://www.hostinger.com/tutorials/set-up-and-install-cassandra-ubuntu/

  def main(args: Array[String]): Unit = {
    println("Salaam")

    val rootLogger = Logger.getRootLogger
    rootLogger.setLevel(Level.ERROR)

    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)


    val (spark, ssc) = SparkStreamFactory.createSparkStream("DE_Spark_Streaming", 10)


    ssc.checkpoint("/tmp/")
    //    import spark.implicits._

    //Text Stream
    //    val textData1 = ssc.sparkContext.textFile("/tmp/samples/1.txt")
    //    val textData2 = ssc.sparkContext.textFile("/tmp/samples/2.txt")
    //    val stream = ssc.queueStream(mutable.Queue(textData1, textData2))

    //Network Stream
    //val stream = ssc.socketTextStream("127.0.0.1", 8008)

    //Kafka Stream
    val stream = KafkaConsumerFactory.createKafkaMessageStream(Array("first_topic"), ssc).map(record => record.value())

    stream.print()

//    val cassandraStream = stream.map(record => record.split(","))
//
//    val columns: SomeColumns = SomeColumns("first_name", "last_name", "email", "age")
//    val table: String = "events"
//    val keySpace: String = "de_class"
//
//    def getTuple(record: Array[String]) = {
//      try {
//        (record(0), record(1), record(2), record(3).toInt)
//      }
//      catch {
//        case _: Throwable => null
//      }
//    }

//    cassandraStream.foreachRDD(rdd => {
//      println("Output result of batch:")
//      rdd.foreach(println)
//    })

//    cassandraStream.map(record => getTuple(record)).filter(record => record != null).saveToCassandra(keySpace, table, columns)

    SparkStreamFactory.startStream(ssc)
  }

//  def updateState(key: String, value: Option[Int], state: State[Int]): (String, Int, Int) = {
//    val prevValue = state.getOption().getOrElse(0)
//
//    if (state.isTimingOut()) {
//      (key, prevValue, -1)
//    }
//    else {
//      val nextValue = prevValue + value.getOrElse(0)
//
//      if (nextValue > 10)
//        state.remove
//      else
//        state.update(nextValue)
//
//      (key, prevValue, nextValue)
//    }
//  }
}
