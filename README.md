# GeoRemind
### - Georemind is an Android app developed with Kotlin that reminds you of your tasks based on your <B>*location and alarm preferences</B>*. You can also write notes.
### -<B> *Jetpack Compose* </B> was used in the ui of the application. 


## MVVM + Clean Architecture
I used *<B>Clean Architectural</B>* guidline together with *<B>MVVM</B>* Architectural Desing Patten

# App Presentation:

https://github.com/OzanKayikci/GeoRemind_Kotlin-Compose/assets/48061680/8ddbe34a-f4e3-4628-8be3-ac4c037bd879


# Folder Structure Preview
``` kotlin
 ├── com.laivinieks.georemind
 |    ├── featureName_feature
 |    |   ├── data
 |    |   |   ├── data_source
 |    |   |   |   └── Dao Object
 |    |   |   ├── repository
 |    |   |   |   └── Repository İmplementations
 |    |   |   └── services(if needed)
 |    |   |       └── Service Classes
 |    |   |   
 |    |   ├── domaion
 |    |   |   ├── modal
 |    |   |   |   └── Modals of the feature
 |    |   |   ├── repository
 |    |   |   |   └── Interfaces of repositories
 |    |   |   └── use_case
 |    |   |   |   ├── AddExampleUseCase
 |    |   |   |   ├── GetExampleuseCase
 |    |   |   |   └── DeleteExampleUseCase
 |    |   |   └── utils
 |    |   |       └── such as Converters, Constants
 |    |   | 
 |    |   └── presentation
 |    |       ├── example_feature_screen
 |    |       |   ├── Feature Screen ViewModel
 |    |       |   ├── Feature Screen Events
 |    |       |   └── Feature Screen Compose
 |    |       ├── BroadCast Recievers (if needed)
 |    |       └── Notification Helper (if needed)
 |    |   
 └────└── core (for core operations as db implementations, Repositories if you need for all Features. same structure)
          ├── data 
          ├── domaion
          └── presentation
```

# Libraries:
- jetpack compose
- dagger hilt (also hilt-navigation-compose)
- compose navigation
- coroutines ( to manage asynchronous tasks and threads)
- room (to store reminders and notes)
- google maps services (also maps for compose)
- geofence (to monitor user-entered location)
- accompanist(for permissions)
- lifecycle(for compose)
- datastore
- material3

# Components:
- Compose
- Broadcast Reciever(to trigger notifications)
- Alarm manager(to access system alarm service)
- Pending intent (to defer an action representation)
- Notification manager
- Flow (for dao)
- NavHostController (navigate between composes)








