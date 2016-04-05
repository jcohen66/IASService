name := "IASService"

version := "1.0"

scalaVersion := "2.11.8"

sbtVersion := "0.13.5"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0-M2",
  "com.typesafe.akka" % "akka-http-experimental_2.11" % "1.0-M2",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4.2",
  "com.typesafe.akka" % "akka-cluster_2.11" % "2.4.2",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "net.sf.ehcache" % "ehcache" % "2.7.4",
  "com.typesafe.play" % "play-json_2.11" % "2.4.0-M2",
  "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.0" % "test"
)

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.7.3"
libraryDependencies += "com.fasterxml.jackson.module" % "jackson-module-scala_2.10" % "2.7.2"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.1.1"
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.19"

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "Typesafe" at "https://repo.typesafe.com/typesafe/releases/"
