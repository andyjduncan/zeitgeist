const index = require("./index");

const symbol = process.argv[2];

index.closingPrices(symbol, (err, prices) => {
    console.log(`${symbol} daily prices: `);
        console.log(prices);
});

index.dailyReturns(symbol, (err, returns) => {
    console.log(`${symbol} daily returns: `);
    console.log(returns)
});

index.meanReturn(symbol, (err, meanReturn) => {
    console.log(`${symbol} mean return: `);
    console.log(meanReturn)
});