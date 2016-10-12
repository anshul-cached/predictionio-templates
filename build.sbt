import AssemblyKeys._

assemblySettings

name := "template-scala-svm-classification"


libraryDependencies ++= Seq(
  "org.apache.predictionio" % "predictionio-core_2.10" % "0.10.0-incubating-rc1"  % "provided",
  "org.apache.spark" % "spark-streaming_2.10" % "1.4.0"  % "provided",
  "org.apache.spark" % "spark-mllib_2.10" % "1.4.0"  % "provided",
  "org.apache.spark" % "spark-core_2.10" % "1.4.0"  % "provided")

mergeStrategy in assembly := {
case PathList("org", "apache", xs @ _*) => MergeStrategy.last
case PathList("com", "google", xs @ _*) => MergeStrategy.last
case x =>
    val oldStrategy = (mergeStrategy in assembly).value
    oldStrategy(x)
}
