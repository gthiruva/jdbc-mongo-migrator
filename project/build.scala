import sbt._
import Keys._

object MyApp extends Build
{
  // val sampleKeyA = SettingKey[String]("sample-a", "demo key A")

  lazy val root = Project("root", file(".")) // dependsOn(uri("git://github.com/milessabin/shapeless.git#shapeless-1.1.0") % "compile->compile")
  // lazy val shapelessProject = RootProject(uri("git://github.com/milessabin/shapeless.git#shapeless-1.1.0"), "shapeless")
}
