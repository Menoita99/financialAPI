from flask import Flask, request
import json
import models

app = Flask(__name__)
endpoint = "/api/python"


@app.route(endpoint + '/stock', methods=['GET', 'POST'])
def processStock():
    data = request.json
    stock = data["data"]
    return models.getPredByStock(stock)


@app.route(endpoint + '/top', methods=['GET', 'POST'])
def processTopStock():
    return models.getTopStocks()


@app.route(endpoint + '/news', methods=['GET', 'POST'])
def processNews():
    data = request.json
    new = data["data"]
    return models.getPredByNews(new)


app.run(debug=True)
