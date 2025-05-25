# StayFlow

StayFlow is a modern platform for managing and discovering rental rooms. It offers features for registration, search, creation, and editing of rooms, with support for geolocation and unstructured image storage.

---

## 🧩 Tech Stack

### Backend

- Java 21
- Spring Boot with GraphQL
- Hexagonal Architecture
- PostgreSQL
- MinIO (unstructured data storage)
- Maven
- Docker & Docker Compose

### Android Client

- Jetpack Compose (UI)
- Ktor (networking)
- Coil (image loading & caching)
- Dagger (dependency injection)
- Google Maps SDK

---

## ⚙️ Prerequisites

- Java 21
- Docker & Docker Compose
- Maven 3.8+
- Android Studio Giraffe or later (with Kotlin 2+ support)

---

## 🚀 Getting Started

## Getting Started

### 1. Clone the repository:
```bash
  git clone https://github.com/your-username/stayflow.git
  cd stayflow
```

## 🐘 Backend Setup

### 2. Create your .env file from the example:

```bash
cp .env.example .env
```
Fill in the required environment variables in `.env`.

### 3. Build and start the services with Docker Compose:
```bash
docker compose --env-file=.env up 
```

This will start:
- PostgreSQL database
- Spring Boot backend server
- MinIO for file storage
- Nginx reverse proxy (if configured)

The GraphQL endpoint will be available at [localhost/api/graphql](http://localhost/api/graphql) with Nginx.

## 🤖 Android App Setup
1. Open the Android project in Android Studio (located in the [/App](./App/) folder).
2. In either gradle.properties or local.properties, add the following:
```
API_URL=http://your-domain.com/api/graphql
MAPS_API_KEY=your_google_maps_api_key
```
Make sure the API URL matches the endpoint exposed by your backend or Nginx.

3. Sync and build the project.

---

## 📱 Android Features

- Login & registration
- Browse countries and cities
- Search rooms by location or place
- View room details
- Upload room images
- Google Maps integration
- 

---

## 🔐 Authentication

StayFlow uses JWT-based authentication. Once logged in, the client receives a token, which must be included in subsequent requests to perform authenticated actions like room creation or modification.

---

## 🗂 Key Components

- **Rooms:** Location-based listings with images
- **Cities/Countries:** Hierarchical geographical data
- **Users:** Registration, login, roles
- **Images:** Stored via MinIO and loaded with Coil
- **GraphQL:** Flexible data querying and mutation