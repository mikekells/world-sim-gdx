# Engine Notes / Developer Reference

These notes capture small formulas and patterns used while building this tile engine so they are easy to recall later.

---

# Tile vs Pixel Coordinates

The engine uses **tile coordinates for game logic** and **pixel coordinates for rendering**.

Example tile position:

```
(3,2)
```

Tile size:

```
32 pixels
```

---

# Converting Tile → Pixel (Tile Corner)

Used when **rendering tiles or entities**.

Formula:

```
pixelX = tileX * TILE_SIZE
pixelY = tileY * TILE_SIZE
```

Example:

```
tile = (3,2)
TILE_SIZE = 32

pixelX = 3 * 32 = 96
pixelY = 2 * 32 = 64
```

Result:

```
(96,64)
```

This gives the **bottom-left corner of the tile**.

Example from renderer:

```java
batch.draw(texture, x * TILE_SIZE, y * TILE_SIZE);
```

---

# Converting Tile → Pixel (Tile Center)

Used when targeting things like:

- camera follow
- projectiles
- centering entities

Formula:

```
pixelX = tileX * TILE_SIZE + TILE_SIZE / 2
pixelY = tileY * TILE_SIZE + TILE_SIZE / 2
```

Example:

```
tile = (3,2)
TILE_SIZE = 32

pixelX = 3 * 32 + 16 = 112
pixelY = 2 * 32 + 16 = 80
```

Result:

```
(112,80)
```

---

# Converting Pixel → Tile

Used when converting things like:

- camera position
- mouse clicks
- world coordinates

Formula:

```
tileX = pixelX / TILE_SIZE
tileY = pixelY / TILE_SIZE
```

Example:

```
pixelX = 320
TILE_SIZE = 32

tileX = 320 / 32 = 10
```

This tells us the pixel lies inside **tile column 10**.

---

# Camera Visible Area Calculation

The camera defines a window into the world.

Visible world bounds:

```
left   = cameraX - viewportWidth / 2
right  = cameraX + viewportWidth / 2
bottom = cameraY - viewportHeight / 2
top    = cameraY + viewportHeight / 2
```

These values are in **pixels**.

They can then be converted into **tile indices** for rendering.

Example:

```
startX = left / TILE_SIZE
endX   = right / TILE_SIZE
```

This allows the renderer to **draw only visible tiles**.

---

# Random Number Generation

Java uses the `Random` class.

Example:

```java
Random random = new Random();
```

---

# Random Movement Example

Used for simple NPC movement.

```
int direction = random.nextInt(4);
```

Possible results:

```
0
1
2
3
```

Example usage:

```java
switch(direction) {
    case 0 -> moveUp();
    case 1 -> moveDown();
    case 2 -> moveLeft();
    case 3 -> moveRight();
}
```

---

# Random Rewards

Randomness can also be used for gameplay systems.

Example: random reward drop.

```java
int roll = random.nextInt(100);
```

Result range:

```
0 - 99
```

Example logic:

```
0-59   -> common item
60-89  -> rare item
90-99  -> legendary item
```

Example code:

```java
if (roll < 60) {
    giveCommonItem();
} else if (roll < 90) {
    giveRareItem();
} else {
    giveLegendaryItem();
}
```

---

# Random Dialogue / Responses

Random numbers can also select dialogue lines.

Example:

```java
int response = random.nextInt(3);
```

Possible outcomes:

```
0
1
2
```

Example:

```java
switch(response) {
    case 0 -> say("Hello!");
    case 1 -> say("Nice weather today.");
    case 2 -> say("Be careful out there.");
}
```

This can make NPCs feel less repetitive.

---

# Summary

Common conversions used in the engine:

Tile → Pixel

```
tile * TILE_SIZE
```

Tile → Pixel Center

```
tile * TILE_SIZE + TILE_SIZE / 2
```

Pixel → Tile

```
pixel / TILE_SIZE
```

Random number range

```
random.nextInt(n)
```

Returns values:

```
0 → n-1
```

---

# Key Idea

Game engines constantly convert between coordinate systems:

```
Tile space   (game logic)
↓
Pixel space  (rendering)
↓
Camera space (viewport)
```

Understanding these conversions is fundamental to building tile-based games.

---

# Programming Patterns Learned So Far

These are practical programming patterns used while building the tile engine.

---

# 1. Separation of Concerns

Different classes have different responsibilities.

Examples in this project:

- `TileMap` stores world data
- `Player` stores player position/state
- `TileRenderer` draws the world
- `Main` coordinates input, update flow, and rendering

Why this matters:

- code is easier to read
- code is easier to change
- bugs are easier to track down

Pattern idea:

```
logic != rendering != orchestration
```

---

# 2. Data vs Presentation

The game world is stored as data first, then drawn visually.

Example:

- player position is stored as tile coordinates in `Player`
- renderer converts those coordinates into pixel positions

Why this matters:

- the game logic does not depend on graphics
- the same logic could be rendered differently later
- easier to test and reason about

Pattern idea:

```
store state
then render state
```

---

# 3. Helper Method Extraction

Larger tasks were broken into smaller helper methods.

Examples:

- `tryMovePlayer()`
- `updateCamera()`
- `fillWithEmpty()`
- `buildWalls()`
- `placePillar()`

Why this matters:

- each method does one clear job
- easier to debug
- easier to reuse logic
- easier to understand intent from method names

Pattern idea:

```
large task
→ smaller named steps
```

---

# 4. Guard Clauses

Return early when something is invalid instead of nesting everything deeply.

Example:

```java
if (!inBounds(targetX, targetY)) {
    debug("[MOVE] Blocked: target out of bounds");
    return;
}
```

Why this matters:

- avoids deep nesting
- makes failure conditions obvious
- keeps the happy path clearer

Pattern idea:

```
check invalid case early
→ exit early
→ continue normal logic
```

---

# 5. State Change Then Render

The engine updates game state first, then draws the result.

Typical flow:

```java
handle input
update state
update camera
render world
```

Why this matters:

- rendering should reflect the latest state
- avoids drawing stale information
- keeps the game loop predictable

Pattern idea:

```
update first
draw second
```

---

# 6. Coordinate Conversion Pattern

The engine constantly converts between coordinate systems.

Examples:

- tile → pixel
- tile center → camera target
- pixel → tile for visible bounds

Why this matters:

- game logic and rendering use different coordinate systems
- camera math depends on converting correctly

Pattern idea:

```
tile space ↔ pixel space ↔ camera space
```

---

# 7. Generation Pipeline

The world is created in ordered steps.

Example:

```java
generateMap()
    → fillWithEmpty()
    → buildWalls()
    → placePillar()
```

Why this matters:

- world generation becomes readable
- easy to add more generation steps later
- easier to debug map-building logic

Pattern idea:

```
build base
→ apply rules
→ place features
```

---

# 8. Shared Constant Pattern

Important shared values should live in one place.

Example:

```java
TileMap.TILE_SIZE
```

instead of duplicating tile size in multiple classes.

Why this matters:

- avoids mismatched values
- easier to maintain
- one source of truth

Pattern idea:

```
shared value
→ define once
→ reuse everywhere
```

---

# 9. Event-Based Logging

Logs should happen when something meaningful changes, not constantly.

Good examples:

- movement success
- movement blocked
- camera moved because player moved
- map created

Bad example:

- logging every frame in `render()`

Why this matters:

- easier to read console output
- useful for debugging
- avoids noise

Pattern idea:

```
log state changes
not steady state
```

---

# 10. Allocation vs Initialisation

Creating storage is not the same as filling it with valid data.

Example:

```java
tiles = new TileType[height][width];
```

This only creates the array.

Then:

```java
generateMap();
```

fills it with actual values such as `EMPTY`, `WALL`, and `PILLAR`.

Why this matters:

- prevents null-related bugs
- important in many areas of programming

Pattern idea:

```
allocate memory
then initialise contents
```

---

# 11. Clamping

A value is forced to stay within allowed limits.

Examples:

- camera position kept inside world bounds
- visible tile range kept inside map bounds

Example:

```java
MathUtils.clamp(value, min, max)
```

Why this matters:

- prevents going out of bounds
- common in camera systems, health values, UI sliders, movement, and more

Pattern idea:

```
value cannot go below min
value cannot go above max
```

---

# 12. Range-Based Random Logic

Random values can drive behaviour by assigning meaning to ranges.

Examples:

- random movement direction
- random rewards
- random NPC responses

Example:

```java
int roll = random.nextInt(100);
```

Then use ranges:

- `0–59` common
- `60–89` rare
- `90–99` legendary

Why this matters:

- simple way to create variation
- useful for rewards, AI, loot, dialogue, events

Pattern idea:

```
generate random number
→ map ranges to outcomes
```

---

# Summary

Patterns used so far:

- Separation of concerns
- Data vs presentation
- Helper method extraction
- Guard clauses
- State change then render
- Coordinate conversion
- Generation pipeline
- Shared constants
- Event-based logging
- Allocation vs initialisation
- Clamping
- Range-based random logic

These are not just game patterns — they are general programming habits that will keep becoming useful in larger projects.

---

# Example Colors You Might Try

Stone floor: `setColor(0.3f, 0.3f, 0.3f, 1);`

Grass: `setColor(0.1f, 0.5f, 0.1f, 1);`

Sand: `setColor(0.8f, 0.7f, 0.3f, 1);`

Water: `setColor(0.1f, 0.2f, 0.8f, 1);`

| Value         | Color       |
| ------------- | ----------- |
| `0,0,0`       | black       |
| `0.2,0.2,0.2` | dark grey   |
| `0.5,0.5,0.5` | medium grey |
| `0.8,0.8,0.8` | light grey  |
| `1,1,1`       | white       |

# Timer Pattern
### A cooldown timer.

```timer -= delta
if(timer <= 0) {
    do action
    timer = delay
}
```

The logic now looks like this:

```
player holds key
↓
check if timer expired
↓
perform action
↓
reset timer
```

In code terms:

```
moveTimer -= delta;

if (moveTimer <= 0f) {
    tryMovePlayer(...);
    moveTimer = moveDelay;
}
```

This pattern is extremely common in games.

### Where This Pattern Appears Later
 Exactly the same logic is used for things like:

### Weapon firing
```
shootTimer -= delta

if (shootTimer <= 0) {
    fireBullet()
    shootTimer = fireDelay
}
```

### Enemy attacks
```
attackTimer -= delta

if (attackTimer <= 0) {
    enemyAttack()
    attackTimer = attackCooldown
}
```

### Animation frames
```
frameTimer -= delta

if (frameTimer <= 0) {
    nextAnimationFrame()
    frameTimer = frameDelay
}
```

### Spawning enemies
```
spawnTimer -= delta

if (spawnTimer <= 0) {
    spawnEnemy()
    spawnTimer = spawnInterval
}
```

### Random

---

1st April 2026

Today I have no code to commit.
Today the green box won't light up... Or will it? 
This sentence could feel like a scam but it's not.

Today I spent time looking back at what was learned. Specifically regarding smooth
movement. I've a world that generates using tiles at specific coordinates (x, y).
We implemented interpolation, use of lerp

Example:
```
float cameraLerpSpeed = 10f;
float alpha = Math.min(1f, cameraLerpSpeed * delta);

float smoothedX = MathUtils.lerp(camera.position.x, playerCenterX, alpha);
float smoothedY = MathUtils.lerp(camera.position.y, playerCenterY, alpha);

```

to create a starting point and end point.
This let's me progress across that boundary. Enabling me to draw sprites more incrementally.

Smooth movement! =D

---
