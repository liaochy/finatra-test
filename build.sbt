name := "finatra-test"
version := "1.0"
organization := "com.twitter.finatra.example"

parallelExecution in ThisBuild := false

lazy val versions = new {
  val finatra = "2.1.2"
  val finagle = "6.31.0"
  val scroogeVersion = "4.2.0"
}

val scroogeLibs = Seq(
  "org.apache.thrift" % "libthrift" % "0.8.0",
  "com.twitter" %% "finagle-thrift" % versions.finagle,
  "com.twitter" %% "finagle-thriftmux" % versions.finagle,
  "com.twitter" %% "scrooge-core" % versions.scroogeVersion)

libraryDependencies ++= Seq(
  "com.twitter.finatra" %% "finatra-http" % versions.finatra,
  "com.twitter.finatra" %% "finatra-httpclient" % versions.finatra,
  "com.twitter.finatra" %% "finatra-slf4j" % versions.finatra,
  "com.twitter.inject" %% "inject-core" % versions.finatra,
  "com.twitter" %% "finagle-mysql" % versions.finagle,
  "com.twitter" %% "finagle-memcached" % versions.finagle excludeAll(
    ExclusionRule(organization = "org.slf4j")
    ),
  "com.twitter" %% "finagle-serversets" %   versions.finagle excludeAll(
    ExclusionRule(organization = "org.slf4j")
    ),
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "com.twitter" %% "finagle-zipkin" % versions.finagle

//  "com.twitter.finatra" %% "finatra-http" % versions.finatra % "test",
//  "com.twitter.finatra" %% "finatra-jackson" % versions.finatra % "test",
//  "com.twitter.inject" %% "inject-server" % versions.finatra % "test",
//  "com.twitter.inject" %% "inject-app" % versions.finatra % "test",
//  "com.twitter.inject" %% "inject-core" % versions.finatra % "test",
//  "com.twitter.inject" %% "inject-modules" % versions.finatra % "test",
//  "com.twitter.finatra" %% "finatra-http" % versions.finatra % "test" classifier "tests",
//  "com.twitter.finatra" %% "finatra-jackson" % versions.finatra % "test" classifier "tests",
//  "com.twitter.inject" %% "inject-server" % versions.finatra % "test" classifier "tests",
//  "com.twitter.inject" %% "inject-app" % versions.finatra % "test" classifier "tests",
//  "com.twitter.inject" %% "inject-core" % versions.finatra % "test" classifier "tests",
//  "com.twitter.inject" %% "inject-modules" % versions.finatra % "test" classifier "tests",
//
//  "org.mockito" % "mockito-core" % "1.9.5" % "test",
//  "org.scalatest" %% "scalatest" % "2.2.3" % "test",
//  "org.specs2" %% "specs2" % "2.3.12" % "test"
)

libraryDependencies ++= scroogeLibs