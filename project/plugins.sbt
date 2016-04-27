logLevel := Level.Info
resolvers += "twitter-repo" at "https://maven.twttr.com"

addSbtPlugin("com.twitter" %% "scrooge-sbt-plugin" % "4.1.0")
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")