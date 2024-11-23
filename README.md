# App1 Documentation

## Overview  
**App1** is an Android application designed to help users learn and explore key Android development components and features through hands-on experience. It covers a variety of essential concepts, such as UI components (e.g., `RecyclerView`, `Buttons`, `TextViews`), data management with Room Database, network requests using Retrofit, user authentication with Firebase, and interacting with system features like Airplane Mode.  

By navigating through different functionalities, users can familiarize themselves with important Android libraries and architecture patterns, including:
- **Room Database** for local storage management.
- **Firebase Authentication** for user login and registration.
- **Retrofit** for making network requests and parsing data.
- **BroadcastReceiver** to handle system events (e.g., Airplane Mode).
- **Services** for background tasks (e.g., playing media).

---

## File: `AirplaneModeChangeReceiver.java`
### Description:
- A `BroadcastReceiver` that listens for changes in airplane mode.
- Displays a toast message to notify the user when airplane mode is toggled on or off.

### Key Methods:
- `onReceive()`: Handles the broadcast and displays the toast based on airplane mode status.
- `isAirplaneModeOn()`: Checks the current airplane mode status.

---

## File: `APIs.java`
### Description:
- This interface defines the API endpoints for interacting with a remote server using Retrofit.
- The `BASE_URL` points to `https://fakestoreapi.com/`, which provides sample product data.
  
### Key Methods:
- `getProducts()`: A GET request to fetch a list of products from the API. It returns a `Call` object that can be used to asynchronously retrieve the data as a `List<ProductResult>`.

---

## File: `AppDatabase.java`
### Description:
- This class defines the Room database for the app, specifically for storing user information in the `UserInfo` entity.
- It uses the `RoomDatabase` class to handle the database connection and operations.

### Key Components:
- **DB_NAME**: The name of the database (`users`).
- **getDB(Context context)**: A singleton method that initializes the database instance if it's not already created. It uses `Room.databaseBuilder` to create the database and allows queries on the main thread (not recommended for production but okay for learning purposes).
- **userDao()**: Provides access to the `UserDao` (Data Access Object) for interacting with the `UserInfo` entity.

### Version:
- The database version is set to 2, and it supports automatic migration with `fallbackToDestructiveMigration` in case of schema changes.

---

## File: `Contacts.java`
### Description:
- This activity displays a list of contacts from the user's phone. It requests permission to read contacts and, once granted, shows the contact names and numbers in a `ListView`.
  
### Key Components:
- **Permissions**: The app checks for the `READ_CONTACTS` permission at runtime. If not granted, it requests the user for permission.
- **displayContacts()**: Queries the contacts content provider (`ContactsContract`) and retrieves names and phone numbers. It then populates a `ListView` with the contact data.
- **onRequestPermissionsResult()**: Handles the result of the permission request. If granted, it calls `displayContacts()`. If denied, it shows a toast message.
  
### UI Elements:
- **Button**: The user can click a button to trigger contact loading.
- **ListView**: Displays the list of contact names and phone numbers.

---

## File: `Login.java`
### Description:
- This activity handles user login using Firebase Authentication. It allows users to log in with an email and password, and saves the credentials locally using `SharedPreferences` and a Room database.
  
### Key Components:
- **Firebase Authentication**: The app uses Firebase's `signInWithEmailAndPassword()` method to authenticate users.
- **SharedPreferences**: The user's email and password are saved and pre-filled in the login fields for future sessions.
- **AppDatabase**: On successful login, the user’s credentials are stored in the local `AppDatabase`.
  
### UI Elements:
- **EditTexts**: For the user to input their email and password.
- **Buttons**:
  - **Login**: Authenticates the user with Firebase and navigates to the next activity (`RvProducts`) upon successful login.
  - **Register**: Navigates to the registration activity (`Register`) if the user doesn’t have an account.

### Logic:
- **onStart()**: Checks if a user is already logged in (using FirebaseAuth) and redirects them to the `RvProducts` activity.
- **onCreate()**: Initializes the FirebaseAuth instance, sets up UI components, and handles login logic.
- **Login Validation**: Ensures that the email and password fields are not empty before attempting login.

---

## File: `MainActivity.java`
### Description:
- This is the main entry point of the app, extending `AppCompatActivity`.
- Currently, the activity does not implement any specific functionality, but it's set up for future UI components and interactions.
  
### Key Components:
- **onCreate()**: The `onCreate()` method is called when the activity is created, but it is currently empty. Future UI elements such as buttons or text fields can be initialized here.

### UI Elements:
- No specific UI elements are implemented yet, but placeholders like `EditText`, `Button`, or others can be added to the layout.

---

## File: `NewMail.java`
### Description:
- This activity allows the user to send an email or a text message using an `EditText` for the email address and message body.
- It also includes buttons to start and stop a media player service (`NewService`), as well as navigate to the `Contacts` activity.

### Key Components:
- **UI Elements**:
  - `EditText`: For inputting the email address (`view_emailId`) and message text (`view_txtMsg`).
  - `Button`: 
    - **Send Message**: Sends the message via an email client or text message.
    - **Contacts Page**: Navigates to the `Contacts` activity.
    - **Start/Stop Service**: Controls the `NewService` (likely a background media service).
  
- **sendMessage()**: 
  - If no email is provided, the message is sent as a plain text using any available email client.
  - If an email is provided, it sends the message directly through Gmail (`com.google.android.gm`).
  
- **Service Control**:
  - `startService()` and `stopService()` are used to manage a background service (`NewService`), toggling it on or off.

### Logic:
- **onCreate()**: Initializes UI components and sets up click listeners for the buttons.
- **sendMessage()**: Checks if an email address is provided; sends the message via `Intent` to either a general email client or specifically to Gmail.

---

## File: `NewService.java`
### Description:
- This service plays the default ringtone in a loop when started and stops the media player when the service is destroyed.

### Key Components:
- **MediaPlayer**: The `MediaPlayer` is used to play the default system ringtone (`Settings.System.DEFAULT_RINGTONE_URI`).
  - The ringtone plays in a loop by setting `setLooping(true)`.
- **onStartCommand()**: This method starts the media player and plays the ringtone on loop when the service is started.
- **onDestroy()**: Stops the media player when the service is destroyed (e.g., when the service is stopped by the user).
- **onBind()**: The method returns `null` because this service is not designed to be bound to any component.

### Logic:
- The service is started via an `Intent`, and it continuously plays the system's default ringtone until stopped.

---

## File: `ProductResult.java`
### Description:
- This class represents a product retrieved from the API. It contains the product's ID, title, and image URL.

### Key Components:
- **Fields**:
  - `id`: The unique identifier of the product.
  - `title`: The name or title of the product.
  - `productImage`: A URL string pointing to the product's image.

- **Annotations**: 
  - `@SerializedName`: Used to map the JSON keys from the API response to the Java fields. For example, the JSON field `"id"` maps to the `id` field in this class.
  
- **Getter Methods**:
  - `getId()`: Returns the product's ID.
  - `getTitle()`: Returns the product's title.
  - `getProductImage()`: Returns the URL of the product image.

---

## File: `Register.java`
### Description:
- This activity allows users to register for an account using Firebase Authentication. The user provides an email and password to create a new account.

### Key Components:
- **Firebase Authentication**: The app uses Firebase's `createUserWithEmailAndPassword()` method to create a new user account.
- **UI Elements**:
  - `EditText`: For entering the email (`view_email`) and password (`view_password`, `view_password2`).
  - `Button`: 
    - **Register**: Registers the user by creating an account with the provided email and password.
    - **Login**: Navigates to the login screen (`Login.class`) for users who already have an account.
  
### Validation:
- **Password Match**: Checks if the password and confirm password fields match. If they don't, it shows a toast with an error.
- **Email and Password Validation**: Ensures both fields are filled in and that the password is at least 6 characters long.
  
### Logic:
- **onCreate()**: Initializes the UI elements, sets click listeners, and handles registration logic.
- **onStart()**: In this case, it is empty, but can be used to check for pre-existing users or handle other initialization tasks.

---

## File: `RetrofitClient.java`
### Description:
- This class provides a singleton instance of Retrofit for making API requests. It sets up Retrofit with the `BASE_URL` and a `GsonConverterFactory` to parse JSON responses.

### Key Components:
- **Singleton Pattern**: Ensures only one instance of `RetrofitClient` is created, providing a centralized point for accessing the API interface (`APIs`).
- **Retrofit Setup**:
  - Initializes Retrofit with a base URL (`APIs.BASE_URL`) and a converter factory (`GsonConverterFactory`) for converting JSON responses into Java objects.
  - Creates an instance of the `APIs` interface for making API calls.
  
- **getApis()**: Returns the instance of the `APIs` interface, allowing other parts of the app to make API requests.

### Logic:
- **getInstance()**: Checks if the `RetrofitClient` instance is `null` and creates it if necessary, ensuring a single instance is used throughout the app.
  
---

## File: `RvAdapter.java`
### Description:
- This class is a custom adapter for a `RecyclerView` that binds a list of `ProductResult` objects (representing products) to a list item layout. It displays the product title and image for each item in the list.

### Key Components:
- **RecyclerView.Adapter**: Extends `RecyclerView.Adapter` to bind the data (`ProductResult` objects) to the `RecyclerView`.
- **HolderRetrofit**: A view holder class that holds references to the `TextView` (for product title) and `ImageView` (for product image).
- **Glide**: Used to load and display images from URLs into the `ImageView`. If the image fails to load, a default error image (`ic_launcher_background`) is shown.

### Logic:
- **onCreateViewHolder()**: Inflates the layout for each item in the `RecyclerView` and returns a `HolderRetrofit` instance.
- **onBindViewHolder()**: Binds the product's title and image to the respective views in each item.
- **getItemCount()**: Returns the total number of products in the list.

### UI Elements:
- **TextView (`ivTitle`)**: Displays the product's title.
- **ImageView (`ivImage`)**: Displays the product's image using the Glide library.

---

## File: `RvProductItem.java`
### Description:
- This activity handles the display of a single product item within the app, adjusting the layout to account for system UI elements (such as status and navigation bars) using `EdgeToEdge` support.

### Key Components:
- **EdgeToEdge**: This feature ensures the layout adapts to the system's UI, such as the status bar or navigation bar, without overlap.
- **WindowInsetsCompat**: Used to handle the insets (e.g., system bars) and apply padding to the `ImageView` (`ivImage`) so that the content doesn't overlap with system UI elements.

### Logic:
- **onCreate()**: 
  - Enables edge-to-edge support with `EdgeToEdge.enable(this)`.
  - Sets the content view to `activity_rv_product_item`.
  - Applies padding to the `ivImage` (ImageView) based on system UI insets to ensure proper display.

### UI Handling:
- **ViewCompat.setOnApplyWindowInsetsListener()**: Listens for window insets and applies the appropriate padding to the `ImageView` based on the system bar insets.

---

## File: `RvProducts.java`
### Description:
- This activity displays a list of products fetched from an API in a `RecyclerView`. It also provides user interaction features like logging out and navigating to a new page (`NewMail`).

### Key Components:
- **RecyclerView**: Displays a list of products using a custom adapter (`RvAdapter`).
- **Firebase Authentication**: Manages user logout via `FirebaseAuth.getInstance().signOut()`.
- **WindowInsetsCompat**: Ensures the layout adjusts properly to system UI insets (e.g., status and navigation bars) using edge-to-edge support.
- **Product API**: Fetches a list of products from a remote API using Retrofit.

### Logic:
- **onCreate()**: 
  - Initializes UI components (`RecyclerView`, buttons, and text views).
  - Sets the logged-in user's Gmail ID in the `ViewUserId` text view.
  - Handles button clicks: 
    - `btn_usr_logout`: Logs the user out and redirects to the `Login` activity.
    - `btn_next_page`: Navigates to the `NewMail` activity.
  - Calls `getProducts()` to fetch products from the API.

- **getProducts()**: 
  - Makes a Retrofit API call to fetch a list of products and updates the `RecyclerView` with the fetched data.
  - Uses `enqueue()` to perform an asynchronous network request and update the UI based on the response.

- **Airplane Mode Receiver**:
  - Listens for changes in the airplane mode and updates the UI via the `AirplaneModeChangeReceiver`.

- **setAdapter()**: Sets the `RvAdapter` for the `RecyclerView` to display the list of products.

### UI Handling:
- **Edge-to-Edge**: Applies padding to the root view to avoid overlapping with system UI elements like status and navigation bars.
  
### Lifecycle Methods:
- **onStart()**: Registers the `AirplaneModeChangeReceiver`.
- **onStop()**: Unregisters the `AirplaneModeChangeReceiver` when the activity is stopped.

---

## File: `UserDao.java`
### Description:
- This interface defines data access methods for the `UserInfo` entity in the Room database.

### Key Components:
- **Room Database DAO (Data Access Object)**: The interface is used for database operations related to the `UserInfo` entity.
- **Methods**:
  - `insertAll(UserInfo user)`: Inserts a single `UserInfo` record into the database.

### Purpose:
- The `UserDao` interface provides a simple method to insert user information into the Room database. This is part of the app's local database handling to store user-related data.

---

## File: `UserInfo.java`
### Description:
- This class represents a `UserInfo` entity for use with the Room database. It stores user information, including email and password.

### Key Components:
- **Entity**: The class is annotated with `@Entity`, indicating that it corresponds to a table (`users`) in the Room database.
- **PrimaryKey**: The `id` field is the primary key, which is auto-generated.
- **ColumnInfo**: The `email` and `password` fields are mapped to columns in the `users` table.
- **Constructor**: The class includes a constructor to initialize `email` and `password`.

### Fields:
- **id**: A unique identifier for each user, automatically generated by Room.
- **email**: The user's email address, stored in the `email` column.
- **password**: The user's password, stored in the `password` column.

### Methods:
- Getter and setter methods for `id`, `email`, and `password` to access and modify the data.

---


