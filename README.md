# Java + LibGDX Game Development Journey

This repository documents my journey learning **Java** and **LibGDX** while building the foundations of a **2D tile-based game engine**.

The goal is to gradually move from simple console-based simulations to a fully functional graphical engine capable of supporting small games.

This project is intentionally built **step-by-step**, focusing on understanding core concepts rather than jumping straight to complex frameworks.

---

# Goals

The long-term objective is to develop the knowledge required to build small games, including:

- Tile-based world rendering
- Camera systems
- Entity systems
- Player movement and interaction
- Basic game systems such as inventory and dialogue

These foundations will later support projects such as:

- A **Tamagotchi-style virtual pet**

---

# Technologies

- **Java 17**
- **LibGDX**
- **IntelliJ IDEA**
- **Ubuntu Linux**
- **Git / GitHub**

---

# What This Project Teaches

This repository focuses on understanding the core ideas behind game engines:

- Game loops
- Tile-based worlds
- Coordinate systems (tile vs pixel space)
- Camera movement and clamping
- Rendering pipelines
- Basic procedural world generation

Rather than copying tutorials, the aim is to **build systems from scratch and understand why they work**.

---

# Engine Concepts Learned So Far

### Coordinate Systems

The engine currently uses multiple coordinate systems:

| System | Purpose |
|------|------|
| Tile Coordinates | Game logic and world layout |
| Pixel Coordinates | Rendering positions |
| Camera Coordinates | Determines what part of the world is visible |

Understanding how to convert between these systems is a key part of building a tile engine.

---

# Progress

## Phase 1 – Console Engine Foundations

- [x] Console tile world simulation
- [x] Tile grid world
- [x] Player movement and collision
- [x] Game state management
- [x] Console renderer separation

## Phase 2 – LibGDX Rendering

- [x] LibGDX project setup
- [x] Tile rendering
- [x] Player rendering
- [x] Player movement (tile based)

## Phase 3 – Camera System

- [x] Camera follow player
- [x] Camera centering math
- [x] Camera bounds / clamping
- [x] Large map rendering
- [x] Render only visible tiles

## Phase 4 – Map Generation

- [x] Procedural map generation
- [x] Map borders / walls
- [x] Feature placement (pillars)

## Phase 5 – Engine Structure (In Progress)

- [x] Rendering layers
- [x] Input repeat (hold key to move)
- [ ] Smooth movement
- [x] Entity system
- [ ] Basic NPC / enemy entities

## Phase 6 – Gameplay Systems

- [ ] Interaction system
- [ ] Inventory
- [ ] Dialogue / messages
- [ ] Items and pickups

## Phase 7 – Visual Improvements

- [ ] Sprite sheets
- [ ] Animation
- [ ] Lighting / atmosphere
- [ ] Sound effects

---

# Current State

The project currently includes:

- A procedurally generated tile map
- Player movement and collision
- A camera that follows the player
- Camera clamping to world bounds
- Rendering of only visible tiles for efficiency

These systems form the early structure of a **simple tile-based game engine**.

---

# Why This Repository Exists

This project serves as:

- A **learning log**
- A **reference for future projects**
- A **foundation for building small games**

It tracks the process of gradually moving from beginner programming knowledge to building functional game systems.

---

# Future Plans

Planned future improvements include:

- Rendering layers (floor, objects, entities)
- Smooth player movement
- An entity system for NPCs and enemies
- Interaction mechanics
- Basic game UI systems
- Sprite animation
- Sound and atmosphere

The goal is to continue expanding the engine while maintaining clear and understandable code.
