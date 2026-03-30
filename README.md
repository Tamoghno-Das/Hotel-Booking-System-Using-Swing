# Hotel Booking System Using Swing

Java Swing desktop app for basic hotel room booking, cancellation, room listing, bill viewing, and AI-based price suggestion.

## What this project includes

- Java + Maven project structure
- Swing UI in `src/main/java/com/example/swing/MainDashboard.java`
- Core booking logic in `src/main/java/com/example/service/HotelService.java`
- Simple entities (`Hotel`, `Room`, `Bill`) and custom exception handling
- Ollama HTTP call for AI room price prediction (fallback pricing if unavailable)

## Prerequisites

- JDK **25** (current `pom.xml` uses source/target 25)
- Maven 3.9+
- Windows PowerShell (examples below), or any shell with equivalent commands
- Optional: Ollama running locally at `http://localhost:11434` for AI pricing

## Build

From project root:

```powershell
mvn clean compile
```

## Run from IntelliJ IDEA

1. Open the project as a Maven project.
2. Ensure Project SDK is set to JDK 25.
3. Open `src/main/java/com/example/swing/MainDashboard.java`.
4. Run the `main` method from the editor gutter.

## UI actions available

- Book Room
- Cancel Booking
- View Rooms
- Count Available
- View Bill
- AI Price

## Known setup issue

If you see this Maven error:

`invalid target release: 25`

it means your active JDK is lower than 25.

### Fix options

1. Install/use JDK 25 and set `JAVA_HOME` to that JDK.
2. Or change compiler level in `pom.xml` to your installed JDK version:

```xml
<maven.compiler.source>21</maven.compiler.source>
<maven.compiler.target>21</maven.compiler.target>
```

Then rebuild:

```powershell
mvn clean compile
```

## Notes

- AI pricing depends on the local Ollama endpoint. If unavailable, default pricing is used by room type.
- There are currently no automated tests under `src/test/java`.

