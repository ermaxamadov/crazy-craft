# CrazyCraft — Old Dimension: Dinozavrlar

> **Hujjat turi:** o‘yin dizayni va implementatsiya spetsifikatsiyasi  
> **Loyiha:** CrazyCraft  
> **Dimension:** Old Dimension  
> **Mavzu:** faqat dinozavrlar — model, o‘lcham, xulq, hujum, qo‘lga o‘rgatish va tushadigan resurslar.

Bu hujjat CrazyCraft’ning Old Dimension qismida paydo bo‘ladigan dinozavrlar qanday ko‘rinishi va o‘yinda qanday ishlashini belgilaydi. Quyidagi HP, damage va boshqa raqamlar **boshlang‘ich balans takliflari** hisoblanadi; ular testdan keyin o‘zgartirilishi mumkin. O‘lchamlar ham o‘yin ichidagi model uchun taxminiy qiymatlardir, paleontologik aniq o‘lchov sifatida qabul qilinmasin.

## 1. Umumiy qoidalar

### Xulq turlari

- **Tinch (Passive):** o‘yinchiga birinchi bo‘lib hujum qilmaydi.
- **Neytral (Neutral):** bezovta qilinmaguncha hujum qilmaydi; zarar ko‘rsa yoki uyasiga yaqinlashilsa javob qaytarishi mumkin.
- **Agressiv (Aggressive):** o‘yinchi yoki o‘lja deb bilgan jonzotni ko‘rsa hujum qiladi.
- **Poda bilan yuruvchi (Pack):** bir nechta kichik yirtqich bir nishonga birgalikda hujum qilishi mumkin.
- **Hududini himoya qiluvchi (Territorial):** uyasi yoki belgilangan hududiga kirgan nishonni quvadi.

### O‘lchov va statlar

- Balandlik va uzunlik metrda beriladi.
- **HP** — dinozavrning hayot miqdori.
- **Damage** — bitta odatiy hujum yetkazadigan zarar.
- **Tame** — qo‘lga o‘rgatish mumkinligi.
- **Target** — dinozavr odatda kimga hujum qilishi.
- **Drop** — yengilganda yoki o‘yindagi tegishli mexanika orqali olinishi mumkin bo‘lgan resurs.

### Umumiy AI talablari

1. Dinozavrlar o‘z turi va xulqiga mos yurish, yugurish, burilish, hujum, jarohatlanish, dam olish va o‘lish animatsiyalariga ega bo‘lishi kerak.
2. O‘txo‘rlar odatda o‘simlik yeydi; yirtqichlar go‘sht bilan oziqlanadi. O‘yinchi ularga yaqinlashsa, javobi turiga qarab farq qiladi.
3. Kichik dinozavrlar o‘yinchini katta yirtqichdan tezroq quvishi yoki poda bo‘lib hujum qilishi mumkin.
4. Ulkan turlar sekinroq harakatlanadi, lekin zarbasi kuchli va ko‘proq HP’ga ega bo‘ladi.
5. Har bir turda alohida ovoz, iz qoldirish effekti, urilish reaksiyasi va kamida bitta o‘ziga xos xatti-harakat bo‘lsin.
6. Dinozavr modeli o‘lchami uning kollayderi, animatsiyasi va haqiqiy zarba masofasi bilan mos bo‘lishi kerak.

## 2. Yirik quruqlik yirtqichlari

### 2.1 Tyrannosaurus Rex (T-Rex)

- **Rol:** Old Dimension’ning eng xavfli quruqlik yirtqichi; mini-boss darajasida.
- **Taxminiy o‘lcham:** balandligi 6 m, uzunligi 12 m.
- **Boshlang‘ich statlar:** 300 HP, 25 damage, tezligi past-o‘rtacha.
- **Xulqi:** agressiv va hududini himoya qiladi. Uzoqdan nishonni ko‘rsa, to‘g‘ri yo‘nalishda quvadi.
- **Kimga hujum qiladi:** o‘yinchi, kichik va o‘rta jonzotlar hamda boshqa yirtqichlar; ulkan o‘txo‘rlarga faqat alohida ov holatida hujum qilishi mumkin.
- **Maxsus hujum:** kuchli tishlash; qisqa masofada nishonni orqaga itaradi. Hujumdan oldin boshini orqaga tortib, ogohlantiruvchi animatsiya ko‘rsatadi.
- **Tame:** oddiy usulda o‘rgatilmaydi. Keyinchalik maxsus endgame mexanikasi qo‘shilsa, juda qiyin bo‘lishi kerak.
- **Drop:** T-Rex tishi, qalin teri, xom go‘sht, kam ehtimol bilan noyob suyak.
- **Paydo bo‘lishi:** yirik o‘rmonlar, ochiq tekisliklar va maxsus yirtqichlar hududi.

### 2.2 Spinosaurus

- **Rol:** suv bo‘yida ov qiluvchi yirik yirtqich.
- **Taxminiy o‘lcham:** balandligi 6.5 m, uzunligi 15 m.
- **Boshlang‘ich statlar:** 280 HP, 22 damage; suvda tezroq harakatlanadi.
- **Xulqi:** suv va quruqlik orasida yuradi; suvdagi o‘lja yaqin bo‘lsa, uni faol ovlaydi.
- **Kimga hujum qiladi:** o‘yinchi, baliq va kichik-o‘rta jonzotlar; hududiga kirgan boshqa yirtqichlarga ham javob berishi mumkin.
- **Maxsus hujum:** tishlash va panja zarbasi; suvda hujum qilganda tezlik bonusi.
- **Tame:** qiyin; maxsus oziq va sabr talab etadi.
- **Drop:** tish, teri, suyak, xom go‘sht va kam ehtimol bilan orqa yelkan uchun dekorativ resurs.
- **Paydo bo‘lishi:** daryolar, botqoqlar va ko‘l qirg‘oqlari.

### 2.3 Giganotosaurus

- **Rol:** juda katta, T-Rex bilan raqobat qiladigan yirtqich.
- **Taxminiy o‘lcham:** balandligi 6.5 m, uzunligi 13 m.
- **Boshlang‘ich statlar:** 290 HP, 23 damage; uzoq vaqt quvishni davom ettira oladi.
- **Xulqi:** agressiv; katta nishonlarni ham quvadi, lekin hujumlari orasidagi tiklanish vaqti T-Rex’dan uzunroq.
- **Kimga hujum qiladi:** o‘yinchi va ko‘pchilik yirik hayvonlar.
- **Maxsus hujum:** kuchli tishlash va qisqa yugurib borib urilish.
- **Tame:** yo‘q.
- **Drop:** tish, qalin teri, suyak, xom go‘sht.
- **Paydo bo‘lishi:** ochiq tekislik va tog‘ etaklari.

### 2.4 Mapusaurus

- **Rol:** poda bilan ov qilishi mumkin bo‘lgan o‘rta-yirik yirtqich.
- **Taxminiy o‘lcham:** balandligi 4 m, uzunligi 10 m.
- **Boshlang‘ich statlar:** 220 HP, 18 damage.
- **Xulqi:** yakka holda ehtiyotkorroq, 2–3 tasi bir joyda bo‘lsa hujumkorlashadi.
- **Kimga hujum qiladi:** o‘yinchi, kichik hayvonlar va zaiflashgan o‘rta hayvonlar.
- **Maxsus hujum:** ketma-ket tishlash; poda bo‘lsa, nishonni ikki tomondan o‘rab olishga harakat qiladi.
- **Tame:** juda qiyin yoki yo‘q — balans testida hal qilinadi.
- **Drop:** tish, teri, suyak, go‘sht.
- **Paydo bo‘lishi:** tekislik va siyrak o‘rmon.

### 2.5 Allosaurus

- **Rol:** tez va moslashuvchan ovchi.
- **Taxminiy o‘lcham:** balandligi 4 m, uzunligi 9 m.
- **Boshlang‘ich statlar:** 200 HP, 17 damage.
- **Xulqi:** agressiv; nishonni qisqa masofada tez quvadi, keyin masofa ochilsa qayta hujum qiladi.
- **Kimga hujum qiladi:** o‘yinchi, kichik va o‘rta hayvonlar.
- **Maxsus hujum:** panja zarbasi va tishlash kombinatsiyasi.
- **Tame:** qiyin.
- **Drop:** tish, tirnoq, teri, xom go‘sht.
- **Paydo bo‘lishi:** o‘rmon cheti va tekislik.

### 2.6 Carnotaurus

- **Rol:** qisqa masofada juda tez hujum qiladigan yirtqich.
- **Taxminiy o‘lcham:** balandligi 3.5 m, uzunligi 6 m.
- **Boshlang‘ich statlar:** 180 HP, 16 damage; yugurish tezligi yuqori.
- **Xulqi:** agressiv; uzoq quvishdan ko‘ra tez yaqinlashib zarba berishga ixtisoslashgan.
- **Kimga hujum qiladi:** o‘yinchi va kichik-o‘rta jonzotlar.
- **Maxsus hujum:** tez yugurib kelib urish yoki tishlash; sovish vaqti bo‘lishi kerak.
- **Tame:** juda qiyin.
- **Drop:** shox, teri, tish, go‘sht.
- **Paydo bo‘lishi:** ochiq tekislik va quruq tepaliklar.

## 3. Kichik va o‘rta yirtqichlar

### 3.1 Velociraptor

- **Rol:** tez, aqlli va poda bilan hujum qiluvchi yirtqich.
- **Taxminiy o‘lcham:** balandligi 1.5 m, uzunligi 2.5 m.
- **Boshlang‘ich statlar:** 70 HP, 8 damage; juda tez yuguradi.
- **Xulqi:** odatda 2–4 tadan guruh bo‘lib harakat qiladi. Yakka raptor o‘yinchidan uzoqlashishi mumkin, ammo poda birgalikda hujum qiladi.
- **Kimga hujum qiladi:** kichik hayvonlar, o‘yinchi va zaiflashgan o‘rta o‘ljalar.
- **Maxsus hujum:** sakrab tashlanish va tez panja zarbalari.
- **Tame:** mumkin; go‘sht bilan o‘rgatish va takroriy ishonch mexanikasi taklif etiladi.
- **Drop:** tirnoq, kichik tish, teri, xom go‘sht.
- **Paydo bo‘lishi:** o‘rmon, paporotnikli hudud va tekislik cheti.

### 3.2 Dilophosaurus

- **Rol:** kichik zaharli hujumchi.
- **Taxminiy o‘lcham:** balandligi 2 m, uzunligi 3 m.
- **Boshlang‘ich statlar:** 90 HP, 7 damage; zahar hujumi qo‘shimcha vaqtinchalik zarar beradi.
- **Xulqi:** o‘yinchini ko‘rsa masofani saqlaydi va ba’zan zahar tupuradi; yaqinlashib qolsa tishlaydi.
- **Kimga hujum qiladi:** o‘yinchi va kichik hayvonlar.
- **Maxsus hujum:** zahar tupurish; ta’siri qisqa bo‘lsin va davolanish bilan kamaytirilishi mumkin.
- **Tame:** mumkin, ammo oddiy go‘shtdan tashqari maxsus o‘lja talab qilishi mumkin.
- **Drop:** tish, teri, zahar bezi va go‘sht.
- **Paydo bo‘lishi:** nam o‘rmon va botqoq cheti.

### 3.3 Compsognathus

- **Rol:** juda kichik, tez va ko‘pincha zararsiz jonzot.
- **Taxminiy o‘lcham:** balandligi 0.6 m, uzunligi 1.5 m.
- **Boshlang‘ich statlar:** 35 HP, 3 damage.
- **Xulqi:** odatda qochadi; katta guruh bo‘lsa, mayda zarbalar bilan himoyalanishi mumkin.
- **Kimga hujum qiladi:** asosan kichik hasharot yoki mayda o‘lja; o‘yinchiga faqat provokatsiya qilinganda javob berishi mumkin.
- **Maxsus xususiyat:** kichik bo‘lgani sababli butalar orasida yashirinadi.
- **Tame:** mumkin.
- **Drop:** kichik suyak, patga o‘xshash material, oz miqdorda xom go‘sht.
- **Paydo bo‘lishi:** o‘rmon tagi, butalar va ochiq tekislik.

### 3.4 Gallimimus

- **Rol:** tez yuguruvchi, asosan qochadigan o‘txo‘r.
- **Taxminiy o‘lcham:** balandligi 2.5 m, uzunligi 6 m.
- **Boshlang‘ich statlar:** 80 HP, 4 damage.
- **Xulqi:** o‘yinchi yoki yirtqich yaqinlashsa yugurib ketadi; to‘siqlarni aylanib o‘tishga harakat qiladi.
- **Kimga hujum qiladi:** odatda hech kimga birinchi bo‘lib hujum qilmaydi.
- **Maxsus xususiyat:** o‘yindagi eng tez quruqlik hayvonlaridan biri.
- **Tame:** mumkin; urug‘, o‘simlik yoki meva bilan.
- **Drop:** pat, xom go‘sht, teri.
- **Paydo bo‘lishi:** keng tekisliklar.

### 3.5 Oviraptor

- **Rol:** tuxum izlaydigan kichik jonzot.
- **Taxminiy o‘lcham:** balandligi 1.5 m, uzunligi 3 m.
- **Boshlang‘ich statlar:** 65 HP, 5 damage.
- **Xulqi:** tuxum yoki uyaga qiziqadi; xavf sezsa chekinadi.
- **Kimga hujum qiladi:** faqat o‘ziga hujum qilgan nishonga yoki tuxumini himoya qilayotgan vaziyatda.
- **Maxsus xususiyat:** yaqin atrofdagi tuxum yoki uya borligini o‘yinchiga bilvosita ko‘rsatishi mumkin.
- **Tame:** mumkin.
- **Drop:** pat, kichik suyak, tuxum po‘chog‘i.
- **Paydo bo‘lishi:** o‘rmon cheti va uyalar yaqinida.

## 4. O‘txo‘r va himoyachi dinozavrlar

### 4.1 Brachiosaurus

- **Rol:** tinch, ulkan va landshaftni jonlantiruvchi o‘txo‘r.
- **Taxminiy o‘lcham:** balandligi 16 m, uzunligi 25 m.
- **Boshlang‘ich statlar:** 400 HP, 12 damage; sekin yuradi.
- **Xulqi:** daraxt barglari bilan oziqlanadi; odatda qochmaydi, lekin xavf tug‘ilsa uzoqlashadi.
- **Kimga hujum qiladi:** birinchi bo‘lib hujum qilmaydi; juda yaqinlashgan yoki hujum qilgan nishonni oyoq bilan bosib himoyalanishi mumkin.
- **Maxsus xususiyat:** yuqori daraxt barglariga yeta oladi; o‘lchami sababli kichik jonzotlar uning ostidan o‘tishi mumkin bo‘lishi mumkin, agar kollayder ruxsat bersa.
- **Tame:** endgame darajasida qiyin yoki faqat kuzatib borish/yo‘naltirish mumkin.
- **Drop:** teri, suyak va go‘sht; o‘ldirish mukofoti katta bo‘lsa ham, ekotizimni saqlash uchun uni ovlashga ehtiyoj tug‘dirmaslik tavsiya etiladi.
- **Paydo bo‘lishi:** ochiq o‘rmon va keng tekislik.

### 4.2 Triceratops

- **Rol:** kuchli himoyachi o‘txo‘r.
- **Taxminiy o‘lcham:** balandligi 4 m, uzunligi 8 m.
- **Boshlang‘ich statlar:** 250 HP, 14 damage.
- **Xulqi:** odatda tinch; yirtqich yaqinlashsa bolalarini yoki podasini himoya qiladi.
- **Kimga hujum qiladi:** hujum qilgan yirtqich, o‘yinchi yoki uya yaqinidagi tahdid.
- **Maxsus hujum:** shox bilan yugurib borib urish; zarba nishonni orqaga uloqtirishi mumkin.
- **Tame:** mumkin, ammo avval ishonch qozonish kerak.
- **Drop:** shox, qalin teri, suyak, o‘simlik yeydigan hayvon go‘shti.
- **Paydo bo‘lishi:** tekislik va siyrak o‘rmon.

### 4.3 Stegosaurus

- **Rol:** orqa plastinalari va dumidagi tikanlar bilan himoyalanadigan o‘txo‘r.
- **Taxminiy o‘lcham:** balandligi 4 m, uzunligi 9 m.
- **Boshlang‘ich statlar:** 220 HP, 12 damage.
- **Xulqi:** sekin yuradi; yaqinlashgan dushmanga dumini siltab javob beradi.
- **Kimga hujum qiladi:** hujum qilgan jonzot yoki o‘yinchi.
- **Maxsus hujum:** dum tikanlari bilan yon tomonga zarba.
- **Tame:** mumkin.
- **Drop:** suyak, plastina materiali, teri.
- **Paydo bo‘lishi:** o‘rmon va tekislik.

### 4.4 Ankylosaurus

- **Rol:** zirhli va juda chidamli himoyachi o‘txo‘r.
- **Taxminiy o‘lcham:** balandligi 3 m, uzunligi 8 m.
- **Boshlang‘ich statlar:** 300 HP, 15 damage; tezligi past.
- **Xulqi:** xavf paydo bo‘lsa o‘zini himoyalaydi; oddiy hujumlar unga nisbatan kamroq zarar yetkazishi mumkin.
- **Kimga hujum qiladi:** hujum qilgan nishon.
- **Maxsus hujum:** og‘ir dum zarbasi; sekin, ammo kuchli va qisqa muddatga karaxt qilishi mumkin.
- **Tame:** mumkin, qiyin.
- **Drop:** zirh plastinasi, suyak, qalin teri.
- **Paydo bo‘lishi:** tekislik, tepaliklar va ochiq o‘rmon.

### 4.5 Parasaurolophus

- **Rol:** podada yuruvchi tinch o‘txo‘r va ogohlantiruvchi jonzot.
- **Taxminiy o‘lcham:** balandligi 4 m, uzunligi 10 m.
- **Boshlang‘ich statlar:** 160 HP, 7 damage.
- **Xulqi:** xavf sezganda maxsus ovoz chiqaradi; yaqinidagi boshqa o‘txo‘rlar qochishni boshlashi mumkin.
- **Kimga hujum qiladi:** odatda hujum qilmaydi; faqat provokatsiya qilinganda o‘zini himoya qiladi.
- **Maxsus xususiyat:** ogohlantiruvchi chaqirig‘i boshqa jonzotlarning AI holatini o‘zgartiradi.
- **Tame:** mumkin.
- **Drop:** teri, suyak, xom go‘sht.
- **Paydo bo‘lishi:** o‘rmon cheti va tekislik.

### 4.6 Iguanodon

- **Rol:** o‘rtacha tezlikdagi ko‘p maqsadli o‘txo‘r.
- **Taxminiy o‘lcham:** balandligi 3.5 m, uzunligi 9 m.
- **Boshlang‘ich statlar:** 170 HP, 9 damage.
- **Xulqi:** tinch; xavf sezganda yugurib qochadi, burchakka tiqilsa o‘zini himoya qiladi.
- **Kimga hujum qiladi:** faqat hujum qilgan nishon.
- **Maxsus xususiyat:** old oyoqlari va boshidan foydalanib qisqa himoya zarbasi bera oladi.
- **Tame:** mumkin.
- **Drop:** teri, suyak, xom go‘sht.
- **Paydo bo‘lishi:** tekislik va o‘rmon.

## 5. Tavsiya etilgan boshlang‘ich balans jadvali

| Dinozavr | Balandlik | Uzunlik | HP | Damage | Xulq | Tame |
|---|---:|---:|---:|---:|---|---|
| Tyrannosaurus Rex | 6 m | 12 m | 300 | 25 | Agressiv, hududiy | Yo‘q / maxsus |
| Spinosaurus | 6.5 m | 15 m | 280 | 22 | Agressiv, suv ovchisi | Juda qiyin |
| Giganotosaurus | 6.5 m | 13 m | 290 | 23 | Agressiv | Yo‘q |
| Mapusaurus | 4 m | 10 m | 220 | 18 | Poda ovchisi | Juda qiyin |
| Allosaurus | 4 m | 9 m | 200 | 17 | Agressiv | Qiyin |
| Carnotaurus | 3.5 m | 6 m | 180 | 16 | Tez, agressiv | Qiyin |
| Velociraptor | 1.5 m | 2.5 m | 70 | 8 | Poda bilan ovlaydi | Ha |
| Dilophosaurus | 2 m | 3 m | 90 | 7 + zahar | Masofali zahar hujumi | Ha, qiyin |
| Compsognathus | 0.6 m | 1.5 m | 35 | 3 | Odatda qochadi | Ha |
| Gallimimus | 2.5 m | 6 m | 80 | 4 | Juda tez, qochadi | Ha |
| Oviraptor | 1.5 m | 3 m | 65 | 5 | Tuxum izlaydi | Ha |
| Brachiosaurus | 16 m | 25 m | 400 | 12 | Tinch, ulkan | Endgame / cheklangan |
| Triceratops | 4 m | 8 m | 250 | 14 | Neytral, himoyachi | Ha |
| Stegosaurus | 4 m | 9 m | 220 | 12 | Neytral, dum bilan himoya | Ha |
| Ankylosaurus | 3 m | 8 m | 300 | 15 | Zirhli himoyachi | Qiyin |
| Parasaurolophus | 4 m | 10 m | 160 | 7 | Tinch, ogohlantiruvchi | Ha |
| Iguanodon | 3.5 m | 9 m | 170 | 9 | Tinch, o‘zini himoya qiladi | Ha |

## 6. Hujum nishonlari va ustuvorlik

AI nishon tanlaganda quyidagi tartibdan foydalanish tavsiya etiladi:

1. **O‘ziga zarar yetkazgan nishon** — o‘zini himoya qiluvchi yoki neytral dinozavr uchun birinchi navbatda.
2. **Uyaga yoki bolalariga tahdid** — hududiy turlar uchun yuqori ustuvorlik.
3. **Oziqa sifatida belgilangan jonzot** — yirtqichning dietasiga mos nishon.
4. **Yaqin va zaif nishon** — poda bilan ovlaydigan turlar uchun.
5. **O‘yinchi** — xavfli turlar uchun; tinch turlar uchun odatiy nishon emas.

Dinozavrlar bir-biriga doim hujum qilmasligi kerak. Aks holda dunyodagi jonzotlar tezda bir-birini yo‘q qiladi. Har bir tur uchun `hostileTargets`, `avoidTargets` va `defendWhenProvoked` kabi alohida sozlamalar bo‘lishi tavsiya etiladi.

## 7. Animatsiya va audio ro‘yxati

Har bir dinozavr modelida imkon qadar quyidagi holatlar bo‘lsin:

- Idle / tinch turish
- Walk / yurish
- Run / yugurish
- Turn / burilish
- Attack / hujum
- Hit / zarba olish
- Roar / ovoz chiqarish
- Eat / oziqlanish
- Sleep yoki Rest / dam olish
- Death / o‘lish
- Swim / suzish — faqat suzishi kerak bo‘lgan turlarda

Ovozlar tur bo‘yicha farqlansin: ulkan yirtqichlar past va kuchli bo‘kiradi, kichik turlar qisqa tovush chiqaradi, o‘txo‘rlar esa bir-birini ogohlantiruvchi chaqiriqlarga ega bo‘lishi mumkin.

## 8. Model va o‘yin ichiga qo‘shish talablari

Har bir dinozavr uchun quyidagilar alohida asset va konfiguratsiyada saqlanishi tavsiya etiladi:

- 3D model va teksturalar
- Animatsiyalar va animatsiya controller’i
- Ovozlar
- O‘lcham, kollayder va zarba masofasi
- HP, damage, tezlik va tajriba qiymatlari
- AI xulqi, hujum nishonlari va qochish qoidalari
- Spawn biome va spawn ehtimoli
- Drop jadvali
- Tame mumkinligi va tame usuli
- Loot, tuxum yoki uya mexanikasi mavjud bo‘lsa, ularning konfiguratsiyasi

**Muhim:** tashqi modelning ko‘rinadigan o‘lchami va kollayder o‘lchami bir-biriga mos bo‘lsin. Ulkan dinozavrlar daraxt yoki qoya ichiga kirib ketmasligi, kichik dinozavrlar esa oddiy bloklardan noto‘g‘ri o‘tib ketmasligi kerak.

## 9. Ishlab chiqish tartibi

1. Avval 3 turdan prototip: Velociraptor, Triceratops va T-Rex.
2. Model, animatsiya, ovoz, AI va damage tizimini tekshirish.
3. O‘txo‘rlar, yirtqichlar va poda xulqini alohida sinash.
4. Qolgan dinozavrlarni guruhlar bo‘yicha qo‘shish.
5. Spawn chastotasi, damage, HP va drop ehtimolini o‘yin balansiga qarab sozlash.
6. FPS, kollayderlar, pathfinding va ko‘p jonzot bir vaqtda mavjud bo‘lgan vaziyatlarda test qilish.

## 10. Qabul qilish mezonlari

- Har bir dinozavr o‘ziga mos ko‘rinish va animatsiyaga ega.
- Tinch dinozavrlar sababsiz o‘yinchiga hujum qilmaydi.
- Yirtqichlar faqat o‘zining target qoidalariga mos jonzotlarni quvadi.
- O‘lcham, kollayder va animatsiya mos keladi.
- Drop va tame mexanikasi takroran test qilinganda barqaror ishlaydi.
- Bir nechta dinozavr birga paydo bo‘lganda AI yoki pathfinding muammosi sabab o‘yin buzilmaydi.

---

**Eslatma:** bu README — dizayn taklifi. Undagi dinozavrlar, raqamlar va mexanikalar hali kodda mavjud degani emas; implementatsiya va testdan keyin yakuniy qiymatlar belgilanadi.
