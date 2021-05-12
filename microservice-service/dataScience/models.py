import pickle
import pandas as pd  # data processing, CSV file I/O (e.g. pd.read_csv)
import json
import warnings
import os
import re
from sklearn.feature_extraction.text import CountVectorizer

warnings.filterwarnings('ignore')

loaded_model_stocks = pickle.load(open("finalized_model.sav", 'rb'))
loaded_model_news = pickle.load(open("finalized_model_2.sav", 'rb'))
loaded_countvector = pickle.load(open("countvector", 'rb'))
ex_variables = ['Open', 'High', 'Low', 'Close']


def getAllDataFiles():
    presentFiles = []
    for root, dirs, file in os.walk('stocks'):
        presentFiles = file
    return presentFiles


def getPredByStock(stock):
    df = pd.read_csv('stocks/APLE.csv')
    for files in getAllDataFiles():
        if stock in files:
            df = pd.read_csv('stocks/' + files)
            break
    test_data = df[len(df) - 2:]
    results = loaded_model_stocks.predict(n_periods=len(test_data), exogenous=test_data[ex_variables])
    return json.dumps(results.tolist())


def getTopStocks():
    stockDiffs = []
    stockNames = []
    for files in getAllDataFiles():
        df = pd.read_csv('stocks/' + files)
        test_data = df[len(df) - 2:]
        results = loaded_model_stocks.predict(n_periods=len(test_data), exogenous=test_data[ex_variables])
        stockDiffs.append((results[1] - results[0], files))  # if negative, price went down, if positive, price went up
        stockNames.append(files)
    stockDiffs.sort()
    topStocks = stockDiffs[-3:]
    return json.dumps(topStocks)


def getPredByNews(news):
    news = re.sub("[^a-zA-Z]", " ", news).lower()
    news = [news]
    prediction = loaded_model_news.predict(loaded_countvector.transform(news))
    return json.dumps(int(prediction[0]))