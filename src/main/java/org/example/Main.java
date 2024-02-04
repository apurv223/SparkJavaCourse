package org.example;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;


/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        List<Double> inputData = new ArrayList<>();
        inputData.add(35.5);
        inputData.add(90.5);
        SparkConf conf = new SparkConf().setAppName("startingSpark").setMaster("local[*]"); // config for spark set here
        JavaSparkContext sc = new JavaSparkContext(conf); // used to talk to spark cluster
        JavaRDD<Double> myRdd = sc.parallelize(inputData); // JavaRDD is representation/wrapper of RDD which is originally in scala.
        Double result = myRdd.reduce((val1, val2) -> val1 + val2 );
        // spark driver sends this function to all nodes and it is applied on any of the two values in the cluster.
        // Once only one value is left in cluster then it does a shuffling and sends all data into a single cluster and applies the finction agains
        System.out.println("result-> " + result);
        sc.close();

    }
}
