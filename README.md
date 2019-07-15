## article-viewer

Authorized req https://api.elevio-staging.com/v1/search/en?query=article    
Returns error: An unexpected error has occurred. Please check the system status or submit a ticket by quoting error id d9e4c118-9b8f-4010-bc26-981de0c22689.


How to run:
 1. Add auth key, token to apiAuth section of src\main\resources\application.conf 
 2. Build an app (I used IntelliJ IDEA) and run AppIO

Tech used: Cats Effect, Sttp, Circe, Logback, ScalaFmt, Tagless Final style.

TODO: Figure out search request error. UI, tests. 