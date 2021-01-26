name := "WordCountFaultTolerant"

version := "0.1"

scalaVersion := "2.13.4"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.11"
libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % "2.6.11"

libraryDependencies +=  "com.github.scopt" %% "scopt" % "4.0.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test"


