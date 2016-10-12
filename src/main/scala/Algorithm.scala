package org.template.classification

import org.apache.predictionio.controller.P2LAlgorithm
import org.apache.predictionio.controller.Params
import org.apache.predictionio.data.storage.BiMap
import org.apache.spark.mllib.optimization.L1Updater
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.classification.SVMModel
import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.linalg.Vectors
import grizzled.slf4j.Logger


case class SVMModeling(
	numIterations: Int
	) extends Params

class Algorithm(val ap: SVMModeling)extends P2LAlgorithm[PreparedData, SVMModel, Query, PredictedResult] {

  @transient lazy val logger = Logger[this.type]

  def train(sc: SparkContext, data: PreparedData): SVMModel = {
    // MLLib NaiveBayes cannot handle empty training data.
    require(data.labeledPoints.take(1).nonEmpty,
      s"RDD[labeledPoints] in PreparedData cannot be empty." +
      " Please check if DataSource generates TrainingData" +
      " and Preparator generates PreparedData correctly.")

    SVMWithSGD.train(data.labeledPoints,ap.numIterations)

}


 def predict(model: SVMModel, query: Query): PredictedResult = {

    val label = model.predict(Vectors.dense(Array(query.temperature,query.voltage,query.frequency)))
    new PredictedResult(label)
  }

}