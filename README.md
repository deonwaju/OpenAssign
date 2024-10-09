# NewsFeed App

A modern Android application that provides a news feed and detailed views of news articles. Built using **Jetpack Compose** and adhering to **Clean Architecture** principles, this app showcases best practices in Android development, including dependency injection, modularization, and unit testing.

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
  - [Modules](#modules)
  - [Presentation Layer](#presentation-layer)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Features

- Display a list of news articles with an infinite scrolling feature.
- View detailed information about each news article.
- Search functionality to find specific news articles.
- Smooth UI interactions built with Jetpack Compose.

## Architecture

This app follows the **Clean Architecture** pattern, which separates concerns into different layers:

### Modules

The application is organized into the following modules:

1. **Core Module**: Contains common utilities, base classes, and shared resources used across the app.
2. **Network Module**: Implements network functionalities using **Retrofit** to fetch news data from APIs.
3. **Feature Module**: Houses the implementation of specific features, such as the news feed and article details.
4. **App Module**: The entry point of the application, which ties all modules together and provides DI setup.

### Presentation Layer

The presentation layer uses **MVVM** (Model-View-ViewModel) combined with **MVI** (Model-View-Intent) principles for better state management:

- **ViewModel**: Manages UI-related data in a lifecycle-conscious way.
- **State**: Represents the UI state for the news feed and article detail screens.

## Technologies Used

- **Jetpack Compose**: For building native UI.
- **Coroutines**: For asynchronous programming.
- **Retrofit**: For network calls.
- **Dagger/Hilt**: For dependency injection.
- **Unit Tests**: Ensuring code reliability and correctness.

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/deonwaju/OpenAssign.git



