/* # eBay Auction Data Example */

// SQLContext entry point for working with structured data
val sqlContext = new org.apache.spark.sql.SQLContext(sc)

// Implicitly convert an RDD to a DataFrame
import sqlContext.implicits._

// Import Spark SQL data types, row and math functions
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

// Load data from HDFS into a new RDD
val ebayText = sc.textFile("data/ebay-xbox.csv")
/* Show the first row of our file: */
ebayText.first()
                      
// Define the schema using a case class
case class Auction(auctionid: String, bid: Float, bidtime: Float,
                   bidder: String, bidderrate: Integer, openbid: Float, 
                   price: Float, item: String)
  
// Create an RDD of Auction objects
val ebayRDD = ebayText.map(_.split(",")).map(p => Auction(p(0),
                                                          p(1).toFloat,
                                                          p(2).toFloat,
                                                          p(3),
                                                          p(4).toInt,
                                                          p(5).toFloat,
                                                          p(6).toFloat,
                                                          "xbox"))
                    
/* How many items in our dataset? */
ebayRDD.count()

// Convert to a DataFrame
val ebayDF = ebayRDD.toDF()
  
/* Show the top 20 rows of the DataFrame: */
ebayDF.show()
/* Return the schema of this DataFrame */
ebayDF.printSchema()

/* Show the physical plan for auctionId, to help with debugging: */
ebayDF.select("auctionid").distinct.explain()

/* ## Now let's explore the data */

/* How many auctions were held? */
ebayDF.select("auctionid").distinct.count
/* How many bids per item? */
ebayDF.groupBy("auctionid", "item").count.show

/* What is the min, average, and max number of bids per item? */
ebayDF.groupBy("item", "auctionid").count.agg(min("count"), avg("count"), max("count")).show

/* Show any auctions with a price > $100 */
val highprice= ebayDF.filter("price > 100")
highprice.show()