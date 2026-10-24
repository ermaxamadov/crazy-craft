"""Create distinct 1.12 block models and pixel textures for registered dimension materials."""
from pathlib import Path
from PIL import Image, ImageDraw
import json
import random
import runpy

root = Path(__file__).resolve().parent.parent / 'src/minecraft/assets/minecraft'
old = {
    'ancient_stone': ((91, 88, 78), (147, 139, 113)),
    'fossil_block': ((107, 100, 83), (224, 209, 164)),
    'amber_block': ((184, 85, 15), (255, 196, 50)),
    'volcanic_rock': ((49, 37, 37), (241, 80, 23)),
    'jungle_dirt': ((91, 58, 34), (67, 127, 68)),
    'mossy_stone': ((88, 92, 80), (51, 134, 80)),
    'tar_pit_block': ((25, 27, 30), (58, 49, 82)),
    'prehistoric_sand': ((202, 176, 124), (248, 222, 162)),
    'redwood_log': ((94, 44, 34), (183, 85, 50)),
    'palm_log': ((125, 91, 52), (203, 152, 77)),
    'crystal_stone': ((38, 95, 100), (81, 229, 226)),
    'sulfur_block': ((162, 143, 45), (245, 218, 70)),
    'mud_block': ((81, 66, 48), (126, 105, 71)),
    'meteorite_ore': ((55, 55, 59), (151, 91, 188)),
    'fossil_ore': ((82, 82, 74), (225, 208, 159)),
    'prehistoric_grass': ((70, 108, 55), (128, 158, 72)),
}
future = {
    'metal_block': ((69, 84, 105), (156, 183, 207)),
    'neon_block': ((15, 46, 80), (22, 223, 255)),
    'energy_core': ((21, 47, 105), (54, 168, 255)),
    'glass_panel': ((44, 111, 154), (104, 218, 255)),
    'circuit_block': ((28, 67, 64), (57, 240, 195)),
    'carbon_block': ((32, 38, 49), (90, 104, 130)),
    'hologram_block': ((40, 28, 83), (199, 75, 255)),
    'force_field': ((31, 62, 128), (98, 211, 255)),
    'machine_block': ((50, 67, 88), (73, 200, 251)),
    'white_metal': ((136, 149, 163), (226, 240, 248)),
    'dark_metal': ((25, 34, 50), (94, 116, 145)),
    'blue_alloy': ((34, 75, 132), (88, 171, 235)),
    'gravity_block': ((29, 42, 99), (149, 106, 255)),
    'teleporter_block': ((43, 30, 90), (215, 101, 255)),
    'solar_panel': ((15, 41, 88), (61, 154, 227)),
    'battery': ((44, 54, 72), (93, 211, 252)),
}

def save_json(relative, value):
    path = root / relative
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(value, separators=(',', ':')) + '\n', encoding='utf-8')

for kind, names in ((0, old), (1, future)):
    for name, (base, accent) in names.items():
        key = 'crazycraft_' + name
        image = Image.new('RGBA', (16, 16), base + (255,))
        draw = ImageDraw.Draw(image)
        noise = random.Random(sum((i + 1) * ord(ch) for i, ch in enumerate(name)))
        for y in range(16):
            for x in range(16):
                shift = noise.randrange(-17, 18)
                alpha = 150 if name in ('glass_panel', 'hologram_block', 'force_field') else 255
                image.putpixel((x, y), tuple(max(0, min(255, c + shift)) for c in base) + (alpha,))
        if kind == 0:
            for y in range(2, 16, 5):
                draw.line((0, y, 15, y + noise.randrange(-1, 2)), fill=accent + (225,), width=1)
            for _ in range(9):
                x, y = noise.randrange(16), noise.randrange(16)
                draw.rectangle((x, y, min(15, x + 1), min(15, y + 1)), fill=accent + (255,))
            if 'ore' in name or 'fossil' in name:
                draw.line((2, 11, 6, 8, 10, 9, 14, 4), fill=accent + (255,), width=2)
        else:
            draw.rectangle((0, 0, 15, 15), outline=accent + (255,), width=2)
            for x in (4, 11):
                draw.line((x, 2, x, 13), fill=accent + (180,), width=1)
            for y in (4, 11):
                draw.line((2, y, 13, y), fill=accent + (180,), width=1)
            if name in ('energy_core', 'battery', 'neon_block', 'teleporter_block'):
                draw.ellipse((5, 5, 10, 10), fill=accent + (255,))
        if name in ('redwood_log', 'palm_log'):
            draw.line((4, 0, 4, 15), fill=accent + (255,), width=2)
            draw.line((11, 0, 11, 15), fill=accent + (255,), width=1)
        path = root / 'textures/blocks' / (key + '.png')
        path.parent.mkdir(parents=True, exist_ok=True)
        image.save(path)
        save_json('blockstates/' + key + '.json', {'variants': {'normal': {'model': key}}})
        save_json('models/block/' + key + '.json',
                  {'parent': 'block/cube_all', 'textures': {'all': 'blocks/' + key}})
        save_json('models/item/' + key + '.json', {'parent': 'block/' + key})

portal_key = 'crazycraft_future_portal'
portal = Image.new('RGBA', (16, 16), (25, 53, 142, 210))
pd = ImageDraw.Draw(portal)
for y in range(16):
    for x in range(16):
        energy = int(25 * (1 + __import__('math').sin((x + y) * 0.8)))
        portal.putpixel((x, y), (18 + energy // 3, 65 + energy,
                                 min(255, 161 + energy), 185 + energy // 2))
for x in (2, 7, 12):
    pd.line((x, 0, x + 2, 15), fill=(101, 219, 255, 235), width=1)
portal.save(root / 'textures/blocks' / (portal_key + '.png'))
save_json('blockstates/' + portal_key + '.json',
          {'variants': {'axis=x': {'model': portal_key},
                        'axis=z': {'model': portal_key, 'y': 90}}})
save_json('models/block/' + portal_key + '.json',
          {'textures': {'surface': 'blocks/' + portal_key, 'particle': 'blocks/' + portal_key},
           'elements': [{'from': [0, 0, 7], 'to': [16, 16, 9],
                         'faces': {face: {'texture': '#surface'} for face in
                                   ('north', 'south', 'west', 'east', 'up', 'down')}}]})
save_json('models/item/' + portal_key + '.json', {'parent': 'block/' + portal_key})

bot = Image.new('RGBA', (64, 64), (38, 53, 72, 255))
bd = ImageDraw.Draw(bot)
for y in range(0, 64, 8):
    bd.line((0, y, 63, y), fill=(64, 83, 108, 255))
for x in range(0, 64, 8):
    bd.line((x, 0, x, 63), fill=(30, 40, 56, 255))
bd.rectangle((8, 9, 15, 12), fill=(63, 211, 245, 255))
bd.rectangle((16, 9, 23, 12), fill=(63, 211, 245, 255))
bd.rectangle((24, 20, 39, 28), outline=(81, 223, 255, 255), width=2)
bd.ellipse((29, 22, 34, 27), fill=(140, 245, 255, 255))
bd.rectangle((40, 32, 47, 45), fill=(65, 105, 146, 255))
bot_path = root / 'textures/entity/crazycraft/worker_bot.png'
bot_path.parent.mkdir(parents=True, exist_ok=True)
bot.save(bot_path)

print('Generated', len(old) + len(future), 'Crazycraft block asset sets.')
runpy.run_path(str(Path(__file__).with_name('enhance-crazycraft-old-art.py')))
