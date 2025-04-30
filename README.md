
# TCP/IP City Temperature Management System

## Overview

A Java-based client-server system that enables multiple clients to **submit** and **query** temperature data for cities in real time. Built on a **TCP/IP** multithreaded architecture, the system supports concurrent connections and simple text-based communication between clients and the server.

## Features

- 🔥 **Temperature Tracking**: Add and retrieve temperatures for any city.
- 👥 **Multi-client Support**: Run multiple producer and consumer clients concurrently.
- ✅ **Input Validation**: Accepts temperatures only between -50°C and 60°C.
- 🔄 **Thread-safe Operations**: Synchronized access to a shared data structure.
- 🧵 **Multithreaded Server**: Each client runs on its own thread.
- 💬 **Simple TCP Protocol**: Easy communication using plain text commands.

## Components

### 🖥️ Server (`serverCityTemperatures.java`)
- Listens for client connections on **port 20000**.
- Spawns a new thread for each connected client.
- Maintains a synchronized `HashMap<String, Double>` of city-temperature pairs.

### 🔧 Producer Client (`ProducerClients.java`)
- Connects to the server to **submit new temperatures**.
- Prompts the user to enter values like: `CityName, Temperature` (e.g., `Beirut, 27.5`).
- Validates input and displays confirmation upon update.

### 🔍 Consumer Client (`ConsumerClients.java`)
- Connects to the server to **query temperatures**.
- Allows input of:
  - A city name (e.g., `Tripoli`) to get its temperature.
  - `list` to view all stored cities and temperatures.
  - `avg` to calculate the average temperature.
  - `.` to terminate the session.

### 🧵 Thread Handlers
- `producerThread.java`: Handles producer connections and updates.
- `consumerThread.java`: Handles consumer queries and responses.

## How to Run

1. **Compile Java files**:
   ```bash
   javac /*.java
   ```

2. **Start the server** (in a terminal):
   ```bash
   java serverCityTemperatures
   ```

3. **Run a producer client** (in another terminal):
   ```bash
   java ProducerClients
   ```

4. **Run a consumer client**:
   ```bash
   java ConsumerClients
   ```

   ✅ When prompted, enter the server's IP address (e.g., `localhost` if on the same machine).

## Example Sessions

### 👨‍💻 Producer Client
```
Connect to:
localhost
Give the city and temperature (please in this Format city, temp)
Paris, 21.3
UPDATED: Paris = 21.3°C
Cairo, 29
UPDATED: Cairo = 29.0°C
.
Session ended.
```

### 👩‍💻 Consumer Client
```
Connect to:
localhost
Enter city name
Cairo
Cairo: 29.0°C
avg
Average temperature = 25.15°C
list
Paris = 21.3°C
Cairo = 29.0°C
.
Session ended.
```

## Technical Details

| Property         | Value                                |
|------------------|--------------------------------------|
| Language         | Java                                 |
| Port             | `20000`                              |
| Data Structure   | `HashMap<String, Double>`            |
| Protocol         | Simple line-based TCP commands       |
| Concurrency      | Thread-per-client model              |
| Validation       | Temperatures between -50 and 60 °C   |

## Limitations

- ❌ Data is stored **in-memory** only (lost after shutdown)
- 🔐 No authentication or encryption
- 📦 No support for saving or querying historical data
- 🚫 No load balancing or distributed deployment

## Future Enhancements

- 💾 Add database storage (e.g., MySQL, SQLite)
- 🔐 User authentication and roles
- 📊 Support historical temperature logs
- 🌍 List all cities by region or sort by temperature
- 🐞 Better error handling and logging
- ⚙️ Configurable server port and timeout settings

