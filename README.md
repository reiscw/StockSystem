# StockSystem
An application used to automatically re-balance stock and mutual fund portfolios

This is a simple application used to create and rebalance Stock/ETF/Mutual Fund Portfolio portfolios. Portfolio information is saved in a simple text file; Position objects are able to update their prices dynamically using Yahoo Finance data.

On most systems (Linux/MacOS) giving executable permissions to buildStockSystem and executing it will create a JAR file. On Windows, buildStockSystem can be modified to a .BAT file for the same purpose.

The program has been used most extensively on macOS (v12.4+) but also works well on Windows and Linux operating systems.

The program uses a private API to obtain quotes for stocks and ETFs.  Mutual fund prices must be entered manually. You can obtain an API key by visiting financialmodelingprep.com. This key must be placed in the working directory in a file named "API_KEY.txt".

