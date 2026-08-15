# UUID Generator Optimization Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Upgrade target/compile SDK to 36, overhaul the UI/UX with Material You (MD3) dynamic cards, and add UUID v7 / format configuration / batch generation features.

**Architecture:** Model-View-ViewModel (MVVM) architecture with ViewBinding, Material 3 components, dynamic state handling with Kotlin StateFlow/LiveData, and extended UUID formatting engine.

**Tech Stack:** Kotlin, Android SDK 36, Material Design 3 (`com.google.android.material`), AndroidX ViewBinding, ConstraintLayout, ViewModel & LiveData.

---

### Task 1: Upgrade SDK and Gradle Configurations

**Files:**
- Modify: `app/build.gradle.kts`
- Modify: `gradle/libs.versions.toml` (if present) or `app/build.gradle.kts`

- [ ] **Step 1: Update targetSdk and compileSdk in app/build.gradle.kts**

Set `compileSdk = 36` and `targetSdk = 36`.

- [ ] **Step 2: Verify project compilation**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit changes**

```bash
git add app/build.gradle.kts
git commit -m "build: upgrade compileSdk and targetSdk to 36"
```

---

### Task 2: Implement Extended UuidEngine Logic & Unit Tests

**Files:**
- Create: `app/src/main/java/work/jsfr/uuidgenerator/utils/UuidEngine.kt`
- Create: `app/src/test/java/work/jsfr/uuidgenerator/utils/UuidEngineTest.kt`

- [ ] **Step 1: Write unit tests for UuidEngine**

Write test methods verifying:
1. `generateV4(isUppercase: Boolean, removeHyphens: Boolean)`
2. `generateV7(isUppercase: Boolean, removeHyphens: Boolean)`
3. `generateBatch(count: Int, version: String, isUppercase: Boolean, removeHyphens: Boolean)`

- [ ] **Step 2: Run tests to ensure failure before implementation**

Run: `./gradlew test`
Expected: FAIL (Class UuidEngine not found)

- [ ] **Step 3: Implement UuidEngine**

Implement UUID v4 and UUID v7 (Epoch timestamp + random bits) generation with uppercase and hyphen-removal options.

- [ ] **Step 4: Run unit tests to verify pass**

Run: `./gradlew test`
Expected: PASS

- [ ] **Step 5: Commit changes**

```bash
git add app/src/main/java/work/jsfr/uuidgenerator/utils/UuidEngine.kt app/src/test/java/work/jsfr/uuidgenerator/utils/UuidEngineTest.kt
git commit -m "feat: implement UuidEngine with v4 and v7 support and formatting options"
```

---

### Task 3: Upgrade Themes and Material 3 Color Styles

**Files:**
- Modify: `app/src/main/res/values/themes.xml`
- Modify: `app/src/main/res/values-night/themes.xml` (if present)
- Modify: `app/src/main/res/values/colors.xml`

- [ ] **Step 1: Update themes.xml to use Material 3 NoActionBar parent**

Set parent theme to `Theme.Material3.DayNight.NoActionBar`.

- [ ] **Step 2: Add Material You theme color attributes**

Define primary, container, surface, and background colors conforming to MD3 palette.

- [ ] **Step 3: Verify build**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: Commit changes**

```bash
git add app/src/main/res/values/themes.xml app/src/main/res/values/colors.xml
git commit -m "style: upgrade app theme to Material 3 NoActionBar"
```

---

### Task 4: Re-design UuidFragment UI & Layout (fragment_uuid.xml)

**Files:**
- Modify: `app/src/main/res/layout/fragment_uuid.xml`
- Create: `app/src/main/res/layout/item_uuid_batch.xml`
- Create: `app/src/main/java/work/jsfr/uuidgenerator/ui/uuid/UuidListAdapter.kt`

- [ ] **Step 1: Create item_uuid_batch.xml layout**

Item card for displaying generated UUIDs in list view with a copy icon button.

- [ ] **Step 2: Redesign fragment_uuid.xml**

Build layout with MaterialCardViews for Display, Configuration (Chips, Switches, Count Slider), and RecyclerView for batch/history results.

- [ ] **Step 3: Implement UuidListAdapter**

Adapter with ViewHolder for binding UUID strings to `item_uuid_batch.xml` with click-to-copy callbacks.

- [ ] **Step 4: Commit changes**

```bash
git add app/src/main/res/layout/ app/src/main/java/work/jsfr/uuidgenerator/ui/uuid/UuidListAdapter.kt
git commit -m "ui: implement Material 3 card layout and batch RecyclerView adapter"
```

---

### Task 5: Update ViewModel & Wire UuidFragment UI Interactions

**Files:**
- Modify: `app/src/main/java/work/jsfr/uuidgenerator/ui/uuid/UuidViewModel.kt`
- Modify: `app/src/main/java/work/jsfr/uuidgenerator/ui/uuid/UuidFragment.kt`

- [ ] **Step 1: Extend UuidViewModel state**

Add LiveData/StateFlow for current single UUID, list of batch UUIDs, selected version (v4/v7), uppercase option, remove hyphens option, and batch count.

- [ ] **Step 2: Wire UuidFragment controls and click listeners**

Connect ChipGroup, Switches, Slider, Copy buttons, and Share actions to ViewModel. Use `UiUtils` or `ClipboardManager` with haptic feedback/Toast on copy.

- [ ] **Step 3: Build and test full application**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: Commit changes**

```bash
git add app/src/main/java/work/jsfr/uuidgenerator/ui/uuid/
git commit -m "feat: connect UuidFragment and ViewModel with full batch generation and formatting capabilities"
```

---

### Task 6: Final Verification & Build Verification

**Files:**
- None (Verification step)

- [ ] **Step 1: Run full test suite and build release/debug APK**

Run: `./gradlew test assembleDebug`
Expected: BUILD SUCCESSFUL, all unit tests pass.

- [ ] **Step 2: Commit any final cleanup**

```bash
git add .
git commit -m "chore: final verification and build cleanup"
```
