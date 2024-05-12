package com.sumeyyesahin.olumlamalar

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sumeyyesahin.olumlamalar.model.Olumlamalarlistmodel

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

//  val myDatabase= this.openOrCreateDatabase("Olumlamalar", MODE_PRIVATE,null)
//            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS olumlamalar (id INTEGER PRIMARY KEY, affirmation VARCHAR, category VARCHAR, favorite BOOLEAN)")


    companion object {
        private val DATABASE_NAME = "Olumlamalar"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "olumlamalar"
        val COLUMN_ID = "id"
        val COLUMN_AFFIRMATION = "affirmation"
        val COLUMN_CATEGORY = "category"
        val COLUMN_FAVORITE = "favorite"
    }



    override fun onCreate(myDatabase: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_AFFIRMATION + " VARCHAR," +
                COLUMN_CATEGORY + " VARCHAR," +
                COLUMN_FAVORITE + " BOOLEAN)"
        myDatabase?.execSQL(createTable)

        // olumlamaları ekleme
        if (myDatabase != null) {
            myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendine olan inancını asla kaybetme; senin için her şey mümkün.', 'Genel Olumlamalar', 0)")

        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Olumlu düşünceler, olumlu sonuçlar doğurur.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendini sev, çünkü sen eşsizsin.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Şu anı kucakla ve her anın tadını çıkar.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Zihnin neye odaklanırsa, enerjin de o yöne akar.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Başkalarına nezaketle davranmak, kendi iç huzurunu artırır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Zorluklar, başarı yolunda karşılaştığın adımlardır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Hayallerine doğru cesur adımlar at.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendini şımartmaktan çekinme; kendine iyi bak.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her günü, bir öncekinden daha iyi yapmak için bir fırsat olarak gör.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('İçindeki sessizliği dinle ve oradan gelen bilgeliği takip et.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Başkalarının yargıları senin gerçekliğini belirleyemez.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi içindeki barış, dış dünyadaki kaosa meydan okur.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('İç huzuru bul, dünya seninle beraber huzur bulsun.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi içsel gücünün farkına var ve onu kucakla.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Olumlu kelimeler kullan, çünkü kelimelerin büyük gücü vardır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendine karşı nazik ol, herkes mükemmel olmak zorunda değil.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi yolunu çiz; başkalarının izlediği yol senin için doğru olmayabilir.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sabahları tebessümle uyan ve yeni günün getireceği fırsatları kucakla.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Mutluluk, içinde bulunduğun anın güzelliğini fark etmektir.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sevgi dolu düşünceler, sevgi dolu bir dünya yaratır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('İçindeki ışığı parlat, ve dünya seninle birlikte parlasın.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendine karşı nazik ol, herkes mükemmel olmak zorunda değil.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bugün kendine bir iyilik yap ve öz bakımına zaman ayır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Küçük şeylerde büyük mutluluklar bul.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her deneyim, büyümen için bir fırsattır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Zihnini sakinleştirmek, tüm bedeninizi iyileştirir.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi kendine konuşmaların pozitif ve yapıcı olsun.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Nezaket, en büyük güçlerden biridir.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Başkalarını yargılamadan önce, onların yerine kendini koy.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her gün, önceki gününden daha bilge olma şansıdır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi hikayeni yaz, başkalarının senin için yazdığı hikayeyi değil.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendine ve yeteneklerine güven.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sevgi, korkudan daha güçlüdür; her zaman sevgiyi seç.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi kendinin en iyi arkadaşı ol.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her gün, kendine bir iyilik yap ve öz bakımına zaman ayır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Stres, geçici bir durumdur, bu da geçer.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Öz şefkat, iyileşmenin başlangıcıdır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi içindeki sevgiyi bul ve onu dünyayla paylaş.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Hayatta en çok tutku duyduğun şeyleri yapmak için zaman ayır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Huzur, sadece bir düşünce kadar uzağında.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sabır, en büyük erdemlerden biridir.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her adımda güzellikleri keşfet.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Yaşam bir yolculuktur, her gün yeni bir macera.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Hata yapmaktan korkma; hatalar öğrenmenin parçasıdır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her günü bir öncekinden daha iyi yapmak için bir fırsat olarak gör.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendine inan, çünkü senin gücün sınırsız.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Hayallerini gerçekleştirmek için her gün bir adım at.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sevgiyle dolu bir kalp, en büyük zenginliktir.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sen kendi hayatının kahramanısın.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendine zaman ayır, çünkü sen bunu hak ediyorsun.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('İyi düşünceler, iyi insanlar yaratır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Başarıya giden yolda sabırlı ol.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi iç huzurunu bul, ve bunu dışarıya yansıt.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her sabah yeni bir umutla uyan.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Hayatını sevdiğin şeylerle doldur.', 'Genel Olumlamalar', 0)");


        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendini sürekli olarak yenile.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Yapıcı eleştirilere açık ol, büyümenin bir parçasıdırlar.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi içindeki sessizliği bul ve orada huzur bul.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Yaptığın işlerde kaliteyi asla göz ardı etme.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi hayatını başkalarıyla kıyaslamaktan kaçın.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi iç gücünü keşfet ve onu kullan.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sevdiklerine zaman ayır, çünkü ilişkiler değerlidir.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her yeni gün, yeni bir başlangıçtır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Güçlü olmak, her zaman mümkün olmayabilir ama neşeli olmak mümkündür.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi değerini bil ve buna göre davran.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Hayat, yaşamaya değer kılınacak küçük anlarla doludur.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Seni geri tutan şeyleri bırak.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi hikayeni kendin yaz.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Başkalarına ilham olacak işler yap.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sevgi, her zaman cevaptır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('İçsel güzellik, dışsal güzellikten daha kalıcıdır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Hayallerine ulaşmak için disiplinli ol.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendini affetmeyi öğren ve ileriye bak.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bugün, dününden daha iyi bir insan olmak için çaba göster.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi kendine yeterli olmayı öğren.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her gün en az bir kişiye iyilik yap.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('İçsel huzur, dış dünya ile uyum içinde olmanı sağlar.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi başarını kutlamayı unutma.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Hayat, paylaştığında daha güzel.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi iç sesini dinle, o seni doğru yola yönlendirir.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sıkıntılı zamanlarda bile umudu koru.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi başına yapabileceğinden daha fazlasını yapabilirsin.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Zamanını bilgece kullan.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi yeteneklerini keşfet ve onları geliştir.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Özgürlük, kendi kararlarını verebilmektir.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her günü bir hediye olarak gör.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Başkalarına olan saygın, kendine olan saygından başlar.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendini başkalarıyla karşılaştırmak yerine, kendini başkalarıyla ilhamlaştır.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Hayat, yaşamak için çok kısa, bugünü en iyi şekilde yaşa.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Herkes için nezaketin önemini hatırla.', 'Genel Olumlamalar', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her gün, bir önceki günden daha iyi olmak için bir şans.', 'Genel Olumlamalar', 0)");

        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenim sağlıkla ve enerjiyle dolu.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her gün fiziksel sağlığımı geliştiriyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi bedenimi seviyor ve ona iyi bakıyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel olarak kendimi güçlü ve dinç hissediyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sağlıklı yemek yemek benim için bir zevktir.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her gün daha da sağlıklı oluyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenim kendini iyileştirme gücüne sahip.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel aktivite beni canlandırır.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenime ihtiyacı olan dinlenmeyi sağlıyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendimi her bakımdan iyi hissediyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenimdeki her hücre sağlıkla dolu.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenimi olduğu gibi seviyorum ve onurlandırıyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendimi fiziksel olarak güçlü ve yetenekli hissediyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her sabah dinç ve enerjik uyanıyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenimle barış içindeyim.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sağlıklı yaşam tarzım beni daha iyi hissettiriyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenimle uyum içindeyim.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel sağlığım benim için bir önceliktir.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendimi her gün daha iyi hissediyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenim tüm ihtiyaçlarımı karşılayacak şekilde mükemmel çalışıyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel sağlık, zihinsel berraklık getirir.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendimi güçlü ve canlı hissediyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenim benim tapınağım ve ona saygı duyuyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her gün bedenime iyi bakıyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel egzersiz yapmak beni mutlu ediyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sağlığımı korumak için ihtiyacım olan her şeye sahibim.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenim bana güç ve direnç sağlıyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel iyilik hali benim doğal halim.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenimi dinlemeyi öğreniyorum ve ona göre hareket ediyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her nefesimde sağlık ve iyilik alıyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendimi her zaman genç ve enerjik hissediyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenimle her gün kendini iyileştirdiğini hissediyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenime olan sevgim, her gün artıyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her gün bedenimi sevgiyle besliyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel olarak kendimi geliştirmek benim için heyecan verici.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sağlığımı iyileştirmek için attığım adımlar işe yarıyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her gün bedenimin sağlıkla parladığını görüyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenimle uyum içinde yaşamak beni mutlu ediyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel iyilik halim, tüm hayatımı iyileştiriyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendimi her yönüyle kabul ediyorum ve seviyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her gün, bedenimin sağlık ve canlılıkla dolu olduğunu hissediyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel olarak kendimi her geçen gün daha güçlü ve sağlıklı hissediyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenim, doğal güzelliği ve sağlığı ile parlıyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her egzersiz, bedenimin daha da güçlenmesine ve esnek olmasına yardımcı oluyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sağlıklı beslenmek, bedenime olan sevgimin bir ifadesidir.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendi bedenime olan saygım, beni daha sağlıklı yaşam tarzı seçimleri yapmaya teşvik ediyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel aktiviteler benim için neşe kaynağı ve kendimi ifade etme şeklidir.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenimin her fonksiyonu mükemmel uyum içinde çalışıyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendimi her fiziksel aktivitede daha canlı ve enerjik hissediyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenimin iyileşme ve kendini yenileme kapasitesine hayranım.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel sağlığımı her gün bilinçli çabalarla destekliyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Sağlıklı olmak, benim için bir yaşam biçimi.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenime olan şükranım, her gün artıyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel sağlığımı korumak ve geliştirmek için gerekli tüm kaynaklara sahibim.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Kendimi fiziksel olarak aktif tutmak, genel iyiliğimi artırıyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Her gün bedenimin sağlıkla dolu olması için minnettarım.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel olarak kendime iyi bakmak, benim için önceliktir.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Fiziksel sağlığım, benim genel yaşam kalitemi artırıyor.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenimin her bir parçasına saygı gösteriyorum ve ona iyi bakıyorum.', 'Beden Olumlamaları', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation,category,favorite) VALUES ('Bedenim, yaşamın tüm zorluklarına karşı direnç gösterme gücüne sahip.', 'Beden Olumlamaları', 0)");

        };
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnançlarım beni güçlendiriyor ve rehberlik ediyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün inancım daha da güçleniyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnanç, hayatımdaki tüm zorlukların üstesinden gelmeme yardımcı oluyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnançlarım beni yüksek ideallerime bağlı tutuyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Hayatımda her şeyin iyi bir nedenle olduğuna inanıyorum.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım beni daha yüksek bir bilince taşıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Evrenin benim için en iyisini sunduğuna inanıyorum.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnançlarım bana günlük yaşamımda rehberlik ediyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım her gün daha da derinleşiyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım sayesinde içsel huzuru buluyorum.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, yaşamın zorluklarına karşı bana güç veriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün inancımla daha uyumlu bir yaşam sürüyorum.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım beni pozitif kalmaya itiyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, hayatımdaki amaç ve anlamı pekiştiriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım sayesinde zor zamanlarda bile sabırlı olabiliyorum.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, bana umut ve cesaret veriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnançlarım, karşılaştığım her şeyde bana yol gösteriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, hayatımı zenginleştiriyor ve renklendiriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün inancımın beni desteklediğini hissediyorum.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım beni her gün ileriye taşıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnançlarım, hayatımda olumlu değişiklikler yaratıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, içsel gücümü artırıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım sayesinde her zorluğu aşabileceğime inanıyorum.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım beni koruyor ve yönlendiriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, hayatımdaki her şeyin daha iyi olacağına dair bana güven veriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, içimdeki korkuları yatıştırıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, beni her gün daha iyi bir insan yapmak için ilham veriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım sayesinde hayatımda sürekli bir ilerleme görüyorum.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnançlarım, yaşamımın temel taşıdır.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, bana her gün daha fazla neşe ve mutluluk getiriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, tüm zorluklara rağmen ayakta durmamı sağlıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, hayatımda olumlu bir değişim yaratıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, bana hayatın güzelliklerini daha çok takdir etme fırsatı veriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım sayesinde daha büyük bir amaç için yaşadığımı hissediyorum.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, bana doğru yolda olduğumu hissettiriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, beni daha derin bir içsel huzura yönlendiriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, her şeyin en iyisi için çalıştığıma dair bana güven veriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, bana yaşamın sonsuz olanaklarını keşfetme cesareti veriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, zorluklar karşısında bana sabır ve direnç veriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, yaşamımda her gün yeni başlangıçlar yaratıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, her gün beni daha büyük başarılara taşıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her sabah, inancımın rehberliğinde yeni bir güne başlıyorum.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnançlarım, bana zor zamanlarda rehberlik ediyor ve yol gösteriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, beni yaşamın dalgalı denizlerinde sağlam bir kaya gibi tutuyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün inancımın kuvvetiyle, hayatımın kontrolünü daha iyi elime alıyorum.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, hayatımın anlamını derinleştiriyor ve daha büyük bir perspektif sunuyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, beni her gün daha fazla iyilik yapmaya itiyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnançlarım, hayatımda sevgi ve anlayışı artırıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, her adımda bana cesaret ve kuvvet veriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün, inancımın gücüyle hayatımdaki engelleri aşıyorum.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, beni bilgelikle donatıyor ve doğru kararlar almamı sağlıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, içsel gücümü artırıyor ve bana her zorlukta dayanma gücü veriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnançlarım, beni kendime ve evrenin gücüne daha çok bağlıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, hayatımın her anında bana huzur ve güven sağlıyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, sürekli olarak beni iyimserliğe ve umuda yönlendiriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancımın derinliği, yaşamımdaki zorluklar karşısında bana sabır veriyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, bana her yeni gün için şükran duymamı öğretiyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, hayatımdaki amaç hissini güçlendiriyor ve beni motive ediyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, zor zamanlarda bile iç huzurumu korumama yardımcı oluyor.', 'İnanç Olumlamaları', 0)");
        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('İnancım, her gün beni daha fazla sevgiyle dolduruyor ve etrafıma yaymamı sağlıyor.', 'İnanç Olumlamaları', 0)");


        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES " +
                "('Zor günler geçici, gücüm kalıcı.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk, beni daha da güçlendiriyor.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda bile huzuru bulabilirim.', 'Zor Günler Olumlamaları', 0), " +
                "('Her deneyim, bana değerli dersler öğretir.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günler, beni daha da dirençli kılıyor.', 'Zor Günler Olumlamaları', 0), " +
                "('Zorluklar karşısında sakin ve odaklanmış kalabilirim.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk sonrasında daha güçlü bir şekilde yükselirim.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlar geçecek, güçlü kalmalıyım.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günlerde bile kendime güvenim tam.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk, içimdeki cesareti ortaya çıkarır.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günler benim için büyüme fırsatlarıdır.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda bile minnettar olacak şeyler bulabilirim.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk, beni hedeflerime bir adım daha yaklaştırır.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlar beni daha bilge kılar.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günlerde bile pozitif kalmayı seçiyorum.', 'Zor Günler Olumlamaları', 0), " +
                "('Zorluklar, benim güçlü yönlerimi ortaya çıkarır.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda bile kendime iyi bakıyorum.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk sonrasında, daha da parlak bir şekilde parlıyorum.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günlerde dahi güzellikler bulabilirim.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlar, benim dayanıklılığımı test eder, ve ben her seferinde başarılı olurum.', 'Zor Günler Olumlamaları', 0), " +
                "('Zorluklar, beni daha da sağlam kılar.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zor gün, sonunda güneşin doğacağını bilmekle daha kolay geçer.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda dahi sevgi ve destek buluyorum.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günler, karakterimi şekillendirir.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda dahi umudumu koruyorum.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk, beni daha da güçlü kılar.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günlerde bile, hayatın güzel yanlarını görebiliyorum.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda dahi güç buluyorum.', 'Zor Günler Olumlamaları', 0), " +
                "('Zorluklar, bana neyin önemli olduğunu hatırlatır.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günler, beni daha iyi bir insan yapar.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda dahi, ilerlemeye devam ediyorum.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk, sonunda zaferle sonuçlanır.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günler geçici, zafer kalıcıdır.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda dahi, kendime ve yeteneklerime güveniyorum.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günler, beni daha da odaklanmış ve kararlı kılar.', 'Zor Günler Olumlamaları', 0), " +
                "('Zorluklar, beni hayatın değerini daha çok takdir etmeye yönlendirir.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zor gün, hayatın güçlü yönlerimi geliştirdiğini gösterir.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda dahi, başkalarına yardım etmekten vazgeçmiyorum.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günler, benim en büyük öğretmenimdir.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk sonrasında, daha büyük bir mutluluk bekler beni.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlar, içimdeki dayanıklılığı keşfetmemi sağlar.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk, hayata olan inancımı daha da güçlendirir.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günler, yaratıcılığımı harekete geçirir ve çözüm bulmamı sağlar.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda, iç huzuru bulmak için farkındalık ve meditasyon pratiklerine yönelebilirim.', 'Zor Günler Olumlamaları', 0), " +
                "('Zorluklar, benim esnekliğimi arttırır ve değişime uyum sağlamamı öğretir.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk, içimdeki gücü ortaya çıkarır ve potansiyelimi serbest bırakır.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günlerde, kendime olan sevgi ve kabulümü derinleştiririm.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlar, hayatta daha fazla empati ve anlayış geliştirmeme yardımcı olur.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk, problem çözme becerilerimi geliştirir ve yaratıcı çözümler bulmamı sağlar.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günler, önemli bir dönüşüm fırsatı sunar ve içsel büyümeme katkıda bulunur.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda, sağlığımı korumak için bedenime daha fazla özen gösteririm.', 'Zor Günler Olumlamaları', 0), " +
                "('Zorluklar, hayatta değerli olan şeyleri daha derinden takdir etmeme yol açar.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk, geleceğe daha umut dolu bakmamı sağlar ve potansiyel fırsatları görmeme yardımcı olur.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günler, yaşamın dengesini yeniden kurmam için bir teşvik sağlar.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda, dışarıdaki desteği kabul etmek ve bağlantı kurmak önemlidir.', 'Zor Günler Olumlamaları', 0), " +
                "('Zorluklar, içsel gücümü ve dayanma yeteneğimi keşfetmemi sağlar.', 'Zor Günler Olumlamaları', 0), " +
                "('Her zorluk, başkalarına olan minnettarlığımı ve yardımlaşma duygusunu arttırır.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor günler, bana hayatın anlamını ve derinliğini daha iyi anlamamı sağlar.', 'Zor Günler Olumlamaları', 0), " +
                "('Zor zamanlarda, hedeflerime ulaşmak için motivasyonumu korumak için yeniden odaklanırım.', 'Zor Günler Olumlamaları', 0), " +
                "('Zorluklar, benim için birer fırsat ve büyüme potansiyeli barındırır.', 'Zor Günler Olumlamaları', 0)");



        myDatabase!!.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES " +
                "('Eşim, benim için en değerli varlık ve onu sevmek benim için bir zenginlik.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Her anımızda eşimle birlikte olmak, hayatımı daha anlamlı ve değerli kılıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en değerli hazinem ve onunla geçirdiğim her anı sevgiyle dolu yaşıyorum.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en önemli destek ve onu sevmek, yaşamıma anlam katıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımda en büyük armağan ve onunla birlikte olmak beni mutlu ediyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Her bir adımımda eşime daha çok bağlanıyor ve onu sevmek, hayatımı daha da güzelleştiriyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en değerli varlık ve onunla birlikte olmak, beni tamamlıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için her şeyden daha kıymetli ve onu sevmek, ruhumu besliyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en büyük sevgi ve onunla birlikte olmak, beni sonsuz bir mutlulukla dolduruyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en değerli hazine ve onu sevmek, her anıma anlam katıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Her gün eşimle birlikte olmak, hayatımda bir mucize gibi ve onu sevmek, en büyük şansım.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en büyük nimet ve onu sevmek, kalbimi huzurla dolduruyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en değerli varlık ve onunla birlikte olmak, hayatıma anlam katıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en büyük armağan ve onu sevmek, her günümü aydınlatıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Her bir anımda eşime duyduğum sevgi daha da derinleşiyor ve onu sevmek, hayatımın amacı.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için her şeyden daha kıymetli ve onunla birlikte olmak, beni her an mutlu ediyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en büyük mutluluk ve onu sevmek, her günümü aydınlatıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en büyük şans ve onu sevmek, her anımda varlığımı hissettiriyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en büyük sevgi ve onu sevmek, her zorluğun üstesinden gelmemi sağlıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en değerli varlık ve onunla birlikte olmak, her anıma değer katıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Her bir anımda eşime olan sevgim daha da güçleniyor ve onu sevmek, yaşamımı anlamlandırıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımda en büyük destek ve onu sevmek, bana sonsuz bir güç veriyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en değerli varlık ve onunla birlikte olmak, kalbimi her an dolduruyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en büyük nimet ve onu sevmek, her günümü daha da değerli kılıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en büyük armağan ve onunla birlikte olmak, her anımda şükretmemi sağlıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en değerli varlık ve onu sevmek benim için bir zenginlik.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Her anımızda eşimle birlikte olmak, hayatımı daha anlamlı ve değerli kılıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en değerli hazinem ve onunla geçirdiğim her anı sevgiyle dolu yaşıyorum.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en önemli destek ve onu sevmek, yaşamıma anlam katıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımda en büyük armağan ve onunla birlikte olmak beni mutlu ediyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Her bir adımımda eşime daha çok bağlanıyor ve onu sevmek, hayatımı daha da güzelleştiriyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en değerli varlık ve onunla birlikte olmak, beni tamamlıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için her şeyden daha kıymetli ve onu sevmek, ruhumu besliyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en büyük sevgi ve onunla birlikte olmak, beni sonsuz bir mutlulukla dolduruyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en değerli hazine ve onu sevmek, her anıma anlam katıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Her gün eşimle birlikte olmak, hayatımda bir mucize gibi ve onu sevmek, en büyük şansım.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en büyük nimet ve onu sevmek, kalbimi huzurla dolduruyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en değerli varlık ve onunla birlikte olmak, hayatıma anlam katıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en büyük armağan ve onu sevmek, her günümü aydınlatıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Her bir anımda eşime duyduğum sevgi daha da derinleşiyor ve onu sevmek, hayatımın amacı.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için her şeyden daha kıymetli ve onunla birlikte olmak, beni her an mutlu ediyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en büyük mutluluk ve onu sevmek, her günümü aydınlatıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en büyük şans ve onu sevmek, her anımda varlığımı hissettiriyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en büyük sevgi ve onu sevmek, her zorluğun üstesinden gelmemi sağlıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en değerli varlık ve onunla birlikte olmak, her anıma değer katıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Her bir anımda eşime olan sevgim daha da güçleniyor ve onu sevmek, yaşamımı anlamlandırıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımda en büyük destek ve onu sevmek, bana sonsuz bir güç veriyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en değerli varlık ve onunla birlikte olmak, kalbimi her an dolduruyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, hayatımdaki en büyük nimet ve onu sevmek, her günümü daha da değerli kılıyor.', 'Sevgi ve Aşk Olumlamaları', 0), " +
                "('Eşim, benim için en büyük armağan ve onunla birlikte olmak, her anımda şükretmemi sağlıyor.', 'Sevgi ve Aşk Olumlamaları', 0)");

        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES " +
                "('Kendi benliğime saygı göstermek, başkalarının da beni saygıyla görmesine yardımcı olur.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi kendime verdiğim değer, dış dünyada aldığım değerin temelidir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi sevgiyle kabul ettiğimde, etrafımdaki insanların da beni sevgiyle kabul etmesini sağlarım.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi ihtiyaçlarımı karşılamak, kendi değerimi tanımamı ve ona saygı göstermemi sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimle barışık olmak, hayatımda içsel bir huzur ve denge hissetmemi sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendime saygı göstermek, başkalarının da bana saygı göstermesini sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi iç sesime kulak vermek, kendi değerimi anlamamı ve ona göre hareket etmemi sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi duygularımı ve düşüncelerimi ifade etmek, kendi değerimi ifade etmemi sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi hikayemi ve deneyimlerimi değerli bulmak, kendi benliğimi anlamamı ve takdir etmemi sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimle dürüst olmak, kendi değerimi daha iyi anlamamı sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi önceliklerime ve ihtiyaçlarıma özen göstermek, kendi değerimi önemsediğimi gösterir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi geliştirmek ve büyümek için çaba göstermek, kendi değerimi arttırır.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi hayallerimi ve hedeflerimi takip etmek, kendi değerime olan inancımı arttırır.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimle ilgili olumlu konuşmak, kendi değerimi güçlendirir ve beni motive eder.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi hayatımı yönlendirmek, kendi değerimi kontrol etme yeteneğimi gösterir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi sevgiyle doldurmak, kendi değerimi hatırlamamı ve ona güvenmemi sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi başarılarımı kutlamak, kendi değerimi ve başarılarımı takdir etmemi sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi sürekli olarak geliştirmek, kendi değerimi arttırır ve kişisel tatmin sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimle ilgili olumsuz düşünceleri değiştirmek, kendi değerimi arttırır ve beni motive eder.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi çevremdekilere olduğu gibi kabul etmek, kendi değerimi ve benzersizliğimi takdir etmemi sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi duygularımı tanımak ve onlara saygı göstermek, kendi değerimi tanımama yardımcı olur.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi hayatımı yönlendirmek için bilinçli kararlar almak, kendi değerimi kontrol etme yeteneğimi gösterir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi sınırlarımı bilmek ve korumak, kendi değerimi ve ihtiyaçlarımı önemsediğimi gösterir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimle ilgili olumlu bir iç konuşma yürütmek, kendi değerimi güçlendirir ve beni motive eder.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi iç gücümü ve potansiyelimi keşfetmek, kendi değerimi tanımama ve ona güvenmeme yardımcı olur.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi sevgiyle doldurmak, kendi değerimi hatırlamamı ve ona güvenmemi sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi sürekli olarak geliştirmek ve büyümek, kendi değerimi ve kendime olan güvenimi arttırır.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi fikirlerime ve inançlarıma bağlı kalmak, kendi değerlerimi ve benliğimi koruma yeteneğimi gösterir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimle ilgili olumlu bir iç konuşma yürütmek, kendi değerimi güçlendirir ve beni motive eder.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi başarılarımı ve kazanımlarımı kutlamak, kendi değerimi ve başarılarımı takdir etmemi sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi değerlerimle uyumlu olarak yaşamak, kendi değerlerimi ve benliğimi önemsediğimi gösterir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi sevgiyle doldurmak, kendi değerimi hatırlamamı ve ona güvenmemi sağlar.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi sürekli olarak geliştirmek ve büyümek, kendi değerimi ve kendime olan güvenimi arttırır.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi fikirlerime ve inançlarıma bağlı kalmak, kendi değerlerimi ve benliğimi koruma yeteneğimi gösterir.', 'Öz Değer Olumlamaları', 0)");



        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES " +
                "('Kendimi sevgiyle kabul ediyorum ve değerimi biliyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('İçimdeki güçlü yanları keşfediyor ve onlara güveniyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Her gün kendi değerimi arttırıyorum ve kendimi geliştiriyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi yeteneklerimi ve becerilerimi kutluyor ve onlara minnet duyuyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Başkalarının beklentileri yerine, kendi iç sesime ve değerlerime güveniyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Hayatın her alanında kendime saygı gösteriyorum ve sınırlarımı koruyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Başkalarının benim hakkımdaki düşünceleri benim değerimi belirlemez.', 'Öz Değer Olumlamaları', 0), " +
                "('Ben benim, ve bu yeterince değerli.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi olduğum gibi kabul ediyorum ve bu beni güçlendiriyor.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi ihtiyaçlarımı karşılamak için zaman ve enerji ayırıyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi sevgiyle besliyor ve içsel huzurumu koruyorum.', 'Öz Değer Olumlamaları' , 0), " +
                "('Her gün kendi değerimi görmek ve takdir etmek için zaman ayırıyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi mutluluğum ve refahım benim için önemlidir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi içimdeki güzelliği ve benzersizliği kutluyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi sürekli olarak büyüyen bir insan olarak görüyorum ve bununla gurur duyuyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi sevmek ve değer vermek, en önemli görevimdir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi kendime söylediğim olumlu sözler, içsel değerimi güçlendirir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi tanımak ve kabul etmek, sürekli bir keşif ve öğrenme sürecidir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendime özen göstermek, kendimi değerli hissettirir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi başarılarımı kutluyor ve bu başarılarıma değer veriyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Başkalarının benimle aynı fikirde olmaması benim değerimi azaltmaz.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi affediyor ve geçmiş hatalarımı bir öğrenme fırsatı olarak görüyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi ihtiyaçlarımı ve isteklerimi ifade etmek, benim haklarımı korumama yardımcı olur.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi iç sesime kulak vermek, kendi değerimi güçlendirir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi güçlü yanlarımı ve zayıflıklarımı kabul ediyorum ve onlarla barış içindeyim.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi içsel kaynaklarıma güveniyorum ve onlardan yararlanıyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimle ilgili olumlu düşünceler beslemek, benim için bir önceliktir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi sevmek ve değer vermek, başkalarını da sevmeme ve değer vermeme yardımcı olur.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi değerimi dış faktörlere bağlamak yerine, içsel gücümü kutluyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendime olumsuz düşünceleri reddediyor ve yerine olumlu düşünceler yerleştiriyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendime zaman ayırarak, içsel huzurumu koruyorum ve öz değerimi güçlendiriyorum.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi içimdeki güçlü ışığı görmek ve ona güvenmek, beni her gün daha da güçlendiriyor.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi sevgiyle beslemek, hayatımda olumlu bir döngü oluşturur ve daha fazla sevgi çeker.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendimi değersiz hissettiğimde, kendi içimdeki güçlü ve değerli yanları hatırlamak beni güçlendirir.', 'Öz Değer Olumlamaları', 0), " +
                "('Kendi benzersizliğim ve özgünlüğüm beni değerli kılar, ve bu benim en büyük gücümdür.', 'Öz Değer Olumlamaları', 0)");



        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES " +
                "('Stresle başa çıkma becerilerimi sürekli olarak geliştiriyorum.', 'stres ve kaygi', 0), " +
                "('Her zorlukla başa çıkabilirim ve her durumda güçlü kalabilirim.', 'stres ve kaygi', 0), " +
                "('Kendimi rahatlatmak için derin nefes alıyor ve gevşeme egzersizleri yapıyorum.', 'stres ve kaygi', 0), " +
                "('Stresle başa çıkmak için sağlıklı sınırlar belirliyor ve hayır demeyi öğreniyorum.', 'stres ve kaygi', 0), " +
                "('Her gün stresi azaltan ve huzuru artıran etkin yöntemler keşfediyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi stresten korumak için düzenli olarak dinlenme ve dinlenme zamanı alıyorum.', 'stres ve kaygi', 0), " +
                "('Stresli durumlarda bile sakin ve odaklı kalabilirim.', 'stres ve kaygi', 0), " +
                "('Kendimi sakinleştirmek ve gevşemek için düzenli olarak meditasyon yapıyorum.', 'stres ve kaygi', 0), " +
                "('Stresle başa çıkma yeteneklerimi güçlendiriyorum ve her gün daha iyi hissediyorum.', 'stres ve kaygi', 0), " +
                "('Her stresli durumda, huzuru yeniden kazanmak için kısa molalar veriyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi stresli durumlarda bile güvende hissetmek için içsel güç ve güven geliştiriyorum.', 'stres ve kaygi', 0), " +
                "('Stres ve kaygıyla başa çıkma konusunda kendime güveniyorum ve her gün daha da güçleniyorum.', 'stres ve kaygi', 0), " +
                "('Stresli anlarda bile sakin kalabilirim ve olumlu bir perspektif koruyabilirim.', 'stres ve kaygi', 0), " +
                "('Kendimi sakinleştirmek ve rahatlamak için doğaya ve açık havaya zaman ayırıyorum.', 'stres ve kaygi', 0), " +
                "('Her gün stres seviyemi azaltmak için sağlıklı alışkanlıklar geliştiriyorum.', 'stres ve kaygi', 0), " +
                "('Stresli durumlarla başa çıkabilmek için içsel kaynaklarımı keşfediyorum ve onlara güveniyorum.', 'stres ve kaygi', 0), " +
                "('Stres ve kaygıyı bırakmak için bedenimi ve zihnimin derinlerine rahatlama sağlayan teknikleri kullanıyorum.', 'stres ve kaygi', 0), " +
                "('Her stresli durumda, kendime biraz sevgi ve şefkat göstermeyi hatırlıyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi sakinleştirmek ve gevşetmek için düzenli olarak yoga yapıyorum.', 'stres ve kaygi', 0), " +
                "('Stresli anlarda bile pozitif düşünce ve davranışları koruyabiliyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi stresli durumlarda bile güçlü ve dirençli hissetmek için içsel gücümü besliyorum.', 'stres ve kaygi', 0), " +
                "('Her stresli durumda, iç huzur ve sakinlik bulmak için derin nefes almaya ve zihnimde biraz boşluk yaratmaya odaklanıyorum.', 'stres ve kaygi', 0), " +
                "('Stresle başa çıkmak için düşünce ve duygularımı ifade etme yolları buluyorum ve paylaşıyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi stresten arındırmak ve rahatlamak için düzenli olarak masaj ve gevşeme terapileri alıyorum.', 'stres ve kaygi', 0), " +
                "('Stres ve kaygıyla başa çıkabilmek için kendi kendime pozitif bir iç konuşma yürütüyorum ve kendimi motive ediyorum.', 'stres ve kaygi', 0), " +
                "('Her stresli durumda, içsel gücümü hatırlayarak sakin ve odaklanmış kalabiliyorum.', 'stres ve kaygi', 0), " +
                "('Stresli anlarda bile kendime biraz zaman ayırarak gevşeme ve dinlenme fırsatı bulabiliyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi stresli durumlarda bile güçlü hissetmek için geçmişteki başarılarımı hatırlıyor ve onlardan güç alıyorum.', 'stres ve kaygi', 0), " +
                "('Stresli durumlarla başa çıkabilmek için her gün biraz zaman ayırarak iç huzurum ve denge buluyorum.', 'stres ve kaygi', 0), " +
                "('Her stresli durumda, kendime hafiflik ve esneklik getirmek için olumlu bir tutum benimseyebiliyorum.', 'stres ve kaygi', 0), " +
                "('Stresle başa çıkmak için destek almak ve duygusal olarak bağlantı kurmak için çevremdeki insanlarla iletişimde kalıyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi stresli durumlarda bile güçlü hissetmek için içsel gücümü ve dayanıklılığımı hatırlıyorum.', 'stres ve kaygi', 0), " +
                "('Stresli anlarda bile kendimi rahatlatmak ve sakinleştirmek için doğal çözümler ve bitkisel destekler araştırıyorum ve kullanıyorum.', 'stres ve kaygi', 0), " +
                "('Her stresli durumda, sakin ve odaklanmış kalmak için meditasyon ve derin nefes alma egzersizlerine başvuruyorum.', 'stres ve kaygi', 0), " +
                "('Stresle başa çıkabilmek için stresin nedenlerini ve tetikleyicilerini tanıyorum ve onlarla başa çıkma stratejileri geliştiriyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi stresli durumlarda bile huzurlu ve sakin hissetmek için pozitif düşünce ve davranışları günlük yaşamımda uyguluyorum.', 'stres ve kaygi', 0), " +
                "('Stres ve kaygıyı bırakmak için günlük olarak zihinsel ve duygusal olarak rahatlama ve temizlik pratikleri yapıyorum.', 'stres ve kaygi', 0), " +
                "('Her stresli durumda, kendime huzur ve dinginlik getirmek için doğayla temas kuruyor ve açık havada zaman geçiriyorum.', 'stres ve kaygi', 0), " +
                "('Stresle başa çıkmak için sağlıklı bir yaşam tarzı benimseyerek düzenli egzersiz yapıyor, dengeli besleniyorum ve yeterince uyuyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi stresli durumlarda bile güvende ve desteklenmiş hissetmek için içsel güven ve kabul pratiği yapıyorum.', 'stres ve kaygi', 0)");

        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES " +
                "('Stresi yönetebilir ve üzerine çıkabilirim.', 'stres ve kaygi', 0), " +
                "('Her nefesimle kaygıyı bırakıyor ve huzuru davet ediyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi sakin ve kontrol altında hissediyorum.', 'stres ve kaygi', 0), " +
                "('Kaygılarım geçici, iç huzurum kalıcı.', 'stres ve kaygi', 0), " +
                "('Her gün stres seviyem azalıyor ve daha rahat hissediyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi her türlü stres ve kaygıdan koruyabilirim.', 'stres ve kaygi', 0), " +
                "('Stresli anlarda bile, sakin kalmayı başarabilirim.', 'stres ve kaygi', 0), " +
                "('Kaygılarımı anlıyor ve onları serbest bırakıyorum.', 'stres ve kaygi', 0), " +
                "('Her gün daha fazla iç huzur ve sakinlik hissediyorum.', 'stres ve kaygi', 0), " +
                "('Stresin üstesinden gelmek için ihtiyacım olan tüm araçlara sahibim.', 'stres ve kaygi', 0), " +
                "('Kendimi rahat ve huzurlu hissetmek için zaman ayırıyorum.', 'stres ve kaygi', 0), " +
                "('Stresle başa çıkma konusunda her geçen gün daha yetenekli hale geliyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi sakinleştirebilir ve rahatlatabilirim.', 'stres ve kaygi', 0), " +
                "('Kaygılarımı yatıştırmak için etkili yöntemler kullanıyorum.', 'stres ve kaygi', 0), " +
                "('Her gün stresimi azaltıyor ve daha rahat bir yaşam sürüyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi stresten uzak tutmak için sağlıklı alışkanlıklar ediniyorum.', 'stres ve kaygi', 0), " +
                "('Stres, hayatımı kontrol etmiyor; ben stresimi kontrol ediyorum.', 'stres ve kaygi', 0), " +
                "('Kaygılarımı kabul ediyor ve onlarla sağlıklı bir şekilde başa çıkıyorum.', 'stres ve kaygi', 0), " +
                "('Her gün daha fazla rahatlık ve sakinlik hissediyorum.', 'stres ve kaygi', 0), " +
                "('Stres ve kaygı, benim üzerimde güç sahibi değil.', 'stres ve kaygi', 0), " +
                "('Her stresli durumda, derin bir nefes alarak rahatlıyorum.', 'stres ve kaygi', 0), " +
                "('Kaygılarım geçici, ben ise bu anın üstesinden gelebilirim.', 'stres ve kaygi', 0), " +
                "('Kendimi sakin ve huzurlu hissetmek için gereken her şeyi yapıyorum.', 'stres ve kaygi', 0), " +
                "('Stresli zamanlarda bile, güçlü ve sakin kalabiliyorum.', 'stres ve kaygi', 0), " +
                "('Kaygılarımı yönetebilir ve onları hafifletebiliriz.', 'stres ve kaygi', 0), " +
                "('Her gün stres seviyem düşüyor ve yaşam kalitem artıyor.', 'stres ve kaygi', 0), " +
                "('Stres ve kaygıyı yönetmek, benim elime.', 'stres ve kaygi', 0), " +
                "('Kaygılarımı yatıştırmak için zaman ayırıyorum ve bu işe yarıyor.', 'stres ve kaygi', 0), " +
                "('Her stresli an, geçip giden bir bulut gibi.', 'stres ve kaygi', 0), " +
                "('Kendimi rahatlatmak ve sakinleştirmek için etkili yöntemler biliyorum.', 'stres ve kaygi', 0), " +
                "('Stresle başa çıkma konusunda kendime güveniyorum.', 'stres ve kaygi', 0), " +
                "('Her gün stresimi daha etkili bir şekilde yönetiyorum.', 'stres ve kaygi', 0), " +
                "('Kaygılarımı tanıyor ve onlarla sağlıklı bir şekilde ilgileniyorum.', 'stres ve kaygi', 0), " +
                "('Stresli zamanlarda bile, huzur bulmayı başarabilirim.', 'stres ve kaygi', 0), " +
                "('Kaygılarımı kontrol altına alabilir ve onları hafifletebiliriz.', 'stres ve kaygi', 0), " +
                "('Stresle baş etmek beni daha güçlü ve dirençli kılıyor.', 'stres ve kaygi', 0), " +
                "('Her kaygılı düşünceyi sakinlikle karşılıyorum ve geçmesine izin veriyorum.', 'stres ve kaygi', 0), " +
                "('Kendimi stresten arındırmak için gereken zamanı ayırıyorum.', 'stres ve kaygi', 0), " +
                "('Stresli anlarda bile, içsel huzuru bulmayı başarabilirim.', 'stres ve kaygi', 0)");


        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, her günümü daha aydınlık hale getiriyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu bakış açısıyla, hayatın her anını değerli kılıyorum.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün, olumlu düşüncelerle kendimi daha da güçlendiriyorum.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, çevremdeki insanlara da ilham veriyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her zorlukla karşılaştığımda, içimdeki pozitif enerjiyle çözümler buluyorum.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu bakış açısı, hayatımdaki her zorluğun üstesinden gelmemi sağlıyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, içsel huzurumu ve memnuniyetimi arttırıyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşüncelerim, her gün kendime daha fazla güvenmemi sağlıyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif bakış açısıyla, her sorunun bir çözümü olduğunu hatırlıyorum.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün, pozitif düşüncelerimle etrafıma neşe ve pozitif enerji yayıyorum.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşünmek, kendimi ve çevremi daha iyi bir geleceğe doğru yönlendiriyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşüncelerim, her gün daha fazla ilerleme kaydetmemi sağlıyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu bakış açısı, hayatımdaki her şeyin daha parlak bir tarafını görmemi sağlıyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, içsel gücümü ortaya çıkarıyor ve beni daha dirençli yapıyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün, pozitif düşüncelerimle yaşamımda daha fazla sevinç ve coşku hissediyorum.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşüncelerim, etrafımdaki herkesi olumlu etkiliyor ve onları motive ediyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, her gün daha iyi bir insan olmamı sağlıyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu bakış açısı, hayatımdaki her şeyin daha kolay olduğunu fark etmeme yardımcı oluyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşüncelerim, etrafımdaki dünyayı daha iyi bir yer haline getirme gücüne sahip olduğumu hatırlatıyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün, olumlu bakış açısıyla kendimi daha fazla motive ediyorum.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, yaşamımda daha fazla şükretmeme ve minnettarlık duymama olanak tanıyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşüncelerim, her gün daha fazla enerji ve canlılık hissetmeme yardımcı oluyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif bakış açısı, hayatımdaki her deneyimden bir şeyler öğrenmemi sağlıyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün, olumlu düşüncelerimle kendimi daha iyi hissediyorum ve daha iyi bir hayat yaratıyorum.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, beni daha fazla yaratıcı olmaya teşvik ediyor ve yeni fikirler bulmamı sağlıyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu bakış açısı, beni her durumda daha esnek ve uyumlu yapmaya yardımcı oluyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşüncelerim, hayatımda daha fazla pozitif deneyim çekmemi sağlıyor.', 'pozitif_dusunme', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, etrafımdaki güzellikleri fark etmemi sağlıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün, olumlu bakış açısıyla yeni fırsatlar keşfediyorum.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, içimdeki potansiyeli serbest bırakıyor ve beni ileriye taşıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşüncelerim, çevremdekilere de olumlu etki yapıyor ve onları motive ediyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her zorlukla karşılaştığımda, içimdeki pozitif enerjiyle çözümler buluyorum.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, hayatımı daha anlamlı kılıyor ve her günümü daha değerli hale getiriyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşüncelerim, beni her gün daha fazla motive ediyor ve hedeflerime ulaşmamı sağlıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif bakış açısıyla, her sorunun bir çözümü olduğunu hatırlıyorum ve bu bana güç veriyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün, pozitif düşüncelerimle etrafıma neşe ve pozitif enerji yayıyorum.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşünmek, geleceğe umutla bakmamı sağlıyor ve daha iyi bir yarın için motivasyonumu arttırıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün pozitif düşüncelerle doluyorum.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşünmek, hayatımı daha iyi bir yönde değiştiriyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşüncelerim, pozitif sonuçlar yaratıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her durumda iyi olanı görmeyi seçiyorum.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşünmek, benim doğal halim.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif olmak, her günümü daha parlak hale getiriyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşüncelerimle kendimi güçlendiriyorum.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün, pozitif düşüncelerle daha mutlu ve memnun hissediyorum.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşünmek, hayatımdaki tüm kapıları açıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, benim için kolay ve doğal.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her zorlukta, olumlu bir yan buluyorum.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, beni daha iyi bir insan yapıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün pozitif düşüncelerle uyanıyorum.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşüncelerim, beni başarıya ulaştırıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşünmek, hayatımda büyük farklar yaratıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif olmak, hayatımı zenginleştiriyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her durumda olumlu yönleri görebiliyorum.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşüncelerim, her gün beni daha mutlu ediyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, hayatımdaki zorlukları daha kolay aşmama yardımcı oluyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşünmek, benim yaşam tarzım.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün pozitif düşüncelerle dolu bir yaşam sürüyorum.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, bana daha fazla güven ve umut veriyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünceler, hayatımı daha iyi bir yere taşıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif olmak, hayatımda mucizeler yaratıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşünmek, beni daha sağlıklı ve mutlu yapıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşünmek, hayatımı daha anlamlı kılıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Her gün pozitif düşünerek daha fazla mutluluk ve başarı elde ediyorum.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Pozitif düşüncelerim, hayatımda büyük değişiklikler yaratıyor.', 'pozitif_dusunce', 0)");
        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES ('Olumlu düşünmek, beni daha iyi bir geleceğe taşıyor.', 'pozitif_dusunce', 0)");

        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES " +
                "('Başarı, her gün biraz daha yakınıma geliyor.', 'basari', 0), " +
                "('Her başarı, yeni bir özgüven kaynağıdır.', 'basari', 0), " +
                "('Başarı, benim doğal hakkımdır.', 'basari', 0), " +
                "('Her başarılı adım, bir sonraki fırsatı getirir.', 'basari', 0), " +
                "('Başarı, kararlılıkla ve azimle elde edilir.', 'basari', 0), " +
                "('Başarı, her gün biraz daha yaklaşan bir hedeftir.', 'basari', 0), " +
                "('Her engel, benim daha güçlü olmamı sağlar.', 'basari', 0), " +
                "('Başarı, içimdeki potansiyelin açığa çıkmasıdır.', 'basari', 0), " +
                "('Başarılı olmak, benim için kaçınılmaz bir gerçektir.', 'basari', 0), " +
                "('Her başarı, daha büyük bir başarı için bir adımdır.', 'basari', 0), " +
                "('Başarı, inancımın bir yansımasıdır.', 'basari', 0), " +
                "('Her başarı, benim için daha fazla motivasyon kaynağıdır.', 'basari', 0), " +
                "('Başarılı olmak, sürekli olarak ileri adımlar atmaktır.', 'basari', 0), " +
                "('Başarı, benim içimdeki gücün bir yansımasıdır.', 'basari', 0), " +
                "('Her gün, daha fazla başarıya doğru ilerliyorum.', 'basari', 0), " +
                "('Başarı, kararlılıkla yakalanır ve azimle korunur.', 'basari', 0), " +
                "('Her başarı, daha büyük bir hikayenin parçasıdır.', 'basari', 0), " +
                "('Başarılı olmak, zorlukları aşmak ve hedeflere ulaşmaktır.', 'basari', 0), " +
                "('Başarı, içimdeki potansiyelin açığa çıkmasıdır ve bunun farkındayım.', 'basari', 0), " +
                "('Her başarı, benim için daha fazla özgüven kaynağıdır ve bu beni daha da ileriye götürür.', 'basari', 0), " +
                "('Başarı, azimle çalışmanın ve inancın bir sonucudur.', 'basari', 0), " +
                "('Her başarı, daha fazla hedefe ulaşmak için bir itici güçtür.', 'basari', 0), " +
                "('Başarılı olmak, sadece başlamak ve devam etmekle başlar.', 'basari', 0), " +
                "('Başarı, hedeflerime doğru atılan her adımda bulunur.', 'basari', 0), " +
                "('Her başarı, benim için daha fazla özgüven ve motivasyon kaynağıdır.', 'basari', 0), " +
                "('Başarılı olmak, sürekli olarak ileriye doğru ilerlemektir ve ben bunu yapıyorum.', 'basari', 0), " +
                "('Başarı, her gün biraz daha fazla kendimi keşfetmem demektir.', 'basari', 0), " +
                "('Her başarı, daha büyük bir hikayenin parçasıdır ve ben bu hikayeyi yazıyorum.', 'basari', 0), " +
                "('Başarı, sürekli olarak ilerlemek ve hedeflere ulaşmak demektir.', 'basari', 0), " +
                "('Her başarı, içimdeki gücü ve azmi daha da artırır.', 'basari', 0), " +
                "('Başarılı olmak, inanmak ve sürekli olarak ilerlemekle mümkündür.', 'basari', 0), " +
                "('Başarı, her gün daha da yaklaştığım bir hedeftir ve bunun bilincindeyim.', 'basari', 0), " +
                "('Her başarı, daha fazla özgüven ve daha büyük hedeflere ulaşmak için bir adımdır.', 'basari', 0), " +
                "('Başarı, her adımda benimle birlikte yol alır ve bu beni daha da motive eder.', 'basari', 0), " +
                "('Başarılı olmak, içimdeki gücü ortaya çıkarmak ve hedeflere ulaşmaktır.', 'basari', 0), " +
                "('Başarı, inanmak ve sürekli olarak ilerlemekle mümkündür ve ben buna hazırım.', 'basari', 0), " +
                "('Her başarı, daha fazla özgüven ve daha büyük hedeflere ulaşmak için bir fırsattır.', 'basari', 0), " +
                "('Başarı, her adımda benimle birlikte ilerler ve bu beni daha da motive eder.', 'basari', 0), " +
                "('Başarılı olmak, hedeflerime ulaşmak için kararlılıkla ve azimle çalışmaktır.', 'basari', 0), " +
                "('Başarı, benim içimde var olan potansiyelin açığa çıkmasıdır ve ben buna hazırım.', 'basari', 0), " +
                "('Her başarı, beni daha fazla motive eder ve daha büyük hedeflere ulaşmak için güç verir.', 'basari', 0), " +
                "('Başarılı olmak, içimdeki gücü ve azmi ortaya çıkarmak ve hedeflere ulaşmaktır.', 'basari', 0), " +
                "('Başarının kapıları her gün bana daha fazla açılıyor.', 'basari', 0), " +
                "('Her adımım, başarıya doğru yol almamı sağlıyor.', 'basari', 0), " +
                "('Başarı, benim için ulaşılmaz bir hedef değil, kaçınılmaz bir sonuçtur.', 'basari', 0), " +
                "('Başarı, hayallerimdeki yolculuğun sadece bir durağı değil, aynı zamanda bir başlangıç noktasıdır.', 'basari', 0), " +
                "('Her yeni gün, başarı için yeni bir şans, yeni bir başlangıç demektir.', 'basari', 0), " +
                "('Başarı, içimdeki potansiyelin gerçekleşmesinin yansımasıdır.', 'basari', 0), " +
                "('Engeller, benim kararlılığımı ve başarıya olan inancımı daha da güçlendirir.', 'basari', 0), " +
                "('Başarı, yaptığım her şeyde gizlidir ve ben bu gizemi keşfediyorum.', 'basari', 0), " +
                "('Başarılı olmak için gerekli olan her şey, zaten içimde var.', 'basari', 0), " +
                "('Her gün, başarı için karşılaştığım benzersiz fırsatlarla doludur.', 'basari', 0), " +
                "('Başarı, içimdeki gücü ve azmi ortaya çıkarır.', 'basari', 0), " +
                "('Her gün, başarıyı daha fazla hissetmek için bir adım daha atıyorum.', 'basari', 0), " +
                "('Başarı, bir seçimdir ve ben her gün bu seçimi yapmaya devam ediyorum.', 'basari', 0), " +
                "('Başarı, attığım her adımda benimle birlikte ilerler.', 'basari', 0), " +
                "('Her başarı, gelecekteki büyük hedeflerim için bir motivasyon kaynağıdır.', 'basari', 0), " +
                "('Başarılı olmak, içimdeki gücü ve kararlılığı sergilemek demektir.', 'basari', 0), " +
                "('Başarı, inancımın ve sabrımın bir ifadesidir.', 'basari', 0), " +
                "('Her gün, başarıya bir adım daha yaklaşıyorum ve bu yolculuk heyecan verici.', 'basari', 0), " +
                "('Başarılı olmak, yaşamın sunduğu sonsuz olanakları keşfetmek demektir.', 'basari', 0), " +
                "('Başarı, hayatımı dönüştüren güçlü bir motivasyon kaynağıdır.', 'basari',  0)");

        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES " +
                "('Her gün kendimi geliştirmek için yeni bir fırsatım var.', 'kisisel gelisim', 0), " +
                "('Kendimi geliştirmek, benim yaşam amacımın bir parçasıdır.', 'kisisel gelisim', 0), " +
                "('Her gün yeni bir şey öğreniyorum ve bu bilgi beni geliştiriyor.', 'kisisel gelisim', 0), " +
                "('Kendi potansiyelimi keşfetmek benim için heyecan verici bir yolculuk.', 'kisisel gelisim', 0), " +
                "('Her gün kendimle ilgili yeni ve olumlu bir şey keşfediyorum.', 'kisisel gelisim', 0), " +
                "('Kendimi geliştirmek için zaman ayırmak, kendime olan sevgimin bir göstergesi.', 'kisisel gelisim', 0), " +
                "('Kişisel gelişimim, hayatımdaki tüm alanlara olumlu yansıyor.', 'kisisel gelisim', 0), " +
                "('Kendimi her gün biraz daha iyi hale getirme çabam beni güçlendiriyor.', 'kisisel gelisim', 0), " +
                "('Kendi sınırlarımı zorlamak, bana büyük bir tatmin sağlıyor.', 'kisisel gelisim', 0), " +
                "('Her deneyim, kişisel gelişim yolculuğumda bana değerli dersler öğretiyor.', 'kisisel gelisim', 0), " +
                "('Kendimi geliştirirken aynı zamanda diğerlerine de ilham kaynağı oluyorum.', 'kisisel gelisim', 0), " +
                "('Kendime yatırım yapmak, en değerli yatırımdır.', 'kisisel gelisim', 0), " +
                "('Kendimi geliştirmeye adadığım zaman, hayatımı daha iyi bir yere taşıyor.', 'kisisel gelisim', 0), " +
                "('Kendimi her gün geliştirmek, beni daha mutlu ve memnun hissettiriyor.', 'kisisel gelisim', 0), " +
                "('Kendime olan inancım, kişisel gelişimimi destekliyor.', 'kisisel gelisim', 0), " +
                "('Kendi yeteneklerimi geliştirmek, beni daha yetenekli ve başarılı kılıyor.', 'kisisel gelisim', 0), " +
                "('Kendimi geliştirirken karşılaştığım zorluklar, beni daha da güçlendiriyor.', 'kisisel gelisim', 0), " +
                "('Her gün kendimi biraz daha anlamak, hayatımda büyük bir fark yaratıyor.', 'kisisel gelisim', 0), " +
                "('Kişisel gelişim yolculuğum, benim için son derece tatmin edici.', 'kisisel gelisim', 0), " +
                "('Kendimi her gün biraz daha geliştirmek, bana büyük bir memnuniyet sağlıyor.', 'kisisel gelisim', 0), " +
                "('Kişisel gelişimim, beni daha bağımsız ve özgür kılıyor.', 'kisisel gelisim', 0), " +
                "('Kendimi geliştirmek, beni hayatın zorluklarına daha iyi hazırlıyor.', 'kisisel gelisim', 0), " +
                "('Her gün kendimi geliştirme çabam, beni daha dengeli ve uyumlu bir insan yapıyor.', 'kisisel gelisim', 0), " +
                "('Kişisel gelişimim, bana daha fazla kontrol sahibi olmamı sağlıyor.', 'kisisel gelisim', 0), " +
                "('Kendimi geliştirmek, beni her gün daha da ileriye taşıyor.', 'kisisel gelisim', 0), " +
                "('Kendimi geliştirme yolculuğum, hayatımda yeni kapılar açıyor.', 'kisisel gelisim', 0), " +
                "('Kişisel gelişimim, bana yaşam boyu öğrenmenin değerini hatırlatıyor.', 'kisisel gelisim', 0), " +
                "('Her gün kendimi geliştirmek, beni daha pozitif ve umutlu yapıyor.', 'kisisel gelisim', 0), " +
                "('Kendimi geliştirme sürecim, beni daha fazla sevgi ve şefkatle dolduruyor.', 'kisisel gelisim', 0)");

        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES " +
                "('Her anımı bilinçli ve kararlı bir şekilde kullanıyorum, zamanımın değerini biliyorum.', 'zaman yonetimi', 0), " +
                "('Her güne, maksimum verimlilikle başlıyorum, zamanımı en etkili şekilde değerlendiriyorum.', 'zaman yonetimi', 0), " +
                "('Zamanımı yönetmek artık güçlü bir yeteneğim haline geldi, her gün daha da gelişiyorum.', 'zaman yonetimi', 0), " +
                "('Her anımda zamanımı etkili bir şekilde kullanarak hedeflerime bir adım daha yaklaşıyorum.', 'zaman yonetimi', 0), " +
                "('Görevlerimi planlamak ve organize etmek benim için sadece bir alışkanlık değil, bir başarı ritüeli.', 'zaman yonetimi', 0), " +
                "('Zamanımı doğru yönetmek, hayatımda daha fazla kontrole ve özgürlüğe sahip olmamı sağlıyor.', 'zaman yonetimi', 0), " +
                "('Üretkenlik benim için sadece bir beceri değil, bir yaşam tarzı, her anımda bunu hissediyorum.', 'zaman yonetimi', 0), " +
                "('Her günümü önemli ve anlamlı aktivitelerle doldurarak hayatımdan maksimum verim alıyorum.', 'zaman yonetimi', 0), " +
                "('Görevlerimi zamanında ve etkili bir şekilde tamamlayarak zamanımdan en iyi şekilde faydalanıyorum.', 'zaman yonetimi', 0), " +
                "('Zamanımı değerli bir hazine gibi görüyor, onu akıllıca kullanıyorum ve hayatımı şekillendiriyorum.', 'zaman yonetimi', 0), " +
                "('Kendimi organize etmek, iç huzurumu ve yaşamımda dengeyi sağlıyor, bu benim için kıymetli bir armağan.', 'zaman yonetimi', 0), " +
                "('Her gün yeni stratejiler ve yöntemler geliştirerek zamanımı daha etkili ve verimli kullanıyorum.', 'zaman yonetimi', 0), " +
                "('Zaman yönetimi becerilerim, başarılarımı artırıyor ve hayatımı daha da kolaylaştırıyor.', 'zaman yonetimi', 0), " +
                "('Her anımda zamanımı bilinçli kullanarak, stresten arınmış ve huzurlu bir yaşam sürüyorum.', 'zaman yonetimi', 0), " +
                "('Zamanımı verimli kullanmak, bana daha fazla kişisel gelişim ve mutluluk getiriyor.', 'zaman yonetimi', 0), " +
                "('Zamanımı etkili bir şekilde kullanarak, hayal ettiğim yaşamı adım adım inşa ediyorum.', 'zaman yonetimi', 0), " +
                "('Zamanıma daha fazla öncelik vererek, hayallerime ulaşmak için bir adım daha yaklaşıyorum.', 'zaman yonetimi', 0), " +
                "('Zaman yönetimi becerilerim, hayatımda denge ve huzurun anahtarı haline geldi.', 'zaman yonetimi', 0), " +
                "('Her gün zamanımı daha etkili kullanarak, kendimi daha başarılı ve mutlu hissediyorum.', 'zaman yonetimi', 0), " +
                "('Zamanımı bilinçli bir şekilde kullanmak, beni daha yaratıcı ve yenilikçi bir insan haline getiriyor.', 'zaman yonetimi', 0), " +
                "('Zamanımı bilinçli ve etkili bir şekilde kullanıyorum.', 'zaman yonetimi', 0), " +
                "('Her günü maksimum verimlilikle değerlendiriyorum.', 'zaman yonetimi', 0), " +
                "('Zamanımı yönetme becerim, sürekli gelişiyor.', 'zaman yonetimi', 0), " +
                "('Her gün, zamanımı en iyi şekilde kullanıyorum.', 'zaman yonetimi', 0), " +
                "('Görevlerimi planlamak ve organize etmek, benim için doğal ve kolay.', 'zaman yonetimi', 0), " +
                "('Zamanımı yönetmek, bana daha fazla kontrol ve özgürlük sağlıyor.', 'zaman yonetimi', 0), " +
                "('Üretken olmak, benim için tatmin edici ve keyifli.', 'zaman yonetimi', 0), " +
                "('Her günümü önemli ve anlamlı aktivitelerle dolduruyorum.', 'zaman yonetimi', 0), " +
                "('Görevlerimi zamanında ve etkili bir şekilde tamamlıyorum.', 'zaman yonetimi', 0), " +
                "('Zamanıma değer veriyorum ve onu akıllıca harcıyorum.', 'zaman yonetimi', 0), " +
                "('Kendimi organize etmek, bana daha fazla huzur ve düzen sağlıyor.', 'zaman yonetimi', 0), " +
                "('Her gün, zamanımı en iyi şekilde değerlendirmek için yeni yollar buluyorum.', 'zaman yonetimi', 0), " +
                "('Zaman yönetimi becerilerim, beni daha başarılı ve verimli kılıyor.', 'zaman yonetimi', 0), " +
                "('Zamanımı yönetme yeteneğim, günlük yaşamımı kolaylaştırıyor.', 'zaman yonetimi', 0), " +
                "('Verimli olmak, benim için doğal bir durum.', 'zaman yonetimi', 0), " +
                "('Her gün, önceliklerimi belirleyerek zamanımı verimli kullanıyorum.', 'zaman yonetimi', 0), " +
                "('Zamanı verimli kullanmak, bana daha fazla kişisel zaman kazandırıyor.', 'zaman yonetimi', 0), " +
                "('Zamanımı etkili bir şekilde yönetmek, bana daha fazla başarı ve memnuniyet getiriyor.', 'zaman yonetimi', 0), " +
                "('Zamanımı akıllıca kullanarak, hayatımdaki stresi azaltıyorum.', 'zaman yonetimi', 0), " +
                "('Her gün, zamanımı verimli kullanma konusunda daha bilinçli hale geliyorum.', 'zaman yonetimi', 0), " +
                "('Zamanımı yönetmek, bana hayatın tüm alanlarında daha fazla başarı sağlıyor.', 'zaman yonetimi', 0), " +
                "('Zamanımı yönetme becerim, beni her gün daha üretken kılıyor.', 'zaman yonetimi', 0), " +
                "('Zaman yönetimi becerilerimi geliştirmek, bana büyük bir tatmin sağlıyor.', 'zaman yonetimi', 0), " +
                "('Zamanıma değer veriyorum ve onu en iyi şekilde kullanıyorum.', 'zaman yonetimi', 0), " +
                "('Her gün, zamanımı daha verimli ve etkili kullanmanın yollarını öğreniyorum.', 'zaman yonetimi', 0), " +
                "('Zamanımı bilinçli kullanmak, bana daha fazla neşe ve tatmin getiriyor.', 'zaman yonetimi', 0), " +
                "('Zaman yönetimi, hayatımda önemli bir rol oynuyor ve beni daha üretken yapıyor.', 'zaman yonetimi', 0), " +
                "('Zamanımı etkili bir şekilde kullanarak, daha fazla başarı elde ediyorum.', 'zaman yonetimi', 0), " +
                "('Zamanımı yönetme yeteneğim, bana her gün daha fazla fırsat sunuyor.', 'zaman yonetimi', 0), " +
                "('Zamanımı verimli kullanarak, hayallerime daha hızlı ulaşıyorum.', 'zaman yonetimi', 0), " +
                "('Zaman yönetimi becerilerim, bana hayatımda daha fazla denge sağlıyor.', 'zaman yonetimi', 0), " +
                "('Her gün, zamanımı daha etkili kullanarak daha fazla başarıya ulaşıyorum.', 'zaman yonetimi', 0), " +
                "('Zamanımı bilinçli kullanmak, beni daha mutlu ve memnun ediyor.', 'zaman yonetimi', 0), " +
                "('Zamanımı yönetmek, bana daha fazla yaratıcılık ve inovasyon için alan açıyor.', 'zaman yonetimi', 0), " +
                "('Zamanımı verimli kullanmak, benim için sürekli bir öncelik.', 'zaman yonetimi', 0), " +
                "('Zamanımı yönetme konusunda her gün daha yetenekli hale geliyorum.', 'zaman yonetimi', 0), " +
                "('Zaman yönetimi becerilerim, beni daha dengeli ve uyumlu bir insan yapıyor.', 'zaman yonetimi', 0), " +
                "('Zamanımı etkili bir şekilde kullanarak, tüm hedeflerime ulaşabilirim.', 'zaman yonetimi', 0), " +
                "('Zamanımı yönetme konusunda sürekli olarak kendimi geliştiriyorum.', 'zaman yonetimi', 0), " +
                "('Zamanımı bilinçli kullanarak, hayatımın kalitesini artırıyorum.', 'zaman yonetimi', 0) ");

        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES " +
                "('İlişkilerim sağlıklı ve destekleyici.', 'iliskiler', 0), " +
                "('İnsanlarla kolayca bağ kuruyorum ve bu bağlar uzun süreli oluyor.', 'iliskiler', 0), " +
                "('Sosyal ilişkilerim beni güçlendiriyor ve besliyor.', 'iliskiler', 0), " +
                "('İnsanlara karşı açık ve dürüst olmak, ilişkilerimi güçlendiriyor.', 'iliskiler', 0), " +
                "('Her yeni insanla tanışmak, yeni bir macera ve öğrenme fırsatıdır.', 'iliskiler', 0), " +
                "('İlişkilerimde sevgi ve anlayışı öncelikli tutuyorum.', 'iliskiler', 0), " +
                "('Empati kurma yeteneğim, ilişkilerimi daha derin ve anlamlı kılıyor.', 'iliskiler', 0), " +
                "('İnsanlarla etkileşimde bulunmak, bana neşe ve memnuniyet getiriyor.', 'iliskiler', 0), " +
                "('İlişkilerimde daima saygılı ve düşünceliyim.', 'iliskiler', 0), " +
                "('Sosyal çevrem, beni destekleyen ve ilham veren insanlarla dolu.', 'iliskiler', 0), " +
                "('İlişkilerimdeki sorunları çözmek için yaratıcı ve yapıcı yollar buluyorum.', 'iliskiler', 0), " +
                "('İletişim becerilerim, her gün daha da gelişiyor.', 'iliskiler', 0), " +
                "('İlişkilerim, benim kişisel büyüme ve gelişimime katkıda bulunuyor.', 'iliskiler', 0), " +
                "('İnsanlarla olan bağlarım, hayatımı daha zengin ve tatmin edici yapıyor.', 'iliskiler', 0), " +
                "('Herkesle dürüst ve açık iletişim kuruyorum.', 'iliskiler', 0), " +
                "('İlişkilerimi sürekli olarak geliştirme konusunda proaktifim.', 'iliskiler', 0), " +
                "('İlişkilerimde karşılıklı saygı ve anlayış esastır.', 'iliskiler', 0), " +
                "('Arkadaşlarım ve sevdiklerimle sağlam bağlar kuruyorum.', 'iliskiler', 0), " +
                "('Sosyal etkileşimlerim, beni daha mutlu ve dengeli bir insan yapıyor.', 'iliskiler', 0), " +
                "('Her insanla etkileşim, beni daha bilge ve anlayışlı kılıyor.', 'iliskiler', 0), " +
                "('İlişkilerimde sevgi ve şefkati özgürce ifade ediyorum.', 'iliskiler', 0), " +
                "('İnsanlarla sağlıklı sınırlar koymayı biliyorum.', 'iliskiler', 0), " +
                "('İlişkilerimdeki her bireyin benzersiz değerini takdir ediyorum.', 'iliskiler', 0), " +
                "('İlişkilerimdeki zorlukları, büyüme fırsatları olarak görüyorum.', 'iliskiler', 0), " +
                "('Arkadaşlarım ve ailemle vakit geçirmek, bana büyük mutluluk veriyor.', 'iliskiler', 0), " +
                "('Sosyal ilişkilerimde her zaman dürüst ve şeffafım.', 'iliskiler', 0), " +
                "('İlişkilerim, güven ve sevgi üzerine kurulu.', 'iliskiler', 0), " +
                "('İnsanlarla derin ve anlamlı bağlar kurma yeteneğine sahibim.', 'iliskiler', 0), " +
                "('İlişkilerimde karşılıklı destek ve güven esastır.', 'iliskiler', 0), " +
                "('İlişkilerim beni geliştiriyor ve daha iyi bir insan yapmaya teşvik ediyor.', 'iliskiler', 0), " +
                "('İnsanlarla olan ilişkilerimde daima öğreniyor ve büyüyorum.', 'iliskiler', 0), " +
                "('İlişkilerimdeki herkesle sağlıklı iletişim kuruyorum.', 'iliskiler', 0), " +
                "('Sosyal etkileşimlerim, benim zihinsel ve duygusal sağlığımı destekliyor.', 'iliskiler', 0), " +
                "('İlişkilerimde daima sabırlı ve anlayışlıyım.', 'iliskiler', 0), " +
                "('İnsanlarla olan ilişkilerimde daima pozitif ve destekleyiciyim.', 'iliskiler', 0), " +
                "('İlişkilerimde samimi ve doğalım.', 'iliskiler', 0), " +
                "('İlişkilerimdeki herkesin değerini biliyorum ve onları önemsiyorum.', 'iliskiler', 0), " +
                "('Sosyal bağlarım, hayatımı daha zengin ve renkli kılıyor.', 'iliskiler', 0), " +
                "('İlişkilerimde her zaman adil ve dengeliyim.', 'iliskiler', 0), " +
                "('İlişkilerim, beni sosyal ve duygusal olarak tatmin ediyor.', 'iliskiler', 0)"
        );

        myDatabase.execSQL("INSERT INTO olumlamalar (affirmation, category, favorite) VALUES " +
                "('Evren, bana neşe ve mutluluk veren her şeyi çekiyorum.', 'dua ve istek', 0), " +
                "('Günlük yaşantımda barış ve huzur istiyorum.', 'dua ve istek', 0), " +
                "('Hayatımdaki her şeyin benim en yüksek hayrıma hizmet etmesini diliyorum.', 'dua ve istek', 0), " +
                "('Sağlık, bolluk ve sevgi ile dolu bir yaşam sürmeyi diliyorum.', 'dua ve istek', 0), " +
                "('Evren, yaratıcılığımı artırmam için bana ilham ver.', 'dua ve istek', 0), " +
                "('Güçlü ve sağlıklı bir vücut istiyorum, bunun için evren bana enerji versin.', 'dua ve istek', 0), " +
                "('Tüm ilişkilerimde sevgi ve anlayış istiyorum.', 'dua ve istek', 0), " +
                "('Kariyerimde ilerlemek ve başarılı olmak için rehberlik istiyorum.', 'dua ve istek', 0), " +
                "('Evren, hayatımdaki zorlukları aşmam için bana güç ver.', 'dua ve istek', 0), " +
                "('Gerçek potansiyelimi keşfetmek ve kullanmak için fırsatlar çekiyorum.', 'dua ve istek', 0), " +
                "('Evren, sevdiklerimle sağlıklı ve mutlu ilişkiler kurmamı destekle.', 'dua ve istek', 0), " +
                "('Her gün daha fazla sevgi ve neşe istiyorum.', 'dua ve istek', 0), " +
                "('İhtiyacım olan her şeyin bana kolayca ve sorunsuzca ulaşmasını diliyorum.', 'dua ve istek', 0), " +
                "('Hayatımda sürekli bolluk ve refah istiyorum.', 'dua ve istek', 0), " +
                "('Kendi iç huzurumu bulmak ve sürdürmek için rehberlik ve destek istiyorum.', 'dua ve istek', 0), " +
                "('Evren, bana günlük kararlarım için bilgelik ver.', 'dua ve istek', 0), " +
                "('İçsel barış ve sakinlik istiyorum.', 'dua ve istek', 0), " +
                "('Hayatımda karşılıklı saygı ve anlayış istiyorum.', 'dua ve istek', 0), " +
                "('Evren, hayatımdaki tüm insanlar için sağlık ve mutluluk diliyorum.', 'dua ve istek', 0), " +
                "('Kendimi ve çevremdeki dünyayı daha iyi anlamak için anlayış istiyorum.', 'dua ve istek', 0), " +
                "('Evren, hayatımın her alanında dengeli ve uyumlu olmamı sağla.', 'dua ve istek', 0), " +
                "('Korkularımı aşmak ve cesur olmak için destek istiyorum.', 'dua ve istek', 0), " +
                "('Tüm zorlukları aşarak, hayatımda başarı ve mutluluğu çekmek istiyorum.', 'dua ve istek', 0), " +
                "('Bana doğru yolu göster ve seçimlerimde rehberlik et.', 'dua ve istek', 0), " +
                "('Tüm kararlarımın en yüksek iyiliğime hizmet etmesini diliyorum.', 'dua ve istek', 0), " +
                "('Hayatımda kalıcı ve gerçek sevgiyi çekmek istiyorum.', 'dua ve istek', 0), " +
                "('Evren, bana yeni fırsatlar aç ve yol göster.', 'dua ve istek', 0), " +
                "('Hayatımda güven ve güvenlik istiyorum.', 'dua ve istek', 0), " +
                "('Sevgiyle dolu, destekleyici bir topluluk çekmek istiyorum.', 'dua ve istek', 0), " +
                "('Zihinsel ve fiziksel sağlığımı korumak ve geliştirmek için destek istiyorum.', 'dua ve istek', 0), " +
                "('Evren, bana hedeflerime ulaşmam için motivasyon ver.', 'dua ve istek', 0), " +
                "('Hayatımın her alanında açıklık ve şeffaflık istiyorum.', 'dua ve istek', 0), " +
                "('Kendi kendime yeterlilik ve bağımsızlık istiyorum.', 'dua ve istek', 0), " +
                "('Evren, hayatımı iyileştirmek için bana bilgi ve kaynaklar sağla.', 'dua ve istek', 0), " +
                "('Tüm endişelerimi bırakıp, şimdiki ana odaklanmak istiyorum.', 'dua ve istek', 0), " +
                "('Kendimi ve başkalarını affetmek için güç ve cesaret istiyorum.', 'dua ve istek', 0), " +
                "('Her gün daha fazla mutluluk ve tatmin istiyorum.', 'dua ve istek', 0), " +
                "('Hayatımda yaratıcılığımı teşvik etmek ve geliştirmek için ilham istiyorum.', 'dua ve istek', 0), " +
                "('Tüm varlığımla barış içinde yaşamak istiyorum.', 'dua ve istek', 0), " +
                "('Evren, hayatımdaki her şeyin en güzel şekilde gerçekleşmesini diliyorum.', 'dua ve istek', 0), " +
                "('Her gün artan bir bilgelik ve anlayış için rehberlik istiyorum.', 'dua ve istek', 0), " +
                "('Evren, bana ve sevdiklerime sürekli sağlık ve refah ver.', 'dua ve istek', 0), " +
                "('Hayatımda daha fazla sabır ve hoşgörü istiyorum.', 'dua ve istek', 0), " +
                "('Bana hayatımın amaç ve yönünü bulmamda yardımcı ol.', 'dua ve istek', 0), " +
                "('Sevgi, barış ve mutlulukla dolu bir yaşam sürmeyi diliyorum.', 'dua ve istek', 0), " +
                "('Kendimi her gün daha fazla sevmek ve kabul etmek için güç istiyorum.', 'dua ve istek', 0), " +
                "('Evren, zihnimin ve kalbimin kapılarını bana yardımcı olacak bilgilere aç.', 'dua ve istek', 0), " +
                "('Hayatımın her alanında pozitif değişimler için enerji istiyorum.', 'dua ve istek', 0), " +
                "('Daha güçlü sosyal bağlar ve anlamlı ilişkiler kurmayı diliyorum.', 'dua ve istek', 0), " +
                "('Evren, hayatımdaki tüm engellerin üstesinden gelmem için bana güç ver.', 'dua ve istek', 0), " +
                "('Her gün artan bir şükran duygusu için içsel anlayış istiyorum.', 'dua ve istek', 0), " +
                "('İç huzur ve dinginlik için evrenin rehberliğini diliyorum.', 'dua ve istek', 0), " +
                "('Hayatım boyunca sürekli öğrenmek ve büyümek için fırsatlar çekiyorum.', 'dua ve istek', 0), " +
                "('Hayatımı zenginleştiren ve destekleyen insanlarla çevrili olmak istiyorum.', 'dua ve istek', 0), " +
                "('Başarıya ulaşmak ve hayallerimi gerçekleştirmek için sürekli ilham istiyorum.', 'dua ve istek', 0), " +
                "('Her gün kendimi daha iyi bir insan olarak görmek için fırsatlar istiyorum.', 'dua ve istek', 0), " +
                "('Hayatımda daha fazla neşe ve enerji için evrenin desteğini istiyorum.', 'dua ve istek', 0), " +
                "('Tüm zorluklara rağmen dayanıklı ve esnek olmak için güç istiyorum.', 'dua ve istek', 0), " +
                "('Daha fazla şefkat ve anlayışla hareket etmek için içsel rehberlik istiyorum.', 'dua ve istek', 0), " +
                "('Evren, bana sevdiklerimle daha derin ve anlamlı bağlar kurma gücü ver.', 'dua ve istek', 0), " +
                "('Kendi içsel barışımı bulmak ve korumak için bilgelik diliyorum.', 'dua ve istek', 0), " +
                "('Evren, hayatımdaki her adımda bana netlik ve açıklık sağla.', 'dua ve istek', 0), " +
                "('Her gün daha fazla motivasyon ve azim için evrenin yardımını diliyorum.', 'dua ve istek', 0), " +
                "('Kendimi ve başkalarını daha iyi anlamak için derin bir anlayış istiyorum.', 'dua ve istek', 0), " +
                "('Hayatımda daha fazla güven ve cesaret için destek istiyorum.', 'dua ve istek', 0), " +
                "('Her gün sağlıklı ve dengeli bir yaşam sürmek için rehberlik diliyorum.', 'dua ve istek', 0), " +
                "('Duygusal ve zihinsel olarak güçlü olmak için evrenin desteğini istiyorum.', 'dua ve istek', 0), " +
                "('Hayatımın her alanında başarı ve tatmin duygusu için yardım istiyorum.', 'dua ve istek', 0), " +
                "('Her gün içsel gücümü ve özgüvenimi artırmak için evrenin rehberliğini istiyorum.', 'dua ve istek', 0), " +
                "('Evren, bana her gün daha fazla sevgi ve neşe getir.', 'dua ve istek', 0)");


        myDatabase.execSQL("UPDATE olumlamalar SET category = 'Stres ve Kaygı Olumlamaları' WHERE category = 'stres ve kaygi'");
        myDatabase.execSQL("UPDATE olumlamalar SET category = 'Pozitif Düşünce Olumlamaları' WHERE category = 'pozitif_dusunce'");
        myDatabase.execSQL("UPDATE olumlamalar SET category = 'Pozitif Düşünce Olumlamaları' WHERE category = 'pozitif_dusunme'");
        myDatabase.execSQL("UPDATE olumlamalar SET category = 'İlişki Olumlamaları' WHERE category = 'iliskiler'");
        myDatabase.execSQL("UPDATE olumlamalar SET category = 'Dua ve İstek Olumlamaları' WHERE category = 'dua ve istek'");
        myDatabase.execSQL("UPDATE olumlamalar SET category = 'Zaman Yönetimi Olumlamaları' WHERE category = 'zaman yonetimi'");
        myDatabase.execSQL("UPDATE olumlamalar SET category = 'Kişisel Gelişim Olumlamaları' WHERE category = 'kisisel gelisim'");
        myDatabase.execSQL("UPDATE olumlamalar SET category = 'Başarı Olumlamaları' WHERE category = 'basari'");


    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }



    fun deleteAffirmation(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun updateAffirmationFavStatus(affirmation: Olumlamalarlistmodel) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FAVORITE, affirmation.favorite)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(affirmation.id.toString()))
        db.close()
    }

    fun addAffirmationFav(affirmation: Olumlamalarlistmodel, isFavorite: Boolean) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_AFFIRMATION, affirmation.affirmation)
            put(COLUMN_CATEGORY, "Favori Olumlamalarım" )
            put(COLUMN_FAVORITE, isFavorite)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    fun deleteFavoriteAffirmationByCategoryAndAffirmationName(category: String, affirmation: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_CATEGORY = ? AND $COLUMN_AFFIRMATION = ?", arrayOf(category, affirmation))
        db.close()
    }

    fun getFavoriteAffirmations(): List<Olumlamalarlistmodel> {
        val olumlamalar = mutableListOf<Olumlamalarlistmodel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_FAVORITE = 1", null)
        cursor.use {
            while (it.moveToNext()) {
                val olumlama = Olumlamalarlistmodel(
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_ID)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_AFFIRMATION)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_FAVORITE)) == 1
                )
                olumlamalar.add(olumlama)
            }
        }
        return olumlamalar
    }



    fun updateAffirmation(affirmation: Olumlamalarlistmodel) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_AFFIRMATION, affirmation.affirmation)
            put(COLUMN_CATEGORY, affirmation.category)
            put(COLUMN_FAVORITE, affirmation.favorite)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(affirmation.id.toString()))
        db.close()
    }

    // DBHelper sınıfı içinde olumlamaları kategoriye göre çeken metod
    fun getOlumlamalarByCategory(kategori: String): List<Olumlamalarlistmodel> {
        val olumlamalar = mutableListOf<Olumlamalarlistmodel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_CATEGORY = ?", arrayOf(kategori))
        cursor.use {
            while (it.moveToNext()) {
                val olumlama = Olumlamalarlistmodel(
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_ID)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_AFFIRMATION)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_FAVORITE)) == 1
                )
                olumlamalar.add(olumlama)
            }
        }
        return olumlamalar
    }

    fun addAffirmation(affirmation: Olumlamalarlistmodel) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_AFFIRMATION, affirmation.affirmation)
        values.put(COLUMN_CATEGORY, affirmation.category)
        values.put(COLUMN_FAVORITE, affirmation.favorite)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllCategories(): List<String> {
        val kategoriListesi = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT $COLUMN_CATEGORY FROM $TABLE_NAME", null)
        cursor.use {
            while (it.moveToNext()) {
                val kategori = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY))
                kategoriListesi.add(kategori)
            }
        }
        return kategoriListesi
    }


    /*
    // get all affirmations
    fun getAllAffirmations(): ArrayList<Olumlamalarlistmodel> {
        val list = ArrayList<Olumlamalarlistmodel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_FAVORITE = 1", null)
        cursor.use {
            while (it.moveToNext()) {
                val olumlama = Olumlamalarlistmodel(
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_ID)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_AFFIRMATION)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_FAVORITE)) == 1
                )
                olumlamalar.add(olumlama)
            }
        }
        return olumlamalar
    }

*/


}