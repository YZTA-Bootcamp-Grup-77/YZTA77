# SympAI Project Structure

## Directory Organization

```
SympAI/
├── app/                      # Main application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/         # Kotlin source files
│   │   │   │   └── com/swanky/sympai/
│   │   │   ├── res/          # Android resources
│   │   │   └── AndroidManifest.xml
│   │   ├── test/             # Unit tests
│   │   └── androidTest/      # Instrumentation tests
│   ├── build.gradle.kts      # App-level build configuration
│   └── proguard-rules.pro    # ProGuard rules
├── gradle/                   # Gradle wrapper files
├── .gradle/                  # Gradle cache (not in version control)
├── .idea/                    # IDE settings (not in version control)
├── build.gradle.kts          # Project-level build configuration
├── settings.gradle.kts       # Project settings
└── README.md                 # Project documentation
```

## Architecture Guidelines

The application follows a modern Android architecture pattern with the following components:

### Recommended Package Structure

```
com.swanky.sympai/
├── data/                     # Data layer
│   ├── api/                  # API services (Gemini API)
│   ├── models/               # Data models
│   └── repositories/         # Data repositories
├── di/                       # Dependency injection
├── ui/                       # UI layer
│   ├── common/               # Shared UI components
│   ├── home/                 # Home screen
│   ├── analysis/             # Symptom analysis screen
│   └── results/              # Results screen
├── utils/                    # Utility classes
└── MainActivity.kt           # Main entry point
```

## Coding Conventions

1. **Package Structure**: Follow feature-based packaging for better modularity
2. **Naming Conventions**:
   - Classes: PascalCase (e.g., `SymptomAnalyzer`)
   - Functions/Variables: camelCase (e.g., `analyzeSymptoms()`)
   - Constants: UPPER_SNAKE_CASE (e.g., `API_KEY`)
   - Layout files: snake_case (e.g., `activity_main.xml`)
3. **Resource Naming**:
   - Layouts: `<component>_<description>.xml` (e.g., `fragment_symptom_input.xml`)
   - Drawables: `<description>_<type>.xml` (e.g., `mic_button.xml`)
   - Strings: Use descriptive keys (e.g., `symptom_input_hint`)

## Testing Strategy

- Unit tests for business logic in the `test/` directory
- UI tests in the `androidTest/` directory
- Follow the AAA pattern (Arrange, Act, Assert) for test structure