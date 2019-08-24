
name := "khame_data_analyze"

version := "0.1"

scalaVersion := "2.12.8"

mainClass in (Compile, run) := Some("App")
mainClass in (Compile, packageBin) := Some("App")

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.2"

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.2"

// https://mvnrepository.com/artifact/org.apache.kafka/kafka
//libraryDependencies += "org.apache.kafka" %% "kafka" % "2.3.0"

// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming-kafka-0-8
//libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-8" % "2.4.3"
//
// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming-kafka-0-10
//libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.3"

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql-kafka-0-10
libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.4.3"


//libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.3.0"


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

//import sbt.Keys._
//assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
