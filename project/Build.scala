import sbt._
import sbt.Keys._

object ProjectBuild extends Build {

  lazy val buildVersion =  "1.2-SNAPSHOT"

  lazy val root = Project(id = "play-plugins-salat", base = file("."), settings = Project.defaultSettings ++ Publish.settings ++ Ls.settings).settings(
    organization := "se.radley",
    description := "MongoDB Salat plugin for PlayFramework 2",
    version := buildVersion,
    scalaVersion := "2.10.0",
    parallelExecution in Test := false,
    testFrameworks += TestFrameworks.Specs2,

    resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
    resolvers += "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
    resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",

    libraryDependencies += "play" %% "play" % "2.1-RC2" % "provided",
    libraryDependencies += "play" %% "play-test" % "2.1-RC2" % "test",
    libraryDependencies += "com.novus" %% "salat-core" % "1.9.2-SNAPSHOT",
    libraryDependencies += "org.mongodb" %% "casbah-gridfs" % "2.5.0-SNAPSHOT"
  )
}

object Publish {
  lazy val settings = Seq(
    publishMavenStyle := true,
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage := Some(url("https://github.com/leon/play-salat")),
    pomExtra := (
      <scm>
        <url>git://github.com/leon/play-salat.git</url>
        <connection>scm:git://github.com/leon/play-salat.git</connection>
      </scm>
      <developers>
        <developer>
          <id>leon</id>
          <name>Leon Radley</name>
          <url>http://github.com/leon</url>
        </developer>
      </developers>)
  )
}

object Ls {

  import _root_.ls.Plugin.LsKeys._

  lazy val settings = _root_.ls.Plugin.lsSettings ++ Seq(
    (description in lsync) := "MongoDB Salat plugin for Play Framework 2.",
    licenses in lsync <<= licenses,
    (tags in lsync) := Seq("play", "playframework", "salat", "mongo", "casbah", "object document mapping", "ODM", "mapper"),
    (docsUrl in lsync) := Some(new URL("https://github.com/leon/play-salat"))
  )
}
