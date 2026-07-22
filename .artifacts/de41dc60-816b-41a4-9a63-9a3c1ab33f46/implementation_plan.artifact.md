# Sync Profile Icon with Database Data in SettingsScreen

We will update the `SettingsViewModel` and `SettingsScreen` so that when a child is selected from the dropdown, their current icon from the database is displayed as the default profile image.

## Proposed Changes

### [Presentation Layer]

#### [MODIFY] [SettingsViewModel.kt](file:///C:/Users/Seryie/AndroidStudioProjects/KidApp/app/src/main/java/com/example/kidapp/presentation/screens/settings/SettingsViewModel.kt)
- Add a `keyToDrawableMap` to map string icon keys (e.g., "panda") back to drawable resource IDs (e.g., `R.drawable.panda`).
- Add a helper function `getDrawableRes(iconKey: String): Int` to handle this mapping safely.

#### [MODIFY] [SettingsScreen.kt](file:///C:/Users/Seryie/AndroidStudioProjects/KidApp/app/src/main/java/com/example/kidapp/presentation/screens/settings/SettingsScreen.kt)
- Update the `DropdownMenuItem` `onClick` to call `settingsViewModel.onSelectedIconChange()` with the correctly mapped drawable ID from the selected child's `iconProfile`.
- Fix the `ProfileImage` component to use the `selectedIcon` state from the ViewModel.

## Verification Plan

### Manual Verification
- Deploy the app and navigate to Settings.
- Open the "Kid's Settings" section.
- Select a child from the dropdown.
- Verify that the Profile Image automatically updates to show that child's current icon from the database.
- Click the icon to change it, and verify that the "Update" button sends the new selection.
