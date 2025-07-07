# Mobile Challenge - Ualá - v0.8
First of all, I would like to thank Ualá for this great opportunity! It has been fun to develop this solution.
I hope you like the end result!

Instructions to compile the project:

1- Clone it

2- Open it with Android Studio and compile it.

## Technical Info
This app respects the principles of the Clean Architecture, so it is organized in 3 well-defined layers: Presentation, Domain, and Data Layer, built around a MVI design pattern.

![image](https://github.com/ivanbarto/kotlin-repos/assets/66323499/2923aaf4-ba7d-4b07-9c54-a89aa4b011c7)

<img width="607" alt="image" src="https://github.com/user-attachments/assets/2d55b605-513a-40ea-b5d0-a1ac2efed5d6" />

A modularization strategy based on features was chosen. 

![image](https://github.com/user-attachments/assets/c8be5a05-3942-488c-8292-5e3a265472cb)

Although in this case we only have one feature (cities), following a feature+layer strategy allows a higher degree of granularity than when following a feature-only strategy (where, within the module, the three layers coexist). This higher granularity allows further decoupling of the modules, which improves maintainability, scalability, testability and build times.

Is this modularization strategy needed for this project? The answer is obviously NO, it makes no sense, since it is a small project. Even so, I thought it was a good idea to show this concept.

The app has 3 android modules:
- app: city-oriented, it contains everything related to the presentation layer.
- domain-cities: city-oriented, it contains everything related to the domain layer.
- data-cities: city-oriented, it contains everything related to the data layer.


Also, the app has support for Dependency Injection pattern, using Koin library, which is very fast to implement and light-weight.

The main challenge of this test is to find a proper solution to avoid loading +200K items in memory/storage. This items count is MASSIVE for a `List` object. One could be tempted to directly download the results and display them in a list, which is possible, but keeping a +200K items list in memory is high memory-consuming.

The strategy I've chosen is to paginate the results, so that we can load them lazily and in a paginated manner, but this requires some extra work:
- Perfom the fetch requests for all the Cities
- Simulate a paging service: here, I have divided the list of +200K items into sublists of 40 items, storing them in indexed form. As the user scrolls through the list of cities, more items are loaded via the saved index.
- Store the pages in a local database to simulate a paginated API (and also provide offline support).
- Use a `PagingSource` implementation to fetch the paginated cities from the local database and implemented it directly on a `LazyColumn` composable.

This way, we save memory because we don't keep a property with +200K items in memory anymore and also the composable list has less items loaded.

## Use Cases
The user can perform the following actions:
- Explore the list of cities and check the city location
  
![1-1](https://github.com/user-attachments/assets/375d50eb-5ca1-42fc-848f-2e1f4cb34543)
![1-2](https://github.com/user-attachments/assets/163b2c3b-bffb-4489-8ddc-caaab3a32135)

- Save a city as favorite
  
![2](https://github.com/user-attachments/assets/1fb5a32a-9511-45f3-bd2e-b08facf78662)

  
- Filter cities by a combination of favorite and prefixes
  
![3](https://github.com/user-attachments/assets/b4867600-40d1-4fac-8a9b-d85d9638dcc5)
 
- Check the city details
  
![4](https://github.com/user-attachments/assets/af5d1b50-da92-4dca-8cce-8aafea397131)

We also have support for offline mode! 

![5](https://github.com/user-attachments/assets/6120d595-9add-4cbe-986b-6a10929226cd)
![5-1](https://github.com/user-attachments/assets/e8a64e28-7111-485d-ad62-202ae74b0da2)


## Filter Details
About the `filter by prefix` use case, first we need to think on the following:
- Filtering should be done directly in the database (with a SQL sentence), since we want to filter ALL the results, not just the currently displayed on the list.
- Also, the results should be paginated (if we filter by a single letter, we will have thousands of results).

To do this, I've follow the next strategy:
- First, filter all the results on the main table, taking into account that this can give us thousands as well as a single result.
- Paginate all the results as when calling the API and storing them in an auxiliary table.
- When filters are active (both favorite/prefix), fetch the results from the auxiliary table; otherwise, fetch the results from the general table.

Also, the results are alphabetically ordered (all by using SQL).
  
## Test

I've used Koin-Test, MockK, Espresso, Compose-Ui-Test and JUnit to support test implementations. All the tests could be found in each module in both `androidTest` and `test` packages.

In the Data module, we have tests for:
- API communication
- Database transactions
- Repository logic (filter by favorite and by prefix)
- DI modules validation.
<img width="715" alt="image" src="https://github.com/user-attachments/assets/6b9a81f7-73a0-4e7c-a89e-4f10e381cc50" />
<img width="283" alt="image" src="https://github.com/user-attachments/assets/c524ec42-e7e7-4215-b240-6e9e33e68c68" />
<img width="277" alt="image" src="https://github.com/user-attachments/assets/35174a4f-f8df-433e-a57e-de339d0e6a06" />



In the Domain module, we only have DI modules validation., since the use cases are equivalent to the repository methods.
<img width="281" alt="image" src="https://github.com/user-attachments/assets/1b2b3a7f-a6aa-495d-8ed0-bd6edfae7830" />



In the App (presentation) module, we have tests for the following UI interactions:
- On Portrait mode, map is hidden
- On screen retotation, map is displayed
- When cities screen show up, cities are loaded
- When clicking on city details button, navigation to city details screen is performed
- When filter by prefix, the results are updated.
- When filter by favorites, the results are updated.
- Last but not less, DI modules validation.
<img width="723" alt="image" src="https://github.com/user-attachments/assets/12e0e6f2-9e3c-4f94-b26b-9378878cd4d3" />
<img width="281" alt="image" src="https://github.com/user-attachments/assets/74be3615-99f1-449b-9960-0951efeb81f7" />


## Improvement Opportunities

I preferred to focus on correct modularization, test implementations and solving the problem of the large amount of results returned by the remote JSON file and choose to improve performance. However:
- The UI design could be updated with a complete compose theme (for both dark/light modes), and probably extra animations. Also, the component design could be improved.
- Also, the City Details Screen could include information about the city or country to which it belongs through other API calls that provide information.


