# FetchList

FetchList is an Android application that fetches data from a [remote JSON API](https://fetch-hiring.s3.amazonaws.com/hiring.json) and displays it. 
It provides users with a convenient way to explore and search through a collection of lists and items.

## Features

- Fetches data from a remote JSON API endpoint (https://fetch-hiring.s3.amazonaws.com/hiring.json).
- Displays lists and items in a table format for easy readability.
- Sorts lists and items in a sorted order based on list ID and item name.
- Provides a search bar for quick and efficient item lookup.
- Offers a user-friendly interface for seamless navigation and exploration.

## Technical Details

- **Language:** Kotlin
- **Minimum SDK Version:** 21 (Android 5.0 Lollipop)
- **Libraries Used:**
  - androidx.appcompat:appcompat
  - androidx.recyclerview:recyclerview
  - kotlinx.coroutines:coroutines-android
- **API Endpoint:** [https://fetch-hiring.s3.amazonaws.com/hiring.json](https://fetch-hiring.s3.amazonaws.com/hiring.json)
- It demonstrates the use of coroutines for asynchronous operations, RecyclerView for displaying lists of data, and SearchView for filtering items.
## Screenshots

![](https://github.com/sohamvsonar/FetchList/blob/master/images/SS.jpg)

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/sohamvsonar/FetchList.git

2. Open the project in Android Studio.
3. Build and run the application on an emulator or physical device.
4. All the dependencies are already added in the gradle file.
5. If you face any issue while running the app:
   go to build-> clean project
   then build-> rebuild project 
## Usage

1. Upon launching the app, you will be presented with a list of items organized in a table format.
2. Use the search bar located at the top of the screen to search for specific items.
3. Scroll through the lists and items to explore the entire collection.
4. Use the organized and sorted presentation of lists and items for efficient browsing.




