package com.example.football.di

import dagger.Module

@Module(
    subcomponents = [
        ClubsComponent::class,
        NationalitiesComponent::class,
        PositionsComponent::class
    ]
)
class AppSubcomponents
