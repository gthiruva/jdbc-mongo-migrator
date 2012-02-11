import AssemblyKeys._ // put this at the top of the file

seq(assemblySettings: _*)

jarName in assembly := "jmm.jar"

organization := "jm.migrator"

name := "jmm"

version := "1.0"

scalaVersion := "2.9.1"

// scalaVersion := "2.10.0-M1"

scalacOptions ++= Seq("-deprecation")

scalacOptions ++= Seq("-unchecked")

libraryDependencies += "org.slf4j" % "slf4j-jdk14" % "1.6.4"

libraryDependencies += "net.lag" % "configgy" % "2.0.0"

libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.9.1"

libraryDependencies += "org.scala-tools" % "vscaladoc" % "1.2-m1"

libraryDependencies += "org.scala-tools" % "scala-tools-parent" % "1.6"

libraryDependencies += "net.liftweb" %% "lift-json" % "2.4"

// libraryDependencies += "com.mongodb" %% "mongo-driver" % "0.2.0" // "2.0.0-SNAPSHOT"   // "2.1.5-1"

//libraryDependencies += "com.mongodb.casbah" %% "casbah" % "3.0.0-M2" //"3.0.0-SNAPSHOT" //% "2.1.5-1" // "2.0.0-SNAPSHOT"   // "2.1.5-1"

libraryDependencies += "org.mongodb" %% "casbah" % "3.0.0-M2" //"3.0.0-SNAPSHOT" //% "2.1.5-1" // "2.0.0-SNAPSHOT"   // "2.1.5-1"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.7.RC2" % "test"

libraryDependencies += "com.twitter" %% "util-core" % "1.12.12"

libraryDependencies += "com.twitter" %% "util-eval" % "1.12.12"

libraryDependencies += "com.twitter" %% "ostrich" % "4.10.4"

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.18"

// libraryDependencies += "com.h2database" % "h2" % "1.2.163"

// libraryDependencies += "org.h2database" % "h2database" % "1.0.20061217"

resolvers += "scala-tools-repo" at "http://scala-tools.org/repo-releases"

resolvers += "scala-tools-snaprepo" at "http://scala-tools.org/repo-snapshots"

resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

resolvers += "twitter-repo" at "http://maven.twttr.com"

resolvers += "sonatype-repo" at "https://oss.sonatype.org/content/groups/scala-tools/"

test in assembly := {}
