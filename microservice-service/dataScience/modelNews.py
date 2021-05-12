import pandas as pd
import numpy as np
import warnings
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.ensemble import RandomForestClassifier

import pickle


warnings.filterwarnings('ignore')

# imported the file which contains top 25 headlines, stock went up or down(label) and date
data1 = pd.read_csv('news/Combined_News_DJIA.csv')

# filling the null values with median

data1['Top23'].fillna(data1['Top23'].median,inplace=True)
data1['Top24'].fillna(data1['Top24'].median,inplace=True)
data1['Top25'].fillna(data1['Top25'].median,inplace=True)

# seperating the data into train and test

train = data1[data1['Date'] < '20150101']
test = data1[data1['Date'] > '20141231']

# removing punctuations and changing all the letters to lowercase for both train and test

all_data = [train,test]

for df in all_data:
    df.replace("[^a-zA-Z]"," ",regex=True, inplace=True)
    for i in df.columns:
        if i=='Date':
            continue
        if i=='Label':
            continue
        df[i] = df[i].str.lower()

# combining all the headlines in train data into one and appending them into a list

headlines = []
for row in range(0,len(train.index)):
    headlines.append(' '.join(str(x) for x in train.iloc[row,2:]))

# combining all the headlines in test data into one and appending them into a list

test_transform = []
for row in range(0, len(test.index)):
    test_transform.append(' '.join(str(x) for x in test.iloc[row,2:27]))

# Applying countvectorizer on headlines list that we created before and max features is set to 100009

countvector = CountVectorizer(ngram_range=(2, 2), max_features=100009)
traindataset = countvector.fit_transform(headlines)

randomclassifier = RandomForestClassifier(n_estimators=200,criterion='entropy')
randomclassifier.fit(traindataset, train['Label'])
# save the model to disk
filename = 'finalized_model_2.sav'
pickle.dump(countvector, open("countvector", 'wb'))
pickle.dump(randomclassifier, open(filename, 'wb'))

# Applying countvectorizer on test_transform list that we created before
test_dataset = countvector.transform(test_transform)
predictions = randomclassifier.predict(test_dataset)
print(predictions)