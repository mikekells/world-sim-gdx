# Java 2D Game Development Learning (LibGDX)

This repository documents my journey learning **Java game development using LibGDX**.

I previously built a **console-based tile world simulation** in Java to understand object-oriented design, entity systems, and grid-based world logic.  
This project continues that work by transitioning the simulation into a **graphical 2D environment using LibGDX**.

The aim is to build a strong understanding of how game engines work while gradually developing the systems required to create my own games.

---

## Progress

- [x] Console tile world simulation
- [x] LibGDX project setup
- [x] Tile rendering
- [x] Player movement
- [ ] Camera follow
- [ ] Large map rendering
- [ ] Camera bounds
- [ ] Entity system

---

## Project Goals

The current focus is learning the **fundamentals of 2D rendering and game loops** while building a simple tile-based engine.

Key learning areas:

- LibGDX application lifecycle
- Game loop structure
- Rendering with `SpriteBatch`
- Tile-based world rendering
- Camera systems
- Player movement and collision
- Separation of logic and rendering

---

## Technologies

- Java 17
- LibGDX
- IntelliJ IDEA
- Ubuntu Linux
- Gradle

---

## Project Structure

```
uk.co.kellsnet.worldsim
│
├── Main.java
│   Entry point for the LibGDX application
│   Handles input, camera, and the render loop
│
├── TileMap.java
│   Stores the tile grid and world layout
│
├── TileType.java
│   Defines tile behaviour (walkable / blocked)
│
├── TileRenderer.java
│   Responsible for rendering tiles and entities
│
└── Player.java
    Player position and movement logic
```

---

## Current Features

- Tile-based world map
- Player movement using WASD
- Collision against walls
- Tile rendering system
- Placeholder sprites generated with Pixmap
- Orthographic camera

---

## Learning Roadmap

This project is being developed incrementally while learning LibGDX concepts.

### Phase 1 – Rendering Fundamentals

- Tile rendering
- Player sprite rendering
- Input handling
- Camera follow
- Larger world maps
- Camera bounds

### Phase 2 – World Systems

- Entity system
- Update loop
- Multiple entities
- Tile interactions
- Collision improvements

### Phase 3 – Movement Systems

- Pixel-based movement
- Delta time
- Player speed
- Camera smoothing

### Phase 4 – Sprites and Animation

- Sprite sheets
- Texture atlases
- Animated player movement
- Animated tiles

### Phase 5 – Gameplay Systems

- Doors and interactions
- Inventory
- UI elements
- Basic game mechanics

---

## Long-Term Goal

The long-term objective is to build a small **2D sprite-based game** using the systems developed in this repository.

Planned experimental projects include:

- A **Tamagotchi-style virtual pet**
- Tile-based exploration mechanics
- Small simulation-style gameplay systems

---

## Why This Repository Exists

This repository serves several purposes:

- Track my **learning progress**
- Practice **clean architecture and Java fundamentals**
- Understand how **2D game engines work internally**
- Document the transition from **console simulations to graphical games**

The project is intentionally built **step-by-step**, with systems added gradually as understanding improves.

---

## Author

Mike Kells

---

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
