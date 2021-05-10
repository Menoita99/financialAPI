import numpy as np # linear algebra
import pandas as pd # data processing, CSV file I/O (e.g. pd.read_csv)
import os
#for dirname, _, filenames in os.walk('/kaggle/input'):
#   for filename in filenames:
# print(os.path.join(dirname, filename))

import plotly.express as px #Visualization
from pmdarima import auto_arima #Predictions

from statsmodels.tsa.arima_model import ARIMA
import warnings
warnings.filterwarnings('ignore')

df = pd.read_csv('../input/stock-market-dataset/stocks/AAPL.csv')
df

df_lastyears = df.loc['2015-01-02':]
#Price difference between two consecutive days
df_lastyears['Price_Difference'] = df_lastyears['Close'].shift(-1) - df_lastyears['Close']
df_lastyears = df_lastyears.dropna()

#Let's calculate the daily return by using the expression we wrote before
df_lastyears['Return'] = df_lastyears['Price_Difference'] / df_lastyears['Close']

fig_return = px.line(data_frame=df_lastyears, x=df_lastyears.index, y='Return', title='Daily Return')
fig_return.update_xaxes(title='Date')
fig_return.show()

df_lastyears['MA10_Close'] = df_lastyears['Close'].rolling(10).mean()
df_lastyears['MA10_Open'] = df_lastyears['Open'].rolling(10).mean()

df_lastyears['MA50_Close'] = df_lastyears['Close'].rolling(50).mean()
df_lastyears['MA50_Open'] = df_lastyears['Open'].rolling(50).mean()


train_data, test_data = df_lastyears[0:int(len(df_lastyears)*0.7)], df_lastyears[int(len(df_lastyears)*0.7):]

ex_variables = ['MA50_Close', 'MA50_Open', 'MA10_Open', 'MA10_Close']
train_data = train_data.dropna()
model = auto_arima(
    train_data['Close'],
    exogenous=train_data[ex_variables],
    trace=True, error_action="ignore",
    suppress_warnings=True)

model.fit(train_data['Close'], exogenous=train_data[ex_variables])

test_data = test_data.dropna()

predictions = model.predict(n_periods=len(test_data), exogenous=test_data[ex_variables])
test_data['Predictions'] = predictions