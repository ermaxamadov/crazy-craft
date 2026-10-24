"""Generate small pixel textures for the first Crazycraft Old Dimension content."""
from pathlib import Path
from PIL import Image, ImageDraw
import random

assets = Path(__file__).resolve().parent.parent / 'src/minecraft/assets/minecraft/textures'
blocks = assets / 'blocks'
entities = assets / 'entity/crazycraft'
blocks.mkdir(parents=True, exist_ok=True)
entities.mkdir(parents=True, exist_ok=True)

frame = Image.new('RGBA', (16, 16), (29, 31, 29, 255))
d = ImageDraw.Draw(frame)
rng = random.Random(421)
for y in range(16):
    for x in range(16):
        v = rng.randrange(-10, 11)
        frame.putpixel((x, y), (max(10, 38 + v), max(10, 39 + v), max(10, 31 + v), 255))
for y in (0, 7, 15):
    d.line((0, y, 15, y), fill=(84, 78, 55, 255))
for x, y in ((5, 0), (11, 7), (6, 15)):
    d.line((x, y, x, min(15, y + 6)), fill=(15, 20, 20, 255))
d.rectangle((4, 3, 11, 11), outline=(153, 126, 64, 255))
d.arc((5, 4, 10, 10), 80, 350, fill=(214, 177, 87, 255), width=2)
frame.save(blocks / 'crazycraft_old_frame.png')

portal = Image.new('RGBA', (16, 16), (17, 45, 50, 205))
d = ImageDraw.Draw(portal)
for y in range(16):
    for x in range(16):
        n = rng.randrange(-10, 11)
        glow = int(max(0, 7 - abs(x - 7.5)) * 5)
        portal.putpixel((x, y), (max(0, 17 + n), max(0, 55 + glow + n), max(0, 64 + glow + n), 190 + rng.randrange(36)))
for points in (((0, 13), (3, 10), (4, 6), (8, 4), (12, 1)),
               ((3, 15), (6, 12), (10, 10), (12, 6), (15, 3)),
               ((0, 5), (4, 3), (7, 1))):
    d.line(points, fill=(85, 219, 183, 235), width=1)
for x, y in ((2, 4), (12, 12), (7, 8), (14, 2)):
    d.point((x, y), fill=(238, 225, 137, 255))
portal.save(blocks / 'crazycraft_old_portal.png')

def dinosaur(name, base, shadow, light, accent, seed):
    rand = random.Random(seed)
    im = Image.new('RGBA', (128, 64), base + (255,))
    draw = ImageDraw.Draw(im)
    for y in range(0, 64, 2):
        for x in range(0, 128, 2):
            roll = rand.randrange(7)
            if roll == 0:
                draw.rectangle((x, y, x + 1, y + 1), fill=shadow + (255,))
            elif roll in (1, 2):
                draw.rectangle((x, y, x + 1, y + 1), fill=light + (255,))
    for x in range(5, 95, 13):
        for y in range(5, 55, 12):
            if rand.randrange(3) == 0:
                draw.rectangle((x, y, x + 2, y + 2), fill=accent + (255,))
    if name == 'trex':
        draw.rectangle((55, 11, 57, 13), fill=(226, 189, 73, 255))
        draw.rectangle((70, 11, 72, 13), fill=(226, 189, 73, 255))
        draw.line((52, 39, 76, 39), fill=(235, 214, 166, 255), width=2)
    else:
        draw.rectangle((65, 20, 90, 35), fill=(208, 190, 146, 255))
        draw.line((66, 20, 86, 26), fill=(242, 225, 183, 255), width=3)
    im.save(entities / (name + '.png'))

dinosaur('trex', (73, 90, 43), (38, 59, 31), (112, 126, 68), (144, 113, 54), 182)
dinosaur('triceratops', (112, 108, 67), (69, 73, 45), (154, 147, 92), (179, 149, 91), 327)

ptero = Image.new('RGBA', (128, 64), (102, 69, 42, 255))
pd = ImageDraw.Draw(ptero)
pr = random.Random(712)
for y in range(64):
    for x in range(128):
        shade = pr.randrange(-17, 18)
        membrane = 24 if 39 <= y <= 51 else 0
        ptero.putpixel((x, y), (max(0, 99 + shade + membrane),
                                max(0, 68 + shade + membrane // 2), max(0, 40 + shade), 255))
for x in range(3, 125, 14):
    pd.line((x, 40, min(x + 9, 127), 51), fill=(61, 43, 30, 255), width=2)
pd.ellipse((14, 26, 18, 30), fill=(236, 184, 66, 255))
ptero.save(entities / 'pteranodon.png')
