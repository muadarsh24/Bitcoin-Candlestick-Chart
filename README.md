# Bitcoin Candlestick Chart

A Bitcoin price visualization application built using Java and JavaFX.

## About

This project reads historical Bitcoin-to-USDT trading data from a CSV file and visualizes it using a candlestick chart. The project visualizes 7 days of historical BTC/USDT market data, with 6 candlesticks per day for a total of 42 candlesticks.

Each candlestick  displays the opening, closing, highest, and lowest prices.

## Features

- Reads historical BTC/USDT market data from a CSV file
- Parses opening time and OHLC (Open, High, Low, Close) price values
- Displays individual candlesticks using JavaFX shapes
- Green candles represent price increases
- Red candles represent price decreases
- Automatically calculates the minimum and maximum prices to scale the chart
- Converts price values into screen coordinates
- Displays price labels along the Y-axis
- Displays date labels along the X-axis

## Technologies Used

- Java
- JavaFX
- Java Collections (ArrayList)
- File I/O (Scanner)
- Java Time API
- CSV data processing

## Data Source

Historical BTC/USDT trading data downloaded from Binance.

## How to Run

1. Clone or download this repository.
2. Open the project in IntelliJ IDEA.
3. Place `BTCUSDT.csv` in the expected project directory.
4. Allow IntelliJ to configure the project and download any required dependencies.
5. Run `HelloApplication.java`.

## Screenshots

<img width="893" height="447" alt="Screenshot 2026-11-18 at 12 42 00 PM" src="https://github.com/user-attachments/assets/32533a32-291b-436e-91e8-0ebd9326a9a0" />


