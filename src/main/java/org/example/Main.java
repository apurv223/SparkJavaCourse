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
        List<Integer> inputData = new ArrayList<>();
        inputData.add(35);
        inputData.add(90);
        SparkConf conf = new SparkConf().setAppName("startingSpark").setMaster("local[*]"); // config for spark set here
        JavaSparkContext sc = new JavaSparkContext(conf); // used to talk to spark cluster
        JavaRDD<Integer> myRdd = sc.parallelize(inputData); // JavaRDD is representation/wrapper of RDD which is originally in scala.
        Integer result = myRdd.reduce((val1, val2) -> val1 + val2 );
        // spark driver sends this function to all nodes and it is applied on any of the two values in the cluster.
        // Once only one value is left in cluster then it does a shuffling and sends all data into a single cluster and applies the finction agains

        JavaRDD<Double> newRdd = myRdd.map(val -> Math.sqrt(val)); // map creates a new Rdd whose return type can be completely different
        newRdd.foreach(val -> System.out.println(val));
        //how many elements are there in a RDD
        System.out.println(newRdd.count());
        //do a count just using map and reduce
        // - map every single value in RDD to a 1
        // - then apply reduce of val1 + val2
        JavaRDD<Integer> singleIntRDD = newRdd.map(val -> 1);
        Integer count = singleIntRDD.reduce((val1,val2) -> val1 + val2);

        System.out.println("count-> " + count);
        sc.close();

    }
}
