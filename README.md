# ReignHackerNews
No extra actions needed to run the demo app.

## Notes:
- In order to retreive sorted data, a singleton to generate an incremental id was created
- There were no Unit tests created, as other aspects of the app were given priority

## Design decisions
- Did not include use cases as it was deemed unnecessary, in a larger app, use cases to connect the ViewModel with the Repository would be better
- DAOs and the Retrofit interface are injected into the Repository, in a larger app, a gateway for online and another for offline operations would be better.
- The library Koin was used as the "Dependency injection" framework as it was deemed fit for the scenario.

## Possible Improvements
- Add unit tests
- Database normalization to join deleted IDs with stored posts
- Better handling of paging query parameter, (ex: calculating the number of posts needed to fill the page before requesting)
