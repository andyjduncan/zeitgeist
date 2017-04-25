"use strict";

const request = require("request");

const compose = require("compose-function");

const wu = require("wu");

const moment = require("moment");

const splitter = (separator) => (text) => text.split(separator);

const bodySplitter = splitter("\n");

const lineSplitter = splitter(",");

const baseUrl = "http://real-chart.finance.yahoo.com/table.csv";

const fieldExtractor = (index) => (elements) => {
    return elements[index];
};

function* lines(text) {
    yield* bodySplitter(text);
}

const fieldIndex = (header) => (name) => lineSplitter(header).indexOf(name);

const url = (now) => (symbol) => {
    const aYearAgo = moment(now).add(-1, 'years').toDate();

    const fromParams = `a=${aYearAgo.getMonth()}&b=${aYearAgo.getDay()}&c=${aYearAgo.getFullYear()}`;

    const toParams = `d=${now.getMonth()}&e=${now.getDay()}&f=${now.getFullYear()}`

    return `${baseUrl}?s=${symbol}&${fromParams}&${toParams}&g=d&ignore=.csv`;
};

const closingPrices = (body) => {

    const tickerLines = lines(body.trim());

    const closingPriceField = fieldIndex(tickerLines.next().value)("Close");

    const closingPrice = compose(parseFloat, fieldExtractor(closingPriceField), lineSplitter);

    return wu(tickerLines).map(closingPrice);
};

const dailyReturns = (body) => {
    const todayPrices = closingPrices(body);

    const yesterdayPrices = closingPrices(body);

    todayPrices.next();

    const dailyReturn = (prices) => (prices[0] - prices[1]) / prices[1];

    return wu.zip(todayPrices, yesterdayPrices).map(dailyReturn);
};

exports.closingPrices = (symbol, callback) => {

    request(
        url(new Date())(symbol),
        (err, response, body) => {
            const allPrices = Array.from(closingPrices(body));

            callback(err, allPrices);
        });
};

exports.dailyReturns = (symbol, callback) => {
    request(
        url(new Date())(symbol),
        (err, response, body) => {
            const allReturns = Array.from(dailyReturns(body));

            callback(err, allReturns);
        });
};

exports.meanReturn = (symbol, callback) => {
    request(
        url(new Date())(symbol),
        (err, response, body) => {
            const returns = dailyReturns(body);

            const returnTotal = wu
                .zip(returns, wu.count(1))
                .reduce((acc, val) => {
                return [acc[0] + val[0], val[1]]
            }
                , [0, 0]
                );

            const meanReturn = returnTotal[0] / returnTotal[1];

            callback(err, meanReturn);
        });
};