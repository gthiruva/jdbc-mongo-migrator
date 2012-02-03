import AssemblyKeys._ // put this at the top of the file

seq(assemblySettings: _*)

organization := "jm.migrator"

name := "jmm"

version := "1.0"

scalaVersion := "2.9.1"

libraryDependencies += "net.lag" % "configgy" % "2.0.0"

libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.9.1"

libraryDependencies += "org.scala-tools" % "vscaladoc" % "1.1"

libraryDependencies += "net.liftweb" % "lift-json_2.9.1" % "2.4"

libraryDependencies += "com.mongodb.casbah" %% "casbah" % "2.1.5-1"

libraryDependencies += "org.scalatest" % "scalatest_2.9.1" % "1.7.RC1"

// libraryDependencies += "com.h2database" % "h2" % "1.2.163"

// libraryDependencies += "org.h2database" % "h2database" % "1.0.20061217"

resolvers += "scala-tools-repo" at "http://scala-tools.org/repo-releases"

resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

test in assembly := {}
