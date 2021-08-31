package ar.com.wolox.android.example.ui.news

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewsModule {
    @ContributesAndroidInjector
    internal abstract fun newsActivity(): NewsActivity

    @ContributesAndroidInjector
    internal abstract fun newsFragment(): NewsFragment
}
