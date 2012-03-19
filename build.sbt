import AssemblyKeys._ // put this at the top of the file

seq(assemblySettings: _*)

jarName in assembly := "jmm.jar"

organization := "jm.migrator"

name := "jmm"

version := "1.0"

scalaVersion in Compile := "2.9.1-1"

scalaVersion := "2.9.1"

scalacOptions ++= Seq("-deprecation")

scalacOptions ++= Seq("-unchecked")

libraryDependencies += "org.slf4j" % "slf4j-jdk14" % "1.6.4"

libraryDependencies += "net.lag" % "configgy" % "2.0.0"

libraryDependencies <++= (scalaVersion){
        case (sv) => List("org.scala-lang" % ("scala-compiler") % sv)
}

libraryDependencies += "org.scala-tools" % "vscaladoc" % "1.2-m1"

libraryDependencies += "org.scala-tools" % "scala-tools-parent" % "1.6"

libraryDependencies += "net.liftweb" %% "lift-json" % "2.5-SNAPSHOT"

// libraryDependencies += "com.mongodb" %% "mongo-driver" % "0.2.0" // "2.0.0-SNAPSHOT"   // "2.1.5-1"

//libraryDependencies += "com.mongodb.casbah" %% "casbah" % "3.0.0-M2" //"3.0.0-SNAPSHOT" //% "2.1.5-1" // "2.0.0-SNAPSHOT"   // "2.1.5-1"

libraryDependencies += "org.mongodb" %% "casbah" % "3.0.0-M2" //"3.0.0-SNAPSHOT" //% "2.1.5-1" // "2.0.0-SNAPSHOT"   // "2.1.5-1"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.7.1" % "test"

libraryDependencies += "com.twitter" %% "util-core" % "3.0.0" // "1.12.12"

libraryDependencies += "com.twitter" %% "util-eval" % "3.0.0" // "1.12.12"

libraryDependencies += "com.twitter" %% "ostrich" % "6.0.0" // "4.10.4"

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.18"

resolvers += "scala-tools-repo" at "http://scala-tools.org/repo-releases"

resolvers += "scala-tools-snaprepo" at "http://scala-tools.org/repo-snapshots"

resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

resolvers += "twitter-repo" at "http://maven.twttr.com"

resolvers += "sonatype-repo" at "https://oss.sonatype.org/content/groups/scala-tools/"

test in assembly := {}
