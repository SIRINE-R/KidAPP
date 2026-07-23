# KidApp 

**KidApp** is a modern Android application designed to provide a safe and controlled environment for children to discover and watch content. The app acts as a bridge between YouTube and the end user, utilizing AI-driven analysis to ensure all content is appropriate before it reaches the child's screen.

> [!NOTE]
> This project is currently a **Work in Progress**.

## 🚀 Project Overview

The primary goal of KidApp is to leverage **Artificial Intelligence** to automatically vet YouTube videos. Instead of relying solely on manual reporting or broad filters, the app's backend analyzes video metadata, transcripts, and visual content to determine if a video is truly child-safe.

## ✨ Key Features (Implemented & In Progress)

- **Parental Controls**: Secure Sign-Up and Login for parents to manage their dashboard.
- **Child Profiles**: Support for multiple child profiles, each with customizable icons and settings.
- **Verification Flow**: A dedicated "Parental Lock" verification screen to prevent children from accessing admin settings.
- **Kid-Friendly Interface**: A simple, colorful, and intuitive UI designed specifically for young users.
- **Watch & Search History**: Tracking what children are watching and searching for (Parental View).
- **Safe Video Feed**: (In Progress) A curated feed of AI-verified videos.

## 🛠️ Tools & Technologies

The app is built using the latest Android development standards:

- **Kotlin**: The primary language for robust and concise code.
- **Jetpack Compose**: Used for building a reactive and modern declarative UI.
- **Dagger Hilt**: Handles Dependency Injection (DI) to keep the codebase modular and testable.
- **Ktor**: A lightweight networking library used for asynchronous API calls to the backend.
- **Kotlin Serialization**: Efficiently handles JSON data parsing.
- **Navigation Compose**: Manages the app's flow and backstack.
- **Material 3**: The latest design system for a polished look and feel.

## 🏗️ Architecture

The project follows **Clean Architecture** principles, separating the code into:
- **Presentation Layer**: Compose UI and ViewModels.
- **Domain Layer**: Business logic, Models, and Use Cases.
- **Data Layer**: Repositories and Remote Data Sources (Ktor API).

## 📅 What's Been Done So Far

1. **Architecture Setup**: Established the Hilt dependency graph and the multi-layered project structure.
2. **Authentication Flow**: Completed the Parent Sign-Up and Login screens with input validation.
3. **Profile Management**: Implemented the ability to add, update, and delete child profiles.
4. **UI Components**: Created a library of reusable "KidApp" components (Buttons, TextFields, Cards).
5. **Parental Verification**: Developed a secure flow that requires parent credentials to access sensitive areas.
6. **Navigation**: Integrated a robust navigation system supporting dynamic arguments (e.g., passing profile IDs).

---

