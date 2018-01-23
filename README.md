# 1. Welcome to the Wall-e automation script

*Wall-e*  is an automation script execution robot.

# 2. What is Wall-e for?
*Wall-e* is usefull tool to automate repetitive tasks like:
* navigate between of web pages getting url, clicking links, sending/getting text to/from datafields
* fulfillment of web pages forms based on Excel worksheets
* extract data from html tables into Excel worksheets


# 3. Command line arguments

```
C:\> java -jar wall-e.jar
Missing required option: f
usage: wall-e [options]
 -1,--args#1 <arg>      parameter argument #1 - use args[1] to reference inside script
 -2,--args#2 <arg>      parameter argument #2 - use args[2] to reference inside script
 -3,--args#3 <arg>      parameter argument #3 - use args[3] to reference inside script
 -4,--args#4 <arg>      parameter argument #4 - use args[4] to reference inside script
 -5,--args#5 <arg>      parameter argument #5 - use args[5] to reference inside script
 -6,--args#6 <arg>      parameter argument #6 - use args[6] to reference inside script
 -7,--args#7 <arg>      parameter argument #7 - use args[7] to reference inside script
 -8,--args#8 <arg>      parameter argument #8 - use args[8] to reference inside script
 -f,--scriptfile <arg>   script file to be executed
 -h,--help               shows usage help message
 -H,--help-command       shows script language sintax help
```


# 4. Wall-e Script Language

 Command                 | Parameter(s) | Description                              |
| ---------------------- | ------------ | ---------------------------------------- |
| \#                     |              | Remark all line.                          |
| **clear**              |              | Clear the content of `lastWebElementFound` |
| **click**              |              | Click on the `lastWebElementFound`         |
| **loadTableFrom**      | *tableName*, *dataSourceType*, *dataSource* | Load into *tableName* symbol data from datasource. When *dataSourceType* is `excel` Then *dataSource* is Excel filename specification. When *dataSourceType* is `htmlTable` Then *dataSource* is xPath of table web element to be loaded |
| **EndForEach**         |              | Loop flow control over *table*. Iterates each line of table allowing access to each column of table record (use forEach .. EndForEach) |
| **findElementByXPath** | *xPath*      | Find an web element on the current page using xPath expression. Element found can be referenced by `lastWebElementFound` |
| **forEach**            | *table*      | Loop flow control over *table*. Iterates each line of table allowing access to each column of table record (use forEach .. EndForEach) |
| **get**                | *url*        | Get url                                       |
| **selectOptionBy**     | *optionByType*, *optionByValue* | Select a combo-box with option. When *optionByType* is `visiableText` Then *optionByValue* is combo-box visible text to be selected. When *optionByType* is `index` Then *optionByValue* is combo-box index position to be selected. When *optionByType* is `value` Then *optionByValue* is combo-box value to be selected. |
| **sendKeys**           | *keysToBeSent* | Send keys to `lastWebElementFound`. When *keysToBeSent* contains inside the text names of *variables* Then all *variables* names are replaced by *variable* contents value |
| **sendKeyEnter**       |              | Send ENTER keys to `lastWebElementFound` |
| **sendKeyTab**         |              | Send TAB key to `lastWebElementFound` |
| **setExpressionToVariable** | *expression*, *variableName* | Sets the results evaluated of an *expression* into *variableName*. If *variableName* does not exists then it's symbol is automatically created in Symbol Table. |
| **wait**               | *millisecs*  | Wait millisecs amount of time                 |


# 5. Usage Examples

## 5.1. Wall-e saying "Hello World" in Google Search Page

```
#
# Get url
#
get                 "http://google.com"
wait                5000
findElementByXPath  .//*[@id='lst-ib']
click
sendkeys            "Wall-e automation robot is saying: Hello world!"
sendkeyEnter
wait                10000
```


## 5.2. Using command line parameter arguments inside script
Command line arguments parameters are usefull to pass username and password to script. It's dangerous store password inside a simple text file for wall-e script commands.

```
C:\> java -jar wall-e.jar -f scriptfile.txt -1 I -2 have -3 been -4 here!
```

```
#
# scriptfile.txt
#
get                 "http://google.com"
wait                5000
findElementByXPath  .//*[@id='lst-ib']
click
sendkeys            "Wall-e automation robot arguments are args[1] args[2] args[3] args[4]"
sendkeyEnter
wait                10000
```

## 5.3. Get and set webElement's values, variable and expression calculations
Sometimes you will need to get and set values into webElement of a webPage, store these values into variables and make some expression calculations with these values

```
get                             "http://eliasnogueira.com/arquivos_blog/selenium/desafio/1desafio/"
wait                            5000
findElementByXPath              .//*[@id='number1']
setExpressionToVariable         lastWebElementFound             number1	
findElementByXPath              .//*[@id='number2']
setExpressionToVariable         lastWebElementFound             number2
setExpressionToVariable         "number1 + number2"             sumResult	
findElementByXPath              .//*[@name='soma']
sendKeys                        sumResult
wait                            15000
findElementByXPath              .//*[@name='submit']
click
```

## 5.4. Manipulating table structures based on Excel files

* Supose de following Excel file spreadsheet `example-1.xlsx`. First line of spreadsheet are the titles of columns of your table. Some columns must have no title, but they can't be addressed for table structures. All others line until bottom of spreadsheet are rows of this table. You can iterate rows and get data from columns titles.

```excel-file
+----------------------------------------------------------------------------------------------------------------------------+
|   A   |   B   |   C       |   D        |   E     |   F   |          G        |            H                                |
+-------+-------+-----------+------------+---------+-------+-------------------+---------------------------------------------+
| Col_A | Col_B | Col_C     | Col_D      |         | Col_F | COL_G             | COL_H                                       |
| 1     | Um    | 1 - Um    | 25/04/1969 | R$ 0,01 |       | Wall-e English    | https://www.youtube.com/watch?v=ZisWjdjs-gM |
| 2     | Dois  | 2 - Dois  | 21/07/1938 | R$ 2,02 | primo | Wall-e Portuguese | https://www.youtube.com/watch?v=m5_lIuBXKWk |
| 3     | Treis | 3 - Treis | 11/09/2011 | R$ 5,02 | primo | Wall-e Automation | https://github.com/josemarsilva/wall-e/wiki |
+----------------------------------------------------------------------------------------------------------------------------+
```

* Script
```wall-e script
#
# Load into table structure 'tableFromXlsx' MS-Excel worksheet ...
#
loadTableFrom       tableFromXlsx      excel        ./doc/examples/example-1.xlsx
#
# Iterate table sctructure ...
#
forEach tableFromXlsx
	get		http://www.google.com.br
	wait	10000
EndForEach
```



# 6. License
GNU GENERAL PUBLIC LICENSE - Version 3.0

# 7. References
* [[http://toolsqa.com/selenium-ide-tutorial/]]
* [[http://www.software-testing-tutorials-automation.com/p/selenium-webdriver.html]]
* [[https://github.com/uklimaschewski/EvalEx]]
* [[https://www.tutorialspoint.com/compiler_design/compiler_design_symbol_table.htm]]
* [[https://www.guru99.com/xpath-selenium.html]]
* [[http://eliasnogueira.com/arquivos_blog/selenium/desafio/]]

----

![wall-e](https://github.com/josemarsilva/wall-e/blob/master/doc/images/wall-e.jpg) 
