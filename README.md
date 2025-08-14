# JavaBase

بسم الله الرحمن الرحيم والصلاة والسلام على اشرف المرسلين .

اليوم اقدم لكم مكتبة تحاكي تقنية ORM للاتصال بقاعدة  البيانات من لغة جافا الى اي قاعدة بيانات بشكل تلقائي بدون الاهتمام بتفاصيل الاستعلامات .

<b>ملاحظة</b> : حالياً لم اقم باضافة استعلامات للتواصل مع كل انواع قواعد البيانات وانما فقط مع MYSQL ، لكن يمكنك فعل ذلك من الحزمة المخصصة ```javabase.core.database.querybuilders``` .


هذه المكتبة هي مقتبسة من فكرة ال ORM في اطار العمل الشهير Laravel وقد حاولت سابقاً ان ابني شئ مشابه ستجده في مستودع JDBCLibrary .

## الية العمل

لكي تبدا باستخدام المكتبة عليك اولاً ان تنشئ فئة جديدة ترث من الفئة Model ، سنضرب مثالاُ باستخدام فئة User كالتالي :

~~~ java
public class User extends Model<User>{
    protected int id;
}
~~~

<b>ملاحطة</b> : يجب ان يتم انشاء الفئة ```User``` في الحزمة ```database.models``` .

المثال يوضح بساطة انشاء مودل جديد ولك كامل الحرية في اضافة المزيد من الخصائص والدوال ، لكن عليك ان تنتبه ان تكون الخصائص التي تريد ان يتم تخزينها في قاعدة البيانات لها محدد وصول <u> protected</u>.

 (كنت اعمل على جعلها private وواجهتني بعض المشاكل ساحلها في التحديث القادم مع تمكين الوراثة ان شاء الله)

كما من الضروري ان تحدد النوع العام للمودل الاب ويكون المودل الابن كما في ```Model<User>``` .

بعد ان تنشئ الفئة يتبقى عليك فقط خطوة اخيرة وهي تسجيل الموديل في المسجل ، اذهب الى ```javabase.MyModels.java``` ثم اضف السطر التالي

~~~ java
Recorder.add(User.class);
~~~

تهانينا الان اصبحت الفئة User قادرة على الاتصال بقاعدة البيانات .

## انشاء جدول في قاعدة البيانات

كي لا تضطر لاستعمال دوال من داخل المكتبة بنفسك قمت ببنا مجموعة من الاوامر لتسهل عليك العمل يمكنك تشغيل الفئة Handler المرفقة افتراضياً مع المكتبة للتظهر لك الاوامر المتاحة .

من المهم التنبيه الى ان المكتبة يجب ان تعمل داخل حاوية وهي ```App.start()``` هذه الحاوية تهيئ الاتصال بقاعدة البيانات وتقوم بامور اخرى قبل تنفيذ الاوامر .

لتخفف على نفسك يمكنك كتابة نقطة البداية في فئة ```Main``` المرفقة ايضاً افتراضياً ثم استدعائها داخل الحاوية في ```Handler``` ومن ثم قم بتشغيل ```Handler``` وستسير الامور على ما يرام .

بالعودة الى المحتوى الافتراضي لل ```Handler``` ستجد الاتي :

~~~java
public class Handler {
    public static void main(String[] args) throws Exception {
        App.start(() -> {
            CommandLine.main(args);
        });
    }
}
~~~

الاوامر التي تكلمت عنها موجودة في الفئة ```javabase.CommandLine``` وهي كالاتي .

الامر ```make:model``` يقوم بانشاء مودل جديد ، يعني بدلاً من انشاء فئة ```User``` وجعها ترث وتسجيلها في ```MyModels``` فقط عليك كتابة الامر التالي :
```
make:model Person
```
سيتم انشاء ملف جديد في الحزمة ```database.models``` باسم ```Person.java``` ويحتوي على الاتي : 

~~~java
package javabaseproject.database.models;

import javabaseproject.javabase.core.annotations.PrimaryKey;
import javabaseproject.javabase.core.annotations.Unique;
import javabaseproject.javabase.core.database.models.Model;

@PrimaryKey("id")
public class Person extends Model<Person>{

    protected int id;
    @Unique
    protected String name;

    // Don't delete this constructor please (: it will cause a problem
    public Person(){}

    public Person(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

// ... add more fields with protected access modifier
}
~~~

يمكنك تخصيص القيود على الاعمدة باستخدام الملاحظات Annotations كما في ```@PrimaryKey("id")``` و ```@Unique``` .

استعمل الامر ```db:migrate``` لانشاء جدول في قاعدة البيانات بالاعتماد على الخصائص الموجودة في الفئة :

```
db:migrate Person
```

او اذا كنت تريد انشاء جداول لكل الفئات استعمل

```
db:migrate
```

قبل اي امر عليك ان تنشئ قاعدة البيانات اولاً والتي يمكنك تحديد اسمها في ملف ```javabase.config.ENV``` وانشئها في مدير قواعد البيانات باستخدام الامر :

~~~
db:init
~~~

يمكنك استعمال الامر ```drop:model``` لحذف الموديل وجدوله من قاعدة البيانات :

```
drop:model Person
```

اكتب الامر ```help``` لاستعراض كل الاوامر

## حفظ البيانات وجلبها من قاعدة البيانات

اذا اردت ادخال بيانات الى قاعدة البيانات يمكنك انشاء كائن ثم استدعا الدالة ```save()``` منه كالتالي : 

~~~java
Person person = new Person(1, "Asem");
person.save();
~~~

تم حفظ البيانات في قاعدة البيانات .

استعمل الدالة ```find(Object key)``` او ```getAll()``` لجلب البيانات من قاعدة البيانات : 

```java
Person person = new Person();
person = person.find(1);
```

الدالة ```find``` تقوم بجلب البيانات من القاعدة وتقوم بارجاع كائن مليئ بالبيانات كما في المثال خزنا قيمته في person نفسه ولاحظ ان انشاء الكائن هو فقط لكي نستطيع استخدام الدالة ```find``` ويمكن استبدال الامر السابق بالتالي : 

```java 
Person person = (Person) Model.of(Person.class).find(1);
```

او :

```java
person = Model.find(Person.class, 1);
```

استعمل الدالة ```toJson()``` لتحويل النص الى json لطباعته .

```
Command.println(person.toJson());
```

والمخرجات ستكون :

```json
{
   id:1,
   name:Asem
}
```

## مرجع شامل للدوال التي يمكنك استعمالها

|اسم الدالة| وظيفتها|
|---|---|
|```find(key)``` | جلب البيانات من قاعدة البيانات اعتماداً على المفتاح الرئيسي الذي تم تحديده في الفئة باستخدام @PrimaryKey()|
|```getAll()```| جلب كل الحقول المخزنة في القاعدة وتعيد ```ArrayList``` تحتوي على كائنات من من نفس نوع الفئة التي استدعتها|
|```delete()```| حذف البيانات من قاعدة البيانات اعتماداً على قيمة محتوى المفتاح الرئيسي|
|```factory()```| جلب كائن من كلاس المصنع ، هذا الكلاس يمكنك انشائه باستخدام الامر ```make:factory Person```|
|```seeder()```| جلب كائن من كلاس البذر ، هذا الكلاس يمكنك انشائه باستخدام الامر ```make:seeder Person```|

## مصانع البيانات

مصانع البيانات تساعدك في انشاء كائنات بقيم عشوائية من الفئة ```Faker``` .

<b>ملاحظة : </b> يمكن دعم تعدد اللغات في انشاء قيم عشوائية وذلك من فئة ```javabase.lang.FakeData``` .

استعمل الامر التالي لانشاء مصنع وباذر للفئة ```Perosn``` :

```
make:factory Person
```

الامر السابق لن يكتفي فقط بانشاء المصنع  فقط وانما سينشئ منشئ جديد في الفئة ```Person``` يحتوي على وسائط لكل الخصائص التي يمكن ادخال بيانات اليها لكي يتم استدعائه في الصانع .

دعنا نطبق المثال اضف ```protected String email;``` الى الفئة ```Person``` ثم نفذ امر انشاء المصنع

ستجد انه تم اضافة constructor الى ```Person``` :

```java
public Person(String name, int id, String email){
    this.name = name;
    this.id = id;
    this.email = email;
}
```

وتم اضافة ملف جديد في ```database.factories``` باسم ```PersonFactory``` ويحتوي على :

~~~java
public class PersonFactory extends Factory<Person>{
    public Person item() {
        return new Person(
   Fake.name(),
   Fake.randomNumber(),
   Fake.email()
        );
    }
}
~~~

يمكنك تخصيصه كما تشاء .

لاحظ اننا نستعمل فئة ```Fake``` للحصول على قيم عشوائية ولكنها ليست فريدة لذا غالباً ما تظهر اخطاء عندما نقوم ببذر العديد من الصفوف بسبب جلب اسماء عشوائية وليست فريدة .

## البذور

استعمل الامر

~~~
make:seeder Person
~~~

وستحصل على باذر بيانات للفئة ```Person``` في ```database.seeders``` باسم ```PersonSeeder``` وتحتوي على :

~~~java
public class PersonSeeder extends Seeder {
    public void run() {
        ExceptionHandler.handle(() -> {
            Model.of(Person.class).factory().create(10);
        });
    }
}
~~~

الان استعمل الامر

~~~
start:seeder Person
~~~

وستحصل على 10 صفوف عشوائية البيانات في قاعدة البيانات جرب استدعيها باستعمال الدالة ```getAll()``` :

~~~java
Model.of(User.class)
    .getAll().stream()
    .forEach(item -> Command.println(item.toJson()));
~~~

## الطباعة

لاحظ اننا نستعمل ```Command.println``` للطباعة وهذا يجعلك تلون المخرجات كما تريد كالتالي :

~~~java
Command.println("r{Hello}, World");
~~~

سيتم طباعة الكلمة ```Hello```  باللون الاحمر ، انظر الجدول التالي :

|اللون | لون النص | لون الخلفية|
|---|---|---|
| احمر | r{text} | r[text]|
| اخضر | g{text} | g[text]|
| اصفر | y{text} | y[text]|
| ازرق | b{text} | b[text]|
| بنفسحي | p{text} | p[text]|
| اسود | k{text} | k[text]|
| ابيض | w{text} | w[text]|

<b>ملاحظة : </b> لا يمكن عمل تلوين متداخل .

## خاتمة

ختاما لم استطيع ان اشرح كل التفاصيل حاول اكتشافها بنفسك ، كما ان المكتبة مليئة بالاخطا لكنها تفي بالغرض .

ولا تنسى الصلاة على النبي
