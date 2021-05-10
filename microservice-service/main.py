import requests
from flask import Flask, request
import json

app = Flask(__name__)
endpoint = "/api/python"


@app.route(endpoint + '/stock', methods=['GET', 'POST'])
def processStock():
    data = request.json
    stock = data["data"]
    return stock


@app.route(endpoint + '/top', methods=['GET', 'POST'])
def processTopStock():
    data = request.json
    stock = data["data"]
    # print(data)


@app.route(endpoint + '/news', methods=['GET', 'POST'])
def processNews():
    data = request.json
    stock = data["news"]
    # print(data)


app.run(debug=True)
