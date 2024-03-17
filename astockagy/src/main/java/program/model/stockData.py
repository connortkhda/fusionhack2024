# Define the ticker list
import sys
#import pandas as pd
import yfinance as yf
import matplotlib.pyplot as plt

#print("Called")
stock = sys.argv[1]
data = yf.download(stock,"2019-3-17")["Adj Close"]

#print(data)

graph = plt.plot(data)
#plt.show()

#WHEN RUNNING FROM JAVA -----------------------------------------------------------------------------------------------
data.to_csv("src/main/resources/data/" + stock + "Data.csv")
plt.savefig("src/main/resources/images/" + stock + ".png")

#WHEN RUNNING FROM TERMINAL -----------------------------------------------------------------------------------------------
# data.to_csv("astockagy/src/main/resources/data/" + stock + "Data.csv")
# plt.savefig("astockagy/src/main/resources/images/" + stock + ".png")