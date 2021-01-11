package com.ehsanrc.zikr_update.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ehsanrc.zikr_update.R;
import com.ehsanrc.zikr_update.view.Dualist;
import com.ehsanrc.zikr_update.view.MainActivity;
import com.ehsanrc.zikr_update.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Dua.class}, version = 2)
public abstract class DuaDatabase extends RoomDatabase {

    private static DuaDatabase instance;

    public static DuaDatabase getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    DuaDatabase.class,
                    "dua_database")
                    .addCallback(roomCallback)
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }

    public abstract DuaDAO duaDAO();

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbTask().execute();
        }
    };

    private static class PopulateDbTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            DuaDAO duaDAO = instance.duaDAO();
            duaDAO.deleteAllDuas();


            List<Dua> myDuas = addDuas();

            for (Dua dua:myDuas){
                duaDAO.insert(dua);
            }
            return null;
        }
    }

    public static List<Dua> addDuas() {

        String[] duaNames = {"ঘুম থেকে উঠে",
                "বাথরুমে যাওয়ার সময়",
                "বাথরুম থেকে বের হবার সময়",
                "ঘর থেকে বেরোবার সময়",
                "যানবাহনে চরার সময়",
                "ঘরে প্রবেশের সময়",
                "খাওয়ার শুরুতে",
                "খাওয়ার শেষে",
                "বিপদে পড়লে",
                "ঘুমানোর আগে"};

        String[] duaArabic = {"الحَمْـدُ لِلّهِ الّذي أَحْـيانا بَعْـدَ ما أَماتَـنا وَإليه النُّـشور",
                "اللَّهُمَّ إِنِّي أَعُوْذُ بِكَ مِنَ الْخُبُثِ وَالْخَبَائِث" ,
                "غُفْـرانَك",
                "بِسْمِ اللهِ ، تَوَكَّلْـتُ عَلى اللهِ وَلا حَوْلَ وَلا قُـوَّةَ إِلاّ بِالله",
                "        بِسْـمِ اللهِ وَالْحَمْـدُ لله، سُـبْحانَ الّذي سَخَّـرَ لَنا هذا وَما كُنّا لَهُ مُقْـرِنين، وَإِنّا إِلى رَبِّنا لَمُنـقَلِبون، الحَمْـدُ لله، الحَمْـدُ لله، الحَمْـدُ لله، اللهُ أكْـبَر، اللهُ أكْـبَر، اللهُ أكْـبَر، سُـبْحانَكَ اللّهُـمَّ إِنّي ظَلَـمْتُ نَفْسي فَاغْـفِرْ لي، فَإِنَّهُ لا يَغْفِـرُ الذُّنوبَ إِلاّ أَنْـت",
                "بِسْـمِ اللهِ وَلَجْنـا، وَبِسْـمِ اللهِ خَـرَجْنـا، وَعَلـى رَبِّنـا تَوَكّلْـنا",
                "بِسْمِ الله\\n بِسْمِ اللهِ في أَوَّلِهِ وَآخِـرِه",
                "الْحَمْـدُ للهِ الَّذي أَطْعَمَنـي هـذا وَرَزَقَنـيهِ مِنْ غَـيْرِ حَوْلٍ مِنِّي وَلا قُوَّة",
                "لَا إِلَهَ إِلَّا أنْـت سُـبْحانَكَ إِنِّي كُنْـتُ مِنَ الظّـالِميـن",
                "بِاسْـمِكَ اللّهُـمَّ أَمـوتُ وَأَحْـيا>"};

        String[] duaPronunciation = {"উচ্চারণঃ আলহামদুলিল্লাহিল্লাযি আহইয়া না বা\'দামা আমা তানা ওয়া ইলাইহিন নুশুর",
                "উচ্চারণঃ আল্লাহুম্মা ইন্নি আঊ\'যুবিকা মিনাল খুবসি ওয়াল খাবা-ইস",
                "উচ্চারণঃ গুফরা-নাকা",
                "উচ্চারণঃ বিসমিল্লা-হি তাওয়াক্কালতু \'আলাল্লাহি ওয়া লা হাওলা ওয়া লা ক্বুউওয়াতা ইল্লা বিল্লাহ",
                "উচ্চারণঃ বিসমিল্লাহি আল হামদু লিল্লাহি সুবহানাল্লাযি সাখখারা লানা হা যা ওয়ামা কুন্না লাহু মুক্বরিনিনা ওয়া ইন্না ইলা রাব্বিনা লামুন ক্বালিবুন।আলহামদুলিলাহ, আলহামদুলিল্লাহ, আলহামদুলিল্লাহ। আল্লাহু আকবার, আল্লাহু আকবার, আল্লাহু আকবার।  সুবহানাকা আল্লাহুম্মা ইন্নি যোয়ালামতু নাফসি ফাগফিরলি ফা ইন্নাহু লা ইয়াগফিরুযযুনুবা ইল্লা আনতা",
                "উচ্চারণঃ বিসমিল্লাহি ওয়া লাজনা , ওয়া বিসমিল্লাহি খারাজনা, ওয়া \'আলা রাব্বানা তাওয়াক্কালনা",
                "উচ্চারণঃ বিসমিল্লাহ\nবিসমিল্লাহি ফি আওয়ালিহি ওয়া আখিরিহি(প্রথমে ভুলে গেলে)",
                "উচ্চারণঃ আলহামদুলিল্লাহিল্লাযি আত \'আমানি হাযা ওয়া রাযাকানিহি মিন গাইরি হাওলিম্মিন্নি ওয়ালা ক্বুওয়াতিন",
                "উচ্চারণঃ লা ইলাহা ইল্লা আনতা সুবহানাকা ইন্নি কুনতু মিনায যোয়ালিমিন",
                "উচ্চারণঃ বিসমিকা আল্লাহুম্মা আমুতু ওয়া আহইয়া"};

        String[] duaTranslation = {"অর্থঃ যাবতীয় প্রশংসা সেই আল্লাহর যিনি আমার (নিদ্রারূপ)মৃত্যুর পর আমাকে (পুনর্জাগরিত করে)জীবিত করলেন, আর তাঁর ই দরবারে সকলের পূনরুথান হবে।",
                "অর্থঃ হে আল্লাহ, আমি তোমার কাছে অপবিত্র জিন নর ও নারীর (অনিষ্ট )থেকে আশ্রয় কামনা করি।",
                "অর্থঃ হে আল্লাহ, তোমার কাছে ক্ষমা প্রার্থনা করছি।",
                "অর্থঃ আল্লাহ তা\'আলার নাম নিয়ে তাঁর ই উপর ভরসা করে ঘর থেকে বের হলাম। আল্লাহর দয়া ছাড়া মূলতঃ কোন শক্তি নেই অসৎ কাজ থেকে বেঁচে থাকার এবং সৎ কাজ করার।",
                "অর্থঃ আমি আল্লাহর নামে সওয়ার করছি, যাবতীয় প্রশংশা আল্লাহর জন্য পবিত্র সেই মহান সত্তা যিনি উহাকে আমাদের জন্য বশীভূত করে দিয়েছেন যদিও আমরা ঊহাকে বশীভূত করতে সক্ষম ছিলাম না, আর আমরা অবশ্যই ফিরে যাব আমাদের পালন কর্তার দিকে। আলহামদুলিলাহ, আলহামদুলিল্লাহ, আলহামদুলিল্লাহ। আল্লাহু আকবার, আল্লাহু আকবার, আল্লাহু আকবার। হে আল্লাহ, তুমি পবিত্র, আমি আমার সত্তার উপর জুলুম করেছি, অতএব তুমি ছাড়া আমাকে ক্ষমা করার আর কেহ নেই।",
                "অর্থঃ আমরা আল্লাহর নামে প্রবেশ করি, আল্লাহর নামেই(ঘর)থেকে বের হই, এবং আমাদের পালনকর্তা আল্লাহর উপর ই আমরা ভরসা করি।",
                "অর্থঃ আল্লাহর নামে শুরু করছি, শুরুতে এবং শেষে।",
                "অর্থঃ যাবতীয় প্রশংসা সেই আল্লাহর যিনি আমাকে এ পানাহার করালেন এবং উহার ক্ষমতা প্রদান করলেন, যাতে ছিলনা আমার পক্ষ থেকে উপায়-উদ্যোগ, ছিলনা কোন শক্তি-ক্ষমতা।",
                "অর্থঃ তুমি ভিন্ন ইবাদাতের কোন উপাস্য নেই। তুমি পবিত্র। নিশ্চই আমি জালিমদের অন্তর্ভুক্ত।",
                "অর্থঃ হে আল্লাহ, তোমার নাম নিয়েই আমি ঘুমাই এবং তোমার নাম নিয়েই জাগ্রত হব।"};

        ArrayList<Dua> myDuas = new ArrayList<>();

        for (int i=0;i<duaNames.length;i++){

            Dua dua = new Dua(duaNames[i], duaArabic[i], duaPronunciation[i], duaTranslation[i]);
            myDuas.add(dua);
            Log.i("Test", myDuas.get(i).duaTitle+"\n");

        }

        return myDuas;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "ALTER TABLE dua_table ADD COLUMN favorite INTEGER DEFAULT 0 NOT NULL");
        }
    };
}
