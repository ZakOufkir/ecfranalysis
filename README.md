This is a project to fetch data from ecfr government website using APIs. we fetch data by using an API which takes two parameters : 
    1. title number 
    2. date of amendment or modification in the format of YYYY-MM-DD
After this , I store this data into a table db h2 . then I can reload it again using my custom API to do some analysis on it such as words counting in an agency and so on.

Example:
Procedure goes: First use the API http://localhost:8080/ecfr/fetch. You will see the page which contains the fetched data. 
use Title number 6 and date of amendment is: 2025-06-09. You will see fetched data in HTML format.
Invoke the second API to do some anaylysis http://localhost:8080/ecfr/6/2025-06-09/analysis
