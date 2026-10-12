برنامه‌ی آپدیت‌شده — ادامه‌ی فیکس باگ‌های Library_ap

## ✅ کارهای انجام‌شده (هر ۱۲ باگ در کد فیکس شدن و کامپایل موفق بود)

1. `InputUtil.java` (جدید) — `readInt(Scanner)` با retry برای ورودی غیرعددی (باگ ۷)
2. `Book.java` — حذف فیلد `available` و `setAvailable`؛ `isAvailable()` از `quantityAvailable` استخراج می‌شه (باگ ۳ و ۱۲)
3. `Student.java` — رمز از `int` به `String` (باگ ۶)
4. `Manager.java` — احراز هویت مدیر با `admin` / `admin123` (باگ ۱۱)
5. `Main.java` — همه‌ی `nextByte()` با `readInt` جایگزین؛ `LibrarianMenu` بازسازی (login یک‌بار، خروج با 0) (باگ ۷، ۱۰)؛ `ManagerMenu` با درگاه login (باگ ۱۱)
6. `Library_Match.java` — `break`های جاافتاده + setter درست (`setYearPublication`) (باگ ۱)؛ `return` بعد از «No book found» (باگ ۲)؛ `setReturned(true)` در `returnBook` (باگ ۴)؛ چاپ واقعی کتاب در جستجوی مهمان (باگ ۵)؛ مقایسه‌ی رشته‌ای رمز در login (باگ ۶)؛ ذخیره‌سازی یکپارچه روی `library.json` با `LocalDateAdapter` و فیلدهای transient، حذف ۷ متد ذخیره/بارگذاری جزئی و متد خراب `loadLibrariansFromFile` (باگ ۸ و ۹)؛ کم/زیاد شدن `quantityAvailable` با lookup کتاب کانونی در approve/return (باگ ۱۲)
7. منوها — گزینه‌ی `0_Exit` / `0_Logout` به همه اضافه شد
8. `lib/gson-2.10.1.jar` دانلود و کل پروژه با `javac` بدون خطا کامپایل شد

## 🔴 باگ جدید (شماره ۱۳) — کشف‌شده در تست دودی

**دو Scanner جدا روی `System.in`**: `Main` یک Scanner خودش رو می‌ساخت و `Library_Match` هم فیلد جداگانه داشت. با ورودی pipe شده اولین Scanner کل بافر ورودی رو می‌بلعه و دومی `NoSuchElementException` می‌گیره؛ در حالت تعاملی هم ریسک گم شدن ورودی هست. (این باگ در کد اصلی هم وجود داشت — تست دودی آشکارش کرد.)

**فیکس (نیمه‌کاره — شروع شده):** یک Scanner مشترک `InputUtil.SCANNER` — ساخته شده در `InputUtil` و `Main` هم‌اینک ازش استفاده می‌کنه.

## ⏳ مراحل باقی‌مونده

**۱. تکمیل فیکس باگ ۱۳ در `Library_Match.java`**
- حذف فیلد `private transient Scanner scanner`
- حذف `lib.scanner = new Scanner(System.in)` از هر دو شاخه‌ی `loadAllData`
- جایگزینی همه‌ی استفاده‌های فیلد `scanner` با `InputUtil.SCANNER` در: `register_Student`، `login_Student`، `searchBook_Student`، `requestBorrowBook_Student`، `searchBook_Guest`، `login_Librarian`
- متدهایی که Scanner رو پارامتر می‌گیرن دست نمی‌خورن (همون instance مشترک پاس داده می‌شه)

**۲. کامپایل مجدد** با `javac -cp lib/gson-2.10.1.jar -d out *.java`

**۳. تست‌های دودی کامل** (در پوشه‌ی موقت `.smoke`، ورودی pipe شده):
- تست ۱ (تکرار): مدیر→کتابدار→کتاب→ثبت‌نام دانشجو با رمز `0123`؛ خروج و بررسی `library.json`
- تست ۲: login دانشجو با `0123` (باگ ۶)، درخواست امانت، تأیید کتابدار (کاهش تعداد نسخه — باگ ۱۲)، برگشت کتاب (افزایش تعداد + `returned=true` — باگ ۴)
- تست ۳: جستجوی مهمان که واقعاً کتاب رو چاپ کنه (باگ ۵)، مشاهده‌ی تاریخچه بدون logout ناگهانی (باگ ۱)، ورودی غیرعددی بدون کرش (باگ ۷)
- بررسی محتوای نهایی `library.json` (تاریخ‌های ISO — باگ ۸)

**۴. پاک‌سازی** فایل‌های تست (`.smoke/` و `library.json` تولیدی)

**۵. گزارش نهایی** — فهرست تک‌تک فیکس‌ها با ارجاع فایل، به‌علاوه‌ی یادداشت درباره‌ی ۲ تغییر کوچک فراتر از ۱۲ باگ: بررسی تکراری‌بودن کتابدار فقط با username (قبلاً username+password بود) و ذخیره‌ی فوری درخواست امانت موقع ثبت.