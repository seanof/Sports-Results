# Project Description
Sports results application built using MVVM, Jetpack Compose, Ktor and Hilt.

## Dependencies
Beyond the default applied dependencies when starting a new project in Android studio, I included the following;

- implementation(libs.hilt.android) - Hilt Android for dependency injection.
- ksp(libs.hilt.android.compiler) - Hilt compiler KSP support.
- coreLibraryDesugaring(libs.desugar.jdk.libs) - Core Library Desugaring, allows usage of LocalDateTime/DateTimeFormatter without worrying about Android version compat.
- implementation(libs.ktor.client.android) - Ktor Android for network client.
- implementation(libs.ktor.client.core)
- implementation(libs.ktor.client.serialization) - Ktor Android serialiser to convert response data into object model.
- implementation(libs.ktor.client.content.negotiation) - Ktor content negotiator for serialisation.
- testImplementation (libs.mockito.core) - Mockito for mocking in unit tests.
- testImplementation (libs.mockito.inline)
- testImplementation (libs.mockito.android)
- testImplementation(libs.kotlinx.coroutines.test) - Enables tests to run on UnconfinedTestDispatcher.

For data/remote directory, I referenced the basic structure from a personal project (though this is not yet committed to GitHub.)

#### What portion of the code do you feel is most important for us to review more closely?

- SportsResultsViewModel - for viewmodel implementation.
- ApiServiceImpl - for Ktor implementation.
- MainActivity - for UI in Compose.

#### If you were to continue to develop the project, what are things you would add, change, or improve?
- Add caching + repository pattern for saving data locally to Room database.
- Espresso or Maestro UI tests.
- Simplifying/extracting Composables in MainActivity.

### Anything else you want us to know while we review your project?
- I opted for regular classes over data classes to represent the sports result model(s) in order to keep things simple and readable, and a base open class to allow flattening of the different result types into a single list, and for ease of implementing further types of sports results. 