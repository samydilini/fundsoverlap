## Overview
This program is designed for MyFund&Stocks, a platform that enables investors to track their investments in equity mutual funds. It provides functionality to calculate the overlap between mutual funds, add new funds or stocks to existing portfolios, and retrieve detailed portfolio overlap information.

-----------------------------------------------------
## Technologies Used 
-as per the specified instructions

Java 8

Gradle 5.1

-----------------------------------------------------

## Assumptions

All input funds will be present in the provided available funds JSON.

Overlap percentages are rounded to two decimal places.

-----------------------------------------------------
## How to Build and Run

### Build

Run the following command to clean and build the project:

`./gradlew clean build`
or
`gradlew clean build`
depending on your system

### Execute

Run the application using the following command:

`java -jar build/libs/geektrust.jar <input_file_path>`

Examples:

`java -jar build/libs/geektrust.jar src/main/resources/input1.txt`

`java -jar build/libs/geektrust.jar src/main/resources/input2.txt`

### Run unit tests  

`./gradlew clean test`
or 
`gradle clean test`
if you are using intelij then you can got to gradle -> Tasks -> build -> build (this will give build run tests and create the jacoco report)
Jacoco report can be found in /build/reports/tests/test/index.html

-----------------------------------------------------
## Github url

https://github.com/samydilini/fundsoverlap

-----------------------------------------------------

## Conclusion

This program demonstrates a robust solution for calculating portfolio overlaps and managing mutual fund investments. The modular structure ensures easy extensibility for additional commands or features in the future.

## The Challenge

You work at MyFund&Stocks, a platform that lets investors track their investments in equity mutual funds.

Equity mutual funds are a collection of individual stocks. Fund overlap is the term used to quantify the percentage of common stocks between different mutual funds. Investors prefer to have funds with as low an overlap as possible, since investing in different mutual funds with a high overlap will not give the benefits of diversification.

Available mutual funds and their respective stocks are mentioned here.

Portfolio Overlap
Overlap is defined as a measure of the commonality between portfolios.

Suppose there are 2 funds A & B. The overlap between them is given as the number of stocks in common, counting both portfolio A and B( (i.e. double counting the common elements) divided by the total number of elements in portfolio A and portfolio B.

Overlap (A,B) =
2*(No of common stocks in A & B)/ (No of stocks in A + No of stocks in B) * 100

Your program should take as input:
1. Name of current funds the user holds in his/her portfolio.
2. New funds to be added.
3. Stock to be added for existing fund.

The output should be
1. Overlapping stocks of the new fund with the current funds.
2. If a given fund name is not present while calculating overlap or adding stocks to existing fund, the output should be FUND_NOT_FOUND.


Input Commands

There are 4 input commands defined to separate out the actions. Your input format will start with either of these commands i.e CURRENT_PORTFOLIO, CALCULATE_OVERLAP, OVERLAP_PERCENTAGE, ADD_STOCK.

CURRENT_PORTFOLIO
The CURRENT_PORTFOLIO command receives the current funds that the user holds in his/her portfolio.
Format - CURRENT_PORTFOLIO FUND_NAME_1 FUND_NAME_2
Example- CURRENT_PORTFOLIO AXIS_BLUECHIP ICICI_PRU_BLUECHIP means that the current fund that the user holds in his/her portfolio are AXIS_BLUECHIP and ICICI_PRU_BLUECHIP

CALCULATE_OVERLAP
The CALCULATE_OVERLAP command receives the fund name and calculates the overlap between that fund with each fund in the current portfolio.
Format - CALCULATE_OVERLAP FUND_NAME
Example - CALCULATE_OVERLAP AXIS_BLUECHIP means calculate the percentage of overlapping stocks of AXIS_BLUECHIP with each fund that the user holds in his/her current portfolio.

OVERLAP_PERCENTAGE
This command should print the overlapping percentages of the given fund with the existing funds in the portfolio.
Format - FUND_NAME EXISTING_FUND OVERLAP_PERCENTAGE%
Example - If you have 3 funds in your portfolio, the CALCULATE_OVERLAP command should print the overlapping percentage of stocks (if its greater than zero) for the given fund with all the funds that the user holds in his/her current portfolio.

ADD_STOCK
The ADD_STOCK command receives the fund name to which the new stock will be added and the name of the new stock.
Format - ADD_STOCK FUND_NAME STOCK_NAME
Example - ADD_STOCK AXIS_BLUECHIP HDFC BANK LIMITED means we have to add the stock HDFC BANK LIMITED to AXIS_BLUECHIP fund.


Assumptions
1. All the input funds will be those that are present in the available funds json provided.
2. The overlap percentages will be rounded to 2 decimal points.
3. The stocks names can have spaces in between, for eg: HDFC BANK LIMITED.

SAMPLE INPUT-OUTPUT 1

INPUT:
CURRENT_PORTFOLIO AXIS_BLUECHIP ICICI_PRU_BLUECHIP UTI_NIFTY_INDEX
CALCULATE_OVERLAP MIRAE_ASSET_EMERGING_BLUECHIP
CALCULATE_OVERLAP MIRAE_ASSET_LARGE_CAP
ADD_STOCK AXIS_BLUECHIP TCS
CALCULATE_OVERLAP MIRAE_ASSET_EMERGING_BLUECHIP

OUTPUT:
MIRAE_ASSET_EMERGING_BLUECHIP AXIS_BLUECHIP 39.13%
MIRAE_ASSET_EMERGING_BLUECHIP ICICI_PRU_BLUECHIP 38.10%
MIRAE_ASSET_EMERGING_BLUECHIP UTI_NIFTY_INDEX 65.52%
MIRAE_ASSET_LARGE_CAP AXIS_BLUECHIP 43.75%
MIRAE_ASSET_LARGE_CAP ICICI_PRU_BLUECHIP 44.62%
MIRAE_ASSET_LARGE_CAP UTI_NIFTY_INDEX 95.00%
MIRAE_ASSET_EMERGING_BLUECHIP AXIS_BLUECHIP 38.71%
MIRAE_ASSET_EMERGING_BLUECHIP ICICI_PRU_BLUECHIP 38.10%
MIRAE_ASSET_EMERGING_BLUECHIP UTI_NIFTY_INDEX 65.52%

SAMPLE INPUT-OUTPUT 2

INPUT:
CURRENT_PORTFOLIO UTI_NIFTY_INDEX AXIS_MIDCAP PARAG_PARIKH_FLEXI_CAP
CALCULATE_OVERLAP ICICI_PRU_NIFTY_NEXT_50_INDEX
CALCULATE_OVERLAP NIPPON_INDIA_PHARMA_FUND
ADD_STOCK PARAG_PARIKH_FLEXI_CAP NOCIL
ADD_STOCK AXIS_MIDCAP NOCIL
CALCULATE_OVERLAP ICICI_PRU_NIFTY_NEXT_50_INDEX

OUTPUT:
ICICI_PRU_NIFTY_NEXT_50_INDEX UTI_NIFTY_INDEX 20.37%
ICICI_PRU_NIFTY_NEXT_50_INDEX AXIS_MIDCAP 14.81%
ICICI_PRU_NIFTY_NEXT_50_INDEX PARAG_PARIKH_FLEXI_CAP 7.41%
FUND_NOT_FOUND
ICICI_PRU_NIFTY_NEXT_50_INDEX UTI_NIFTY_INDEX 20.37%
ICICI_PRU_NIFTY_NEXT_50_INDEX AXIS_MIDCAP 14.68%
ICICI_PRU_NIFTY_NEXT_50_INDEX PARAG_PARIKH_FLEXI_CAP 7.32%


