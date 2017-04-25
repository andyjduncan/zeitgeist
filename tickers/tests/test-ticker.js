"use strict";

const chai = require('chai');

const should = chai.should();

const nock = require("nock");

const lolex = require("lolex");

const moment = require("moment");

const index = require("../index");

const randString = () => Math.random().toString(36).substr(2, 5);

before('setup nock', () => {
    nock.disableNetConnect();
});

after('cleanup nock', () => {
    nock.cleanAll();
    nock.enableNetConnect();
});

describe("Ticker", () => {

    const tickerHeader = function*() {
        yield 'Date,Open,High,Low,Close,Volume,Adj Close'
    };

    const tickerRows = function*(prices) {
        for (let price of prices) {
            yield `,,,,${price},,`
        }
    };

    const prices = function*(startingPrice) {
        let incr = 0;

        while (incr++ < 10) {
            yield startingPrice + incr;
        }
    };

    const tickerResponse = function*(startingPrice) {
        yield* tickerHeader();
        yield* tickerRows(prices(startingPrice));
    };

    const firstPrice = Math.floor(10 * Math.random());

    const response = Array.from(tickerResponse(firstPrice)).join("\n");

    const symbol = randString();

    const year = 2001 + (20 * Math.random());

    const month = 11 * Math.random();

    const day = 1 + (27 * Math.random());

    const lastYear = new Date(year, month, day);

    const now = moment(lastYear).add(1, 'years').toDate();

    let clock;

    beforeEach('setup time', function () {
        clock = lolex.install(now.getTime());
    });

    afterEach('restore time', function () {
        clock.uninstall();
    });

    beforeEach(() => {
        nock("http://real-chart.finance.yahoo.com")
            .get("/table.csv")
            .query(
                {
                    s: symbol,
                    a: lastYear.getMonth(),
                    b: lastYear.getDay(),
                    c: lastYear.getFullYear(),
                    d: now.getMonth(),
                    e: now.getDay(),
                    f: now.getFullYear(),
                    g: 'd',
                    ignore: '.csv'
                }
            )
            .reply(200, response);
    });

    it("should return historical closing prices", (done) => {
        index.closingPrices(symbol, (err, result) => {
            result.should.eql(Array.from(prices(firstPrice)));
            done();
        });
    });

    it("should return daily returns", (done) => {
        const yesterdayPrices = Array.from(prices(firstPrice));

        const todayPrices = yesterdayPrices.slice(1);

        const returns = [];

        for (let i = 0; i < todayPrices.length; i++) {
            const todayPrice = todayPrices[i];
            const yesterdayPrice = yesterdayPrices[i];
            returns.push((todayPrice - yesterdayPrice) / yesterdayPrice);
        }

        index.dailyReturns(symbol, (err, result) => {
            result.should.eql(returns);
            done();
        });
    });

    it("should return the mean return", (done) => {
        const yesterdayPrices = Array.from(prices(firstPrice));

        const todayPrices = yesterdayPrices.slice(1);

        let sum = 0;

        for (let i = 0; i < todayPrices.length; i++) {
            const todayPrice = todayPrices[i];
            const yesterdayPrice = yesterdayPrices[i];
            sum += (todayPrice - yesterdayPrice) / yesterdayPrice;
        }

        const meanReturn = sum / todayPrices.length;

        index.meanReturn(symbol, (err, result) => {
            result.should.equal(meanReturn);

            done();
        });
    });
});