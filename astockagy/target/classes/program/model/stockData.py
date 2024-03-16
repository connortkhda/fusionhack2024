# Define the ticker list
import pandas as pd
tickers_list = ['AMD']

# Fetch the data
import yfinance as yf
data = yf.download(tickers_list,'2019-3-22')['Adj Close']

# Print first 5 rows of the data
print(data)

# Import the plotting library
import matplotlib.pyplot as plt

# Plot the close price of the AAPL
#data['Adj Close'].plot()
plt.plot(data)
plt.show()