Spark Demos
Simple demos for Spark in Cloudera Data Science Workbench.

Contents
/examples/data/ (sample data for projects)
/examples/R/sparklyr-demo.R (SparklyR example from spark.rstudio.com)
/examples/python/kmeans-demo.py (PySpark k-means clustering example, including HDFS file copying)
/examples/scala/ebay-demo.scala (Scala Spark example)
Setup
You'll need to install Sparklyr after cloning the project by running the following commands in an R session:

library("devtools")
devtools::install_github("rstudio/sparklyr")
Note: You only need to do this once.
