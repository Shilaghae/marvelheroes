
#  Marvel Heroes

The application show a list of heroes, each item of the list can be clicked and the user can look at the details of the hero and decide to hire or fire it.
I use a MVVM architecture with ViewModel/LiveData, and I make sure there is a clear separation between the Api Model/Domain Model and Ui Model.

The pattern here is, I have a ViewModel that uses interactors (in other cases called UseCases).
The interactors are responsible to interact with the repositories and transform the domain model into the UI Model, while the repositories are responsible to interact with the Data Sources that in our case can either be API or Database and transform the API/DB Model into the Domain Model.
So we have API Model -> Domain Model through Mappers and Domain Model -> UI Model through Convertors.
When in offline mode the user can still see the heroes saved in the database.
I saved the Marvel API key into a .so library so normally you shouldn't see the c++ code in the github repo but only the .so library. I added it with the only purpose to show my implementation.

With this separation every part of the code can be tested; unit test will need to cover Convertors/Mappers/Repositories/Interactors and ViewModels.

![Alt text] (https://github.com/Shilaghae/marvelheroes/blob/master/yourheroes/images/hero_list.png)

![Alt text] (https://github.com/Shilaghae/marvelheroes/blob/master/yourheroes/images/hero_details.png)
