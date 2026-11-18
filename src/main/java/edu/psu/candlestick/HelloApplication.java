package edu.psu.candlestick;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class HelloApplication extends Application {
    private ArrayList<CandleStick> candles = new ArrayList<>();

    // price to Y pixel position method
    private double priceToY(double price, double maxPrice, double priceRange,
                            double drawingHeight, double padding){
        return padding + (maxPrice - price) / priceRange * drawingHeight;
    }

    @Override
    public void start(Stage stage) throws IOException {

        File file = new File("BTCUSDT.csv");
        try (Scanner scanner = new Scanner(file)){
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] values = line.split(",");
                long openTime = Long.parseLong(values[0]);
                double open = Double.parseDouble(values[1]);
                double high = Double.parseDouble(values[2]);
                double low = Double.parseDouble(values[3]);
                double close = Double.parseDouble(values[4]);
               CandleStick candleStick = new CandleStick(openTime, open, high, low, close);
               candles.add(candleStick);
            }
        } catch(FileNotFoundException e){
            System.out.println("File could not be found");
        }

// determine the max and min range to fit all the candles-----------------------------------------
        double maxPrice = candles.get(0).getHigh();
        double minPrice = candles.get(0).getLow();
        for (CandleStick c : candles){
            if (c.getHigh() > maxPrice){
                maxPrice = c.getHigh();
            }
            if (c.getLow() < minPrice){
                minPrice = c.getLow();
            }
        }
// determine the max and min range to fit all the candles-----------------------------------------


        Pane chartPane = new Pane();
        double chartWidth = 900;
        double chartHeight = 450;
        chartPane.setPrefSize(chartWidth, chartHeight);
        double padding = 65;
        double priceRange = maxPrice - minPrice;
        double drawingHeight = chartHeight - 2 * padding;

        // get x and y coordinate for candles
        double candleSpacing = (chartWidth - 2 * padding) / candles.size();

        for (int i = 0; i < candles.size(); i++){
            CandleStick c = candles.get(i);

            // for line placement
            double x = padding + i * candleSpacing;
            double centerX = x + candleSpacing / 2;

            // x-axis labels
            String date = DateTimeFormatter.ofPattern("MM-dd")
                    .withZone(ZoneOffset.UTC)
                    .format(Instant.ofEpochMilli(c.getOpenTime()));

            if (i % 6 == 0){
                Text dateLabel = new Text(date);
                dateLabel.setStyle("-fx-font-size: 10px;");
                dateLabel.setX(centerX + 25);
                dateLabel.setY(chartHeight - padding + 20);
                chartPane.getChildren().add(dateLabel);
            }
            Text xAxisTitle = new Text("Day");
            xAxisTitle.setStyle("-fx-font-size: 12px;");
            xAxisTitle.setX(chartWidth / 2);
            xAxisTitle.setY(420);
            chartPane.getChildren().add(xAxisTitle);


            double highY = priceToY(c.getHigh(), maxPrice, priceRange, drawingHeight, padding);
            double lowY = priceToY(c.getLow(), maxPrice, priceRange, drawingHeight, padding);
            double openY = priceToY(c.getOpen(), maxPrice, priceRange, drawingHeight, padding);
            double closeY = priceToY(c.getClose(), maxPrice, priceRange, drawingHeight, padding);


            // for body
            double bodyY  = Math.min(openY, closeY);
            double bodyHeight = Math.abs(openY - closeY);
            double bodyWidth = candleSpacing * 0.6;
            double bodyX = centerX - bodyWidth / 2;

            // make wick body of candle
            Line wick = new Line(centerX, highY, centerX, lowY);
            Rectangle body = new Rectangle(bodyX, bodyY, bodyWidth, bodyHeight);
            if (c.getClose() > c.getOpen()){
                body.setFill(Color.GREEN);
            } else{
                body.setFill(Color.RED);
            }
            chartPane.getChildren().addAll(wick, body);
        }

        // axes
        Line yAxis = new Line(padding, padding, padding, chartHeight - padding);
        Line xAxis = new Line(padding, chartHeight - padding, chartWidth - padding, chartHeight - padding);
        chartPane.getChildren().addAll(yAxis, xAxis);

        // y-axis labels
        int numberOfLabels = 10;
        int numberOfIntervals = numberOfLabels - 1;
        double priceStep = priceRange / numberOfIntervals;
        for (int i = 0; i < numberOfLabels; i++){
            double price = minPrice + i * priceStep;
            double y = priceToY(price, maxPrice, priceRange, drawingHeight, padding);
            Text priceLabel = new Text(String.format("%,.0f", price));
            priceLabel.setStyle("-fx-font-size: 10px;");
            priceLabel.setX(6);
            priceLabel.setY(y);
            chartPane.getChildren().add(priceLabel);
        }
        Text yAxisTitle = new Text("Price (USDT)");
        yAxisTitle.setStyle("-fx-font-size: 12px;");
        yAxisTitle.setX(20);
        yAxisTitle.setY(chartHeight / 2);
        yAxisTitle.setRotate(-90);
        chartPane.getChildren().add(yAxisTitle);



        // make scene
        var scene = new Scene(chartPane);
        stage.setHeight(chartHeight);
        stage.setWidth(chartWidth);
        stage.setScene(scene);
        stage.setTitle("Bitcoin candlestick chart");
        stage.show();

    }
}
