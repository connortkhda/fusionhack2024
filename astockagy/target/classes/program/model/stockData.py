# Define the ticker list
import sys
#import pandas as pd
import yfinance as yf
import matplotlib.pyplot as plt

stock = sys.argv[1]
data = yf.download(stock,"2019-3-22")["Adj Close"]
print(data)
plt.plot(data)
#plt.show()
plt.savefig("astockagy/src/main/resources/images/" + stock + ".png")