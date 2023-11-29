package com.practice.view_system_practice.clean_architecture_1.di.movie

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MovieScope // use it as the scope of MovieViewModel Dependency