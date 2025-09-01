## GetBlock - Solana Blockchain Explorer
GetBlock is an Android application that serves as a [Solana Blockchain Explorer](https://solscan.io/), allowing users to explore and monitor the Solana blockchain in real-time. The app provides a modern, intuitive interface for viewing blockchain data including blocks, supply information, and epoch details.

### Features
- **Real-time blockchain data**: Live updates of Solana blockchain information.
- **Block explorer**: Search and view specific blocks by block number.
- **Supply information**: Monitor total SOL supply, circulating supply, and non-circulating supply.
- **Epoch information**: Track current epoch, slot ranges, and time remaining.
- **Modern UI**: Built with Jetpack Compose for a smooth, native Android experience.


### Tech stack
+ **Language**: Kotlin.
+ **UI framework**: Jetpack Compose.
+ **Networking**: Ktor HTTP client for API calls.
+ **Image loading**: Coil for efficient image loading.
+ **API**: [GetBlock.io Solana RPC endpoint](https://docs.getblock.io/api-reference/solana-sol/sol_getblock).
+ **Data models**: Kotlin data classes with *kotlinx* serialization.
+ **State management**: StateFlow and Compose state.


### Architecture
The app follows modern Android development practices with *clean separation of concerns, **repository** pattern for data management, **ViewModel** for business logic, **Compose UI** components, and **RPC API** integration for blockchain data*.


### Dependencies
* **Ktor Client** for HTTP client and serialization;
* **Kotlinx Serialization** for JSON parsing;
* **Jetpack Compose** for UI;
* **AndroidX Lifecycle** components;
* **Navigation Compose** for screen navigation;
* **Material Design 3** components.


### Installation
1. Clone the repository.
2. Open in Android Studio.
3. Sync Gradle dependencies.
4. Run on device or emulator.


### License
Personal project for educational and portfolio purposes.
