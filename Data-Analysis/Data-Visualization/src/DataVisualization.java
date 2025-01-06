//ONEIL853

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;

public class DataVisualization extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DataVisualization.class.getResource("hello-view.fxml"));
//Defining the x an y axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        //Setting labels for the axes
        xAxis.setLabel("Wind Speed Squares");
        yAxis.setLabel("Cumulative Probability");
        //Creating a line chart
        LineChart<Number, Number> linechart = new LineChart<Number, Number>(xAxis, yAxis);
        //Preparing the data points for the line1
        XYChart.Series exactCumulativeProbabilities = new XYChart.Series();
        double[] probabilitiesList = getFileData();
        int interval = 10000/probabilitiesList.length;
        for (int i = 0; i < probabilitiesList.length; i++) {
            if (probabilitiesList[i] <= 0.002)
                break;
            exactCumulativeProbabilities.getData().add(new XYChart.Data(i*interval, probabilitiesList[i]));
        }

        //Setting the name to the line (series)
        exactCumulativeProbabilities.setName("Cumulative Probabilities per interval");

        XYChart.Series ols = new XYChart.Series();
        double num = 0.0;
        double den = 0.0;
        double correlationCoefficient;
        for (int i = 0; i < probabilitiesList.length-1; i++) {
            if (probabilitiesList[i] <= 0.002)
                break;
            num = num - Math.log(probabilitiesList[i]);
            den = den + (interval * (i+1));
        }
        correlationCoefficient = num/den;
        for (int i = 0; i < probabilitiesList.length-1; i++) {
            if (probabilitiesList[i] <= 0.002)
                break;
            double cumulativeProbabilities = Math.exp(-1 * correlationCoefficient * interval * i);
            ols.getData().add(new XYChart.Data(i*interval, cumulativeProbabilities));
        }


        ols.setName("OLS");






        //Setting the data to Line chart
        linechart.getData().addAll(exactCumulativeProbabilities,ols);
        //Creating a stack pane to hold the chart
        StackPane pane = new StackPane(linechart);
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.setStyle("-fx-background-color: BEIGE");
        //Setting the Scene



        Scene scene = new Scene(pane, 595, 350);
        stage.setTitle("Line Chart");
        stage.setScene(scene);
        stage.show();
    }

    public static double[] getFileData() throws IOException {
        File inputFile = new File("cumProbability.txt");
        Scanner fileScanner = new Scanner(inputFile);
        LinkedList<Double> data = new LinkedList<Double>();
        while(fileScanner.hasNext()) {
            fileScanner.next();
            data.add(Double.parseDouble(fileScanner.nextLine()));
        }
        double[] returnList = new double[data.size()];
        for(int i = 0; i < returnList.length; i++) {
            returnList[i] = data.remove();
        }
        return returnList;
    }

    public static void main(String[] args) {
        launch();
    }
}