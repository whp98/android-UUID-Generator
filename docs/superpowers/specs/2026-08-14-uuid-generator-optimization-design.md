# Design Spec: Modernizing UUID Generator (Material You / MD3 & Target SDK 36)

## 1. Project Goal
- Upgrade Android target SDK and compile SDK from 35 to **36** (Android 16).
- Completely overhaul the UI/UX into a modern **Material 3 (Material You)** dynamic aesthetic.
- Enhance feature set: Single UUID generation, Batch generation (1~100), Configurable formats (UUID v4 / Time-based v7, Uppercase, Remove Hyphens, Namespace/Name UUID v3/v5), History logging, One-touch Copy & Share with Material Feedback (Haptic & Toast/Snackbar).

## 2. Target SDK Upgrade & Configuration
- **Compile SDK**: 36
- **Target SDK**: 36
- **Min SDK**: 24
- **Dependencies Update**:
  - `com.google.android.material:material`: Latest Material 3 components
  - `androidx.core:core-ktx`
  - `androidx.appcompat:appcompat`
  - `androidx.recyclerview:recyclerview`
  - `androidx.viewpager2:viewpager2`
  - Lifecycle, Navigation, etc.

## 3. UI/UX Architecture & Design (Material 3)
- **Theme**: Upgrade base theme from `Theme.MaterialComponents.DayNight.DarkActionBar` to `Theme.Material3.DayNight.NoActionBar` (Edge-to-Edge with Material You Dynamic Colors support).
- **Primary Screen (`fragment_uuid.xml`) Layout**:
  1. **Top Display Card (`MaterialCardView`)**:
     - Large Monospace Typography for UUID display.
     - Single-tap Copy Button with Haptic feedback.
     - Quick Action Bar: Generate New, Share, Clear/Reset.
  2. **Configuration Card (`MaterialCardView`)**:
     - **Version Selection**: ChipGroup (`UUID v4 (Random)`, `UUID v7 (Time-ordered)`).
     - **Format Options**: Switches / Chips for `大写 (UPPERCASE)` and `不含连字符 (Remove -)`.
     - **Batch Count**: Slider / Number Picker for count (1 to 50/100).
  3. **Batch / History Results Card (`RecyclerView`)**:
     - Modern Card list with quick copy icon per item, batch copy all button.
     - Smooth transitions and empty-state placeholders.

## 4. Business Logic & Utilities
- **`UuidGenerator` Engine**:
  - Standard UUID v4 via `java.util.UUID.randomUUID()`
  - UUID v7 (Unix Epoch timestamp prefix + secure random bits)
  - String formatting according to uppercase and hyphen configuration options.
- **`HistoryManager` / ViewModel State**:
  - LiveData / StateFlow managing current UUID list, config state, and history items.
- **Copy & Share Integration**:
  - `ClipboardManager` with Android 13+ standard feedback consideration.
  - Native Android Intent for Sharing generated UUIDs.

## 5. Verification Plan
- Build project using `./gradlew assembleDebug`.
- Verify Kotlin compile without errors against SDK 36.
- Run UI tests / check generated APK attributes.
