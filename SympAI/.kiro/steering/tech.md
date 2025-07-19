# SympAI Technical Stack

## Core Technologies

- **Language**: Kotlin
- **Platform**: Android
- **Minimum SDK**: 26 (Android 8.0 Oreo)
- **Target SDK**: 36
- **Build System**: Gradle with Kotlin DSL (build.gradle.kts)
- **AI Integration**: Gemini API for LLM-based symptom analysis

## Key Dependencies

- AndroidX Core KTX
- AndroidX AppCompat
- Material Design Components
- AndroidX Activity
- AndroidX ConstraintLayout
- JUnit for testing
- Espresso for UI testing

## Development Environment

- Java Version: 11
- Kotlin JVM Target: 11

## Common Commands

### Building the Project
```bash
./gradlew build
```

### Running Tests
```bash
./gradlew test
```

### Installing Debug Build
```bash
./gradlew installDebug
```

### Generating Release APK
```bash
./gradlew assembleRelease
```

### Clean Build
```bash
./gradlew clean build
```

## API Integration

The application integrates with Google's Gemini API for natural language processing of symptom descriptions. API keys should be stored securely and never committed to version control.