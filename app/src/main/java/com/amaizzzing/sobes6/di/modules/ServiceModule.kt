package com.amaizzzing.sobes6.di.modules

import com.amaizzzing.sobes6.services.database.FireDatabase
import com.amaizzzing.sobes6.services.database.IFireDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule {
    @Provides
    @Singleton
    fun fireDatabase(firebaseFirestore: FirebaseFirestore): IFireDatabase =
        FireDatabase(firebaseFirestore)

    @Provides
    @Singleton
    fun firebaseFirestore(): FirebaseFirestore = Firebase.firestore
}