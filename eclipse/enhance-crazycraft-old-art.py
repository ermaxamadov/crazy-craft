"""Deterministic hand-drawn pixel patterns for Old Dimension materials and new dinosaurs."""
from pathlib import Path
from PIL import Image, ImageDraw
import json
import random

ROOT = Path(__file__).resolve().parent.parent / 'src/minecraft/assets/minecraft'
BLOCKS = ROOT / 'textures/blocks'
ENTITIES = ROOT / 'textures/entity/crazycraft'
MODELS = ROOT / 'models/block'

PALETTE = {
    'ancient_stone': ((72, 78, 68), (157, 149, 112)),
    'fossil_block': ((113, 96, 70), (231, 211, 163)),
    'amber_block': ((113, 56, 16), (255, 185, 39)),
    'volcanic_rock': ((36, 31, 35), (255, 96, 22)),
    'jungle_dirt': ((77, 55, 37), (71, 139, 59)),
    'mossy_stone': ((72, 78, 72), (71, 153, 88)),
    'tar_pit_block': ((17, 20, 25), (89, 75, 112)),
    'prehistoric_sand': ((189, 156, 104), (251, 221, 149)),
    'redwood_log': ((83, 42, 34), (199, 99, 55)),
    'palm_log': ((132, 94, 56), (223, 176, 100)),
    'crystal_stone': ((24, 65, 77), (76, 232, 221)),
    'sulfur_block': ((106, 99, 48), (237, 217, 61)),
    'mud_block': ((70, 56, 47), (155, 125, 93)),
    'meteorite_ore': ((47, 50, 62), (189, 103, 229)),
    'fossil_ore': ((75, 77, 70), (231, 212, 166)),
    'prehistoric_grass': ((53, 100, 57), (137, 174, 88)),
}


def mix(a, b, t):
    return tuple(int(a[i] * (1 - t) + b[i] * t) for i in range(3))


def rgba(rgb, alpha=255):
    return (*rgb, alpha)


def tile(name, base, accent):
    rng = random.Random(name)
    im = Image.new('RGBA', (32, 32))
    px = im.load()
    for y in range(32):
        for x in range(32):
            n = rng.randrange(-14, 15)
            tone = tuple(max(0, min(255, c + n)) for c in base)
            px[x, y] = rgba(tone)
    d = ImageDraw.Draw(im)
    dark = mix(base, (9, 13, 13), .48)
    light = mix(base, accent, .48)
    if name in ('ancient_stone', 'mossy_stone', 'fossil_block', 'fossil_ore'):
        for row in range(0, 33, 8):
            y = row + rng.randrange(-1, 2)
            d.line((0, y, 31, y), fill=rgba(dark), width=2)
            for shift in (0, 16):
                x = shift + (8 if row // 8 % 2 else 0)
                d.line((x, y, x + rng.randrange(-2, 3), y + 7), fill=rgba(dark), width=1)
        for _ in range(25):
            x, y = rng.randrange(32), rng.randrange(32)
            d.point((x, y), fill=rgba(light))
    if name in ('fossil_block', 'fossil_ore'):
        d.line((3, 21, 10, 18, 15, 11, 23, 10, 29, 5), fill=rgba(dark), width=5)
        d.line((3, 20, 10, 17, 15, 10, 23, 9, 29, 4), fill=rgba(accent), width=3)
        for x, y in ((4, 20), (29, 4)):
            d.ellipse((x-2, y-2, x+2, y+2), fill=rgba(accent))
    elif name == 'amber_block':
        for coords in ((1, 5, 15, 21), (13, 0, 31, 14), (17, 17, 31, 31)):
            d.polygon([(coords[0], coords[1]), (coords[2], coords[1]+3),
                       (coords[2]-3, coords[3]), (coords[0]+3, coords[3]-2)],
                      fill=rgba(mix(base, accent, .6)), outline=rgba(dark))
        d.line((4, 3, 13, 1, 19, 10, 29, 11), fill=rgba((255, 232, 133)), width=2)
        d.ellipse((12, 18, 18, 24), fill=rgba((64, 44, 17)))
    elif name == 'volcanic_rock':
        for points in (((0, 8), (9, 11), (14, 18), (25, 16), (31, 23)),
                       ((8, 0), (10, 9), (5, 18), (12, 31)),
                       ((24, 0), (19, 9), (31, 14))):
            d.line(points, fill=rgba((97, 34, 24)), width=5)
            d.line(points, fill=rgba(accent), width=2)
        for _ in range(15):
            d.point((rng.randrange(32), rng.randrange(32)), fill=rgba((112, 90, 76)))
    elif name in ('jungle_dirt', 'mud_block'):
        for _ in range(60):
            x, y = rng.randrange(32), rng.randrange(32)
            d.line((x, y, x+rng.randrange(1, 4), y), fill=rgba(light if rng.randrange(2) else dark))
        if name == 'jungle_dirt':
            for x in (2, 8, 17, 25, 30):
                d.line((x, 0, x+2, 5), fill=rgba(accent), width=2)
        else:
            for y in (6, 17, 27):
                d.arc((2, y-3, 29, y+3), 0, 170, fill=rgba(light), width=1)
    elif name == 'mossy_stone':
        for _ in range(13):
            x, y = rng.randrange(32), rng.randrange(32)
            d.ellipse((x, y, x+rng.randrange(2, 7), y+rng.randrange(2, 6)), fill=rgba(accent))
    elif name == 'tar_pit_block':
        for k in range(3):
            y = k * 11 + rng.randrange(3)
            d.arc((-5, y-5, 20, y+11), 0, 180, fill=rgba(accent), width=2)
            d.arc((13, y-3, 38, y+13), 170, 355, fill=rgba(light), width=1)
        for x, y in ((6, 5), (22, 17), (28, 26)):
            d.ellipse((x, y, x+2, y+2), outline=rgba((147, 133, 171)))
    elif name == 'prehistoric_sand':
        for y in (4, 12, 20, 28):
            d.line((0, y, 7, y-1, 17, y+1, 31, y), fill=rgba(accent), width=2)
            d.line((2, y+2, 12, y+3, 24, y+2), fill=rgba(dark))
    elif name in ('redwood_log', 'palm_log'):
        spacing = 5 if name == 'redwood_log' else 7
        for x in range(1, 32, spacing):
            d.line((x, 0, x+rng.randrange(-2, 3), 31), fill=rgba(dark), width=2)
            d.line((x+2, 0, x+2+rng.randrange(-2, 3), 31), fill=rgba(accent), width=1)
        if name == 'palm_log':
            for y in (4, 12, 20, 28):
                d.line((0, y, 31, y-2), fill=rgba(light), width=2)
    elif name == 'crystal_stone':
        for x, y, size in ((2, 2, 15), (17, 0, 13), (12, 16, 17), (-3, 19, 13)):
            d.polygon(((x, y+size//2), (x+size//2, y), (x+size, y+size//2),
                       (x+size//2, y+size)), fill=rgba(mix(base, accent, .45)), outline=rgba(accent))
            d.line((x+size//2, y, x+size//2, y+size), fill=rgba((154, 255, 251)))
    elif name == 'sulfur_block':
        for _ in range(15):
            x, y = rng.randrange(32), rng.randrange(32)
            r = rng.randrange(2, 6)
            d.polygon(((x, y+r), (x+r//2, y), (x+r, y+r)), fill=rgba(accent), outline=rgba(dark))
    elif name == 'meteorite_ore':
        for _ in range(19):
            x, y = rng.randrange(32), rng.randrange(32)
            r = rng.randrange(2, 5)
            d.polygon(((x, y), (x+r, y+1), (x+r-1, y+r), (x-1, y+r-1)),
                      fill=rgba(accent if rng.randrange(2) else light), outline=rgba(dark))
    elif name == 'prehistoric_grass':
        for _ in range(95):
            x, y = rng.randrange(32), rng.randrange(32)
            d.line((x, y, x+rng.randrange(-1, 2), y-rng.randrange(1, 4)),
                   fill=rgba(accent if rng.randrange(3) else dark))
    return im


for name, (base, accent) in PALETTE.items():
    tile(name, base, accent).save(BLOCKS / ('crazycraft_' + name + '.png'))

for name in ('redwood_log', 'palm_log'):
    base, accent = PALETTE[name]
    end = Image.new('RGBA', (32, 32), rgba(base))
    d = ImageDraw.Draw(end)
    for inset in (2, 5, 9, 13):
        d.ellipse((inset, inset, 31-inset, 31-inset), outline=rgba(accent), width=2)
    end.save(BLOCKS / ('crazycraft_' + name + '_end.png'))
    (MODELS / ('crazycraft_' + name + '.json')).write_text(json.dumps({
        'parent': 'block/cube_column',
        'textures': {'side': 'blocks/crazycraft_' + name,
                     'end': 'blocks/crazycraft_' + name + '_end'}
    }), encoding='utf-8')

side = tile('jungle_dirt', *PALETTE['jungle_dirt'])
d = ImageDraw.Draw(side)
d.rectangle((0, 0, 31, 5), fill=rgba(PALETTE['prehistoric_grass'][0]))
for x in range(0, 32, 3):
    d.line((x, 4, x, 6 + (x % 4)), fill=rgba(PALETTE['prehistoric_grass'][1]))
side.save(BLOCKS / 'crazycraft_prehistoric_grass_side.png')
(MODELS / 'crazycraft_prehistoric_grass.json').write_text(json.dumps({
    'parent': 'block/cube_bottom_top',
    'textures': {'top': 'blocks/crazycraft_prehistoric_grass',
                 'side': 'blocks/crazycraft_prehistoric_grass_side',
                 'bottom': 'blocks/crazycraft_jungle_dirt'}
}), encoding='utf-8')

ENTITIES.mkdir(parents=True, exist_ok=True)
for name, base, accent in (
    ('stegosaurus', (78, 103, 57), (196, 163, 91)),
    ('velociraptor', (127, 75, 49), (218, 152, 88)),
):
    rng = random.Random(name)
    image = Image.new('RGBA', (128, 64), rgba(base))
    pixels = image.load()
    for y in range(64):
        for x in range(128):
            n = rng.randrange(-17, 18)
            pixels[x, y] = rgba(tuple(max(0, min(255, c+n)) for c in base))
    d = ImageDraw.Draw(image)
    for _ in range(220):
        x, y = rng.randrange(128), rng.randrange(64)
        size = rng.randrange(1, 4)
        d.ellipse((x, y, x+size, y+size), fill=rgba(accent if rng.randrange(3) else mix(base, (25, 20, 19), .45)))
    if name == 'stegosaurus':
        for x in range(0, 128, 12):
            d.polygon(((x, 0), (x+5, 10), (x+11, 0)), fill=rgba(accent))
    else:
        for y in range(4, 64, 11):
            d.line((0, y, 127, y+7), fill=rgba(mix(base, accent, .65)), width=3)
    image.save(ENTITIES / (name + '.png'))

SPECIES_SKIN = {
    'spinosaurus': ((55, 72, 77), (154, 102, 72)),
    'carnotaurus': ((122, 55, 43), (196, 105, 66)),
    'allosaurus': ((104, 101, 77), (174, 158, 111)),
    'dilophosaurus': ((74, 102, 67), (214, 124, 61)),
    'brachiosaurus': ((107, 99, 83), (179, 160, 126)),
    'ankylosaurus': ((77, 79, 62), (171, 152, 109)),
    'parasaurolophus': ((100, 80, 65), (193, 137, 87)),
    'iguanodon': ((94, 108, 79), (174, 170, 117)),
}
for name, (base, accent) in SPECIES_SKIN.items():
    rng = random.Random('dinosaur-' + name)
    image = Image.new('RGBA', (128, 128), rgba(base))
    pixels = image.load()
    for y in range(128):
        for x in range(128):
            grain = rng.randrange(-13, 14)
            pixels[x, y] = rgba(tuple(max(0, min(255, c+grain)) for c in base))
    d = ImageDraw.Draw(image)
    for _ in range(520):
        x, y = rng.randrange(128), rng.randrange(128)
        r = rng.randrange(1, 5)
        shade = accent if rng.randrange(3) else mix(base, (15, 17, 16), .48)
        d.ellipse((x, y, x+r, y+r//2+1), fill=rgba(shade))
    for y in range(6, 128, 22):
        d.arc((-12, y-8, 139, y+24), 5, 175, fill=rgba(mix(base, accent, .55)), width=2)
    if name == 'spinosaurus':
        for x in range(0, 128, 10):
            d.polygon(((x, 0), (x+5, 18), (x+10, 0)), fill=rgba(accent))
    if name == 'dilophosaurus':
        for x in (8, 16, 24, 32):
            d.line((x, 4, x+4, 28), fill=rgba((229, 171, 75)), width=3)
    if name == 'ankylosaurus':
        for y in (18, 47, 77, 106):
            for x in range(7, 128, 20):
                d.polygon(((x, y+5), (x+8, y), (x+17, y+6), (x+9, y+12)),
                          fill=rgba(accent), outline=rgba(mix(base, (17, 20, 16), .55)))
    image.save(ENTITIES / (name + '.png'))

print('Enhanced 16 Old blocks and added Stegosaurus/Velociraptor textures.')
