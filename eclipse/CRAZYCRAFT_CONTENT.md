# Crazycraft content inventory

The two supplied reference images are concept art and content lists. They show different estimates (250+ and 353+), so the named entries below are the concrete target. A name in this file is not an implemented game feature unless marked **in game**.

## Old Dimension

| Area | In game | Still to build from the references |
| --- | --- | --- |
| Dimension and portal | Separate saved DIM2, Old portal, return gate, themed arrival | Additional portal effects and sounds |
| Creatures | T-Rex, Triceratops, Pteranodon, Stegosaurus, Velociraptor, Spinosaurus, Brachiosaurus, Carnotaurus, Allosaurus, Ankylosaurus, Dilophosaurus, Parasaurolophus, Iguanodon, Giganotosaurus, Mapusaurus, Compsognathus, Gallimimus and Oviraptor; the flying Pteranodon dives on small prey and occasionally a survival player | Mosasaurus, Ichthyosaurus, Plesiosaurus, Mammoth, Saber Tooth Tiger, Gigantoraptor, Titanoboa, Megalodon, crocodile, turtle, fish, piranha, insects, ancient bird |
| Biomes | 10 named surface regions with distinct terrain height, colors and ground blocks: prehistoric jungle, volcanic lands, savanna, fossil desert, swamp, redwood forest, coastal, canyon, tar pit, ancient plains | Crystal caves and true underground cave biome; biome-specific flora and formations need more detail |
| Blocks | 16 named Old building materials with distinct 32px material patterns, separate log ends and grass side, plus portal frame and portal | Separate functional flora, ore drops, animated tar/hot spring/crystal blocks, and the remaining block variants to meet the references' 50+/100+ estimates |
| Plants | Ferns, sparse new-shaped teal-canopy trees; block-built palm, cycad, baobab, redwood, crystal-tree and giant-fern landmarks | Separate plant blocks, bamboo, horsetail, carnivorous plant, mushrooms, flowers, bushes, hanging vines and growth mechanics |
| Structures | Bone skeletons, fossils, volcanoes, entry lagoon, stone towers, tar pits, simple dinosaur nests and ancient temples | Fossil excavation, jungle ruins, abandoned camp, natural bridges, hidden caves, prehistoric village and volcano fortress; nests and temples need loot and interaction |
| Items and equipment | Spawn eggs | Dino bone, fossil, amber, raw meat, explorer hat, saddle, ancient tools, bone sword, spear, trap, fossil compass, armor, backpack, medicine, crafting and drops |

Old Dimension population: five land/flying species are registered. Ground dinosaurs use single-animal natural spawn groups; the old extra per-chunk ground spawn was removed. T-Rex hunts less frequently, Triceratops and Stegosaurus retaliate instead of starting fights, and Pteranodon occasionally dives on prey. Existing saved dinosaurs are not deleted by these changes.

## Future Dimension

| Area | In game | Still to build from the references |
| --- | --- | --- |
| Dimension and portal | Separate saved DIM3, blue portal with energy-core frame | Portal animation, sound and dedicated future arrival landmark |
| Creatures and machines | Worker Bot | Defense Bot, Mech Golem, Drone, Flying Drone, Combat Mech, Turret Bot, AI Robot, Hover Bot, Boss Mech, robot dinosaur, mech spider, security robot, alien creature and the other listed variants |
| Vehicles | Decorative hover car shape | Rideable hover/flying cars, spaceship, drone ship, mech walker, submarine, jetpack, speeder bike, cargo ship, UFO, hover tank, mining drill and crafting/fuel systems |
| Biomes | 8 named surface regions with distinct height, colors and ground blocks: future city, floating islands, crystal desert, cyber mountains, space station, neon forest, robot factory, asteroid field | Biome-specific terrain and decorations need more detail; the generated island, station and asteroid shapes are simple block-built landmarks |
| Blocks | 16 named tech building materials | Conveyor, computer, storage unit, smart glass, anti-gravity, elevator and the remaining functional blocks to meet the 40+/80+ estimates |
| Structures | Towers, roads, factory shells, decorative hover cars, simple floating islands, station shells, asteroids, research labs and energy towers | Complete futuristic city layouts, furnished space station and research lab, launch pad and teleport gate |
| Equipment and systems | Future Creative tab | Laser sword, plasma gun, energy bow, nano armor, shield, fuel, quests, bosses, trading, soundtrack, achievements and unlocks |

## Verification

`verify-crazycraft.ps1 -Install` compiles sources, checks registration and key asset files, then copies compiled classes and assets to `Client/bin`. A successful compile does not establish that a new entity, biome, vehicle or structure matches the reference art visually or works during gameplay; each still needs a game run and inspection.
